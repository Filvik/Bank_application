-- liquibase formatted sql

-- changeset user:1689866613896-3
ALTER TABLE operations ADD id_account_recipient BIGINT;

ALTER TABLE operations ADD CONSTRAINT FK_3 FOREIGN KEY (id_account_recipient) REFERENCES account (id);

ALTER TABLE operations DROP CONSTRAINT FK_2;

DELETE FROM public.type_operation WHERE id >= 3;

INSERT INTO type_operation (id, description) VALUES (3, 'Перевод другому клиенту');

ALTER TABLE operations ADD CONSTRAINT FK_2 FOREIGN KEY (id_type_operation) REFERENCES type_operation (id);



