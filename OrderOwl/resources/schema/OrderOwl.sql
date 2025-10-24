-- OrderOwl 데이터베이스 생성 및 사용
CREATE DATABASE OrderOwl;
USE OrderOwl;

-- 1. User 테이블 생성
CREATE TABLE User (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(20) NOT NULL,
    password VARCHAR(30) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    role VARCHAR(50) NOT NULL DEFAULT 'owner',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 2. Store 테이블 생성
CREATE TABLE Store (
    store_id INT PRIMARY KEY AUTO_INCREMENT,
    owner_id INT NOT NULL,
    store_name VARCHAR(50) NOT NULL,
    address VARCHAR(70),
    region VARCHAR(30),
    phone_number VARCHAR(20),
    description TEXT,
    img_src VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (owner_id) REFERENCES User(user_id) ON DELETE CASCADE
);

-- 3. StoreTable 테이블 생성
CREATE TABLE StoreTable (
    table_id INT PRIMARY KEY AUTO_INCREMENT,
    store_id INT NOT NULL,
    table_no VARCHAR(50) NOT NULL,
    FOREIGN KEY (store_id) REFERENCES Store(store_id) ON DELETE CASCADE
);

-- 4. Category1 테이블 생성
CREATE TABLE Category1 (
    category1_code INT PRIMARY KEY AUTO_INCREMENT,
    category1_name VARCHAR(20)
);

-- 5. Category2 테이블 생성
CREATE TABLE Category2 (
    category2_code INT PRIMARY KEY AUTO_INCREMENT,
    category2_name VARCHAR(20)
);

-- 6. Menu 테이블 생성
CREATE TABLE Menu (
    menu_id INT PRIMARY KEY AUTO_INCREMENT,
    store_id INT NOT NULL,
    menu_name VARCHAR(100) NOT NULL,
    price INT NOT NULL,
    description TEXT,
    img_src VARCHAR(255),
    category1_code INT,
    category2_code INT,
    check_rec VARCHAR(1) DEFAULT 'N',
    order_request VARCHAR(50) DEFAULT NULL,
    close_time TIME,
    sold_out VARCHAR(1) DEFAULT 'N',
    FOREIGN KEY (store_id) REFERENCES Store(store_id) ON DELETE CASCADE,
    FOREIGN KEY (category1_code) REFERENCES Category1(category1_code),
    FOREIGN KEY (category2_code) REFERENCES Category2(category2_code)
);

-- 7. OrderTable 테이블 생성
CREATE TABLE OrderTable (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    store_id INT NOT NULL,
    table_id INT NOT NULL,
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) NOT NULL DEFAULT 'pending' COMMENT '주문 상태 (pending, completed, cancelled)',
    total_price INT NOT NULL DEFAULT 0,
    min_price INT DEFAULT 0,
    table_status VARCHAR(1) DEFAULT 'N' COMMENT '테이블 주문 상태 (Y,N)',
    FOREIGN KEY (store_id) REFERENCES Store(store_id) ON DELETE CASCADE,
    FOREIGN KEY (table_id) REFERENCES StoreTable(table_id)
);

-- 8. OrderDetail 테이블 생성
CREATE TABLE OrderDetail (
    order_detail_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    menu_id INT NOT NULL,
    quantity INT NOT NULL,
    price INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES OrderTable(order_id) ON DELETE CASCADE,
    FOREIGN KEY (menu_id) REFERENCES Menu(menu_id)
);

-- 9. Payment 테이블 생성
CREATE TABLE Payment (
    payment_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    payment_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    amount INT NOT NULL COMMENT '결제 금액',
    status VARCHAR(50) NOT NULL COMMENT '결제 상태 (paid, unpaid, refunded)',
    FOREIGN KEY (order_id) REFERENCES OrderTable(order_id)
);

-- 10. QRCode 테이블 생성
CREATE TABLE QRCode (
    qrcode_id INT PRIMARY KEY AUTO_INCREMENT,
    table_id INT NOT NULL UNIQUE,
    qrcode_data VARCHAR(255) NOT NULL,
    qr_img_src VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (table_id) REFERENCES StoreTable(table_id) ON DELETE CASCADE
);

-- ==================== 더미 데이터 삽입 ====================

-- 1. User 테이블 더미 데이터
INSERT INTO User (username, password, email, role) VALUES
('owner1', 'password123', 'owner1@orderowl.com', 'owner'),
('owner2', 'password456', 'owner2@orderowl.com', 'owner'),
('manager1', 'password789', 'manager1@orderowl.com', 'manager'),
('staff1', 'password000', 'staff1@orderowl.com', 'staff'),
('owner3', 'password111', 'owner3@orderowl.com', 'owner');

