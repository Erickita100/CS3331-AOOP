
import java.io.*;
import java.util.*;


/**
 * Bank class
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

public class RunBank implements Log{
    
    static public String fileName;
    static public String updatedFile;
    static public String actions;
    static public String path;
    static public ArrayList<Customer> customers = new ArrayList<Customer>();
    static public ArrayList<double[]> prevBalances = new ArrayList<double[]>();
    
    public static void main(String[] args){
        
        path = System.getProperty("user.dir");
        //path= path.substring(0, path.length()-8);
        fileName= path+"/bankusers4.csv";
        updatedFile= path+"/updated.csv";
        actions = path+"/TransactionActions.csv";
        //path = "/Users/erickanajera/Desktop/";
        //fileName = path+"bankusers4.csv";
        //updatedFile=path+"updated.csv";
        
        readFile();
        prevBalances=balances();
        startBank();
        Log.logFile(path+"/log.txt");
        //Log.logFile("/Users/erickanajera/Desktop/log.txt");
        updateBalances(prevBalances);
        
    }/**
     * gets the header file and creates a position array for the columns
     * @param line header file string
     * @return returns a position array
     */
    public static int[] fileOutline(String[] line){
        int[] positions = new int[line.length];
        for(int i =0; i<positions.length;i++){
            if(line[i].equalsIgnoreCase("first name")){
                positions[0]=i;
            }
            else if(line[i].equalsIgnoreCase("last name")){
                positions[1]=i;
            }
            else if(line[i].equalsIgnoreCase("date of birth")){
                positions[2]=i;
            }else if(line[i].equalsIgnoreCase("identification number")){
                positions[3]=i;
            }
            else if(line[i].equalsIgnoreCase("address")){
                positions[4]=i; 
            }
            else if(line[i].equalsIgnoreCase("phone number")){
                positions[5]=i;
            }
            else if(line[i].equalsIgnoreCase("checking account number")){
                positions[6]=i;
            }
            else if(line[i].equalsIgnoreCase("savings account number")){
                positions[7]=i;
            }else if(line[i].equalsIgnoreCase("credit account number")){
                positions[8]=i;
            }
            else if(line[i].equalsIgnoreCase("checking starting balance")){
          
                positions[9]=i;
            }else if(line[i].equalsIgnoreCase("savings starting balance")){
                positions[10]=i;
            }
            else if(line[i].equalsIgnoreCase("credit starting balance")){
                positions[11]=i;
                
            }
            else if (line[i].equalsIgnoreCase("credit max")){
                positions[12]=i;
            }
     
        }
        return positions;
    }
    /**
     * old read file method reads input file and stores account information in an arrayList
     * //@param filename name of input file
     * 
     */
    /*
    public static void readFile(){
      
        try{
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while ( (line = reader.readLine())!= null) {
                String[] info = line.split(",");
                String birthdate = info[2]+info[3];
                String address = info[5]+info[6]+info[7];
                int id = Integer.parseInt(info[4]);
                Savings savings = new Savings(Integer.parseInt(info[10]),Double.parseDouble(info[13]));
                Checking checkings = new Checking(Integer.parseInt(info[9]),Double.parseDouble(info[12]));
                Credit credit = new Credit(Integer.parseInt(info[11]),Double.parseDouble(info[14]),Double.parseDouble(info[15]));
                customers.add(new Customer(info[0],info[1],birthdate,address,info[8], id,savings,checkings,credit));
                
        }
        reader.close();
        }catch(Exception e){
            System.out.println(e);
            System.out.println("Account file information not found");
        }
        
        
    }*/
    /**
     * read the transaction actions file
     * depending on the key word it does that specific transaction and prints a report for each
     */
    public static void readTransactions(){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(actions));
            String line = reader.readLine();
            String name ="";
            double amount =0;
            int index = -1;
            while ( (line = reader.readLine())!= null) {
                
                String[] info = line.split(",");
                System.out.println();
               
                if(!info[0].equalsIgnoreCase("")){
                    name = info[0];
                    index = searchAccounts(name,info[1]);
                }
                if(info[3].equalsIgnoreCase("pays")){
                    amount = convertToDouble(info[7]);
                    paySomeone(index,info[4],info[5],amount);
                    
                }else if(info[3].equalsIgnoreCase("transfers")){
                    String account = info[6];
                    amount = convertToDouble(info[7]);
                    if (account.equalsIgnoreCase("checking")){
                        transferToCheckings(index,amount);
                    }else if(account.equalsIgnoreCase("savings")){
                        transferToSavings(index,amount);
                    }
                    else{
                        transferToCredit(info[2],index,amount);
                    }
                }else if(info[3].equalsIgnoreCase("inquires")){
                    inquireBalance(info[2],index);
                }else if(info[3].equalsIgnoreCase("withdraws")){
                    String account = info[6];
                    amount = convertToDouble(info[7]);
                    withdraw(info[2],index,amount);
                }else if(info[3].equalsIgnoreCase("deposits")){
                    name = info[4];
                    index = searchAccounts(name,info[5]);
                    String account = info[6];
                    amount = convertToDouble(info[7]);
                    deposit(account,index,amount);
                    
                }
        }
        reader.close();
        }catch(Exception e){
            System.out.println(e);
            System.out.println("Transaction Actions file information not found");
        }
    }
    /**
     * new read file using the positions array
     */
    public static void readFile(){
      
        try{
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine(); 
            String[] firstLine = line.split(",");
            int[] positions = fileOutline(firstLine);
            while ( (line = reader.readLine())!= null) {
                String[] info = line.split(",");
                double savBal = convertToDouble(info[positions[12]]);
                double creBal = convertToDouble(info[positions[12]+1]);
                double cheBal = convertToDouble(info[positions[12]+1]);
                int savAcc = convertToInt(info[positions[9]]);
                int creAcc = convertToInt(info[positions[10]]);
                int cheAcc = convertToInt(info[positions[11]]);
                double max = convertToDouble(info[positions[12]+1]);
              
                String first = info[positions[0]];
                String last = info[positions[1]];
                String birth = info[positions[2]]+info[positions[2]+1];
                
                int id = convertToInt(info[positions[4]]);
                String address = info[positions[5]]+info[positions[5]+1]+info[positions[5]+2];
                String phone = info[positions[8]];
                
                Savings savings = new Savings(savAcc,savBal);
                Checking checkings = new Checking(cheAcc,cheBal);
                Credit credit = new Credit(creAcc,creBal,max);
                customers.add(new Customer(first,last,birth,address,phone,id,savings,checkings,credit));
                
        }
        reader.close();
        }catch(Exception e){
            System.out.println(e);
            System.out.println("Account file information not found");
        }
        
        
    }
    /**
     * converts a string to double, if empty  then it return 0
     * @param value string to convert
     * @return double
     */
    public static double convertToDouble(String value){
        if(value.equals(" ")){
            return 0;
        }
        return Double.parseDouble(value);
    }
    /**
     * converts a string to int, if empty  then it return 0
     * @param value string to convert
     * @return int
     */
    public static int convertToInt(String value){
        if(value.equals(" ")){
            return 0;
        }
        return Integer.parseInt(value);
    }
    /**
     * Initiates the bank, asks user to choose if they are a customer, manager or want to exit the program 
     */
    public static void startBank(){
        System.out.println("Welcome to Ericka's bank");

        String s =" ";
        boolean cont = false;
        while(!cont){
            System.out.println();
            System.out.println("a. Existing customer ");
            System.out.println("b. Manager" );
            System.out.println("c. Read Transaction actions");
            System.out.println("d. Exit");
                Scanner in = new Scanner(System.in);
                s = in.nextLine();
             
            if(s.equalsIgnoreCase("a")){
                int index = login();
                System.out.println("You have logged into the account");
                ATM(index);
            }
            else if (s.equalsIgnoreCase("b")){
                startsManager();
            }
            else if (s.equalsIgnoreCase("c")){
                readTransactions();
            }
            else if((s.equalsIgnoreCase("d"))){
                System.out.println("Thank you for choosing Ericka's bank");
                cont = true;
            }
        }
    }
    /**
     * allows user to log in with first name, checks the accounts for a valid account
     * @return  index of the account which is logged in
     */
    
    public static int login(){
            System.out.println("To access an account please type the first name on account");
            Scanner sc = new Scanner(System.in);
            String first = sc.nextLine();
            System.out.println("To access an account please type the last name on account");
            String last = sc.nextLine();
            //String[] name = input.split( " ");
            int accountIndex = -1;
            boolean loggedIn = false;
            while(!loggedIn){
                accountIndex = searchAccounts(first, last);
                if(accountIndex > -1){
                   
                    loggedIn = true;
                    return accountIndex;
                }
                else{
                    System.out.println("There are no accounts with that name please try again");
                    System.out.println("first name?");
                    first = sc.nextLine();
                    System.out.println("last name?");
                    last = sc.nextLine();
                }
            }
       
        return -1;
    }
    /**
     * Searches the arrayList for that account number
     * @param accountNum the account number to look for
     * @param account receives the string with the type of account
     * @return  the index of the account with that account number
     */
    public static int searchAccountNum(int accountNum,String account){
         int counter = 0;
        while(counter<customers.size()){
                if(account.equalsIgnoreCase("savings")){
                    if((customers.get(counter).getSavings().getAccountNum())==accountNum){
                         return counter;
                    }
                }
                else if (account.equalsIgnoreCase("credit")){
                    if(customers.get(counter).getCredit().getAccountNum()==accountNum){
                        return counter;
                    }
                }
                else{
                    if(customers.get(counter).getChecking().getAccountNum()==accountNum){
                        return counter;
                    }
                }
                counter++;
            }
        return -1;
    }
    /**
     * searches the array for that name in the accounts and return index of the found account
     * @param name name to search for in the array list
     * @return the index of the account with that name
     */
    public static int searchAccounts( String first,String last){
        int counter = 0;
        while(counter<customers.size()){
                if((customers.get(counter).getFirstName().equalsIgnoreCase(first)) && (customers.get(counter).getLastName().equalsIgnoreCase(last))){
                    return counter;
                }
                counter++;
            }
        return -1;
        
    }
    /**
     * initiates the manager profile to choose how to inquire the information of the account 
     */
    public static void startsManager(){
        System.out.println("A. inquire account by name");
        System.out.println("B. inquire account by type/number");
        System.out.println("C. inquire info of all account");
        System.out.println("D. create new user");
        System.out.println("E. print statements");
        boolean validOption = false;
        String option = " ";
        Scanner in = new Scanner(System.in);
        while(!validOption){
        option = in.nextLine();
        if(option.equalsIgnoreCase("a")){
            validOption = true;
            System.out.println("Who's account would you like to inquire about?");
            int index = login();
            customers.get(index).inquiry();
            Log.logManager(customers,"single",index);
            
        }
        else if(option.equalsIgnoreCase("b")){
             validOption = true;
            String chosen =chooseAccount();
            int index=managerLogin(chosen);
            customers.get(index).inquiry();
            Log.logManager(customers,"single", index);
        }
        else if(option.equalsIgnoreCase("c")){
             validOption = true;
            readAccount();
            Log.logManager(customers, " ", 0);
              
        }
        else if(option.equalsIgnoreCase("d")){
             validOption = true;
            createUser();
        }
        else if(option.equalsIgnoreCase("e")){
            validOption = true;
            System.out.println("Type the first name on account");
            Scanner sc = new Scanner(System.in);
            String first = sc.nextLine();
            System.out.println("Type last name on account");
            String last = sc.nextLine();
            int accountIndex = -1;
            boolean loggedIn = false;
            while(!loggedIn){
                accountIndex = searchAccounts(first, last);
                if(accountIndex > -1){
                   
                    loggedIn = true;
                    
                    BankStatement.writeStatement(path+customers.get(accountIndex).getLastName()+customers.get(accountIndex).getFirstName()+".csv",accountIndex);
                    //return accountIndex;
                }
                else{
                    System.out.println("There are no accounts with that name please try again");
                    System.out.println("first name?");
                    first = sc.nextLine();
                    System.out.println("last name?");
                    last = sc.nextLine();
                }
            }
       
            }
        }
    }
    /**
     * creates a new user, asks user for input for account information
     */
    public static void createUser(){
        System.out.println("First Name?");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        String first = input; 
        System.out.println("last Name?");
        input = sc.nextLine();
        String last = input; 
        System.out.println("Date of birth?");
        input = sc.nextLine();
        String dob = input;
        System.out.println("Address?");
        input = sc.nextLine();
        String add = input;
        System.out.println("Phone?");
        input = sc.nextLine();
        String phone = input;
        System.out.println("Savings created");
        Savings savings = new Savings( customers.get(customers.size()-1).getSavings().getAccountNum()+1,0.0);
        System.out.println("Would you like a checkings account? yes or no");
        input = sc.nextLine();
        Checking checkings = new Checking();
        if(input.contains("yes")){
             checkings = new Checking( customers.get(customers.size()-1).getChecking().getAccountNum()+1,0.0);
        }
        else{
             checkings = new Checking( customers.get(customers.size()-1).getChecking().getAccountNum()+1,0.0);
        }
        System.out.println("Would you like a credit account? yes or no");
        input = sc.nextLine();
        Credit credit = new Credit();
        if(input.contains("yes")){
            credit = new Credit( customers.get(customers.size()-1).getCredit().getAccountNum()+1,0.0,500);
        }
        else{
        credit = new Credit( customers.get(customers.size()-1).getCredit().getAccountNum()+1,0.0,500);
        }
        customers.add(new Customer(first,last,dob,add, phone, customers.size()+1,savings,checkings,credit));
        System.out.println("customer account created");
        prevBalances.add(new double[]{customers.get(customers.size()-1).getChecking().getBalance(),customers.get(customers.size()-1).getSavings().getBalance(),customers.get(customers.size()-1).getCredit().getBalance()});

        
    }
    /**
     * Asks user to choose checking, savings or credit account
     * @return 
     */
    public static String chooseAccount(){
        System.out.println("What account type?");
        System.out.println("a. checking, b. Savings, or c. credit");
        String option = " ";
        boolean validOption = false;
        while(!validOption){
            try{
            Scanner s = new Scanner(System.in);
                option = s.nextLine();
                    if(option.equalsIgnoreCase("A")|| option.equalsIgnoreCase("B")||option.equalsIgnoreCase("c")){
                        validOption = true;
                    }
                    else{
                        System.out.println("Choose A or B or C");
                    }
                }catch(Exception e){
                System.out.println("Input not a option, restart");
                }
        }
            if(option.equalsIgnoreCase("a")){
                return "checking";
            }
            else if (option.equalsIgnoreCase("b")){
                return "savings";
            }
            else{
                return "credit";
            }
    }
    /**
     * Asks the manager to type in account number to inquire about
     * @param account type of account 
     * @return the index of the account to be inquired
     */
    public static int managerLogin(String account){
            System.out.println("What is the account number");
            boolean valid = false;
            int in =0;
            Scanner sc = new Scanner(System.in);
            while(!valid){
                try{
                  
                    sc = new Scanner(System.in);
                    in = sc.nextInt();
                    valid = true;

                }catch(InputMismatchException e){
                    System.out.println("Input not a number");
                    
                }
            }
            int accountIndex = -1;
            boolean loggedIn = false;
            while(!loggedIn){
                    accountIndex = searchAccountNum(in,account);
                if(accountIndex > -1){
                    
                    loggedIn = true;
                    return accountIndex;

                }
                else{
                    System.out.println("There are no accounts with that name please try again");
                    in = sc.nextInt();
                }

            }
       
        return -1;
    }
    /**
     * depending on the type of account it calls the method respecting the account 
     * @param index the index of the account logged in
     */
    public static void ATM(int index){
        int option =0;
        boolean cont= false;
            String account = chooseAccount();
            if(account.equalsIgnoreCase("checking")){
                checkingsAccount(index);
            }else if(account.equalsIgnoreCase("savings")){
                savingsAccount(index);
            }else{
                creditAccount(index);
            }
           
    }
    /**
     * gives the user the transaction to do in the checking account
     * @param index index of the logged in account
     */
    public static void checkingsAccount( int index){
            int option =0;
            String account = "checking";
            System.out.println("What would you desire to do");
            System.out.println("1. Check your Balance");
            System.out.println("2. Pay someone");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5.Transfer");
            System.out.println("6.Log out");
            Scanner in = new Scanner(System.in);
            try{
            option = in.nextInt();
            }catch(Exception e){
                System.out.println("Input not a number, restart");
            }
            //returns balance of the account
            if(option ==1){
                inquireBalance(account,index);
                Log.logInquiry(customers, account, index);
              
            }else if(option ==2){
                paySomeone(index,"","",0);
                
            //deposits amount into account
            }else if(option==3){
                deposit(account,index,0);
                
            //withdraws money from account only if there are enough funds
            }else if(option ==4){
                withdraw(account,index,0);
            }
            else if (option ==5){
                System.out.println("transfer to A. savings or B. credit");
                in = new Scanner(System.in);
                String ans = in.nextLine();
                if(ans.equalsIgnoreCase("a")){
                    transferToSavings(index,0);
                }else if(ans.equalsIgnoreCase("b")){
                    transferToCredit("checking",index,0);
                }
            }

    } 
    /**
     * gives the user the transaction to do in the savings account
     * @param index index of logged in account
     */
    public static void savingsAccount( int index){
            int option =0;
            String account = "savings";
            System.out.println("What would you desire to do");
            System.out.println("1. Check your Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4.Transfer");
            System.out.println("5.Log out");
            Scanner in = new Scanner(System.in);
            try{
            option = in.nextInt();
            }catch(Exception e){
                System.out.println("Input not a number, restart");
            }
            //returns balance of the account
            if(option ==1){
                inquireBalance(account,index);
            }    
                //log.add(accounts[index].getFirstName()+ " "+accounts[index].getLastName()+" checked the balance");
           
            //deposits amount into account
            else if(option==2){
                deposit(account,index,0);
                
            //withdraws money from account only if there are enough funds
            }else if(option ==3){
                withdraw(account,index,0);
            }
            else if(option==4){
                transferToSavings(index,0);
            }
    } 
    /**
     * gives the user the transaction to do in the credit account
     * @param index index of logged in account
     */
    public static void creditAccount( int index){
            int option =0;
            String account = "credit";
            System.out.println("What would you desire to do");
            System.out.println("1. Check your Balance");
            System.out.println("2.Transfer");
            System.out.println("3.Log out");
            Scanner in = new Scanner(System.in);
            try{
            option = in.nextInt();
            }catch(Exception e){
                System.out.println("Input not a number, restart");
            }
            if(option ==1){
                inquireBalance(account,index);
                
            }else if (option ==2){
                transferToCredit("checking",index,0);
            }
    }
    /**
     * prints the balance of the account given
     * @param account type of account
     * @param index index of the logged in customer
     */
    public static void inquireBalance( String account, int index){
                if(account.equalsIgnoreCase("checking")){
                    System.out.println(customers.get(index).getFirstName()+" "+customers.get(index).getLastName()+" current balance is $"+customers.get(index).getChecking().getBalance());
                }
                else if (account.equalsIgnoreCase("savings")){
                    System.out.println(customers.get(index).getFirstName()+" "+customers.get(index).getLastName()+" current balance is $"+customers.get(index).getSavings().getBalance());
                }
                else{
                    System.out.println(customers.get(index).getFirstName()+" "+customers.get(index).getLastName()+" current balance is $"+customers.get(index).getCredit().getBalance());
                }
                Log.logInquiry(customers, account, index);
    }
    /**
     * Asks user to who they wish to pay and deposits into the checking account
     * @param index index of the logged in customer
     */
    public static void paySomeone(int index,String first,String last, double amount){
        String name ="";
        int person = -1;
        if(first.isEmpty() && last.isEmpty()){
         System.out.println("Please input the name of the person you would like to pay to");
         Scanner in = new Scanner(System.in);
                name = in.nextLine();
                boolean found = false;
                while (!found){
                    name = in.nextLine();
                    String[] input = name.split(" ");
                    person = searchAccounts(input[0],input[1]);
                    
                    if(person>-1 && person !=index){
                        found = true;
                    }
                    else if(person ==-1 || person ==index){
                        System.out.println("Account not found, re-enter name");
                        name = in.nextLine();
                    }
                }
        }
        else{
            person = searchAccounts(first,last);
            name = first+" "+last;
        }
        if(amount==0){
                System.out.println("How much would you like to pay "+ name);
                 amount = getAmount();
        }
                if(amount>0){
                    String oldBal = Double.toString(customers.get(person).getChecking().getBalance());
                    customers.get(person).getChecking().deposit(amount);
                    
                    System.out.println(name+ "'s old balance was "+oldBal);
                    System.out.println(customers.get(index).getFirstName()+" "+customers.get(index).getLastName()+" payed $" +amount+ " to " +name+ "'s account");
                    System.out.println(name+ "'s new balance was "+customers.get(person).getChecking().getBalance());
                    }
                else{
                    
                    System.out.println("Transcation was cancelled");
                    
                }
    }
    /**
     * Asks user how much to deposit and add moneys into that account
     * @param account type of account
     * @param index index of the logged in customer
     */
    public static void deposit(String account, int index,double value){
        double amount =0;
        if(value==0){
            System.out.println("How much would you like to deposit?");
             amount = getAmount();
        }else{
            amount=value;
        }
        double prev = 0.0;
                if(account.equalsIgnoreCase("checking")){
                    prev =customers.get(index).getChecking().getBalance();
                    if(amount>0){
                        customers.get(index).getChecking().deposit(amount);
                        System.out.println( customers.get(index).getFirstName()+" "+customers.get(index).getLastName()+" previous balance was $"+ prev);
                        System.out.println(customers.get(index).getFirstName()+" "+customers.get(index).getLastName()+" deposited " +amount);
                        System.out.println(customers.get(index).getFirstName()+" "+customers.get(index).getLastName()+" new balance is $"+customers.get(index).getChecking().getBalance());
                        Log.logDeposit(customers, account, index, amount);
                    }
                    else{
                        System.out.println("Deposit cancelled");
                        
                    }
                }else {
                    prev = customers.get(index).getSavings().getBalance();

                    if(amount>0){
                        customers.get(index).getSavings().deposit(amount);
                        System.out.println(customers.get(index).getFirstName()+" "+customers.get(index).getLastName()+" previous balance was $"+ prev);
                        System.out.println(customers.get(index).getFirstName()+" "+customers.get(index).getLastName()+" deposited " +amount);
                        System.out.println(customers.get(index).getFirstName()+" "+customers.get(index).getLastName()+" new balance is $"+customers.get(index).getSavings().getBalance());
                        Log.logDeposit(customers, account, index, amount);
                    }
                    else{
                        System.out.println("Deposit cancelled");
                        
                    }
                }
    }
    /**
     * asks user how much they wish to withdraw and remove amount from specific account
     * @param account type of account
     * @param index index of customer logged in
     */
    public static void withdraw(String account, int index,double val){
        double amount = 0;
        if(val==0){
            System.out.println("How much would you like to withdraw?");
            amount = getAmount();
        }else{
            amount = val;
        }
        if(account.equalsIgnoreCase("checking")){
                double prev = customers.get(index).getChecking().getBalance();
                if(amount>0){
                    customers.get(index).getChecking().withdraw(amount);
                    System.out.println(customers.get(index).getFirstName()+" "+customers.get(index).getLastName()+" previous balance was $"+ prev);
                    System.out.println(customers.get(index).getFirstName()+" "+customers.get(index).getLastName()+" withdrawled " +amount);
                    System.out.println(customers.get(index).getFirstName()+" "+customers.get(index).getLastName()+" new balance is $"+customers.get(index).getChecking().getBalance());
                    Log.logWithdral(customers, account, index, amount);
                }
                else{
                    System.out.println("Withdraw cancelled");
                }
        }
        else{
               double prev = customers.get(index).getSavings().getBalance();
                if(amount>0){
                    customers.get(index).getSavings().withdraw(amount);
                    System.out.println(customers.get(index).getFirstName()+" "+customers.get(index).getLastName()+" previous balance was $"+ prev);
                    System.out.println(customers.get(index).getFirstName()+" "+customers.get(index).getLastName()+" withdrawled " +amount);
                    System.out.println(customers.get(index).getFirstName()+" "+customers.get(index).getLastName()+" new balance is $"+customers.get(index).getSavings().getBalance());
                    Log.logWithdral(customers, account, index, amount);
                    //log.add(accounts[index].getFirstName()+ " "+accounts[index].getLastName()+" withdrawled $" +amount);
                }
                else{
                    System.out.println("Withdraw cancelled");
                    //log.add(accounts[index].getFirstName()+ " "+accounts[index].getLastName()+" transcation was cancelled");
                }     
        }
    }
    /**
     * asks user how much to transfer and add money to credit account and removes checking
     * @param account
     * @param index index of customer logged in
     * @param val
     */
    public static void transferToCredit(String account,int index,double val){
        double amount = 0;
        if (account.equalsIgnoreCase("checking")){
            if(val==0){
                System.out.println("How much would you like to transfer from checkings to credit?");
                amount = getAmount();
            }else{
                amount =val;
            }
        double prevCheckings =customers.get(index).getChecking().getBalance();
        double prevCredit = customers.get(index).getCredit().getBalance();
        double total = prevCredit + amount;
        double valid = prevCheckings - amount;
        if(amount>0 && total<=0 &&valid>=0){
                    customers.get(index).getCredit().deposit(amount);
                    customers.get(index).getChecking().withdraw(amount);
                    System.out.println(customers.get(index).getFirstName()+customers.get(index).getLastName()+" previous balance in your credit account was $"+ prevCredit);
                    System.out.println(customers.get(index).getFirstName()+customers.get(index).getLastName()+" transfered " +amount);
                    System.out.println(customers.get(index).getFirstName()+customers.get(index).getLastName()+" new balance in credit is $"+customers.get(index).getCredit().getBalance());
                    System.out.println(customers.get(index).getFirstName()+customers.get(index).getLastName()+" new balance in checkings is $"+customers.get(index).getChecking().getBalance());
                    Log.logTransfer(customers,"credit", index, amount);
                        //log.add(accounts[index].getFirstName()+ " "+accounts[index].getLastName()+" deposited $" +amount);
                }
                else{
                    System.out.println("Transfer to credit cancelled: insufficient funds in Checkings");
                        //log.add(accounts[index].getFirstName()+ " "+accounts[index].getLastName()+" transcation was cancelled");

                }
        }else{
            if(val==0){
                System.out.println("How much would you like to transfer from savings to credit?");
                amount = getAmount();
            }else{
                amount =val;
            }
        double prevSavings =customers.get(index).getSavings().getBalance();
        double prevCredit = customers.get(index).getCredit().getBalance();
        double total = prevCredit + amount;
        double valid = prevSavings - amount;
        if(amount>0 && total<=0 &&valid>=0){
                    customers.get(index).getCredit().deposit(amount);
                    customers.get(index).getSavings().withdraw(amount);
                    System.out.println(customers.get(index).getFirstName()+customers.get(index).getLastName()+" previous balance in your credit account was $"+ prevCredit);
                    System.out.println(customers.get(index).getFirstName()+customers.get(index).getLastName()+" transfered " +amount);
                    System.out.println(customers.get(index).getFirstName()+customers.get(index).getLastName()+" new balance in credit is $"+customers.get(index).getCredit().getBalance());
                    System.out.println(customers.get(index).getFirstName()+customers.get(index).getLastName()+" new balance in checkings is $"+customers.get(index).getSavings().getBalance());
                    Log.logTransfer(customers,"credit", index, amount);
                        //log.add(accounts[index].getFirstName()+ " "+accounts[index].getLastName()+" deposited $" +amount);
                }
                else{
                    System.out.println("Transfer to credit cancelled: insufficient funds in Savings");
                        //log.add(accounts[index].getFirstName()+ " "+accounts[index].getLastName()+" transcation was cancelled");

                }
        }
            
    }

    /**
     * asks user how much to transfer and add money to credit account and removes checking
     * @param index index of customer logged in
     * @param val
     */
    public static void transferToCheckings(int index, double val){
        double amount =0;
        if(val==0){
            System.out.println("How much would you like to transfer from savings to credit?");
            amount = getAmount();
        }else{
            amount =val;
        }
        double prevCheckings =customers.get(index).getChecking().getBalance();
        double prevCredit = customers.get(index).getCredit().getBalance();
        double total = prevCredit + amount;
        double valid = prevCheckings - amount;
        if(amount>0 && total<=0 &&valid>=0){
                    customers.get(index).getCredit().deposit(amount);
                    customers.get(index).getChecking().withdraw(amount);
                    System.out.println(customers.get(index).getFirstName()+customers.get(index).getLastName()+" previous balance in your credit account was $"+ prevCredit);
                    System.out.println(customers.get(index).getFirstName()+customers.get(index).getLastName()+" transfered " +amount);
                    System.out.println(customers.get(index).getFirstName()+customers.get(index).getLastName()+" new balance in credit is $"+customers.get(index).getCredit().getBalance());
                    System.out.println(customers.get(index).getFirstName()+customers.get(index).getLastName()+" new balance in checkings is $"+customers.get(index).getChecking().getBalance());
                    Log.logTransfer(customers,"credit", index, amount);
                        //log.add(accounts[index].getFirstName()+ " "+accounts[index].getLastName()+" deposited $" +amount);
                }
                else{
                    System.out.println("Transfer to checking cancelled: insufficient funds in Savings");
                        //log.add(accounts[index].getFirstName()+ " "+accounts[index].getLastName()+" transcation was cancelled");

                }
            
    }
    /**
     * asks user how much to transfer and add money to savings account and removes checking
     * @param index index of customer logged in
     */
    public static void transferToSavings(int index,double val){
            double amount = 0;
            if(val==0){
                System.out.println("How much would you like to transfer from checkings to Savings?");
                amount = getAmount();
            }else{
                amount = val;
            }
            double prevCheckings =customers.get(index).getChecking().getBalance();
            double prevSavings = customers.get(index).getSavings().getBalance();
            double total = prevCheckings - amount;
            if(amount>0 && total>=0){
                        customers.get(index).getSavings().deposit(amount);
                        customers.get(index).getChecking().withdraw(amount);
                        System.out.println(customers.get(index).getFirstName()+customers.get(index).getLastName()+" previous balance in your savings account was $"+ prevSavings);
                        System.out.println(customers.get(index).getFirstName()+customers.get(index).getLastName()+" transfered " +amount);
                        System.out.println(customers.get(index).getFirstName()+customers.get(index).getLastName()+" new balance in savings is $"+customers.get(index).getSavings().getBalance());
                        System.out.println(customers.get(index).getFirstName()+customers.get(index).getLastName()+" new balance in checkings is $"+customers.get(index).getChecking().getBalance());
                        Log.logTransfer(customers, "savings", index, amount);
                            //log.add(accounts[index].getFirstName()+ " "+accounts[index].getLastName()+" deposited $" +amount);
                    }
                    else{
                        System.out.println("Transfer to savings cancelled: insufficient funds in Checkings");
                            //log.add(accounts[index].getFirstName()+ " "+accounts[index].getLastName()+" transcation was cancelled");

                    }
        
        
    }
    /**
     * asks user for a valid amount of money
     * @return returns a double with the amount input by user
     */
    public static double getAmount(){
        boolean valid = false;
        double amount = 0.0;
        while(!valid){
            Scanner in = new Scanner(System.in);
            try{
                amount = in.nextDouble();
                valid = true;
                
            }catch(InputMismatchException e){
                System.out.println("input not a valid number");
            }
        }
        return amount;
    }
    /**
     * reads the account info of all accounts
     */
    public static void readAccount(){
        
        try{
            BufferedReader reader = new BufferedReader(new FileReader(updatedFile));
            String line = reader.readLine();
            while ( (line = reader.readLine())!= null) {
                System.out.println(line);
        }
        reader.close();
        }catch(Exception e){
            System.out.println(e);
            System.out.println("Account file information not found");
        }
    }
    /**
     * saves all the balances of the customer's account into a 3d array
     * @return a double 3d array with balances of all accounts
     */
    public static ArrayList<double[]> balances(){
        ArrayList<double[]> balances = new ArrayList<double[]>();
        //double[][] balances = new double[customers.size()][3];
        for(int i = 0;i<customers.size();i++){
            //double bal[]= new double{customers.get(i).getChecking().getBalance(),customers.get(i).getSavings().getBalance()customers.get(i).getCredit().getBalance())};
            balances.add(new double[]{customers.get(i).getChecking().getBalance(),customers.get(i).getSavings().getBalance(),customers.get(i).getCredit().getBalance()});
//balances[i][1]=customers.get(i).getSavings().getBalance();
           // balances[i][2]=customers.get(i).getCredit().getBalance();
        }
        return balances;
    }
    /**
     * traverses both arrays of balances both old and new balances and updates the file if there is a change
     * @param oldBal receives the old balances to compare to new ones
     */
    public static void updateBalances(ArrayList<double[]> oldBal){
        ArrayList<double[]> newBal = balances();
        for(int i =0;i<newBal.size();i++){
            for(int j = 0;j<3;j++){
                if(newBal.get(i)[j]!=oldBal.get(i)[j]){
                    //System.out.println(newBal[i][j]+" "+oldBal[i][j]);
                    writeUpdatedFile(String.valueOf(oldBal.get(i)[j]),String.valueOf(newBal.get(i)[j]));
                }
            }
        }
        
    }
    /**
     * updates account file by replacing the old balance with the new balance then a writer will rewrite the file
     * @param oldBal old balance
     * @param newBal new balance to be replaced
     */
    public static void writeUpdatedFile(String oldBal, String newBal){
        File updated = new File(updatedFile);
        String old = " ";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(updated));
            String line = reader.readLine();
            //int counter =0;
            while(line!=null){
                old= old + line+System.lineSeparator();
                line = reader.readLine();
                //counter++;
            }
            
            String newLine = old.replace(oldBal,newBal);
           //System.out.println(newLine);
            FileWriter writer = new FileWriter(updated);
            writer.write(newLine);
            reader.close();
            writer.close();
        } catch (Exception ex) {
            System.out.println("updated file not found");
        } 
        
    }
    

    
    
    
}

