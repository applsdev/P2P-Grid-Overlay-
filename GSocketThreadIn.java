import java.io.*;
import java.net.*;
import java.util.concurrent.locks.*;

public class GSocketThreadIn extends Thread{
    public GSocketThreadIn(Socket socket, PGOCoord coord, GPDatagram datagram){
	this.socket = socket;
	this.coord = coord;
	this.datagram = datagram;
    }
    
    public void run(){
	try{
	    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	    while(true){
		String received = datagram.get();
		out.println(received);
		System.out.println(received);
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