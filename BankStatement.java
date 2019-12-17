
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;



/**
 *Bank Statement class generates a bank statement for a user
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
public class BankStatement implements Log {
    
    public static ArrayList<String> trans;
    /**
     * collects customer info and formats the file
     * @param cus customers array list
     * @param index index of customer to print statement 
     */
    public static void getCustomerInfo(ArrayList<Customer> cus,int index){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        trans.add("Bank Statement," + "Date: "+dtf.format(now));
        trans.add("First Name Last Name:, "+ cus.get(index).getFirstName()+ " "+cus.get(index).getLastName());
        trans.add("Date of Birth:, "+ cus.get(index).getBirthDate());
        trans.add("Mailing address:, " + cus.get(index).getAddress());
        trans.add("Phone Number:, "+ cus.get(index).getPhone());
        String iden = Integer.toString(cus.get(index).getId());
        trans.add("Identification number:, "+ iden);
        trans.add( " ");
        trans.add("Checkings");
        trans.add(" ,Account num:, "+ cus.get(index).getChecking().getAccountNum());
        trans.add(" ,Starting balance:, "+"$"+ RunBank.prevBalances.get(index)[0]);
        trans.add("Savings ");
        trans.add(" ,Account num:, "+ cus.get(index).getSavings().getAccountNum());
        trans.add(" ,Starting balance:, "+"$"+ RunBank.prevBalances.get(index)[1]);
        trans.add("Credit");
        trans.add(" ,Account num:, "+ cus.get(index).getCredit().getAccountNum());
        trans.add(" ,Starting balance:, "+ "$"+RunBank.prevBalances.get(index)[2]);
        
        
    }
    /**
     * writes a bank statement
     * @param file name of file
     * @param index index of customer being printed
     */
    public static void writeStatement(String file, int index){
       trans = new ArrayList<String>();
       getCustomerInfo(RunBank.customers,index);
       getTrans(RunBank.customers,index);
       String[] list = new String[trans.size()];
       list = trans.toArray(list);
       PrintWriter writer;
        try {
            writer = new PrintWriter(file, "UTF-8");
            int counter =0;
            while(counter < list.length){
            writer.println(list[counter]);
            counter++;
            }
            writer.close();
            System.out.println("Bank Statement written");
        } catch (FileNotFoundException ex) {
            System.out.println();
        } catch (UnsupportedEncodingException ex) {
            //Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        } 
       
    }
    /**
     * searches the log array list for transactions made from that user
     * @param cus customers info array list
     * @param index  index of user
     */
    public static void getTrans(ArrayList<Customer> cus,int index){
        int counter = 0;
        String first = cus.get(index).getFirstName();
        String last = cus.get(index).getLastName();
        trans.add( " ");
        trans.add( "Transactions");
        while(counter<log.size()){
                if(log.get(counter).contains(first) && log.get(counter).contains(last)){
                    trans.add(","+log.get(counter));
                    //System.out.println(log.get(counter));
                }
                counter++;
            }
        trans.add( "Ending Balances:");
        trans.add("Ending checking balance: , "+"$"+cus.get(index).getChecking().getBalance());
        trans.add("Ending savings balance: , "+"$"+cus.get(index).getSavings().getBalance());
        trans.add("Ending credit balance: , "+"$"+cus.get(index).getCredit().getBalance());
    }
    
    
}
