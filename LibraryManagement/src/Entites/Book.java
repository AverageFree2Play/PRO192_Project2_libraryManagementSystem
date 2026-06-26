package Entites;

import Utilities.DataValidation;

public class Book {
    private String ID,Title,Author,Genre;
    private int Quantity,PubYear;
    // CONSTRUCTORS
    public Book(String part, String part1, String part2, String part3, int parseInt) {
        ID = "";
        Title = "";
        Author = "";
        Genre = "";
        PubYear = 1900;
        Quantity = 1;
    }

    public Book(String ID, String Title, String Author, String Genre, int PubYear, int Quantity) throws Exception {
        setID(ID);
        setTitle(Title);
        setAuthor(Author);
        setGenre(Genre);
        setPubYear(PubYear);
        setQuantity(Quantity);
    }
    
    // METHODS
    public String getBookID(){return ID;}
    public void setID(String ID) throws Exception{
        if(!DataValidation.checkStringWithFormat(ID.toUpperCase(),"B\\d{2}")){
            throw new Exception("Invalid ID. Format: Bxx. Got:" + ID);
        }        
        this.ID = ID;
    }

    public String getTitle(){return Title;}
    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getAuthor(){return Author;}
    public void setAuthor(String Author){
        this.Author = Author;
    }

    public String getGenre(){return Genre;}
    public void setGenre(String Genre){
        this.Genre = Genre;
    }

    public int getPubYear(){return PubYear;}
    public void setPubYear(int PubYear) throws Exception{
        if(!DataValidation.checkIfValidYear(PubYear)){
            throw new Exception("Invalid year. Got:" + PubYear);
        }
        
        this.PubYear = PubYear;
    }

    public int getQuantity(){return Quantity;}
    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }    

    @Override
    public String toString() {
        return String.format("%-5s | %-30s | %-20s | %-15s | %4d | %d",
        ID, Title, Author, Genre, PubYear, Quantity);
    }
    
}
