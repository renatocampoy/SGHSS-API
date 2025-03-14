SET SQL_SAFE_UPDATES = 1;
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema healthcare_api
-- -----------------------------------------------------

CREATE SCHEMA IF NOT EXISTS `healthcare_api` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `healthcare_api` ;


SET FOREIGN_KEY_CHECKS = 0;

-- Tabela de Usuários
CREATE TABLE Users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role ENUM('ADMIN', 'PROFISSIONAL', 'PACIENTE') NOT NULL,
                       status ENUM('ATIVO', 'BLOQUEADO', 'DESATIVADO') NOT NULL DEFAULT 'ATIVO',
                       last_login DATETIME DEFAULT NULL,
                       password_reset_token VARCHAR(255) DEFAULT NULL,
                       password_reset_expires DATETIME DEFAULT NULL,
                       two_factor_enabled TINYINT(1) DEFAULT FALSE,
                       failed_login_attempts INT DEFAULT 0,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Unidades de Saúde
CREATE TABLE ServiceUnit (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             name VARCHAR(255) NOT NULL,
                             address VARCHAR(255) NOT NULL,
                             number VARCHAR(10) NOT NULL,
                             city VARCHAR(100) NOT NULL,
                             state VARCHAR(50) NOT NULL,
                             country VARCHAR(100) NOT NULL,
                             email VARCHAR(255) UNIQUE NOT NULL,
                             phone VARCHAR(20),
                             mobile VARCHAR(20),
                             capacity INT DEFAULT 0,
                             status ENUM('ATIVO', 'DESATIVADO') NOT NULL DEFAULT 'ATIVO',
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Pacientes
CREATE TABLE Patients (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          user_id INT UNIQUE NOT NULL,
                          name VARCHAR(255) NOT NULL,
                          birth_date DATE NOT NULL,
                          cpf VARCHAR(14) UNIQUE NOT NULL,
                          address VARCHAR(255) NOT NULL,
                          number VARCHAR(10) NOT NULL,
                          city VARCHAR(100) NOT NULL,
                          state VARCHAR(50) NOT NULL,
                          country VARCHAR(100) NOT NULL,
                          email VARCHAR(255) UNIQUE NOT NULL,
                          phone VARCHAR(20),
                          phone_contact VARCHAR(20),
                          mobile VARCHAR(20),
                          status ENUM('ATIVO', 'DESATIVADO') NOT NULL DEFAULT 'ATIVO',
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
) ENGINE=InnoDB ;

-- Quartos e Ocupação
CREATE TABLE Rooms (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       service_unit_id INT NOT NULL,
                       number_beds INT NOT NULL,
                       name_room VARCHAR(100) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       FOREIGN KEY (service_unit_id) REFERENCES ServiceUnit(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE RoomsOccupancy (
                                id INT AUTO_INCREMENT PRIMARY KEY,
                                room_id INT NOT NULL,
                                number_bed INT NOT NULL,
                                status ENUM('OCUPADO', 'LIVRE') NOT NULL DEFAULT 'LIVRE',
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                FOREIGN KEY (room_id) REFERENCES Rooms(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Estoque
CREATE TABLE Stock (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       service_unit_id INT NOT NULL,
                       name VARCHAR(255) NOT NULL,
                       quantity INT NOT NULL,
                       unit_value DECIMAL(10,2) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       FOREIGN KEY (service_unit_id) REFERENCES ServiceUnit(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE StockItens (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            stock_id INT NOT NULL,
                            hospitalization_id INT DEFAULT NULL,
                            qty INT NOT NULL,
                            expiration_date DATE NOT NULL,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            FOREIGN KEY (stock_id) REFERENCES Stock(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Consultas Médicas
CREATE TABLE MedicalConsultations (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      patient_id INT NOT NULL,
                                      professional_id INT NOT NULL,
                                      schedule_datetime DATETIME NOT NULL,
                                      duration INT NOT NULL,
                                      diagnosis TEXT DEFAULT NULL,
                                      notes TEXT DEFAULT NULL,
                                      status ENUM('ATIVO', 'DESATIVADO') NOT NULL DEFAULT 'ATIVO',
                                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                      FOREIGN KEY (patient_id) REFERENCES Patients(id) ON DELETE CASCADE,
                                      FOREIGN KEY (professional_id) REFERENCES Users(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Prescrição Médica
CREATE TABLE MedicalPrescrition (
                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                    medical_consultation_id INT NOT NULL,
                                    notes TEXT DEFAULT NULL,
                                    status ENUM('ATIVO', 'EXCLUIDA') NOT NULL DEFAULT 'ATIVO',
                                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                    FOREIGN KEY (medical_consultation_id) REFERENCES MedicalConsultations(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Internações
CREATE TABLE Hospitalization (
                                 id INT AUTO_INCREMENT PRIMARY KEY,
                                 patient_id INT NOT NULL,
                                 professional_id INT NOT NULL,
                                 rooms_occupancy_id INT NOT NULL,
                                 status ENUM('ATIVO', 'ALTA') NOT NULL DEFAULT 'ATIVO',
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                 FOREIGN KEY (patient_id) REFERENCES Patients(id) ON DELETE CASCADE,
                                 FOREIGN KEY (rooms_occupancy_id) REFERENCES RoomsOccupancy(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE HospitalizationReport (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       hospitalization_id INT NOT NULL,
                                       medical_data TEXT NOT NULL,
                                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                       FOREIGN KEY (hospitalization_id) REFERENCES Hospitalization(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Telemedicina
CREATE TABLE Teleservice (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             medical_consultation_id INT NOT NULL,
                             status ENUM('AGUARDANDO', 'ATENDIMENTO', 'FINALIZADO') NOT NULL DEFAULT 'AGUARDANDO',
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             FOREIGN KEY (medical_consultation_id) REFERENCES MedicalConsultations(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE TeleserviceChat (
                                 id INT AUTO_INCREMENT PRIMARY KEY,
                                 teleservice_id INT NOT NULL,
                                 professional_id INT NOT NULL,
                                 patient_id INT NOT NULL,
                                 text_chat TEXT NOT NULL,
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                 FOREIGN KEY (teleservice_id) REFERENCES Teleservice(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Auditoria do Sistema
CREATE TABLE SystemAudit (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             user_id INT NOT NULL,
                             ip_address VARCHAR(45) NOT NULL,
                             module VARCHAR(255) NOT NULL,
                             payload TEXT NOT NULL,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Regras e Permissões
CREATE TABLE Rules (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE Permissions (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             rule_id INT NOT NULL,
                             module VARCHAR(255) NOT NULL,
                             action VARCHAR(255) NOT NULL,
                             permission TINYINT(1) NOT NULL DEFAULT TRUE,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             FOREIGN KEY (rule_id) REFERENCES Rules(id) ON DELETE CASCADE
) ENGINE=InnoDB;


-- Profissionais
CREATE TABLE Professionals (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               service_unit_id INT NOT NULL,
                               name VARCHAR(255) NOT NULL,
                               user_id INT UNIQUE NOT NULL,
                               class_registration VARCHAR(50) UNIQUE NOT NULL,
                               cpf VARCHAR(14) UNIQUE NOT NULL,
                               address VARCHAR(255) NOT NULL,
                               number VARCHAR(10) NOT NULL,
                               city VARCHAR(100) NOT NULL,
                               state VARCHAR(50) NOT NULL,
                               country VARCHAR(100) NOT NULL,
                               email VARCHAR(255) UNIQUE NOT NULL,
                               phone VARCHAR(20),
                               phone_contact VARCHAR(20),
                               mobile VARCHAR(20),
                               status ENUM('ATIVO', 'DESATIVADO') NOT NULL DEFAULT 'ATIVO',
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               FOREIGN KEY (service_unit_id) REFERENCES ServiceUnit(id) ON DELETE CASCADE,
                               FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Especialização Médica
CREATE TABLE MedicalSpecialization (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       name VARCHAR(255) NOT NULL UNIQUE,
                                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Funções dos Profissionais
CREATE TABLE Roles (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       professional_id INT NOT NULL,
                       role ENUM('MEDICO', 'ENFERMEIRO', 'TECNICO', 'ADMINISTRATIVO') NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       FOREIGN KEY (professional_id) REFERENCES Professionals(id) ON DELETE CASCADE
) ENGINE=InnoDB;


SET FOREIGN_KEY_CHECKS = 1;