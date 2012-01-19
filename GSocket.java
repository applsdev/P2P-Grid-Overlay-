import java.util.*;
import java.net.*;
import java.io.*;

public class GSocket{
    public GSocket (PGOCoord coord){
	this.coord = coord;
	datagram = new GPDatagram(coord);
	try{
	    this.connect();
	} catch(IOException e){
	    e.printStackTrace();
	    System.exit(1);
	}
    }

    public void newInput(String input){
	datagram.put(input);
    }

    private void connect() throws IOException{
	int port = getPort(coord); //separate ports for each client
	ServerSocket serverSocket = new ServerSocket(port);

	boolean north = false;
	boolean south = false;
	boolean east = false;
	boolean west = false;

	if(coord.getX() == 1){
	    PGOCoord northCoord = new PGOCoord(coord.getX()-1, coord.getY());
	    int northPort = getPort(northCoord);

	    Timer t1 = new Timer();
	    Accept a = new Accept(northPort, northCoord, "north", t1, datagram);
	    t1.schedule(a, 0, 10);

	    northIn = new GSocketThreadIn(serverSocket.accept(), coord, datagram);
	    northIn.start();
	    northOut= a.getThread();
	    System.out.println("accepted on north");
	    north = true;
	}

	if(coord.getX() == 0){
	    PGOCoord southCoord = new PGOCoord(coord.getX()+1, coord.getY());
	    int southPort = getPort(southCoord);

	    Timer t2 = new Timer();
	    Accept a = new Accept(southPort, southCoord, "south", t2, datagram);
	    t2.schedule(a, 0, 10);

	    southIn = new GSocketThreadIn(serverSocket.accept(), coord, datagram);
	    southIn.start();
	    southOut = a.getThread();
	    System.out.println("accepted on south");
	    south = true;
	}

	if(coord.getY() > 0){
	    PGOCoord westCoord = new PGOCoord(coord.getX(), coord.getY()-1);
	    int westPort = getPort(westCoord);

	    Timer t3 = new Timer();
	    Accept a = new Accept(westPort, westCoord, "west", t3, datagram);
	    t3.schedule(a, 0, 10);
	    westOut = a.getThread();

	    westIn = new GSocketThreadIn(serverSocket.accept(), coord, datagram);
	    westIn.start();
	    System.out.println("accepted on west");
	    west = true;
	}

	if(coord.getY() < 2){
	    PGOCoord eastCoord = new PGOCoord(coord.getX(), coord.getY()+1);
	    int eastPort = getPort(eastCoord);

	    Timer t4 = new Timer();
	    Accept a = new Accept(eastPort, eastCoord, "east", t4, datagram);
	    t4.schedule(a, 0, 10);
	    
	    eastIn = new GSocketThreadIn(serverSocket.accept(), coord, datagram);
	    eastIn.start();
	    eastOut = a.getThread();
	    System.out.println("accepted on east");
	    east = true;
	}

	if(north){
	    datagram.set(1, northIn, northOut);
	    System.out.println("set north");
	}
	if(south){
	    datagram.set(2, southIn, southOut);
	    System.out.println("set south");
	}
	if(east){
	    datagram.set(3, eastIn, eastOut);
	    System.out.println("set east");
	}
	if(west){
	    datagram.set(4, westIn, westOut);
	    System.out.println("set west");
	}
    }

    private int getPort(PGOCoord newCoord){
	return 8000 + 3 * newCoord.getX() + newCoord.getY();
    }

    private PGOCoord coord;
    private GPDatagram datagram;

    protected GSocketThreadIn northIn;
    protected GSocketThreadOut northOut;
    protected GSocketThreadIn southIn;
    protected GSocketThreadOut southOut;
    protected GSocketThreadIn eastIn;
    protected GSocketThreadOut eastOut;
    protected GSocketThreadIn westIn;
    protected GSocketThreadOut westOut;

    private class Accept extends TimerTask{
	Accept(int port, PGOCoord coord, String dir, Timer t, GPDatagram datagram){
	    this.port = port;
	    this.coord = coord;
	    this.dir = dir;
	    this.t = t;
	    this.datagram = datagram; 
	}

	public void run(){
	    try{
		Socket socket = new Socket("localhost", port);
		out = new GSocketThreadOut(socket, coord, datagram);
		new Thread(out).start();
		System.out.println("connected on " + dir);
		t.cancel();
	    } catch(ConnectException e){
	    } catch(UnknownHostException e){
		System.err.println("Don't know about localhost??");
		System.exit(1);
	    } catch(IOException e){
		System.err.println("Couldn't establish I/O");
		System.exit(1);
	    }
	}

	public GSocketThreadOut getThread(){
	    return out;
	}

	private int port;
	private PGOCoord coord;
	private String dir;
	private Timer t;
	private GPDatagram datagram;
	private GSocketThreadOut out;
    }
}