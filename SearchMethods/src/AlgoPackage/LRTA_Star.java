package AlgoPackage;


import MainPackage.*;

import java.util.ArrayList;
import java.util.List;

//Implementation based on this pseudocode
//http://turing.cs.pub.ro/blia_2003/Real-time_search_1.htm

public class LRTA_Star {

    private Nodes[][] heuristicArea;
    private Nodes finalNode;
    private Grid grid;
    private int startingX;
    private int startingY;
    private int[] stepsMatrix;

    static class Nodes {

        private int curX;
        private int curY;
        private boolean visited;
        private int bestPathCost;
        private Nodes parent;
        private int f = 0;
        private int g;
        private int h;

        public Nodes(int curX,int curY){

            this.curX = curX;
            this.curY = curY;

        }

        public String toString(){
            return "Position: (" + this.curX + "," + this.curY + ") - " + "Visited: " + this.isVisited() + "\nCost till now: " + bestPathCost;
        }

        public boolean isVisited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        //TODO CHANGE THAT
        public int calculateHeuristic(Nodes finalNode) {  this.h = Math.abs(finalNode.curX - curX) + Math.abs(finalNode.curY - curY);
            return this.h;
        }

        public Nodes getParent() {
            return parent;
        }

        public void setParent(Nodes parent) {
            this.parent = parent;
        }

        public int getH() {
            return h;
        }

        public void setH(int h) {
            this.h = h;
        }

    }

    public LRTA_Star(Grid grid, int startingX,int startingY) {
        this.grid = grid;
        this.heuristicArea = new Nodes[grid.getNumOfRows()][grid.getNumOfColumns()];
        this.startingX = startingX;
        this.startingY = startingY;
        this.finalNode = new Nodes(grid.getTerminal()[0],grid.getTerminal()[1]);
        this.stepsMatrix = new int[(this.grid.getNumOfColumns()+1)*(this.grid.getNumOfRows()+1)];

    }

    /**
     * The same as a star algorithm. We initialize this array to compute all the heuristic costs across the board
     */
    private void nodeInitializer() {

        for (int i = 0; i < this.grid.getNumOfRows(); i++) {
            for (int j = 0; j < this.grid.getNumOfColumns(); j++) {

                if(!this.grid.getCell(i,j).isWall()){
                    Nodes node = new Nodes(i,j);
                    node.calculateHeuristic(this.finalNode);
                    this.heuristicArea[i][j] = node;
                }

            }
        }
    }


    public int[] LRTA_StarSearch() {

        nodeInitializer();

        int[] returnValue = {0,-1};
        Nodes currentNode = new Nodes(this.startingX,this.startingY);;

        while(currentNode != null){

            if (this.grid.getCell(currentNode.curX,currentNode.curY).isTerminal()){
                returnValue[0] = 1;
                returnValue[1] = currentNode.bestPathCost;
                return returnValue;
            }


            int minFValue = Integer.MAX_VALUE;
            List<Nodes> neighNodes = validNeighList(currentNode);
            Nodes nextNode = null;
            for (Nodes n : neighNodes) {
                int fValue = getLRTACost(n);
                //n.setVisited(true);
                this.grid.setCellVisited(n.curX,n.curY);
                if (fValue < minFValue){
                    minFValue = fValue;
                    nextNode = n;
                }
            }
            this.heuristicArea[currentNode.curX][currentNode.curY].setH(minFValue);

            //TODO CHECK THAT
            int cost = this.grid.getCell(nextNode.curX,nextNode.curY).isGrass() ? 2 : 1;

            nextNode.bestPathCost = currentNode.bestPathCost + cost;

            currentNode = nextNode;

        }
        return returnValue;
    }

    private List<Nodes> validNeighList(Nodes node) {

        List<Nodes> neighbours = new ArrayList<>();

        if(isValid(node.curX,node.curY-1)){
            neighbours.add(this.heuristicArea[node.curX][node.curY - 1]);
        }


        if(isValid(node.curX-1,node.curY)){
            neighbours.add(this.heuristicArea[node.curX - 1][node.curY]);
        }

        if(isValid(node.curX, node.curY + 1)){
            neighbours.add(this.heuristicArea[node.curX][node.curY + 1]);
        }

        if(isValid(node.curX+1,node.curY)){
            neighbours.add(this.heuristicArea[node.curX + 1][node.curY]);
        }
        return neighbours;

    }

    private int getLRTACost(Nodes candNext){
        int fValue;
        int kValue;


        int hValue = this.heuristicArea[candNext.curX][candNext.curY].getH();

        kValue = this.grid.getCell(candNext.curX,candNext.curY).isGrass() ? 2 : 1;
        fValue = hValue + kValue;

        return fValue;
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

    /**
     * Function created for visualization purposes in the Drawing. It demanded a 1d array with all the steps required
     * to reach the final goal(All visited nodes)
     * @return
     */
    public int[] getAllStepsMatrix() {

        int counter = 0;
        int totalCost = 0;

        for(int i = 0; i< this.grid.getNumOfRows();i++){
            for(int j = 0; j < this.grid.getNumOfColumns();j++){
                if(this.grid.isCellVisited(i,j) && !this.grid.getCell(i,j).isStart() && !this.grid.getCell(i,j).isTerminal()){
                    stepsMatrix[counter] = i * this.grid.getNumOfColumns() + j;
                    totalCost += this.grid.getCell(i,j).getCost();
                    counter++;
                }

            }
        }

        System.out.println("Total cost "+totalCost);
        return stepsMatrix;
    }

}
