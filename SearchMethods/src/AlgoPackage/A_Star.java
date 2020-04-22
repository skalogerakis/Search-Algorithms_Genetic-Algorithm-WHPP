package AlgoPackage;

import MainPackage.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;


//Based on pseudocode
//https://gist.github.com/damienstanton/7de65065bf584a43f96a
public class A_Star {

    Nodes[][] heuristicArea;
    PriorityQueue<Nodes> openList;
    ArrayList<Nodes> closedSet;
    private Nodes finalNode;
    private Grid grid;
    private int startingX;
    private int startingY;
    private int[] stepsMatrix;
    private int returnValue = -1;

    LinkedList<Nodes> visitedList = new LinkedList<>();

    //TODO almost done here just get rid of some unused variables
    static class Nodes implements Comparable<Nodes>{

        private int curX;
        private int curY;
        private Nodes parent;
        private int f_score = 0;
        private int g_score;
        private int h_score;

        public Nodes(int curX,int curY){

            this.curX = curX;
            this.curY = curY;

        }

        public String toString(){
            return "Position: (" + this.curX + "," + this.curY + ") - " + "\nCost till now: " + f_score;
        }


        //TODO CHANGE THAT
        public int calculateHeuristic(Nodes finalNode) {
            this.h_score = Math.abs(finalNode.curX - curX) + Math.abs(finalNode.curY - curY);
            return this.h_score;
        }

        public boolean isBetter(Nodes currentNode, int cost) {

            if (currentNode.g_score + cost < this.g_score) {
                setParent(currentNode);
                this.g_score = currentNode.g_score + cost;
                this.f_score = this.g_score + this.h_score;
                return true;
            }
            return false;
        }

        public Nodes getParent() {
            return parent;
        }

        public void setParent(Nodes parent) {
            this.parent = parent;
        }

        //Make nodes comparable for our priority queue
        @Override
        public int compareTo(Nodes o) {
            if(this.f_score > o.f_score) {
                return 1;
            } else if (this.f_score < o.f_score) {
                return -1;
            } else {
                return 0;
            }
        }
    }


    public A_Star(Grid grid,int startingX, int startingY) {

        this.grid = grid;
        this.heuristicArea = new Nodes[this.grid.getNumOfRows()][this.grid.getNumOfColumns()];
        this.startingX = startingX;
        this.startingY = startingY;
        this.finalNode = new Nodes(grid.getTerminal()[0],grid.getTerminal()[1]);
        this.closedSet = new ArrayList<>();
        this.stepsMatrix = new int[(this.grid.getNumOfColumns()+1)*(this.grid.getNumOfRows()+1)];

    }

    public int A_StarSearch() {

        nodeInitializer();

        this.openList = new NoDuplicates<Nodes>();


        openList.add(new Nodes(this.startingX,this.startingY));
        while (!openList.isEmpty()) {

            Nodes currentNode = openList.poll();
            //Direction: UP,RIGHT, LEFT, DOWN

            if (this.grid.getCell(currentNode.curX,currentNode.curY).isTerminal()) {
                this.visitedList.addLast(currentNode);
                returnValue = currentNode.f_score + 1;
                break;
            }

            int cost = this.grid.getCell(currentNode.curX,currentNode.curY).getCost();

            if(isValid(currentNode.curX,currentNode.curY - 1)){
                neighbourCheck(currentNode, currentNode.curX,currentNode.curY - 1, cost);
            }

            if(isValid(currentNode.curX+1,currentNode.curY)){
                neighbourCheck(currentNode, currentNode.curX + 1, currentNode.curY, cost);
            }

            if(isValid(currentNode.curX-1,currentNode.curY)){
                neighbourCheck(currentNode, currentNode.curX - 1, currentNode.curY, cost);
            }


            if(isValid(currentNode.curX, currentNode.curY + 1)){
                neighbourCheck(currentNode, currentNode.curX,currentNode.curY + 1, cost);
            }

            closedSet.add(currentNode);
            this.grid.setCellVisited(currentNode.curX,currentNode.curY);
        }

        return returnValue;
    }


    private void neighbourCheck(Nodes currentNode, int row, int col,int cost) {
        Nodes neighNode = this.heuristicArea[row][col];

        //if neighbour in closed set
        if(this.closedSet.contains(neighNode)) return;

        if (!this.openList.contains(neighNode) || neighNode.isBetter(currentNode, cost)) {
            this.visitedList.add(currentNode);
            neighNode.setParent(currentNode);
            neighNode.g_score = currentNode.g_score + cost;
            neighNode.f_score = neighNode.g_score + neighNode.h_score;

            this.openList.add(neighNode);
        }

    }

    private boolean isValid(int nextX, int nextY){
        try {
            if(this.grid.getCell(nextX,nextY).isWall() || nextX < 0 || nextX >= this.grid.getNumOfRows() || nextY < 0 || nextY >= this.grid.getNumOfColumns() ){
                return false;
            }

        }catch (ArrayIndexOutOfBoundsException ao){
            return false;
        }

        return true;

    }

    public void nodeInitializer(){

        for (int i = 0; i < this.grid.getNumOfRows(); i++) {
            for (int j = 0; j < this.grid.getNumOfColumns(); j++) {
                //Don't calculate heuristic values for walls(cannot be visited)
                if(!this.grid.getCell(i,j).isWall()){
                    Nodes node = new Nodes(i,j);
                    node.calculateHeuristic(this.finalNode);
                    this.heuristicArea[i][j] = node;
                }
            }
        }

    }

    //Eliminate duplicates. NOTE: In final version there should not be any duplicates
    class NoDuplicates<E> extends PriorityQueue<E> {

        /**
         * Modified class to meet our needs. What this does, is that in case the element we want
         * does not exist in our class add it, or otherwise re-shuffle priority queue to poll the
         * element we need
         */

        public boolean add(E e) {

            boolean isAdded = false;
            if (!super.contains(e)) {
                isAdded = super.add(e);
            }
            super.remove(e);
            super.add(e);
            return isAdded;
        }

    }

    /**
     * Function created for visualization purposes in the Drawing. It demanded a 1d array with all the steps required
     * to reach the final goal(Only the best path)
     * @return
     */
    public int[] getStepsMatrix() {

        Nodes nodeback = visitedList.getLast().getParent();

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