package pack;

import java.util.LinkedList;

public class Board {

	private Tile[][] board;	//Le plateau
	private int dimension = 17;	//Les dimensions de plateau (dimension x dimension)
	
	
	//Constructeur
	public Board() {
		
		board = new Tile[dimension][dimension];
		
		//Cr�ation d'instance pour chaque Tile
		for (int i=0;i<dimension; i++)
			for (int j=0;j<dimension; j++)
				board[i][j] = new Tile();
		
		//2 boucles for imbriqu�e pour definir les cases qui peuvent contenir un mur
		//Nilam : J'ai corrigé quelques trucs pour que les murs soit bien placés
		for (int i=0; i<dimension; i++) {
			for (int j=0; j<dimension; j++) {
				if(j%2==1 || i%2==1) board[i][j].SetIsWallTile(true);
			}
		}
	}
	
	/*Place un mur d'une longueur de 3*/
	public boolean setWall(int x, int y, Pawn player, Pawn player2) {
		
		if(!(board[x][y].GetIsWallTile()) || board[x][y].GetHasWall() || (x%2==1 && y%2==1)  || x==16 || y==0) {
			//System.out.print("Cet emplacement ne peut pas contenir de mur (" + x + "," + y + ")\n");
			return false;
		} 
		else if(containsWall(x,y)) {
			//System.out.print("Cet emplacement contient déjà un mur(" + x + "," + y + ")\n");
			return false;
		}
		else if(player.getNumWalls()==0) {
			//System.out.print("Vous avez plus de murs � emplacer\n");
			return false;
		}
		else {
			if(y%2==1) for(int i=0;i<3;i++) board[x+i][y].SetHasWall(true);
			if(x%2==1) for(int i=0;i<3;i++) board[x][y-i].SetHasWall(true);
			LinkedList<Integer> l = AStar.AlgoAStar(player, this);
			LinkedList<Integer> l2 = AStar.AlgoAStar(player2, this);

			if (l2.size() == 0 || l.size() == 0 ) {
				System.out.println("Vous ne pouvez pas completement bloquer les pions : "+ x + " , " + y);
				this.undoWall(x, y, player);
				return false;
			}

			//System.out.print("Le mur a été placé x: " + x + "y: " + y);
			//System.out.println(player.getColor() + " , " +  player.getY() + " , " + player.getX() + " : " + l + " ; " + player2.getColor() + " , " +  player2.getY() + " , " + player2.getX() + " : " + l2);
			player.decreaseNumWalls();
			return true;
		}
	}
	
	public void undoWall(int x, int y, Pawn player) {
		if(y%2==1) for(int i=0;i<3;i++) board[x+i][y].SetHasWall(false);
		if(x%2==1) for(int i=0;i<3;i++) board[x][y-i].SetHasWall(false);
		//System.out.print("Le mur a été enleve\n");
		player.increaseNumWalls();
	}
	
	/*Cree une clone du board*/
	public Board clone() {
		Board b = new Board();//Board clone
		for (int i=0; i<b.getDimension(); i++)
			for (int j=0; j<b.getDimension(); j++) {
				b.setTile(i, j, this.board[i][j]);//Le Tile du nouveau board = Tile du this board
			}
		
		return b;
	}
	
	public boolean containsWall(int x,int y) {
		if(board[x][y].GetHasWall()) return true;
		for(int i=0;i<3;i++) {
			if(y%2==1) if(board[x+i][y].GetHasWall()) return true;
			if(x%2==1) if(board[x][y-i].GetHasWall()) return true;
		}
		return false;
	}
	
	public void removeWall(int x,int y){
		if(y%2==1) for(int i=0;i<3;i++) board[x+i][y].SetHasWall(false);
			if(x%2==1) for(int i=0;i<3;i++) board[x][y-i].SetHasWall(false);
	}
	
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
