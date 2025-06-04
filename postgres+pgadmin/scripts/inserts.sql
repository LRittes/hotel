-- Hotel (cod_hotel, nome, endereco, telefone)
INSERT INTO Hotel (nome, endereco, telefone) 
VALUES ('Hotel Estrela do Mar', 'Av. Beira Mar, 1500, Salvador, BA', '(71) 3555-1234');

INSERT INTO Hotel (nome, endereco, telefone) 
VALUES ('Hotel Montanha Azul', 'Rua das Araucárias, 250, Gramado, RS', '(54) 3295-7890');

-- Cliente (cod_cliente, nome, endereco, telefone)
INSERT INTO Cliente (cpf, nome, endereco, telefone) 
VALUES ('12345678909','Ana Silva', 'Rua das Flores, 123, São Paulo, SP', '(11) 98765-4321');

INSERT INTO Cliente (cpf, nome, endereco, telefone) 
VALUES ('09876543212','Roberto Santos', 'Av. Paulista, 1000, Apto 501, São Paulo, SP', '(11) 91234-5678');

-- Tipo_Quarto (tipo_quarto, cod_hotel, preco_noite)
INSERT INTO Tipo_Quarto (cod_tipo_quarto, plano, tipo_quarto, cod_hotel, preco_noite) 
VALUES (1, 'standard', 'single', 1, 250.00);

INSERT INTO Tipo_Quarto (cod_tipo_quarto, plano, tipo_quarto, cod_hotel, preco_noite) 
VALUES (2, 'standard', 'duplo', 1, 450.00);

INSERT INTO Tipo_Quarto (cod_tipo_quarto, plano, tipo_quarto, cod_hotel, preco_noite) 
VALUES (3, 'standard', 'casal', 1, 550.00);

INSERT INTO Tipo_Quarto (cod_tipo_quarto, plano, tipo_quarto, cod_hotel, preco_noite) 
VALUES (4, 'standard', 'suite master', 1, 750.00);

INSERT INTO Tipo_Quarto (cod_tipo_quarto, plano, tipo_quarto, cod_hotel, preco_noite) 
VALUES (5, 'luxo', 'single', 1, 450.00);

INSERT INTO Tipo_Quarto (cod_tipo_quarto, plano, tipo_quarto, cod_hotel, preco_noite) 
VALUES (6, 'luxo', 'duplo', 1, 650.00);

INSERT INTO Tipo_Quarto (cod_tipo_quarto, plano, tipo_quarto, cod_hotel, preco_noite) 
VALUES (7, 'luxo', 'casal', 2, 750.00);

INSERT INTO Tipo_Quarto (cod_tipo_quarto, plano, tipo_quarto, cod_hotel, preco_noite) 
VALUES (8, 'luxo', 'suite master', 2, 950.00);

INSERT INTO Tipo_Quarto (cod_tipo_quarto, plano, tipo_quarto, cod_hotel, preco_noite) 
VALUES (9, 'vip', 'single', 1, 650.00);

INSERT INTO Tipo_Quarto (cod_tipo_quarto, plano, tipo_quarto, cod_hotel, preco_noite) 
VALUES (10, 'vip', 'duplo', 2, 850.00);

INSERT INTO Tipo_Quarto (cod_tipo_quarto, plano, tipo_quarto, cod_hotel, preco_noite) 
VALUES (11, 'vip', 'casal', 2, 950.00);

INSERT INTO Tipo_Quarto (cod_tipo_quarto, plano, tipo_quarto, cod_hotel, preco_noite) 
VALUES (12, 'vip', 'suite master', 2, 1150.00);

INSERT INTO Tipo_Quarto (cod_tipo_quarto, plano, tipo_quarto, cod_hotel, preco_noite) 
VALUES (13, 'presidencial', 'single', 1, 850.00);

