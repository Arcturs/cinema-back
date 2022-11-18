DO '
BEGIN
    ALTER TABLE seat_plan_for_session
        RENAME COLUMN session TO session_id;
EXCEPTION
    WHEN undefined_column THEN
END ';

DO '
BEGIN
    ALTER TABLE seat_plan_for_session
        RENAME COLUMN seat_plan_for_session TO seat_plan_for_session_id;
EXCEPTION
    WHEN undefined_column THEN
END ';