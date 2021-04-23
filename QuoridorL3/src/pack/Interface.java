package pack;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

public class Interface {
	
	private static int result;
	
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
		while(result != 2) {
			menuBase(p1,board, p2);
			if(result != 2) {
				menuBase(p2,board, p1);
			}
		}
		System.out.print("************************************************************\n");
		System.out.print("|     BRAVO LA PARTIE EST FINIE, VOUS AVEZ GAGNE !! ^^     |\n");
		System.out.print("************************************************************\n\n\n");
		printGrid(board);
		sc.close();
	}
	
	//Menu avec l'IA qui jouent le pion blanc
	public static void menuIA1(int depth) {
		Scanner sc = new Scanner(System.in);
		Board board = new Board();
		Pawn p1 = new Pawn(board, "White");
		Pawn p2 = new Pawn(board, "Black");
		System.out.print("---BIENVENUE AU JEU DU QUORIDOR---\n");
		while(result != 2) {
			String[] s = AI.miniMax(board, true, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, p1, p2);
			//System.out.println (s[0] + "Move: " + s[1]);
			//If move is wall move
			if (s[1].contains(",")) {
				int commaIndex = s[1].indexOf(',');
				int xWall = Integer.parseInt(s[1].substring(0, commaIndex));
				int yWall = Integer.parseInt(s[1].substring(commaIndex+1, s[1].length()));
				board.setWall(xWall, yWall, p1, p2);
			}
			
			//If move is pawn move
			else {
				result = p1.Move(s[1]);
			}
			if(result != 2) {
				menuBase(p2,board, p1);
			}
		}
		System.out.print("************************************************************\n");
		System.out.print("|     BRAVO LA PARTIE EST FINIE, VOUS AVEZ GAGNE !! ^^     |\n");
		System.out.print("************************************************************\n\n\n");
		printGrid(board);
		sc.close();
	}
	
	//Menu avec l'IA qui jouent le pion noir
	public static void menuIA2(int depth) {
		Scanner sc = new Scanner(System.in);
		Board board = new Board();
		Pawn p1 = new Pawn(board, "White");
		Pawn p2 = new Pawn(board, "Black");
		System.out.print("---BIENVENUE AU JEU DU QUORIDOR---\n");
		while(result != 2) {
			menuBase(p1,board, p2);
			if(result != 2) {
				String[] s = AI.miniMax(board, false, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, p1, p2);
				
				//If move is wall move
				if (s[1].contains(",")) {
					int commaIndex = s[1].indexOf(',');
					int xWall = Integer.parseInt(s[1].substring(0, commaIndex));
					int yWall = Integer.parseInt(s[1].substring(commaIndex+1, s[1].length()));
					board.setWall(xWall, yWall, p2, p1);
				}
				
				//If move is pawn move
				else {
					result = p2.Move(s[1]);
				}

			}
		}
		System.out.print("************************************************************\n");
		System.out.print("|     BRAVO LA PARTIE EST FINIE, VOUS AVEZ GAGNE !! ^^     |\n");
		System.out.print("************************************************************\n\n\n");
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
	
	public static void menuBase(Pawn player, Board board, Pawn player2) {
		System.out.print(player.getColor() + ":\n");
		printMenuBase(board);
		int choice = UtilEntree.scannerInt(1,4);
		switch(choice) {
		case 1 : menuDeplacement(player,board); break;
		case 2 : if (player.getNumWalls()> 0) {
					menuWall(player, board, player2);
				}
				else {
					System.out.println("Vous n'avez plus de mur!");
					menuBase(player, board, player2);
				}
				break;
		case 3 : System.out.print("Au Revoir"); break;
		case 4 : player.Move("Down"); break;
		default : System.out.print("J'ai pas compris\n");
		}
	}
	
	public static void printMenuDeplacement(Pawn player, Board board) {
		ArrayList<String> possibleMoves = player.possibleMoves();
		
		System.out.print("*****************************************\n");
		System.out.print("|\tVoici vos Possibilités :\t|\n");
		for (int i=0; i<possibleMoves.size(); i++)
			System.out.println("|\t" + (i+1) + " " + possibleMoves.get(i) +"\t\t\t\t|");
		System.out.print("*****************************************\n");
	}
	
	public static void menuDeplacement(Pawn player, Board board) {
		printMenuDeplacement(player, board);
		ArrayList<String> possibleMoves = player.possibleMoves();
		int choice = UtilEntree.scannerInt(1,possibleMoves.size());
		switch(choice) {
		case 1 : result = player.Move(possibleMoves.get(0)); break;
		case 2 : result = player.Move(possibleMoves.get(1)); break;
		case 3 : result = player.Move(possibleMoves.get(2)); break;
		case 4 : result = player.Move(possibleMoves.get(3)); break;
		case 5 : result = player.Move(possibleMoves.get(4)); break;
		case 6 : result = player.Move(possibleMoves.get(5)); break;
		case 7 : result = player.Move(possibleMoves.get(6)); break;
		case 8 : result = player.Move(possibleMoves.get(7)); break;
		default : System.out.print("J'ai pas compris\n");
		}
	}
	
	public static void menuWall(Pawn player, Board board, Pawn player2) {
		boolean isSet = false;
		do {
			System.out.print("x : ");
			int x = UtilEntree.scannerInt(0,16);
			System.out.print("y : ");
			int y = UtilEntree.scannerInt(0,16);
			isSet = board.setWall(x, y, player, player2);
		} while (!isSet);
	}
	

}
