CREATE SCHEMA IF NOT EXISTS lending;

CREATE TYPE lending.item_status AS ENUM ('AVAILABLE', 'RESERVED', 'CHECKED_OUT', 'LOST', 'DAMAGED', 'ARCHIVED');
CREATE TYPE lending.reservation_status AS ENUM ('ACTIVE', 'CANCELLED', 'EXPIRED', 'FULFILLED');
CREATE TYPE lending.loan_status AS ENUM ('ACTIVE', 'OVERDUE', 'RETURNED', 'LOST');

CREATE TABLE lending.item (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    isbn VARCHAR(17) NOT NULL,
    barcode VARCHAR(20) NOT NULL UNIQUE,
    status lending.item_status NOT NULL DEFAULT 'AVAILABLE',
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE
);

CREATE TABLE lending.reservation (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    item_id UUID NOT NULL REFERENCES lending.item(id),
    user_id UUID NOT NULL,
    status lending.reservation_status NOT NULL DEFAULT 'ACTIVE',
    reserved_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP WITH TIME ZONE NOT NULL,
    fulfilled_at TIMESTAMP WITH TIME ZONE
);

CREATE TABLE lending.loan (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    item_id UUID NOT NULL REFERENCES lending.item(id),
    borrower_id UUID NOT NULL,
    status lending.loan_status NOT NULL DEFAULT 'ACTIVE',
    borrowed_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    due_at TIMESTAMP WITH TIME ZONE NOT NULL,
    returned_at TIMESTAMP WITH TIME ZONE
);

CREATE INDEX idx_item_isbn ON lending.item(isbn);

CREATE INDEX idx_item_status ON lending.item(status);

CREATE INDEX idx_loan_borrower ON lending.loan(borrower_id);

CREATE INDEX idx_loan_status ON lending.loan(status);

CREATE INDEX idx_loan_due_at ON lending.loan(due_at);