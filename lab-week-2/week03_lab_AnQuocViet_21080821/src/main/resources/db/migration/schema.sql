CREATE TABLE customers
(
    cust_id   BIGINT AUTO_INCREMENT NOT NULL,
    cust_name VARCHAR(255)          NULL,
    email     VARCHAR(255)          NULL,
    phone     VARCHAR(255)          NULL,
    address   VARCHAR(255)          NULL,
    CONSTRAINT pk_customers PRIMARY KEY (cust_id)
);

CREATE TABLE employees
(
    emp_id    BIGINT AUTO_INCREMENT NOT NULL,
    full_name VARCHAR(255)          NULL,
    dob       datetime              NULL,
    email     VARCHAR(255)          NULL,
    phone     VARCHAR(255)          NULL,
    address   VARCHAR(255)          NULL,
    status    SMALLINT              NULL,
    CONSTRAINT pk_employees PRIMARY KEY (emp_id)
);

CREATE TABLE order_details
(
    order_id   BIGINT       NOT NULL,
    product_id BIGINT       NOT NULL,
    price      DOUBLE       NOT NULL,
    quantity   DOUBLE       NOT NULL,
    note       VARCHAR(255) NULL,
    CONSTRAINT pk_order_details PRIMARY KEY (order_id, product_id)
);

CREATE TABLE orders
(
    order_id   BIGINT AUTO_INCREMENT NOT NULL,
    order_date datetime              NULL,
    emp_id     BIGINT                NULL,
    cust_id    BIGINT                NULL,
    CONSTRAINT pk_orders PRIMARY KEY (order_id)
);

CREATE TABLE product_images
(
    image_id    BIGINT AUTO_INCREMENT NOT NULL,
    product_id  BIGINT                NOT NULL,
    `path`      VARCHAR(255)          NULL,
    alternative VARCHAR(255)          NULL,
    CONSTRAINT pk_product_images PRIMARY KEY (image_id, product_id)
);

CREATE TABLE product_prices
(
    price_date_time datetime     NOT NULL,
    product_id      BIGINT       NOT NULL,
    price           DOUBLE       NOT NULL,
    note            VARCHAR(255) NULL,
    CONSTRAINT pk_product_prices PRIMARY KEY (price_date_time, product_id)
);

CREATE TABLE products
(
    product_id        BIGINT AUTO_INCREMENT NOT NULL,
    name              VARCHAR(255)          NULL,
    `description`     VARCHAR(255)          NULL,
    unit              VARCHAR(255)          NULL,
    manufacturer_name VARCHAR(255)          NULL,
    status            SMALLINT              NULL,
    CONSTRAINT pk_products PRIMARY KEY (product_id)
);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_CUST FOREIGN KEY (cust_id) REFERENCES customers (cust_id);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_EMP FOREIGN KEY (emp_id) REFERENCES employees (emp_id);

ALTER TABLE order_details
    ADD CONSTRAINT FK_ORDER_DETAILS_ON_ORDER FOREIGN KEY (order_id) REFERENCES orders (order_id);

ALTER TABLE order_details
    ADD CONSTRAINT FK_ORDER_DETAILS_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES products (product_id);

ALTER TABLE product_images
    ADD CONSTRAINT FK_PRODUCT_IMAGES_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES products (product_id);

ALTER TABLE product_prices
    ADD CONSTRAINT FK_PRODUCT_PRICES_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES products (product_id);