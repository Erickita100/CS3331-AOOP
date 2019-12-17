public class CurrentTime {
   
     
    public void printTime(String name){
        try{
             synchronized(this){
               System.out.println(name + " " + java.time.LocalTime.now());
                Thread.sleep(5000);
                this.notify();
           }
           // System.out.println(System.currentTimeMillis());
            //Thread.sleep(5000);
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
