package pack;

import java.util.ArrayList;
import java.util.LinkedList;

public class AI {
	
	/*
	 * La proximite au cases de fin donne une evaluation plus forte
	 * + pour p1
	 * - pour p2
	 */
	/*
	 * La proximite au cases de fin donne une evaluation plus forte
	 * + pour p1
	 * - pour p2
	 */

	/*Total eval = pathEval + wallEval*/
	public static float eval (Board board, Pawn p1, Pawn p2) {
		
		
		float eval = 0;
		eval = pathEval(board, p1, p2) + wallEval(board, p1, p2);
		return eval;
		
	}
	
	/*Eval according to distance to victory*/
	public static float pathEval(Board board, Pawn p1, Pawn p2) {
		
		float eval = 0;
		
		float eval1 = AStar.AlgoAStar(p1, board).size();//La longueur du AStar en cases
		if (eval1 == 0) eval1 = Float.MAX_VALUE;//On peut pas diviser par 0, on met 1000
		else {
			
			//Fonction inverse, plus le chemin est long, plus l'evaluation s'approche du 0
			eval1 = 1/eval1;
		}
		
		float eval2 = AStar.AlgoAStar(p2, board).size();//La longueur du AStar en cases
		if (eval2 == 0) eval2 = Float.MAX_VALUE;//On peut pas diviser par 0, on met 1000
		else {
			
			//Fonction inverse, plus le chemin est long, plus l'evaluation s'approche du 0
			eval2 = 1/eval2;
		}
		
		eval = (eval1 - eval2)*10;
		return eval;
	}
	
