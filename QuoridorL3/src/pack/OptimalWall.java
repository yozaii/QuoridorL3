package pack;

import java.util.ArrayList;
import java.util.LinkedList;

public class OptimalWall {

    private static int optimalX;
    private static int optimalY;

/**
 * Walls to try for a single tile :
 * y+2 x-1
 * y+2 x+1
 * y+1 x-2
 * y+1 x
 * y x-1
 * y x+1
 * y-1 x-2
 * y-1 x
 */

    static public int optimalWall1(Pawn playerActive,Pawn player2, Board board){
    	
    	
        int x = player2.getX();
        int y = player2.getY();

        int xWall = -1;
        int YWall = -1;

        int bestWall = 0;

        if(y != 0) {
            if(x != 0) {
                if(board.setWall(x-2,y-1,playerActive,player2)) {
                    if(AStar.AlgoAStar(player2, board).size() > bestWall) {
                        bestWall = AStar.AlgoAStar(player2, board).size();
                        xWall = x-2;
                        YWall = y-1;
                    }
                    board.undoWall(x-2, y-1, playerActive);
                }
            }
            if(board.setWall(x,y-1,playerActive,player2)) {
                if(AStar.AlgoAStar(player2, board).size() > bestWall) {
                    bestWall = AStar.AlgoAStar(player2, board).size();
                    xWall = x;
                    YWall = y-1;
                }
                board.undoWall(x, y-1, playerActive);
            }
        }
        if(y != 16) {
            if(x != 0) {
                if(board.setWall(x-1,y+2,playerActive,player2)) {
                    if(AStar.AlgoAStar(player2, board).size() > bestWall) {
                        bestWall = AStar.AlgoAStar(player2, board).size();
                        xWall = x-1;
                        YWall = y+2;
                    }
                    board.undoWall(x-1, y+2, playerActive);
                }
                if(board.setWall(x-2,y+1,playerActive,player2)) {
                    if(AStar.AlgoAStar(player2, board).size() > bestWall) {
                        bestWall = AStar.AlgoAStar(player2, board).size();
                        xWall = x-2;
                        YWall = y+1;
                    }
                    board.undoWall(x-2, y+1, playerActive);
                }
            }
            if(x != 16) {
                if(board.setWall(x+1,y+2,playerActive,player2)) {
                    if(AStar.AlgoAStar(player2, board).size() > bestWall) {
                        bestWall = AStar.AlgoAStar(player2, board).size();
                        xWall = x+1;
                        YWall = y+2;
                    }
                    board.undoWall(x+1, y+2, playerActive);
                }
            }
            if(board.setWall(x,y+1,playerActive,player2)) {
                if(AStar.AlgoAStar(player2, board).size() > bestWall) {
                    bestWall = AStar.AlgoAStar(player2, board).size();
                    xWall = x;
                    YWall = y+1;
                }
                board.undoWall(x, y+1, playerActive);
            }
        }
        if(x != 0) {
            if(board.setWall(x-1,y,playerActive,player2)) {
                if(AStar.AlgoAStar(player2, board).size() > bestWall) {
                    bestWall = AStar.AlgoAStar(player2, board).size();
                    xWall = x-1;
                    YWall = y;
                }
                board.undoWall(x-1, y, playerActive);
            }
        }
        if(x != 16) {
            if(board.setWall(x+1,y,playerActive,player2)) {
                if(AStar.AlgoAStar(player2, board).size() > bestWall) {
                    bestWall = AStar.AlgoAStar(player2, board).size();
                    xWall = x+1;
                    YWall = y;
                }
                board.undoWall(x+1, y, playerActive);
            }
        }
        
        optimalX = xWall;
        optimalY = YWall;
        //System.out.println("1: x=" + xWall + " ; y=" + YWall);

        return bestWall;
    }

    static public ArrayList<Integer> optimalWall2(Pawn playerActive, Pawn player2, Board board) {
    	
    	
    	/*------------------------------------------------------*/
        /*----------Initializing wall coordinates---------------*/
    	/*------------------------------------------------------*/
        int xWall = -1;int yWall = -1;
        ArrayList<Integer> bestWall = new ArrayList<Integer>();

        /*------------------------------------------------------*/
        /*------------The path opponent pawn will take----------*/
        /*------------------------------------------------------*/
        LinkedList <Integer> tempAStar = AStar.AlgoAStar(player2, board);
        
        /*------------------------------------------------------*/
        /*--------------We iterate max of 5 times---------------*/
        /*--------------to avoid wide miniMax branches----------*/
        /*------------------------------------------------------*/
        int iter = tempAStar.size();
        if (iter > 5) iter = 5;
        for(int i = 0; i < iter; i++) {
        	
            int x = tempAStar.get(i)%17;
            int y = tempAStar.get(i)/17;
            
            //Temp pawn with new x y position according to AStar path
            Pawn tempPlayer = new Pawn(board,x,y);
            int tempOptWall1 = optimalWall1(playerActive, tempPlayer, board);
            xWall = optimalX;
            yWall = optimalY;
            if (xWall >= 0 && yWall >= 0) {
            	bestWall.add(xWall); bestWall.add(yWall);
            }
        }


        return bestWall;

    }
    
}