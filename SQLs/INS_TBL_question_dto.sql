SELECT *
  FROM [ENEM_SIMULADO].[dbo].[question_dto]

  UPDATE [ENEM_SIMULADO].[dbo].[question_dto] SET linguagem = 1


  INSERT INTO [ENEM_SIMULADO].[dbo].[question_dto]([questao], [ano], azul, linguagem)
  VALUES('Esse cartaz de campanha sugere que'
+ CHAR(13)+CHAR(10) + 'A os lix�es precisam de amplia��o.'
+ CHAR(13)+CHAR(10) + 'B o desperd�cio degrada o ambiente.'
+ CHAR(13)+CHAR(10) + 'C os mercados doam alimentos perec�veis.'
+ CHAR(13)+CHAR(10) + 'D a desnutri��o compromete o racioc�nio.'
+ CHAR(13)+CHAR(10) + 'E as resid�ncias carecem de refrigeradores.',
2023,
1,
1
)


SELECT * FROM    [ENEM_SIMULADO].[dbo].[question_dto]


UPDATE [ENEM_SIMULADO].[dbo].[question_dto]
SET questao = 
'Esse cartaz de campanha sugere que'
+ CHAR(13)+CHAR(10) + '(A) os lix�es precisam de amplia��o.'
+ CHAR(13)+CHAR(10) + '(B) o desperd�cio degrada o ambiente.'
+ CHAR(13)+CHAR(10) + '(C) os mercados doam alimentos perec�veis.'
+ CHAR(13)+CHAR(10) + '(D) a desnutri��o compromete o racioc�nio.'
+ CHAR(13)+CHAR(10) + '(E) as resid�ncias carecem de refrigeradores.', alternativa_correta = 2