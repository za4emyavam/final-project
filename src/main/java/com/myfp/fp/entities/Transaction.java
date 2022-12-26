package com.myfp.fp.entities;

import java.sql.Date;

public class Transaction extends Entity {
    private Integer balanceId;
    private TransactionType type;
    private Integer transactionAmount;
    private Date transactionDate;
    private TransactionStatus status;

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Integer getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(Integer balanceId) {
        this.balanceId = balanceId;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Integer getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Integer transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
}
