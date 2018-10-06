/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xuri
 */
public interface iGraph<V> {
    
    public int getSize();
    
    public ArrayList<V> getNeighbors(int index);
    
    public ArrayList<V> getNeighbors(V v);
    
    public V getVertex(int index);
    
    public int getIndex(V v);
    
    public int getDegree(int index);
    
    public int getDegree(V v);
    
    public boolean addVertex(V v);
    
    public boolean addEdge(int i1, int i2);
    
    public boolean addEdge(V u, V v);
    
    public boolean isNeighbor(int i1, int i2);
    
    public boolean isNeighbor(V u, V v);
    
    public boolean remove(V v);
    
    public boolean remove(int v);
    
    public boolean removeEdge(V u, V v);
    
    public boolean removeEdge(int u, int v);
    
    public boolean contains(V v);
    
    public List<V> getPathDFS(int s, int e);
    
    public List<V> getPathDFS(V s, V e);
    
    public List<V> getPathBFS(int s, int e);
    
    public List<V> getPathBFS(V s, V e);
    
    public ArrayList<V> DFS(int s);
    
    public ArrayList<V> DFS(V s);
    
    public ArrayList<V> BFS(int s);
    
    public ArrayList<V> BFS(V s);
    
}
