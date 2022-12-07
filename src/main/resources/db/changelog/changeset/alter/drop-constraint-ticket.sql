ALTER TABLE ticket DROP CONSTRAINT IF EXISTS ticket_constraint;

DROP INDEX IF EXISTS order_id_ticket_index;

CREATE INDEX IF NOT EXISTS order_id_ticket_index ON ticket (order_id);