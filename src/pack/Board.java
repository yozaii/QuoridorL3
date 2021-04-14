package pack;

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
	
	

	public boolean setWall(int x, int y, Pawn player) {
		if(!(board[x][y].GetIsWallTile()) || board[x][y].GetHasWall() || (x%2==1 && y%2==1) || x==16 || y==0) {
			System.out.print("Cet emplacement ne peut pas contenir de mur (" + x + "," + y + ")\n");
			return false;
		}
		else if(containsWall(x,y)) {
			System.out.print("Cet emplacement contient déjà un mur(" + x + "," + y + ")\n");
			return false;
		}
		else if(player.getNumWalls()==0) {
			System.out.print("Vous avez plus de murs � emplacer\n");
			return false;
		}
		else {
			if(y%2==1) for(int i=0;i<3;i++) board[x+i][y].SetHasWall(true);
			if(x%2==1) for(int i=0;i<3;i++) board[x][y-i].SetHasWall(true);
			System.out.print("Le mur a été placé\n");
			player.decreaseNumWalls();
			return true;
		}
	}
	
	public boolean containsWall(int x,int y) {
		if(board[x][y].GetHasWall()) return true;
		for(int i=0;i<3;i++) {
			if(y%2==1) if(board[x+i][y].GetHasWall()) return true;
			if(x%2==1) if(board[x][y-i].GetHasWall()) return true;
		}
		return false;
	}
	public Tile GetTile(int x, int y) {
		return board[x][y];
	}
	public int getDimension() {
		return dimension;
	}
}
