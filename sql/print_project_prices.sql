SELECT p.id AS name,
       (DATEDIFF('MONTH', p.start_date, p.finish_date) * (SELECT SUM(w.salary) FROM worker w JOIN project_worker pw ON w.id = pw.worker_id WHERE pw.project_id = p.id)) AS price
FROM project p
ORDER BY price DESC;
