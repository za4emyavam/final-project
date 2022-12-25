DROP FUNCTION if exists f_check_payment();
DROP FUNCTION if exists f_check_payment_by_user_id(integer);
DROP TABLE if exists checks;
DROP TABLE if exists connection_request;
DROP TABLE if exists user_tariffs;
DROP TABLE if exists tariff;
DROP TABLE if exists transaction;
DROP TABLE if exists service;
DROP TABLE if exists "user";
DROP TYPE if exists role_type;
DROP TYPE if exists user_status_type;
DROP TYPE if exists request_status_type;
DROP TYPE if exists transaction_type;
DROP TYPE if exists transaction_status_type;

CREATE TYPE role_type AS ENUM ('user', 'admin', 'main_admin');
CREATE TYPE user_status_type AS ENUM ('subscribed', 'blocked');
CREATE TYPE request_status_type AS ENUM ('in processing', 'rejected', 'approved');
CREATE TYPE transaction_type AS ENUM ('debit', 'refill');
CREATE TYPE transaction_status_type AS ENUM ('successful', 'denied');

CREATE TABLE "user"
(
    user_id           SERIAL PRIMARY KEY,
    email             varchar(320) NOT NULL UNIQUE,
    pass              varchar(64)  NOT NULL,
    registration_date date         NOT NULL DEFAULT (CURRENT_DATE),
    user_role         role_type             DEFAULT ('user'),
    user_status       user_status_type      DEFAULT ('subscribed'),
    user_balance      DECIMAL(8, 2)         DEFAULT 0 CHECK ( user_balance >= 0 ),
    firstname         varchar(30)  NOT NULL,
    middle_name       varchar(30)  NOT NULL,
    surname           varchar(30)  NOT NULL,
    telephone_number  varchar(30)  NOT NULL,
    CONSTRAINT proper_email CHECK ( email ~* '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$' ),
    CONSTRAINT proper_telephone_number CHECK (telephone_number ~ '^\+38[0-9\-\+]{9,15}$' )
);

CREATE TABLE transaction
(
    transaction_id     SERIAL PRIMARY KEY,
    balance_id         INTEGER,
    type               transaction_type,
    transaction_amount DECIMAL(8, 2),
    transaction_date   DATE DEFAULT CURRENT_DATE,
    transaction_status transaction_status_type NULL,
    FOREIGN KEY (balance_id) REFERENCES "user" (user_id) ON DELETE CASCADE
);

CREATE OR REPLACE FUNCTION add(DECIMAL, DECIMAL) RETURNS DECIMAL
AS
'select $1 + $2;'
    LANGUAGE SQL
    IMMUTABLE
    RETURNS NULL ON NULL INPUT;

CREATE OR REPLACE FUNCTION subtract(DECIMAL, DECIMAL) RETURNS DECIMAL
AS
'select $1 - $2;'
    LANGUAGE SQL
    IMMUTABLE
    RETURNS NULL ON NULL INPUT;

CREATE OR REPLACE FUNCTION f_t_transaction()
    RETURNS trigger AS
$BODY$
DECLARE
    temp_user_balance DECIMAL;
BEGIN
    SELECT INTO temp_user_balance user_balance FROM "user" WHERE NEW.balance_id = user_id;
    IF (NEW.type = 'refill') THEN
        UPDATE "user" u
        SET user_balance=add(temp_user_balance, NEW.transaction_amount)
        WHERE NEW.balance_id = user_id;
        UPDATE transaction t SET transaction_status='successful' WHERE t.transaction_id = New.transaction_id;
    ELSE
        IF (temp_user_balance >= NEW.transaction_amount) THEN
            UPDATE "user" u
            SET user_balance=subtract(user_balance, NEW.transaction_amount)
            WHERE NEW.balance_id = user_id;
            UPDATE transaction t SET transaction_status='successful' WHERE t.transaction_id = New.transaction_id;
            UPDATE "user" u SET user_status='subscribed' WHERE u.user_id = NEW.balance_id;
        ELSE
            UPDATE "user" u SET user_status='blocked' WHERE u.user_id = NEW.balance_id;
            UPDATE transaction t SET transaction_status='denied' WHERE t.transaction_id = New.transaction_id;
        END IF;
    END IF;
    RETURN NEW;
END
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
    name                 VARCHAR(60) array[2] NOT NULL UNIQUE,
    description          text array[2]        NOT NULL UNIQUE,
    service              INTEGER,
    cost                 DECIMAL(8, 2)        NOT NULL,
    frequency_of_payment INTEGER              NOT NULL,
    FOREIGN KEY (service) REFERENCES service (service_id) ON DELETE CASCADE
);

