package utils;

import java.util.ArrayList;
import java.util.Random;

public class Population {
    static int MIN = 0;
    static int MAX = 29;

    /**
     * Hard Constraints as defined from assignment
     */
    static int[][] hardConstraints = {
            {10, 10, 5, 5, 5, 5, 5, 10, 10, 5, 5, 5, 5, 5},
            {10, 10, 10, 5, 10, 5, 5, 10, 10, 10, 5, 10, 5, 5},
            {5, 5, 5, 5, 5, 5, 5, 10, 10, 10, 5, 10, 5, 5}
    };



    //TODO use arraylist with 2d arrays to start. Always 3d array available

    /**
     * Pseudorandom number generator that produces the numbers in sequence that are
     * uniform distributed which looks random. This specific function, generates a population
     * of a given size(passed as parameter). It uses the initMatrix function as described below to
     * produce valid chromosome, and then this function shuffles each chromosome randomly and also
     * randomly makes invalid some of the population
     * @param size
     * @param x_axis
     * @param y_axis
     * @return
     */
    public ArrayList<int[][]> population_generator(int size, int x_axis, int y_axis){
        //int[][][] popMatrix = new int[size][x_axis][y_axis];
        ArrayList<int[][]> popMatrix = new ArrayList<int[][]>();

        for(int i=0; i<size; i++){
            int[][] tempMatrix = initMatrix(x_axis,y_axis);

            for(int j=0; j<x_axis; j++){
                Random rand = new Random();
                //Approximately in 1 in 20 cases(5.5%) in x_axis(DAYS) change hard constraint assignment(make it invalid)
                if( new Random().nextDouble() <= 0.055 ) {
                    tempMatrix[j][0] = 0;
                }
                for(int k=0; k<y_axis; k++){
                    int randomIndexToSwap = rand.nextInt(y_axis);
                    int temp = tempMatrix[j][randomIndexToSwap];
                    tempMatrix[j][randomIndexToSwap] = tempMatrix[j][k];
                    tempMatrix[j][k] = temp;

                }
            }

            popMatrix.add(tempMatrix);


        }

        System.out.println("Population generated successfully.");
        return popMatrix;

    }

    /**
     * This is used to generate one random chromosome in case someone goes invalid.
     * @param x_axis
     * @param y_axis
     * @return
     */
    public int[][] randomValidPopulation(int x_axis, int y_axis){

        int[][] tempMatrix = initMatrix(x_axis,y_axis);

        for(int j=0; j<x_axis; j++){
            Random rand = new Random();

            for(int k=0; k<y_axis; k++){
                int randomIndexToSwap = rand.nextInt(y_axis);
                int temp = tempMatrix[j][randomIndexToSwap];
                tempMatrix[j][randomIndexToSwap] = tempMatrix[j][k];
                tempMatrix[j][k] = temp;

            }
        }

        return tempMatrix;

    }

    /**
     * Matrix initialization based on hard constraint array. I simply reversed engineered the problem
     * by initially producing a init matrix that will definitely pass the hard constraints and then
     * make it random(see functions above)
     * @param x_axis
     * @param y_axis
     * @return
     */
    private int[][] initMatrix(int x_axis, int y_axis){

        int[][] tempMatrix = new int[x_axis][y_axis];

        for(int j=0; j<x_axis; j++){
            int oneCounter = 0;
            int twoCounter = 0;
            int threeCounter = 0;
            for(int k=0; k<y_axis; k++){
                if(hardConstraints[0][j] != oneCounter){
                    tempMatrix[j][k] = 1;
                    oneCounter++;
                }else if(hardConstraints[1][j] != twoCounter){
                    tempMatrix[j][k] = 2;
                    twoCounter++;
                }else if(hardConstraints[2][j] != threeCounter){
                    tempMatrix[j][k] = 3;
                    threeCounter++;
                }
            }
        }

        return tempMatrix;
    }
}
