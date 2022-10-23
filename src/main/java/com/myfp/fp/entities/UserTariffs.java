package com.myfp.fp.entities;

import java.sql.Date;

public class UserTariffs extends Entity {
    private User user;
    private Tariff tariff;
    private Date dateOfStart;
    private Date dateOfLastPayment;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public Date getDateOfStart() {
        return dateOfStart;
    }

    public void setDateOfStart(Date dateOfStart) {
        this.dateOfStart = dateOfStart;
    }

    public Date getDateOfLastPayment() {
        return dateOfLastPayment;
    }

    public void setDateOfLastPayment(Date dateOfLastPayment) {
        this.dateOfLastPayment = dateOfLastPayment;
    }
}