CREATE TABLE user_tariffs
(
    user_tariffs_id      SERIAL PRIMARY KEY,
    user_id              INTEGER NOT NULL,
    tariff_id            INTEGER NOT NULL,
    date_of_start        timestamp DEFAULT now(),
    date_of_last_payment timestamp    NULL,
    FOREIGN KEY (user_id) REFERENCES "user" (user_id) ON DELETE CASCADE,
    FOREIGN KEY (tariff_id) REFERENCES tariff (tariff_id) ON DELETE CASCADE
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
    FOREIGN KEY (subscriber) REFERENCES "user" (user_id) ON DELETE CASCADE,
    FOREIGN KEY (tariff) REFERENCES tariff (tariff_id) ON DELETE CASCADE
);

CREATE OR REPLACE FUNCTION f_t_update_request_status() RETURNS trigger
    LANGUAGE plpgsql AS

$$
DECLARE
    temp_tariff_cost DECIMAL;
    temp_user_status user_status_type;
BEGIN


    IF (NEW.status = 'approved') then
        SELECT INTO temp_tariff_cost cost FROM tariff t WHERE t.tariff_id = NEW.tariff;
        INSERT INTO transaction(balance_id, type, transaction_amount, transaction_date, transaction_status)
        VALUES (NEW.subscriber, 'debit', temp_tariff_cost, now(), NULL);
        SELECT INTO temp_user_status user_status FROM "user" u WHERE NEW.subscriber = u.user_id;
        IF (temp_user_status = 'blocked') THEN
            INSERT INTO user_tariffs(user_id, tariff_id, date_of_start, date_of_last_payment)
            VALUES (NEW.subscriber, NEW.tariff, now(), NULL);
        ELSE
            INSERT INTO user_tariffs(user_id, tariff_id, date_of_start, date_of_last_payment)
            VALUES (NEW.subscriber, NEW.tariff, now(), now());
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

CREATE TABLE checks (
                        check_id SERIAL PRIMARY KEY,
                        checker_id integer,
                        users integer,
                        amount decimal,
                        date_of_check timestamp,
                        FOREIGN KEY (checker_id) REFERENCES "user" (user_id) ON DELETE CASCADE
);

CREATE OR REPLACE FUNCTION f_check_payment_by_user_id(integer) RETURNS DECIMAL
    LANGUAGE plpgsql AS
$$
DECLARE
    temp_tariffs              integer[];
    temp_date_of_last_payment timestamp;
    temp_frequency_of_payment integer;
    temp_tariff_cost          DECIMAL;
    temp_id                   integer;
    temp_status               transaction_status_type;
    temp_sum                  DECIMAL;
BEGIN
    temp_sum = 0;
    temp_tariffs := ARRAY(
            SELECT tariff_id FROM user_tariffs ut WHERE user_id = $1
        );
    IF (array_length(temp_tariffs, 1) < 1) THEN
        RETURN temp_sum;
    end if;
    FOR var in array_lower(temp_tariffs, 1)..array_upper(temp_tariffs, 1)
        loop
            SELECT INTO temp_frequency_of_payment frequency_of_payment
            FROM tariff t
            WHERE tariff_id = temp_tariffs[var];
            SELECT INTO temp_date_of_last_payment date_of_last_payment
            FROM user_tariffs ut
            WHERE user_id = $1
              AND tariff_id = temp_tariffs[var];
            IF (temp_date_of_last_payment IS NULL OR
                datediff('day', temp_date_of_last_payment::DATE, NOW()::DATE) > temp_frequency_of_payment) THEN
                SELECT INTO temp_tariff_cost cost
                FROM tariff t
                WHERE tariff_id = temp_tariffs[var];
                INSERT INTO transaction(balance_id, type, transaction_amount, transaction_date, transaction_status)
                VALUES ($1, 'debit', temp_tariff_cost, now(), NULL)
                RETURNING transaction_id INTO temp_id;
                SELECT INTO temp_status transaction_status FROM transaction t WHERE transaction_id = temp_id;
                IF (temp_status = 'successful') THEN
                    UPDATE user_tariffs ut
                    SET date_of_last_payment=now()
                    WHERE user_id = $1
                      AND tariff_id = temp_tariffs[var];
                    temp_sum = temp_sum + temp_tariff_cost;
                end if;
            end if;
        end loop;
    RETURN temp_sum;
END;
$$;

CREATE OR REPLACE FUNCTION f_check_payment(integer)
    RETURNS TABLE
            (
                num_of_users  integer,
                amount_of_all decimal
            )
    LANGUAGE plpgsql
