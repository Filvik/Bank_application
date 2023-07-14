-- liquibase formatted sql

-- changeset user:1689344633583-2
CREATE TABLE type_operation (id INTEGER NOT NULL, description VARCHAR(255), CONSTRAINT pk_type_operation PRIMARY KEY (id));

INSERT INTO type_operation (id, description)
VALUES(1, 'Внесение'),(2, 'Снятие'),(3, 'Перевод');

