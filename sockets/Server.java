import java.net.*;  
import java.io.*;  

/**
 * Server class 
 * Server socket is created and runs until client says goodbye
 * @author ericka najera, karla chavez
 */
class Server{  
	public static void main(String args[])throws Exception{  
    
    	ServerSocket server = new ServerSocket(5000);  
    	System.out.println("Server waiting for connection");
    	Socket socket=server.accept();  
    	System.out.println("Client connected");
    	DataInputStream input=new DataInputStream(socket.getInputStream());  
    	DataOutputStream output=new DataOutputStream(socket.getOutputStream());  
    	BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));  
  
    	String line=" ";  
    	while(!line.equals("goodbye")){  
        	line=input.readUTF();  
        	System.out.println("client says: "+line);    
        	if(line.contains("time")){
            	String time = "Current time is: " + java.time.LocalTime.now();
            	output.writeUTF(time);
            	output.flush();
        	}
        	output.writeUTF(line);  
        	output.flush();  
    	}  
    	input.close();  
    	socket.close();  
    	server.close();  
	}
} 
