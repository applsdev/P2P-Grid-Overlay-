import java.io.*;
import java.net.*;

public class GSocketThreadOut extends Thread{
    public GSocketThreadOut(Socket socket, PGOCoord coord, GPDatagram datagram){
	this.socket = socket;
	this.coord = coord;
	this.datagram = datagram;
    }

    public void run(){
	try{
	    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

	    String inputLine;
	    while(true){
		if((inputLine = in.readLine()) != null){
		    datagram.put(inputLine);
		}
	    }
	} catch(IOException e){
	    System.err.println("Couldn't get I/O");
	    System.exit(1);
	}
    }

    private Socket socket;
    private PGOCoord coord;
    private GPDatagram datagram;
}