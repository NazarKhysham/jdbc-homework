SELECT c.name, COUNT(p.id) AS project_count
FROM client c
JOIN project p ON c.id = p.client_id
GROUP BY c.name
HAVING COUNT(p.id) = (
    SELECT MAX(count_proj) FROM (
        SELECT COUNT(id) AS count_proj FROM project GROUP BY client_id
    ) AS sub
);
