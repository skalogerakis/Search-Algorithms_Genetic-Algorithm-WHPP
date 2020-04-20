package utils;

import java.util.ArrayList;
import java.util.List;
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


    /*
    LEGACY: this implementation is not currently used. This would be applicable is we are trying the max of our score.
    In our problem, score is considered as negative weight, so we compute the 1/s value
     */
    public int rouletteWheelSelection(ArrayList<int[][]> population, int[] score){
        int totalSum=0;

        for(int i = 0; i < population.size(); i++){
            totalSum += score[i];
        }

        int MAX = totalSum;

        int rand = (int) (Math.random() * ((MAX - MIN) + 1)) + MIN;

        int partialSum = 0;

        for(int j = 0; j < population.size(); j++){
            partialSum += score[j];

            if(partialSum >= rand){

                return j;
            }
        }

        //Something went wrong to reach this point
        return -1;
    }

    /*
    This is the implementation used for our problem implementing roulette wheel selection. We use 1/score
    to compute our sums and total sums
     */
    public int rouletteWheelSelectionF(ArrayList<Statistics> score){
        double totalSum = 0.0d;

        totalSum += score.stream().mapToDouble(stat -> (double) 1 / stat.getScore()).sum();


        double MAX = totalSum;

        Random r = new Random();
        double rand = MIN + (MAX - MIN) * r.nextDouble();

        double partialSum = 0.0d;

        for(Statistics stat : score){
            partialSum += (double) 1/stat.getScore();
            if(Double.compare(partialSum,rand) >= 0){

                return score.indexOf(stat);
            }
        }
        //Something went wrong to reach this point
        System.out.println("ERROR HERE CHECK");
        return -1;
    }
}
