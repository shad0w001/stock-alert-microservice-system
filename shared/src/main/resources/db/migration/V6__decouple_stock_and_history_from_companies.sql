ALTER TABLE stock_quotes
DROP CONSTRAINT IF EXISTS stock_quotes_symbol_fkey;

ALTER TABLE price_history
DROP CONSTRAINT IF EXISTS price_history_symbol_fkey;