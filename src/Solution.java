import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

//:TODO oczyszczenie kodu
//:TODO okienko do file not found
public class Solution {

	public static int SIZE_X = 20;
	public static int SIZE_Y = 20;

	List<List<Field>> solutionList = new LinkedList<List<Field>>();
	List<List<Field>> solutionList2 = new LinkedList<List<Field>>();
	List<Field> stepList = new LinkedList<Field>();
	Field[][] maze = new Field[SIZE_X + 1][SIZE_Y + 1];
	List<Integer> order=new ArrayList<Integer>();
	
	void setSizeX(int sizeX) {
		SIZE_X = sizeX;
	}

	void setSizeY(int sizeY) {
		SIZE_Y = sizeY;
	}

	void setMazeSize(){
		this.maze=new Field[SIZE_X + 1][SIZE_Y + 1];
	}
	
	List<Integer> getOrder(){
		return this.order;
	}
	
	Field getLastJunction() {
		for (int i = getListSize() - 1; i >= 0; i--) {
			if (getList(i).getJunction() == true)
				return getList(i);
		}
		return getEntrance();
	}

	void addList(Field field) {
		this.stepList.add(field);
	}

	void removeList(int index) {
		this.stepList.remove(index);
	}

	void removeList(Field field) {
		this.stepList.remove(field);
	}

	Field getList(int index) {
		return this.stepList.get(index);
	}

	Field getList(int index, List<Field> stepList) {
		return stepList.get(index);
	}

	Field getListTail() {
		return this.stepList.get(this.stepList.size() - 1);
	}

	int getListSize() {
		return this.stepList.size();
	}

	int getListSize(List<Field> stepList) {
		return stepList.size();
	}

	boolean isOnList(Field field) {
		for (int i = 0; i < getListSize(); i++) {
			if (getList(i) == field)
				return true;
		}
		return false;
	}

	boolean isOnList(Field field, List<Field> stepList) {
		for (int i = 0; i < getListSize(stepList); i++) {
			if (getList(i, stepList) == field)
				return true;
		}
		return false;
	}

	Field getNextField(Field field,int valueX, int valueY){
		return getField(field.getPositionX()+valueX,field.getPositionY()+valueY);
	}
	
	boolean isOnList(Field field,int valueX,int valueY,List<Field> stepList){
		Field nextField=getNextField(field,valueX,valueY);
		for (int i = 0; i < getListSize(stepList); i++) {
			if (getList(i, stepList) == nextField)
				return true;
		}
		return false;
	}
	
	
	void printList() {
		for (int i = 0; i < this.stepList.size(); i++) {
			System.out.println("x:" + getList(i).getPositionX() + "y:"
					+ getList(i).getPositionY());
		}
	}

	void printList(List<Field> stepList) {
		for (int i = 0; i < stepList.size(); i++) {
			System.out.println("x:" + getList(i, stepList).getPositionX() + "y:"
					+ getList(i, stepList).getPositionY());
		}
	}

	List<Field> copySteps() {
		List<Field> tmp = new LinkedList<Field>();

		for (int i = 0; i < this.stepList.size(); i++) {
			Field tmpField = getList(i);
			tmp.add(tmpField);
		}
		return tmp;
	}

	List<Field> saveList() {
		List<Field> tmp = new LinkedList<Field>();
		tmp = copySteps();
		this.solutionList.add(tmp);
		return tmp;
	}

	void printSolutionList() {
		for (int i = 0; i < this.solutionList.size(); i++) {

			List<Field> stepList = this.solutionList.get(i);
			System.out.println("Solution no.:" + i);
			printList(stepList);
		}
	}

	
	
	
	void shortenList(List<Field> stepList) {

			Field tmpField;
			Field tmpField2;

			for (int i = 1; i < stepList.size(); i++)
				for(int j=1;j<stepList.size();j++){
				tmpField=stepList.get(i);
				tmpField2=stepList.get(j);
				if((canShortcut(tmpField,tmpField2,stepList))&&(tmpField.getType()==0)&&(tmpField2.getType()==0)){
					
					System.out.println(tmpField+" , "+tmpField2);
					shortcut(tmpField,tmpField2,stepList);
				}
				}
		
	}

