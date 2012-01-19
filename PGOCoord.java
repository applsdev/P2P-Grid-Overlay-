public class PGOCoord{
    public PGOCoord(int x, int y){
	this.x = x;
	this.y = y;
    }

    public int getX(){
	return x;
    }

    public int getY(){
	return y;
    }

    public boolean equals(PGOCoord other){
	if(x == other.getX() && y == other.getY()){
	    return true; 
	} else{
	    return false;
	}
    }

    private final int x;
    private final int y;
}