package MainPackage; /**
			INTELLIGENCE LAB
	course		: 	COMP 417 - Artificial Intelligence
	authors		:	A. Vogiatzis, N. Trigkas
	excercise	:	1st Programming
	term 		: 	Spring 2019-2020
	date 		:   March 2020
*/
import java.util.*;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Canvas;
import java.util.stream.IntStream;
import AlgoPackage.*;

class GridGenerator{
	public static void VisualizeGrid(String frame_name, int N, int M, int [] walls, int [] grass, int start_idx, int terminal_idx ){
		JFrame frame = new JFrame(frame_name);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Canvas canvas = new Drawing(N,M,walls,grass,start_idx,terminal_idx);
		canvas.setSize(M*30,N*30);
		frame.add(canvas);
		frame.pack();
		frame.setVisible(true);
	}
	public static void VisualizeGrid(String frame_name, int N, int M, int [] walls, int [] grass, int [] steps ,int start_idx, int terminal_idx ){
		JFrame frame = new JFrame(frame_name);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Canvas canvas = new Drawing(N,M,walls,grass, steps, start_idx,terminal_idx);
		canvas.setSize(M*30,N*30);
		frame.add(canvas);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * TODO QUESTIONS
	 * -What happens when small dimensions are used as parameters?
	 */

	public static void main(String[] args) {
		String frame = "Random World";
		Grid mygrid;
		if (args.length<1)
			mygrid = new Grid();
		else if (args[0].equals("-i")){
			mygrid = new Grid(args[1]);
			frame = args[1].split("/")[1];
		}else if (args[0].equals("-d")){
			mygrid = new Grid(Integer.parseInt(args[1]),Integer.parseInt(args[2]));
		}else{
			mygrid = new Grid("world_examples/default.world");
			frame = "default.world";
		}
		int N = mygrid.getNumOfRows();
		int M = mygrid.getNumOfColumns();

		//Visualize initial maze
		VisualizeGrid(frame,N,M,mygrid.getWalls(),mygrid.getGrass(),mygrid.getStartidx(),mygrid.getTerminalidx());


		int userChoice = 0;
		do{
			Scanner scanner = new Scanner(System.in);
			System.out.println("\nPlease choose the number of the algorithm to show results");
			System.out.println("Any other input will lead to termination");
			System.out.println("1. BFS");
			System.out.println("2. DFS");
			System.out.println("3. A*");
			System.out.println("4. LTRA*\n");


			try{
				userChoice = scanner.nextInt();
			}catch (InputMismatchException io){

			}

			//Exit status
			if(userChoice == 9){
				System.exit(9);
			}


//		}while(userChoice != 1 && userChoice != 2 && userChoice != 3 && userChoice != 4);


		if(userChoice == 1){	//BFS choice
			BFS mybfs = new BFS(mygrid, mygrid.getStart()[0],mygrid.getStart()[1]);

			int result = mybfs.BFS_search();

			if(result == -1){
				System.out.println("Something went wrong could not find route!");
			}else {
				System.out.println("Best path cost: " + result);
			}

			int[] allSteps = mybfs.getAllStepsMatrix(0);

			GridGenerator.VisualizeGrid(frame,N,M,mygrid.getWalls(),mygrid.getGrass(),mybfs.getStepsMatrix(),mygrid.getStartidx(),mygrid.getTerminalidx());

		}else if(userChoice == 2){
			DFS mydfs = new DFS(mygrid, mygrid.getStart()[0],mygrid.getStart()[1]);

			int result = mydfs.DFS_Search();

			if(result == -1){
				System.out.println("Something went wrong could not find route!");
			}else {
				System.out.println("Best path cost: " + result);
			}

			int[] all_steps = mydfs.getAllStepsMatrix(0);

			GridGenerator.VisualizeGrid(frame,N,M,mygrid.getWalls(),mygrid.getGrass(),mydfs.getStepsMatrix(),mygrid.getStartidx(),mygrid.getTerminalidx());

		}else if(userChoice == 3){
			A_Star mystar = new A_Star(mygrid, mygrid.getStart()[0],mygrid.getStart()[1]);

			int result = mystar.A_StarSearch();

			if(result == -1){
				System.out.println("Something went wrong could not find route!");
			}else {
				System.out.println("Best path cost: " + result);
			}

			int[] all_steps = mystar.getAllStepsMatrix(0);

			GridGenerator.VisualizeGrid(frame,N,M,mygrid.getWalls(),mygrid.getGrass(),mystar.getStepsMatrix(),mygrid.getStartidx(),mygrid.getTerminalidx());

		}else if(userChoice == 4){
			LRTA_Star mystar = new LRTA_Star(mygrid, mygrid.getStart()[0],mygrid.getStart()[1]);

			int result = mystar.LRTA_StarSearch();

			if(result == -1){
				System.out.println("Something went wrong could not find route!");
			}else {
				System.out.println("Best path cost: " + result);
			}

			int[] all_steps = mystar.getAllStepsMatrix(0);

			GridGenerator.VisualizeGrid(frame,N,M,mygrid.getWalls(),mygrid.getGrass(),mystar.getStepsMatrix(),mygrid.getStartidx(),mygrid.getTerminalidx());

		}
		//Re-initialize every time visited table to get right results
		mygrid.setVisitedFalse();

		}while(userChoice != 9);


	}
		
}