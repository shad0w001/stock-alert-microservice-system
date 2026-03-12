CREATE TABLE companies (
    symbol VARCHAR(10) PRIMARY KEY,
    company_name VARCHAR(255) NOT NULL,
    country VARCHAR(100),
    industry VARCHAR(100),
    market_capitalization BIGINT,
    shares_outstanding DECIMAL(19, 2),
    website VARCHAR(255),
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE stock_quotes (
    symbol VARCHAR(10) PRIMARY KEY REFERENCES companies(symbol) ON DELETE CASCADE,
    current_price DECIMAL(19, 4) NOT NULL,
    price_change DECIMAL(19, 4),
    percent_change DECIMAL(7, 4),
    high_price_day DECIMAL(19, 4),
    low_price_day DECIMAL(19, 4),
    open_price_day DECIMAL(19, 4),
    prev_close_price DECIMAL(19, 4),
    last_refreshed TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE price_history (
    id BIGSERIAL PRIMARY KEY,
    symbol VARCHAR(10) NOT NULL REFERENCES companies(symbol) ON DELETE CASCADE,
    price DECIMAL(19, 4) NOT NULL,
    recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_price_history_symbol_time ON price_history(symbol, recorded_at);