package pack;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

public class AStar {

    /**
     * AStar Algorithm, find the best path to the end for a player
     * @param player
     * @param board
     * @return the path chosen by AStar
     */
    static public LinkedList<Integer> AlgoAStar(Pawn player, Board board) {

        LinkedList<Integer> path = new LinkedList<Integer>();
        boolean isFinished = false;
        Queue<Integer> c = new LinkedList<Integer>();
        Queue<Integer> q = new LinkedList<Integer>();
        int a = player.getX()%17+player.getY()*17;
        q.add(a);

        /*------------------------------------------------------------*/
		/*-----------Set the origin and the cost for all tiles--------*/
		/*------------------------------------------------------------*/
        HashMap<Integer,Integer> cout = new HashMap<Integer,Integer>();
        HashMap<Integer,Integer> from = new HashMap<Integer,Integer>();
        for(int i=0;i<(17*17);i++) {
            cout.put(i,1000);
        }
        cout.replace(a,0);
        for(int i=0;i<(17*17);i++) {
            from.put(i,a);
        }
        int u = 0;


        while((a/17 != player.getYWin() || isFinished==false) && u!=1000) {
            int minv = 2000;
            u = 1000;

            /*------------------------------------------------------------*/
		    /*---------Search for tile with minimal cost + heuristic------*/
		    /*------------------------------------------------------------*/
            for(int i : cout.keySet()) {
                if(((cout.get(i)+Math.abs(i/17-player.getYWin()) < minv)) && (!c.contains(i))) {
                    minv = cout.get(i) + Math.abs(i/17-player.getYWin());
                    if(minv != 1000) u = i;
                }
            }

            /*------------------------------------------------------------*/
		    /*---------------If the tile is in the winning line-----------*/
		    /*------------------------------------------------------------*/
            if(u/17 == player.getYWin()) {
                a = u;
                isFinished = true;
            }

            /*------------------------------------------------------------*/
		    /*----------------------Test all tile around------------------*/
		    /*------------------------------------------------------------*/
            else if(u!=1000) {
                ArrayList<Integer> adjacencyList = getAdjacency(u,board);
                for(int v : adjacencyList){
                    int k = cout.get(u);
                    int w = cout.get(v) + Math.abs(k/17-player.getYWin());

                    /*------------------------------------------------------------*/
		            /*--------------Set new parameters for tile around------------*/
		            /*------------------------------------------------------------*/
                    if((!(c.contains(v))) && w>(k+2)) {
                        cout.replace(v,cout.get(u)+2);
                        q.add(v);
                        from.replace(v,u);
                    }
                }
                c.add(u);
            }
        }

        /*------------------------------------------------------------*/
		/*----------Build the path from origin to winning tile--------*/
		/*------------------------------------------------------------*/
        if(isFinished) {
            while(a != player.getX()%17+player.getY()*17) {
                path.add(a);
                a = from.get(a);
            }
        }
        LinkedList<Integer> invertedPath = new LinkedList<Integer>();
        for(int i=path.size()-1;i>=0;i--) {
            invertedPath.add(path.get(i));
        }
        return invertedPath;
        
    }

    /**
     * Return a list of adjacent's tiles.
     * @param z a tile
     * @param board
     * @return all the tile next to z where we can go (without a wall between)
     */
    static public ArrayList<Integer> getAdjacency(int z,Board board) {
        int x = z%17;
        int y = z/17;
        ArrayList<Integer> adjacency = new ArrayList<Integer>();
        Pawn p = new Pawn(board,x,y);

        /*------------------------------------------------------------*/
		/*---Return coordinates according to possibleMoves method-----*/
		/*------------------------------------------------------------*/
        for(int i=0;i<p.possibleMoves().size();i++) {
            switch(p.possibleMoves().get(i)) {
                case "Up" : adjacency.add(x%17+(y+2)*17); break;
                case "Down" : adjacency.add(x%17+(y-2)*17); break;
                case "Left" : adjacency.add((x-2)%17+y*17); break;
                case "Right" : adjacency.add((x+2)%17+y*17); break;
                case "Up-Right" : adjacency.add((x+2)%17+(y+1)*17); break;
                case "Down-Right" : adjacency.add((x+2)%17+(y-2)*17); break;
                case "Up-Left" : adjacency.add((x-2)%17+(y+1)*17); break;
                case "Down-Left" : adjacency.add((x-2)%17+(y-2)*17); break;
            }
        }
        return adjacency;
    }
    
}
