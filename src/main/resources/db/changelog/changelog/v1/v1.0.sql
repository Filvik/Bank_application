-- liquibase formatted sql

-- changeset sanch:1689329868507-1
CREATE TABLE account (balance numeric(38, 2), id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL, type_currency VARCHAR(255), CONSTRAINT account_pkey PRIMARY KEY (id));

