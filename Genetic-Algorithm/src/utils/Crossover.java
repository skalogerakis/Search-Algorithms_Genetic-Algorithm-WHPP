package utils;

import java.util.*;

public class Crossover {

    static int MIN = 0;
    static int MULTIBREAK = 4; //The number of point break in multiple point crossover case
    int[][] parentNode1;
    int[][] parentNode2;
    int x_axis;
    int y_axis;

    public Crossover(int[][] parentNode1, int[][] parentNode2,int x_axis, int y_axis){
        this.parentNode1 = parentNode1;
        this.parentNode2 = parentNode2;
        this.x_axis = x_axis;
        this.y_axis = y_axis;
    }

    //Basic concepts and idea behind implementation
    //https://www.geeksforgeeks.org/crossover-in-genetic-algorithm/
    /**
     * In our implementation we choose one random point from x_axis(days) to split
     * our parentNodes.
     */

    public int[][] singlePointCross(){


        int MAX = this.x_axis-1;
        int rand = (int) (Math.random() * ((MAX - MIN) + 1)) + MIN;

        int[][] child1 = new int[this.x_axis][this.y_axis];
        int[][] child2 = new int[this.x_axis][this.y_axis];

        /**
         * Nothing too complicated, just parsing through the parent nodes and simply assign
         * two child nodes. Just to spice things up, after we create these two child array
         * choose randomly the one to send back
         */

        for(int y = 0; y < this.y_axis; y++){
            for(int x = 0; x < this.x_axis; x++){
                if(x <= rand){
                    child1[x][y] = parentNode1[x][y];
                    child2[x][y] = parentNode2[x][y];
                }else{
                    child1[x][y] = parentNode2[x][y];
                    child2[x][y] = parentNode1[x][y];
                }
            }
        }

        int randchoice = new Random().nextBoolean() ? 1 : 2;

        return randchoice == 1 ? child1 : child2;
    }

    /**
     * Very similar to single point crossover, this time simply make it a little more complex.
     * We choose random points from x axis(days) to split and divide our parent arrays.
     * Again 2 different child arrays are produced and we randomly choose which one to send back
     * @return
     */
    public int[][] multiplePointCrossover(){

        int MAX = this.x_axis-1;

        LinkedList<Integer> breakpoints = new LinkedList<>();
        for(int r = 0; r < MULTIBREAK; r++){
            int rand = (int) (Math.random() * ((MAX - MIN) + 1)) + MIN;
            //IMPORTANT: Do not add duplicate values
            if (!breakpoints.contains(rand)) breakpoints.add(rand);
        }
        Collections.sort(breakpoints);


        int[][] child1 = new int[this.x_axis][this.y_axis];
        int[][] child2 = new int[this.x_axis][this.y_axis];

        int nextbreak = breakpoints.pollFirst();
        boolean changeFlag = false;

        for(int x = 0; x < this.x_axis; x++){
            for(int y = 0; y < this.y_axis; y++){
                //When we reach changing point in the first column simply trigger flag
                //and assign value to different arrays

                if(!changeFlag){
                    child1[x][y] = parentNode1[x][y];
                    child2[x][y] = parentNode2[x][y];
                }else{
                    child1[x][y] = parentNode2[x][y];
                    child2[x][y] = parentNode1[x][y];
                }

                if(x == nextbreak && y == this.y_axis-1){
                    changeFlag = !changeFlag;
                    try{
                        nextbreak = breakpoints.pollFirst();

                    }catch (NullPointerException io){
                        nextbreak = -1;
                    }
                }




            }
        }

        //Again choose randomly the final result

        int randchoice = new Random().nextBoolean() ? 1 : 2;

        return randchoice == 1 ? child1 : child2;
    }
}
