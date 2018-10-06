/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.Objects;

/**
 *
 * @author xuri
 */
public class Course {
    
    public String name;
    public int credit;
    
    public Course(String name){
        this.name = name;
    }
    
    public Course(String name, int credit){
        this.name = name;
        this.credit = credit;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Course other = (Course) obj;
        if (!(Objects.equals(this.name, other.name)&&
                (Objects.equals(this.credit, other.credit)))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString(){
        return this.name;
    }
    
}