-- 2. Store 테이블 더미 데이터
INSERT INTO Store (owner_id, store_name, address, region, phone_number, description, img_src) VALUES
(1, '맛있는 한식당', '서울시 강남구 테헤란로 123', '강남구', '02-1234-5678', '전통 한식을 현대적으로 재해석한 레스토랑', '/images/store1.jpg'),
(2, '이탈리안 파스타 하우스', '서울시 마포구 홍익로 456', '마포구', '02-2345-6789', '이탈리안 정통 파스타와 피자 전문점', '/images/store2.jpg'),
(1, '스시 마스터', '서울시 서초구 반포대로 789', '서초구', '02-3456-7890', '신선한 재료로 만드는 일식 전문점', '/images/store3.jpg'),
(3, '버거 앤 프라이즈', '서울시 종로구 종로 101', '종로구', '02-4567-8901', '수제버거와 감자튀김 전문 패스트푸드점', '/images/store4.jpg');


-- 3. StoreTable 테이블 더미 데이터
INSERT INTO StoreTable (store_id, table_no) VALUES
-- 한식당 (store_id: 1)
(1, 'A1'), (1, 'A2'), (1, 'A3'), (1, 'B1'), (1, 'B2'),
-- 이탈리안 (store_id: 2)
(2, '1번'), (2, '2번'), (2, '3번'), (2, '4번'),
-- 일식당 (store_id: 3)
(3, '카운터1'), (3, '카운터2'), (3, '룸1'), (3, '룸2'),
-- 버거샵 (store_id: 4)
(4, 'T1'), (4, 'T2'), (4, 'T3'), (4, 'T4'), (4, 'T5');

-- 4. Category1 테이블 더미 데이터 (대분류)
INSERT INTO Category1 (category1_name) VALUES
('메인요리'), ('사이드'), ('음료'), ('디저트'), ('세트메뉴');

-- 5. Category2 테이블 더미 데이터 (중분류) - 수정됨
INSERT INTO Category2 (category2_name) VALUES
('한식'), ('중식'), ('일식'), ('양식'), ('분식'),
('커피'), ('차'), ('주스'), ('스무디'),
('케이크'), ('아이스크림'), ('와플');

-- 6. Menu 테이블 더미 데이터
-- 한식당 메뉴 (store_id: 1)
INSERT INTO Menu (store_id, menu_name, price, description, img_src, category1_code, category2_code, check_rec, order_request, sold_out) VALUES
(1, '된장찌개', 8000, '집에서 먹는 듯한 정성된 된장찌개', '/images/menu1.jpg', 1, 1, 'Y', '맵기조절', 'N'),
(1, '김치찌개', 9000, '입맛 돋우는 시원한 김치찌개', '/images/menu2.jpg', 1, 1, 'Y', '맵기조절', 'N'),
(1, '불고기', 15000, '부드러운 국내산 소고기 불고기', '/images/menu3.jpg', 1, 1, 'N', NULL, 'N'),
(1, '비빔밥', 11000, '신선한 채소와 고추장이 어우러진 비빔밥', '/images/menu4.jpg', 1, 1, 'Y', NULL, 'N'),
(1, '콜라', 2000, '시원한 탄산음료', '/images/drink1.jpg', 3, 8, 'N', NULL, 'N');

-- 이탈리안 레스토랑 메뉴 (store_id: 2)
INSERT INTO Menu (store_id, menu_name, price, description, img_src, category1_code, category2_code, check_rec, order_request, sold_out) VALUES
(2, '까르보나라', 12000, '크림소스와 베이컨의 조화', '/images/menu5.jpg', 1, 4, 'Y', NULL, 'N'),
(2, '토마토 파스타', 11000, '신선한 토마토 소스 파스타', '/images/menu6.jpg', 1, 4, 'N', NULL, 'N'),
(2, '마르게리타 피자', 18000, '모짜렐라 치즈와 바질의 클래식 피자', '/images/menu7.jpg', 1, 4, 'Y', NULL, 'N'),
(2, '카프레제 샐러드', 8000, '신선한 토마토와 모짜렐라 치즈 샐러드', '/images/menu8.jpg', 2, 4, 'N', NULL, 'N'),
(2, '레몬에이드', 4000, '상큼한 레몬에이드', '/images/drink2.jpg', 3, 8, 'N', NULL, 'N');

