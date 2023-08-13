
CREATE SEQUENCE cars_seq START 1;

DROP TABLE IF EXISTS cars CASCADE ;
CREATE TABLE cars (
                      id       serial PRIMARY KEY,
                      make     varchar,
                      year     integer,
                      model    varchar
);
DROP TABLE IF EXISTS category CASCADE ;
CREATE TABLE category (
                          id   serial PRIMARY KEY,
                          name varchar
);
DROP TABLE IF EXISTS car_category CASCADE ;
CREATE TABLE car_category (
                              car_id     int REFERENCES cars(id) ON DELETE CASCADE ON UPDATE CASCADE,
                              category_id int REFERENCES category(id) ON DELETE CASCADE ON UPDATE CASCADE,
                              PRIMARY KEY (car_id, category_id)
);
