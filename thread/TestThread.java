public class TestThread {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      CurrentTime time = new CurrentTime();
      ThreadDemo t1= new ThreadDemo("Thread 1",time);
      ThreadDemo t2 = new ThreadDemo("Thread 2",time);
      ThreadDemo t3 = new ThreadDemo("Thread 3",time);
      t1.start();
      t2.start();
      t3.start();
      try{
          t1.join();
          t2.join();
          t3.join();
         
          
                  
      }catch(Exception e){
          System.out.println(e);
      }
    }
    
}
