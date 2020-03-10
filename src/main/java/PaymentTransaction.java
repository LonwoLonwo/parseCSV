import java.util.Date;

public class PaymentTransaction
{
    private String bankAccount;
    private Date transactionDate;
    private int incoming;
    private double expense;

    public PaymentTransaction(String bankAccount, Date transactionDate, int incoming, double expense) {
        this.bankAccount = bankAccount;
        this.transactionDate = transactionDate;
        this.incoming = incoming;
        this.expense = expense;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public int getIncoming() {
        return incoming;
    }

    public void setIncoming(int incoming) {
        this.incoming = incoming;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }
}
