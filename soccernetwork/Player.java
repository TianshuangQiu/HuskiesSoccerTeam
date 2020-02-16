/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soccernetwork;

import java.util.ArrayList;

/**
 *
 * @author eqiu
 */
public class Player {

    String name;
    char position;
    double activation;
    double buffer;

    public void pass(String type) {
        if (type.toUpperCase().contains("HIGH") || type.toUpperCase().contains("HAND") || type.toUpperCase().contains("LAUNCH"))
            buffer += 1;
        else if (type.toUpperCase().contains("HEAD") || type.toUpperCase().contains("SIMPLE") || type.toUpperCase().contains("SMART"))
            buffer += 10;
        else if (type.toUpperCase().contains("CROSS"))
            buffer += 100;
    }

    public Player() {
        name = "ERROR";
        position = 'z';
        activation = 0;
    }

    public Player(String n) {
        System.out.println("trying to initiate " + n);
        name = n;
        position = n.charAt(n.indexOf("_"));
        activation = 0;
        buffer = 0;
    }

    public String getName() {
        return name;
    }

    public void activate(int i) {
        activation = activation + buffer * i;
        //System.out.println(buffer);
        buffer = 0;
    }

    public String printResult() {
        return name + " " + activation;
    }

}
