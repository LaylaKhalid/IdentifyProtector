package com.identifyprotector.identifyprotector;

public class creditcard {

    private String Nickname;
    private String ActivateMonitoring;
    private String CreditNum;
    private String SecureCode;
    private String CardOwner;
    private String ExpDate;
    private String PhoneNum;
    private String Country;
    private String City;
    private String Street;
    private String ZipCode;
    private String ID;

    public creditcard(String ID,String nickname, String activateMonitoring, String creditNum, String secureCode, String cardOwner, String expDate, String phoneNum, String country, String city, String street, String zipCode) {
        this.ID=ID;
        Nickname = nickname;
        ActivateMonitoring = activateMonitoring;
        CreditNum = creditNum;
        SecureCode = secureCode;
        CardOwner = cardOwner;
        ExpDate = expDate;
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

    public String getCreditNum() {
        return CreditNum;
    }

    public void setCreditNum(String creditNum) {
        CreditNum = creditNum;
    }

    public String getSecureCode() {
        return SecureCode;
    }

    public void setSecureCode(String secureCode) {
        SecureCode = secureCode;
    }

    public String getCardOwner() {
        return CardOwner;
    }

    public void setCardOwner(String cardOwner) {
        CardOwner = cardOwner;
    }


    public String getExpDate() {
        return ExpDate;
    }

    public void setExpDate(String expDate) {
        ExpDate = expDate;
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
