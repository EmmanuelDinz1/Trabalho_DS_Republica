-- CORREÇÃO: Adicionada a coluna 'role' e o valor 'ADMIN'
INSERT INTO tb_morador (id, nome, cpf, data_nascimento, celular, email, contatos_familia, senha, role) VALUES (1, 'Emmanuel Diniz Cheroto', '12345678900', DATE '2000-01-15', '31999990000', 'emmanuel@example.com', '(31)98888-7777', '$2a$12$yJkKgbr6g9nIKCGb/ulOUuWhAi.tvQbQx6B/6Ake1up99LBOgxWA.', 'ADMIN');

INSERT INTO tb_morador (id, nome, cpf, data_nascimento, celular, email, contatos_familia, senha, role) VALUES (2, 'Gustavo Gusmão', '09876543211', DATE '2001-03-20', '31911112222', 'gustavo@example.com', '(31)92222-3333', '$2a$12$yJkKgbr6g9nIKCGb/ulOUuWhAi.tvQbQx6B/6Ake1up99LBOgxWA.', 'USER');

INSERT INTO tb_tipoconta (id, descricao, observacao) VALUES (1, 'Aluguel', 'Pagamento mensal do aluguel');
INSERT INTO tb_tipoconta (id, descricao, observacao) VALUES (2, 'Internet', 'Conta de internet fibra');
INSERT INTO tb_tipoconta (id, descricao, observacao) VALUES (3, 'Luz', 'Conta de energia elétrica');

INSERT INTO tb_conta (id, observacao, valor, data_vencimento, id_responsavel, id_tipoconta, situacao) VALUES (1, 'Aluguel junho/2025', 1200.00, DATE '2025-06-05', 1, 1, 'PENDENTE');
INSERT INTO tb_conta (id, observacao, valor, data_vencimento, id_responsavel, id_tipoconta, situacao) VALUES (2, 'Internet Vivo Fibra', 110.00, DATE '2025-06-10', 2, 2, 'PENDENTE');

INSERT INTO tb_rateio (id, valor, status, id_conta, id_morador) VALUES (1, 1200.00, 'PENDENTE', 1, 1);
INSERT INTO tb_rateio (id, valor, status, id_conta, id_morador) VALUES (2, 55.00, 'PENDENTE', 2, 1);
INSERT INTO tb_rateio (id, valor, status, id_conta, id_morador) VALUES (3, 55.00, 'PENDENTE', 2, 2);

INSERT INTO tb_historico_conta (conta_id, morador_id, acao, timestamp) VALUES
    (1, 1, 'PENDENTE', NOW()),
    (2, 2, 'PENDENTE', NOW());