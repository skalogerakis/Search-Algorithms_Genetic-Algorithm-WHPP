package AlgoPackage;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DFS {

    //We define starting point statically. We know it in our given code
    //make everything global and static. TODO change that
    //TODO also very important to figure out what to do with size
    static int sizeMatr = 5;
    int[][] sampleMatrix;
    int maxSizeRow;
    int maxSizeCol;
    int[] stepsMatrix;


    public DFS(int StartCoorX, int StartCoorY,int maxSizeRow,int maxSizeCol, int[][] myMatrix){

        /**
         * UPDATED:
         * 1 used as starting point
         * 3 used as visiting nodes, ground
         * 4 used as visiting nodes, grass
         * 5 used as walls
         * 9 used as termination point
         * 7 used for nodes that are already visited
         */
        this.sampleMatrix = myMatrix;
        this.maxSizeRow = maxSizeRow;
        this.maxSizeCol = maxSizeCol;
        this.stepsMatrix = new int[(maxSizeCol+1)*(maxSizeRow+1)];


        printer(this.sampleMatrix);
//        System.out.println(initArr.length+" ,"+ initArr.length);
        dfs(StartCoorX, StartCoorY);

        System.out.println("\n\n");
        printer(this.sampleMatrix);
    }


    /**
     * Function that applies depth first search algorithm.The function works recursively.
     * Based on implementation of different another personal project from link below
     * https://github.com/skalogerakis/TUC_Autonomous_Map_Exploring_Car/blob/master/Milestone2/DFS/main.c
     */
    public int dfs(int row, int col)
    {
        //Don't go out of bounds
        if (row < 0 || row >= maxSizeRow || col < 0 || col >= maxSizeCol){
            return 0;
        }

        if (sampleMatrix[row][col] == 9) {
            return 1;
        }

        /*
         * Our implementation applies dfs for nodes first upwards(UP), then leftwards(LEFT), then downwards
         * (DOWN) and finally rightwatds(RIGHT)
         */

        if (sampleMatrix[row][col] == 1 || sampleMatrix[row][col] == 3 || sampleMatrix[row][col] == 4) {
            sampleMatrix[row][col] = 7;


            if (dfs(row - 1, col) == 1){
                sampleMatrix[row][col] = 7;
                return 1;
            }

            if (dfs(row, col - 1) == 1){
                sampleMatrix[row][col] = 7;
                return 1;
            }

            if (dfs(row + 1, col) == 1){
                sampleMatrix[row][col] = 7;
                return 1;
            }

            if (dfs(row, col + 1) == 1){
                sampleMatrix[row][col] = 7;
                return 1;
            }

        }

        return 0;
    }



    public void printer(int arr[][])
    {
        // Loop through all rows
        for (int[] row : arr)
            // converting each row as string
            // and then printing in a separate line
            System.out.println(Arrays.toString(row));
    }

    public int[] getStepsMatrix(){
        int counter=0;
//        System.out.println(maxSizeCol);
//        System.out.println(maxSizeRow);

        for(int i=0; i < maxSizeRow;i++){
            for (int j=0; j < maxSizeCol;j++){
//                System.out.println(i);
//                System.out.println(j);
                if( sampleMatrix[i][j] == 7){
//                    System.out.println(i*maxSizeCol+j);
                    stepsMatrix[counter++] = i*maxSizeCol+j;

                }
            }
        }
        return stepsMatrix;
    }
}
