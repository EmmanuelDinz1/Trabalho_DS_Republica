INSERT INTO tb_morador (id, nome, cpf, data_nascimento, celular, email, contatos_familia, senha) VALUES (1, 'Emmanuel Diniz Cheroto', '12345678900', '2000-01-15', '31999990000', 'emmanuel@example.com', '(31)98888-7777', '$2a$10$abcdefg'); 
-- Atenção: senha já deve vir criptografada conforme BCrypt

INSERT INTO tb_tipo_conta (id, descricao, observacao) VALUES (1, 'Aluguel', 'Aluguel mensal da república');

INSERT INTO tb_conta (id, observacao, valor, data_vencimento, responsavel_id, tipo_conta_id, situacao) VALUES (1, 'Aluguel junho/2025', 1200.00, '2025-06-05', 1, 1, 'PENDENTE');

INSERT INTO tb_rateio (id, valor, status, conta_id, morador_id) VALUES (1, 1200.00, 'PENDENTE', 1, 1);

INSERT INTO tb_historico_conta (id, conta_id, morador_id, acao, timestamp) VALUES (1, 1, 1, 'PENDENTE', '2025-06-01T09:00:00');
