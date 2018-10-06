/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import dataaccess.DataControl;

/**
 *
 * @author xuri
 */
public class CourseManager {
    //field
    private DataControl dc = new DataControl();
    
    /**
     * Default constructor
     */
    public CourseManager(){
    }   
    
    public boolean loadCourse(String filePath){
        return dc.loadCourse(filePath);
    }
    
    public boolean addCourse(String name){
        if(!dc.addCourse(name))
            return false;
        else
            return dc.saveCourse();
    }
    
    public boolean addPreq(String pre, String cou){
        if(!dc.addPreq(pre, cou))
            return false;
        else
            return dc.saveCourse();
    }
    
    public boolean removeCourse(String name){
        if(!dc.removeCourse(name))
            return false;
        else
            return dc.saveCourse();
    }
    
    public boolean removePreq(String preq, String course){
        if(!dc.removePreq(preq, course))
            return false;
        else
            return dc.saveCourse();
    }
    
    public String listPath(){
        return dc.listPath();
    }  
    
    public String listCourses(){
        return dc.listCourses();
    }
    
}
