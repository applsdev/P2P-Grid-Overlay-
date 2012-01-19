public class GPDatagram{
    public GPDatagram(PGOCoord coord){
	set = false;
	this.coord = coord;
    }

    public void set(int dir, GSocketThreadIn in, GSocketThreadOut out){
	switch(dir){
	case(1):
	    northIn = in;
	    northOut = out;
	    break;
	case(2):
	    southIn = in;
	    southOut = out;
	    break;
	case(3):
	    eastIn = in;
	    eastOut = out;
	    break;
	case(4):
	    westIn = in;
	    westOut = out;
	    break;
	}
    }

    public synchronized String get(){
	if(!set){
	    try{
		wait();
	    } catch(InterruptedException e){}
	}
	set = false;
	int x = Integer.parseInt(data.substring(0,1));
	int y = Integer.parseInt(data.substring(1,2));
	if(coord.getX() == x && coord.getY() == y){
	    System.out.println(data);
	}
	if(coord.getX() == x){
	    if(coord.getY() < y){
		System.out.println("going east");
		eastOut.interrupt();
	    } else{
		System.out.println("going west");
		westOut.interrupt();
	    }
	} else{
	    if(coord.getX() > x){
		System.out.println("going north");
		northOut.interrupt();
	    } else{
		System.out.println("going south");
		southOut.interrupt();
	    }
	}
	return data;
    }

    public synchronized void put(String input){
	if(set){
	    try{
		wait();
	    } catch(InterruptedException e){}
	}
	set = true;
	data = input;
	int x = Integer.parseInt(input.substring(0,1));
	int y = Integer.parseInt(input.substring(1,2));
	System.out.println("this: " + coord.getX() + coord.getY());
	if(coord.getX() == x && coord.getY() == y){
	    System.out.println(input);
	} else{
	    if(coord.getX() == x){
		if(coord.getY() > y){
		    System.out.println("going west");
		    westIn.interrupt();
		} else{
		    System.out.println("going east");
		    eastIn.interrupt();
		}
	    } else{
		if(coord.getX() > x){
		    System.out.println("going north");
		    northIn.interrupt();
		} else{
		    System.out.println("going south");
		    southIn.interrupt();
		}
	    }
	}
	
    }

    private PGOCoord coord;
    private String data;
    private boolean set;

    private GSocketThreadIn northIn;
    private GSocketThreadOut northOut;
    private GSocketThreadIn southIn;
    private GSocketThreadOut southOut;
    private GSocketThreadIn eastIn;
    private GSocketThreadOut eastOut;
    private GSocketThreadIn westIn;
    private GSocketThreadOut westOut;

    public static final int NORTH = 1;
    public static final int SOUTH = 2;
    public static final int EAST = 3;
    public static final int WEST = 4;
}