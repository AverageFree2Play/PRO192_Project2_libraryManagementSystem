package Entites;

import Utilities.DataValidation;

public class Member extends Entity{
    private String ID,Name,Phone,Email;
    private boolean IsPremium;

    public Member() {
        ID = "";
        Name = "N/A";
        Phone = "000.000.0000";
        Email = "N/A";
        IsPremium = false;
    }
    // CONSTRUCTOR
    public Member(String ID, String Name, String Phone, String Email,boolean IsPremium) throws Exception{
        setId(ID);
        setName(Name);
        setPhone(Phone);
        setEmail(Email);
        
        this.IsPremium = IsPremium;
    }
    // METHODS
    
    @Override
    public void setId(String ID) throws Exception {
        if(!DataValidation.checkStringWithFormat(ID.toUpperCase(),"M\\d{3}")){
            throw new Exception("Invalid ID. Format: Mxxx. Got:" + ID);
        }        
        this.ID = ID;
    }

    public String getName(){return Name;}
    public void setName(String Name){
        this.Name = Name;
    }

    public String getPhone(){return Phone;}
    public void setPhone(String Phone) throws Exception{
        if(!DataValidation.checkIfValidPhoneNumber(Phone)){
            throw new Exception("Invalid phone number. Format: xxx.xxx.xxxx. Got:" + Phone);
        }
        this.Phone = Phone;
    }

    public String getEmail(){return Email;}
    public void setEmail(String Email) throws Exception {
        if(!DataValidation.checkStringWithFormat(Email,"^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")){
            throw new Exception("Email is invalid.");
        }
        this.Email = Email;
    }
    
    
}
