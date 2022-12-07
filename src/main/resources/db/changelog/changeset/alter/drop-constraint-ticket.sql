ALTER TABLE ticket DROP CONSTRAINT ticket_constraint;

DROP INDEX order_id_ticket_index;

CREATE INDEX IF NOT EXISTS order_id_ticket_index ON ticket (order_id);