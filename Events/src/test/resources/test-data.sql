DELETE FROM events;
DELETE FROM venues;
DELETE FROM users;

ALTER TABLE events ALTER COLUMN id RESTART WITH 1;

-- Crear usuario admin
INSERT INTO users (id, username, password, role)
VALUES (1, 'adminUser',
'$2a$10$ZlIRLzKzjI1lB1x9Rr6Qve5pyTqT9XyM7c4mzVn1sEh09IuTz.amC',
'ROLE_ADMIN');

-- Crear venue necesario para el test
INSERT INTO venues (venue_id, name, capacity)
VALUES (1, 'Test Venue', 500);

INSERT INTO events (title, description, start_at, end_at, venue_id)
VALUES (
    'Evento Inicial',
    'Evento cargado por test-data.sql',
    '2025-12-10T10:00:00',
    '2025-12-10T12:00:00',
    1
);