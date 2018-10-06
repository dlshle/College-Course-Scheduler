/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.util.ArrayList;

/**
 *
 * @author xuri
 */
public class DirectedAcyclicGraph<Course> extends AbstractGraph<Course> {
    
    public DirectedAcyclicGraph(){
        super();
    }

    public DirectedAcyclicGraph(ArrayList<Course> vertices, ArrayList<ArrayList<Edge>> neighbors) {
        super(vertices, neighbors);
    }

    public DirectedAcyclicGraph(Course[] vertices, int[][] edges) {
        super(vertices, edges);
    }
    

    public int[] topologicalSort(){
        boolean[] visited = new boolean[getSize()];
        
        int[] topoChart = new int[getSize()];
        
        int n = getSize();
        
        topologicalSort(0, n, topoChart, visited);
        
        return topoChart;
    }
    
    public int topologicalSort(int vertex, int n, int[] topoChart, boolean[] visited){
        visited[vertex]=true;
        
        for(int v:getNeighborsIndex(vertex)){
            if(!visited[v])
                n = topologicalSort(v,n,topoChart,visited);
        }
        
        topoChart[n]=vertex;
        
        return n;
    }
    
    
    @Override
    public ArrayList<Course> shortestPath(Course s, Course e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
