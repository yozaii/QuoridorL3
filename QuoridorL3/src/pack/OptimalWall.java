package pack;

import java.util.ArrayList;

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

    static public int optimalWall1(Pawn player, Board board){

        int x = player.getX();
        int y = player.getY();

        int xWall = -1;
        int YWall = -1;

        int bestWall = AStar.AlgoAStar(player, board).size();

        if(y != 0) {
            if(x != 0) {
                if(board.setWall(x-2,y-1,player)) {
                    if(AStar.AlgoAStar(player, board).size() > bestWall) {
                        bestWall = AStar.AlgoAStar(player, board).size();
                        xWall = x-2;
                        YWall = y-1;
                        player.increaseNumWalls();
                    }
                    board.removeWall(x-2, y-1);
                }
            }
            if(board.setWall(x,y-1,player)) {
                if(AStar.AlgoAStar(player, board).size() > bestWall) {
                    bestWall = AStar.AlgoAStar(player, board).size();
                    xWall = x;
                    YWall = y-1;
                    player.increaseNumWalls();
                }
                board.removeWall(x, y-1);
            }
        }
        if(y != 16) {
            if(x != 0) {
                if(board.setWall(x-1,y+2,player)) {
                    if(AStar.AlgoAStar(player, board).size() > bestWall) {
                        bestWall = AStar.AlgoAStar(player, board).size();
                        xWall = x-1;
                        YWall = y+2;
                        player.increaseNumWalls();
                    }
                    board.removeWall(x-1, y+2);
                }
                if(board.setWall(x-2,y+1,player)) {
                    if(AStar.AlgoAStar(player, board).size() > bestWall) {
                        bestWall = AStar.AlgoAStar(player, board).size();
                        xWall = x-2;
                        YWall = y+1;
                        player.increaseNumWalls();
                    }
                    board.removeWall(x-2, y+1);
                }
            }
            if(x != 16) {
                if(board.setWall(x+1,y+2,player)) {
                    if(AStar.AlgoAStar(player, board).size() > bestWall) {
                        bestWall = AStar.AlgoAStar(player, board).size();
                        xWall = x+1;
                        YWall = y+2;
                        player.increaseNumWalls();
                    }
                    board.removeWall(x+1, y+2);
                }
            }
            if(board.setWall(x,y+1,player)) {
                if(AStar.AlgoAStar(player, board).size() > bestWall) {
                    bestWall = AStar.AlgoAStar(player, board).size();
                    xWall = x;
                    YWall = y+1;
                    player.increaseNumWalls();
                }
                board.removeWall(x, y+1);
            }
        }
        if(x != 0) {
            if(board.setWall(x-1,y,player)) {
                if(AStar.AlgoAStar(player, board).size() > bestWall) {
                    bestWall = AStar.AlgoAStar(player, board).size();
                    xWall = x-1;
                    YWall = y;
                    player.increaseNumWalls();
                }
                board.removeWall(x-1, y);
            }
        }
        if(x != 16) {
            if(board.setWall(x+1,y,player)) {
                if(AStar.AlgoAStar(player, board).size() > bestWall) {
                    bestWall = AStar.AlgoAStar(player, board).size();
                    xWall = x+1;
                    YWall = y;
                    player.increaseNumWalls();
                }
                board.removeWall(x+1, y);
            }
        }
        
        optimalX = xWall;
        optimalY = YWall;
        System.out.println("1: x=" + xWall + " ; y=" + YWall);

        return bestWall;
    }

    static public ArrayList<Integer> optimalWall2(Pawn player, Board board) {

        //Initialisating the coordinates of the best walls 
        int xWall1 = -1;int YWall1 = -1;
        int xWall2 = -1;int YWall2 = -1;
        int xWall3 = -1;int YWall3 = -1;

        //We are testing 3 walls to increase the chance to find the best wall through time
        int bestWall1 = AStar.AlgoAStar(player, board).size();
        int bestWall2 = AStar.AlgoAStar(player, board).size();
        int bestWall3 = AStar.AlgoAStar(player, board).size();

        //We test walls for the tile where the pawn is
        if(bestWall1 < optimalWall1(player, board)) {
            bestWall1 = optimalWall1(player, board);
            xWall1 = optimalX;
            YWall1 = optimalY;
        }
        else if(bestWall2 < optimalWall1(player, board)) {
            bestWall2 = optimalWall1(player, board);
            xWall2 = optimalX;
            YWall2 = optimalY;
        }
        else if(bestWall3 < optimalWall1(player, board)) {
            bestWall3 = optimalWall1(player, board);
            xWall3 = optimalX;
            YWall3 = optimalY;
        }

        //We test the walls for all the tile in the best way to win
        for(int i : AStar.AlgoAStar(player, board)) {
            int x = i%17;
            int y = i/17;

            Pawn tempPlayer = new Pawn(board,x,y);
            if(bestWall1 < optimalWall1(tempPlayer, board)) {
                bestWall1 = optimalWall1(tempPlayer, board);
                xWall1 = optimalX;
                YWall1 = optimalY;
            }
            else if(bestWall2 < optimalWall1(tempPlayer, board)) {
                bestWall2 = optimalWall1(tempPlayer, board);
                xWall2 = optimalX;
                YWall2 = optimalY;
            }
            else if(bestWall3 < optimalWall1(tempPlayer, board)) {
                bestWall3 = optimalWall1(tempPlayer, board);
                xWall3 = optimalX;
                YWall3 = optimalY;
            }
        }

        //System.out.println("2 : x=" + xWall1 + " ; y=" + YWall1);
        //System.out.println("2 : x=" + xWall2 + " ; y=" + YWall2);
        //System.out.println("2 : x=" + xWall3 + " ; y=" + YWall3);
        
        ArrayList<Integer> bestWall = new ArrayList<Integer>();
        bestWall.add(xWall1);bestWall.add(YWall1);
        bestWall.add(xWall2);bestWall.add(YWall2);
        bestWall.add(xWall3);bestWall.add(YWall3);
        return bestWall;

    }
    
}
