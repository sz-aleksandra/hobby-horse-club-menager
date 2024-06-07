DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

CREATE TABLE "clients" (
                           "client_id" serial PRIMARY KEY,
                           "username" varchar,
                           "password" varchar,
                           "name" varchar,
                           "surname" varchar,
                           "email" varchar,
                           "phone_number" varchar,
                           "address_id" integer,
                           "birth_date" timestamp,
                           "nationality" varchar,
                           "gender" varchar,
                           "is_active" bool
);

CREATE TABLE "addresses" (
                             "address_id" serial PRIMARY KEY,
                             "country" varchar,
                             "city" varchar,
                             "street" varchar,
                             "postal_code" varchar,
                             "street_no" varchar
);

CREATE TABLE "payment_methods" (
                                   "payment_method_id" serial PRIMARY KEY,
                                   "client_id" integer,
                                   "card_number" varchar,
                                   "cvv" varchar,
                                   "expiration_date" timestamp,
                                   "card_holder" varchar,
                                   "is_active" bool
);

CREATE TABLE "owners" (
                          "owner_id" serial PRIMARY KEY,
                          "username" varchar,
                          "password" varchar,
                          "company_name" varchar,
                          "email" varchar,
                          "phone_number" varchar,
                          "address_id" integer,
                          "nip" varchar,
                          "is_verified" bool,
                          "is_active" bool
);

CREATE TABLE "admins" (
                          "admin_id" serial PRIMARY KEY,
                          "username" varchar,
                          "password" varchar,
                          "name" varchar,
                          "is_active" bool
);

CREATE TABLE "hotels" (
                          "hotel_id" serial PRIMARY KEY,
                          "owner_id" integer,
                          "name" varchar,
                          "add_date" timestamp,
                          "description" varchar,
                          "address_id" integer,
                          "email" varchar,
                          "website" varchar,
                          "phone_number" varchar,
                          "bank_account_number" varchar,
                          "is_active" bool
);

CREATE TABLE "offers" (
                          "offer_id" serial PRIMARY KEY,
                          "hotel_id" integer,
                          "room_type" varchar,
                          "name" varchar,
                          "add_date" timestamp,
                          "description" varchar,
                          "bathroom_no" integer,
                          "room_no" integer,
                          "bed_no" integer,
                          "has_kitchen" bool,
                          "pet_friendly" bool,
                          "has_wifi" bool,
                          "smoking_allowed" bool,
                          "has_parking" bool,
                          "price" float,
                          "is_active" bool,
                          "image" bytea
);

CREATE TABLE "ratings" (
                           "rating_id" serial PRIMARY KEY,
                           "offer_id" integer,
                           "client_id" integer,
                           "rating" integer,
                           "comment" varchar,
                           "date" timestamp,
                           "is_hidden" bool
);

CREATE TABLE "reservations" (
                                "reservation_id" serial PRIMARY KEY,
                                "client_id" integer,
                                "offer_id" integer,
                                "start_date" timestamp,
                                "end_date" timestamp,
                                "description" varchar,
                                "paid_amount" float,
                                "status" varchar,
                                "is_paid" bool
);

CREATE TABLE "favourite_hotels" (
                                    "id" serial PRIMARY KEY,
                                    "client_id" integer,
                                    "hotel_id" integer
);

CREATE TABLE "favourite_offers" (
                                    "id" serial PRIMARY KEY,
                                    "client_id" integer,
                                    "offer_id" integer
);

CREATE TABLE "penalties" (
                             "penalty_id" serial PRIMARY KEY,
                             "reservation_id" integer,
                             "reason" varchar,
                             "amount" float,
                             "is_paid" bool
);

CREATE TABLE "discounts" (
                             "discount_id" serial PRIMARY KEY,
                             "code" varchar,
                             "value_type" integer,
                             "type" integer,
                             "description" varchar,
                             "value" float,
                             "hotel_id" integer,
                             "is_active" bool
);