-- 일식당 메뉴 (store_id: 3)
INSERT INTO Menu (store_id, menu_name, price, description, img_src, category1_code, category2_code, check_rec, order_request, sold_out) VALUES
(3, '연어초밥', 18000, '신선한 노르웨이 연어 초밥', '/images/menu9.jpg', 1, 3, 'Y', NULL, 'N'),
(3, '광어초밥', 22000, '싱싱한 광어 초밥', '/images/menu10.jpg', 1, 3, 'N', NULL, 'N'),
(3, '우동', 7000, '따뜻한 국물 우동', '/images/menu11.jpg', 1, 3, 'N', NULL, 'N'),
(3, '가츠동', 9000, '바삭한 돈까스 덮밥', '/images/menu12.jpg', 1, 3, 'Y', NULL, 'N'),
(3, '녹차', 3000, '향긋한 일본 녹차', '/images/drink3.jpg', 3, 7, 'N', NULL, 'N');

-- 버거샵 메뉴 (store_id: 4)
INSERT INTO Menu (store_id, menu_name, price, description, img_src, category1_code, category2_code, check_rec, order_request, sold_out) VALUES
(4, '치즈버거', 6000, '신선한 패티와 치즈의 조화', '/images/menu13.jpg', 1, 4, 'Y', NULL, 'N'),
(4, '베이컨버거', 7500, '바삭한 베이컨이 들어간 버거', '/images/menu14.jpg', 1, 4, 'N', NULL, 'N'),
(4, '감자튀김', 3000, '바삭한 감자튀김', '/images/menu15.jpg', 2, 4, 'Y', NULL, 'N'),
(4, '치킨너겟', 4000, '바삭바삭 치킨너겟', '/images/menu16.jpg', 2, 4, 'N', NULL, 'N'),
(4, '아메리카노', 3500, '신선한 원두 커피', '/images/drink4.jpg', 3, 6, 'N', NULL, 'N');

-- 7. OrderTable 테이블 더미 데이터
INSERT INTO OrderTable (store_id, table_id, total_price, min_price, table_status, status) VALUES
(1, 1, 25000, 15000, 'Y', 'pending'),
(1, 2, 18000, 15000, 'N', 'completed'),
(2, 6, 35000, 20000, 'Y', 'pending'),
(3, 11, 42000, 25000, 'Y', 'pending'),
(4, 14, 14000, 10000, 'N', 'completed');

-- 8. OrderDetail 테이블 더미 데이터
INSERT INTO OrderDetail (order_id, menu_id, quantity, price) VALUES
-- 주문 1
(1, 1, 1, 8000),
(1, 3, 1, 15000),
(1, 5, 1, 2000),
-- 주문 2
(2, 2, 1, 9000),
(2, 4, 1, 11000),
-- 주문 3
(3, 6, 2, 12000),
(3, 10, 1, 4000),
(3, 9, 1, 8000),
-- 주문 4
(4, 11, 1, 18000),
(4, 12, 1, 22000),
(4, 15, 1, 3000),
-- 주문 5
(5, 17, 2, 6000),
(5, 19, 1, 3000);

-- 9. Payment 테이블 더미 데이터
INSERT INTO Payment (order_id, amount, status) VALUES
(1, 25000, 'unpaid'),
(2, 18000, 'paid'),
(3, 35000, 'unpaid'),
(4, 42000, 'unpaid'),
(5, 14000, 'paid');

-- 10. QRCode 테이블 더미 데이터
INSERT INTO QRCode (table_id, qrcode_data, qr_img_src) VALUES
(1, 'store1_table_a1_qrcode_12345', '/qrcodes/store1_a1.png'),
(2, 'store1_table_a2_qrcode_12346', '/qrcodes/store1_a2.png'),
(3, 'store1_table_a3_qrcode_12347', '/qrcodes/store1_a3.png'),
(4, 'store1_table_b1_qrcode_12348', '/qrcodes/store1_b1.png'),
(5, 'store1_table_b2_qrcode_12349', '/qrcodes/store1_b2.png'),
(6, 'store2_table_1_qrcode_23456', '/qrcodes/store2_1.png'),
(7, 'store2_table_2_qrcode_23457', '/qrcodes/store2_2.png'),
(8, 'store2_table_3_qrcode_23458', '/qrcodes/store2_3.png'),
(9, 'store2_table_4_qrcode_23459', '/qrcodes/store2_4.png'),
(11, 'store3_table_counter1_qrcode_34567', '/qrcodes/store3_counter1.png'),
(12, 'store3_table_counter2_qrcode_34568', '/qrcodes/store3_counter2.png'),
(13, 'store3_table_room1_qrcode_34569', '/qrcodes/store3_room1.png'),
(14, 'store4_table_t1_qrcode_45678', '/qrcodes/store4_t1.png'),
(15, 'store4_table_t2_qrcode_45679', '/qrcodes/store4_t2.png');