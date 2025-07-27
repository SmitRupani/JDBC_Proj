CREATE DATABASE IF NOT EXISTS realestate;
USE realestate;

-- Agencies Table
CREATE TABLE Agencies (
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(255),
    registered_at DATETIME
);

-- Agents Table
CREATE TABLE Agents (
    id CHAR(36) PRIMARY KEY,
    agency_id CHAR(36),
    name VARCHAR(255),
    phone_number VARCHAR(255),
    password VARCHAR(255),
    hire_date DATE,
    properties_sold BIGINT,
    FOREIGN KEY (agency_id) REFERENCES Agencies(id)
);

-- Buyers Table
CREATE TABLE Buyers (
    id CHAR(36) PRIMARY KEY,
    email VARCHAR(255),
    phone_number VARCHAR(255),
    password VARCHAR(255),
    created_at DATETIME
);

-- Properties Table
CREATE TABLE Properties (
    id CHAR(36) PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    address TEXT,
    neighborhood VARCHAR(255),
    listing_price BIGINT,
    type ENUM('apartment', 'house', 'villa', 'commercial'),
    status ENUM('available', 'under_offer', 'sold'),
    created_at DATETIME,
    sold_date DATETIME
);

CREATE INDEX idx_neighborhood ON Properties (neighborhood);
CREATE INDEX idx_type ON Properties (type);
CREATE FULLTEXT INDEX idx_description ON Properties (description);
CREATE INDEX idx_status ON Properties (status);

-- Price Logs Table
CREATE TABLE Price_logs (
    id CHAR(36) PRIMARY KEY,
    property_id CHAR(36),
    updated_at DATETIME,
    price BIGINT,
    FOREIGN KEY (property_id) REFERENCES Properties(id)
);

-- Features Table
CREATE TABLE Features (
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(255)
);

-- Property Features Table (many-to-many)
CREATE TABLE Property_features (
    property_id CHAR(36),
    feature_id CHAR(36),
    PRIMARY KEY (property_id, feature_id),
    FOREIGN KEY (property_id) REFERENCES Properties(id),
    FOREIGN KEY (feature_id) REFERENCES Features(id)
);

-- Inquiries Table
CREATE TABLE Inquiries (
    id CHAR(36) PRIMARY KEY,
    agent_id CHAR(36),
    buyer_id CHAR(36),
    property_id CHAR(36),
    message TEXT,
    status ENUM('new', 'responded', 'closed'),
    created_at DATETIME,
    responded_at DATETIME,
    FOREIGN KEY (agent_id) REFERENCES Agents(id),
    FOREIGN KEY (buyer_id) REFERENCES Buyers(id),
    FOREIGN KEY (property_id) REFERENCES Properties(id)
);

CREATE INDEX idx_agent_status ON Inquiries (agent_id, status);
CREATE INDEX idx_property_time ON Inquiries (created_at, property_id);

-- Offers Table
CREATE TABLE Offers (
    id CHAR(36) PRIMARY KEY,
    buyer_id CHAR(36),
    agent_id CHAR(36),
    property_id CHAR(36),
    offered_by_buyer BOOLEAN,
    status ENUM('closed', 'pending_response', 'pending_transaction', 'rejected'),
    price BIGINT,
    created_at DATETIME,
    final_date DATETIME NULL,
    FOREIGN KEY (buyer_id) REFERENCES Buyers(id),
    FOREIGN KEY (agent_id) REFERENCES Agents(id),
    FOREIGN KEY (property_id) REFERENCES Properties(id)
);

CREATE INDEX idx_offer_status ON Offers (id, status);

-- Agents_Properties Table (many-to-many)
CREATE TABLE Agents_Properties (
    agent_id CHAR(36),
    property_id CHAR(36),
    PRIMARY KEY (agent_id, property_id),
    FOREIGN KEY (agent_id) REFERENCES Agents(id),
    FOREIGN KEY (property_id) REFERENCES Properties(id)
);
