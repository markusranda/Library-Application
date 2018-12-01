INSERT INTO Branches

      (`name`, `address`)
VALUES
       ("Moa Bibliotek",
        "Granveien 3"),
       ("Ålesund Bibliotek",
        "Sausveien 3"),
       ("Brunholmen Bibliotek",
        "Feskveien 3"),
       ("Campus Ålesund Bibliotek",
        "Reinveien 3"),
       ("Gåseid Bibliotek",
        "Brødveien 3"),
       ("Magerholm Bibliotek",
        "Fjellveien 3");


INSERT INTO Employee

    (fname, lname, address, phone, accountNumber, SSN, position, idBranch)

VALUES
      ("Markus", "Randa", "Storgata 22", "23435423", "122323212", "12345678910", "IT", 5),
      ("Frode", "Kroknes", "Uhugata 69", "63435423", "322323212", "92345678910", "IT", 5);


INSERT INTO Users

    (username, password, usertype)

VALUES
    ("mra", "password", "admin"),
    ("frp", "password", "admin"),
    ("tml", "password", "customer"),
    ("emv", "password", "customer");


INSERT INTO Customer

    (fname, lname, address, phone)

VALUES
    ("Tor Martin", "Lysheim", "Langneset 23", "12324523"),
    ("Emil", "Valdenes", "Skummelgata 909", "19324523");


INSERT INTO Employee_Users

    (username, idEmployee)

VALUES
      ("mra", 1),
      ("frp", 2);


INSERT INTO Customer_Users

    (username, idCustomer)

VALUES

      ("tml", 1),
      ("emv", 2);


INSERT INTO Books

    (`title`,`publisher`,`ISBN`,image)

VALUES
       ("Grantyper",
        "GranGeir AS",
        "1231231",
        Null),
       ("Saustyper",
        "SausGeir AS",
        "1231231",
        Null),
       ("Fesktyper",
        "FeskGeir AS",
        "1231231",
        Null),
       ("Reintyper",
        "ReinGeir AS",
        "1231231",
        Null),
       ("Brødtyper",
        "BrødGeir AS",
        "1231231",
        Null),
       ("Fjelltyper",
        "FjellGeir AS",
        "1231231",
        Null),
       ("Lurtyper",
        "LurGeir AS",
        "1231231",
        Null),
       ("Det siste verset",
        "JBB AS",
        "1231231",
        Null),
       ("Jamaikansk kultur",
        "JBB AS",
        "1231231",
        Null),
       ("Snømannen",
        "JBB AS",
        "1231231",
        Null),
       ("Trailertips Med Roald",
        "JBB AS",
        "1231231",
        Null),
       ("Kjøtt eller løk",
        "JBB AS",
        "1231231",
        Null),
       ("Den komplette skuterguiden",
        "JBB AS",
        "1231231",
        Null),
       ("Søsken til evig tid",
        "Samlaget",
        "9788252197587",
        Null),
       ("Kampen om Narvik 62 desperate dager",
        "Gyldendal",
        "9788205504806",
        NULL),
       ("Sapiens en kort historie om menneskeheten",
        "Bazar",
        "9788280878021",
        NULL),
       ("Silkeveiene en ny verdenshistorie",
        "Gyldendal",
        "9788205514805",
        NULL),
       ("Arnhem slaget om broene, 1944",
        "Cappelen Damm",
        "9788202587826",
        NULL),
       ("Sapiens en innføring i menneskehetens historie",
        "Bazar",
        "9788280876850",
        NULL);


INSERT INTO Authors

    (lName, fName)

VALUES

    ("Ivar Myklebust", "Longvastøl"),
    ("Asbjørn", "Jaklin"),
    ("Yuval Noah", "Harari"),
    ("Peter", "Frankopan"),
    ("Antony", "Beevor");


INSERT INTO Book_Authors

      (idBook, idAuthors)

VALUES

      (14, 1),
      (15, 2),
      (16, 3),
      (17, 4),
      (18, 5),
      (19, 3);


INSERT INTO Genres

      (name)

VALUES

      ("History"),
      ("Fiction"),
      ("Crime"),
      ("Hobby"),
      ("Art"),
      ("Food");


INSERT INTO Book_Genres

      (idGenre, idBook)

VALUES

      (1, 15),
      (1, 16),
      (1, 17),
      (1, 18),
      (1, 19);


INSERT INTO `library_db`.`Book_Quantity`

      (`quantity`, `idBook`, `idBranch`)

VALUES

        (5,1,1),
        (4,2,3),
        (3,2,4),
        (4,3,5),
        (6,5,6),
        (8,4,5),
        (6,5,4),
        (6,6,3),
        (6,7,2),
        (6,8,1),
        (6,9,2),
        (6,10,3),
        (6,11,4),
        (6,12,5),
        (6,13,6),
        (6,5,1),
        (100, 14, 1),
        (100, 15, 1),
        (100, 16, 1),
        (100, 17, 1),
        (100, 18, 1),
        (100, 19, 1),
        (100, 14, 2),
        (100, 15, 2),
        (100, 16, 2),
        (100, 17, 2),
        (100, 18, 2),
        (100, 19, 2),
        (100, 14, 3),
        (100, 15, 3),
        (100, 16, 3),
        (100, 17, 3),
        (100, 18, 3),
        (100, 19, 3),
        (100, 14, 4),
        (100, 15, 4),
        (100, 16, 4),
        (100, 17, 4),
        (100, 18, 4),
        (100, 19, 4),
        (100, 14, 5),
        (100, 15, 5),
        (100, 16, 5),
        (100, 17, 5),
        (100, 18, 5),
        (100, 19, 5),
        (100, 14, 6),
        (100, 15, 6),
        (100, 16, 6),
        (100, 17, 6),
        (100, 18, 6),
        (100, 19, 6);


INSERT INTO `library_db`.`Book_Genres`

      (`idBook`, `idGenre`)

VALUES
        (14, 6),
        (15, 6),
        (16, 6),
        (17, 6),
        (18, 6),
        (19, 6);


INSERT INTO `library_db`.`Loans`

      (loanDate, loanDue, idBook, idBranch, username)

VALUES
        ("1980-12-24", "2002-02-24", 14, 1, "emv"),
        ("1899-12-24", "2000-02-24", 15, 2, "emv"),
        ("1985-12-19", "1999-10-21", 16, 3, "emv"),
        ("1980-12-24", "2002-02-24", 17, 3, "tml"),
        ("1899-12-24", "2000-02-24", 18, 2, "tml"),
        ("1985-12-19", "1999-10-21", 19, 1, "tml");


