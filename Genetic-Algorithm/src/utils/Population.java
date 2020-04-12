package utils;

import java.util.ArrayList;
import java.util.Random;

public class Population {
    static int MIN = 0;
    static int MAX = 3;

    //TODO use arraylist with 2d arrays to start. Always 3d array available

    /**
     * Pseudorandom number generator that produces the numbers in sequence that are
     * uniform distributed which looks random. From documentation: "Math.random() uses Random.nextDouble()
     * internally and Random.nextDouble() uses Random.next() twice to generate a double number that has approximately
     * uniformly distributed bits in its mantissa, so it is uniformly distributed in the range 0 to 1-(2^-52"
     * @param size
     * @param x_axis
     * @param y_axis
     * @return
     */
    public ArrayList<int[][]> population_generator(int size, int x_axis, int y_axis){
        //int[][][] popMatrix = new int[size][x_axis][y_axis];
        ArrayList<int[][]> popMatrix = new ArrayList<int[][]>();
        Random random = new Random();

        for(int i=0; i<size; i++){
            int[][] tempMatrix = new int[x_axis][y_axis];
            for(int j=0; j<x_axis; j++){
                for(int k=0; k<y_axis; k++){
                    //popMatrix[i][j][k] = (int) (Math.random() * ((MAX - MIN) + 1)) + MIN;
                    tempMatrix[j][k] = (int) (Math.random() * ((MAX - MIN) + 1)) + MIN;
                    //tempMatrix[j][k] = random.ints(MIN,MAX+1).limit(1).findFirst().getAsInt();


                }
            }
            popMatrix.add(tempMatrix);
        }

        System.out.println("Population generated successfully.");
        return popMatrix;

    }
}
