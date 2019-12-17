
/**
 *abstract person class
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
public abstract class Person {
    private int id = 0;
    private String birthDate= " ";
    private String firstName =" ";
    private String lastName= " ";
    private String address= " ";
    private String phone= " ";
    
    public Person(){
        
    }
    /**
     * Person class
     * @param firstName first name
     * @param lastName last name
     * @param birthDate birth date
     * @param address address
     * @param phone phone number
     * @param id identification number
     */
    public Person(String firstName,String lastName,String birthDate,String address, String phone, int id){
        this.firstName=firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.phone = phone;
        this.id = id;
              
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getBirthDate(){
        return birthDate;
    }
    public void setBirthDate(String bd){
        this.birthDate = bd;
    }
    public String getFirstName(){
        return firstName;
    }
    public void setFirstName(String name){
        this.firstName = name;
    }
    public String getLastName(){
        return lastName;
    }
    public void setLastName(String name){
        this.lastName = name;
    }
    public String getAddress(){
        return address;
    }
    public void setAddress(String add){
        this.address = add;
    }
    public String getPhone(){
        return phone;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
}
