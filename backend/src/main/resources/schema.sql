DROP TABLE IF EXISTS hotel;

CREATE TABLE hotel (
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(255)  NOT NULL,
    hotel_type   VARCHAR(20)   NOT NULL,
    room_type    VARCHAR(20)   NOT NULL,
    city         VARCHAR(100)  NOT NULL,
    state        VARCHAR(100)  NOT NULL,
    base_price   DECIMAL(10,2) NOT NULL,
    final_price  DECIMAL(10,2) NOT NULL,
    rating       DECIMAL(2,1)  NOT NULL
);

CREATE INDEX idx_city_state
    ON hotel (city, state);

CREATE INDEX idx_city_state_price
    ON hotel (city, state, final_price);

CREATE INDEX idx_city_state_rating
    ON hotel (city, state, rating);
