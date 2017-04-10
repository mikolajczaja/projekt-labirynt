import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Solution {
	
	public static int SIZE=5;
	List<Field> stepList= new LinkedList<Field>();
	Field[][] maze= new Field[SIZE][SIZE];

	
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
		for(int i=0;i<this.stepList.size();i++)
			System.out.print(i+", "+getList(i)+" | ");
	}
	
	
	Field[][] fillMaze(){
		
		for(int i=0;i<SIZE;i++){
			for(int j=0;j<SIZE;j++){
				maze[i][j]=new Field(i,j,0,0);
			}
		}
		return maze;
	}
	
	
	void setExit(int positionX,int positionY){
		getField(positionX,positionY).setType(-1);
	}

	Field getField(int positionX,int positionY){
		return maze[positionX][positionY];
	}
	
	void printMaze(){
		for(int i=0;i<SIZE;i++){
			for(int j=0;j<SIZE;j++){
				System.out.print(getField(i,j)); //:INFO odwrocone x i y
			}
		System.out.print("\n");
		}
	}
	
	void printMazeCounters(){
		for(int i=0;i<SIZE;i++){
			for(int j=0;j<SIZE;j++){
				System.out.print(getField(i,j).getCounter()+"|");
			}
		System.out.print("\n");
		}
	}
	
	void setRandomWalls(){
		
		Random random=new Random();
		
		for(int i=0;i<SIZE*SIZE/2;i++){
			int r1=random.nextInt(SIZE);
			int r2=random.nextInt(SIZE);
			
			while(getField(r1,r2).getType()==1){
				r1=random.nextInt(SIZE);
				r2=random.nextInt(SIZE);
			}
			getField(r1,r2).setType(1); //1-sciana	
		}
	}

	
	void iterateNearbyCounters(Field field){
		
		int positionX=field.getPositionX();
		int positionY=field.getPositionY();
		Field tmpField;
		
		if(field.getType()==0){
			if(positionX+1<SIZE){
				tmpField=getField(positionX+1,positionY);
				if(tmpField.getType()==0)field.iterateCounter();
			}
			if(positionY+1<SIZE){
				tmpField=getField(positionX,positionY+1);
				if(tmpField.getType()==0)field.iterateCounter();
			}
			if(positionX-1>0){
				tmpField=getField(positionX-1,positionY);
				if(tmpField.getType()==0)field.iterateCounter();
			}
			if(positionY-1>0){
				tmpField=getField(positionX,positionY-1);
				if(tmpField.getType()==0)field.iterateCounter();
			}
		}
	}

	void iterateAll(){
		for(int i=0;i<SIZE;i++){
			for(int j=0;j<SIZE;j++){
				iterateNearbyCounters(getField(i,j));
			}
		}
	}
	
	public static void main(String[] args){
		Solution s1= new Solution();

		s1.fillMaze();
		s1.setRandomWalls();
		//s1.addList(s1.getField(1,1));
		//s1.printList();
		//s1.printNearbyFields(s1.getField(3,3));
		//s1.setWall(4,3);
		s1.setExit(SIZE-1,SIZE-1);
		s1.iterateAll();
		s1.printMaze();	
		s1.printMazeCounters();
	}
	
}