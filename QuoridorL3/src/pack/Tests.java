package pack;

import java.util.LinkedList;
import java.util.Scanner;

public class Tests {
	
	public static void main(String args[]) {
		
		long startTime = System.nanoTime();
		
		
		Board board = new Board();
		Pawn p1 = new Pawn(board, "White");
		Pawn p2 = new Pawn(board, "Black");
		Interface.printGrid(board);
		
		int depth = 9;
		String[] s = AI.miniMax(board, false, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, p1, p2);
		Interface.printGrid(board);
		//String[] s = AI.miniMaxNaive(board, true, depth,  p1, p2);
		System.out.println (s[0] + s[1]);
		
		
		long endTime = System.nanoTime();
		long duration = (endTime - startTime)/ 1000000;  //divide by 1000000 to get milliseconds.
		System.out.println("Time = " +  duration + " ms");
		
	}

}