ALTER TABLE offers
    ADD CONSTRAINT fk_offers_hotels FOREIGN KEY (hotel_id) REFERENCES hotels (hotel_id);

ALTER TABLE payment_methods
    ADD CONSTRAINT fk_payment_methods_clients FOREIGN KEY (client_id) REFERENCES clients (client_id);

ALTER TABLE clients
    ADD CONSTRAINT fk_clients_addresses FOREIGN KEY (address_id) REFERENCES addresses (address_id);

ALTER TABLE owners
    ADD CONSTRAINT fk_owners_addresses FOREIGN KEY (address_id) REFERENCES addresses (address_id);

ALTER TABLE hotels
    ADD CONSTRAINT fk_hotels_owners FOREIGN KEY (owner_id) REFERENCES owners (owner_id);

ALTER TABLE hotels
    ADD CONSTRAINT fk_hotels_addresses FOREIGN KEY (address_id) REFERENCES addresses (address_id);

ALTER TABLE ratings
    ADD CONSTRAINT fk_ratings_offers FOREIGN KEY (offer_id) REFERENCES offers (offer_id);

ALTER TABLE ratings
    ADD CONSTRAINT fk_ratings_clients FOREIGN KEY (client_id) REFERENCES clients (client_id);

ALTER TABLE favourite_hotels
    ADD CONSTRAINT fk_favourite_hotels_clients FOREIGN KEY (client_id) REFERENCES clients (client_id);

ALTER TABLE favourite_hotels
    ADD CONSTRAINT fk_favourite_hotels_hotels FOREIGN KEY (hotel_id) REFERENCES hotels (hotel_id);

ALTER TABLE favourite_offers
    ADD CONSTRAINT fk_favourite_offers_clients FOREIGN KEY (client_id) REFERENCES clients (client_id);

ALTER TABLE favourite_offers
    ADD CONSTRAINT fk_favourite_offers_offers FOREIGN KEY (offer_id) REFERENCES offers (offer_id);

ALTER TABLE reservations
    ADD CONSTRAINT fk_reservations_clients FOREIGN KEY (client_id) REFERENCES clients (client_id);

ALTER TABLE reservations
    ADD CONSTRAINT fk_reservations_offers FOREIGN KEY (offer_id) REFERENCES offers (offer_id);

ALTER TABLE penalties
    ADD CONSTRAINT fk_penalties_reservations FOREIGN KEY (reservation_id) REFERENCES reservations (reservation_id);

ALTER TABLE discounts
    ADD CONSTRAINT fk_discounts_hotels FOREIGN KEY (hotel_id) REFERENCES hotels (hotel_id);


insert into addresses values (0, 'No country', 'No city', 'No street', 'No postal-code', '0N');
insert into owners values (0, 'dnawiuda', 'dawda', 'dawda d', 'd adwa', 'dwa d', 0, 'nudawdadll', true, false);
insert into hotels values (0, 0, 'ALL HOTELS', localtimestamp, 'representing all hotels (for discount)', 0, 'all@gmail.com', 'www.abc.pl', '123456789', '123456' , true);


INSERT INTO addresses ("country", "city", "street", "postal_code", "street_no") VALUES
('USA', 'New York', 'Broadway St', '10001', '123'),
('Canada', 'Toronto', 'King St', 'M5V 1K4', '456'),
('UK', 'London', 'Oxford St', 'W1D 1AY', '789'),
('Australia', 'Sydney', 'George St', '2000', '101'),
('Germany', 'Berlin', 'Unter den Linden', '10117', '202'),
('France', 'Paris', 'Champs-Élysées', '75008', '303'),
('Japan', 'Tokyo', 'Shibuya', '150-0041', '404'),
('Brazil', 'Rio de Janeiro', 'Copacabana', '22050-002', '505'),
('India', 'Mumbai', 'Marine Drive', '400020', '606'),
('China', 'Beijing', 'Wangfujing', '100006', '707');

