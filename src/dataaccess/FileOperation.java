/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import dataaccess.AbstractGraph.Edge;
import domain.Course;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author xuri
 */
public class FileOperation {

    private String filePath;
    private String regexMap = "([A-Z]+\\s+)+[0-9]+";

    public FileOperation(String filePath) {
        this.filePath = filePath;
    }

    public DirectedAcyclicGraph readCourses(HashMap<String, ArrayList<Integer>> map) {
        try {
            Scanner input = new Scanner(new File(filePath));

            ArrayList<Course> courses = new ArrayList<>();
            ArrayList<ArrayList<Edge>> neighbors = new ArrayList<>();

            //add courses
            String line = input.nextLine().toUpperCase().trim();
            String[] tokens = line.split(";");

            

            while (input.hasNextLine()) {
                line = input.nextLine().toUpperCase().trim();
                if(line.isEmpty())
                    continue;
                
                //Map add
                if(line.matches(regexMap)){
                    tokens = line.split(" ");
                    
                    String name = "";
                    
                    for(int i=0;i<tokens.length-1;i++)
                        name+=tokens[i].trim();
                    
                    map.put(name, new ArrayList<>(Integer.valueOf(tokens
                            [tokens.length-1])));
                    
                    //take course info
                    line = input.nextLine().trim().toUpperCase();
                    
                    tokens = line.split(";");
                    
                    for(String k:tokens){
                        String[] tt = k.split("@");
                        Course temp = new Course(tt[0].trim(),Integer.valueOf(tt[1].trim()));
                        if(!courses.contains(temp))
                            courses.add(temp);
                    }
                }
                
                tokens = line.split("-");

                //isolated course
                if (tokens.length == 1) {
                    continue;
                }

                for (int i = 0; i < tokens.length - 1; i++) {
                    int u = courses.indexOf(tokens[i]);
                    int v = courses.indexOf(tokens[i + 1]);
                    neighbors.get(u).add(new Edge(u, v));
                }
            }

            DirectedAcyclicGraph graph = new DirectedAcyclicGraph(courses, neighbors);

            return graph;
        } catch (IOException ioe) {
            System.out.println("Can not read the file " + filePath + ".");
        } catch (Exception e) {
            System.out.println("Unmatched course information or incorrect "
                    + "file format.");
        }

        return null;
    }

    public boolean saveCourseInfo(DirectedAcyclicGraph<Course> courseDAG,
            HashMap<String, ArrayList<Integer>> map) {
        try {
            StringBuilder out = new StringBuilder();

            FileWriter fw = new FileWriter("/courseinfo.txt", true);
            PrintWriter pw = new PrintWriter(fw);

            //course information
            for (String key : map.keySet()) {
                ArrayList<Integer> list = map.get(key);
                pw.println(key + " " + list.get(0));
                for (int i = 1; i < list.size(); i++) {
                    out.append(courseDAG.getVertex(list.get(i))).append("@").
                            append(courseDAG.getVertex(i).credit).append(";");
                }
            }

            //edge information      
            for (int i = 0; i < courseDAG.getSize(); i++) {
                int[] arrEdge = courseDAG.getNeighborsIndex(i);

                for (Integer x : arrEdge) {
                    out.append(courseDAG.getVertex(i)).append("-").append(courseDAG.getVertex(x)).append("\n");
                }
            }

            System.out.println(out);
            pw.println(out.toString());

        } catch (IOException ioe) {
            System.out.println("Couldn't save file.");
        }
        return false;
    }

}
