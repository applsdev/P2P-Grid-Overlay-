public class PGOPeer{
    public static void main(String[] args){
	try{
	    int x = Integer.parseInt(args[0]);
	    int y = Integer.parseInt(args[1]);
	    if(x == 0 && y == 0){
		StartSocket socket = new StartSocket();
	    } else{
		GSocket socket = new GSocket(new PGOCoord(x,y));
	    }
	} catch (NumberFormatException e) { 
	    System.err.println("Usage: java PGOPeer x y");
	    System.exit(1);
	} catch(ArrayIndexOutOfBoundsException e){
	    System.err.println("Usage: java PGOPeer x y");
	    System.exit(1);
	}
    }
}