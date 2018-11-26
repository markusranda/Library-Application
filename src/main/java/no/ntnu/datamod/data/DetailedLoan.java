package no.ntnu.datamod.data;

import java.sql.Date;

public class DetailedLoan {

    private String library;
    private String bookTitle;
    private String authors;
    private Date loanDate;
    private Date loanDue;
    private long remainingDays;
    private long fine;

    public DetailedLoan(String library, String bookTitle, String authors, Date loanDate, Date loanDue, long remainingDays, long fine) {
        this.library = library;
        this.bookTitle = bookTitle;
        this.authors = authors;
        this.loanDate = loanDate;
        this.loanDue = loanDue;
        this.remainingDays = remainingDays;
        this.fine = fine;
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
