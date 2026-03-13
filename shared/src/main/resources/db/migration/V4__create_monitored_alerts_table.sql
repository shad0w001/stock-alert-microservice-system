CREATE TABLE monitored_alerts (
    alert_id BIGINT PRIMARY KEY,
    symbol VARCHAR(10) NOT NULL,
    target_price DECIMAL(19, 4) NOT NULL,
    condition_type alert_condition NOT NULL,
    status alert_status DEFAULT 'PENDING',

    last_synced_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_monitored_alerts_lookup ON monitored_alerts(symbol, status);