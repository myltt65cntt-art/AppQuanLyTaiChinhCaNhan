package ck.my65131996.spendwise;

public class TransactionModel {

    private String id;
    private String transactionId;
    private String money;
    private String note;
    private String category;
    private String date;
    private String fundId;

    private boolean isIncome;

    private long timestamp;

    public TransactionModel() {
    }

    // ID FIREBASE

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // TRANSACTION ID

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    // MONEY

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    // NOTE

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    // CATEGORY

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // DATE

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    // FUND ID
    public String getFundId() {
        return fundId;
    }
    public void setFundId(String fundId) {
        this.fundId = fundId;
    }
    // INCOME

    public boolean getIsIncome() {
        return isIncome;
    }

    public boolean isIncome() {
        return isIncome;
    }

    public void setIncome(boolean income) {
        this.isIncome = income;
    }

    public void setIsIncome(boolean income) {
        this.isIncome = income;
    }

    // TIMESTAMP

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}