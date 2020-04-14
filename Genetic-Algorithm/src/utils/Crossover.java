package utils;

import java.util.*;

public class Crossover {

    static int MIN = 0;
    static int MULTIBREAK = 4;
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
     * In our implementation we choose one random point from y_axis(employees) to split
     * our parentNodes.
     */

    public int[][] singlePointCross(){
//        System.out.println("Parent Node 1");
//        System.out.println(Arrays.deepToString(this.parentNode1).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
//
//        System.out.println("\n\nParent Node 2");
//        System.out.println(Arrays.deepToString(this.parentNode2).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));


        int MAX = this.y_axis;
        int rand = (int) (Math.random() * ((MAX - MIN) + 1)) + MIN;

        System.out.println("Random "+ rand);

        int[][] child1 = new int[this.x_axis][this.y_axis];
        int[][] child2 = new int[this.x_axis][this.y_axis];

        /**
         * Nothing too complicating, just parsing through the parent nodes and simply assign
         * two child nodes. Just to spice things up, after we create these two child array
         * choose randomly the one to send back
         */

        for(int x = 0; x < this.x_axis; x++){
            for(int y = 0; y < this.y_axis; y++){
                if(y <= rand){
                    child1[x][y] = parentNode1[x][y];
                    child2[x][y] = parentNode2[x][y];
                }else{
                    child1[x][y] = parentNode2[x][y];
                    child2[x][y] = parentNode1[x][y];
                }
            }
        }
//
//        System.out.println("\n\nchild Node 1");
//        System.out.println(Arrays.deepToString(child1).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
//
//        System.out.println("\n\nchild Node 2");
//        System.out.println(Arrays.deepToString(child2).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

        int randchoice = new Random().nextBoolean() ? 1 : 2;

        return randchoice == 1 ? child1 : child2;
    }

    public int[][] multiplePointCrossover(){
//                System.out.println("Parent Node 1");
//        System.out.println(Arrays.deepToString(this.parentNode1).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
//
//        System.out.println("\n\nParent Node 2");
//        System.out.println(Arrays.deepToString(this.parentNode2).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

        int MAX = this.y_axis;

        LinkedList<Integer> breakpoints = new LinkedList<>();
        for(int r = 0; r < MULTIBREAK; r++){
            int rand = (int) (Math.random() * ((MAX - MIN) + 1)) + MIN;
//            System.out.println("Random "+ rand);
            //IMPORTANT: Do not add duplicate values
            if (!breakpoints.contains(rand)) breakpoints.add(rand);
        }
        Collections.sort(breakpoints);


        int[][] child1 = new int[this.x_axis][this.y_axis];
        int[][] child2 = new int[this.x_axis][this.y_axis];

        int nextbreak = breakpoints.pollFirst();
        boolean changeFlag = false;

        for(int y = 0; y < this.y_axis; y++){
            for(int x = 0; x < this.x_axis; x++){
                //When we reach changing point in the first row simply trigger flag
                //and assign value to different arrays
                if(y == nextbreak+1 && x ==0){
                    changeFlag = !changeFlag;
                    try{
                        nextbreak = breakpoints.pollFirst();

                    }catch (NullPointerException io){
                        nextbreak = -1;
                    }
                }

                if(!changeFlag){
                    child1[x][y] = parentNode1[x][y];
                    child2[x][y] = parentNode2[x][y];
                }else{
                    child1[x][y] = parentNode2[x][y];
                    child2[x][y] = parentNode1[x][y];
                }


            }
        }

        //Again choose randomly the final result
//
//        System.out.println("\n\nchild Node 1");
//        System.out.println(Arrays.deepToString(child1).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
//
//        System.out.println("\n\nchild Node 2");
//        System.out.println(Arrays.deepToString(child2).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

        int randchoice = new Random().nextBoolean() ? 1 : 2;

        return randchoice == 1 ? child1 : child2;
    }
}
