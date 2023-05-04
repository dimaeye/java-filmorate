# java-filmorate

## Схема БД:
![Screenshot](filmorate-diagram.png)

## Примеры запросов для основных операций приложения:

### Получить список друзей пользователя и "статус дружбы":
```sql
SELECT  fr.*, 
        fst.status 
FROM
	(SELECT u.*, 
            	f.status_id,
            	f.friend_id
	FROM users AS u
	INNER JOIN friends AS f ON u.id = f.user_id
	WHERE u.id = ??
	UNION
	SELECT  u.*, 
	        f.status_id,
            	f.user_id
	FROM users AS u
	INNER JOIN friends AS f ON u.id = f.friend_id
	WHERE u.id = ??) AS fr
LEFT JOIN friendship_status AS fst ON fr.status_id = fst.id;
```

### Получить ТОП 10 фильмов, кол-во лайков и средний возраст пользователей, которые поставили лайк:
```sql
SELECT  f.*, 
        COUNT(u.id) AS likes_count,
        ROUND(AVG(EXTRACT('year' FROM CURRENT_DATE) - EXTRACT('year' FROM u.birthday))) AS avg_age
FROM films AS f
LEFT OUTER JOIN likes AS l ON f.id = l.film_id
LEFT OUTER JOIN users as u ON l.user_id = u.id
GROUP BY f.id
ORDER BY likes_count DESC;
```

### Получить полную информацию о фильмах:
```sql
SELECT  f.*, 
        mpa.title AS mpa_title, 
        STRING_AGG(g.title, ', ')  AS genre
FROM films AS f
LEFT OUTER JOIN film_genre AS fg ON f.id = fg.film_id
LEFT OUTER JOIN genre AS g ON g.id = fg.genre_id
LEFT OUTER JOIN mpa ON f.mpa_id = mpa.id
GROUP BY f.id, mpa.title;
```
