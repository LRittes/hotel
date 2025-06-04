DROP TABLE IF EXISTS Limpeza CASCADE;
DROP TABLE IF EXISTS Servico_Estadia CASCADE;
DROP TABLE IF EXISTS Servico_Extra CASCADE;
DROP TABLE IF EXISTS Estadia CASCADE;
DROP TABLE IF EXISTS Reserva CASCADE;
DROP TABLE IF EXISTS Quarto CASCADE;
DROP TABLE IF EXISTS Tipo_Quarto CASCADE;
DROP TABLE IF EXISTS Cliente CASCADE;
DROP TABLE IF EXISTS Hotel CASCADE;
DROP TABLE IF EXISTS Empregado CASCADE;

drop TRIGGER IF EXISTS trg_verificar_cpf_cliente ON cliente;
drop TRIGGER IF EXISTS trg_verificar_disponibilidade_antes_reserva ON reserva;
drop TRIGGER IF EXISTS trg_calcular_valor_reserva ON reserva;
drop TRIGGER IF EXISTS trg_validar_periodo_reserva ON reserva;
DROP TRIGGER IF EXISTS trg_sincronizar_estadia ON Reserva;

DROP FUNCTION IF EXISTS verificar_cpf_cliente, verificar_disponibilidade_quarto, calcular_valor_reserva,
validar_periodo_reserva , excluir_reservas_pendentes_expiradas, sincronizar_estadia_com_reserva;