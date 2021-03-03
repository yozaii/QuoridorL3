package pack;

public class Tile {
	
	private boolean isWallTile = false;	//Si la case peut contenir un mur
	private boolean hasWall = false;	//Si la case contient un mur
	private boolean hasPawn = false;	//Si la case contient un pion
	

	
	//Modifie la valeur de isWall
	public void SetIsWallTile(boolean bool) {
		isWallTile = bool;
	}
	
	//Modifie la valeur de hasWall si la case peut contenir un mur
	public void SetHasWall(boolean bool) {
		if (isWallTile == true) hasWall = bool;
	}
	
	//Modifie la valeur de hasPawn
	public void SetHasPawn(boolean bool) {
		if (isWallTile == false) hasPawn = bool;
	}
	
	public boolean GetIsWallTile() {
		return isWallTile;
	}
	
	public boolean GetHasWall() {
		return hasWall;
	}
	
	public boolean GetHasPawn() {
		return hasPawn;
	}
	

}
