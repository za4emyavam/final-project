package com.myfp.fp.entities;

import java.sql.Date;

public class Check extends Entity {
    private User checkerId;
    private int users;
    private int amount;
    private Date dateOfCheck;

    public User getCheckerId() {
        return checkerId;
    }

    public void setCheckerId(User checkerId) {
        this.checkerId = checkerId;
    }

    public int getUsers() {
        return users;
    }

    public void setUsers(int users) {
        this.users = users;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getDateOfCheck() {
        return dateOfCheck;
    }

    public void setDateOfCheck(Date dateOfCheck) {
        this.dateOfCheck = dateOfCheck;
    }
}
