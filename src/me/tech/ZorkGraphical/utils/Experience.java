package me.tech.ZorkGraphical.utils;

/**
 * Created by renaudj on 12/15/15.
 */
public class Experience {
    public static int KILL = 150;
    public static int getRequiredExpForLevel(int level){
        double xp = Math.pow(level, logBase(3, level));
        return (int) (xp*100);
    }

    public static double logBase(double base, double operand){
        return Math.log(operand)/Math.log(base);
    }
}
