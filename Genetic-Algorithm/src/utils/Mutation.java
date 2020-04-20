package utils;

import java.util.Arrays;

public class Mutation {


    int[][] parent;
    int x_axis;
    int y_axis;
    static int MIN = 0;

    public Mutation(int[][] parent,int x_axis, int y_axis){
        this.parent = parent;
        this.x_axis = x_axis;
        this.y_axis = y_axis;
    }

    /**
     * LEGACY METHODS: First two methods will not be used after testing. In our
     * problem these methods will most likely make our chromosome invalid which is
     * something that we do not necessarily want. We could add a new random chromosome
     * but that made the results worse, so we chose not to use them
     * Mutation techniques based on the following citation
     * https://www.hindawi.com/journals/mpe/2015/906305/
     */
    /*
     LEGACY: This method simply chooses two random rows(days) and swaps them.
     */
    public int[][] stringSwapping(){

        int[][] child = this.parent.clone();
        int MAX = this.x_axis - 1;
        int rand1;
        int rand2;

        do{
            rand1 = (int) (Math.random() * ((MAX - MIN) + 1)) + MIN;
            rand2 = (int) (Math.random() * ((MAX - MIN) + 1)) + MIN;
        }while (rand1 == rand2);

        int tempArr[] = new int[this.y_axis];
        int firstVal = -1;

        for(int x = 0; x < this.x_axis ; x++){
            for(int y = 0; y < this.y_axis ; y++){
                if(x == rand1 || x == rand2){
                    if(firstVal == -1){
                        tempArr[y] = child[x][y];
                        firstVal = y == this.y_axis - 1 ? x : - 1;

                    }else {
                        child[firstVal][y] = child[x][y];
                        child[x][y] = tempArr[y];

                    }

                }
            }
        }

        return child;
    }

    /*
    LEGACY: This method simply chooses random point from our chromosome and swaps them
     */
    public int[][] twoPointSwapping(){


        int[][] child = this.parent.clone();

        int xMAX = this.x_axis - 1;
        int yMAX = this.y_axis - 1;
        int randx, randxnext;
        int randy, randynext;


        do{
            randx = (int) (Math.random() * ((xMAX - MIN) + 1)) + MIN;
            randy = (int) (Math.random() * ((yMAX - MIN) + 1)) + MIN;
            randxnext = (int) (Math.random() * ((xMAX - MIN) + 1)) + MIN;
            randynext = (int) (Math.random() * ((yMAX - MIN) + 1)) + MIN;

        }while (randx == randxnext && randy == randynext);


        int[] firstVal = {-1,-1,-1};

        for(int x = 0; x < this.x_axis ; x++){
            for(int y = 0; y < this.y_axis ; y++){
                if((x == randx && y == randy) || (x == randxnext && y == randynext)){
                    if(firstVal[0] == -1){
                        firstVal[0] = x;
                        firstVal[1] = y;
                        firstVal[2] = child[x][y];
                    }else {
                        child[firstVal[0]][firstVal[1]] = child[x][y];
                        child[x][y] = firstVal[2];

                    }

                }
            }
        }

        return child;
    }

    /*
    This method is used to swap two points located in the same row. Both row and
    starting and finishing point are chosen randomly
     */
    public int[][] twoPointSwappingSameRow(){


        int[][] child = this.parent.clone();

        int xMAX = this.x_axis - 1;
        int yMAX = this.y_axis - 1;
        int randx;
        int randy, randynext;


        do{
            randx = (int) (Math.random() * ((xMAX - MIN) + 1)) + MIN;
            randy = (int) (Math.random() * ((yMAX - MIN) + 1)) + MIN;
            randynext = (int) (Math.random() * ((yMAX - MIN) + 1)) + MIN;

        }while (randy == randynext);


        int[] firstVal = {-1,-1,-1};

        for(int x = 0; x < this.x_axis ; x++){
            for(int y = 0; y < this.y_axis ; y++){
                if( x == randx && (y == randy || y == randynext)){
                    if(firstVal[0] == -1){
                        firstVal[0] = x;
                        firstVal[1] = y;
                        firstVal[2] = child[x][y];
                    }else {
                        child[firstVal[0]][firstVal[1]] = child[x][y];
                        child[x][y] = firstVal[2];

                    }

                }
            }
        }

        return child;
    }

    /*
    This method is used to inverse in a random day the content of a specific range.
    We randomly choose the starting and the ending point and we inverse the elements in
    that range
     */
    public int[][] InverseDays(){


        int[][] child = this.parent.clone();

        int xMAX = this.x_axis - 1;
        int yMAX = this.y_axis - 1;
        int randx;
        int randy, randynext;



        do{
            randx = (int) (Math.random() * ((xMAX - MIN) + 1)) + MIN;
            randy = (int) (Math.random() * ((yMAX - MIN) + 1)) + MIN;
            randynext = (int) (Math.random() * ((yMAX - MIN) + 1)) + MIN;

        }while (randy == randynext);


        int first;
        int last;
        if(randy>randynext){
            first = randynext;
            last = randy;
        }else{
            first = randy;
            last = randynext;
        }

        int index = 0;
        boolean flag = false;
        int[] revArr = new int[Math.abs(randy - randynext)+1];
        int revValue;
        for(int x = 0; x < this.x_axis ; x++){
            for(int y = 0; y < this.y_axis ; y++){

                if( x == randx && y >=first && !flag){

                    if( (last-index) - (first+index) <= 0){
                        flag = true;
                        break;
                    }
                    revValue = child[x][first+index];
                    child[x][first+index] = child[x][last-index];
                    child[x][last-index] = revValue;
                    index++;
                }
            }
        }


        return child;
    }

}
