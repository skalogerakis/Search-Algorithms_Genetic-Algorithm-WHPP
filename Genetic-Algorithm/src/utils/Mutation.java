package utils;

import java.util.Arrays;

public class Mutation {

    /**
     * Mutation techniques based on the following citation
     * https://www.hindawi.com/journals/mpe/2015/906305/
     * The two techniques used are, two dimensional two point swapping
     * mutation and two dimensional String Swapping mutations
     */
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
     * This method simply chooses two random rows(days) and swaps them.
     */
    public int[][] stringSwapping(){

        int[][] child = this.parent.clone();
        int MAX = this.x_axis - 1;
        int rand1;
        int rand2;

//        System.out.println("\n\nBefore mutation");
//        System.out.println(Arrays.deepToString(child).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

        do{
            rand1 = (int) (Math.random() * ((MAX - MIN) + 1)) + MIN;
            rand2 = (int) (Math.random() * ((MAX - MIN) + 1)) + MIN;
        }while (rand1 == rand2);

//        System.out.println("Rand1 "+rand1);
//        System.out.println("Rand2 "+rand2);

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

//        System.out.println("\n\nAfter mutation");
//        System.out.println(Arrays.deepToString(child).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

        return child;
    }

    public int[][] twoPointSwapping(){


        int[][] child = this.parent.clone();

        int xMAX = this.x_axis - 1;
        int yMAX = this.y_axis - 1;
        int randx, randxnext;
        int randy, randynext;

//        System.out.println("\n\nBefore mutation");
//        System.out.println(Arrays.deepToString(child).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

        do{
            randx = (int) (Math.random() * ((xMAX - MIN) + 1)) + MIN;
            randy = (int) (Math.random() * ((yMAX - MIN) + 1)) + MIN;
            randxnext = (int) (Math.random() * ((xMAX - MIN) + 1)) + MIN;
            randynext = (int) (Math.random() * ((yMAX - MIN) + 1)) + MIN;

        }while (randx == randxnext && randy == randynext);

//        System.out.println("\n\nRandx "+randx);
//        System.out.println("Randy "+randy);
//        System.out.println("Randxnext "+randxnext);
//        System.out.println("Randynext "+randynext);
        //TODO Generate a random number. If random number larger than mutation rate proceed


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

//        System.out.println("\n\nAfter mutation");
//        System.out.println(Arrays.deepToString(child).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

        return child;
    }

    public int[][] twoPointSwappingSameRow(){


        int[][] child = this.parent.clone();

        int xMAX = this.x_axis - 1;
        int yMAX = this.y_axis - 1;
        int randx;
        int randy, randynext;

//        System.out.println("\n\nBefore mutation");
//        System.out.println(Arrays.deepToString(child).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

        do{
            randx = (int) (Math.random() * ((xMAX - MIN) + 1)) + MIN;
            randy = (int) (Math.random() * ((yMAX - MIN) + 1)) + MIN;
            //randxnext = (int) (Math.random() * ((xMAX - MIN) + 1)) + MIN;
            randynext = (int) (Math.random() * ((yMAX - MIN) + 1)) + MIN;

        }while (randy == randynext);

//        System.out.println("\n\nRandx "+randx);
//        System.out.println("Randy "+randy);
//        System.out.println("Randynext "+randynext);
        //TODO Generate a random number. If random number larger than mutation rate proceed


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

//        System.out.println("\n\nAfter mutation");
//        System.out.println(Arrays.deepToString(child).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

        return child;
    }

    public int[][] InverseDays(){


        int[][] child = this.parent.clone();

        int xMAX = this.x_axis - 1;
        int yMAX = this.y_axis - 1;
        int randx;
        int randy, randynext;

//        System.out.println("\n\nBefore mutation");
//        System.out.println(Arrays.deepToString(child).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

        do{
            randx = (int) (Math.random() * ((xMAX - MIN) + 1)) + MIN;
            randy = (int) (Math.random() * ((yMAX - MIN) + 1)) + MIN;
            //randxnext = (int) (Math.random() * ((xMAX - MIN) + 1)) + MIN;
            randynext = (int) (Math.random() * ((yMAX - MIN) + 1)) + MIN;

        }while (randy == randynext);

//        System.out.println("\n\nRandx "+randx);
//        System.out.println("Randy "+randy);
//        System.out.println("Randynext "+randynext);
        //TODO Generate a random number. If random number larger than mutation rate proceed

        int first;
        int last;
        if(randy>randynext){
            first = randynext;
            last = randy;
        }else{
            first = randy;
            last = randynext;
        }
        int[] firstVal = {-1,-1,-1};
        int index = 0;
        boolean flag = false;
        int[] revArr = new int[Math.abs(randy - randynext)+1];
        int revValue;
        for(int x = 0; x < this.x_axis ; x++){
            for(int y = 0; y < this.y_axis ; y++){

                if( x == randx && y >=first && !flag){
//                    System.out.println("FIRST "+ (y+index));
//                    System.out.println("NEXT "+ (last-index));

                    if( (last-index) - (first+index) <= 0){
                        flag = true;
                        break;
                    }
                    revValue = child[x][first+index];
                    child[x][first+index] = child[x][last-index];
                    child[x][last-index] = revValue;
                    index++;



//                    revArr[index++] = child[x][y];

//                    if(firstVal[0] == -1){
//                        firstVal[0] = x;
//                        firstVal[1] = y;
//                        firstVal[2] = child[x][y];
//                    }else {
//                        child[firstVal[0]][firstVal[1]] = child[x][y];
//                        child[x][y] = firstVal[2];
//
//                    }


                }

                if(y == randy || y == randynext && index!=1){

                }
            }
        }

//        System.out.println("\n\nAfter mutation");
//        System.out.println(Arrays.deepToString(child).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

        return child;
    }

}
