package AlgoPackage;

import MainPackage.*;
import java.util.LinkedList;

//Based on pseudocode _DFS iterative in that page
//https://www.hackerearth.com/practice/algorithms/graphs/depth-first-search/tutorial/

public class DFS {

    private int returnValue = -1;
    private Grid grid;
    private int startingX;
    private int startingY;
    private int[] stepsMatrix;
    LinkedList <Node> visitedList = new LinkedList<>();

    static class Node {

        private int curX;
        private int curY;
        private boolean visited;
        private int bestPathCost;
        private Node parent;


        public Node(int curX, int curY, boolean visited, int bestPathCost) {

            this.curX = curX;
            this.curY = curY;
            this.visited = visited;
            this.bestPathCost = bestPathCost;
        }

        public String toString() {
            return "Position: (" + this.curX + "," + this.curY + ") - " + "\nCost till now: " + bestPathCost;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }


    }

    public DFS(Grid grid, int startingX, int startingY){
        this.grid = grid;
        this.startingX = startingX;
        this.startingY = startingY;
        this.stepsMatrix = new int[(this.grid.getNumOfColumns()+1)*(this.grid.getNumOfRows()+1)];
    }

    public int DFS_Search(){


        LinkedList<Node> stack = new LinkedList<>();

        //We start from -1 cost as the first iteration will add 1 to the starting point.
        //To get exact result we 'omit' the first iteration that way
        Node start = new Node(grid.getStart()[0],grid.getStart()[1],false,-1);

        stack.add(start);
        this.visitedList.add(start);
        this.grid.setCellVisited(this.startingX,this.startingY);

        while (!stack.isEmpty()){
            Node current = stack.poll();
            //Direction: UP,RIGHT, LEFT, DOWN


            if(grid.getCell(current.curX,current.curY).isTerminal()) {
                returnValue = current.bestPathCost;
                this.visitedList.addLast(current);
                return returnValue;
            }

            this.grid.setCellVisited(current.curX,current.curY);
            this.visitedList.add(current);

            //choose right cost depending on the terrain
            int curCost = this.grid.getCell(current.curX,current.curY).getCost();
            current.bestPathCost =  current.bestPathCost + curCost;

            //Go Up
            if(isValid(current.curX,current.curY - 1)){
                Node temp = new Node(current.curX, current.curY - 1,false,current.bestPathCost);
                stack.push(temp);
                temp.setParent(current);
            }

            //Go Right
            if(isValid(current.curX + 1,current.curY)){
                Node temp = new Node(current.curX + 1, current.curY,false,current.bestPathCost);
                stack.push(temp);
                temp.setParent(current);
            }

            //Go Left
            if(isValid(current.curX - 1, current.curY)){
                Node temp = new Node(current.curX - 1, current.curY,false,current.bestPathCost);
                stack.push(temp);
                temp.setParent(current);
            }

            //Go Down
            if(isValid(current.curX,current.curY + 1)){
                Node temp  = new Node(current.curX, current.curY + 1,false,current.bestPathCost);
                stack.push(temp);
                temp.setParent(current);
            }





        }

        return returnValue;

    }

    //isValid function is responsible to check if next available node is accessible(in a similar fashion to BFS)
    private boolean isValid(int nextX, int nextY){
        try {
            if(this.grid.getCell(nextX,nextY).isWall() || this.grid.isCellVisited(nextX,nextY) || nextX < 0 || nextX >= this.grid.getNumOfRows() || nextY < 0 || nextY >= this.grid.getNumOfColumns() ){
                return false;
            }

        }catch (ArrayIndexOutOfBoundsException ao){
            return false;
        }

        return true;

    }

    /**
     * Function created for visualization purposes in the Drawing. It demanded a 1d array with all the steps required
     * to reach the final goal(Only the best path)
     * @return
     */
    public int[] getStepsMatrix() {

        Node nodeback = visitedList.getLast();

        int counter = 0;

        while (nodeback != null) {
            stepsMatrix[counter] = nodeback.curX * this.grid.getNumOfColumns() + nodeback.curY;
            counter++;

            for (int i = 0; i < visitedList.size(); i++) {
                try {
                    if (nodeback.getParent().curX == visitedList.get(i).curX && nodeback.getParent().curY == visitedList.get(i).curY) {
                        nodeback = visitedList.get(i);
                    }
                } catch (NullPointerException io) {
                    nodeback = null;
                    break;
                }

            }

        }
        return stepsMatrix;
    }


    /**
     * Function created for visualization purposes in the Drawing. It demanded a 1d array with all the steps required
     * to reach the final goal(All visited nodes)
     * @return
     */
    public int[] getAllStepsMatrix(int mode) {

        int counter = 0;
        int totalCost = 0;

        for(int i = 0; i< this.grid.getNumOfRows();i++){
            for(int j = 0; j < this.grid.getNumOfColumns();j++){
                if(this.grid.isCellVisited(i,j) && !this.grid.getCell(i,j).isStart() && !this.grid.getCell(i,j).isTerminal()){
                    if(mode == 1){
                        stepsMatrix[counter] = i * this.grid.getNumOfColumns() + j;
                    }
                    totalCost += this.grid.getCell(i,j).getCost();
                    counter++;
                }

            }
        }

        System.out.println("Full Total cost "+totalCost);
        return stepsMatrix;
    }
}
