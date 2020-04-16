package MainPackage; /**
			INTELLIGENCE LAB
	course		: 	COMP 417 - Artificial Intelligence
	authors		:	A. Vogiatzis, N. Trigkas
	excercise	:	1st Programming
	term 		: 	Spring 2019-2020
	date 		:   March 2020
*/
import java.util.Arrays;
import java.util.Random;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
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

		//TODO visualize init maze
		VisualizeGrid(frame,N,M,mygrid.getWalls(),mygrid.getGrass(),mygrid.getStartidx(),mygrid.getTerminalidx());

//		int[] start = mygrid.getStart();
//		int[] terminal = mygrid.getTerminal();
		int[] walls = mygrid.getWalls();
		int[] grass = mygrid.getGrass();
//		System.out.println("start: "+mygrid.getStart()[0]);
//		System.out.println("startid: "+mygrid.getStartidx());
//		System.out.println("terminal: "+mygrid.getTerminal()[0]);
//		System.out.println("terminalid: "+mygrid.getTerminalidx());


		int[][] grid2D = mygrid.gridto2D();

//TODO dfs part done
//		_DFS mydfs = new _DFS(mygrid.getStart()[0],mygrid.getStart()[1],N ,M,grid2D);
//
//		int[] steps = mydfs.getStepsMatrix();
//
//		VisualizeGrid(frame,N,M,mygrid.getWalls(),mygrid.getGrass(),steps,mygrid.getStartidx(),mygrid.getTerminalidx());
		BFS mybfs = new BFS(mygrid, mygrid.getStart()[0],mygrid.getStart()[1]);

		int BFSResult[] = mybfs._BFS();

		if(BFSResult[0] == 0) System.out.println("\nBFS Algorithm could not found route!\n");

		else System.out.println("\nMYYYY BFS Algorithm found route with cost: " + BFSResult[1] + " steps");

		GridGenerator.VisualizeGrid(frame,N,M,mygrid.getWalls(),mygrid.getGrass(),mybfs.getAllStepsMatrix(),mygrid.getStartidx(),mygrid.getTerminalidx());


//		_BFS mybfs = new _BFS(mygrid.getStart()[0],mygrid.getStart()[1],N ,M,grid2D);
//
//		int[] steps = mybfs.getStepsMatrix();
//
//		VisualizeGrid(frame,N,M,mygrid.getWalls(),mygrid.getGrass(),steps,mygrid.getStartidx(),mygrid.getTerminalidx());

//		_A_Star myAstar = new _A_Star(mygrid.getStart()[0],mygrid.getStart()[1],mygrid.getTerminal()[0],mygrid.getTerminal()[1],N ,M,grid2D);
//
//		int[] steps = myAstar.getStepsMatrix();
//
//		VisualizeGrid(frame,N,M,mygrid.getWalls(),mygrid.getGrass(),steps,mygrid.getStartidx(),mygrid.getTerminalidx());

	}
		
}