INSERT INTO clients ("username", "password", "name", "surname", "email", "phone_number", "address_id", "birth_date", "nationality", "gender", "is_active") VALUES
('username1', 'pasS123#', 'John', 'Doe', 'john.doe@email.com', '+123456789', 1, '1990-01-15', 'US', 'Male', true),
('username2', 'pasS456!', 'Jane', 'Smith', 'jane.smith@email.com', '+987654321', 2, '1985-05-20', 'CA', 'Female', true),
('username3', 'pasS789$', 'Michael', 'Johnson', 'michael.johnson@email.com', '+1122334455', 3, '1988-08-12', 'UK', 'Male', true),
('username4', 'pasSabc#', 'Emily', 'Williams', 'emily.williams@email.com', '+9988776655', 4, '1995-04-03', 'AU', 'Female', true),
('username5', 'pasSdef&', 'David', 'Brown', 'david.brown@email.com', '+5544332211', 5, '1983-11-28', 'DE', 'Male', true),
('username6', 'pasSghi#', 'Sophia', 'Miller', 'sophia.miller@email.com', '+1122334455', 1, '1992-07-09', 'FR', 'Female', true),
('username7', 'pasSjkl#', 'Ethan', 'Wilson', 'ethan.wilson@email.com', '+9988776655', 2, '1980-02-17', 'JP', 'Male', true),
('username8', 'pasSmno#', 'Olivia', 'Moore', 'olivia.moore@email.com', '+5544332211', 3, '1987-06-25', 'BR', 'Female', true),
('username9', 'pasSpqr#', 'William', 'Taylor', 'william.taylor@email.com', '+1122334455', 4, '1998-09-30', 'IN', 'Male', true),
('username10', 'paSswordqwerty#', 'Ava', 'Davis', 'ava.davis@email.com', '+9988776655', 5, '1993-12-14', 'CN', 'Female', true);

INSERT INTO payment_methods ("client_id", "card_number", "cvv", "expiration_date", "card_holder", "is_active") VALUES
(1, '1234567812345678', '123', '2025-12-01', 'John Doe', true),
(2, '9876543298765432', '456', '2024-10-01', 'Jane Smith', true),
(3, '1111222233334444', '789', '2023-05-01', 'Michael Johnson', true),
(4, '5555666677778888', 'ABC', '2024-08-01', 'Emily Williams', true),
(5, '9999888877776666', 'DEF', '2023-11-01', 'David Brown', true),
(6, '4444333322221111', 'GHI', '2025-02-01', 'Sophia Miller', true),
(7, '8888777766665555', 'JKL', '2023-07-01', 'Ethan Wilson', true),
(8, '7777666655554444', 'MNO', '2022-12-01', 'Olivia Moore', true),
(9, '2222111133334444', 'PQR', '2023-03-01', 'William Taylor', true),
(10, '6666555544443333', 'STU', '2024-06-01', 'Ava Davis', true);


INSERT INTO owners ("username", "password", "company_name", "email", "phone_number", "address_id", "nip", "is_verified", "is_active") VALUES
('hotel_owner1', 'ownerPass1!', 'ABC Hotels', 'owner1@email.com', '+1122334455', 3, '1234567890', true, true),
('hotel_owner2', 'ownerPass2!', 'XYZ Resorts', 'owner2@email.com', '+9988776655', 4, '0987654321', true, true),
('hotel_owner3', 'ownerPass3!', '123 Hospitality', 'owner3@email.com', '+5544332211', 5, '1122334455', true, true),
('hotel_owner4', 'ownerPass4#', 'Golden Resorts', 'owner4@email.com', '+123456789', 6, '9876543210', true, true),
('hotel_owner5', 'ownerPass5$', 'Sunny Hotels', 'owner5@email.com', '+9988776655', 1, '5432109876', true, true),
('hotel_owner6', 'ownerPass6%', 'Mountain Retreats', 'owner6@email.com', '+1122334455', 2, '1111222233', true, true),
('hotel_owner7', 'ownerPass7#', 'Beach Resorts Inc.', 'owner7@email.com', '+5544332211', 3, '4455667788', true, true),
('hotel_owner8', 'ownerPass8#', 'Cityscape Hotels', 'owner8@email.com', '+9988776655', 4, '7777888899', true, true),
('hotel_owner9', 'ownerPass9#', 'Luxe Retreats', 'owner9@email.com', '+123456789', 5, '1212121212', true, true),
('hotel_owner10', 'ownerPass10#', 'Global Suites', 'owner10@email.com', '+9988776655', 6, '9090909090', true, true);

