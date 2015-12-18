package me.tech.ZorkGraphical.utils;

import java.util.Random;

public class Experience {
    public static int KILL = 20;
    public static int CRAFT = 10;
    public static int EAT = 5;
    public static int BLOCK = 3;
    public static int getRequiredExpForLevel(int level){
        double xp = Math.pow(level, logBase(3, level));
        return (int) (xp*100);
    }

    public static double logBase(double base, double operand){
        return Math.log(operand)/Math.log(base);
    }

    public static int randomInt(int min, int max){
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }
}
