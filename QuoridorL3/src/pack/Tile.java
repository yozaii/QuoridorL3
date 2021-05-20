package pack;

public class Tile {
	
	private boolean isWallTile = false;	//If the tile can contain a wall
	private boolean hasWall = false;	//If the tile contains a wall
	private String hasPawn = "None";	//If the tile contains a pawn
	
	
	public Tile clone() {
		Tile t = new Tile();
		t.SetIsWallTile(this.isWallTile);
		t.SetHasWall(this.hasWall);
		t.SetHasPawn(this.hasPawn);
		return t;
	}
	
	/**
	 * Modifies is wall value
	 * @param bool : true if it is a wall tile, false otherwise
	 */
	public void SetIsWallTile(boolean bool) {
		isWallTile = bool;
	}
	
	/**
	 * Modifies the value of hasWall
	 * @param bool : true if it can have a wall, false otherwise
	 */
	public void SetHasWall(boolean bool) {
		if (isWallTile == true) hasWall = bool;
	}
	
	/**
	 * Modifies the value of hasPawn
	 * @param color : hasPawn is the color of the pawn if it is not a wall tile
	 */
	public void SetHasPawn(String color) {
		if (isWallTile == false) hasPawn = color;
	}
	
	
	/*---------------------------------------------------------------------*/
	/*---------------------GETTERS AND SETTERS-----------------------------*/
	/*---------------------------------------------------------------------*/
	public boolean GetIsWallTile() {
		return isWallTile;
	}
	
	public boolean GetHasWall() {
		return hasWall;
	}
	
	public String GetHasPawn() {
		return hasPawn;
	}
	

}
