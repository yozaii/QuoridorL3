package pack;

public class AI {
	
	public int eval (Board board, Pawn p1, Pawn p2) {
		return 1;
	}
	
	/*Algorithme Minimax
	 * retourne une tableau de chaine avec 2 valeurs: [eval, bestMove]
	 */
	public String[] miniMax(Board board, boolean maxPlayer, int depth, Pawn p1, Pawn p2 ) {
		
		if (depth == 0||  p1.dijsktra.size() == 0 || p2.dijsktra.size() == 0) {
			
			//Le retour ([eval, bestMove]
			String [] ret = {String.valueOf(eval(board,p1,p2)), "None"};
			return ret;
		}
		
		String[] retHolder;
		String move;
		String bestMove;
		int maxEval;
		int minEval;
		int currentEval;
		
		if (maxPlayer) {
			
			maxEval = Integer.MIN_VALUE;
			
			for (int i =0; i < p1.possibleMoves().size(); i++) {
				
				move = p1.possibleMoves().get(i);
				p1.Move(move);
				retHolder = miniMax(board, false, depth-1, p1, p2);
				currentEval = Integer.parseInt(retHolder[0]);
				p1.Move(p1.oppositeMove(move));//Annule le deplacement effectuer precedemment
				
				if (currentEval > maxEval) {
					maxEval = currentEval;
					bestMove = move;
				}
			}
			
			String [] ret = {String.valueOf(maxEval), bestMove};
			return ret;
		}
		
		else {
			
			minEval = Integer.MIN_VALUE;
			
			for (int i =0; i < p1.possibleMoves().size(); i++) {
				
				move = p1.possibleMoves().get(i);
				p1.Move(move);
				retHolder = miniMax(board, true, depth-1, p1, p2);
				currentEval = Integer.parseInt(retHolder[0]);
				p1.Move(p1.oppositeMove(move));//Annule le deplacement effectuer precedemment
				
				if (currentEval < minEval) {
					minEval = currentEval;
					bestMove = move;
				}
			}
			
			String [] ret = {String.valueOf(minEval), bestMove};
			return ret;
			
		}
	}
}
