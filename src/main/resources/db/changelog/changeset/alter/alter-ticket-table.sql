ALTER TABLE ticket
    ADD COLUMN IF NOT EXISTS is_paid BOOLEAN DEFAULT FALSE;

ALTER TABLE ticket
    ADD COLUMN IF NOT EXISTS transaction_start_timestamp TIMESTAMP;

ALTER TABLE ticket
    ALTER COLUMN transaction_start_timestamp SET NOT NULL;

ALTER TABLE ticket
    ADD COLUMN IF NOT EXISTS transaction_end_timestamp TIMESTAMP;

ALTER TABLE ticket
    ALTER COLUMN transaction_end_timestamp SET NOT NULL;