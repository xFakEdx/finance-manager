CREATE TABLE account
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_name VARCHAR(255) UNIQUE NOT NULL,
    balance      DECIMAL(10, 2)      NOT NULL DEFAULT 0.00,
    last_changed TIMESTAMP           NOT NULL,
    created_at   TIMESTAMP           NOT NULL Default NOW(),
    version      INT                 NOT NULL DEFAULT 0
) collate = utf8mb3_general_ci;

CREATE TABLE category
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
) collate = utf8mb3_general_ci;

-- Initiale Kategorien einfügen
INSERT INTO category (name)
VALUES ('SALARY'),
       ('RENT'),
       ('GROCERIES'),
       ('ENTERTAINMENT'),
       ('SAVINGS'),
       ('INVESTMENT'),
       ('UTILITIES'),
       ('TRAVEL'),
       ('HEALTHCARE'),
       ('EDUCATION'),
       ('TRANSPORTATION'),
       ('RESTAURANT'),
       ('INSURANCE'),
       ('MISCELLANEOUS');


CREATE TABLE transaction
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    transaction_name VARCHAR(64) UNIQUE NOT NULL,
    transaction_type VARCHAR(50)        NOT NULL,
    amount           DECIMAL(10, 2)     NOT NULL,
    currency         VARCHAR(3)         NOT NULL,
    transaction_date TIMESTAMP          NOT NULL,
    category_id      BIGINT             NULL,
    account_id       BIGINT             NOT NULL,
    version          INT                NOT NULL DEFAULT 0,
    cleared          BOOLEAN            NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_account FOREIGN KEY (account_id) REFERENCES account (id) ON DELETE CASCADE,
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE SET NULL
) collate = utf8mb3_general_ci;


