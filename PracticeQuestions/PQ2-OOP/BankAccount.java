public class BankAccount {
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    
    public double getBalance(){
        return balance;
    }
    public void setBalance(double balance){
        if (balance < 0) {
            System.out.println("Inavlid balance");
        } else {
            this.balance = balance;
        }
    }
    public String getAccountHolderName() {
        return accountHolderName;
    }
    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    void displayDetails(){
System.out.println(accountHolderName+accountNumber+balance);
    }
}
