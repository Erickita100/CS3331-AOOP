import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author erickanajera
 */
public class ThreadDemo extends Thread{
    private Thread t;
    private String threadName;
    CurrentTime time;
    
    public ThreadDemo(String name,CurrentTime time){
        this.threadName = name;
        this.time = time;
    }
   
    public void run(){
        synchronized(time){
            
            for (int i=1;i<=3;i++){
                time.printTime(threadName);
                try {
                    time.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ThreadDemo.class.getName()).log(Level.SEVERE, null, ex);
                }
               
            }
            
        }
        
        System.out.println( threadName +" exists");
        
    }
    
    
    public void start(){
        System.out.println(threadName + " started");
        if(t==null){
            t = new Thread(this, threadName);
            t.start();
        }
        
    }
}
