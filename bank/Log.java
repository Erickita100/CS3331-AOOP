
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
 
/**
 *log interface
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
public interface Log {
    public static ArrayList<String> log = new ArrayList<String>();
    
    /**
     * gets the log list and writes it into a file
     * @param file file name
     */
    static void logFile(String file){
       String[] list = new String[log.size()];
       list = log.toArray(list);
       PrintWriter writer;
        try {
            writer = new PrintWriter(file, "UTF-8");
            int counter =0;
            while(counter < list.length){
            writer.println(list[counter]);
            counter++;
            }
            writer.close();
        } catch (FileNotFoundException ex) {
            System.out.println();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        } 
       
    }
    /**
     * adds to log what account was inquired
     * @param customers customers information
     * @param account type of account
     * @param index index of account logged in
     */
    static public void logInquiry(ArrayList<Customer> customers,String account,int index) {
        if(account.equalsIgnoreCase("checking")){
            log.add(customers.get(index).getFirstName()+" "+customers.get(index).getLastName()+" made a balance inquiry on Checking-"+ customers.get(index).getChecking().getAccountNum()+
            ". Balance: $" + customers.get(index).getChecking().getBalance());
        }
        else if(account.equalsIgnoreCase("savings")){
            log.add(customers.get(index).getFirstName()+" "+customers.get(index).getLastName()+" made a balance inquiry on Savings-"+ customers.get(index).getSavings().getAccountNum()+
            ". Balance: $" + customers.get(index).getSavings().getBalance());
        }
        else{
            log.add(customers.get(index).getFirstName()+" "+customers.get(index).getLastName()+" made a balance inquiry on Credit-"+ customers.get(index).getCredit().getAccountNum()+
            ". Balance: $" + customers.get(index).getCredit().getBalance());
            
        }
        log.add(" ");
    }

    /**
     * adds to log what account was withdrawled
     * @param customers customers information
     * @param account type of account
     * @param index index of account logged in
     * @param amount amount of money
     */
    static public void logWithdral(ArrayList<Customer> customers,String account,int index,double amount) {
        if(account.equalsIgnoreCase("checking")){
            log.add(customers.get(index).getFirstName()+" "+customers.get(index).getLastName()+" made a withdraw of $" +amount+" on Checking-"+ customers.get(index).getChecking().getAccountNum()+
            ". Balance: $" + customers.get(index).getChecking().getBalance());
        }
        else if(account.equalsIgnoreCase("savings")){
            log.add(customers.get(index).getFirstName()+" "+customers.get(index).getLastName()+" made a withdraw of $" +amount+"on Savings-"+ customers.get(index).getSavings().getAccountNum()+
            ". Balance: $" + customers.get(index).getSavings().getBalance());
        }
         log.add(" ");
    }

    /**
     * adds to log what account was deposited
     * @param customers customers information
     * @param account type of account
     * @param index index of account logged in
     * @param amount amount of money
     */
    static public void logDeposit(ArrayList<Customer> customers,String account,int index,double amount) {
       if(account.equalsIgnoreCase("checking")){
            log.add(customers.get(index).getFirstName()+" "+customers.get(index).getLastName()+" made a deposit of $" +amount+" on Checking-"+ customers.get(index).getChecking().getAccountNum()+
            ". Balance: $" + customers.get(index).getChecking().getBalance());
        }
        else if(account.equalsIgnoreCase("savings")){
            log.add(customers.get(index).getFirstName()+" "+customers.get(index).getLastName()+" made a deposit of $" +amount+" on Savings-"+ customers.get(index).getSavings().getAccountNum()+
            ". Balance: $" + customers.get(index).getSavings().getBalance());
        }
        log.add(" ");
    }

   /**
     * adds to log what account was pay to and from
     * @param customers customers information
     * @param recipient index of account payed to
     * @param index index of account logged in
     * @param amount amount of money
     */
    static public void logPayment(ArrayList<Customer> customers,int index,double amount,int recipient) {
        log.add(customers.get(recipient).getFirstName()+" "+customers.get(recipient).getLastName()+" received $" +amount+
        "from"+ customers.get(index).getFirstName()+customers.get(index).getLastName()+ 
        ". Balance: $" + customers.get(recipient).getSavings().getBalance());
        
         log.add(" ");
    }

    /**
     * adds to log what accounts were transfered money
     * @param customers customers information
     * @param account type of account
     * @param index index of account logged in
     * @param amount amount of money
     */
    static public void logTransfer(ArrayList<Customer> customers,String account,int index,double amount) {
        if(account.equalsIgnoreCase("credit")){
            log.add(customers.get(index).getFirstName()+" "+customers.get(index).getLastName()+" made a transfer of $" +amount+" on Checking-"+ customers.get(index).getChecking().getAccountNum()+
            "to Credit-"+customers.get(index).getCredit().getAccountNum()+". Balance: $" + customers.get(index).getCredit().getBalance());
        }
        else if(account.equalsIgnoreCase("savings")){
            log.add(customers.get(index).getFirstName()+" "+customers.get(index).getLastName()+" made a transfer of $" +amount+" on Checking-"+ customers.get(index).getChecking().getAccountNum()+
            "to Savings-"+customers.get(index).getSavings().getAccountNum()+". Balance: $" + customers.get(index).getSavings().getBalance());
        }
         log.add(" ");
    }

    /**
     * adds to log what account the manager inquired about
     * @param customers customers information
     * @param option if it was a single client or all accounts
     * @param index index of account logged in
     */
    static public void logManager(ArrayList<Customer> customers,String option,int index) {
       if(option.equalsIgnoreCase("single")){
           log.add("The manager made an inquire of " + customers.get(index).getFirstName()+ " "+customers.get(index).getLastName());
       }
       else{
           log.add("The manager made an inquire of all accounts");
       }
        log.add(" ");
    }
    
}
