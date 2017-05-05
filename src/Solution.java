import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Solution {

	public static int SIZE=5;
	List<Field> stepList= new LinkedList<Field>();
	Field[][] maze= new Field[SIZE+1][SIZE+1];


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

	boolean alreadyVisited(Field field){
		if(field.getCounter()>=countNearbyFields(field))return true;
		else return false;
	}



	Field[][] fillMaze(){

		for(int j=1;j<=SIZE;j++){
			for(int i=1;i<=SIZE;i++){
				maze[i][j]=new Field(i,j,0,0);
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
				System.out.print(getField(i,j).getCounter()+"|");
			}
			System.out.print("\n");
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
			if(positionX<SIZE){     //:INFO SIZE=max X+1 bo zakres X:0-4, a size jest 5
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


	boolean canMove(Field field){
		if((field!=null)&&(alreadyVisited(field)==false)&&(field.getType()!=1))
		{
			//System.out.println(field.getPositionX()+","+field.getPositionY());
			return true;
		}
		else return false;
	}

	Field changeField(Field field){
		//System.out.println(field);
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
		if((field.getCounter()>=countNearbyFields(field))&&(field.getType()!=-1))
		{
			removeList(field);
			nextField=getListTail();
			moveBack(nextField);
		}
		return field;
	}


	void move(Field field){

		//System.out.println(getListTail());
		if((field.getType()!=-2)){
			field.incrementCounter();
			Field nextField;
			int nearbyCount=countNearbyFields(field);
			//System.err.println(nearbyCount+","+field.getCounter());
			if((nearbyCount>=field.getCounter())&&(field.getCounter()<=4)){
				nextField=changeField(field);
				//System.out.println(nextField.getPositionX()+","+nextField.getPositionY());
				if(nextField!=field){
					addList(nextField);//:TODO check if it even works
					move(nextField);
				}
				//else nextField=moveBack(field);
			}
			else nextField=moveBack(field);

			//if(nextField!=null)
			//else System.out.print("\n\n"+"aaaaaaaaaa"+"\n");
		} 
		//return nextField;

	}

	//:TODO move:-sprawdzenie alreadyVisited
	//			 -zliczenie countNearbyFields
	//			 -sprawdzenie czy powyzsze==field.counter 
	//							(jesli nie to idz w pole nr counter od prawej)
	//							(jesli tak, to idz do tylu)
	//			 -incrementFieldCounter
	// to wszystko dla nastepnego pola (Field)



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

		s1.fillMaze();
		s1.setRandomWalls(10);

		/*		Field f1=new Field(2,2,0,0);
		Field f2=new Field(3,3,0,0);
		s1.addList(f1);
		s1.addList(f1);
		s1.addList(f1);
		s1.addList(f2);

		s1.printList();
		System.out.print("\n");
		s1.removeList(f1);
		s1.printList();
		System.out.print("\n");
		s1.removeList(f1);
		s1.printList();
		System.out.print("\n");
		 */	



		/*
		s1.addList(s1.getField(1,1));
		s1.addList(s1.getField(1,2));
		s1.addList(s1.getField(1,3));
		System.err.println(s1.alreadyVisited(s1.getField(2,3)));
		s1.addList(s1.getField(2,3));		
		System.err.println(s1.alreadyVisited(s1.getField(1,3)));
		System.err.println(s1.alreadyVisited(s1.getField(2,3)));
		System.err.println(s1.alreadyVisited(s1.getField(3,3)));
		 */

		//s1.printNearbyFields(s1.getField(3,3));
		//s1.setWall(4,3);

		s1.setEntrance(1,1);
		s1.setExit(SIZE,SIZE);

		//s1.printMaze();
		s1.addList(s1.getEntrance());
		s1.move(s1.getEntrance());
		s1.printList();
		s1.printMaze();
		//System.out.println(s1.getList(s1.getListSize()-1).getPositionX()+","+s1.getList(s1.getListSize()-1).getPositionY());

		//s1.printAllXY();

		//s1.printMazeCounters();
	}

}