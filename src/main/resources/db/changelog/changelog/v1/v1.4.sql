-- liquibase formatted sql

-- changeset user:1689866613896-4
INSERT INTO public.account (id, balance, type_currency) VALUES (1000, 1000, 'usdt'),(2000, 2000, 'usdt'),(3000, 3000, 'rub'),
 (4000, 4000, 'usd'),(5000, 5000, 'usd'),(6000, 6000, 'rub'), (7000, 7000, 'eur'),(8000, 8000, 'eur'),(9000, 9000, 'rub');