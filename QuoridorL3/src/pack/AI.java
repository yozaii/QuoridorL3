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
	 * The proximity to the winning tiles gives a higher evaluation
	 * The more walls a player has, the higher the evaluation
	 * + for p1
	 * -  p2
	 */

	/**
	 * Total eval by calculation pathEval + wallEval
	 * @param board : The board that is being used
	 * @param p1 : Maximizing player (Pawn)
	 * @param p2 : Minimizing player (Pawn)
	 * @return : Total eval
	 */
	public static float eval (Board board, Pawn p1, Pawn p2) {
		
		
		float eval = 0;
		eval = pathEval(board, p1, p2) + wallEval(board, p1, p2);
		return eval;
		
	}
	
	/**
	 * Eval according to each pawn's distance to winning tiles
	 * @param board : The Board that is being used
	 * @param p1 : Maximizing player (Pawn)
	 * @param p2 : Minimizing player (Pawn)
	 * @return : the evaluation
	 */
	public static float pathEval(Board board, Pawn p1, Pawn p2) {
		
		float eval = 0;
		
		float eval1 = AStar.AlgoAStar(p1, board).size();//The length of AStar in number of tiles
		if (eval1 == 0) eval1 = Float.MAX_VALUE;//1000 since we can't divide by 0
		else {
			
			//Inverse function, the longer the path, the closer the evaluation is to 0
			eval1 = 1/eval1;
		}
		
		float eval2 = AStar.AlgoAStar(p2, board).size();//The length of AStar in number of tiles
		if (eval2 == 0) eval2 = Float.MAX_VALUE;//1000 since we can't divide by 0
		else {
			
			//Inverse function, the longer the path, the closer the evaluation is to 0
			eval2 = 1/eval2;
		}
		
		eval = (eval1 - eval2)*10;
		return eval;
	}
	
	
	/**
	 * Eval according to the number of walls a player has
	 * @param board : The Board that is being used
	 * @param p1 : Maximizing player (Pawn)
	 * @param p2 : Minimizing player (Pawn)
	 * @return : the evaluation
	 */
	public static float wallEval(Board board, Pawn p1, Pawn p2) {
		
		float nWalls1 = p1.getNumWalls();
		float nWalls2 = p2.getNumWalls();
		float eval1, eval2;
		float constant;
		
		/*------------------------------------------------------------*/
		/*---------Constant will determine the sign of the eval-------*/
		/*------------------------------------------------------------*/
		if (nWalls1>=nWalls2) constant = 1;
		else constant = -1;
		
		double difference = nWalls1 - nWalls2;
		
		//The eval is an exponential function of the difference between the two walls
		float eval = constant*(float)Math.pow((2/3)*difference, 2);
		
		return eval;
	}
	
	
	/**
	 * Minimax algorithm
	 * @param board : The Board being used
	 * @param maxPlayer : True if it is maximizingPlayer, False otherwise
	 * @param depth : depth of miniMax algorithm
	 * @param alpha : alpha in alpha-beta pruning
	 * @param beta : beta in alpha-beta pruning
	 * @param p1 : Pawn, maximizing player (White)
	 * @param p2 : Pawn, minimizing player (Black)
	 * @return : A table of two strings, first index contains the evaluation, second contains the bestMove
	 */
	public static String[] miniMax(Board board, boolean maxPlayer, int depth, float alpha, float beta, Pawn p1, Pawn p2 ) {
		
		/*------------------------------------------------------------*/
		/*----------If depth is 0, or if any player has won-----------*/
		/*------------------------------------------------------------*/
		if (depth == 0 || p1.getY() == p1.getYWin() || p2.getY() == p2.getYWin() ) {
			
			//returns a tuple [eval, bestMove]
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
		
		/*------------------------------------------------------------*/
		/*--------------If maximizing player is playing (p1)----------*/
		/*------------------------------------------------------------*/
		if (maxPlayer) {
			
			//boolean wallSuccess;
			maxEval = Integer.MIN_VALUE;
			ArrayList<String> pMoves = p1.possibleMoves();
			ArrayList<Integer> optWalls =  OptimalWall.optimalWall2(p1, p2, board, 5);
			//ArrayList<Integer> optWallsEnemy = OptimalWall.optimalWall2(p2, p1, board, 2);
			ArrayList<String> combination = UtilList.intStringComb(pMoves, optWalls);
			//combination = UtilList.intStringComb(combination, optWallsEnemy);
			
			/*------------------------------------------------------------*/
			/*--------------Loop to branch all possible moves-------------*/
			/*------------------------------------------------------------*/
			for (int i = 0; i < combination.size(); i++) {
				
				/*------------------------------------------------------------*/
				/*------------------------Pawn moves--------------------------*/
				/*------------------------------------------------------------*/
				if (i < pMoves.size()) {
					move = combination.get(i);
					p1.Move(move);
				}
				
				/*------------------------------------------------------------*/
				/*---------------------Wall placements------------------------*/
				/*------------------------------------------------------------*/
				else {
					move = combination.get(i) + "," + combination.get(i+1);
					int xWall = Integer.parseInt(combination.get(i));
					int yWall = Integer.parseInt(combination.get(i+1));
					boolean bool = board.setWall(xWall, yWall, p1, p2); //bool added to cut branch if wall placement fails
					if (bool == false) break;
				}
				
				/*------------------------------------------------------------*/
				/*--Recursion of miniMax and extraction of its return value---*/
				/*------------------------------------------------------------*/
				retHolder = miniMax(board, false, depth-1, alpha, beta, p1, p2);
				currentEval = Float.parseFloat(retHolder[0]);
				
				
				/*------------------------------------------------------------*/
				/*--------------------Undoing pawn moves----------------------*/
				/*------------------------------------------------------------*/
				if (i < pMoves.size()) {
					move = combination.get(i);
					p1.Move(p1.oppositeMove(move));//Undoes pawn move
				}
				
				/*------------------------------------------------------------*/
				/*--------------------Undoing wall placements-----------------*/
				/*------------------------------------------------------------*/
				else {
					int xWall = Integer.parseInt(combination.get(i));
					int yWall = Integer.parseInt(combination.get(i+1));
					board.undoWall(xWall, yWall, p1);//Undoes wall placement
					i++;
				}
				
				/*------------------------------------------------------------*/
				/*--------------------updating maxEval------------------------*/
				/*------------------------------------------------------------*/
				if (currentEval > maxEval) {
					maxEval = currentEval;
					bestMove = move;
				}
				
				/*------------------------------------------------------------*/
				/*--------------------Alpha-beta pruning----------------------*/
				/*------------------------------------------------------------*/
				alpha = Math.max(alpha, currentEval);
				if (beta<= alpha)
					break;
			}
			
			
			/*------------------------------------------------------------*/
			/*------------------updating return value---------------------*/
			/*------------------------------------------------------------*/
			String [] ret = {String.valueOf(maxEval), bestMove};
			//System.out.print("\tevalmax: " + ret[0] + "Move: " + ret[1]);
			return ret;
		}
		
		
		/*------------------------------------------------------------*/
		/*--------------If minimizing player is playing (p2)----------*/
		/*------------------------------------------------------------*/
		else {
			
			
			minEval = Integer.MAX_VALUE;
			ArrayList<String> pMoves = p2.possibleMoves();
			ArrayList<Integer> optWalls = OptimalWall.optimalWall2(p2, p1, board, 5);
			//ArrayList<Integer> optWallsEnemy =  OptimalWall.optimalWall2(p1, p2, board, 2);
			ArrayList<String> combination = UtilList.intStringComb(pMoves, optWalls);
			//combination = UtilList.intStringComb(combination, optWallsEnemy);
			
			/*------------------------------------------------------------*/
			/*--------------Loop to branch all possible moves-------------*/
			/*------------------------------------------------------------*/
			for (int i = 0; i < combination.size(); i++) {
				
				/*------------------------------------------------------------*/
				/*------------------------Pawn moves--------------------------*/
				/*------------------------------------------------------------*/
				if (i < pMoves.size()) {
					move = combination.get(i);
					p2.Move(move);
				}
				
				/*------------------------------------------------------------*/
				/*---------------------Wall placements------------------------*/
				/*------------------------------------------------------------*/
				else {
					move = combination.get(i) + "," + combination.get(i+1);
					int xWall = Integer.parseInt(combination.get(i));
					int yWall = Integer.parseInt(combination.get(i+1));
					boolean bool = board.setWall(xWall, yWall, p2, p1);//bool added to cut branch if wall placement fails
					if (bool == false) break;
				}
				
				/*------------------------------------------------------------*/
				/*--Recursion of miniMax and extraction of its return value---*/
				/*------------------------------------------------------------*/
				retHolder = miniMax(board, true, depth-1, alpha, beta, p1, p2);
				currentEval = Float.parseFloat(retHolder[0]);
			
				/*------------------------------------------------------------*/
				/*--------------------Undoing pawn moves----------------------*/
				/*------------------------------------------------------------*/
				if (i < pMoves.size()) {
					move = combination.get(i);
					p2.Move(p1.oppositeMove(move));//Undoes pawn move
				}
				
				/*------------------------------------------------------------*/
				/*--------------------Undoing wall placements-----------------*/
				/*------------------------------------------------------------*/
				else {
					int xWall = Integer.parseInt(combination.get(i));
					int yWall = Integer.parseInt(combination.get(i+1));
					board.undoWall(xWall, yWall, p2);//Undoes wall placement
					i++;
				}
				
				/*------------------------------------------------------------*/
				/*--------------------updating minEval------------------------*/
				/*------------------------------------------------------------*/
				if (currentEval < minEval) {
					minEval = currentEval;
					bestMove = move;
				}
				
				/*------------------------------------------------------------*/
				/*--------------------Alpha-beta pruning----------------------*/
				/*------------------------------------------------------------*/
				beta  = Math.min(beta, currentEval);
				if (beta<= alpha)
					break;
			}
			
			/*------------------------------------------------------------*/
			/*------------------updating return value---------------------*/
			/*------------------------------------------------------------*/
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
