import java.io.*;
import java.net.*;

public class FileTransfer{
    public static void main(String[] args){
	try{
	    Socket socket = new Socket("localhost", 9000);
	    System.out.println("connection established");
	    
	    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
	    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

	    System.out.print("To send: ");
	    String input;
	    while(true){
		input = stdIn.readLine();
		out.println(input);
		System.out.println(input);
		System.out.print("To send: ");
	    }
	} catch(UnknownHostException e){
	    e.printStackTrace();
	} catch(IOException e){
	    e.printStackTrace();
	}
    }
}