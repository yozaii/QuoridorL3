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
	
	
	public void setWall(int x, int y) {
		if(!(board[x][y].GetIsWallTile()) || board[x][y].GetHasWall() || (x%2==1 && y%2==1) || x>14 || y<2) {
			System.out.print("Cet emplacement ne peut pas contenir de mur");
		}
		else {
			if(y%2==1) {
				board[x][y].SetHasWall(true);
				board[x+1][y].SetHasWall(true);
				board[x+2][y].SetHasWall(true);
			}
			if(x%2==1) {
				board[x][y].SetHasWall(true);
				board[x][y-1].SetHasWall(true);
				board[x][y-2].SetHasWall(true);
			}
			
		}
	}
	public Tile GetTile(int x, int y) {
		return board[x][y];
	}
	public int getDimension() {
		return dimension;
	}
}
