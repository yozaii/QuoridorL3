package pack;

public class OptimalWall {

    private static int optimalX;
    private static int optimalY;

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

    static public void optimalWall2(Pawn player, Board board) {
        int x = player.getX();
        int y = player.getY();

        int xWall = -1;
        int YWall = -1;

        int bestWall = AStar.AlgoAStar(player, board).size();

        if(bestWall < optimalWall1(player, board)) {
            bestWall = optimalWall1(player, board);
            xWall = optimalX;
            YWall = optimalY;
        }
        for(int i : AStar.AlgoAStar(player, board)) {
            x = i%17;
            y = i/17;

            Pawn tempPlayer = new Pawn(board,x,y);
            if(bestWall < optimalWall1(tempPlayer, board)) {
                bestWall = optimalWall1(tempPlayer, board);
                xWall = optimalX;
                YWall = optimalY;
            }
        }

        System.out.println("2 : x=" + xWall + " ; y=" + YWall);

    }
    
}

/**
 *  Mur a poser  :
 * y+2 x-1
 * y+2 x+1
 * y+1 x-2
 * y+1 x
 * y x-1
 * y x+1
 * y-1 x-2
 * y-1 x
 * 
 */
