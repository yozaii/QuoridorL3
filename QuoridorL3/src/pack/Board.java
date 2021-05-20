package pack;

import java.util.LinkedList;

public class Board {

	private Tile[][] board;	//The board
	private int dimension = 17;	//The board dimensions (dimension x dimension)
	
	
	/**
	 * Constructor
	 */
	public Board() {
		
		board = new Tile[dimension][dimension];
		
		/*------------------------------------------------------------*/
		/*-------------Creating an instance of every tile-------------*/
		/*------------------------------------------------------------*/
		for (int i=0;i<dimension; i++)
			for (int j=0;j<dimension; j++)
				board[i][j] = new Tile();
		
		/*------------------------------------------------------------*/
		/*-------------2 nested for loops to define tiles-------------*/
		/*-------------which can hold walls---------------------------*/
		/*------------------------------------------------------------*/
		for (int i=0; i<dimension; i++) {
			for (int j=0; j<dimension; j++) {
				if(j%2==1 || i%2==1) board[i][j].SetIsWallTile(true);
			}
		}
	}
	
	/**
	 * Placing a wall with a length of 3 tiles
	 * @param x : x coordinate of wall
	 * @param y : y coordinate of wall
	 * @param player : the player (Pawn) who places the wall
	 * @param player2 : the opponent player (Pawn)
	 * @return : boolean determining if wall was placed successfully or not
	 */
	public boolean setWall(int x, int y, Pawn player, Pawn player2) {
		
		/*------------------------------------------------------------*/
		/*--------------if wall is placed outside the board-----------*/
		/*------------------------------------------------------------*/
		if(x<0 || x>16 || y<0 || y>16) return false;
		
		/*------------------------------------------------------------*/
		/*--------------if x y argument combination is incorrect------*/
		/*------------------------------------------------------------*/
		if(!(board[x][y].GetIsWallTile()) || board[x][y].GetHasWall() || (x%2==1 && y%2==1)  || x==16 || y==0) {
			//System.out.print("Cet emplacement ne peut pas contenir de mur (" + x + "," + y + ")\n");
			return false;
		}
		
		/*------------------------------------------------------------*/
		/*--------------if there is an already existing wall----------*/
		/*------------------------------------------------------------*/
		else if(containsWall(x,y)) {
			//System.out.print("Cet emplacement contient déjà un mur(" + x + "," + y + ")\n");
			return false;
		}
		
		
		/*------------------------------------------------------------*/
		/*--------------if player has no more walls to place----------*/
		/*------------------------------------------------------------*/
		else if(player.getNumWalls()==0) {
			//System.out.print("Vous avez plus de murs � emplacer\n");
			return false;
		}
		
		/*------------------------------------------------------------*/
		/*----if wall completely blocks a player's path to victory----*/
		/*------------------------------------------------------------*/
		else {
			if(y%2==1) for(int i=0;i<3;i++) board[x+i][y].SetHasWall(true);
			if(x%2==1) for(int i=0;i<3;i++) board[x][y-i].SetHasWall(true);
			LinkedList<Integer> l = AStar.AlgoAStar(player, this);
			LinkedList<Integer> l2 = AStar.AlgoAStar(player2, this);

			if (l2.size() == 0 || l.size() == 0 ) {
				System.out.print("Vous ne pouvez pas completement bloquer les pions");
				this.undoWall(x, y, player);
				return false;
			}

			//System.out.print("Le mur a été placé x: " + x + "y: " + y);
			//System.out.println(player.getColor() + " , " +  player.getY() + " , " + player.getX() + " : " + l + " ; " + player2.getColor() + " , " +  player2.getY() + " , " + player2.getX() + " : " + l2);
			player.decreaseNumWalls();
			return true;
		}
	}
	
	/**
	 * Undoes a 3 length wall and increments the player's numWalls
	 * @param x : x coordinate of wall
	 * @param y : y coordinate of wall
	 * @param player : player (Pawn) that undoes the walls
	 */
	public void undoWall(int x, int y, Pawn player) {
		if(y%2==1) for(int i=0;i<3;i++) board[x+i][y].SetHasWall(false);
		if(x%2==1) for(int i=0;i<3;i++) board[x][y-i].SetHasWall(false);
		//System.out.print("Le mur a été enleve\n");
		player.increaseNumWalls();
	}
	
	/**
	 * Clones this Board
	 * @return : the new Board
	 */
	public Board clone() {
		Board b = new Board();//Board clone
		for (int i=0; i<b.getDimension(); i++)
			for (int j=0; j<b.getDimension(); j++) {
				b.setTile(i, j, this.board[i][j]);//Le Tile du nouveau board = Tile du this board
			}
		
		return b;
	}
	
	/**
	 * Returns true if a tile contains a wall
	 * @param x : x coordinate of tile to test
	 * @param y : y coordinate of tile to test
	 * @return : true if contains, false otherwise
	 */
	public boolean containsWall(int x,int y) {
		if(board[x][y].GetHasWall()) return true;
		for(int i=0;i<3;i++) {
			if(y%2==1) if(board[x+i][y].GetHasWall()) return true;
			if(x%2==1) if(board[x][y-i].GetHasWall()) return true;
		}
		return false;
	}
	
	/**
	 * Removes a wall from a tile
	 * @param x : x coordinate of a tile
	 * @param y : y coordinate of a tile
	 */
	public void removeWall(int x,int y){
		if(y%2==1) for(int i=0;i<3;i++) board[x+i][y].SetHasWall(false);
			if(x%2==1) for(int i=0;i<3;i++) board[x][y-i].SetHasWall(false);
	}
	
	/*---------------------------------------------------------------------*/
	/*---------------------GETTERS AND SETTERS-----------------------------*/
	/*---------------------------------------------------------------------*/
	
	public Tile GetTile(int x, int y) {
		return board[x][y];
	}
	
	public void setTile(int x, int y, Tile t) {
		this.board[x][y] = t.clone();
	}
	
	public int getDimension() {
		return dimension;
	}
}
