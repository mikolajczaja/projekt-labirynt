import java.util.Iterator;
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
	
	Field getList(int index){
		return this.stepList.get(index);
	}
	
	void printList(){		
		Iterator<Field> iterator=this.stepList.iterator();
		while(iterator.hasNext()){
			Field tmpField=iterator.next();
			System.out.println("x:"+tmpField.getPositionX()+"y:"+tmpField.getPositionY());
		}
			
	}
	
	boolean alreadyVisited(Field field){
		Iterator<Field> iterator=this.stepList.iterator();
		while(iterator.hasNext()){
			if(iterator.next()==field) return true;
		}
		return false;
	}
	
	//:TODO move:-sprawdzenie alreadyVisited
	//			 -zliczenie countNearbyFields
	//			 -sprawdzenie czy powyzsze==field.counter 
	//							(jesli nie to idz w pole nr counter od prawej)
	//							(jesli tak, to idz do tylu)
	//			 -incrementFieldCounter
	// to wszystko dla nastepnego pola (Field)
	
	
	Field[][] fillMaze(){
		
		for(int i=1;i<=SIZE;i++){
			for(int j=1;j<=SIZE;j++){
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

	Field getField(int positionX,int positionY){
		return maze[positionX][positionY];
	}
	
	void printMaze(){
		for(int i=1;i<=SIZE;i++){
			for(int j=1;j<=SIZE;j++){
				System.out.print(getField(i,j)); //:INFO odwrocone x i y
			}
		System.out.print("\n");
		}
	}
	
	void printMazeCounters(){
		for(int i=1;i<=SIZE;i++){
			for(int j=1;j<=SIZE;j++){
				System.out.print(getField(i,j).getCounter()+"|");
			}
		System.out.print("\n");
		}
	}
	
	void setRandomWalls(){
		
		Random random=new Random();
		
		for(int i=0;i<SIZE*SIZE/2;i++){
			int r1=random.nextInt(SIZE-1)+1;
			int r2=random.nextInt(SIZE-1)+1;
			
			while(getField(r1,r2).getType()==1){
				r1=random.nextInt(SIZE-1)+1;
				r2=random.nextInt(SIZE-1)+1;
			}
			getField(r1,r2).setType(1); //1-sciana	
		}
	}

	
	int countNearbyFields(Field field){
		
		int fieldCounter=0;
		int positionX=field.getPositionX();
		int positionY=field.getPositionY();
		Field tmpField;
		
		if(field.getType()==0){
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

	void printAllXY(){  //:INFO ta metoda do testow jest tylko
		for(int i=1;i<=SIZE;i++){
			for(int j=1;j<=SIZE;j++){
				System.out.print(getField(i,j).getPositionX()+","+getField(i,j).getPositionY()+"|");
			}
		System.out.print("\n");
		}
	}
	

	
	public static void main(String[] args){
		Solution s1= new Solution();

		s1.fillMaze();
		s1.setRandomWalls();
		
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
		s1.printList();
		//s1.printNearbyFields(s1.getField(3,3));
		//s1.setWall(4,3);
		
		s1.setEntrance(1,1);
		s1.setExit(SIZE,SIZE);
		
		s1.printMaze();
		//s1.printAllXY();


		System.out.println(s1.countNearbyFields(s1.getField(4,4)));
		System.out.println(s1.countNearbyFields(s1.getField(3,3)));
		System.out.println(s1.countNearbyFields(s1.getField(2,2)));
		System.out.println(s1.countNearbyFields(s1.getField(5,4)));
		//s1.printMazeCounters();
	}
	
}