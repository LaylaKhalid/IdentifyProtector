package com.identifyprotector.identifyprotector;

public class personalinformation {

    private String Nickname;
    private String ActivateMonitoring;
    private String FirstName;
    private String MiddleMame;
    private String LastName;
    private String DOB;
    private String NationalID;
    private String PassportNumber;
    private String mail;
    private String PhoneNum;
    private String Country;
    private String City;
    private String Street;
    private String ZipCode;
    private String ID;


    public personalinformation(String ID,String nickname, String activateMonitoring, String firstName, String middleMame, String lastName, String DOB, String nationalID, String passportNumber, String mail, String phoneNum, String country, String city, String street, String zipCode) {
        this.ID=ID;
        Nickname = nickname;
        ActivateMonitoring = activateMonitoring;
        FirstName = firstName;
        MiddleMame = middleMame;
        LastName = lastName;
        this.DOB = DOB;
        NationalID = nationalID;
        PassportNumber = passportNumber;
        this.mail = mail;
        PhoneNum = phoneNum;
        Country = country;
        City = city;
        Street = street;
        ZipCode = zipCode;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public String getActivateMonitoring() {
        return ActivateMonitoring;
    }

    public void setActivateMonitoring(String activateMonitoring) {
        ActivateMonitoring = activateMonitoring;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getMiddleMame() {
        return MiddleMame;
    }

    public void setMiddleMame(String middleMame) {
        MiddleMame = middleMame;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getNationalID() {
        return NationalID;
    }

    public void setNationalID(String nationalID) {
        NationalID = nationalID;
    }

    public String getPassportNumber() {
        return PassportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        PassportNumber = passportNumber;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhoneNum() {
        return PhoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        PhoneNum = phoneNum;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }
}
