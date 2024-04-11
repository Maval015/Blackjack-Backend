CREATE TABLE credit_card_info (
    Name VARCHAR(50),
    Number BIGINT,
    Expiration DATE,
    CVV INT,
    Zipcode INT,
    PRIMARY KEY (Name, Number)
);

CREATE TABLE incident_report (
    Name VARCHAR(50),
    Date DATE,
    Email VARCHAR(100),
    PRIMARY KEY (Name, Email)
);

CREATE TABLE user (
    Username VARCHAR(50),
    Password VARCHAR(50),
    Email VARCHAR(100),
    Available_Tokens INT,
    PRIMARY KEY (Username)
);

INSERT INTO user (Username, Password, Email, Available_Tokens) 
VALUES 
('clarkep', 'pass123', 'clarkep@fiu.edu', 1000),
('mattK', 'pass789', 'mattK@fiu.edu', 20000),
('yailanB', 'pass456', 'yailanB@fiu.edu', 10000);

-- Generate sample rows for incident_report table
INSERT INTO incident_report (Name, Date, Email)
VALUES 
('John Doe', '2024-04-10', 'john.doe@example.com'),
('Jane Smith', '2024-04-09', 'jane.smith@example.com'),
('Bob Johnson', '2024-04-08', 'bob.johnson@example.com');

-- Generate sample rows for credit_card_info table
INSERT INTO credit_card_info (Name, Number, Expiration, CVV, Zipcode)
VALUES 
('John Doe', 1234567890123456, '2025-12-31', 123, 12345),
('Jane Smith', 9876543210987654, '2024-10-31', 456, 54321),
('Bob Johnson', 1111222233334444, '2023-05-31', 789, 67890);