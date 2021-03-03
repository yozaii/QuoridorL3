package pack;

public class Board {

	private Tile[][] board;	//Le plateau
	private int dimension = 17;	//Les dimensions de plateau (dimension x dimension)
	
	
	//Constructeur
	public Board() {
		
		board = new Tile[dimension][dimension];
		
		//Création d'instance pour chaque Tile
		for (int i=0;i<dimension; i++)
			for (int j=0;j<dimension; j++)
				board[i][j] = new Tile();
		
		//2 boucles for imbriquée pour definir les cases qui peuvent contenir un mur
		for (int i=1; i<dimension; i+=2) {
			for (int j=1; j<dimension; j+=2) {
				board[i][j].SetIsWallTile(true);
			}
		}
	}
	
	public Tile GetTile(int x, int y) {
		return board[x][y];
	}
}
