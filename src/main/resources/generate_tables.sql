-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema library_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema library_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `library_db` DEFAULT CHARACTER SET latin1 ;
USE `library_db` ;

-- -----------------------------------------------------
-- Table `library_db`.`Book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_db`.`Books` (
  `idBook` INT(11) NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  `publisher` VARCHAR(45) NOT NULL,
  `authors` VARCHAR(45) NOT NULL,
  `ISBN` VARCHAR(45) NULL DEFAULT NULL,
  `image` BLOB NULL,
  PRIMARY KEY (`idBook`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `library_db`.`Branch`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_db`.`Branches` (
  `idBranch` INT(11) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idBranch`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `library_db`.`Book_Quantity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_db`.`Book_Quantity` (
  `quantity` INT(11) NOT NULL,
  `idBook` INT(11) NOT NULL,
  `idBranch` INT(11) NOT NULL,
  PRIMARY KEY (`idBook`, `idBranch`),
  INDEX `fk_Book_Copies_Branches1_idx` (`idBranch` ASC),
  CONSTRAINT `fk_Book_Copies_Books`
    FOREIGN KEY (`idBook`)
    REFERENCES `library_db`.`Books` (`idBook`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Book_Copies_Branches1`
    FOREIGN KEY (`idBranch`)
    REFERENCES `library_db`.`Branches` (`idBranch`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `library_db`.`Customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_db`.`Customer` (
  `idCustomer` INT NOT NULL,
  `fname` VARCHAR(45) NOT NULL,
  `lname` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idCustomer`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `library_db`.`Employee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_db`.`Employee` (
  `idEmployee` INT(11) NOT NULL,
  `fname` VARCHAR(45) NOT NULL,
  `lname` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(45) NOT NULL,
  `accountNumber` VARCHAR(45) NULL,
  `SSN` INT(11) NOT NULL,
  `position` VARCHAR(45) NOT NULL,
  `idBranch` INT(11) NOT NULL,
  PRIMARY KEY (`idEmployee`, `idBranch`),
  INDEX `fk_Employee_Branches1_idx` (`idBranch` ASC),
  CONSTRAINT `fk_Employee_Branches1`
    FOREIGN KEY (`idBranch`)
    REFERENCES `library_db`.`Branches` (`idBranch`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `library_db`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_db`.`Users` (
  `idUser` INT(11) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `usertype` VARCHAR(45) NOT NULL,
  `idCustomer` INT NOT NULL,
  `idEmployee` INT NOT NULL,
  PRIMARY KEY (`idUser`, `idCustomer`, `idEmployee`),
  INDEX `fk_Users_Customer1_idx` (`idCustomer` ASC),
  INDEX `fk_Users_Employee1_idx` (`idEmployee` ASC),
  CONSTRAINT `fk_Users_Customer1`
    FOREIGN KEY (`idCustomer`)
    REFERENCES `library_db`.`Customer` (`idCustomer`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Users_Employee1`
    FOREIGN KEY (`idEmployee`)
    REFERENCES `library_db`.`Employee` (`idEmployee`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `library_db`.`Loan`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_db`.`Loans` (
  `idLoans` INT(11) NOT NULL,
  `loanDate` DATE NOT NULL,
  `loanDue` DATE NOT NULL,
  `idBook` INT(11) NOT NULL,
  `idUser` INT(11) NOT NULL,
  PRIMARY KEY (`idLoans`, `idBook`, `idUser`),
  INDEX `fk_Loans_Books1_idx` (`idBook` ASC),
  INDEX `fk_Loans_Users1_idx` (`idUser` ASC),
  CONSTRAINT `fk_Loans_Books1`
    FOREIGN KEY (`idBook`)
    REFERENCES `library_db`.`Books` (`idBook`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Loans_Users1`
    FOREIGN KEY (`idUser`)
    REFERENCES `library_db`.`Users` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
