
public class Field {

	private int positionX;
	private int positionY;
	
	private int counter;
	private boolean type=true;  //:INFO false-sciana
	
	Field(int positionX, int positionY,int counter,boolean type){
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
	boolean getType(){
		return this.type;
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
	void setType(boolean type){
		this.type=type;
	}
	
	
	@Override
	public String toString(){
		if(this.type==true)return "x:"+this.positionX+",y:"+this.positionY;//+" ,ctr.: "+this.counter;
		else return "-------";
	}
}
