package AlgoPackage;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

//TODO add comments
public class BFS {

    int[][] sampleMatrix;
    int maxSizeRow;
    int maxSizeCol;
    int[] stepsMatrix;
    int StartCoorX;
    int StartCoorY;
    Node targetNode = new Node();
    LinkedList<Node> visited = new LinkedList<Node>();

    static class Node{
        int curX;
        int curY;
        Node parentNode;

        public Node(){

        }

        public Node(int curX, int curY,Node parent){
            this.curX = curX;
            this.curY = curY;
            this.parentNode = parent;
        }
    }

    public BFS(int StartCoorX, int StartCoorY, int maxSizeRow, int maxSizeCol, int[][] myMatrix){

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

        printer(this.sampleMatrix);
        bfs();

        System.out.println("\n\n");
        printer(this.sampleMatrix);
    }


    /**
     * TODO change that
     * Function that applies depth first search algorithm.The function works recursively.
     * Based on implementation of different another personal project from link below
     * https://github.com/skalogerakis/TUC_Autonomous_Map_Exploring_Car/blob/master/Milestone2/DFS/main.c
     */
    public void bfs()
    {
        LinkedList<Node> queue = new LinkedList<Node>();


        Node node = new Node(this.StartCoorX,this.StartCoorY,null);
        queue.add(node);
        visited.add(node);

        while (!queue.isEmpty()){
            Node curNode = queue.poll();

            if(sampleMatrix[curNode.curX][curNode.curY] == 9){
//                targetNode.curX = curNode.curX;
//                targetNode.curY = curNode.curY;
                visited.addLast(curNode);
                return;
            }


            sampleMatrix[curNode.curX][curNode.curY] = 7;

//            Node nextNodeN = new Node(curNode.curX-1,curNode.curY);
            if(isValid(curNode.curX-1, curNode.curY)){
                queue.add(new Node(curNode.curX-1,curNode.curY,curNode));
                visited.add(new Node(curNode.curX-1,curNode.curY,curNode));
                //sampleMatrix[curNode.curX-1][curNode.curY] = 7;
            }

            if(isValid(curNode.curX, curNode.curY-1)){
                queue.add(new Node(curNode.curX,curNode.curY-1,curNode));
                visited.add(new Node(curNode.curX,curNode.curY-1,curNode));
                //sampleMatrix[curNode.curX][curNode.curY-1] = 7;
            }

            if(isValid(curNode.curX+1, curNode.curY)){
                queue.add(new Node(curNode.curX+1,curNode.curY,curNode));
                visited.add(new Node(curNode.curX+1,curNode.curY,curNode));
                //sampleMatrix[curNode.curX+1][curNode.curY] = 7;
            }

            if(isValid(curNode.curX, curNode.curY+1)){
                queue.add(new Node(curNode.curX,curNode.curY+1,curNode));
                visited.add(new Node(curNode.curX,curNode.curY+1,curNode));
                //sampleMatrix[curNode.curX][curNode.curY+1] = 7;
            }

        }
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

        Node nodeback = visited.getLast().parentNode;

        int counter=0;

        while(nodeback!=null){
            stepsMatrix[counter] = nodeback.curX*maxSizeCol+nodeback.curY;
            counter++;

            for(int i=0; i < visited.size();i++){
                try {
                    if(nodeback.parentNode.curX == visited.get(i).curX && nodeback.parentNode.curY == visited.get(i).curY) {
                        nodeback = visited.get(i);
                    }
                }catch (NullPointerException io){
                    nodeback = null;
                    break;
                }

            }

//            visited.indexOf(nodeback.parentNode);
//            }catch (NullPointerException io){
//                break;
//            }

        }

//        for(int i=0; i < maxSizeRow;i++){
//            for (int j=0; j < maxSizeCol;j++){
//
//                if( sampleMatrix[i][j] == 7 && !(this.StartCoorY==j && this.StartCoorX == i)){
////                    System.out.println(i*maxSizeCol+j);
//                    stepsMatrix[counter++] = i*maxSizeCol+j;
//
//                }
//            }
//        }
        return stepsMatrix;
    }
}