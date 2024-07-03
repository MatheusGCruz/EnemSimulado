SELECT 
COUNT(*) AS [Individual Sessions], 
COUNT(DISTINCT(telegram_chat_id)) AS [Distinct Sessions], 
CAST(created_at AS DATE) AS [Session Day]

FROM session_dto
GROUP BY CAST(created_at AS DATE) 
ORDER BY CAST(created_at AS DATE) DESC