	void shortenRight(List<Field> stepList) {

		for (int i = 1; i < stepList.size(); i++) {
			Field tmpField = stepList.get(i);
			Field tmpField2 = getField(tmpField.getPositionX() + 1,
					tmpField.getPositionY());

			if (isOnList(tmpField2, stepList)) {
				shortcut(tmpField, tmpField2, stepList);
			}

		}
	}

	
	void shortenLeft(List<Field> stepList) {

		for (int i = 1; i < stepList.size(); i++) {
			Field tmpField = stepList.get(i);
			Field tmpField2 = getField(tmpField.getPositionX() - 1,
					tmpField.getPositionY());

			if (isOnList(tmpField2, stepList)) {
				shortcut(tmpField, tmpField2, stepList);
			}

		}
	}

	boolean canShortcut(Field field, Field field2, List<Field> stepList){
		int ctr=0;
		int diffX=field.getPositionX()-field2.getPositionX();
		int diffY=field.getPositionY()-field2.getPositionY();
		
		if (diffX > 0) {
			ctr=0;
			for (int i = 1; i < SIZE_X - field.getPositionX(); i++) {
				if (((isOnList(field, i - 1, 0, stepList)))
						&& (getNextField(field, i, 0).getType() != 1)
						&& (isOnList(getNextField(field, i, 0)) == false))
					ctr++;
			}
			if (ctr == SIZE_X - field.getPositionX() - 1)
				return true;
		}
		else if(diffX<0){
			ctr=0;
			for (int i = -1; i > SIZE_X - field.getPositionX(); i--) {
				if (((isOnList(field, i+1, 0, stepList)))
						&& (getNextField(field, i, 0).getType() != 1)
						&& (isOnList(getNextField(field, i, 0)) == false))
					ctr++;
			}
			if (ctr == SIZE_X - field.getPositionX() - 1)
				return true;
		}
		else if(diffY>0){
			ctr=0;
			for (int i = 1; i < SIZE_Y - field.getPositionY(); i++) {
				if (((isOnList(field, 0, i-1, stepList)))
						&& (getNextField(field, 0, i).getType() != 1)
						&& (isOnList(getNextField(field, 0, i)) == false))
					ctr++;
			}
			if (ctr == SIZE_Y - field.getPositionY() - 1)
				return true;
		}
		else if(diffY<0){
			ctr=0;
			for (int i = -1; i > SIZE_Y - field.getPositionY(); i++) {
				if (((isOnList(field, 0, i+1, stepList)))
						&& (getNextField(field, 0, i).getType() != 1)
						&& (isOnList(getNextField(field, 0, i)) == false))
					ctr++;
			}
			if (ctr == SIZE_Y - field.getPositionY() - 1)
				return true;
		}
	return false;
	}
	
	void shortenDown(List<Field> stepList) {

		for (int i = 1; i < stepList.size(); i++) {
			Field tmpField = stepList.get(i);
			Field tmpField2 = getField(tmpField.getPositionX(),
					tmpField.getPositionY() + 1);

			if (isOnList(tmpField2, stepList)) {
				shortcut(tmpField, tmpField2, stepList);
			}

		}
	}

	void shortenUp(List<Field> stepList) {

		for (int i = 1; i < stepList.size(); i++) {
			Field tmpField = stepList.get(i);
			Field tmpField2 = getField(tmpField.getPositionX() + 1,
					tmpField.getPositionY() - 1);

			if (isOnList(tmpField2, stepList)) {
				shortcut(tmpField, tmpField2, stepList);
			}

		}
	}

