package AlgoPackage;

import java.util.Arrays;
import java.util.LinkedList;

import static java.lang.Math.sqrt;

public class A_Star {

    int[][] sampleMatrix;
    int maxSizeRow;
    int maxSizeCol;
    int[] stepsMatrix;
    int StartCoorX;
    int StartCoorY;
    int FinishCoorX;
    int FinishCoorY;
    Node[][] nodeSpecs;


    static class Node{
        int curX;
        int curY;
        double heuristicCost;
        int targetX;
        int targetY;

        public Node(){

        }

        public Node(int curX, int curY,int targetX, int targetY){
            this.curX = curX;
            this.curY = curY;
            this.targetX = targetX;
            this.targetY = targetY;
            this.heuristicCost = getHeuristicCost();
        }

        public double getHeuristicCost(){
            heuristicCost = sqrt(Math.pow(curX - targetX,2)- Math.pow(curY - targetY,2));
            return heuristicCost;
        }
    }

    public A_Star(int StartCoorX, int StartCoorY,int FinishCoorX, int FinishCoorY, int maxSizeRow, int maxSizeCol, int[][] myMatrix){

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
        this.StartCoorX = StartCoorX;
        this.StartCoorY = StartCoorY;
        this.FinishCoorX = FinishCoorX;
        this.FinishCoorY = FinishCoorY;

        printer(this.sampleMatrix);

        nodeInitializer();

        A_star();
        //https://github.com/malkfilipp/maze-runner/blob/master/src/maze/algo/solving/Fugitive.java
        //https://github.com/malkfilipp/maze-runner/blob/master/src/maze/algo/solving/Node.java

        System.out.println("\n\n");
        printer(this.sampleMatrix);
    }


    /**
     * TODO change that
     * Function that applies depth first search algorithm.The function works recursively.
     * Based on implementation of different another personal project from link below
     * https://github.com/skalogerakis/TUC_Autonomous_Map_Exploring_Car/blob/master/Milestone2/DFS/main.c
     */
    public void A_star()
    {

    }


    //2D array printer for debbuging purposes only
    public void printer(int arr[][])
    {
        // Loop through all rows
        for (int[] row : arr)
            // converting each row as string
            // and then printing in a separate line
            System.out.println(Arrays.toString(row));
    }

    public boolean isValid(int nextX, int nextY){
        try {
            if(this.sampleMatrix[nextX][nextY] == 5 || this.sampleMatrix[nextX][nextY] == 7 || nextX < 0 || nextX >= this.maxSizeRow || nextY < 0 || nextY >= this.maxSizeCol ) return false;

        }catch (ArrayIndexOutOfBoundsException ao){
            return false;
        }
        return true;
    }

    public int[] getStepsMatrix(){
        return stepsMatrix;
    }

    public void nodeInitializer(){
        for(int i = 0; i < this.maxSizeRow; i++){
            for(int j = 0; j < this.maxSizeCol; j++){
                //In case we don't hit a wall compute heuristic
                if(this.sampleMatrix[i][j] != 5 ){
                    //this.nodeSpecs
                    this.nodeSpecs[i][j] = new Node(i,j,this.FinishCoorX,this.FinishCoorY);
                }
            }
        }
    }
}
