
public class Field {

	private int positionX;
	private int positionY;
	
	private int counter;
	private int type=0;  //:INFO 1-sciana, 0- puste, -1-koniec
	
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
	
	
	@Override
	public String toString(){
		//if(this.type==true)return "x:"+this.positionX+",y:"+this.positionY;//+" ,ctr.: "+this.counter;
		//else return "-------";
		if(this.type==1)return "X";
		if(this.type==0)return ".";
		if(this.type==-1)return "*";
		else return "?";
	}
}
