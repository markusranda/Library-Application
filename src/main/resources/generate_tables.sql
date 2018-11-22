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
-- Table `library_db`.`Authors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_db`.`Authors` (
  `idAuthors` INT(11) NOT NULL AUTO_INCREMENT,
  `lName` VARCHAR(45) NULL DEFAULT NULL,
  `fName` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idAuthors`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `library_db`.`Books`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_db`.`Books` (
  `idBook` INT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `publisher` VARCHAR(45) NOT NULL,
  `ISBN` VARCHAR(45) NULL DEFAULT NULL,
  `image` BLOB NULL DEFAULT NULL,
  PRIMARY KEY (`idBook`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `library_db`.`Book_Authors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_db`.`Book_Authors` (
  `idBook` INT(11) NOT NULL,
  `idAuthors` INT(11) NOT NULL,
  PRIMARY KEY (`idBook`, `idAuthors`),
  CONSTRAINT `fk_Book_Authors_Authors1`
    FOREIGN KEY (`idAuthors`)
    REFERENCES `library_db`.`Authors` (`idAuthors`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Book_Authors_Books1`
    FOREIGN KEY (`idBook`)
    REFERENCES `library_db`.`Books` (`idBook`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `library_db`.`Genre`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_db`.`Genre` (
  `idGenre` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idGenre`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `library_db`.`Book_Genres`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_db`.`Book_Genres` (
  `idGenre` INT(11) NOT NULL,
  `idBook` INT(11) NOT NULL,
  PRIMARY KEY (`idGenre`, `idBook`),
  CONSTRAINT `fk_Book_Genres_Books1`
    FOREIGN KEY (`idBook`)
    REFERENCES `library_db`.`Books` (`idBook`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Book_Genres_Genre1`
    FOREIGN KEY (`idGenre`)
    REFERENCES `library_db`.`Genre` (`idGenre`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `library_db`.`Branches`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_db`.`Branches` (
  `idBranch` INT(11) NOT NULL AUTO_INCREMENT,
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
  `idCustomer` INT(11) NOT NULL AUTO_INCREMENT,
  `fname` VARCHAR(45) NOT NULL,
  `lname` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idCustomer`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `library_db`.`Employee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_db`.`Employee` (
  `idEmployee` INT(11) NOT NULL AUTO_INCREMENT,
  `fname` VARCHAR(45) NOT NULL,
  `lname` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(45) NOT NULL,
  `accountNumber` VARCHAR(45) NULL DEFAULT NULL,
  `SSN` INT(11) NOT NULL,
  `position` VARCHAR(45) NOT NULL,
  `idBranch` INT(11) NOT NULL,
  PRIMARY KEY (`idEmployee`, `idBranch`),
  CONSTRAINT `fk_Employee_Branches1`
    FOREIGN KEY (`idBranch`)
    REFERENCES `library_db`.`Branches` (`idBranch`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `library_db`.`Users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `library_db`.`Users`;
CREATE TABLE IF NOT EXISTS `library_db`.`Users` (
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `usertype` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `library_db`.`Loans`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `library_db`.`Loans`;
CREATE TABLE IF NOT EXISTS `library_db`.`Loans` (
  `idLoans` INT(11) NOT NULL AUTO_INCREMENT,
  `loanDate` DATE NOT NULL,
  `loanDue` DATE NOT NULL,
  `idBook` INT(11) NOT NULL,
  `idBranch` INT(11) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idLoans`, `idBook`, `username`, `idBranch`),
  CONSTRAINT `fk_Loans_Books1`
    FOREIGN KEY (`idBook`)
    REFERENCES `library_db`.`Books` (`idBook`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Loans_Users1`
    FOREIGN KEY (`username`)
    REFERENCES `library_db`.`Users` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Loans_Branches1`
    FOREIGN KEY (`idBranch`)
    REFERENCES `library_db`.`Branches` (`idBranch`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `library_db`.`Customer_Users`
-- -----------------------------------------------------
DROP TABLE `library_db`.`Customer_Users`;
CREATE TABLE IF NOT EXISTS `library_db`.`Customer_Users` (
  `Users_username` VARCHAR(45) NOT NULL,
  `Customer_idCustomer` INT(11) NOT NULL,
  PRIMARY KEY (`Users_username`, `Customer_idCustomer`),
  CONSTRAINT `fk_Customer_Users_Users1`
    FOREIGN KEY (`Users_username`)
    REFERENCES `library_db`.`Users` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Customer_Users_Customer1`
    FOREIGN KEY (`Customer_idCustomer`)
    REFERENCES `library_db`.`Customer` (`idCustomer`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `library_db`.`Employee_Users`
-- -----------------------------------------------------
DROP TABLE `library_db`.`Employee_Users`;
CREATE TABLE IF NOT EXISTS `library_db`.`Employee_Users` (
  `username` VARCHAR(45) NOT NULL,
  `idEmployee` INT(11) NOT NULL,
  PRIMARY KEY (`username`, `idEmployee`),
  CONSTRAINT `fk_Employee_Users_Users1`
    FOREIGN KEY (`username`)
    REFERENCES `library_db`.`Users` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Employee_Users_Employee1`
    FOREIGN KEY (`idEmployee`)
    REFERENCES `library_db`.`Employee` (`idEmployee`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