INSERT INTO admins ("username", "password", "name", "is_active") VALUES
('adminname1', 'adminPass1!', 'Admin One', true),
('adminname2', 'adminPass2#', 'Admin Two', true),
('adminname3', 'adminPass3!', 'Admin Three', true),
('adminname4', 'adminPass4@', 'Admin Four', true),
('adminname5', 'adminPass5#', 'Admin Five', true),
('adminname6', 'adminPass6#', 'Admin Six', true),
('adminname7', 'adminPass7#', 'Admin Seven', true),
('adminname8', 'adminPass8#', 'Admin Eight', true),
('adminname9', 'adminPass9$', 'Admin Nine', true),
('adminname10', 'adminPass10#', 'Admin Ten', true);

INSERT INTO hotels ("owner_id", "name", "add_date", "description", "address_id", "email", "website", "phone_number", "bank_account_number", "is_active") VALUES
(1, 'Grand Hotel', '2023-01-01', 'Luxurious hotel in the heart of the city', 5, 'grandhotel@email.com', 'www.grandhotel.com', '+1122334455', '9876543210', true),
(2, 'Sunset Resorts', '2023-02-01', 'Seaside resort with breathtaking views', 6, 'sunsetresorts@email.com', 'www.sunsetresorts.com', '+9988776655', '1234567890', true),
(3, 'Cityscape Inn', '2023-03-01', 'Urban retreat with modern amenities', 1, 'cityscape@email.com', 'www.cityscapeinn.com', '+5544332211', '1111222233', true),
(4, 'Golden Sands Hotel', '2023-04-01', 'Beachfront paradise with golden sands', 2, 'goldensands@email.com', 'www.goldensandshotel.com', '+123456789', '9876543210', true),
(5, 'Sunny Lodge', '2023-05-01', 'Tranquil getaway surrounded by nature', 3, 'sunnylodge@email.com', 'www.sunnylodge.com', '+9988776655', '5432109876', true),
(6, 'Mountain Retreats', '2023-06-01', 'Scenic mountain resort for outdoor enthusiasts', 4, 'mountainretreats@email.com', 'www.mountainretreats.com', '+1122334455', '1122334455', true),
(7, 'Beach Resorts Inc.', '2023-07-01', 'All-inclusive beachfront resort for ultimate relaxation', 5, 'beachresorts@email.com', 'www.beachresorts.com', '+5544332211', '4455667788', true),
(8, 'Cityscape Hotels', '2023-08-01', 'Chic urban hotel in the city center', 6, 'cityscapehotels@email.com', 'www.cityscapehotels.com', '+9988776655', '7777888899', true),
(9, 'Luxe Retreats', '2023-09-01', 'Elegant retreat with spa and fine dining', 1, 'luxeretreats@email.com', 'www.luxeretreats.com', '+123456789', '1212121212', true),
(10, 'Global Suites', '2023-10-01', 'International chain of modern and comfortable suites', 2, 'globalsuites@email.com', 'www.globalsuites.com', '+9988776655', '9090909090', true);

