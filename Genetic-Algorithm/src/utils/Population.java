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

        for(int i=0; i<size; i++){
            int[][] tempMatrix = initMatrix(x_axis,y_axis);

            for(int j=0; j<x_axis; j++){
                Random rand = new Random();
                //In 1 in 25 cases(4%) in x_axis(DAYS) change hard constraint assignment(make it invalid)
                if( new Random().nextDouble() <= 0.04 ) {
                    tempMatrix[j][0] = 0;
                }
                for(int k=0; k<y_axis; k++){
                    //In 1 in 500 case change the hard constraint assignment(make it invalid)
//                    if( new Random().nextDouble() <= 0.002 ) {
//                        tempMatrix[j][k] = 0;
//                    }
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
