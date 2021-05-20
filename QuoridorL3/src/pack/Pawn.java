package pack;

import java.util.ArrayList;

public class Pawn {
	
	private int x;
	private int y;
	private Board board;
	private String color;
	private int yWin;
	private int numWalls;
	
	/**
	 * Constructor 1
	 * @param board : the Board where the pawn will play
	 * @param Color : White or Black
	 */
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
	
	/**
	 * Constructor 2 
	 * @param board : the Board where the pawn will play
	 * @param x : the x position of the pawn
	 * @param y : the y position of the pawn
	 */
	public Pawn(Board board,int x,int y) {
		this.board = board;
		this.x=x;
		this.y=y;
		this.numWalls=10;
	}
	
	/**
	 * Method that returns a list of possible moves
	 * @return : list of possible moves
	 */
	public ArrayList<String> possibleMoves() {
		ArrayList<String> possibleMoves = new ArrayList<String>();
		
		/*------------------------------------------------------------*/
		/*--------------Settings to add "Right" move------------------*/
		/*------------------------------------------------------------*/
		if (x<=14 && board.GetTile(x+1, y).GetHasWall() == false) {
			if (board.GetTile(x+2, y).GetHasPawn()=="None") {
				possibleMoves.add("Right");
			}
			else if (board.GetTile(x+2, y).GetHasPawn()!="None" && x<14 &&
					board.GetTile(x+3, y).GetHasWall() == false && x<=12) {//For a jump
				possibleMoves.add("Right");
			}
		}
		
		/*------------------------------------------------------------*/
		/*--------------Settings to add "Left" move-------------------*/
		/*------------------------------------------------------------*/
		if (x>=2 && board.GetTile(x-1, y).GetHasWall() == false) {
			if (board.GetTile(x-2, y).GetHasPawn()=="None") {
				possibleMoves.add("Left");
			}
			else if (board.GetTile(x-2, y).GetHasPawn()!="None" && x>2 && 
					board.GetTile(x-3, y).GetHasWall() == false && x>=4) {//For a jump
				possibleMoves.add("Left");
			}
		}
		
		/*------------------------------------------------------------*/
		/*--------------Settings to add "Up" move---------------------*/
		/*------------------------------------------------------------*/
		if (y<=14 && board.GetTile(x, y+1).GetHasWall() == false) {
			if (board.GetTile(x, y+2).GetHasPawn()=="None") {
				possibleMoves.add("Up");
			}
			else if (board.GetTile(x, y+2).GetHasPawn()!="None" && y<14 &&
					board.GetTile(x, y+3).GetHasWall() == false && y<=12) {//For a jump
				possibleMoves.add("Up");
			}
		}
		
		/*------------------------------------------------------------*/
		/*--------------Settings to add "Down" move-------------------*/
		/*------------------------------------------------------------*/
		if (y>=2 && board.GetTile(x, y-1).GetHasWall() == false) {
			if (board.GetTile(x, y-2).GetHasPawn()=="None") {
				possibleMoves.add("Down");
			}
			else if (board.GetTile(x, y-2).GetHasPawn()!="None" && y>2 &&
					board.GetTile(x, y-3).GetHasWall() == false && y>=4) {//For a jump
				possibleMoves.add("Down");
			}
		}
		
		/*------------------------------------------------------------*/
		/*---------Settings to add "Up-Right and Up-Left" moves-------*/
		/*---(if the opponent pawn is upwards with a wall behind it)--*/
		/*------------------------------------------------------------*/
		if (y<14 && board.GetTile(x, y+2).GetHasPawn() != "None" &&
				board.GetTile(x, y+3).GetHasWall() == true) {
			if (x<=14 && board.GetTile(x+1, y+2).GetHasWall() == false) {
				possibleMoves.add("Up-Right");
			}
			if (x>=2 && board.GetTile(x-1, y+2).GetHasWall() == false) {
				possibleMoves.add("Up-Left");
			}
		}
		
		/*------------------------------------------------------------*/
		/*-------Settings to add "Down-Right and Down-Left" moves-----*/
		/*--(if the opponent pawn is downwards with a wall behind it)-*/
		/*------------------------------------------------------------*/
		if (y>2 && board.GetTile(x, y-2).GetHasPawn() != "None" &&
				board.GetTile(x, y-3).GetHasWall() == true) {
			if (x<=14 && board.GetTile(x+1, y-2).GetHasWall() == false) {
				possibleMoves.add("Down-Right");
			}
			if (x>=2 && board.GetTile(x-1, y-2).GetHasWall() == false) {
				possibleMoves.add("Down-Left");
			}
		}
		
		/*------------------------------------------------------------*/
		/*-------Settings to add "Up-Left and Down-Left" moves--------*/
		/*(if the opponent pawn is to the left with a wall behind it)-*/
		/*------------------------------------------------------------*/
		if (x>2 && board.GetTile(x-2, y).GetHasPawn() != "None" &&
				board.GetTile(x-3, y).GetHasWall() == true) {
			if (y<=14 && board.GetTile(x-2, y+1).GetHasWall() == false) {
				possibleMoves.add("Up-Left");
			}
			if (y>=2 && board.GetTile(x-2, y-1).GetHasWall() == false) {
				possibleMoves.add("Down-Left");
			}
		}
		
		/*------------------------------------------------------------*/
		/*-------Settings to add "Up-Right and "Down-Right" moves-----*/
		/*(if the opponent pawn is to the right with a wall behind it)*/
		/*------------------------------------------------------------*/
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
	
	/**
	 * Returns the opposite of a given move
	 * @param move : the move
	 * @return : returns opposite of move
	 */
	public String oppositeMove(String move) {
		String opp;
		
		switch (move) {
		
		case "Right":
			opp = "Left";
			break;
			
		case "Left":
			opp = "Right";
			break;
			
		case "Up":
			opp = "Down";
			break;
			
		case "Down":
			opp = "Up";
			break;
			
		case "Down-Right":
			opp = "Up-Left";
			break;
			
		case "Down-Left":
			opp = "Up-Right";
			break;
			

		case "Up-Right":
			opp = "Down-Left";
			break;
			
		case "Up-Left":
			opp = "Down-Right";
			break;
			
		default:
			opp = "Error";
			break;
		}
		
		return opp;
	}
	
	
	
	
	
	/**
	 * The movement method, returns 1 if success, -1 if failure, and 2 if the move wins
	 * @param Direction : Direction of the movement
	 * @return : 1 if success, -1 if failure, and 2 if the move wins
	 */
	public int Move(String Direction) {
		
		int result =-1;	//The return of the method
		
		switch(Direction) {
		
		case "Right":
			
			if (board.GetTile(x+2, y).GetHasPawn()=="None") {
				board.GetTile(x, y).SetHasPawn("None");
				board.GetTile(x+2, y).SetHasPawn(this.color);
				this.x = x+2;
			}
			
			/*------------------------------------------------------------*/
			/*--------------Jump if there is a pawn-----------------------*/
			/*------------------------------------------------------------*/
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
			
			/*------------------------------------------------------------*/
			/*--------------Jump if there is a pawn-----------------------*/
			/*------------------------------------------------------------*/
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
			
			/*------------------------------------------------------------*/
			/*--------------Jump if there is a pawn-----------------------*/
			/*------------------------------------------------------------*/
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
			
			/*------------------------------------------------------------*/
			/*--------------Jump if there is a pawn-----------------------*/
			/*------------------------------------------------------------*/
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
		return result;
	}
	
	/**
	 * Decreases numWalls by one
	 */
	public void decreaseNumWalls() {
		this.numWalls--;
		//System.out.println("Walls:" + this.numWalls);
	}
	
	/**
	 * Increases numWalls by one
	 */
	public void increaseNumWalls() {
		this.numWalls++;
	}
	
	/**
	 * Prints current position
	 */
	public void PrintPosition() {
		//System.out.println("x: " + x + " y: " + y);
	}
	
	public boolean isThatYou(int x,int y) {
		if(x==this.x && y == this.y) return true;
		else return false;
	}
	
	/*---------------------------------------------------------------------*/
	/*---------------------GETTERS AND SETTERS-----------------------------*/
	/*---------------------------------------------------------------------*/
	
	public String getColor() {
		return this.color;
	}
	
	public void setPosition(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
	public int[] getPosition() {
		int [] ret = {this.x, this.y};
		return ret;
	}
	
	public int getNumWalls() {
		return this.numWalls;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getYWin() {
		return this.yWin;
	}
	
	
	
}