AS
$$
DECLARE
    temp_users_id integer[];
    temp_amount   decimal;
    num_of_users  integer;
    amount_of_all decimal;
BEGIN
    num_of_users = 0;
    amount_of_all = 0;
    temp_users_id := ARRAY(
            SELECT DISTINCT user_id FROM user_tariffs
        );
    FOR var in array_lower(temp_users_id, 1)..array_upper(temp_users_id, 1)
        loop
            SELECT INTO temp_amount f_check_payment_by_user_id(temp_users_id[var]);
            IF (temp_amount > 0) THEN
                num_of_users = num_of_users + 1;
                amount_of_all = amount_of_all + temp_amount;
            end if;
        end loop;
    INSERT INTO checks VALUES (DEFAULT, $1, num_of_users, amount_of_all, NOW());
    RETURN QUERY SELECT num_of_users, amount_of_all;
END;
$$;

INSERT INTO service (service_type)
VALUES ('IP-TV'),
       ('Internet'),
       ('Telephone');

INSERT INTO tariff (name, description, service, cost, frequency_of_payment)
VALUES (ARRAY ['Простий IP-TV', 'Basic IP-TV'], ARRAY ['звичайний ip-tv', 'default ip-tv'], 1, 120, 28),
       (ARRAY ['IP-TV Плюс', 'IP-TV Plus'], ARRAY ['ip-tv плюс', 'ip-tv plus'], 1, 150, 28),
       (ARRAY ['Простий Internet', 'Basic Internet'], ARRAY ['iнтернет', 'internet'], 2, 150, 28),
       (ARRAY ['Супер Internet','Super Internet'], ARRAY ['кращий iнтернет', 'best internet'], 2, 180, 28),
       (ARRAY ['Простий Telephone', 'Basic Telephone'], ARRAY ['простий телефон','basic telephone'], 3, 50, 28),
       (ARRAY ['Супер Телефон','Super Telephone'], ARRAY ['супер телефон','super telephone'], 3, 80, 28);


INSERT INTO "user" (email, pass, registration_date, user_role, user_status, user_balance, firstname,
                    middle_name,
                    surname, telephone_number)
VALUES ('example@gmail.com', 'WZRHGrsBESr8wYFZ9sx0tPURuZgG2lmzyvWpwXPKz8U=', CURRENT_DATE, DEFAULT, DEFAULT, 500,
        'Vasya', 'Ivanovich', 'Pupkin',
        '+380634325657'),
       ('manager@gmail.com', 'WZRHGrsBESr8wYFZ9sx0tPURuZgG2lmzyvWpwXPKz8U=', CURRENT_DATE, 'admin', DEFAULT, DEFAULT,
        'Kiriil', 'Bubenovich', 'Karapuzin',
        '+380634325657'),
       ('admin@gmail.com', 'WZRHGrsBESr8wYFZ9sx0tPURuZgG2lmzyvWpwXPKz8U=', CURRENT_DATE, 'main_admin', DEFAULT, DEFAULT,
        'Ivan', 'Kulebovich', 'Antonov',
        '+380764325621'),
       ('example2@gmail.com', 'WZRHGrsBESr8wYFZ9sx0tPURuZgG2lmzyvWpwXPKz8U=', CURRENT_DATE, DEFAULT, DEFAULT, DEFAULT,
        'Danya', 'Ivanovich', 'Pupkin',
        '+380634978657'),
       ('example3@gmail.com', 'WZRHGrsBESr8wYFZ9sx0tPURuZgG2lmzyvWpwXPKz8U=', CURRENT_DATE, DEFAULT, DEFAULT, DEFAULT,
        'Maxim', 'Ivanovich', 'Pupkin',
        '+380634343657'),
       ('example4@gmail.com', 'WZRHGrsBESr8wYFZ9sx0tPURuZgG2lmzyvWpwXPKz8U=', CURRENT_DATE, DEFAULT, DEFAULT, DEFAULT,
        'John', 'Ivanovich', 'Pupkin',
        '+380634325617'),
       ('example5@gmail.com', 'WZRHGrsBESr8wYFZ9sx0tPURuZgG2lmzyvWpwXPKz8U=', CURRENT_DATE, DEFAULT, DEFAULT, DEFAULT,
        'John', 'Ivanovich', 'Pupkin',
        '+380634325627');

INSERT INTO connection_request (subscriber, city, address, tariff)
VALUES (1, 'Odessa', 'Bocharova 45 214', 2);

INSERT INTO user_tariffs(user_id, tariff_id, date_of_start, date_of_last_payment)
VALUES (1, 2, '2022-09-19'::DATE, '2022-09-19'::DATE);