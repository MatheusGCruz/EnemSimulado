SELECT *
  FROM [ENEM_SIMULADO].[dbo].[question_dto]

  UPDATE [ENEM_SIMULADO].[dbo].[question_dto] SET linguagem = 1


  INSERT INTO [ENEM_SIMULADO].[dbo].[question_dto]([questao], [ano], azul, linguagem)
  VALUES('Esse cartaz de campanha sugere que'
+ CHAR(13)+CHAR(10) + 'A os lixões precisam de ampliação.'
+ CHAR(13)+CHAR(10) + 'B o desperdício degrada o ambiente.'
+ CHAR(13)+CHAR(10) + 'C os mercados doam alimentos perecíveis.'
+ CHAR(13)+CHAR(10) + 'D a desnutrição compromete o raciocínio.'
+ CHAR(13)+CHAR(10) + 'E as residências carecem de refrigeradores.',
2023,
1,
1
)


SELECT * FROM    [ENEM_SIMULADO].[dbo].[question_dto]


UPDATE [ENEM_SIMULADO].[dbo].[question_dto]
SET questao = 
'Esse cartaz de campanha sugere que'
+ CHAR(13)+CHAR(10) + '(A) os lixões precisam de ampliação.'
+ CHAR(13)+CHAR(10) + '(B) o desperdício degrada o ambiente.'
+ CHAR(13)+CHAR(10) + '(C) os mercados doam alimentos perecíveis.'
+ CHAR(13)+CHAR(10) + '(D) a desnutrição compromete o raciocínio.'
+ CHAR(13)+CHAR(10) + '(E) as residências carecem de refrigeradores.', alternativa_correta = 2