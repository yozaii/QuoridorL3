package pack;

public class Minimax {
	
	public static void minimaxDecision() {
		
	}
	
	public static int maxValue(Pawn player, Pawn bot) {
		if(player.isWin || bot.isWin) {
			if(bot.isWin) return 1;
			if(player.isWin) return -1;
		}
		else {
			
		}
		return 0;
		
	}
	
	public static void possibleState(Pawn p, Board b) {
		int choix = 0;
		int x=0,y=0;
		switch(choix) {
		case 1 : p.Move("Up"); break;
		case 2 : p.Move("Left"); break;
		case 3 : p.Move("Right"); break;
		case 4 : p.Move("Down"); break;
		case 5 : b.setWall(x, y); break;
		}
		
	}

}
