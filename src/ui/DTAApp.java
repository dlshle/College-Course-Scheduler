/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import business.CourseManager;
import java.util.Scanner;

/**
 *
 * @author xuri
 */
public class DTAApp {
    
    private CourseManager man = new CourseManager();
    Scanner input = new Scanner(System.in);
    private String invalidInput = "Invalid input. Please follow the instruction"
                    + "above.";
    
    public static void main(String[] args){
        DTAApp ui = new DTAApp();
    }
    
    public DTAApp(){
        mainCycle();
    }
    
    public void mainCycle(){
        loadCourse();
        
        int choice = 0;
        
        do{
            choice = displayCycle();
            switch(choice){
                case 0:
                    listCourses();
                    break;
                case 1:
                    listPath();
                    break;
                default:
                    System.out.println(invalidInput);
            }
        }while(choice>-1);
    }
    
    public int displayCycle(){        
        System.out.println("Selecte an option:\n\t0. List all the courses with "
                + "prerequisite.\n\t1.Generate a course path for"
                + " CS DAT.\n\t2.Add a course to the list.\n\t3.Add a "
                + "prerequisite to a course.\n\t4.Remove a course from the list."
                + "\n\t5.Remove a prerequisite from a course.\n\t6.Load another"
                + " course file.\n\t"
                + "-1.Exit the system.");
        
        String line = input.nextLine().trim();
        
        try{
            int choice = Integer.parseInt(line);
            
            return choice;
        }catch(NumberFormatException nfe){
            System.out.println(invalidInput);
        }
        
        return displayCycle();
    }
    
    public void loadCourse(){
        String line = "";
        
        System.out.println("Input the path of the course file:");
        
        line = input.nextLine().trim();
        
        if(!man.loadCourse(line))
            loadCourse();
        
        System.out.println("The course file has been sucessfully loaded.");
    }
    
    public void listPath(){
        System.out.println(man.listPath());
    }
    
    public void listCourses(){
        System.out.println(man.listCourses());
    }
    
    
}
