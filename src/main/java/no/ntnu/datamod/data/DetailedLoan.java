package no.ntnu.datamod.data;

import java.sql.Date;

public class DetailedLoan {

    private int idLoan;
    private int idBook;
    private int idBranch;
    private String username;
    private String library;
    private String bookTitle;
    private String authors;
    private Date loanDate;
    private Date loanDue;
    private long remainingDays;
    private long fine;

    public DetailedLoan(int idLoan, int idBook, int idBranch, String username, String library, String bookTitle, String authors, Date loanDate, Date loanDue, long remainingDays, long fine) {
        this.idLoan = idLoan;
        this.idBook = idBook;
        this.idBranch = idBranch;
        this.username = username;
        this.library = library;
        this.bookTitle = bookTitle;
        this.authors = authors;
        this.loanDate = loanDate;
        this.loanDue = loanDue;
        this.remainingDays = remainingDays;
        this.fine = fine;
    }

    public int getIdBranch() {
        return idBranch;
    }

    public int getIdBook() {
        return idBook;
    }

    public String getUsername() {
        return username;
    }

    public int getIdLoan() {
        return idLoan;
    }

    public String getLibrary() {
        return library;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getAuthors() {
        return authors;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public Date getLoanDue() {
        return loanDue;
    }

    public long getRemainingDays() {
        return remainingDays;
    }

    public long getFine() {
        return fine;
    }
}
