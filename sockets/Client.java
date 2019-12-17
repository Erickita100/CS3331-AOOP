import java.net.*;  
import java.io.*;  

/**
 * Client class
 * creates a socket when it connects to the server and asks server for current time
 * @author ericka najera , karla chavez
 */
class Client{  
	public static void main(String args[])throws Exception{  
   	 	InetAddress ip = InetAddress.getLocalHost();//172.31.99.49
   	 	try (Socket socket = new Socket(ip,5000)) {
        	System.out.println("Socket connected...start typing");
        	DataInputStream input=new DataInputStream(socket.getInputStream());
        	DataOutputStream output=new DataOutputStream(socket.getOutputStream());
        	BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
        	String line="";
        	String answer ="";
        	while(!line.equals("goodbye")){
          	  	line=reader.readLine();
          	  	output.writeUTF(line);
            	output.flush();
            	answer=input.readUTF();
            	if(line.equalsIgnoreCase("goodbye")){
                	answer = "goodbye";
            	}
            	System.out.println("Server says: "+answer);
        	}
            	output.close();
    	}  
	}
}  