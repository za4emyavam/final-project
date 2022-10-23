DROP FUNCTION if exists f_check_payment(integer);
DROP TABLE if exists request_additional_services;
DROP TABLE if exists connection_request;
DROP TABLE if exists user_tariffs;
DROP TABLE if exists tariff;
DROP TABLE if exists transaction;
DROP TABLE if exists fp_schema.user;
DROP TABLE if exists user_info;
DROP TABLE if exists service;
DROP TABLE if exists additional_service;
DROP TYPE if exists role_type;
DROP TYPE if exists user_status_type;
DROP TYPE if exists tariff_status_type;
DROP TYPE if exists request_status_type;
DROP TYPE if exists transaction_type;
DROP TYPE if exists transaction_status_type;

CREATE SCHEMA if not exists fp_schema;

CREATE TYPE role_type AS ENUM ('user', 'admin', 'main_admin');
CREATE TYPE user_status_type AS ENUM ('subscribed', 'blocked');
CREATE TYPE tariff_status_type AS ENUM ('disabled', 'active');
CREATE TYPE request_status_type AS ENUM ('in processing', 'rejected', 'approved');
CREATE TYPE transaction_type AS ENUM ('debit', 'refill');
CREATE TYPE transaction_status_type AS ENUM ('successful', 'denied');

CREATE TABLE fp_schema.user
(
    user_id           SERIAL PRIMARY KEY,
    email             varchar(320)     NOT NULL UNIQUE,
    pass              varchar(64)      NOT NULL,
    user_description  INTEGER          NULL,
    registration_date date             NOT NULL,
    user_role         role_type DEFAULT ('user'),
    user_status       user_status_type NULL,
    user_balance      INTEGER   DEFAULT 0,
    firstname         varchar(30)      NOT NULL,
    middle_name       varchar(30)      NOT NULL,
    surname           varchar(30)      NOT NULL,
    telephone_number  varchar(30)      NOT NULL
);

/*CREATE OR REPLACE FUNCTION f_t_user()
    RETURNS trigger AS
$BODY$
BEGIN
    if (NEW.balance is NULL) then
        INSERT INTO user_balance DEFAULT VALUES RETURNING user_balance_id INTO NEW.balance;
    end if;
    RETURN NEW;
END;
$BODY$
    LANGUAGE plpgsql VOLATILE;

CREATE TRIGGER t_user
    BEFORE INSERT
    ON fp_schema.user
    FOR EACH ROW
EXECUTE PROCEDURE f_t_user();*/

CREATE TABLE transaction
(
    transaction_id     SERIAL PRIMARY KEY,
    balance_id         INTEGER,
    type               transaction_type,
    transaction_amount INTEGER,
    transaction_date   DATE DEFAULT CURRENT_DATE,
    transaction_status transaction_status_type NULL,
    FOREIGN KEY (balance_id) REFERENCES fp_schema.user (user_id) ON DELETE CASCADE
);

/*CREATE TABLE balance_transactions
(
    balance_transactions_id SERIAL PRIMARY KEY,
    balance_id              INTEGER NOT NULL,
    transaction_id          INTEGER NOT NULL,
    transaction_status      transaction_status_type,
    FOREIGN KEY (balance_id) REFERENCES user_balance (user_balance_id) ON DELETE CASCADE,
    FOREIGN KEY (transaction_id) REFERENCES transaction (transaction_id) ON DELETE CASCADE
);*/

CREATE OR REPLACE FUNCTION add(integer, integer) RETURNS integer
AS
'select $1 + $2;'
    LANGUAGE SQL
    IMMUTABLE
    RETURNS NULL ON NULL INPUT;

CREATE OR REPLACE FUNCTION subtract(integer, integer) RETURNS integer
AS
'select $1 - $2;'
    LANGUAGE SQL
    IMMUTABLE
    RETURNS NULL ON NULL INPUT;

CREATE OR REPLACE FUNCTION f_t_transaction()
    RETURNS trigger AS
$BODY$
DECLARE
    temp_user_balance integer;
