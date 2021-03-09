package pack;

import java.util.Scanner;

public class Interface {
	
	//Methode qui permet de visualiser la grille
	public static void affichage(Board board) {
		System.out.print("   00000000001111111\n");
		System.out.print("   01234567890123456\n");
		for(int y=16; y>=0;y--) {
			System.out.print(y/10);
			System.out.print(y%10);
			System.out.print("|");
			for(int x=0;x<17;x++) {
				if(board.GetTile(x, y).GetHasWall()) System.out.print("#");
				else if(board.GetTile(x, y).GetIsWallTile() && y%2==0) System.out.print("-");
				else if(board.GetTile(x, y).GetHasPawn()) System.out.print("@");
				else System.out.print(" ");
			}
			System.out.print("|\n");
		}
		System.out.print("   00000000001111111\n");
		System.out.print("   01234567890123456\n");
		System.out.print("\n");
	}
	
	public static void menu() {
		Scanner sc = new Scanner(System.in);
		Board board = new Board();
		Pawn p1 = new Pawn(board, "White");
		Pawn p2 = new Pawn(board, "Black");
		board.setWall(3,4);
		board.setWall(3,8);
		board.setWall(6,15);
		board.setWall(11,10);
		System.out.print("***Bienvenue au Jeu du Quoridor***\n");
		System.out.print("Le joueur p1 commence :) \n");
		for(int i=0;i<5;i++) {
			int move;
			System.out.print("P1 : ");
			menuDeplacement(board);
			move = sc.nextInt();
			switch(move) {
			case 1 : p1.Move("Up"); break;
			case 2 : p1.Move("Left"); break;
			case 3 : p1.Move("Right"); break;
			case 4 : p1.Move("Down"); break;
			default : System.out.print("Gé pas compris\n");
			}
			System.out.print("P2 : ");
			menuDeplacement(board);
			move = sc.nextInt();
			switch(move) {
			case 1 : p2.Move("Up"); break;
			case 2 : p2.Move("Left"); break;
			case 3 : p2.Move("Right"); break;
			case 4 : p2.Move("Down"); break;
			default : System.out.print("Gé pas compris\n");
			}
		}
		sc.close();
	}
	
	public static void menuDeplacement(Board board) {
		System.out.print("Voici vos Possibilités :\n");
		System.out.print("1 : Aller en Haut\n");
		System.out.print("2 : Aller à Gauche\n");
		System.out.print("3 : Aller à Droite\n");
		System.out.print("4 : Aller en Bas\n");
		System.out.print("Voici le plateau :\n");
		affichage(board);
		System.out.print("Votre Choix : ");
	}
	

}
