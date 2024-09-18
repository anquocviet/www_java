CREATE TABLE customer
(
    cust_id   BIGINT AUTO_INCREMENT NOT NULL,
    cust_name VARCHAR(255) NULL,
    email     VARCHAR(255) NULL,
    phone     VARCHAR(255) NULL,
    address   VARCHAR(255) NULL,
    CONSTRAINT pk_customer PRIMARY KEY (cust_id)
);

CREATE TABLE employee
(
    emp_id    BIGINT AUTO_INCREMENT NOT NULL,
    full_name VARCHAR(255) NULL,
    dob       datetime NULL,
    email     VARCHAR(255) NULL,
    phone     VARCHAR(255) NULL,
    address   VARCHAR(255) NULL,
    status    SMALLINT NULL,
    CONSTRAINT pk_employee PRIMARY KEY (emp_id)
);

CREATE TABLE `order`
(
    order_id   BIGINT AUTO_INCREMENT NOT NULL,
    order_date datetime NULL,
    emp_id     BIGINT NULL,
    cust_id    BIGINT NULL,
    CONSTRAINT pk_order PRIMARY KEY (order_id)
);

CREATE TABLE order_detail
(
    order_id   BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    price DOUBLE NOT NULL,
    quantity DOUBLE NOT NULL,
    note       VARCHAR(255) NULL,
    CONSTRAINT pk_order_detail PRIMARY KEY (order_id, product_id)
);

CREATE TABLE product
(
    product_id        BIGINT AUTO_INCREMENT NOT NULL,
    name              VARCHAR(255) NULL,
    `description`     VARCHAR(255) NULL,
    unit              VARCHAR(255) NULL,
    manufacturer_name VARCHAR(255) NULL,
    status            SMALLINT NULL,
    CONSTRAINT pk_product PRIMARY KEY (product_id)
);

CREATE TABLE product_image
(
    image_id    BIGINT AUTO_INCREMENT NOT NULL,
    product_id  BIGINT NOT NULL,
    `path`      VARCHAR(255) NULL,
    alternative VARCHAR(255) NULL,
    CONSTRAINT pk_product_image PRIMARY KEY (image_id, product_id)
);

CREATE TABLE product_price
(
    price_date_time datetime NOT NULL,
    product_id      BIGINT NULL,
    price DOUBLE NOT NULL,
    note            VARCHAR(255) NULL,
    CONSTRAINT pk_product_price PRIMARY KEY (price_date_time)
);

ALTER TABLE order_detail
    ADD CONSTRAINT FK_ORDER_DETAIL_ON_ORDER FOREIGN KEY (order_id) REFERENCES `order` (order_id);

ALTER TABLE order_detail
    ADD CONSTRAINT FK_ORDER_DETAIL_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (product_id);

ALTER TABLE `order`
    ADD CONSTRAINT FK_ORDER_ON_CUST FOREIGN KEY (cust_id) REFERENCES customer (cust_id);

ALTER TABLE `order`
    ADD CONSTRAINT FK_ORDER_ON_EMP FOREIGN KEY (emp_id) REFERENCES employee (emp_id);

ALTER TABLE product_image
    ADD CONSTRAINT FK_PRODUCT_IMAGE_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (product_id);

ALTER TABLE product_price
    ADD CONSTRAINT FK_PRODUCT_PRICE_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (product_id);