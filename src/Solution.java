import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Solution {

	public static int SIZE=10;
	List<Field> stepList= new LinkedList<Field>();
	Field[][] maze= new Field[SIZE+1][SIZE+1];


	Field getLastJunction(){
		for(int i=getListSize()-1;i>=0;i--){
			if(getList(i).getJunction()==true)return getList(i);
		}
		return getEntrance();
	}

	void addList(Field field){
		this.stepList.add(field);
	}

	void removeList(int index){
		this.stepList.remove(index);
	}

	void removeList(Field field){
		this.stepList.remove(field);
	}

	Field getList(int index){
		return this.stepList.get(index);
	}
	Field getListTail(){
		return this.stepList.get(this.stepList.size()-1);
	}

	int getListSize(){
		return this.stepList.size();
	}

	boolean isOnList(Field field){
		for(int i=0;i<getListSize();i++){
			if(getList(i)==field)return true;
		}
		return false;
	}

	void printList(){
		for(int i=0;i<this.stepList.size();i++){
			System.out.println("x:"+getList(i).getPositionX()+"y:"+getList(i).getPositionY());
		}
	}


	Field[][] fillMaze(){

		for(int j=1;j<=SIZE;j++){
			for(int i=1;i<=SIZE;i++){
				maze[i][j]=new Field(i,j,0,0);
			}
		}
		return maze;
	}

	Field[][] copyMaze(Field[][] readyMaze){

		for(int j=1;j<=SIZE;j++){
			for(int i=1;i<=SIZE;i++){
				maze[i][j]=new Field(i,j,readyMaze[i][j].getCounter(),readyMaze[i][j].getType());
			}
		}
		return maze;
	}


	void setEntrance(int positionX,int positionY){
		getField(positionX,positionY).setType(-1);
	}
	void setExit(int positionX,int positionY){
		getField(positionX,positionY).setType(-2);
	}

	Field getEntrance(){

		for(int j=1;j<=SIZE;j++){
			for(int i=1;i<=SIZE;i++){
				if(getField(i,j).getType()==-1)return getField(i,j);
			}
		}
		return null;
	}

	Field getField(int positionX,int positionY){
		if((positionX>0)&&(positionX<=SIZE)&&
				(positionY>0)&&(positionY<=SIZE))
			return maze[positionX][positionY];
		else return null;
	}

	char mazeRepresentation(Field field){
		if(field.getType()==1)return 'X';
		if(field.getType()==0)return '.';
		if(field.getType()==-1)return 'p';
		if(field.getType()==-2)return 'k';
		else return '?';	
	}

	void printMaze(){
		for(int j=1;j<=SIZE;j++){
			for(int i=1;i<=SIZE;i++){
				if(isOnList(getField(i,j)))
					System.out.print('+');
				else System.out.print(mazeRepresentation(getField(i,j)));
			}
			System.out.print("\n");
		}
	}


	void printMazeCounters(){
		for(int j=1;j<=SIZE;j++){
			for(int i=1;i<=SIZE;i++){
				System.out.print(getField(i,j).getCounter());
			}
			System.out.print("\n");
		}
	}
	void printMazeJunctions(){
		for(int j=1;j<=SIZE;j++){
			for(int i=1;i<=SIZE;i++){
				if(getField(i,j).getJunction()==true)
					System.out.print('x');
				else System.out.print('.');
			}
			System.out.print("\n");
		}
	}

	void setJunctions(){
		for(int j=1;j<=SIZE;j++){
			for(int i=1;i<=SIZE;i++){
				if(countNearbyFields(getField(i,j))>2)getField(i,j).setJunction(true);
			}
		}
	}


	void setRandomWalls(int number){

		Random random=new Random();

		for(int i=0;i<number;i++){
			int r1=random.nextInt(SIZE)+1;
			int r2=random.nextInt(SIZE)+1;

			while(getField(r1,r2).getType()==1){
				r1=random.nextInt(SIZE)+1;
				r2=random.nextInt(SIZE)+1;
			}
			getField(r1,r2).setType(1); //1-sciana	
		}
	}


	int countNearbyFields(Field field){

		int fieldCounter=0;
		int positionX=field.getPositionX();
		int positionY=field.getPositionY();
		Field tmpField;

		if(field.getType()!=1){
			if(positionX<SIZE){
				tmpField=getField(positionX+1,positionY);
				if(tmpField.getType()!=1)fieldCounter++;
			}
			if(positionY<SIZE){
				tmpField=getField(positionX,positionY+1);
				if(tmpField.getType()!=1)fieldCounter++;
			}
			if(positionX-1>=1){
				tmpField=getField(positionX-1,positionY);
				if(tmpField.getType()!=1)fieldCounter++;
			}
			if(positionY-1>=1){
				tmpField=getField(positionX,positionY-1);
				if(tmpField.getType()!=1)fieldCounter++;
			}
		}else fieldCounter=-1;

		return fieldCounter;
	}

	Field makeNextFieldIfEmpty(Field field){

		Field nextField=getField(field.getPositionX()+1,field.getPositionY());
		if(nextField.getType()==0)return nextField;
		else return null;

	}

	boolean alreadyVisited(Field field){
		if(field.getCounter()>0)return true;
		else return false;
	}

	boolean counterFull(Field field){
		if(field.getCounter()==countNearbyFields(field))return true;
		else return false;
	}

	boolean canMove(Field field){
		if((field!=null)&&(alreadyVisited(field)==false)&&(field.getType()!=1))
			return true;
		else return false;
	}

	Field changeField(Field field){

		Field nextField=getField(field.getPositionX()+1,field.getPositionY());
		if(canMove(nextField))return nextField;
		else {
			nextField=getField(field.getPositionX(),field.getPositionY()+1);
			if(canMove(nextField))return nextField;
			else{
				nextField=getField(field.getPositionX()-1,field.getPositionY());
				if(canMove(nextField))return nextField;
				else{
					nextField=getField(field.getPositionX(),field.getPositionY()-1);
					if(canMove(nextField))return nextField;
					else return field;
				}
			}
		}

	}

	Field moveBack(Field field){
		Field nextField;

		if(getListSize()>1)removeList(field);
		
		nextField=getListTail();
		return nextField;

	}

	Field moveBack2(Field field){
		Field nextField;
		
		if((counterFull(field))&&(getListSize()>1)){
			removeList(field);
			nextField=getListTail();
			moveBack(nextField);
		}
		return field;
	}


	void move(Field field){

		if((field.getType()!=-2)&&(counterFull(field)==false)){
			field.incrementCounter();
			Field nextField;

			nextField=changeField(field);
			if(nextField!=field){
				addList(nextField);
				move(nextField);
			}
			else {
				nextField=moveBack(field);
				move(nextField);
			}
		}

	}


	void printAllXY(){  //:INFO ta metoda do testow jest tylko
		for(int j=1;j<=SIZE;j++){
			for(int i=1;i<=SIZE;i++){
				System.out.print(getField(i,j).getPositionX()+","+getField(i,j).getPositionY()+"|");
			}
			System.out.print("\n");
		}
	}



	public static void main(String[] args){
		Solution s1= new Solution();
		//Solution s2= new Solution();

		Field[][] maze=s1.fillMaze();

		s1.setRandomWalls(30);

		//s1.setJunctions();

		s1.setEntrance(1,1);
		s1.setExit(SIZE,SIZE);

		System.out.print("\n");
		s1.addList(s1.getEntrance());
		s1.move(s1.getEntrance());
		s1.printList();
		s1.printMaze();

	}

}