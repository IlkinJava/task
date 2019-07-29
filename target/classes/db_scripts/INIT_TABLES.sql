CREATE TABLE parking_payments (
  id         SERIAL PRIMARY KEY,
  code       VARCHAR(200),
  status     VARCHAR(20),
  debt       INTEGER,
  created_at TIMESTAMP,
  updated_at TIMESTAMP
);