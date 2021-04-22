package pack;

import java.util.ArrayList;
import java.util.LinkedList;

public class AI {
	
	/*
	 * La proximite au cases de fin donne une evaluation plus forte
	 * + pour p1
	 * - pour p2
	 */
	public static float eval (Board board, Pawn p1, Pawn p2) {
		
		
		float eval = 0;
		
		
		float eval1 = AStar.AlgoAStar(p1, board).size();//La longueur du AStar en cases
		if (eval1 == 0) eval1 = 1000;//On peut pas diviser par 0, on met 1000
		else {
			
			//Fonction inverse, plus le chemin est long, plus l'evaluation s'approche du 0
			eval1 = 1/eval1; 
		}
		
		
		float eval2 = AStar.AlgoAStar(p2, board).size();//La longueur du AStar en cases
		if (eval2 == 0) eval2 = 1000;//On peut pas diviser par 0, on met 1000
		else {
			
			//Fonction inverse, plus le chemin est long, plus l'evaluation s'approche du 0
			eval2 = 1/eval2;
		}
		
		eval = (eval1 - eval2) * 10;
		
		return eval;
	}
	
	/*Algorithme Minimax
	 * retourne une tableau de chaine avec 2 valeurs: [eval, bestMove]
	 */
	public static String[] miniMax(Board board, boolean maxPlayer, int depth, float alpha, float beta, Pawn p1, Pawn p2 ) {
		
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
			
			
			ArrayList<String> pMoves = p1.possibleMoves();
			ArrayList<Integer> optWalls = OptimalWall.optimalWall2(p1, board);
			
			/*Transformation des list int a des list string*/
			ArrayList<String> newOptWalls = new ArrayList<>(optWalls.size());
			for (Integer myInt : optWalls) { 
				newOptWalls.add(String.valueOf(myInt)); 
			}
			
			
			/*La combinaison des possible moves avec optimalWall*/
			ArrayList<String> combination = new ArrayList<String>(pMoves);
			combination.addAll(newOptWalls);
			
			
			//Boucles pour tous les moves possibles
			for (int i =0; i < combination.size(); i++) {
				
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
					board.setWall(xWall, yWall, p1);
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
			ArrayList<Integer> optWalls = OptimalWall.optimalWall2(p2, board);
			
			/*Transformation des list int a des list string*/
			ArrayList<String> newOptWalls = new ArrayList<>(optWalls.size());
			for (Integer myInt : optWalls) { 
				newOptWalls.add(String.valueOf(myInt)); 
			}
			
			
			/*La combinaison des possible moves avec optimalWall*/
			ArrayList<String> combination = new ArrayList<String>(pMoves);
			combination.addAll(newOptWalls);
			
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
					board.setWall(xWall, yWall, p2);
				}
				
				
				retHolder = miniMax(board, false, depth-1, alpha, beta, p1, p2);
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
