CREATE TABLE library.book
(
    id               BIGSERIAL                     NOT NULL PRIMARY KEY,
    title            TEXT                          NOT NULL,
    author           TEXT                          NOT NULL,
    genre            TEXT                          NOT NULL,
    inventory_number BIGINT                        NOT NULL UNIQUE,
    start_date       DATE                          NULL,
    end_date         DATE                          NULL,
    reader_id        BIGINT references library.reader (id) NULL,
    created_date     TIMESTAMP                     NOT NULL DEFAULT now(),
    modified_date    TIMESTAMP                     NOT NULL DEFAULT now()
);

