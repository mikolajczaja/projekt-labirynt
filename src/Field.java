
public class Field {

	private int positionX;
	private int positionY;
	
	private int counter=0;
	private int type=0;  //:INFO 1-sciana, 0- puste, -1-poczatek, -2-koniec
	private boolean junction=false;
	
	Field(int positionX, int positionY,int counter,int type){
		this.positionX=positionX;
		this.positionY=positionY;
		this.counter=counter;
		this.type=type;
	}
	
	
	int getPositionX(){
		return this.positionX;
	}
	int getPositionY(){
		return this.positionY;
	}
	int getCounter(){
		return this.counter;
	}
	int getType(){
		return this.type;
	}
	boolean getJunction(){
		return this.junction;
	}
	
	void setPositionX(int positionX){
		this.positionX=positionX;
	}
	void setPositionY(int positionY){
		this.positionY=positionY;
	}
	void setCounter(int counter){
		this.counter=counter;
	}
	void setType(int type){
		this.type=type;
	}
	void setJunction(boolean junction){
		this.junction=junction;
	}
	
	
	void incrementCounter(){
		this.counter++;
	}
	
	@Override
	public String toString(){
		return this.positionX+","+this.positionY;
	}
}
