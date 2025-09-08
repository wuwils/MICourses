CREATE TABLE IF NOT EXISTS user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
);

 ALTER TABLE user ADD CONSTRAINT unique_username UNIQUE (username);

CREATE TABLE IF NOT EXISTS item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS comment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    user_id BIGINT,
    item_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (item_id) REFERENCES item(id)
);

INSERT INTO user (username, password, email) VALUES
('alice', 'password123', 'alice@example.com'),
('bob', 'password456', 'bob@example.com'),
('charlie', 'password789', 'charlie@example.com');


INSERT INTO item (name, description, price, user_id) VALUES
('Used Textbook', 'Introduction to Algorithms, 3rd Edition', 25.00, 1),
('Bicycle', 'Mountain bike, lightly used', 150.00, 2),
('Laptop', 'MacBook Pro 2019, 16GB RAM', 1200.00, 3);

INSERT INTO comment (content, user_id, item_id) VALUES
('Great condition, would recommend!', 2, 1),
('Price is a bit high, but good quality.', 3, 1),
('Smooth ride, well maintained.', 1, 2),
('Battery life is excellent.', 2, 3);