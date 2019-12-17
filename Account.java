
import java.text.DecimalFormat;

/**
 *Account abstract class
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

public abstract class Account {
    
    private int accountNum = 0;
    private double balance = 0;
    
    
    Account(){
        
    }
    Account(int accountNum, double balance){
        this.accountNum = accountNum;
        this.balance = balance;
    }
    /**
     * 
     * @return returns account number
     */
    public int getAccountNum(){
        return accountNum;
    }
    /**
     * 
     * @return returns balance
     */
    public double getBalance(){
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(2);
        //System.out.println(df.format(test));
        
        return Double.parseDouble(df.format(balance));
    }
    /**
     * sets the account number
     * @param account account number
     */
    public void setAccountNum(int account){
        this.accountNum = account;
    }
    /**
     * sets balance of account
     * @param bal new balance
     */
    public void setBalance(double bal){
        this.balance = bal;
    }


}
