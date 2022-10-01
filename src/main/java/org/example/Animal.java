package org.example;

import java.util.HashMap;

/*This is animal class. 
 * It is the superclass.
 * It check different types. 
 * 
 */

public class Animal {
    public int row;
    public int col;
    public int max;
    public int steps;
    public String name;
    public String gender;
    public String type;
    public boolean ifEat;
    public HashMap<Integer, String> map;
    public String direction;
    public boolean value;


    public Animal() {
        map = new HashMap<>();
        map.put(0, "male");
        map.put(1, "female");
    }

    public String type() {
        return type;
    }
    public String toString() {
        return this.name.substring(0, 1);
    }


}