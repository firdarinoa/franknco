package co.id.franknco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by e_er_de on 13/09/2017.
 */

public class General {

    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("card_id")
    @Expose
    private String cardId;
    @SerializedName("x175")
    @Expose
    private String x175;
    @SerializedName("card_key")
    @Expose
    private String cardKey;
    @SerializedName("valid_until")
    @Expose
    private String validUntil;
    @SerializedName("card_number")
    @Expose
    private String cardNumber;
    @SerializedName("transaction_type")
    @Expose
    private String transactionType;
    @SerializedName("x761")
    @Expose
    private String x761;
    @SerializedName("trans_date")
    @Expose
    private String transDate;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getX175() {
        return x175;
    }

    public void setX175(String x175) {
        this.x175 = x175;
    }

    public String getCardKey() {
        return cardKey;
    }

    public void setCardKey(String cardKey) {
        this.cardKey = cardKey;
    }

    public String getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(String validUntil) {
        this.validUntil = validUntil;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getX761() {
        return x761;
    }

    public void setX761(String x761) {
        this.x761 = x761;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }
}
