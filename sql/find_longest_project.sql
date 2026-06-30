SELECT id AS name, DATEDIFF('MONTH', start_date, finish_date) AS month_count
FROM project
WHERE DATEDIFF('MONTH', start_date, finish_date) = (
    SELECT MAX(DATEDIFF('MONTH', start_date, finish_date))
    FROM project
);