BEGIN
    SELECT INTO temp_user_balance user_balance FROM "user" WHERE NEW.balance_id = user_id;
    IF (NEW.type = 'refill') THEN
        UPDATE fp_schema."user" u
        SET user_balance=add(temp_user_balance, NEW.transaction_amount)
        WHERE NEW.balance_id = user_id;
        UPDATE transaction t SET transaction_status='successful' WHERE t.transaction_id = New.transaction_id;
    ELSE
        IF (temp_user_balance >= NEW.transaction_amount) THEN
            UPDATE fp_schema."user" u
            SET user_balance=subtract(user_balance, NEW.transaction_amount)
            WHERE NEW.balance_id = user_id;
            UPDATE transaction t SET transaction_status='successful' WHERE t.transaction_id = New.transaction_id;
            UPDATE fp_schema.user u SET user_status='subscribed' WHERE u.user_id = NEW.balance_id;
        ELSE
            UPDATE fp_schema.user u SET user_status='blocked' WHERE u.user_id = NEW.balance_id;
            UPDATE transaction t SET transaction_status='denied' WHERE t.transaction_id = New.transaction_id;
        END IF;
    END IF;
    RETURN NEW;
END;
$BODY$
    LANGUAGE plpgsql VOLATILE;

CREATE TRIGGER t_transaction
    AFTER INSERT
    ON transaction
    FOR EACH ROW
EXECUTE PROCEDURE f_t_transaction();

