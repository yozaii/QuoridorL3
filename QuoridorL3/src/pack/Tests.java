package pack;

import java.util.LinkedList;
import java.util.Scanner;

public class Tests {
	
	public static void main(String args[]) {
		
		Scanner sc = new Scanner(System.in);
		Board board = new Board();
		Pawn p1 = new Pawn(board, "White");
		Pawn p2 = new Pawn(board, "Black");
		Interface.printGrid(board);
		//LinkedList<Integer> l = AStar.AlgoAStar(p1, board);
		String[] s = AI.miniMax(board, true, 11, Integer.MIN_VALUE, Integer.MAX_VALUE, p1, p2);
		System.out.println (s[0] + s[1]);
		
		
	}

}
