SELECT * from reserva
-- Função verifica disponibilidade do quarto
CREATE OR REPLACE FUNCTION verificar_disponibilidade_quarto()
RETURNS TRIGGER AS $$
DECLARE
    quarto_ocupado BOOLEAN;
BEGIN
    -- Verifica se o quarto específico está disponível no período solicitado
    SELECT EXISTS (
        SELECT 1
        FROM Reserva r
        WHERE r.cod_hotel = NEW.cod_hotel
          AND r.num_quarto = NEW.num_quarto
          AND r.status = 'confirmada'
          AND r.cod_reserva != COALESCE(NEW.cod_reserva, 0)  -- Ignora a própria reserva em caso de UPDATE
          AND (
              -- Verifica se há sobreposição de datas:
              -- 1. O check-in da nova reserva ocorre durante uma reserva existente
              (NEW.data_checkin_prevista >= r.data_checkin_prevista AND 
               NEW.data_checkin_prevista < r.data_checkout_previsto)
              OR
              -- 2. O check-out da nova reserva ocorre durante uma reserva existente
              (NEW.data_checkout_previsto > r.data_checkin_prevista AND 
               NEW.data_checkout_previsto <= r.data_checkout_previsto)
              OR
              -- 3. A nova reserva engloba completamente uma reserva existente
              (NEW.data_checkin_prevista <= r.data_checkin_prevista AND 
               NEW.data_checkout_previsto >= r.data_checkout_previsto)
          )
    ) INTO quarto_ocupado;

    -- Se o quarto estiver ocupado no período, rejeitar a reserva
    IF quarto_ocupado THEN
        RAISE EXCEPTION 'O quarto % do hotel % não está disponível para o período de % a %', 
                        NEW.num_quarto, NEW.cod_hotel, 
                        NEW.data_checkin_prevista, NEW.data_checkout_previsto;
    END IF;

    -- Verificar se o tipo de quarto solicitado corresponde ao quarto escolhido
    PERFORM 1
    FROM Quarto q
    WHERE q.num_quarto = NEW.num_quarto
      AND q.cod_hotel = NEW.cod_hotel
      AND q.cod_tipo_quarto = NEW.cod_tipo_quarto;
    
    IF NOT FOUND THEN
        RAISE EXCEPTION 'O quarto % do hotel % não é do tipo %', 
                        NEW.num_quarto, NEW.cod_hotel, NEW.tipo_quarto;
    END IF;

    -- Se chegou aqui, o quarto está disponível
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- DROP TRIGGER verificar_disponibilidade_antes_reserva on reserva; DROP FUNCTION verificar_disponibilidade_quarto 
CREATE TRIGGER trg_verificar_disponibilidade_antes_reserva
BEFORE INSERT OR UPDATE ON Reserva
FOR EACH ROW
WHEN (NEW.status = 'confirmada')
EXECUTE FUNCTION verificar_disponibilidade_quarto();

-- Função de verificar cpf
CREATE OR REPLACE FUNCTION verificar_cpf_cliente()
RETURNS TRIGGER AS $$
BEGIN
    -- Verifica se o CPF já existe em outro cliente
    IF EXISTS (
        SELECT 1 FROM Cliente
        WHERE cpf = NEW.cpf AND cod_cliente <> NEW.cod_cliente
    ) THEN
        RAISE EXCEPTION 'CPF % já está cadastrado para outro cliente.', NEW.cpf;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_verificar_cpf_cliente
BEFORE INSERT OR UPDATE ON Cliente
FOR EACH ROW
EXECUTE FUNCTION verificar_cpf_cliente();

-- valor total da reserva
CREATE OR REPLACE FUNCTION calcular_valor_reserva()
RETURNS TRIGGER AS $$
DECLARE
    preco_base NUMERIC(10,2);
    andar_quarto INT;
    dias_estadia INT;
    fator_andar NUMERIC := 1.0;
BEGIN
    -- Obter o preço da diária
    SELECT preco_noite INTO preco_base
    FROM Tipo_Quarto
    WHERE cod_tipo_quarto = NEW.cod_tipo_quarto and cod_hotel = new.cod_hotel;

    -- Obter o andar do quarto
    SELECT andar INTO andar_quarto
    FROM Quarto
    WHERE num_quarto = NEW.num_quarto AND cod_hotel = NEW.cod_hotel;

    -- Calcular dias da estadia (data_checkout - data_checkin)
    dias_estadia := GREATEST(NEW.data_checkout_previsto - NEW.data_checkin_prevista, 1);

    -- Calcular fator de aumento por andar acima do primeiro
    IF andar_quarto > 1 THEN
        fator_andar := 1 + 0.1 * (andar_quarto - 1);
    END IF;

    -- Calcular o valor final
    NEW.valor := preco_base * dias_estadia * fator_andar;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_calcular_valor_reserva
BEFORE INSERT OR UPDATE ON Reserva
FOR EACH ROW
EXECUTE FUNCTION calcular_valor_reserva();

-- Função de validar periodo de check in e check out
CREATE OR REPLACE FUNCTION validar_periodo_reserva()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.data_checkout_previsto <= NEW.data_checkin_prevista THEN
        RAISE EXCEPTION 'A data de check-out (%)
                         deve ser posterior à data de check-in (%)',
                         NEW.data_checkout_previsto, NEW.data_checkin_prevista;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_validar_periodo_reserva
BEFORE INSERT OR UPDATE ON Reserva
FOR EACH ROW
EXECUTE FUNCTION validar_periodo_reserva();

-- Função de excluir reservas depois de 2 dias
CREATE OR REPLACE FUNCTION excluir_reservas_pendentes_expiradas()
RETURNS VOID AS $$
BEGIN
    UPDATE Reserva
    set status = 'cancelada'
    WHERE status = 'pendente'
      AND CURRENT_DATE >= data_reserva + INTERVAL '2 days';
END;
$$ LANGUAGE plpgsql;

-- função inserir estádia depois de confirmar a reserva
-- DROP trigger trg_sincronizar_estadia on reserva; drop function sincronizar_estadia_com_reserva
CREATE OR REPLACE FUNCTION sincronizar_estadia_com_reserva()
RETURNS TRIGGER AS $$
BEGIN
    -- Quando a reserva é confirmada
    IF NEW.status = 'confirmada' THEN
            INSERT INTO Estadia (
                data_checkin,
                data_checkout,
                cod_cliente,
                num_quarto,
                cod_hotel
            )
            VALUES (
                NEW.data_checkin_prevista,
                NEW.data_checkout_previsto,
                NEW.cod_cliente,
                NEW.num_quarto,
                NEW.cod_hotel
            );
	END IF;	

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_sincronizar_estadia
AFTER INSERT OR UPDATE ON Reserva
FOR EACH ROW
EXECUTE FUNCTION sincronizar_estadia_com_reserva();
