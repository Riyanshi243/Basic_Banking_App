package com.internship.bank_sparks;

public class Model {
    String phoneNo, name, balance, name1, name2, date, transaction_status;

    public Model(String phoneNo, String name, String balance) {
        this.phoneNo = phoneNo;
        this.name = name;
        this.balance = balance;
    }

    public Model(String name1, String name2, String balance, String date, String transaction_status) {
        this.name1 = name1;
        this.name2 = name2;
        this.balance = balance;
        this.date = date;
        this.transaction_status = transaction_status;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBalance() {
        return balance;
    }

    public String getName1() {
        return name1;
    }

    public String getName2() {
        return name2;
    }

    public String getDate() {
        return date;
    }

    public String getTransaction_status() {
        return transaction_status;
    }

}
