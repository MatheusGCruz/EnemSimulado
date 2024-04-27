SELECT *
  FROM [ENEM_SIMULADO].[dbo].[stage_dto]

/*

  INSERT INTO [ENEM_SIMULADO].[dbo].[stage_dto](
  [description],[message],[parent_stage],[stage],[telegram_command])
	  VALUES
	  ('Pesquisa questoes do Enem','Escolha uma opção:'+ CHAR(13)+CHAR(10) + '/texto para pesquisar pelo texto da questao'+ CHAR(13)+CHAR(10) + '/ano para pesquisar uma questao especifica', 0, 4, '/pesquisa'),
	  ('Pesquisa questoes do Enem pelo texto','Digite o texto para pesquisar', 4, 401, '/texto'),
	  ('Pesquisa questoes do Enem pelo ano e cor da prova','Digite o ano da prova', 4, 402, '/ano')
	  

  INSERT INTO [ENEM_SIMULADO].[dbo].[stage_dto]( [description],[message],[parent_stage],[stage],[telegram_command])
	  VALUES ('Prepara um simulado com as questões na base de dados','Digite o ano escolhido, ou 0 para todos os anos', 0, 5, '/simulado')

  INSERT INTO [ENEM_SIMULADO].[dbo].[stage_dto]( [description],[message],[parent_stage],[stage],[telegram_command], [has_follow_up], [child_stage]) VALUES 
  ('Seta o Ano escolhido','Digite o codigo da matriz escolhida:', 5, 501, null, 1, 502),
  ('Seta a Matriz escolhido','Digite a lingua desejada (ingles ou espanhol):', 501, 502, null, 1, 503),
  ('Seta a Linguagem escolhida','Digite o tipo de simulado (curto(5 questoes), medio(20 questoes) ou completo(45 questoes)):', 502, 503, null, 1, 504),
  ('Seta a Quantidade de Questões por Tópico','Digite o tipo de simulado (oculto(gabarito apenas no final) ou instantaneo(gabarito assim que responder)):', 503, 504, null, 1, 505),
  ('Seta a Confidencialidade','', 504, 505, null, 1, 6)
---*/

/*

UPDATE [ENEM_SIMULADO].[dbo].[stage_dto] SET message = 'Iniciando o Simulado.'+ CHAR(13)+CHAR(10) + 'O tempo passa agora a ser contado''+ CHAR(13)+CHAR(10) WHERE stage = 505
DELETE [ENEM_SIMULADO].[dbo].[stage_dto] WHERE id in (7,8,9,10,11)

---*/