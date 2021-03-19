package pack;

import java.util.Scanner;

public class Interface {
	
	/**
	 * Method which print the grid with walls and pawn
	 * @param board
	 */
	public static void printGrid(Board board) {
		for(int y=16; y>=0;y--) {
			System.out.print(y/10);
			System.out.print(y%10);
			System.out.print("|");
			for(int x=0;x<17;x++) {
				if(board.GetTile(x, y).GetHasWall()) System.out.print("# ");
				else if(board.GetTile(x, y).GetIsWallTile() && y%2==0) System.out.print("- ");
				else if(board.GetTile(x, y).GetHasPawn() == "White") {System.out.print("W ");}
				else if(board.GetTile(x, y).GetHasPawn() == "Black") {System.out.print("B ");}
				else System.out.print("  ");
			}
			System.out.print("|\n");
		}
		System.out.print("   0 0 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1\n");
		System.out.print("   0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6\n");
		System.out.print("\n");
	}
	
	public static void menu() {
		Scanner sc = new Scanner(System.in);
		Board board = new Board();
		Pawn p1 = new Pawn(board, "White");
		Pawn p2 = new Pawn(board, "Black");
		System.out.print("---BIENVENUE AU JEU DU QUORIDOR---\n");
		while(!p1.isWin && !p2.isWin) {
			menuBase(p1,board);
			if(!p1.isWin) {
				menuBase(p2,board);
			}
		}
		System.out.print("*************************************************************\n");
		System.out.print("|     BRAVO LA PARTIE EST FINIE, VOUS AVEZ GAGNEE !! ^^     |\n");
		System.out.print("*************************************************************\n\n\n");
		printGrid(board);
		sc.close();
	}
	
	public static void printMenuBase(Board board) {
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
	
	public static void menuBase(Pawn player, Board board) {
		System.out.print(player.getColor() + ":\n");
		printMenuBase(board);
		int choice = UtilEntree.scannerInt(1,4);
		switch(choice) {
		case 1 : menuDeplacement(player,board); break;
		case 2 : menuWall(player,board); break;
		case 3 : System.out.print("Au Revoir"); break;
		case 4 : player.Move("Down"); break;
		default : System.out.print("J'ai pas compris\n");
		}
	}
	
	public static void printMenuDeplacement(Board board) {
		System.out.print("************************************\n");
		System.out.print("|     Voici vos Possibilités :     |\n");
		System.out.print("|     1 : Aller en Haut            |\n");
		System.out.print("|     2 : Aller à Gauche           |\n");
		System.out.print("|     3 : Aller à Droite           |\n");
		System.out.print("|     4 : Aller en Bas             |\n");
		System.out.print("************************************\n");
	}
	
	public static void menuDeplacement(Pawn player, Board board) {
		printMenuDeplacement(board);
		int choice = UtilEntree.scannerInt(1,4);
		switch(choice) {
		case 1 : player.Move("Up"); break;
		case 2 : player.Move("Left"); break;
		case 3 : player.Move("Right"); break;
		case 4 : player.Move("Down"); break;
		default : System.out.print("J'ai pas compris\n");
		}
	}
	
	public static void menuWall(Pawn player, Board board) {
		boolean isSet = false;
		do {
			System.out.print("x : ");
			int x = UtilEntree.scannerInt(0,16);
			System.out.print("y : ");
			int y = UtilEntree.scannerInt(0,16);
			isSet = board.setWall(x, y);
		} while (!isSet);
	}
	

}
