CREATE UNIQUE INDEX uq_pending_alerts ON alerts (user_id, symbol, target_price, condition_type)
WHERE status = 'PENDING';