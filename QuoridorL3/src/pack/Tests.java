package pack;

import java.util.Scanner;

public class Tests {
	
	public static void main(String args[]) {
		
		Scanner sc = new Scanner(System.in);
		Board board = new Board();
		Pawn p1 = new Pawn(board, "White");
		Pawn p2 = new Pawn(board, "Black");
		Interface.printGrid(board);
		p1.Move("Down");
		board.setWall(5, 8, p1);
		Board newBrd = board.clone();
		System.out.println("New**************");
		Pawn cl1 = new Pawn(newBrd, p1.getColor());
		newBrd.GetTile(cl1.getPosition()[0], cl1.getPosition()[1]).SetHasPawn("None");
		cl1.setPosition(p1.getPosition()[0], p1.getPosition()[1]);
		cl1.Move("Right");
		p1.Move("Down");
		p1.Move("Down");
		Interface.printGrid(newBrd);
		System.out.println("Old**************");
		Interface.printGrid(board);
		
	}

}
