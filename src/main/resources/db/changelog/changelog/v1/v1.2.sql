-- liquibase formatted sql

-- changeset user:1689353907749-3
CREATE TABLE operations (id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL, data BIGINT NOT NULL, id_account BIGINT NOT NULL, id_type_operation INTEGER NOT NULL, sum DECIMAL NOT NULL, CONSTRAINT pk_operations PRIMARY KEY (id));

ALTER TABLE operations ADD CONSTRAINT FK_1 FOREIGN KEY (id_account) REFERENCES account (id);

ALTER TABLE operations ADD CONSTRAINT FK_2 FOREIGN KEY (id_type_operation) REFERENCES type_operation (id);


