/**
 *Customer class with is a subclass of person
 * @author erickanajera
 * alias boo
 * November 8, 2019
 * CS3331 advanced object oriented
 * Daniel Mejia
 * Lab 3
 * Added abstract classes and interfaces to bank and allow more transactions
 * I confirm that the work of this assignment is completely my own. 
 * By turning in this assignment, I declare that i did not receive unauthorized assistance.
 * Moreover, all deliverables including, but not limited to the source code, lab report, and output files were written and produced by me alone.
 */
public class Customer extends Person{
    private Savings savings;
    private Checking checking;
    private Credit credit;
    
    public Customer(String firstName,String lastName,String birthDate,String address, String phone, int id,Savings savings,Checking checking,Credit credit){
        super(firstName,lastName,birthDate,address,phone,id);
        this.savings=savings;
        this.checking = checking;
        this.credit = credit;
        
        
    }
    /**
     * prints an inquire report of that account including name of customer and balances on all accounts
     */
    public void inquiry(){
        System.out.println("Account name: "+ getFirstName()+" "+ getLastName());
        System.out.println("Checking balance: $"+ checking.getBalance());
        System.out.println("Savings balance: $"+ savings.getBalance());
        System.out.println("Credit balance: $"+ credit.getBalance());

    }
    /**
     * 
     * @return the savings instance of the object
     */
    public Savings getSavings(){
        return savings;
    }
    /**
     * sets the savings info for that customer
     * @param acc account number
     * @param bal balance of account
     */
    public void setSavings(int acc, double bal){
        savings.setAccountNum(acc);
        savings.setBalance(bal);
    }
    /**
     * 
     * @return the checking instance of the object
     */
    public Checking getChecking(){
        return checking;
    }
    
    /**
     * sets the checking info for that customer
     * @param acc account number
     * @param bal balance of account
     */
    public void setChecking(int acc, double bal){
        checking.setAccountNum(acc);
        checking.setBalance(bal);
    }
    /**
     * 
     * @return the credit instance of the object
     */
    public Credit getCredit(){
        return credit;
    }
    /**
     * sets the credit info for that customer
     * @param acc account number
     * @param bal balance of account
     */
    public void setCredit(int acc, double bal){
        credit.setAccountNum(acc);
        credit.setBalance(bal);
    }
    
}
