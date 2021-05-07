package pack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Tests {
	
	public static void main(String args[]) {
		
		long startTime = System.nanoTime();
		
		
		Board board = new Board();
		Pawn p1 = new Pawn(board, "White");
		Pawn p2 = new Pawn(board, "Black");
		p1.Move("Down");
		p1.Move("Down");
		p1.Move("Down");

		Interface.printGrid(board);
		
		int depth = 5;
		String[] s = AI.miniMax(board, true, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, p1, p2);
		//String[] s = AI.miniMaxNoWall(board, true, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, p1, p2);

		Interface.printGrid(board);
		//String[] s = AI.miniMaxNaive(board, true, depth,  p1, p2);
		

		//System.out.println(p1.getNumWalls());
		System.out.println (s[0] + "Move: " + s[1]);
		System.out.println(OptimalWall.optimalWall2(p1, p2, board).toString());
		
		System.out.println(" eval: " + AI.eval(board, p1, p2));
		
		
		long endTime = System.nanoTime();
		long duration = (endTime - startTime)/ 1000000;  //divide by 1000000 to get milliseconds.
		System.out.println("Time = " +  duration + " ms");
		
	}

}
