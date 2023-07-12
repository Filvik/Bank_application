CREATE TABLE IF NOT EXISTS public.account(
    ID bigint PRIMARY KEY NOT NULL,
    balance numeric(38,2),
    type_currency character varying(255)
    );