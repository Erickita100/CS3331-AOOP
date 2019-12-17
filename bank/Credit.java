
/**
 *Credit account subclass of account
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
public class Credit extends Account{
    
    private double max;
    public Credit(int account, double balance,double max){
        super(account,balance);
        this.max=max;
    }
    public Credit(){
        
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
    public double getMax(){
        return max;
    }
    public void setMax(double val){
        max =val;
    }
}
