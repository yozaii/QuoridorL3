package pack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Pawn {
	
	private int x;
	private int y;
	private Board board;
	private String color;
	private int yWin;
	private int numWalls;
	
	//Constructeur
	public Pawn(Board board, String Color) {
		this.board = board;
		this.color = Color;
		if (Color == "White") {
			board.GetTile(8, 16).SetHasPawn(Color);
			this.x = 8;
			this.y = 16;
			this.yWin = 0;
		}
		else if (Color == "Black") {
			board.GetTile(8, 0).SetHasPawn(Color);
			this.x = 8;
			this.y = 0;
			this.yWin = 16;
		}
		this.numWalls = 10;
	}
	
	
	
	//Methode pour renvoyer un set des deplacements possible
	public ArrayList<String> possibleMoves() {
		ArrayList<String> possibleMoves = new ArrayList<String>();
		
		//Reglage pour deplacement "Right"
		if (x<=14 && board.GetTile(x+1, y).GetHasWall() == false) {
			if (board.GetTile(x+2, y).GetHasPawn()=="None") {
				possibleMoves.add("Right");
			}
			else if (board.GetTile(x+2, y).GetHasPawn()!="None" && x<14 &&
					board.GetTile(x+3, y).GetHasWall() == false && x<=12) {//Pour un saut
				possibleMoves.add("Right");
			}
		}
		
		//Reglage pour deplacement "Left"
		if (x>=2 && board.GetTile(x-1, y).GetHasWall() == false) {
			if (board.GetTile(x-2, y).GetHasPawn()=="None") {
				possibleMoves.add("Left");
			}
			else if (board.GetTile(x-2, y).GetHasPawn()!="None" && x>2 && 
					board.GetTile(x-3, y).GetHasWall() == false && x>=4) {//Pour un saut
				possibleMoves.add("Left");
			}
		}
		
		//Reglage pour deplacement "Up"
		if (y<=14 && board.GetTile(x, y+1).GetHasWall() == false) {
			if (board.GetTile(x, y+2).GetHasPawn()=="None") {
				possibleMoves.add("Up");
			}
			else if (board.GetTile(x, y+2).GetHasPawn()!="None" && y<14 &&
					board.GetTile(x, y+3).GetHasWall() == false && y<=12) {//Pour un saut
				possibleMoves.add("Up");
			}
		}
		
		//Reglage pour deplacement "Down"
		if (y>=2 && board.GetTile(x, y-1).GetHasWall() == false) {
			if (board.GetTile(x, y-2).GetHasPawn()=="None") {
				possibleMoves.add("Down");
			}
			else if (board.GetTile(x, y-2).GetHasPawn()!="None" && y>2 &&
					board.GetTile(x, y-3).GetHasWall() == false && y>=4) {//Pour un saut
				possibleMoves.add("Down");
			}
		}
		
		//Reglage pour deplacement si pion oppose et en face vers le haut avec un mur derriere
		if (y<14 && board.GetTile(x, y+2).GetHasPawn() != "None" &&
				board.GetTile(x, y+3).GetHasWall() == true) {
			if (x<=14 && board.GetTile(x+1, y+2).GetHasWall() == false) {
				possibleMoves.add("Up-Right");
			}
			if (x>=2 && board.GetTile(x-1, y+2).GetHasWall() == false) {
				possibleMoves.add("Up-Left");
			}
		}
		
		//Reglage pour deplacement si pion oppose et en face vers le bas avec un mur derriere
		if (y>2 && board.GetTile(x, y-2).GetHasPawn() != "None" &&
				board.GetTile(x, y-3).GetHasWall() == true) {
			if (x<=14 && board.GetTile(x+1, y-2).GetHasWall() == false) {
				possibleMoves.add("Down-Right");
			}
			if (x>=2 && board.GetTile(x-1, y-2).GetHasWall() == false) {
				possibleMoves.add("Down-Left");
			}
		}
		
		//Reglage pour deplacement si pion oppose et en face vers le gauche avec un mur derriere
		if (x>2 && board.GetTile(x-2, y).GetHasPawn() != "None" &&
				board.GetTile(x-3, y).GetHasWall() == true) {
			if (y<=14 && board.GetTile(x-2, y+1).GetHasWall() == false) {
				possibleMoves.add("Up-Left");
			}
			if (y>=2 && board.GetTile(x-2, y-1).GetHasWall() == false) {
				possibleMoves.add("Down-Left");
			}
		}
		
		//Reglage pour deplacement si pion oppose et en face vers la droite avec un mur derriere
		if (x<14 && board.GetTile(x+2, y).GetHasPawn() != "None" &&
				board.GetTile(x+3, y).GetHasWall() == true) {
			if (y<=14 && board.GetTile(x+2, y+1).GetHasWall() == false) {
				possibleMoves.add("Up-Right");
			}
			if (y>=2 && board.GetTile(x+2, y-1).GetHasWall() == false) {
				possibleMoves.add("Down-Right");
			}
		}
		
		return possibleMoves;
	}
	
	
	
	
	
	//Methode de d�placement, retourne 1 en cas de succes , -1 en cas d'echec
	public int Move(String Direction) {
		
		int result =-1;	//Le retour pour cette m�thode
		
		switch(Direction) {
		
		case "Right":
			
			if (board.GetTile(x+2, y).GetHasPawn()=="None") {
				board.GetTile(x, y).SetHasPawn("None");
				board.GetTile(x+2, y).SetHasPawn(this.color);
				this.x = x+2;
			}
			//Saut si il y a un pion
			else {
				board.GetTile(x, y).SetHasPawn("None");
				board.GetTile(x+4,y).SetHasPawn(this.color);
				this.x = x+4;
			}
			break;

		case "Left":
			
			if (board.GetTile(x-2, y).GetHasPawn()=="None") {
				board.GetTile(x, y).SetHasPawn("None");
				board.GetTile(x-2, y).SetHasPawn(this.color);
				this.x = x-2;
			}
			//Saut si il y a un pion
			else {
				board.GetTile(x, y).SetHasPawn("None");
				board.GetTile(x-4,y).SetHasPawn(this.color);
				this.x = x-4;
			}
			break;
			
		case "Up":
			
			if (board.GetTile(x, y+2).GetHasPawn()=="None") {
				board.GetTile(x, y).SetHasPawn("None");
				board.GetTile(x, y+2).SetHasPawn(this.color);
				this.y = y+2;
				if(this.y == this.yWin) result = 2;
			}
			//Saut si il y a un pion
			else {
				board.GetTile(x, y).SetHasPawn("None");
				board.GetTile(x, y+4).SetHasPawn(this.color);
				this.y = y+4;
				if(this.y == this.yWin) result = 2;
			}
			break;
			
		case "Down":
			
			
			if (board.GetTile(x, y-2).GetHasPawn()=="None") {
				board.GetTile(x, y).SetHasPawn("None");
				board.GetTile(x, y-2).SetHasPawn(this.color);
				this.y = y-2;
				if(this.y == this.yWin) result = 2;
			}
			//Saut si il y a un pion
			else {
				board.GetTile(x, y).SetHasPawn("None");
				board.GetTile(x, y-4).SetHasPawn(this.color);
				this.y = y-4;
				if(this.y == this.yWin) result = 2;
			}
			break;
			
		case "Up-Right":
			
			board.GetTile(x, y).SetHasPawn("None");
			board.GetTile(x+2, y+2).SetHasPawn(this.color);
			this.y = y+2;
			this.x = x+2;
			if(this.y == this.yWin) result = 2;
			break;
			
		case "Down-Right":
			
			board.GetTile(x, y).SetHasPawn("None");
			board.GetTile(x+2, y-2).SetHasPawn(this.color);
			this.y = y-2;
			this.x = x+2;
			if(this.y == this.yWin) result = 2;
			break;
			
		case "Down-Left":
			
			board.GetTile(x, y).SetHasPawn("None");
			board.GetTile(x-2, y-2).SetHasPawn(this.color);
			this.y = y-2;
			this.x = x-2;
			if(this.y == this.yWin) result = 2;
			break;
			
		case "Up-Left":
			
			board.GetTile(x, y).SetHasPawn("None");
			board.GetTile(x-2, y+2).SetHasPawn(this.color);
			this.y = y+2;
			this.x = x-2;
			if(this.y == this.yWin) result = 2;
			break;
			
		default:
			
			result = -1;
			break;
		
		}
		System.out.print("+ " + result + " "); PrintPosition(); 
		return result;
	}
	
	//Retire 1 de numWalls
	public void decreaseNumWalls() {
		this.numWalls--;
		System.out.println(this.numWalls);
	}
	
	public void PrintPosition() {
		System.out.println("x: " + x + " y: " + y);
	}
	
	public boolean isThatYou(int x,int y) {
		if(x==this.x && y == this.y) return true;
		else return false;
	}
	
	public String getColor() {
		return this.color;
	}
	
	public int getNumWalls() {
		return this.numWalls;
	}
	
	
	
	
}
