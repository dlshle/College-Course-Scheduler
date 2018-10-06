/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author xuri
 */
public abstract class AbstractGraph<V> implements iGraph<V> {

    private int size = 0;
    private ArrayList<V> vertices;
    private ArrayList<ArrayList<Edge>> neighbors;

    public AbstractGraph() {
        vertices = new ArrayList<>();
        neighbors = new ArrayList<>();
    }

    public AbstractGraph(ArrayList<V> vertices, ArrayList<ArrayList<Edge>> neighbors) {
        this.vertices = vertices;
        this.neighbors = neighbors;
        
        this.size = vertices.size();
    }

    public AbstractGraph(V[] vertices, int[][] edges) {
        this.vertices = new ArrayList<>();
        this.neighbors = new ArrayList<>();

        int vLength = vertices.length;
        for (int i = 0; i < vLength; i++) {
            this.vertices.add(vertices[i]);
        }

        //add edges({iU,iV})
        //add arraylists to the neighbors arraylist
        for (int i = 0; i < vLength; i++) {
            this.neighbors.add(i, new ArrayList<Edge>());
        }

        for (int[] intEdge : edges) {
            int iU = intEdge[0];
            int iV = intEdge[1];

            this.neighbors.get(iU).add(new Edge(iU, iV));
        }
        
        this.size = vLength;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public ArrayList<V>getNeighbors(int index) {
        if(index<0||index>size-1)
            return null;
        
        ArrayList<V> neighbors = new ArrayList<>();
        
        int count = this.neighbors.get(index).size();
        
        for(int i=0;i<count;i++){
            neighbors.add(vertices.get(this.neighbors.get(index).get(i).v));
        }
        
        return neighbors;
    }

    @Override
    public ArrayList<V>getNeighbors(V v) {
        int index = vertices.indexOf(v);
        if(index==-1)
            return null;
        else
            return getNeighbors(index);
    }
    
    public int[] getNeighborsIndex(int index){
        if(index<0||index>size-1)
            return null;
        
        int len = neighbors.get(index).size();
        
        int[] neighborsIndex = new int[len];
        
        for(int i=0;i<len;i++){
            neighborsIndex[i]=neighbors.get(index).get(i).v;
        }
        
        return neighborsIndex;
    }

    @Override
    public V getVertex(int index) {
        if(index<0||index>size-1)
            return null;
        else
            return vertices.get(index);
    }

    @Override
    public int getIndex(V v) {
        return vertices.indexOf(v);
    }

    /**
     * returns -1 if the vertex DNE or the degree
     * @param index
     * @return 
     */
    @Override
    public int getDegree(int index) {
        if(index<0||index>size-1)
            return -1;
        else
            return neighbors.get(index).size();
    }

    @Override
    public int getDegree(V v) {
        int index = vertices.indexOf(v);
        
        return getDegree(index);
    }

    @Override
    public boolean addVertex(V v) {
        if (!vertices.contains(v)) {
            vertices.add(v);
            neighbors.add(new ArrayList<>());
            size++;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean addEdge(int i1, int i2) {
        if(i1<0||i2<0||i1>size-1||i2>size-1){
            System.out.println("i1 or i2 out of bound.");
            return false;
        }
        
        Edge e = new Edge(i1,i2);
        
        if(neighbors.get(i1).contains(e))
            return false;
        else
            neighbors.get(i1).add(new Edge(i1,i2));
        
        return true;
    }

    @Override
    public boolean addEdge(V u, V v) {
        int i1 = vertices.indexOf(u);
        int i2 = vertices.indexOf(v);
        
        return addEdge(i1,i2);
    }
    
    @Override
    public boolean isNeighbor(int i1, int i2){
        if(i1<0||i2<0||i1>size-1||i2>size-1)
            return false;
        Edge temp = new Edge(i1,i2);
        
        if(neighbors.get(i1).contains(temp))
            return true;
        
        temp.u=i2;
        temp.v=i1;
        
        if(neighbors.get(i2).contains(temp))
            return true;
        else
            return false;
    }
    
    @Override
    public boolean isNeighbor(V u, V v){
        int i1 = vertices.indexOf(u);
        int i2 = vertices.indexOf(v);
        
        return isNeighbor(i1,i2);
    }
    
    @Override
    public boolean remove(V v){
        return remove(vertices.indexOf(v));
    }
    
    @Override
    public boolean remove(int v){
        if(v<0||v>size-1)
            return false;
        
        vertices.remove(v);
        neighbors.remove(v);
        
        for(ArrayList<Edge> arr:neighbors){
            for(int i=0;i<arr.size();i++){
                if(arr.get(i).v==v)
                    arr.remove(i);
            }
        }
        
        return true;
    }
    
    @Override
    public boolean removeEdge(V u, V v){
        int i1 = vertices.indexOf(u);
        int i2 = vertices.indexOf(v);
        
        return removeEdge(i1,i2);
    }
    
    /**
     * Remove v from u
     * @param u
     * @param v
     * @return 
     */
    @Override
    public boolean removeEdge(int u, int v){
        if(u<0||v<0||u>size-1||v>size-1)
            return false;
        
        Edge e = new Edge(u,v);
        
        if(neighbors.get(u).contains(e)){
            neighbors.get(u).remove(e);
            return true;
        }else{
            return false;
        }
    }
    
    @Override
    public boolean contains(V v){
        return vertices.contains(v);
    }

    /**
     * DFS by looping stack(inefficient)
     * @param s
     * @param e
     * @return 
     */
    @Override
    public List<V>getPathDFS(int s, int e) {
        if(s<0||e<s||e>size-1){
            return null;
        }
        
        ArrayList<V> list = DFS(s);
        
        int index = list.indexOf(vertices.get(e));
        
        if(index<0)
            return null;
        
        return (List<V>) list.subList(0, index+1);
    }

    @Override
    public List<V>getPathDFS(V s, V e) {
        int st = vertices.indexOf(s);
        int en = vertices.indexOf(e);
        
        return getPathDFS(st,en);
    }

    @Override
    public List<V> getPathBFS(int s, int e) {
        if(s<0||e<s||e>size-1){
            return null;
        }
        
        ArrayList<V> list = BFS(s);
        
        int index = list.indexOf(vertices.get(e));
        
        if(index<0)
            return null;
        
        return (List<V>) list.subList(0, index+1);
    }

    @Override
    public List<V> getPathBFS(V s, V e) {
        int st = vertices.indexOf(s);
        int en = vertices.indexOf(e);

        return getPathBFS(st, en);
    }
    
    abstract public ArrayList<V>shortestPath(V s, V e);

    @Override
    public ArrayList<V>DFS(int s) {
        if(s<0||s>size-1){
            return null;
        }
        
        LinkedList<Integer> col = new LinkedList<>();
        ArrayList<V> list = new ArrayList<>();
        HashSet<Integer> vis = new HashSet<>();
        
        col.offer(s);
        
        while(!col.isEmpty()){
            int index = col.pollLast();
            
            if(vis.contains(index))
                continue;
            
            //if doesn't contain
            list.add(vertices.get(index));
            vis.add(index);
            
            int[] neighbors = getNeighborsIndex(index);
            
            for(int i=0;i<neighbors.length;i++)
                col.offer(neighbors[i]);
        }
        
        return list;    
    }

    @Override
    public ArrayList<V>DFS(V s) {
        int index = vertices.indexOf(s);
        return DFS(index);
    }

    @Override
    public ArrayList<V>BFS(int s) {
        if(s<0||s>size-1){
            return null;
        }
        
        LinkedList<Integer> col = new LinkedList<>();
        ArrayList<V> list = new ArrayList<>();
        HashSet<Integer> vis = new HashSet<>();
        
        col.offer(s);
        
        while(!col.isEmpty()){
            int index = col.pollFirst();
            
            if(vis.contains(index))
                continue;
            
            //if doesn't contain
            list.add(vertices.get(index));
            vis.add(index);
            
            int[] neighbors = getNeighborsIndex(index);
            
            for(int i=0;i<neighbors.length;i++)
                col.offer(neighbors[i]);
        }
        
        return list;
    }

    @Override
    public ArrayList<V>BFS(V s) {
        int index = vertices.indexOf(s);
        return BFS(index);
    }
    
    /**
     * rDfs helper method
     * @param s
     * @return 
     */
    public ArrayList<V> dfs(int s){
        if(s<0||s>size-1)
            return null;
        
        ArrayList<V> list = new ArrayList<>();
        rDfs(s, list);
        
        return list;
    }
    
    /**
     * recursively dfs
     * @param s
     * @return 
     */
    public void rDfs(int s, ArrayList<V> list){
        list.add(vertices.get(s));
        
        ArrayList<V> subList = getNeighbors(s);
        
        for(int i=0;i<subList.size();i++){
            if(!list.contains(subList.get(i)))
                rDfs(getIndex(subList.get(i)),list);
        }
    }
    
    public static class Edge{
        public int u;
        public int v;

        public Edge(int u, int v) {
            this.v = v;
            this.u = u;
        }  
        
        @Override
        public boolean equals(Object o){
            Edge obj = (Edge)o;
            
            return this.v==obj.v&&this.u==obj.u;
        }
    }

    /**
     * The edge class
     */
    public static class WeightedEdge extends Edge {
        public int weight;

        public WeightedEdge(int u, int v) {
            super(u,v);
        }

        public WeightedEdge(int u, int v, int weight) {
            super(u,v);
            this.weight = weight;
        }
        
        @Override
        public boolean equals(Object o){
            WeightedEdge obj = (WeightedEdge)o;
            
            return u==obj.u&&v==obj.v&&weight==obj.weight;
        }

    }
    
    @Override
    public String toString(){
        StringBuilder out = new StringBuilder();
        
        for(int i=0;i<size;i++){
            for(Edge e:neighbors.get(i)){
                out.append(vertices.get(i)).append("->").
                        append(vertices.get(e.v)).append("\n");
            }
        }
        
        return out.toString();
    }

}
