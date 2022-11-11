-- time function round to 5s for the timestamp
CREATE FUNCTION round5(ts timestamp) RETURNS timestamp AS
$$
BEGIN
    RETURN date_trunc('hour', ts) + date_part('minute', ts):: int / 5 * interval '5 min';
END;
$$
    LANGUAGE PLPGSQL;
--- Group hosts by hardware info
SELECT
    cpu_number,id AS host_id,
    total_mem
FROM host_info
ORDER BY cpu_number, total_mem DESC;

-- Average Memory Usage for each host
SELECT
    host_info.id AS host_id,
    host_info.hostname, round5(host_usage.timestamp) AS ts,
    AVG(host_info.total_mem/1000 - host_usage.memory_free) as avg_used_mem_percentage
FROM host_info, host_usage
WHERE host_info.id = host_usage.host_id
GROUP BY ts, host_info.id;

-- Detect Host Failure
SELECT
    host_id,
    round5(timestamp) AS ts,
    COUNT(*) AS num_data_points
FROM host_usage
GROUP BY host_id, ts
HAVING COUNT(*) < 3;