	void shortcut(Field field1, Field field2, List<Field> stepList) {
		int shortcutStart = 0;
		int shortcutFinish = 0;

		for (int i = 0; i < stepList.size(); i++)
			if (stepList.get(i) == field1)
				shortcutStart = i;

		for (int i = 0; i < stepList.size(); i++)
			if (stepList.get(i) == field2)
				shortcutFinish = i;

		int shortcutDifference = shortcutFinish - shortcutStart;

		if (shortcutDifference > 1)
			for (int i = shortcutFinish-1; i >=shortcutStart+1 ; i--) {
				//System.out.println("1");
				stepList.remove(i);
			}
		else if (shortcutDifference < -1)
			for (int i = shortcutStart; i > shortcutFinish; i--) {
				//System.out.println("2");
				stepList.remove(i);
			}
	}

	
	List<Field> pickBestSolution() {

		List<Field> bestSolution;
		
		if (this.solutionList.isEmpty() == false) {
			bestSolution = this.solutionList.get(0);
		
			for (int i = 0; i < this.solutionList.size(); i++) {
				if (this.solutionList.get(i).size() < bestSolution.size()){
					bestSolution = this.solutionList.get(i);
				}
			}
			
		}else bestSolution=null;

		return bestSolution;
	}
	

	void drawBestSolution() {
		if (this.solutionList.isEmpty() == false) {
			List<Field> bestSolution = this.solutionList.get(0);
			for (int i = 0; i < this.solutionList.size(); i++) {
				if (this.solutionList.get(i).size() < bestSolution.size())
					bestSolution = this.solutionList.get(i);
			}
			//makeGraphicRepresentation(getSolutionIndex(bestSolution));
		}
	}

	int getSolutionIndex(List<Field> solution) {
		for (int i = 0; i < this.solutionList.size(); i++) {
			if (this.solutionList.get(i) == solution)
				return i;
		}
		return -1;
	}

	Field[][] fillMaze() {

		for (int j = 1; j <= SIZE_Y; j++) {
			for (int i = 1; i <= SIZE_X; i++) {
				maze[i][j] = new Field(i, j, 0, 0);
			}
		}
		return maze;
	}

	int findMaxX(String fileName) throws IOException {

		int maxX = 0;
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		int nextInt = bufferedReader.read();
		char nextChar;
		int i = 1;

		while (nextInt != -1) {
			nextChar = (char) nextInt;
			nextInt = bufferedReader.read();

			if (nextChar == 13) {
				if (i > maxX)
					maxX = i;
				i = 0;
			} else
				i++;

		}
		bufferedReader.close();
		fileReader.close();
		return maxX;
	}

	int findMaxY(String fileName) throws IOException {

		int maxY = 1;
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		int nextInt = bufferedReader.read();
		char nextChar;

		while (nextInt != -1) {
			nextChar = (char) nextInt;
			nextInt = bufferedReader.read();

			if (nextChar == 13)
				maxY = maxY + 1;

		}
		bufferedReader.close();
		fileReader.close();
		return maxY;
	}

	void readMazeFromFile(String fileName) throws IOException {

		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		int nextInt = bufferedReader.read();
		char nextChar;
		int i = 1;
		int j = 1;
		int type = 0;

		int sizeX = findMaxX(fileName);
		int sizeY = findMaxY(fileName);
		SIZE_X = sizeX;
		SIZE_Y = sizeY;

		maze = new Field[SIZE_X + 1][SIZE_Y + 1];

		while (nextInt != -1) {
			nextChar = (char) nextInt;
			nextInt = bufferedReader.read();


			if (nextChar == 13) {
				for (int counter = i; counter <= SIZE_X; counter++)
					maze[counter][j] = new Field(counter, j, 0, 0);
				type = 0;
				j++;
				i = 0;
			} else {
				if ((nextChar == 'X') || (nextChar == 'x'))
					type = 1;
				if (nextChar == '.')
					type = 0;
				if ((nextChar == 'k') || (nextChar == 'K'))
					type = -2;
				if ((nextChar == 'p') || (nextChar == 'P'))
					type = -1;

				maze[i][j] = new Field(i, j, 0, type);
				i++;

			}
		}
		for (int counter = i; counter <= SIZE_X; counter++)
			maze[counter][j] = new Field(counter, j, 0, 0);

		bufferedReader.close();
		fileReader.close();
	}

	Field[][] copyMaze(Field[][] readyMaze) {

		for (int j = 1; j <= SIZE_Y; j++) {
			for (int i = 1; i <= SIZE_X; i++) {
				maze[i][j] = new Field(i, j, readyMaze[i][j].getCounter(),
						readyMaze[i][j].getType());
			}
		}
		return maze;
	}