	/*Eval according to number of walls*/
	public static float wallEval(Board board, Pawn p1, Pawn p2) {
		
		float nWalls1 = p1.getNumWalls();
		float nWalls2 = p2.getNumWalls();
		float eval1, eval2;
		float constant;
		
		if (nWalls1>=nWalls2) constant = 1;
		else constant = -1;
		
		double difference = nWalls1 - nWalls2;
		

		float eval = constant*(float)Math.pow((2/3)*difference, 2);
		
		return eval;
	}
	
	
	/*Algorithme Minimax
	 * retourne une tableau de chaine avec 2 valeurs: [eval, bestMove]
	 */
	public static String[] miniMax(Board board, boolean maxPlayer, int depth, float alpha, float beta, Pawn p1, Pawn p2 ) {
		
		if (depth == 0 || p1.getY() == p1.getYWin() || p2.getY() == p2.getYWin() ) {
			
			//Le retour ([eval, bestMove]
			String [] ret = {String.valueOf(eval(board,p1,p2)), "None"};
			//System.out.println("eval: " + ret[0] + "Move: " + ret[1]);
			return ret;
		}
		
		String[] retHolder;
		String move;
		String bestMove = "None";
		float maxEval;
		float minEval;
		float currentEval;
		
		
		if (maxPlayer) {	//Joueur max (p1)
			
			maxEval = Integer.MIN_VALUE;
			
			ArrayList<String> pMoves = p1.possibleMoves();
			ArrayList<Integer> optWalls =  OptimalWall.optimalWall2(p1, p2, board);
			ArrayList<String> combination = UtilList.intStringComb(pMoves, optWalls);
			
			//Boucles pour tous les moves possibles
			for (int i = 0; i < combination.size(); i++) {
				
				//Pawn move
				if (i < pMoves.size()) {
					move = combination.get(i);
					p1.Move(move);
				}
				//Wall placement
				else {
					move = combination.get(i) + "," + combination.get(i+1);
					int xWall = Integer.parseInt(combination.get(i));
					int yWall = Integer.parseInt(combination.get(i+1));
					boolean bool = board.setWall(xWall, yWall, p1, p2); //bool added to cut tree if wall placement fails
					if (bool == false) break;
				}
				
				
				retHolder = miniMax(board, false, depth-1, alpha, beta, p1, p2);
				currentEval = Float.parseFloat(retHolder[0]);
				
				
				//Pawn move
				if (i < pMoves.size()) {
					move = combination.get(i);
					p1.Move(p1.oppositeMove(move));//Annule le deplacement effectuer precedemment
				}
				//Wall placement
				else {
					int xWall = Integer.parseInt(combination.get(i));
					int yWall = Integer.parseInt(combination.get(i+1));
					board.undoWall(xWall, yWall, p1);//Annule le mur placer precedemment
					i++;
				}
				
				if (currentEval > maxEval) {
					maxEval = currentEval;
					bestMove = move;
				}
				alpha = Math.max(alpha, currentEval);
				if (beta<= alpha)
					break;
			}
			
			String [] ret = {String.valueOf(maxEval), bestMove};
			//System.out.print("\tevalmax: " + ret[0] + "Move: " + ret[1]);
			return ret;
		}
		
		else { //Joueur min (p2)
			
			minEval = Integer.MAX_VALUE;
			
			ArrayList<String> pMoves = p2.possibleMoves();
			ArrayList<Integer> optWalls = OptimalWall.optimalWall2(p2, p1, board);
			
			ArrayList<String> combination = UtilList.intStringComb(pMoves, optWalls);
			
			//Boucles pour tous les moves possibles
			for (int i = 0; i < p2.possibleMoves().size(); i++) {
				
				//Pawn move
				if (i < pMoves.size()) {
					move = combination.get(i);
					p2.Move(move);
				}
				//Wall placement
				else {
					move = combination.get(i) + "," + combination.get(i+1);
					int xWall = Integer.parseInt(combination.get(i));
					int yWall = Integer.parseInt(combination.get(i+1));
					boolean bool = board.setWall(xWall, yWall, p2, p1);//bool added to cut tree if wall placement fails
					if (bool == false) break;
				}
				
				
				retHolder = miniMax(board, true, depth-1, alpha, beta, p1, p2);
				currentEval = Float.parseFloat(retHolder[0]);
			
				//Pawn move
				if (i < pMoves.size()) {
					move = combination.get(i);
					p2.Move(p1.oppositeMove(move));//Annule le deplacement effectuer precedemment
				}
				
				//Wall placement
				else {
					int xWall = Integer.parseInt(combination.get(i));
					int yWall = Integer.parseInt(combination.get(i+1));
					board.undoWall(xWall, yWall, p2);//Annule le mur placer precedemment
					i++;
				}
				
				if (currentEval < minEval) {
					minEval = currentEval;
					bestMove = move;
				}
				beta  = Math.min(beta, currentEval);
				if (beta<= alpha)
					break;
			}
			
			String [] ret = {String.valueOf(minEval), bestMove};
			//System.out.print("\tevalmin: " + ret[0] + "Move: " + ret[1]);
			return ret;
			
		}
	}
	
	
	
