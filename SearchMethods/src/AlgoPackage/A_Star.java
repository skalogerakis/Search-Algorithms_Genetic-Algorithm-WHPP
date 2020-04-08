package AlgoPackage;

import sun.awt.X11.XSystemTrayPeer;

import java.util.*;

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
    LinkedList<Node> pathToGoal = new LinkedList<>();


    /**
     * A priority queue to perform the selection of minimum
     * estimated cost node on every step of the algorithm.
     */
    private PriorityQueue<Node> open = new PriorityQueue<>();



    static class Node{
        int curX;
        int curY;
        double heuristicCost;
        int targetX;
        int targetY;
        double finalCost;
        Node parentNode;

        public Node(){

        }

        public Node(int curX, int curY,int targetX, int targetY){
            this.curX = curX;
            this.curY = curY;
            this.targetX = targetX;
            this.targetY = targetY;
            this.heuristicCost = getHeuristicCost();
            this.finalCost = getFinalCost();
        }

        public Node(int curX, int curY,int targetX, int targetY, Node parentNode){
            this.curX = curX;
            this.curY = curY;
            this.targetX = targetX;
            this.targetY = targetY;
            this.parentNode = parentNode;
            this.heuristicCost = getHeuristicCost();
            this.finalCost = getFinalCost();
        }

        public double getHeuristicCost(){
            heuristicCost = sqrt(Math.pow(curX - targetX,2)- Math.pow(curY - targetY,2));
            return heuristicCost;
        }

        public double getFinalCost(){
            return getHeuristicCost();
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

        Node[][] nodeSpecs = new Node[maxSizeRow][maxSizeCol];

        printer(this.sampleMatrix);

        //nodeSpecs = nodeInitializer(nodeSpecs);

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
        //https://mat.uab.cat/~alseda/MasterOpt/AStar-Algorithm.pdf
        //Implementation based on this pseudocode

        //Open List consists on nodes that have been visited but not expanded (meaning that sucessors have not been
        //explored yet). This is the list of pending tasks
        //We use a PriorityQueue for openList so that polling it always returns the node with minimum costEstimate
//        PriorityQueue<Node> openList = new PriorityQueue<Node>(10,
//                Comparator.comparingInt((Node a) -> a.calculateEstimate(gameMap.goal)));
        LinkedList<Node> openList = new LinkedList<>();
        //We use a HashSet because checking if a node is in it is O(1)
        //Closed List consists on nodes that have been visited and expanded (sucessors have been explored already and
        //included in the open list, if this was the case)
        HashSet<Node> closedList = new HashSet<>();

        openList.add(new Node(this.StartCoorX,this.StartCoorY,this.FinishCoorX,this.FinishCoorY,null));

//        System.out.println("Stage one");
        while (!openList.isEmpty()) {
            Node curNode = openList.poll();

            //If we reached the goal, end the game and print the path
            if(sampleMatrix[curNode.curX][curNode.curY] == 9){
                System.out.println("We reached to the end");
//                ArrayList<Node> pathToGoal = new ArrayList<>();

                while (curNode.parentNode != null) {
                    pathToGoal.add(curNode);
                    curNode = curNode.parentNode;
                }

//                for (int i = pathToGoal.size() - 1; i >= 0; i--){
//                    System.out.println(pathToGoal.get(i).curX +","+ pathToGoal.get(i).curY);
//                }
                break;
                //todo check what to do when we are done
              //  return;
            }


            ArrayList<Node> SuccessorNodes = new ArrayList<>();
            //Notice new node's g is increased by one because cost to move from starting point to this node is one
            //tile + the cost to move to the previous tile
            //Up

            if(isValid(curNode.curX-1, curNode.curY)){
                SuccessorNodes.add(new Node(curNode.curX-1,curNode.curY,this.FinishCoorX,this.FinishCoorY,curNode));

            }

            if(isValid(curNode.curX, curNode.curY-1)){
                SuccessorNodes.add(new Node(curNode.curX,curNode.curY-1,this.FinishCoorX,this.FinishCoorY,curNode));

            }

            if(isValid(curNode.curX+1, curNode.curY)){
                SuccessorNodes.add(new Node(curNode.curX+1,curNode.curY,this.FinishCoorX,this.FinishCoorY,curNode));

            }

            if(isValid(curNode.curX, curNode.curY+1)){
                SuccessorNodes.add(new Node(curNode.curX,curNode.curY+1,this.FinishCoorX,this.FinishCoorY,curNode));

            }


            //https://github.com/hayalbaz/a-star-java/blob/master/src/Main.java
            SuccessorLoop:
            for (Node successor: SuccessorNodes) {
                //Check if a node with same coordinates exists in openList that has better cost estimate
                //If so skip this node
                //Since PriorityQueue.contains() is O(n) operation, we might as well check with a for loop
                for (Node n: openList) {
//                    if (successor.sameCoordinates(n) && n.calculateEstimate(gameMap.goal) > successor.calculateEstimate(gameMap.goal)) {
//                        continue SuccessorLoop;
//                    }
                    if (successor.curX == n.curX && successor.curY == n.curY &&  n.heuristicCost > successor.heuristicCost) {
                        continue SuccessorLoop;
                    }
                }
                //Check if a node with same coordinates exists in closedList that has better cost estimate
                //If so skip this node
                if (closedList.contains(successor)) {
                    for (Node n: closedList) {
                        if (successor.curX == n.curX && successor.curY == n.curY &&  n.heuristicCost > successor.heuristicCost) {
                            continue SuccessorLoop;
                        }
                    }
                }
                //If not add this node to openList
                openList.add(successor);
            }
            //Add current node to closedList
            closedList.add(curNode);
            sampleMatrix[curNode.curX][curNode.curY] = 7;
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


        int counter=0;

            for(int i=0; i < pathToGoal.size();i++){
                Node nodeback = pathToGoal.get(i);
                stepsMatrix[counter++] = nodeback.curX*maxSizeCol+nodeback.curY;

            }

        return stepsMatrix;
    }

    public Node[][] nodeInitializer(Node[][] nodeSpecs){
        for(int i = 0; i < this.maxSizeRow; i++){
            for(int j = 0; j < this.maxSizeCol; j++){
                //In case we don't hit a wall compute heuristic
                if(this.sampleMatrix[i][j] != 5 ){
                    //this.nodeSpecs
                    nodeSpecs[i][j] = new Node(i,j,this.FinishCoorX,this.FinishCoorY);
                }
            }
        }
        return nodeSpecs;
    }
}
