ALTER TABLE events
ADD CONSTRAINT fk_event_venue
FOREIGN KEY (venue_id) REFERENCES venues(venue_id);

CREATE INDEX idx_event_start_date ON events(start_at);