	void setEntrance(int positionX, int positionY) {
		getField(positionX, positionY).setType(-1);
	}

	void setExit(int positionX, int positionY) {
		getField(positionX, positionY).setType(-2);
	}

	Field getEntrance() {

		for (int j = 1; j <= SIZE_Y; j++) {
			for (int i = 1; i <= SIZE_X; i++) {
				if (getField(i, j).getType() == -1)
					return getField(i, j);
			}
		}
		return null;
	}

	Field getExit() {

		for (int j = 1; j <= SIZE_Y; j++) {
			for (int i = 1; i <= SIZE_X; i++) {
				if (getField(i, j).getType() == -2)
					return getField(i, j);
			}
		}
		return null;
	}

	Field getField(int positionX, int positionY) {
		if ((positionX > 0) && (positionX <= SIZE_X) && (positionY > 0)
				&& (positionY <= SIZE_Y))
			return maze[positionX][positionY];
		else
			return null;
	}

	char mazeRepresentation(Field field) {
		if (field == null)
			return 'n';
		if (field.getType() == 1)
			return 'X';
		if (field.getType() == 0)
			return '.';
		if (field.getType() == -1)
			return 'p';
		if (field.getType() == -2)
			return 'k';
		else
			return '?';

	}

	void makeGraphicRepresentation(List<Field> stepList) {

		//List<Field> stepList = this.solutionList.get(index);
		if(stepList!=null){
		JFrame frame = new JFrame("solution");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(100 + 30 * SIZE_X, 100 + 30 * SIZE_Y);
		frame.getContentPane().setLayout(null);

		JPanel drawingPanel = new JPanel() {
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;

				Field tmpField = getField(1, 1);

				for (int j = 1; j <= SIZE_Y; j++) {
					for (int i = 1; i <= SIZE_X; i++) {
						tmpField = getField(i, j);

						if (tmpField == null)
							g2d.setColor(Color.GRAY);

						if (tmpField.getType() == 1)
							g2d.setColor(Color.BLACK);
						if (tmpField.getType() == 0)
							g2d.setColor(Color.WHITE);
						if (isOnList(tmpField, stepList))
							g2d.setColor(Color.ORANGE);
						if (tmpField.getType() == -1)
							g2d.setColor(Color.RED);
						if (tmpField.getType() == -2)
							g2d.setColor(Color.YELLOW);

						g2d.fillRect(tmpField.getPositionX() * 30 - 25,
								tmpField.getPositionY() * 30 - 25, 30, 30);
					}
				}

			}
		};
		drawingPanel.setBounds(0, 0, SIZE_X * 30 + 5, SIZE_Y * 30 + 5);
		frame.add(drawingPanel);

		frame.setVisible(true);

		JButton solverButton = new JButton("menu");
		solverButton.setBounds(30 * SIZE_X, 25 + 30 * SIZE_Y, 75, 25);
		frame.getContentPane().add(solverButton);

		JFrame desc = drawDescription();

		solverButton.addMouseListener(new SolverButtonMouseAdapter() {

			public void mouseClicked(MouseEvent e) {

				frame.dispose();
				mazeSolver();
				desc.dispose();
			}
		});
	}
	}

	class SolverButtonMouseAdapter extends MouseAdapter {
	}

	void drawError(){
	JFrame errorFrame=new JFrame("error");
	errorFrame.setSize(200, 100);
	errorFrame.setLocation(100,100);

	
	JTextField error = new JTextField();
	error.setBounds(110, 110, 75, 20);
	error.setEditable(false);
	errorFrame.getContentPane().add(error);
	error.setText("nie znaleziono rozwiazania");
	
	errorFrame.setVisible(true);
	}
	
	JFrame drawDescription() {

		JFrame descFrame = new JFrame("description");
		descFrame.setSize(150, 200);
		descFrame.setLocation(100 + 30 * SIZE_X, 0);

		descFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTextField desc1 = new JTextField();
		desc1.setBounds(25, 25, 75, 20);
		desc1.setEditable(false);
		descFrame.getContentPane().add(desc1);
		desc1.setText("poczatek");

		JTextField desc2 = new JTextField();
		desc2.setBounds(25, 55, 75, 20);
		desc2.setEditable(false);
		descFrame.getContentPane().add(desc2);
		desc2.setText("koniec");

		JTextField desc3 = new JTextField();
		desc3.setBounds(25, 85, 75, 20);
		desc3.setEditable(false);
		descFrame.getContentPane().add(desc3);
		desc3.setText("droga");

		JTextField desc4 = new JTextField();
		desc4.setBounds(25, 115, 75, 20);
		desc4.setEditable(false);
		descFrame.getContentPane().add(desc4);
		desc4.setText("puste pole");

		JTextField desc5 = new JTextField();
		desc5.setBounds(25, 145, 75, 20);
		desc5.setEditable(false);
		descFrame.getContentPane().add(desc5);
		desc5.setText("sciana");

		descFrame.add(new JPanel() {
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;

				g2d.setColor(Color.red);
				g2d.fillRect(100, 20, 30, 30);
				g2d.setColor(Color.YELLOW);
				g2d.fillRect(100, 50, 30, 30);
				g2d.setColor(Color.orange);
				g2d.fillRect(100, 80, 30, 30);
				g2d.setColor(Color.white);
				g2d.fillRect(100, 110, 30, 30);
				g2d.setColor(Color.black);
				g2d.fillRect(100, 140, 30, 30);
			}
		});
		descFrame.setVisible(true);

		return descFrame;
	}

	void printMaze(int index) {
		
		List<Field> stepList = this.solutionList.get(index);
		
		for (int j = 1; j <= SIZE_Y; j++) {
			for (int i = 1; i <= SIZE_X; i++) {
				if (isOnList(getField(i,j),stepList))
					System.out.print('+');
				else
					System.out.print(mazeRepresentation(getField(i, j)));
			}
			System.out.print("\n");
		}
	}

	void printMazeCounters() {
		for (int j = 1; j <= SIZE_Y; j++) {
			for (int i = 1; i <= SIZE_X; i++) {
				System.out.print(getField(i, j).getCounter());
			}
			System.out.print("\n");
		}
	}

	void printMazeJunctions() {
		for (int j = 1; j <= SIZE_Y; j++) {
			for (int i = 1; i <= SIZE_X; i++) {
				if (getField(i, j).getJunction() == true)
					System.out.print('x');
				else
					System.out.print('.');
			}
			System.out.print("\n");
		}
	}


	void setRandomWalls(int number) {

		Random random = new Random();

		for (int i = 0; i < number; i++) {
			int r1 = random.nextInt(SIZE_X) + 1;
			int r2 = random.nextInt(SIZE_Y) + 1;

			while (getField(r1, r2).getType() == 1) {
				r1 = random.nextInt(SIZE_X) + 1;
				r2 = random.nextInt(SIZE_Y) + 1;
			}
			getField(r1, r2).setType(1); // 1-sciana
		}
	}

	int countNearbyFields(Field field) {

		int fieldCounter = 0;
		int positionX = field.getPositionX();
		int positionY = field.getPositionY();
		Field tmpField;

		if (field.getType() != 1) {
			if (positionX < SIZE_X) {
				tmpField = getField(positionX + 1, positionY);
				if (tmpField.getType() != 1)
					fieldCounter++;
			}
			if (positionY < SIZE_Y) {
				tmpField = getField(positionX, positionY + 1);
				if (tmpField.getType() != 1)
					fieldCounter++;
			}
			if (positionX - 1 >= 1) {
				tmpField = getField(positionX - 1, positionY);
				if (tmpField.getType() != 1)
					fieldCounter++;
			}
			if (positionY - 1 >= 1) {
				tmpField = getField(positionX, positionY - 1);
				if (tmpField.getType() != 1)
					fieldCounter++;
			}
		} else
			fieldCounter = -1;

		return fieldCounter;
	}

	Field makeNextFieldIfEmpty(Field field) {

		Field nextField = getField(field.getPositionX() + 1, field.getPositionY());
		if (nextField.getType() == 0)
			return nextField;
		else
			return null;

	}

	boolean alreadyVisited(Field field) {
		if (field.getCounter() > 0)
			return true;
		else
			return false;
	}

	boolean counterFull(Field field) {
		
		if (field.getCounter() == countNearbyFields(field)){

			return true;
		}
		
		else
			return false;
	}

	boolean canMove(Field field) {
		if ((field != null) && (alreadyVisited(field) == false)
				&& (field.getType() != 1))
			return true;
		else
			return false;
	}

	Field getFieldNo(Field field,int number){
		Field tmpField;

		
		if(number==1)tmpField=getField(field.getPositionX()+1, field.getPositionY());
		else if(number==2)tmpField=getField(field.getPositionX()-1, field.getPositionY());
		else if(number==3)tmpField=getField(field.getPositionX(), field.getPositionY()+1);
		else if(number==4)tmpField=getField(field.getPositionX(), field.getPositionY()-1);
		else tmpField=field;
		return tmpField;
	}
	
	List<Integer> setOrder(){
		
		for (int i =1; i<=4; i++) {
		    order.add(i);
		}
		Collections.shuffle(order);
		
		return order;
	}
	

	
	Field changeField(Field field,List<Integer> order){
		
		Field nextField;
		
		for(int i=0;i<4;i++){
		nextField=getFieldNo(field,order.get(i));
		if (canMove(nextField))return nextField;
		}
		
	return field;
	}
	
	Field changeField2(Field field) {

		Field nextField = getField(field.getPositionX() + 1, field.getPositionY());
		if (canMove(nextField))
			return nextField;
		else {
			nextField = getField(field.getPositionX(), field.getPositionY() + 1);
			if (canMove(nextField))
				return nextField;
			else {
				nextField = getField(field.getPositionX() - 1, field.getPositionY());
				if (canMove(nextField))
					return nextField;
				else {
					nextField = getField(field.getPositionX(),
							field.getPositionY() - 1);
					if (canMove(nextField))
						return nextField;
					else
						return field;
				}
			}
		}

	}

	Field moveBack(Field field) {
		Field nextField = field;

		if (getListSize() > 1)
			removeList(field);

		nextField = getListTail();

		return nextField;

	}

	Field moveBack2(Field field) {
		Field nextField;

		if ((counterFull(field)) && (getListSize() > 1)) {
			removeList(field);
			nextField = getListTail();
			moveBack(nextField);
		}
		return field;
	}

	void move(Field field) {

		if ((field.getType() != -2) && (counterFull(field) == false)) {

			
			field.incrementCounter();
			Field nextField;

			nextField = changeField(field,getOrder());
			if (nextField != field) {
				addList(nextField);
				move(nextField);
			} else {
				nextField = moveBack(field);
				
				//for(int i=0;i<getListSize();i++){
				 //nextField=moveBack(nextField);
				 //}
				// :TODO tu zmiana powyzej
				if (nextField != field)
					move(nextField);
			}
		} else if (field.getType() == -2) {
			saveList();
			Field nextField = moveBack(field);

			for (int i = 0; i < getListSize(); i++) {

				nextField = moveBack(nextField);
			}
			if (nextField != field)
				move(nextField);
		}

	}

	void printAllXY() { // :INFO ta metoda do testow jest tylko
		for (int j = 1; j <= SIZE_Y; j++) {
			for (int i = 1; i <= SIZE_X; i++) {
				System.out.print(getField(i, j).getPositionX() + ","
						+ getField(i, j).getPositionY() + "|");
			}
			System.out.print("\n");
		}
	}

	void clearMaze() {
		for (int j = 1; j <= SIZE_Y; j++) {
			for (int i = 1; i <= SIZE_X; i++) {
				getField(i,j).setCounter(0);
			}
		}
	}
	//:INFO mazeSolver
	void solveMaze(int sizeX, int sizeY, int wallCount) {
		setSizeX(sizeX);
		setSizeY(sizeY);
		setMazeSize();
		fillMaze();
		setRandomWalls(wallCount);
		setEntrance(1, 1);
		setExit(SIZE_X, SIZE_Y);

		setOrder();
		for(int i=0;i<100;i++){
		setOrder();
		addList(getEntrance());
		move(getEntrance());

		clearMaze();
		}
		
		if (pickBestSolution() == null) {

			List<Field> newList=new LinkedList<Field>();
			newList.add(getEntrance());
			makeGraphicRepresentation(newList);
			drawError();
			//solveMaze(sizeX,sizeY,wallCount);
			//mazeSolver();
		} else
			makeGraphicRepresentation(pickBestSolution());

	}

	void solveMaze(String fileName) {
		try {
			setMazeSize();
			fillMaze();
			readMazeFromFile(fileName);
			
			for(int i=0;i<100;i++){
				setOrder();
				addList(getEntrance());
				move(getEntrance());
				
				clearMaze();
				}
			
			if (pickBestSolution() == null) {

				List<Field> newList=new LinkedList<Field>();
				newList.add(getEntrance());
				makeGraphicRepresentation(newList);
				drawError();
				//mazeSolver();
			} else
				makeGraphicRepresentation(pickBestSolution());

		} catch (IOException e) {
			System.err.println("file not found");
		}
	}

	void mazeSolver() {
		
		solutionList.clear();
		JFrame frame = new JFrame("maze solver");
		frame.setSize(500, 300);
		frame.getContentPane().setLayout(null);

		JLabel sizeX = new JLabel();
		sizeX.setBounds(25, 50, 150, 25);
		frame.getContentPane().add(sizeX);
		sizeX.setText("szerokosc labiryntu");

		JLabel sizeY = new JLabel();
		sizeY.setBounds(25, 100, 150, 25);
		frame.getContentPane().add(sizeY);
		sizeY.setText("dlugosc labiryntu");

		JLabel wallCount = new JLabel();
		wallCount.setBounds(25, 150, 150, 25);
		frame.getContentPane().add(wallCount);
		wallCount.setText("ilosc scian");

		JTextField sizeXField = new JTextField();
		sizeXField.setBounds(150, 50, 75, 25);
		frame.getContentPane().add(sizeXField);
		sizeXField.setText("10");

		JTextField sizeYField = new JTextField();
		sizeYField.setBounds(150, 100, 75, 25);
		frame.getContentPane().add(sizeYField);
		sizeYField.setText("10");

		JTextField wallCountField = new JTextField();
		wallCountField.setBounds(150, 150, 75, 25);
		frame.getContentPane().add(wallCountField);
		wallCountField.setText("25");

		JButton solveGeneratedButton = new JButton("wygeneruj i rozwiaz");
		solveGeneratedButton.setBounds(75, 200, 150, 25);
		frame.getContentPane().add(solveGeneratedButton);

		solveGeneratedButton
				.addMouseListener(new SolveGeneratedButtonMouseAdapter() {

					public void mouseClicked(MouseEvent e) {
						String getValue1 = sizeXField.getText();
						String getValue2 = sizeYField.getText();
						String getValue3 = wallCountField.getText();

						frame.dispose();
						solveMaze(Integer.parseInt(getValue1),
								Integer.parseInt(getValue2),
								Integer.parseInt(getValue3));
					}
				});

		JLabel fileName = new JLabel();
		fileName.setBounds(275, 150, 75, 25);
		frame.getContentPane().add(fileName);
		fileName.setText("nazwa pliku");

		JTextField fileNameField = new JTextField();
		fileNameField.setBounds(350, 150, 75, 25);
		frame.getContentPane().add(fileNameField);
		fileNameField.setText("maze.txt");

		JButton solveFromFileButton = new JButton("wczytaj i rozwiaz");
		solveFromFileButton.setBounds(275, 200, 150, 25);
		frame.getContentPane().add(solveFromFileButton);

		solveFromFileButton.addMouseListener(new SolveFromFileButtonMouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				String getValue1 = fileNameField.getText();

				frame.dispose();
				solveMaze(getValue1);
			}
		});

		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				frame.dispose();
			}
		});
	}

	class SolveGeneratedButtonMouseAdapter extends MouseAdapter {
	}

	class SolveFromFileButtonMouseAdapter extends MouseAdapter {
	}

	public static void main(String[] args) {
		Solution s1 = new Solution();
		// Solution s2= new Solution();
		s1.mazeSolver();
		// s1.solveMaze("maze.txt");

		// s2.solveMaze(4,5,2);

	}

}