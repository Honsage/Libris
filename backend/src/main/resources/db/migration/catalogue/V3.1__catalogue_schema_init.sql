CREATE SCHEMA IF NOT EXISTS catalogue;

CREATE TABLE catalogue.author (
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(150) NOT NULL
);

CREATE TABLE catalogue.category (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE catalogue.book (
    isbn VARCHAR(17) PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    description TEXT,
    publication_year INT CHECK (publication_year > 0 AND publication_year <= EXTRACT(YEAR FROM CURRENT_DATE)),
    publisher VARCHAR(100),
    cover_url VARCHAR(255),
    is_archived BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE
);

CREATE TABLE catalogue.book_author (
    book_isbn VARCHAR(17) NOT NULL REFERENCES catalogue.book(isbn) ON DELETE CASCADE,
    author_id BIGINT NOT NULL REFERENCES catalogue.author(id) ON DELETE CASCADE,
    PRIMARY KEY (book_isbn, author_id)
);

CREATE TABLE catalogue.book_category (
    book_isbn VARCHAR(17) NOT NULL REFERENCES catalogue.book(isbn) ON DELETE CASCADE,
    category_id BIGINT NOT NULL REFERENCES catalogue.category(id) ON DELETE CASCADE,
    PRIMARY KEY (book_isbn, category_id)
);

CREATE INDEX idx_book_title_trgm ON catalogue.book USING GIN (title gin_trgm_ops);

CREATE INDEX idx_book_author_author_id ON catalogue.book_author(author_id);

CREATE INDEX idx_book_category_category_id ON catalogue.book_category(category_id);

CREATE INDEX idx_author_name_trgm ON catalogue.author USING GIN (full_name gin_trgm_ops);

CREATE INDEX idx_category_name_trgm ON catalogue.category USING GIN (name gin_trgm_ops);