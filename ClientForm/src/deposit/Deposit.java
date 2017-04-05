package deposit;

import java.io.Serializable;

/**
 * Created by mozil on 01.04.2017.
 */
public class Deposit implements Serializable {

    private String nameOfBank;
    private String country;
    private String typeOfDeposit;
    private String depositorName;
    private int accountId;
    private double amountOnDeposit;
    private double profitability;
    private int timeConstraints;

    public Deposit() {
    }

    public Deposit(String nameOfBank, String country,
                   String typeOfDeposit, String depositorName,
                   int accountId, double amountOnDeposit,
                   double profitability, int timeConstraints) {
        this.nameOfBank = nameOfBank;
        this.country = country;
        this.typeOfDeposit = typeOfDeposit;
        this.depositorName = depositorName;
        this.accountId = accountId;
        this.amountOnDeposit = amountOnDeposit;
        this.profitability = profitability;
        this.timeConstraints = timeConstraints;
    }

    public String getNameOfBank() {
        return nameOfBank;
    }

    public void setNameOfBank(String nameOfBank) {
        this.nameOfBank = nameOfBank;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTypeOfDeposit() {
        return typeOfDeposit;
    }

    public void setTypeOfDeposit(String typeOfDeposit) {
        this.typeOfDeposit = typeOfDeposit;
    }

    public String getDepositorName() {
        return depositorName;
    }

    public void setDepositorName(String depositorName) {
        this.depositorName = depositorName;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getAmountOnDeposit() {
        return amountOnDeposit;
    }

    public void setAmountOnDeposit(double amountOnDeposit) {
        this.amountOnDeposit = amountOnDeposit;
    }

    public double getProfitability() {
        return profitability;
    }

    public void setProfitability(double profitability) {
        this.profitability = profitability;
    }

    public int getTimeConstraints() {
        return timeConstraints;
    }

    public void setTimeConstraints(int timeConstraints) {
        this.timeConstraints = timeConstraints;
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "nameOfBank='" + nameOfBank + '\'' +
                ", country='" + country + '\'' +
                ", typeOfDeposit='" + typeOfDeposit + '\'' +
                ", depositorName='" + depositorName + '\'' +
                ", accountId=" + accountId +
                ", amountOnDeposit=" + amountOnDeposit +
                ", profitability=" + profitability +
                ", timeConstraints=" + timeConstraints +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Deposit deposit = (Deposit) o;

        return accountId == deposit.accountId;
    }

    @Override
    public int hashCode() {
        return accountId;
    }
}


