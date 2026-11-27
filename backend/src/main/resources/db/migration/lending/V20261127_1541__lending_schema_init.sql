CREATE SCHEMA IF NOT EXISTS lending;

CREATE TABLE lending.item (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    isbn VARCHAR(17) NOT NULL,
    barcode VARCHAR(20) NOT NULL UNIQUE,
    status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE'
        CONSTRAINT item_status_check CHECK (status in ('AVAILABLE', 'RESERVED', 'CHECKED_OUT', 'LOST', 'DAMAGED', 'ARCHIVED')),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE
);

CREATE TABLE lending.reservation (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    item_id UUID NOT NULL REFERENCES lending.item(id),
    user_id UUID NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE'
        CONSTRAINT reservation_status_check CHECK (status in ('ACTIVE', 'CANCELLED', 'EXPIRED', 'FULFILLED')),
    reserved_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP WITH TIME ZONE NOT NULL,
    fulfilled_at TIMESTAMP WITH TIME ZONE
);

CREATE TABLE lending.loan (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    item_id UUID NOT NULL REFERENCES lending.item(id),
    borrower_id UUID NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE'
        CONSTRAINT loan_status_check CHECK (status in ('ACTIVE', 'OVERDUE', 'RETURNED', 'LOST')),
    borrowed_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    due_at TIMESTAMP WITH TIME ZONE NOT NULL,
    returned_at TIMESTAMP WITH TIME ZONE
);

CREATE INDEX idx_item_isbn ON lending.item(isbn);

CREATE INDEX idx_item_status ON lending.item(status);

CREATE INDEX idx_loan_borrower ON lending.loan(borrower_id);

CREATE INDEX idx_loan_status ON lending.loan(status);

CREATE INDEX idx_loan_due_at ON lending.loan(due_at);