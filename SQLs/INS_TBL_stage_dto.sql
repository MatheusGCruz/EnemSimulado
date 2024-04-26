SELECT TOP (1000) [id]
      ,[description]
      ,[message]
      ,[parent_stage]
      ,[stage]
      ,[telegram_command]
  FROM [ENEM_SIMULADO].[dbo].[stage_dto]

  /*
  INSERT INTO [ENEM_SIMULADO].[dbo].[stage_dto] ([description]
      ,[message]
      ,[parent_stage]
      ,[stage]
      ,[telegram_command]) 
	  VALUES
	  ('Registro de novos usuários','',0,1,'/register'),
	  ('Inativação de usuario','',0,2,'/exit'),
	  ('Lista as matrizes de conhecimento','',0,3,'/matrizes')


---*/