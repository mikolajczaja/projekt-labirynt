import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Solution {
	
	public static int SIZE=25;
	List<Field> stepList= new LinkedList<Field>();
	Field[][] maze= new Field[SIZE][SIZE];

	void addList(Field field){
		this.stepList.add(field);
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
	
	
	void setExit(Field[][] fieldSet,int positionX, int positionY){
		getField(fieldSet,positionX,positionY).setType(-1);
	}

	Field getField(Field[][] fieldSet, int positionX,int positionY){
		return fieldSet[positionX][positionY];
	}
	
	void printMaze(Field[][] fieldSet){
		for(int i=0;i<SIZE;i++){
			for(int j=0;j<SIZE;j++){
				System.out.print(fieldSet[j][i]);//+" | ");//:INFO odwrocone x i y
			}
		System.out.print("\n");
		}
	}
	
	
	void setRandomWalls(Field[][] fieldSet){
		
		Random random=new Random();
		
		for(int i=0;i<SIZE*SIZE/2;i++){
			int r1=random.nextInt(SIZE);
			int r2=random.nextInt(SIZE);
			
			while(getField(fieldSet,r1,r2).getType()==1){
				r1=random.nextInt(SIZE);
				r2=random.nextInt(SIZE);
			}
				
			getField(fieldSet,r1,r2).setType(1); //1-sciana	
		}
	}

	void printNearbyFields(Field[][] fieldSet, Field field){
		System.out.println("\n"+fieldSet[field.getPositionX()][field.getPositionY()+1]);
		System.out.println(fieldSet[field.getPositionX()][field.getPositionY()-1]);
		System.out.println(fieldSet[field.getPositionX()+1][field.getPositionY()]);
		System.out.print(fieldSet[field.getPositionX()-1][field.getPositionY()]);
	}

	public static void main(String[] args){
		Solution s1= new Solution();
		Field[][] maze1=s1.fillMaze();

		s1.setRandomWalls(maze1);
		//s1.addList(s1.getField(maze1,1,1));
		//s1.printList();
		//s1.printNearbyFields(maze1,s1.getField(maze1,3,3));
		//s1.setWall(maze1,4,3);
		s1.setExit(maze1,24,6);
		s1.printMaze(maze1);	
	}
	
}