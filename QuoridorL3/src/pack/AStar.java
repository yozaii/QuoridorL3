package pack;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

public class AStar {

    static public LinkedList<Integer> AlgoAStar(Pawn player, Board board) {

        LinkedList<Integer> path = new LinkedList<Integer>();
        boolean isFinished = false;
        Queue<Integer> c = new LinkedList<Integer>();
        Queue<Integer> q = new LinkedList<Integer>();
        int a = player.getX()%17+player.getY()*17;
        q.add(a);
        HashMap<Integer,Integer> cout = new HashMap<Integer,Integer>();
        HashMap<Integer,Integer> from = new HashMap<Integer,Integer>();
        for(int i=0;i<(17*17);i++) {
            cout.put(i,1000);
        }
        cout.replace(a,0);
        int u = 0;
        while((a/17 != player.getYWin() || isFinished==false) && u!=1000) {
            int minv = 2000;
            u = 1000;
            for(int i : cout.keySet()) {
                if(((cout.get(i)+Math.abs(i/17-player.getYWin()) < minv)) && (!c.contains(i))) {
                    minv = cout.get(i) + Math.abs(i/17-player.getYWin());
                    //System.out.println(i + ":" + minv);
                    if(minv != 1000) u = i;
                }
            }
            //System.out.println("u: " + u);
            if(u/17 == player.getYWin()) {
                a = u;
                isFinished = true;
            }
            else if(u!=1000) {
                ArrayList<Integer> adjacencyList = getAdjacency(u,board);
                for(int v : adjacencyList){
                    int k = cout.get(u);
                    int w = cout.get(v) + Math.abs(k/17-player.getYWin());
                    if((!(c.contains(v))) && w>(k+2)) {
                        cout.replace(v,cout.get(u)+2);
                        q.add(v);
                        from.put(v,u);
                        //System.out.println(u + "&" + cout.get(u) + "-" + v + "&" + cout.get(v) + "-" + from.get(v));
                    }
                }
                c.add(u);
            }
        }
        //System.out.println(isFinished);
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


    static public ArrayList<Integer> getAdjacency(int z,Board board) {
        int x = z%17;
        int y = z/17;
        ArrayList<Integer> adjacency = new ArrayList<Integer>();
        Pawn p = new Pawn(board,x,y);
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
