package com.identifyprotector.identifyprotector.simple;

public class creditcard {

    private int ID;
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
    private String UserID;

public creditcard(int ID, String Nickname, String ActivateMonitoring, String CreditNum, String SecureCode, String CardOwner, String ExpDate,
                  String PhoneNum, String Country, String City, String Street, String ZipCode, String UserID){

    this.ID=ID;
    this.Nickname=Nickname;
    this.ActivateMonitoring=ActivateMonitoring;
    this.CreditNum=CreditNum;
    this.SecureCode=SecureCode;
    this.CardOwner=CardOwner;
    this.ExpDate=ExpDate;
    this.PhoneNum=PhoneNum;
    this.Country=Country;
    this.City=City;
    this.Street=Street;
    this.ZipCode=ZipCode;
    this.UserID=UserID;

}

    public int getID() {
        return ID;
    }

    public String getNickname() {
        return Nickname;
    }

    public String getActivateMonitoring() {
        return ActivateMonitoring;
    }

    public String getCreditNum() {
        return CreditNum;
    }

    public String getSecureCode() {
        return SecureCode;
    }

    public String getCardOwner() {
        return CardOwner;
    }

    public String getExpDate() {
        return ExpDate;
    }

    public String getPhoneNum() {
        return PhoneNum;
    }

    public String getCountry() {
        return Country;
    }

    public String getCity() {
        return City;
    }

    public String getStreet() {
        return Street;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public String getUserID() {
        return UserID;
    }
}
