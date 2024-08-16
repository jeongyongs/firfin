INSERT INTO users(user_status, money, point, money_limit, per_payment_limit, daily_payment_limit, monthly_payment_limit)
VALUES (0, 10000, 0, 300000, 50000, 20000, 100000);

INSERT INTO payments(user_id, payment_status, transaction_id, merchant_id, amount, create_at, update_at)
VALUES (1, 0, X'123e4567e89b12d3a456426614174000', 123, 10000, NOW(), null);
