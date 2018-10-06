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
import java.util.Scanner;

/**
 *
 * @author xuri
 */
public class FileOperator {

    private String filePath;
    private String regexCourse = "[A-Z]+&?\\s*[0-9]{1,3}";

    public FileOperator(String filePath) {
        this.filePath = filePath;
    }

    public DirectedAcyclicGraph readCourses() {
        try {
            Scanner input = new Scanner(new File(filePath));

            ArrayList<Course> courses = new ArrayList<>();
            ArrayList<ArrayList<Edge>> neighbors = new ArrayList<>();

            //add courses
            String line;
            String[] tokens;
            

            while (input.hasNextLine()) {
                line = input.nextLine().trim().toUpperCase();
                
                if(line.isEmpty())
                    continue;
                
                tokens = line.split("-");

                //isolated course
                if (tokens.length == 1) {
                    if(!courses.contains(tokens[0])){
                        courses.add(new Course(tokens[0].trim()));
                        neighbors.add(new ArrayList<>());
                        continue;
                    }
                }

                for (int i = 0; i < tokens.length - 1; i++) {
                    int u = courses.indexOf(tokens[i].trim());
                    //check if u is in the list
                    if(u==-1){
                        courses.add(new Course(tokens[i].trim()));
                        neighbors.add(new ArrayList<>());
                        u=courses.size()-1;
                    }
                    int v = courses.indexOf(tokens[i + 1].trim());
                    //check if v is in the list
                    if(v==-1){
                        courses.add(new Course(tokens[i+1].trim()));                        
                        neighbors.add(new ArrayList<>());
                        v=courses.size()-1;
                    }
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
            e.printStackTrace();
        }

        return null;
    }

    public boolean saveCourseInfo(DirectedAcyclicGraph<Course> courseDAG) {
        try {
            StringBuilder out = new StringBuilder();

            FileWriter fw = new FileWriter("/courseinfo.txt", true);
            PrintWriter pw = new PrintWriter(fw);

            //edge information      
            for (int i = 0; i < courseDAG.getSize(); i++) {
                int[] arrEdge = courseDAG.getNeighborsIndex(i);

                for (Integer x : arrEdge) {
                    out.append(courseDAG.getVertex(i)).append("-").append(courseDAG.getVertex(x)).append("\n");
                }
            }

            System.out.println(out);
            pw.println(out.toString());
            
            return true;

        } catch (IOException ioe) {
            System.out.println("Couldn't save file.");
        }
        return false;
    }

}
