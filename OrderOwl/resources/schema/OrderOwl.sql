-- 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS orderowl;
USE orderowl;

-- User 테이블
CREATE TABLE IF NOT EXISTS User (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('CUSTOMER', 'OWNER', 'ADMIN') DEFAULT 'CUSTOMER',
    status ENUM('ACTIVE', 'INACTIVE', 'FORCE_DELETED') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Store 테이블
CREATE TABLE IF NOT EXISTS Store (
    store_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    owner_id BIGINT NOT NULL,
    store_name VARCHAR(255) NOT NULL,
    business_number VARCHAR(20) UNIQUE NOT NULL,
    address TEXT,
    phone_number VARCHAR(20),
    region VARCHAR(100),
    description TEXT,
    img_src VARCHAR(500),
    qr_path VARCHAR(500),
    status ENUM('PENDING', 'ACTIVE', 'INACTIVE', 'DELETE_PENDING') DEFAULT 'PENDING',
    business_verified BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (owner_id) REFERENCES User(user_id) ON DELETE CASCADE
);

-- StoreRequest 테이블 (매장 등록/수정 요청)
CREATE TABLE IF NOT EXISTS StoreRequest (
    request_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    store_id BIGINT NULL, -- 수정 요청인 경우만
    owner_id BIGINT NOT NULL,
    store_name VARCHAR(255) NOT NULL,
    business_number VARCHAR(20) NOT NULL,
    address TEXT,
    phone_number VARCHAR(20),
    request_type ENUM('ADD', 'UPDATE') DEFAULT 'ADD',
    status ENUM('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (owner_id) REFERENCES User(user_id) ON DELETE CASCADE,
    FOREIGN KEY (store_id) REFERENCES Store(store_id) ON DELETE CASCADE
);

-- Menu 테이블
CREATE TABLE IF NOT EXISTS Menu (
    menu_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    store_id BIGINT NOT NULL,
    menu_name VARCHAR(255) NOT NULL,
    price INT NOT NULL,
    category VARCHAR(100),
    description TEXT,
    img_src VARCHAR(500),
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (store_id) REFERENCES Store(store_id) ON DELETE CASCADE
);

-- MenuRequest 테이블 (메뉴 등록/수정/삭제 요청)
CREATE TABLE IF NOT EXISTS MenuRequest (
    request_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    menu_id BIGINT NULL, -- 삭제/수정 요청인 경우
    store_id BIGINT NOT NULL,
    owner_id BIGINT NOT NULL,
    request_type ENUM('ADD', 'UPDATE', 'DELETE') DEFAULT 'ADD',
    menu_name VARCHAR(255),
    price INT,
    category VARCHAR(100),
    description TEXT,
    status ENUM('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (menu_id) REFERENCES Menu(menu_id) ON DELETE CASCADE,
    FOREIGN KEY (store_id) REFERENCES Store(store_id) ON DELETE CASCADE,
    FOREIGN KEY (owner_id) REFERENCES User(user_id) ON DELETE CASCADE
);

-- OrderTable 테이블
CREATE TABLE IF NOT EXISTS OrderTable (
    order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    store_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    total_amount INT NOT NULL,
    status ENUM('PENDING', 'CONFIRMED', 'PREPARING', 'READY', 'COMPLETED', 'CANCELLED') DEFAULT 'PENDING',
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (store_id) REFERENCES Store(store_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE CASCADE
);

-- OrderDetail 테이블
CREATE TABLE IF NOT EXISTS OrderDetail (
    order_detail_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    menu_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES OrderTable(order_id) ON DELETE CASCADE,
    FOREIGN KEY (menu_id) REFERENCES Menu(menu_id) ON DELETE CASCADE
);

-- Payment 테이블
CREATE TABLE IF NOT EXISTS Payment (
    payment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    store_id BIGINT NOT NULL,
    total_amount INT NOT NULL,
    payment_method VARCHAR(50),
    payment_status ENUM('PENDING', 'COMPLETED', 'FAILED', 'REFUNDED') DEFAULT 'PENDING',
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES OrderTable(order_id) ON DELETE CASCADE,
    FOREIGN KEY (store_id) REFERENCES Store(store_id) ON DELETE CASCADE
);

-- ForceDeleteLog 테이블
CREATE TABLE IF NOT EXISTS ForceDeleteLog (
    log_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    reason TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE CASCADE
);

-- 샘플 데이터 삽입
INSERT INTO User (name, email, password, role, status) VALUES
('관리자', 'admin@orderowl.com', 'admin123', 'ADMIN', 'ACTIVE'),
('김사장', 'owner1@orderowl.com', 'owner123', 'OWNER', 'ACTIVE'),
('이사장', 'owner2@orderowl.com', 'owner123', 'OWNER', 'ACTIVE'),
('홍길동', 'customer1@orderowl.com', 'customer123', 'CUSTOMER', 'ACTIVE');

INSERT INTO Store (owner_id, store_name, business_number, address, phone_number, status, business_verified) VALUES
(2, '맛있는 김밥', '123-45-67890', '서울시 강남구 테헤란로 123', '02-1234-5678', 'ACTIVE', TRUE),
(3, '착한 떡볶이', '234-56-78901', '서울시 서초구 반포대로 456', '02-2345-6789', 'ACTIVE', TRUE);

INSERT INTO StoreRequest (owner_id, store_name, business_number, address, phone_number, request_type, status) VALUES
(2, '새로운 매장', '345-67-89012', '서울시 송파구 올림픽로 789', '02-3456-7890', 'ADD', 'PENDING'),
(3, '두번째 매장', '456-78-90123', '서울시 영등포구 여의대로 101', '02-4567-8901', 'ADD', 'PENDING');

INSERT INTO Menu (store_id, menu_name, price, category, description) VALUES
(1, '김밥', 3000, '분식', '맛있는 기본 김밥'),
(1, '떡볶이', 5000, '분식', '매콤한 떡볶이'),
(2, '치즈떡볶이', 7000, '분식', '치즈가 듬뿍 들어간 떡볶이'),
(2, '순대', 4000, '분식', '쫄깃한 순대');

INSERT INTO MenuRequest (store_id, owner_id, request_type, menu_name, price, category, status) VALUES
(1, 2, 'ADD', '라면', 4000, '분식', 'PENDING'),
(2, 3, 'ADD', '튀김', 3000, '분식', 'PENDING');

-- 샘플 데이터 추가삽입

INSERT INTO User (name, email, password, role, status) VALUES
('박사장', 'owner4@orderowl.com', 'owner123', 'OWNER', 'ACTIVE'),
('최사장', 'owner5@orderowl.com', 'owner123', 'OWNER', 'ACTIVE'),
('윤사장', 'owner6@orderowl.com', 'owner123', 'OWNER', 'ACTIVE'),
('조고객', 'customer2@orderowl.com', 'customer123', 'CUSTOMER', 'ACTIVE'),
('강고객', 'customer3@orderowl.com', 'customer123', 'CUSTOMER', 'ACTIVE'),
('신고객', 'customer4@orderowl.com', 'customer123', 'CUSTOMER', 'ACTIVE');

INSERT INTO Store (owner_id, store_name, business_number, address, phone_number, region, status, business_verified) VALUES
(5, '청담스테이크하우스', '345-12-67890', '서울시 강남구 도산대로 211', '02-3456-1111', '서울', 'ACTIVE', TRUE),
(6, '한남파스타', '456-23-78901', '서울시 용산구 한남대로 77', '02-4567-2222', '서울', 'ACTIVE', TRUE),
(7, '부산오뎅마을', '567-34-89012', '부산시 남구 대연로 25', '051-567-3333', '부산', 'ACTIVE', TRUE);


INSERT INTO StoreRequest (owner_id, store_name, business_number, address, phone_number, request_type, status) VALUES
(5, '홍대커피로스터스', '567-12-34567', '서울시 마포구 와우산로 56', '02-5678-4321', 'ADD', 'PENDING'),
(6, '건대버거하우스', '678-23-45678', '서울시 광진구 능동로 12', '02-6789-5432', 'ADD', 'PENDING'),
(7, '부산해물찜', '789-34-56789', '부산시 해운대구 달맞이길 33', '051-789-6543', 'ADD', 'PENDING'),
(8, '광주삼겹살타운', '678-45-90123', '광주시 북구 하서로 88', '062-678-4444', 'ADD', 'PENDING'),
(9, '대전칼국수', '789-56-01234', '대전시 서구 둔산대로 155', '042-789-5555', 'ADD', 'PENDING'),
(8, '전주콩나물국밥', '890-45-67890', '전주시 덕진구 백제대로 88', '063-890-7654', 'ADD', 'PENDING'),
(9, '제주고기국수', '901-56-78901', '제주시 구좌읍일주동로 101', '064-901-8765', 'ADD', 'PENDING');

INSERT INTO Menu (store_id, menu_name, price, category, description) VALUES
(3, '스테이크 세트', 25000, '양식', '부드러운 안심 스테이크와 샐러드 세트'),
(3, '감자튀김', 5000, '사이드', '바삭한 감자튀김'),
(4, '까르보나라', 12000, '파스타', '진한 크림 소스의 파스타'),
(4, '토마토 파스타', 11000, '파스타', '신선한 토마토 소스 파스타'),
(5, '부산오뎅탕', 8000, '국물요리', '따뜻하고 시원한 오뎅탕'),
(5, '오뎅꼬치', 2000, '사이드', '간편한 꼬치 오뎅'),
(6, '삼겹살 1인분', 15000, '고기', '국내산 삼겹살'),
(6, '된장찌개', 6000, '식사류', '진한 된장 맛');


INSERT INTO MenuRequest (store_id, owner_id, request_type, menu_name, price, category, description, status) VALUES
(3, 5, 'ADD', '스테이크샐러드', 13000, '샐러드', '신선한 채소와 스테이크 조합', 'PENDING'),
(4, 6, 'ADD', '알리오올리오', 11000, '파스타', '마늘 향이 가득한 오일 파스타', 'PENDING'),
(5, 7, 'ADD', '오뎅볶음', 7000, '사이드', '매콤달콤한 부산식 오뎅볶음', 'PENDING'),
(6, 8, 'ADD', '제육덮밥', 9000, '식사류', '매콤한 제육덮밥', 'PENDING'),
(7, 9, 'ADD', '칼국수', 8000, '식사류', '시원한 멸치육수 칼국수', 'PENDING');
