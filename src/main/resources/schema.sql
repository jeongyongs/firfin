CREATE TABLE users
(
    user_id               BIGINT  NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_status           TINYINT NOT NULL COMMENT '0=ACTIVE=활성, 1=INACTIVE=비활성',
    money                 BIGINT  NOT NULL,
    point                 BIGINT  NOT NULL,
    money_limit           BIGINT  NOT NULL,
    per_payment_limit     BIGINT  NOT NULL,
    daily_payment_limit   BIGINT  NOT NULL,
    monthly_payment_limit BIGINT  NOT NULL
);

CREATE TABLE payments
(
    payment_id     BIGINT     NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id        BIGINT     NOT NULL REFERENCES users (user_id),
    payment_status TINYINT    NOT NULL COMMENT '0=APPROVED=승인, 1=CANCELED=취소',
    transaction_id BINARY(16) NOT NULL UNIQUE COMMENT 'UUID',
    merchant_id    BIGINT     NOT NULL,
    amount         BIGINT     NOT NULL,
    create_at      DATETIME   NOT NULL,
    update_at      DATETIME   NULL
);

CREATE TABLE paybacks
(
    payback_id     BIGINT   NOT NULL PRIMARY KEY AUTO_INCREMENT,
    payment_id     BIGINT   NOT NULL UNIQUE REFERENCES payments (payment_id),
    payback_status TINYINT  NOT NULL COMMENT '0=PAID=지급, 1=CANCELED=취소',
    amount         BIGINT   NOT NULL,
    create_at      DATETIME NOT NULL,
    update_at      DATETIME NULL
);

CREATE INDEX idx_payments_user_status_date ON payments (user_id, payment_status, create_at);
