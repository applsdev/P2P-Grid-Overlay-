import java.net.*;
import java.io.*;

public class StartSocket extends GSocket{
    public StartSocket(){
	super(new PGOCoord(0,0));
	System.out.println("I'm a special socket!");
	getFile();
    }

    private void getFile(){
	try{
	    ServerSocket serverSocket = new ServerSocket(9000);
	    Socket socket = serverSocket.accept();
	    System.out.println("connection established");

	    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    String inputLine;
	    while((inputLine = in.readLine()) != null){
		System.out.println("Received: " + inputLine);
		super.newInput(inputLine);
	    }
	} catch(IOException e){
	    System.err.println("Couldn't get I/O");
	    System.exit(1);
	}
    }
}