	/*Algorithme Minimax
	 * retourne une tableau de chaine avec 2 valeurs: [eval, bestMove]
	 */
	public static String[] miniMaxNoWall(Board board, boolean maxPlayer, int depth, float alpha, float beta, Pawn p1, Pawn p2 ) {
		
		if (depth == 0||  p1.getY() == p1.getYWin()||  p2.getY() == p2.getYWin() ) {
			
			//Le retour ([eval, bestMove]
			String [] ret = {String.valueOf(eval(board,p1,p2)), "None"};
			//System.out.print("\t\t\teval: " + ret[0] + "Move: " + ret[1]);
			return ret;
		}
		
		String[] retHolder;
		String move;
		String bestMove = "None";
		float maxEval;
		float minEval;
		float currentEval;
		
		if (maxPlayer) {	//Joueur max (p1)
			
			maxEval = Integer.MIN_VALUE;
			
			//Boucles pour tous les moves possibles
			for (int i =0; i < p1.possibleMoves().size(); i++) {
				
				move = p1.possibleMoves().get(i);
				p1.Move(move);
				retHolder = miniMaxNoWall(board, false, depth-1, alpha, beta, p1, p2);
				currentEval = Float.parseFloat(retHolder[0]);
				p1.Move(p1.oppositeMove(move));//Annule le deplacement effectuer precedemment
				
				if (currentEval > maxEval) {
					maxEval = currentEval;
					bestMove = move;
				}
				alpha = Math.max(alpha, currentEval);
				if (beta<= alpha)
					break;
			}
			
			String [] ret = {String.valueOf(maxEval), bestMove};
			//System.out.print("\tevalmax: " + ret[0] + "Move: " + ret[1]);
			return ret;
		}
		
		else { //Joueur min (p2)
			
			minEval = Integer.MAX_VALUE;
			
			//Boucles pour tous les moves possibles
			for (int i = 0; i < p2.possibleMoves().size(); i++) {
				
				move = p2.possibleMoves().get(i);
				p2.Move(move);
				retHolder = miniMaxNoWall(board, true, depth-1, alpha, beta, p1, p2);
				currentEval = Float.parseFloat(retHolder[0]);
				p2.Move(p2.oppositeMove(move));//Annule le deplacement effectuer precedemment
				
				if (currentEval < minEval) {
					minEval = currentEval;
					bestMove = move;
				}
				beta  = Math.min(beta, currentEval);
				if (beta<= alpha)
					break;
			}
			
			String [] ret = {String.valueOf(minEval), bestMove};
			//System.out.print("\tevalmin: " + ret[0] + "Move: " + ret[1]);
			return ret;
			
		}
	}
	
	/*Algorithme Minimax
	 * retourne une tableau de chaine avec 2 valeurs: [eval, bestMove]
	 */
	public static String[] miniMaxNaive(Board board, boolean maxPlayer, int depth, Pawn p1, Pawn p2 ) {
		
		if (depth == 0||  p1.getY() == p1.getYWin()||  p2.getY() == p2.getYWin() ) {
			
			//Le retour ([eval, bestMove]
			String [] ret = {String.valueOf(eval(board,p1,p2)), "None"};
			//System.out.print("\t\t\teval: " + ret[0] + "Move: " + ret[1]);
			return ret;
		}
		
		String[] retHolder;
		String move;
		String bestMove = "None";
		float maxEval;
		float minEval;
		float currentEval;
		
		if (maxPlayer) {	//Joueur max (p1)
			
			maxEval = Integer.MIN_VALUE;
			
			//Boucles pour tous les moves possibles
			for (int i =0; i < p1.possibleMoves().size(); i++) {
				
				move = p1.possibleMoves().get(i);
				p1.Move(move);
				retHolder = miniMaxNaive(board, false, depth-1, p1, p2);
				currentEval = Float.parseFloat(retHolder[0]);
				p1.Move(p1.oppositeMove(move));//Annule le deplacement effectuer precedemment
				
				if (currentEval > maxEval) {
					maxEval = currentEval;
					bestMove = move;
				}
			}
			
			String [] ret = {String.valueOf(maxEval), bestMove};
			//System.out.print("\tevalmax: " + ret[0] + "Move: " + ret[1]);
			return ret;
		}
		
		else { //Joueur min (p2)
			
			minEval = Integer.MAX_VALUE;
			
			//Boucles pour tous les moves possibles
			for (int i = 0; i < p2.possibleMoves().size(); i++) {
				
				move = p2.possibleMoves().get(i);
				p2.Move(move);
				retHolder = miniMaxNaive(board, true, depth-1, p1, p2);
				currentEval = Float.parseFloat(retHolder[0]);
				p2.Move(p2.oppositeMove(move));//Annule le deplacement effectuer precedemment
				
				if (currentEval < minEval) {
					minEval = currentEval;
					bestMove = move;
				}
			}
			
			String [] ret = {String.valueOf(minEval), bestMove};
			//System.out.print("\tevalmin: " + ret[0] + "Move: " + ret[1]);
			return ret;
		}
	}
}