INSERT INTO Tipo_Quarto (cod_tipo_quarto, plano, tipo_quarto, cod_hotel, preco_noite) 
VALUES (14, 'presidencial', 'duplo', 1, 1050.00);

INSERT INTO Tipo_Quarto (cod_tipo_quarto, plano, tipo_quarto, cod_hotel, preco_noite) 
VALUES (15, 'presidencial', 'casal', 2, 1150.00);

INSERT INTO Tipo_Quarto (cod_tipo_quarto, plano, tipo_quarto, cod_hotel, preco_noite) 
VALUES (16, 'presidencial', 'suite master', 2, 1350.00);

-- Quarto (num_quarto, cod_hotel, andar, tipo_quarto)
INSERT INTO Quarto (num_quarto, cod_hotel, andar, cod_tipo_quarto) 
VALUES (101, 1, 1, 1);

INSERT INTO Quarto (num_quarto, cod_hotel, andar, cod_tipo_quarto) 
VALUES (307, 1, 3, 2);

INSERT INTO Quarto (num_quarto, cod_hotel, andar, cod_tipo_quarto) 
VALUES (605, 2, 6, 11);

INSERT INTO Quarto (num_quarto, cod_hotel, andar, cod_tipo_quarto) 
VALUES (701, 2, 7, 16);

-- Reserva (cod_reserva, data_reserva, data_checkin_prevista, data_checkout_previsto, tipo_quarto, num_quarto, cama_extra, cod_cliente, cod_hotel, valor, status)
INSERT INTO Reserva (data_reserva, data_checkin_prevista, data_checkout_previsto, cod_tipo_quarto, num_quarto, cama_extra, cod_cliente, cod_hotel, valor, status) 
VALUES ('2025-05-01', '2025-05-15', '2025-05-20', 1, 101, FALSE, 1, 1, 1250.00, 'confirmada');

INSERT INTO Reserva (data_reserva, data_checkin_prevista, data_checkout_previsto, cod_tipo_quarto, num_quarto, cama_extra, cod_cliente, cod_hotel, valor, status) 
VALUES ('2025-05-05', '2025-06-10', '2025-06-15', 2, 307, TRUE, 2, 1, 2350.00, 'pendente');

-- Estadia (cod_estadia, data_checkin, data_checkout, cod_cliente, num_quarto, cod_hotel)
INSERT INTO Estadia (data_checkin, data_checkout, cod_cliente, num_quarto, cod_hotel) 
VALUES ('2025-05-15', '2025-05-20', 1, 101, 1);

INSERT INTO Estadia (data_checkin, data_checkout, cod_cliente, num_quarto, cod_hotel) 
VALUES ('2025-04-20', '2025-04-25', 2, 605, 2);

-- Servico_Extra (cod_servico, descricao, preco)
INSERT INTO Servico_Extra (descricao, preco) 
VALUES ('Café da manhã no quarto', 50.00);

INSERT INTO Servico_Extra (descricao, preco) 
VALUES ('Massagem relaxante', 180.00);

-- Servico_Estadia (cod_estadia, cod_servico, data_hora)
INSERT INTO Servico_Estadia (cod_estadia, cod_servico, data_hora) 
VALUES (1, 1, '2025-05-16 08:30:00');

INSERT INTO Servico_Estadia (cod_estadia, cod_servico, data_hora) 
VALUES (2, 2, '2025-04-22 14:00:00');

-- Empregado (cod_empregado, nome)
INSERT INTO Empregado (nome) 
VALUES ('Mariana Oliveira');

INSERT INTO Empregado (nome) 
VALUES ('João Pereira');

-- Limpeza (cod_empregado, cod_hotel, num_quarto, data)
INSERT INTO Limpeza (cod_empregado, cod_hotel, num_quarto, data) 
VALUES (1, 1, 101, '2025-05-16');

INSERT INTO Limpeza (cod_empregado, cod_hotel, num_quarto, data) 
VALUES (2, 2, 605, '2025-04-22');

SELECT * from reserva