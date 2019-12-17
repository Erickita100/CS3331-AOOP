

/**
 *Checkings class
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

public class Checking extends Account{
    
    public Checking(int account, double balance){
        super(account,balance);
    }
    
    public Checking(){
        
    }
   
    
    /**
     * add money to balance
     * @param money amount of money
     */ 
    public void deposit(double money){
        double bal = getBalance();
        bal +=money;
        setBalance(bal);
    }
    /**
     * removes money from balance
     * @param money amount of money
     */
    public void withdraw(double money){
        double bal = getBalance();
        if(bal - money >0){
            bal -=money;
            setBalance(bal);}
        else{
            System.out.println("Withdrawl cancelled: insuficient funds");
        }
    }
    
   
   
    
}
