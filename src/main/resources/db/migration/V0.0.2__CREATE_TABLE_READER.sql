CREATE TABLE library.reader
(
    id            BIGSERIAL NOT NULL PRIMARY KEY,
    name          TEXT      NOT NULL,
    lastname      TEXT      NOT NULL,
    phone         TEXT      NOT NULL UNIQUE,
    created_date  TIMESTAMP NOT NULL DEFAULT now(),
    modified_date TIMESTAMP NOT NULL DEFAULT now()
);
