package pack;

public class Main {

	public static void main(String args[]) {
		
		
		Board board = new Board();
		Pawn p1 = new Pawn(board, "White");
		Pawn p2 = new Pawn(board, "Black");
		
		System.out.println("***************");
		System.out.print("P1: ");
		p1.PrintPosition();
		System.out.print("P2: ");
		p2.PrintPosition();
		
		for (int i=0;i<10;i++) {
			p1.Move("Up");
			System.out.println("***************");
			System.out.print("P1: ");
			p1.PrintPosition();
			System.out.print("P2: ");
			p2.PrintPosition();
		}
		
		p2.Move("Left");
		
		System.out.println("******Apres la boucle*********");
		System.out.print("P1: ");
		p1.PrintPosition();
		System.out.print("P2: ");
		p2.PrintPosition();
		
		p1.Move("Up");
		
		System.out.println("******Apres la boucle*********");
		System.out.print("P1: ");
		p1.PrintPosition();
		System.out.print("P2: ");
		p2.PrintPosition();
	}
}
