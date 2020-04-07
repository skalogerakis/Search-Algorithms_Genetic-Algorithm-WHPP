package AlgoPackage;

import java.util.Arrays;

public class __DFS {

    //We define starting point statically. We know it in our given code
    //make everything global and static. TODO change that
    static int initCoor[] = {1,1};
    //TODO also very important to figure out what to do with size
    static int sizeMatr = 5;

    static int[][] sampleMatrix = {
            {2,2,2,2,2},
            {1,3,1,2,2},
            {2,1,2,2,2},
            {2,1,1,2,2},
            {2,2,2,2,1},
            {9,2,2,2,1}};

    public static void main(String[] args) {
        /**
         * Static array used in our scenarios. If you wish to modify don't forget to change
         * sizeMatr accordingly
         * 1 used as walls
         * 2 used as visiting nodes
         * 7 used for nodes that are already visited
         * 9 used as termination point
         * 3 used as starting point
         */



        printer(sampleMatrix);
//        System.out.println(initArr.length+" ,"+ initArr.length);
        dfs(initCoor[0], initCoor[1]);

        System.out.println("\n\n");
        printer(sampleMatrix);

    }


    /**
     * Function that applies depth first search algorithm.The function works recursively.
     * Based on implementation of different another project from link below
     * https://github.com/skalogerakis/TUC_Autonomous_Map_Exploring_Car/blob/master/Milestone2/DFS/main.c
     */
    public static int dfs(int row, int col)
    {
        //Don't go out of bounds
        if (row < 0 || row > sizeMatr || col < 0 || col > sizeMatr){
            return 0;
        }

        if (sampleMatrix[row][col] == 9) {
            return 1;
        }

        /*
         * Our implementation applies dfs for nodes first upwards(UP), then leftwards(LEFT), then downwards
         * (DOWN) and finally rightwatds(RIGHT)
         */

        if (sampleMatrix[row][col] == 2 || sampleMatrix[row][col] == 3) {
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



    public static void printer(int arr[][])
    {
        // Loop through all rows
        for (int[] row : arr)
            // converting each row as string
            // and then printing in a separate line
            System.out.println(Arrays.toString(row));
    }
}