INSERT INTO offers ("hotel_id", "room_type", "name", "add_date", "description", "bathroom_no", "room_no", "bed_no", "has_kitchen", "pet_friendly", "has_wifi", "smoking_allowed", "has_parking", "price", "is_active") VALUES
(1, 'Suite', 'Luxury Suite', '2023-01-05', 'Spacious suite with city view', 2, 1, 1, true, false, true, false, true, 300.00, true),
(1, 'Double', 'Executive Double Room', '2023-02-10', 'Executive room with modern amenities', 1, 2, 2, false, true, false, true, true, 250.00, true),
(2, 'Standard', 'Ocean View Standard Room', '2023-03-15', 'Standard room with a view of the ocean', 2, 2, 2, false, false, false, false, false, 180.00, true),
(2, 'Suite', 'Sunset Suite', '2023-04-20', 'Suite with a breathtaking view of the sunset', 1, 2, 1, true, false, false, false, true, 320.00, true),
(3, 'Deluxe', 'Cityscape Deluxe Room', '2023-05-25', 'Deluxe room with panoramic city views', 2, 2, 2, true, false, false, false, true, 220.00, true),
(3, 'Standard', 'Cityscape Standard Room', '2023-06-30', 'Standard room with city view', 1, 2, 2, false, true, true, true, true, 200.00, true),
(4, 'Suite', 'Golden Sands Suite', '2023-07-05', 'Spacious suite with views of the golden sands', 2, 3, 1, true, true, true, true, true, 350.00, true),
(4, 'Double', 'Beachfront Double Room', '2023-08-10', 'Double room with direct access to the beach', 1, 3, 2, false, false, true, false, true, 280.00, true),
(5, 'Cabin', 'Sunny Cabin Retreat', '2023-09-15', 'Cozy cabin with a rustic charm', 1, 1, 1, false, true, true, false, true, 150.00, true),
(5, 'Suite', 'Nature Suite', '2023-10-20', 'Suite with views of the surrounding nature', 2, 5, 1, true, false, true, true, true, 280.00, true);

INSERT INTO ratings ("offer_id", "client_id", "rating", "comment", "date", "is_hidden") VALUES
(1, 1, 5, 'Excellent stay!', '2023-01-15', false),
(2, 2, 4, 'Beautiful view, but the room was a bit small', '2023-02-20', false),
(3, 3, 5, 'Perfect city views from the room', '2023-03-25', false),
(4, 4, 3, 'Nice suite, but service could be improved', '2023-04-30', false),
(5, 5, 4, 'Great experience in the cabin retreat', '2023-05-05', false);

INSERT INTO reservations ("client_id", "offer_id", "start_date", "end_date", "description", "paid_amount", "status", "is_paid") VALUES
(1, 1, '2023-01-20', '2023-01-25', 'Late check-in requested', 250.00, 'Confirmed', true),
(2, 2, '2023-02-25', '2023-03-05', 'Vegetarian meal preferences', 200.00, 'Confirmed', true),
(3, 3, '2023-03-30', '2023-04-05', 'Airport transfer requested', 220.00, 'Confirmed', true),
(4, 4, '2023-05-01', '2023-05-10', 'Early check-in and late check-out requested', 320.00, 'Confirmed', true),
(5, 5, '2023-06-10', '2023-06-15', 'Pet-friendly room requested', 180.00, 'Confirmed', true);

INSERT INTO penalties ("reservation_id", "reason", "amount", "is_paid") VALUES
(1, 'Late check-out without prior notice', 50.00, false),
(2, 'Cancellation less than 48 hours before check-in', 100.00, false);

INSERT INTO favourite_hotels ("client_id", "hotel_id") VALUES
(1, 1),
(1, 3),
(2, 2),
(3, 5),
(4, 4),
(5, 2),
(6, 1),
(7, 4),
(8, 3),
(9, 5);

INSERT INTO favourite_offers ("client_id", "offer_id") VALUES
(1, 1),
(2, 3),
(3, 5),
(4, 7),
(5, 10),
(6, 2),
(7, 6),
(8, 8),
(9, 9),
(10, 4);

INSERT INTO discounts ("code", "value_type", "type", "description", "value", "hotel_id", "is_active") VALUES
('SUMMER25', 1, 1, 'Summer Promotion', 25.00, 1, true),
('WEEKEND10', 1, 2, 'Weekend Special', 10.00, 2, true),
('LOYALTY15', 2, 1, 'Loyalty Discount', 15.00, 3, true);
