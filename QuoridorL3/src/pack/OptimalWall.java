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

    static public ArrayList<Integer> optimalWall1(Pawn playerActive,Pawn player2, Board board){
    	
    	
        int x = player2.getX();
        int y = player2.getY();

        ArrayList<Integer> bestWallList = new ArrayList<Integer>();
        bestWallList.add(0); //Size Wall 1
        bestWallList.add(0); //X Wall 1
        bestWallList.add(0); //Y Wall 1
        bestWallList.add(0); //Size Wall 2
        bestWallList.add(0); //X Wall 2
        bestWallList.add(0); //Y Wall 2
        bestWallList.add(0); //Size Wall 3
        bestWallList.add(0); //X Wall 3
        bestWallList.add(0); //Y Wall 3
        bestWallList.add(0); //Size Min
        bestWallList.add(0); //Index Min
        bestWallList = setMinimumSize(bestWallList);


        if(y != 0) {
            if(x != 0) {
                if(board.setWall(x-2,y-1,playerActive, player2)) {
                    int size = AStar.AlgoAStar(player2, board).size();
                    if(size > bestWallList.get(9)) {
                        bestWallList.set(9, size);
                        bestWallList.set(bestWallList.get(10)+1, x-2);
                        bestWallList.set(bestWallList.get(10)+2, y-1);
                        bestWallList = setMinimumSize(bestWallList);
                    }
                    board.undoWall(x-2, y-1, playerActive);
                }
            }
            if(board.setWall(x,y-1,playerActive, player2)) {
                int size = AStar.AlgoAStar(player2, board).size();
                if(size > bestWallList.get(9)) {
                    bestWallList.set(9, size);
                    bestWallList.set(bestWallList.get(10)+1, x);
                    bestWallList.set(bestWallList.get(10)+2, y-1);
                    bestWallList = setMinimumSize(bestWallList);
                }
                board.undoWall(x, y-1, playerActive);
            }
        }
        if(y != 16) {
            if(x != 0) {
                if(board.setWall(x-1,y+2,playerActive, player2)) {
                    int size = AStar.AlgoAStar(player2, board).size();
                    if(size > bestWallList.get(9)) {
                        bestWallList.set(9, size);
                        bestWallList.set(bestWallList.get(10)+1, x-1);
                        bestWallList.set(bestWallList.get(10)+2, y+2);
                        bestWallList = setMinimumSize(bestWallList);
                    }
                    board.undoWall(x-1, y+2, playerActive);
                }
                if(board.setWall(x-2,y+1,playerActive, player2)) {
                    int size = AStar.AlgoAStar(player2, board).size();
                    if(size > bestWallList.get(9)) {
                        bestWallList.set(9, size);
                        bestWallList.set(bestWallList.get(10)+1, x-2);
                        bestWallList.set(bestWallList.get(10)+2, y+1);
                        bestWallList = setMinimumSize(bestWallList);
                    }
                    board.undoWall(x-2, y+1, playerActive);
                }
            }
            if(x != 16) {
                if(board.setWall(x+1,y+2,playerActive, player2)) {
                    int size = AStar.AlgoAStar(player2, board).size();
                    if(size > bestWallList.get(9)) {
                        bestWallList.set(9, size);
                        bestWallList.set(bestWallList.get(10)+1, x+1);
                        bestWallList.set(bestWallList.get(10)+2, y+2);
                        bestWallList = setMinimumSize(bestWallList);
                    }
                    board.undoWall(x+1, y+2, playerActive);
                }
            }
            if(board.setWall(x,y+1,playerActive, player2)) {
                int size = AStar.AlgoAStar(player2, board).size();
                if(size > bestWallList.get(9)) {
                    bestWallList.set(9, size);
                    bestWallList.set(bestWallList.get(10)+1, x);
                    bestWallList.set(bestWallList.get(10)+2, y+1);
                    bestWallList = setMinimumSize(bestWallList);
                }
                board.undoWall(x, y+1, playerActive);
            }
        }
        if(x != 0) {
            if(board.setWall(x-1,y,playerActive, player2)) {
                int size = AStar.AlgoAStar(player2, board).size();
                if(size > bestWallList.get(9)) {
                    bestWallList.set(9, size);
                    bestWallList.set(bestWallList.get(10)+1, x-1);
                    bestWallList.set(bestWallList.get(10)+2, y);
                    bestWallList = setMinimumSize(bestWallList);
                }
                board.undoWall(x-1, y, playerActive);
            }
        }
        if(x != 16) {
            if(board.setWall(x+1,y,playerActive, player2)) {
                int size = AStar.AlgoAStar(player2, board).size();
                if(size > bestWallList.get(9)) {
                    bestWallList.set(9,size);
                    bestWallList.set(bestWallList.get(10)+1, x+1);
                    bestWallList.set(bestWallList.get(10)+2, y);
                    bestWallList = setMinimumSize(bestWallList);
                }
                board.undoWall(x+1, y, playerActive);
            }
        }

        return bestWallList;
    }

    static public ArrayList<Integer> optimalWall2(Pawn playerActive, Pawn player2, Board board) {

        //We test walls for the tile where the pawn is
        ArrayList<Integer> bestWallList = optimalWall1(playerActive, player2, board);

        //We test the walls for all the tile in the best way to win
        for(int i : AStar.AlgoAStar(player2, board)) {
            int x = i%17;
            int y = i/17;

            Pawn tempPlayer = new Pawn(board,x,y);
            ArrayList<Integer> bestWallTemp = optimalWall1(playerActive, tempPlayer, board);
            bestWallList = Concat2Array(bestWallTemp, bestWallList);
        }

        //System.out.println("2 : x=" + xWall1 + " ; y=" + YWall1);
        //System.out.println("2 : x=" + xWall2 + " ; y=" + YWall2);
        //System.out.println("2 : x=" + xWall3 + " ; y=" + YWall3);
        
        ArrayList<Integer> bestWall = new ArrayList<Integer>();
        for(int i=0; i<3;i+=3) {
            bestWall.add(bestWallList.get(i*3+1));
            bestWall.add(bestWallList.get(i*3+2));
        }
        return bestWall;

    }

    public static ArrayList<Integer> setMinimumSize(ArrayList<Integer> list) {
        int minimumSize = 0;
        int indexSize = 0;
        for(int i=0;i<3;i++) {
            if(list.get(i*3) < minimumSize) {
                minimumSize = list.get(i*3);
                indexSize = i*3;
            }
        }
        list.set(9, minimumSize);
        list.set(10,indexSize);
        return list;
    }

    public static ArrayList<Integer> Concat2Array(ArrayList<Integer> list, ArrayList<Integer> bestWallList) {
        int maximumSize = 0;
        int indexSize = 0;
        for(int i=0;i<3;i++) {
            if(list.get(i*3) > maximumSize) {
                maximumSize = list.get(i*3);
                indexSize = i*3;
            }
        }
        for(int i=0;i<3;i++) {
            if(maximumSize > bestWallList.get(9)) {
                list.set(indexSize, 0);
                bestWallList.set(bestWallList.get(10), maximumSize);
                bestWallList.set(bestWallList.get(10)+1, list.get(maximumSize+1));
                bestWallList.set(bestWallList.get(10)+2, list.get(maximumSize+2));
                bestWallList = setMinimumSize(bestWallList);
            }
        }
        return bestWallList;
    }
    
}
