SELECT * from hotel
-- Tentar criar uma reserva para o mesmo tipo de quarto no mesmo hotel
-- com sobreposição de datas (deve falhar devido ao trigger)
INSERT INTO Reserva (
    data_reserva, 
    data_checkin_prevista, 
    data_checkout_previsto, 
    cod_tipo_quarto, 
    cama_extra, 
    cod_cliente, 
    cod_hotel, 
  	num_quarto,
  valor,
    status
) VALUES (
    '2025-05-02',  -- data da reserva
    '2025-05-13',  -- check-in (durante a reserva anterior)
    '2025-05-17',  -- check-out
    1,    -- mesmo tipo de quarto
    false,         -- sem cama extra
    2,             -- outro cliente
    1,             -- mesmo hotel
  	101,
  	1000,
    'confirmada'   -- status da reserva
);

-- test verifica cpf cliente
INSERT INTO Cliente (cpf, nome, endereco, telefone) 
VALUES ('12345678909','Ana Silva 2', 'Rua das Flores, 123, São Paulo, SP', '(11) 98765-4321');

-- test calcular valor total da reserva
INSERT INTO Quarto (num_quarto, cod_hotel, andar, cod_tipo_quarto) 
VALUES (205, 1, 2, 1);

INSERT INTO Reserva (data_reserva, data_checkin_prevista, data_checkout_previsto, cod_tipo_quarto, num_quarto, cama_extra, cod_cliente, cod_hotel, status) 
VALUES ('2025-06-01', '2025-06-15', '2025-06-20', 1, 205, FALSE, 1, 1, 'confirmada');

SELECT * from reserva

-- test check out antes de check in
INSERT INTO Reserva (data_reserva, data_checkin_prevista, data_checkout_previsto, cod_tipo_quarto, num_quarto, cama_extra, cod_cliente, cod_hotel, status) 
VALUES ('2025-07-01', '2025-07-25', '2025-07-22', 1, 205, FALSE, 1, 1, 'confirmada');

-- test cancela reservas expiradas
SELECT excluir_reservas_pendentes_expiradas();

-- Plugin cron que executa todo dia às 2 da manhã
SELECT cron.schedule(
  'remove_reservas_expiradas',
  '0 2 * * *',
  'SELECT excluir_reservas_pendentes_expiradas()'
);

-- test para sincronizar estádia com reservada confirmada
INSERT INTO Reserva (data_reserva, data_checkin_prevista, data_checkout_previsto, cod_tipo_quarto, num_quarto, cama_extra, cod_cliente, cod_hotel, status) 
VALUES ('2025-08-01', '2025-08-15', '2025-08-22', 1, 205, FALSE, 1, 1, 'pendente');

UPDATE reserva
set status = 'confirmada'
where cod_reserva = 8

select * FROM reserva
select * FROM estadia
SELECT * from quarto