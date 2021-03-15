package pack;

import java.util.Scanner;

public class Interface {
	
	/**
	 * Method which print the grid with walls and pawn
	 * @param board
	 */
	public static void printGrid(Board board) {
		System.out.print("   0 0 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1\n");
		System.out.print("   0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6\n");
		for(int y=16; y>=0;y--) {
			System.out.print(y/10);
			System.out.print(y%10);
			System.out.print("|");
			for(int x=0;x<17;x++) {
				if(board.GetTile(x, y).GetHasWall()) System.out.print("# ");
				else if(board.GetTile(x, y).GetIsWallTile() && y%2==0) System.out.print("- ");
				else if(board.GetTile(x, y).GetHasPawn()) System.out.print("@ ");
				else System.out.print("  ");
			}
			System.out.print("|\n");
		}
		System.out.print("\n");
	}
	
	public static void menu() {
		Scanner sc = new Scanner(System.in);
		Board board = new Board();
		Pawn p1 = new Pawn(board, "White");
		Pawn p2 = new Pawn(board, "Black");
		System.out.print("---BIENVENUE AU JEU DU QUORIDOR---\n");
		System.out.print("Le joueur p1 commence :) (Joueur du Haut) \n");
		for(int i=0;i<5;i++) {
			int move;
			System.out.print("P1 : \n");
			printMenuDeplacement(board);
			move = UtilEntree.scannerInt(1,4);
			switch(move) {
			case 1 : p1.Move("Up"); break;
			case 2 : p1.Move("Left"); break;
			case 3 : p1.Move("Right"); break;
			case 4 : p1.Move("Down"); break;
			default : System.out.print("J'ai pas compris\n");
			}
			System.out.print("P2 : \n");
			printMenuDeplacement(board);
			move = UtilEntree.scannerInt(1,4);
			switch(move) {
			case 1 : p2.Move("Up"); break;
			case 2 : p2.Move("Left"); break;
			case 3 : p2.Move("Right"); break;
			case 4 : p2.Move("Down"); break;
			default : System.out.print("J'ai pas compris\n");
			}
		}
		sc.close();
	}
	
	public static void printMenuDeplacement(Board board) {
		System.out.print("************************************\n");
		System.out.print("|     Voici vos Possibilités :     |\n");
		System.out.print("|     1 : Aller en Haut            |\n");
		System.out.print("|     2 : Aller à Gauche           |\n");
		System.out.print("|     3 : Aller à Droite           |\n");
		System.out.print("|     4 : Aller en Bas             |\n");
		System.out.print("************************************\n");
		System.out.print("Le plateau :\n");
		printGrid(board);
	}
	
	public static void printMenuChoix(Board board) {
		System.out.print("************************************\n");
		System.out.print("|     Voici vos Possibilités :     |\n");
		System.out.print("|     1 : Deplacer votre Pion      |\n");
		System.out.print("|     2 : Poser un mur             |\n");
		System.out.print("|     3 : Abandonner               |\n");
		System.out.print("|     4 : Consulter les règles     |\n");
		System.out.print("************************************\n");
		System.out.print("Le plateau :\n");
		printGrid(board);
	}
	

}
