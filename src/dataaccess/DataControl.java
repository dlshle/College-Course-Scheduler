/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import domain.Course;

/**
 *
 * @author xuri
 */
public class DataControl {

    FileOperator fo;
    DirectedAcyclicGraph<Course> courseGraph;

    public DataControl() {
        courseGraph = new DirectedAcyclicGraph<>();
    }

    public boolean loadCourse(String path) {
        fo = new FileOperator(path);

        courseGraph = fo.readCourses();

        if (courseGraph == null) {
            return false;
        }
        return true;
    }

    public boolean saveCourse() {
        return fo.saveCourseInfo(courseGraph);
    }

    public boolean removeCourse(String courseName) {
        Course temp = new Course(courseName);
        return courseGraph.remove(temp);
    }

    public boolean addCourse(String courseName) {
        return courseGraph.addVertex(new Course(courseName));
    }

    public boolean addPreq(String preq, String course) {
        return courseGraph.addEdge(new Course(preq), new Course(course));
    }
    
    public boolean removePreq(String preq, String course) {
        return courseGraph.removeEdge(new Course(course), new Course(preq));
    }
    
    public String listPath(){
        StringBuilder out = new StringBuilder();
        int[] chart = courseGraph.topologicalSort();
        
        for(int i=0;i<chart.length;i++){
            out.append(chart[i]).append(" ");
        }
        
        return out.toString();
    }
    
    public String listCourses(){
        StringBuilder out = new StringBuilder();
        out.append("Course before '->' stands for the prerequisite class after"
                + " '->'.");
        
        out.append(courseGraph.toString());
        
        return out.toString();
    }

}
