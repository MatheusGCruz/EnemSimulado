/*
UPDATE [dbo].[stage_dto] SET show_menu = 1 where stage in (1,2,3,4,90,91,5,51,92)
--*/


SELECT *  FROM [ENEM_SIMULADO].[dbo].[stage_dto]

/*

INSERT INTO [dbo].[stage_dto](admin_stage, child_stage, description, has_follow_up, message, parent_stage, show_menu, stage, stage_order, telegram_command) VALUES
-- (0, 0, 'Registro de novos usuários', 1, 'Mensagem-Registro', 0, 1, 1, NULL, '/register'),
-- (0, 0, 'Inativação de usuario', 1, 'Mensagem - Inativação', 0, 1, 2, NULL, '/exit'),
-- (0, 0, 'Lista as matrizes de conhecimento', 0, 'Mensagem-Matrizes', 0, 1, 3, NULL, '/matrizes'),
-- (0, 0, 'Pesquisa questoes do Enem', 1, 'Escolha uma opção: /texto para pesquisar pelo texto da questao, /ano para pesquisar uma questao especifica', 0, 1, 4, NULL, '/pesquisa'),
-- (0, 0, 'Pesquisa questoes do Enem pelo texto', 1, 'Digite o texto para pesquisar', 4, 0, 401, NULL, '/texto'),
-- (0, 0, 'Pesquisa questoes do Enem pelo ano e cor da prova', 1, 'Digite a cor do caderno, o ano da prova, e o numero da questão (separados por virgulas):', 4, 0, 402, NULL, '/ano'),
-- (0, 6, 'Seta a Confidencialidade', 1, 'Iniciando o Simulado.O tempo passa agora a ser contado', 504, 0, 505, NULL, NULL),
-- (1, 901, 'Insere nova questão', 1, 'Digite o ano, questão e matriz:', 0, 1, 90, NULL, '/new'),
-- (1, 9, 'Insere nova questão', 1, 'Digite o ano, questão e matriz:', 9, 0, 901, NULL, NULL),
-- (1, 910, 'Insere nova questão com imagem', 1, 'Digite o ano, questão e matriz:', 911, 0, 912, NULL, NULL),
-- (1, 910, 'Insere nova questão com imagem', 1, 'Digite o ano, questão e matriz:', 0, 1, 91, NULL, '/newImage'),
-- (1, 911, 'Insere nova questão com imagem', 1, 'Digite a questão:', 91, 0, 910, NULL, NULL),
-- (1, 91, 'Insere nova questão com imagem', 1, 'Envie a Imagem escolhida:', 910, 0, 911, NULL, NULL),
-- (0, 501, 'Cria um novo simulado', 1, 'Escolha a linguagem desejada', 0, 1, '5', NULL, '/simulado'),
-- (0, 501, 'Cria um novo simulado Rápido', 1, 'Escolha a linguagem desejada', 0, 1, 51, NULL, '/simulado_rapido'),
-- (1, 0, 'Insere novo lote de questões', 1, 'Mensagem-lote', 0, 1, 92, NULL, '/lote_questao'),
-- (0, 502, 'Seleciona a linguagem', 1, 'Escolha o ano desejado', 500, 0, 501, NULL, NULL),
-- (0, 600, 'Seleciona o ano', 1, 'Simulado iniciado. Boa sorte', 501, 0, 502, NULL, NULL),
-- (0, 699, 'Encerra simulado', 0, 'Encerra simulado', 600, 0, 699, null, null)


---*/