CREATE TABLE service
(
    service_id   SERIAL PRIMARY KEY,
    service_type VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE tariff
(
    tariff_id            SERIAL PRIMARY KEY,
    name                 VARCHAR(40) NOT NULL UNIQUE,
    description          text        NOT NULL,
    service              INTEGER,
    cost                 INTEGER     NOT NULL,
    frequency_of_payment INTEGER     NOT NULL,
    status               tariff_status_type DEFAULT 'active',
    FOREIGN KEY (service) REFERENCES service (service_id) ON DELETE CASCADE
);

CREATE TABLE user_tariffs
(
    user_tariffs_id      SERIAL PRIMARY KEY,
    user_id              INTEGER NOT NULL,
    tariff_id            INTEGER NOT NULL,
    date_of_start        DATE DEFAULT CURRENT_DATE,
    date_of_last_payment DATE    NULL,
    FOREIGN KEY (user_id) REFERENCES fp_schema.user (user_id) ON DELETE CASCADE,
    FOREIGN KEY (tariff_id) REFERENCES tariff (tariff_id) ON DELETE CASCADE
);

/*CREATE TRIGGER t_user_tariffs();*/

CREATE TABLE additional_service
(
    additional_service_id SERIAL PRIMARY KEY,
    name                  VARCHAR(40) NOT NULL UNIQUE,
    description           TEXT        NOT NULL,
    cost                  INTEGER     NOT NULL
);

CREATE TABLE connection_request
(
    connection_request_id SERIAL PRIMARY KEY,
    subscriber            INTEGER NOT NULL,
    city                  VARCHAR(25),
    address               VARCHAR(40),
    tariff                INTEGER NOT NULL,
    date_of_change        date                DEFAULT CURRENT_DATE,
    status                request_status_type DEFAULT 'in processing',
    FOREIGN KEY (subscriber) REFERENCES fp_schema.user (user_id) ON DELETE CASCADE,
    FOREIGN KEY (tariff) REFERENCES tariff (tariff_id) ON DELETE CASCADE
);

CREATE OR REPLACE FUNCTION f_t_update_request_status() RETURNS trigger
    LANGUAGE plpgsql AS

$$
DECLARE
    temp_user_balance integer;
    temp_tariff_cost  integer;
    temp_user_status  user_status_type;
BEGIN


    IF (NEW.status = 'approved') then
        SELECT INTO temp_tariff_cost cost FROM tariff t WHERE t.tariff_id = NEW.tariff;
        INSERT INTO transaction(balance_id, type, transaction_amount, transaction_date, transaction_status)
        VALUES (NEW.subscriber, 'debit', temp_tariff_cost, CURRENT_DATE, NULL);
        SELECT INTO temp_user_status user_status FROM "user" u WHERE NEW.subscriber = u.user_id;
        IF (temp_user_status = 'blocked') THEN
            INSERT INTO user_tariffs(user_id, tariff_id, date_of_start, date_of_last_payment)
            VALUES (NEW.subscriber, NEW.tariff, CURRENT_DATE, NULL);
        ELSE
            INSERT INTO user_tariffs(user_id, tariff_id, date_of_start, date_of_last_payment)
            VALUES (NEW.subscriber, NEW.tariff, CURRENT_DATE, CURRENT_DATE);
        END IF;
    end if;
    RETURN NEW;
END;
$$;

CREATE OR REPLACE TRIGGER t_con_request_change_status
    AFTER UPDATE
    ON connection_request
    FOR EACH ROW
EXECUTE FUNCTION f_t_update_request_status();

CREATE TABLE request_additional_services
(
    request_additional_services_id SERIAL PRIMARY KEY,
    request_id                     INTEGER NOT NULL,
    services_id                    INTEGER,
    FOREIGN KEY (request_id) REFERENCES connection_request (connection_request_id) ON DELETE CASCADE,
    FOREIGN KEY (services_id) REFERENCES additional_service (additional_service_id) ON DELETE CASCADE
);

CREATE OR REPLACE FUNCTION datediff(type VARCHAR, date_from DATE, date_to DATE) RETURNS INTEGER
    LANGUAGE plpgsql
AS
$$
DECLARE
    age INTERVAL;
BEGIN
    CASE type
        WHEN 'year' THEN RETURN date_part('year', date_to) - date_part('year', date_from);
        WHEN 'month' THEN age := age(date_to, date_from);
                          RETURN date_part('year', age) * 12 + date_part('month', age);
        ELSE RETURN (date_to - date_from)::int;
        END CASE;
END;
$$;

CREATE OR REPLACE FUNCTION f_check_payment(integer) RETURNS INTEGER
    LANGUAGE plpgsql AS
$$
DECLARE
    temp_tariffs              integer[];
    temp_date_of_last_payment date;
    temp_frequency_of_payment integer;
    temp_tariff_cost          integer;
    temp_id integer;
    temp_status transaction_status_type;
BEGIN
    temp_tariffs := ARRAY(
            SELECT tariff_id FROM user_tariffs ut WHERE user_id = $1
        );
    IF (array_length(temp_tariffs, 1) < 1) THEN
        RETURN 1;
    end if;
    FOR var in array_lower(temp_tariffs, 1)..array_upper(temp_tariffs, 1)
        loop
            SELECT INTO temp_frequency_of_payment frequency_of_payment
            FROM tariff t
            WHERE tariff_id = temp_tariffs[var];
            SELECT INTO temp_date_of_last_payment date_of_last_payment
            FROM user_tariffs ut WHERE user_id=$1 AND tariff_id=temp_tariffs[var];
            IF (temp_date_of_last_payment IS NULL OR datediff('day', temp_date_of_last_payment, NOW()::DATE) > temp_frequency_of_payment) THEN
                SELECT INTO temp_tariff_cost cost
                FROM tariff t
                WHERE tariff_id = temp_tariffs[var];
                INSERT INTO transaction(balance_id, type, transaction_amount, transaction_date, transaction_status)
                VALUES ($1, 'debit', temp_tariff_cost, CURRENT_DATE, NULL) RETURNING transaction_id INTO temp_id;
                SELECT INTO temp_status transaction_status FROM transaction t WHERE transaction_id=temp_id;
                IF(temp_status='successful') THEN
                    UPDATE user_tariffs ut SET date_of_last_payment=CURRENT_DATE WHERE user_id=$1 AND tariff_id=temp_tariffs[var];
                end if;
            end if;
        end loop;
    RETURN -1;
END;
$$;

INSERT INTO service (service_type)
VALUES ('IP-TV'),
       ('Internet'),
       ('Telephone');

INSERT INTO tariff (name, description, service, cost, frequency_of_payment)
VALUES ('IP-TV1', 'best ip-tv', 1, 120, 28),
       ('Super Internet', 'best internet', 2, 180, 28);


INSERT INTO fp_schema.user (email, pass, user_description, registration_date, user_role, user_balance, firstname, middle_name,
                            surname, telephone_number)
VALUES ('example@gmail.com', 12345, 1, CURRENT_DATE, DEFAULT, 500, 'Vasya', 'Ivanovich', 'Pupkin', '+380634325657'),
       ('manager@gmail.com', 12345, 1, CURRENT_DATE, 'admin', DEFAULT, 'Kiriil', 'Bubenovich', 'Karapuzin', '+380634325657'),
       ('admin@gmail.com', 12345, 2, CURRENT_DATE, 'main_admin', DEFAULT, 'Ivan', 'Kulebovich', 'Antonov', '+380764325621');

INSERT INTO additional_service (name, description, cost)
VALUES ('Podkluchenie', 'viezd i ustanovka oborudovaniya', 200),
       ('Router', 'router', 100);

/*INSERT INTO connection_request (subscriber, city, address, tariff, date_of_change)
VALUES (1, 'Odessa', 'Bocharova 45 214', 2, '2022-09-19'::DATE);*/
INSERT INTO connection_request (subscriber, city, address, tariff)
VALUES (1, 'Odessa', 'Bocharova 45 214', 2);

INSERT INTO user_tariffs(user_id, tariff_id, date_of_start, date_of_last_payment)
VALUES (1, 2, '2022-09-19'::DATE, '2022-09-19'::DATE);

INSERT INTO request_additional_services (request_id, services_id)
VALUES (1, 1),
       (1, 2);