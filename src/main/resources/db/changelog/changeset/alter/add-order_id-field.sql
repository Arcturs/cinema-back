ALTER TABLE ticket
    ADD COLUMN IF NOT EXISTS order_id VARCHAR(40);

ALTER TABLE ticket
    ALTER COLUMN order_id SET NOT NULL;

ALTER TABLE ticket
    ADD CONSTRAINT ticket_constraint UNIQUE (order_id);

CREATE UNIQUE INDEX IF NOT EXISTS order_id_ticket_index ON ticket (order_id);