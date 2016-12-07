/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package euleriancirclsdirected;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class EulerianCirclsDirected {

    int v;
    Set<Integer> visitedDFS;
    int in[];
    HashMap<Integer, List<Integer>> adjList;

    public EulerianCirclsDirected(int v) {
        this.visitedDFS = new HashSet<Integer>();
        this.adjList = new HashMap<Integer, List<Integer>>();
        this.v = v;
        in = new int[v];
    }

    //add edge
    public void addEdge(int src, int dest) {
        List<Integer> srcNeighbour = this.adjList.get(src);
        if (srcNeighbour == null) {
            this.adjList.put(src, srcNeighbour = new ArrayList<Integer>());
        }
        srcNeighbour.add(dest);
        in[dest]++;
    }

    //get neighbours of vertex
    public Iterable<Integer> getNeighbours(Integer vertex) {

        List<Integer> neighbours = this.adjList.get(vertex);
        if (neighbours == null) {
            return Collections.emptyList();
        } else {
            return Collections.unmodifiableList(neighbours);
        }

    }

    public int sizeNeighbours(Integer vertex) {

        List<Integer> list = (List<Integer>) this.getNeighbours(vertex);

        return list.size();
    }

    //depth first search
    public Iterable<Integer> DFS(Integer src) {
        Stack<Integer> stack = new Stack<Integer>();
        List<Integer> paths = new ArrayList<Integer>();
        stack.add(src);
        visitedDFS.add(src);
        paths.add(src);

        while (!stack.isEmpty()) {

            int ref = stack.pop();
            for (int neig : this.getNeighbours(ref)) {
                if (!visitedDFS.contains(neig)) {
                    stack.add(neig);
                    visitedDFS.add(neig);
                    paths.add(neig);
                }
            }

        }

        return Collections.unmodifiableSet(visitedDFS);
    }

    public int numVertices() {
        return this.v;
    }

    //transpose of graph
    public EulerianCirclsDirected getTranspose() {

        int v = this.numVertices();
        EulerianCirclsDirected gr = new EulerianCirclsDirected(v);
        for (int i = 0; i < v; i++) {
            for (int neig : this.getNeighbours(i)) {
                gr.addEdge(neig, i);
            }
        }
        return gr;

    }

    //checks if graph has a eulerian cycle
    public boolean isEulerianCycle() {
        int v = this.numVertices();

        //check if graph is connect
        //that is every non zero degree vertex is part 
        //of a strongly connected component
        if (!isConnected()) {
            return false;
        }

        //check for indegree and out degree
        for (int i = 0; i < v; i++) {
            if (in[i] != this.adjList.get(i).size()) {
                return false;
            }
        }

        return true;

    }

    //method to verify for strongly connected component
    public boolean isConnected() {

        int v = this.numVertices();
        int i;

        //get the first non zero degre vertex
        for (i = 0; i < v; i++) {
            if (this.sizeNeighbours(i) > 0) {
                break;
            }
        }

        //first run dfs for original graph at the first non
        //zero degree vertex
        this.DFS(i);

        //check if all vertices where visited during dfs
        for (int j = 0; j < v; j++) {
            if (!visitedDFS.contains(j)) {
                System.out.println("first " + visitedDFS.contains(j));
                return false;
            }

        }

        //get transpose of graph and run dfs
        //so we have to reset visitedDFS
        visitedDFS.clear();
        EulerianCirclsDirected gr = this.getTranspose();

        //update visitedDFS to be that of the 
        //transpose
        visitedDFS = (Set<Integer>) gr.DFS(i);

        //check again if all vertices are visited in the 
        //transposed graph
        int grV = gr.numVertices();
        for (int j = 0; j < grV; j++) {
            if (!visitedDFS.contains(j)) {
                return false;
            }

        }
        return true;

    }

    public static void main(String[] args) {

        //          EulerianCirlsDirected g = new EulerianCirlsDirected(2);
        //          g.addEdge(0, 1);
        //          g.addEdge(1, 0);
        //          g.addEdge(2, 3);
        //          g.addEdge(3, 0);
        //          g.addEdge(2, 4);
        //          g.addEdge(4, 2);
        EulerianCirclsDirected g1 = new EulerianCirclsDirected(2);
        g1.addEdge(0, 1);
        g1.addEdge(1, 0);
        System.out.println(g1.isEulerianCycle());
        //System.out.println(g.sizeNeighbours(1));

    }

}
