
//取到作答次數
SELECT * ,count(q_id) TQ  FROM(
	SELECT *,count(q_id) total FROM takequiz   NATURAL join (
		SELECT cl_id, q_id,correct_answer, question, account, name FROM quiz NATURAL  join (
			SELECT * FROM class NATURAL  join (
				SELECT  c.account, c.name, enroll.co_id FROM enroll  NATURAL  join (
					SELECT  * FROM user
				)c where enroll.co_id = '15'
			) b  
		)a
	)z GROUP BY z.account HAVING answer = correct_answer
)x GROUP BY account
SELECT * FROM message 

SELECT account,	name ,total,count(q_id) CQ,cl_id,question  FROM(
	SELECT *,count(q_id) TQ FROM takequiz   NATURAL join (
		SELECT cl_id, q_id,correct_answer, question, account, name FROM quiz NATURAL  join (
			SELECT * FROM class NATURAL  join (
				SELECT  c.account, c.name, enroll.co_id FROM enroll  NATURAL  join (
					SELECT  * FROM user
				)c where enroll.co_id = '15'
			) b  
		)a
	)z GROUP BY z.account HAVING answer = correct_answer
)x GROUP BY account

//取得該堂課發言次數

SELECT account,name, count(m_id) TM FROM message NATURAL  join (
	SELECT * FROM class NATURAL  join (
		SELECT  c.account, c.name, enroll.co_id FROM enroll  NATURAL  join (
			SELECT  * FROM user
		)c where enroll.co_id = '15'
	) b  
)a GROUP BY account


// 取得一個學生在該上課的資料
SELECT P.account,P.name ,ifnull(Q.TQ, 0) TQ2 ,ifnull(Q.CQ, 0)CQ2,ifnull(M.TM, 0) TM2 FROM
	(SELECT  c.account, c.name, enroll.co_id FROM enroll  NATURAL  join (
		SELECT  * FROM user where role = 'student'
		)c where enroll.co_id = '15'
	)P	
left join
	(SELECT account, count(m_id) TM FROM message NATURAL  join (
		SELECT * FROM class NATURAL  join (
			SELECT  c.account, c.name, enroll.co_id FROM enroll  NATURAL  join (
				SELECT  * FROM user
			)c where enroll.co_id = '15'
		) b  
	)a GROUP BY account) M
ON P.account = M.account
left join
	(SELECT account ,count(q_id) CQ,TQ ,question  FROM(
		SELECT *,count(q_id) TQ FROM takequiz   NATURAL join (
			SELECT cl_id, q_id,correct_answer, question, account, name FROM quiz NATURAL  join (
				SELECT * FROM class NATURAL  join (
					SELECT  c.account, c.name, enroll.co_id FROM enroll  NATURAL  join (
						SELECT  * FROM user
					)c where enroll.co_id = '15'
				) b  
			)a
		)z GROUP BY z.account HAVING answer = correct_answer
	)x GROUP BY account ) Q
ON P.account = Q.account

//取得人+課
SELECT  c.account, c.name, enroll.co_id FROM enroll  NATURAL  join (
	SELECT  * FROM user
)c	

//取得課的所有問題
SELECT  c.account, c.name, enroll.co_id FROM enroll  NATURAL  join (
	SELECT  * FROM user
)c	


//抓全部- 失敗

SELECT user.account, user.name, a.co_id  FROM user  INNER JOIN (
	SELECT enroll.account, enroll.co_id   FROM enroll INNER JOIN(
		SELECT course.co_id   FROM course INNER JOIN(
			SELECT class.cl_id FROM class INNER JOIN(
				SELECT quiz.cl_id FROM quiz(
					SELECT FROM takequiz
				)e WHERE quiz.qid = e.qid
			)d  WHERE class.cl_id = d.cl_id
		) c WHERE course.co_id = c.co_id
	)b	WHERE enroll.co_id = b.co_id
) a	WHERE user.account = a.account






