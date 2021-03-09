package pack;

public class Pawn {
	
	private int x;
	private int y;
	private Board board;
	private int yWin;
	
	//Constructeur
	public Pawn(Board board, String Color) {
		this.board = board;
		if (Color == "White") {
			board.GetTile(8, 0).SetHasPawn(true);
			this.x = 8;
			this.y = 16;
			this.yWin = 16;
		}
		else if (Color == "Black") {
			board.GetTile(8, 16).SetHasPawn(true);
			this.x = 8;
			this.y = 0;
			this.yWin = 0;
		}
	}
	
	//Methode de d�placement, retourne 1 en cas de succes , -1 en cas d'echec
	public int Move(String Direction) {
		
		int result;	//Le retour pour cette m�thode
		
		switch(Direction) {
		
		case "Right":
			
			/*On peut se deplacer a droite si on est pas dans la case d'�xtremit� droite && qu'il n'y a pas de mur a droite
			 * && qu'il n'y a pas d'autre pion ou on veut se deplacer
			 */
			if (x<=14 && board.GetTile(x+1, y).GetHasWall() == false && board.GetTile(x+2, y).GetHasPawn()==false) {
				board.GetTile(x, y).SetHasPawn(false);
				board.GetTile(x+2, y).SetHasPawn(true);
				this.x = x+2;
				result = 1;
			}
			else result =-1;
			break;

		case "Left":
			
			/*On peut se deplacer a gauche si on est pas dans la case d'�xtremit� gauche && qu'il n'y a pas de mur a gauche
			 * && qu'il n'y a pas d'autre pion ou on veut se deplacer
			 */
			if (x>=2 && board.GetTile(x-1, y).GetHasWall() == false && board.GetTile(x-2, y).GetHasPawn()==false) {
				board.GetTile(x, y).SetHasPawn(false);
				board.GetTile(x-2, y).SetHasPawn(true);
				this.x = x-2;
				result = 1;
			}
			else result =-1;
			break;
			
		case "Up":
			
			/*On peut se deplacer en haut si on est pas dans la case �xtremit� d'en haut && qu'il n'y a pas de mur en haut
			 * && qu'il n'y a pas d'autre pion ou on veut se deplacer
			 */
			if (y<=14 && board.GetTile(x, y+1).GetHasWall() == false && board.GetTile(x, y+2).GetHasPawn()==false) {
				board.GetTile(x, y).SetHasPawn(false);
				board.GetTile(x, y+2).SetHasPawn(true);
				this.y = y+2;
				result = 1;
			}
			else result =-1;
			break;
			
		case "Down":
			
			/*On peut se deplacer en bas si on est pas dans la case �xtremit� d'en bas && qu'il n'y a pas de mur en bas
			 * && qu'il n'y a pas d'autre pion ou on veut se deplacer
			 */
			if (y>=2 && board.GetTile(x, y-1).GetHasWall() == false && board.GetTile(x, y-2).GetHasPawn()==false) {
				board.GetTile(x, y).SetHasPawn(false);
				board.GetTile(x, y-2).SetHasPawn(true);
				this.y = y-2;
				result = 1;
			}
			else result =-1;
			break;
			
		default:
			
			result = -1;
			break;
		
		}
		
		return result;
	}
	
	public void PrintPosition() {
		System.out.println("x: " + x + " y: " + y);
	}
	
	
	
	
}
