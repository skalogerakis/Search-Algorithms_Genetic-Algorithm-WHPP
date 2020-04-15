package utils;

import java.util.ArrayList;
import java.util.Random;


public class Selection {

    static int MIN = 0;


    /**
     * Implementation based on this series
     * https://www.youtube.com/watch?v=9JzFcGdpT8E&list=PLea0WJq13cnARQILcbHUPINYLy1lOSmjH&index=15
     * Steps:
     * - [SUM] Calculate sum of all chromosome fitness in population
     * - [SELECT] Generate random number r from interval (0,S)
     * - [LOOP] Go through the population and sum fitness from 0-sum s
     *      -When sum is greater than r, stop and return the chromosome where you are
     */
    public int rouletteWheelSelection(ArrayList<int[][]> population, int[] score){
        int totalSum=0;

        for(int i = 0; i < population.size(); i++){
            totalSum += score[i];
        }

        //int rand = TODO
        int MAX = totalSum;

        int rand = (int) (Math.random() * ((MAX - MIN) + 1)) + MIN;
        //System.out.println("SUM "+totalSum+" rand "+rand);

        int partialSum = 0;

        for(int j = 0; j < population.size(); j++){
            partialSum += score[j];

            if(partialSum >= rand){
                //
                return j;
            }
        }

        //Something went wrong to reach this point
        return -1;
    }

    public int rouletteWheelSelectionF(ArrayList<int[][]> population, int[] score){
        double totalSum = 0.0d;

        for(int i = 0; i < population.size(); i++){
            totalSum += (double) 1/score[i];
        }
        double MAX = totalSum;

        Random r = new Random();
        double rand = MIN + (MAX - MIN) * r.nextDouble();


        //double rand = (Math.random() * ((MAX - MIN) + 1)) + MIN;
        //System.out.println("SUM "+totalSum+" rand "+rand);

        double partialSum = 0.0d;

        for(int j = 0; j < population.size(); j++){
            partialSum += (double) 1/score[j];
            //System.out.println("PARTIAL SUM "+partialSum+" rand "+rand);

            if(Double.compare(partialSum,rand) >= 0){
                //System.out.println("j "+j);
                return j;
            }
        }

        //Something went wrong to reach this point
        System.out.println("ERROR HERE CHECK");
        return -1;
    }
}
