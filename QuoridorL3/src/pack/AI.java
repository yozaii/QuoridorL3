package pack;

public class AI {
	
	
	public int miniMax(Board board, boolean maxPlayer, int depth, Pawn p1, Pawn p2 ) {
		if (depth == 0||  p1.dijsktra.size() == 0 || p2.dijsktra.size() == 0) {
			return eval;
		}
		
		if (maxPlayer) {
			int maxEval = Integer.MIN_VALUE;
			for 
		}
	}
	
	function minimax(position, depth, maximizingPlayer)
	if depth == 0 or game over in position
		return static evaluation of position
 
	if maximizingPlayer
		maxEval = -infinity
		for each child of position
			eval = minimax(child, depth - 1, false)
			maxEval = max(maxEval, eval)
		return maxEval
 
	else
		minEval = +infinity
		for each child of position
			eval = minimax(child, depth - 1, true)
			minEval = min(minEval, eval)
		return minEval
 
 
// initial call
minimax(currentPosition, 3, true)
	

}
