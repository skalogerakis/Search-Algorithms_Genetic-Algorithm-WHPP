package AlgoPackage;


import MainPackage.*;

import java.util.*;

//Implementation based on this pseudocode
//http://turing.cs.pub.ro/blia_2003/Real-time_search_1.htm

public class LRTA_Star {

    private Nodes[][] heuristicArea;
    private Nodes finalNode;
    private Grid grid;
    private int startingX;
    private int startingY;
    private int[] stepsMatrix;
    int minFValue;
    LinkedList<Nodes> visitedList = new LinkedList<>();

    static class Nodes {

        private int curX;
        private int curY;
        private int bestPathCost;
        private Nodes parent;
        int heuristic;

        public Nodes(int curX,int curY){

            this.curX = curX;
            this.curY = curY;

        }

        //TODO CHANGE THAT
        public int calculateHeuristic(Nodes finalNode) {
            this.heuristic = Math.abs(finalNode.curX - curX) + Math.abs(finalNode.curY - curY);
            return this.heuristic;
        }

        public void setParent(Nodes parent) {
            this.parent = parent;
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
        Nodes currentNode = new Nodes(this.startingX,this.startingY);
        this.visitedList.add(currentNode);
        currentNode.setParent(null);

        while(currentNode != null){

            if (this.grid.getCell(currentNode.curX,currentNode.curY).isTerminal()){
                this.visitedList.addLast(currentNode);
                returnValue[0] = 1;
                returnValue[1] = currentNode.bestPathCost;
                return returnValue;
            }


            this.minFValue = Integer.MAX_VALUE;
            Nodes nextNode = null;

            /**
             * Starting searching all your neighbours for min f value and assign it to
             * h(heuristic value) of current Node. We also do online search
             */
            if(isValid(currentNode.curX,currentNode.curY-1)){
                this.grid.setCellVisited(currentNode.curX,currentNode.curY-1);

                if(LTRA_F_Finder(currentNode.curX,currentNode.curY-1) < this.minFValue){
                    this.minFValue = LTRA_F_Finder(currentNode.curX,currentNode.curY-1);
                    nextNode = this.heuristicArea[currentNode.curX][currentNode.curY - 1];
                }

            }


            if(isValid(currentNode.curX-1,currentNode.curY)){
                this.grid.setCellVisited(currentNode.curX - 1,currentNode.curY);

                if(LTRA_F_Finder(currentNode.curX - 1,currentNode.curY) < this.minFValue){
                    this.minFValue = LTRA_F_Finder(currentNode.curX - 1,currentNode.curY);
                    nextNode = this.heuristicArea[currentNode.curX - 1][currentNode.curY];
                }
            }


            if(isValid(currentNode.curX, currentNode.curY + 1)){

                this.grid.setCellVisited(currentNode.curX,currentNode.curY+1);
                if(LTRA_F_Finder(currentNode.curX,currentNode.curY+1) < this.minFValue){
                    this.minFValue = LTRA_F_Finder(currentNode.curX,currentNode.curY+1);
                    nextNode = this.heuristicArea[currentNode.curX][currentNode.curY + 1];
                }
            }

            if(isValid(currentNode.curX+1,currentNode.curY)){

                this.grid.setCellVisited(currentNode.curX + 1,currentNode.curY);
                if(LTRA_F_Finder(currentNode.curX + 1,currentNode.curY) < this.minFValue){
                    this.minFValue = LTRA_F_Finder(currentNode.curX + 1,currentNode.curY);
                    nextNode = this.heuristicArea[currentNode.curX+1][currentNode.curY];
                }
            }


            //assign min f value to current Node heuristic function
            this.heuristicArea[currentNode.curX][currentNode.curY].heuristic = minFValue;
            //TODO CHECK THAT
            int cost = this.grid.getCell(nextNode.curX,nextNode.curY).isGrass() ? 2 : 1;

            nextNode.bestPathCost = currentNode.bestPathCost + cost;
            nextNode.setParent(currentNode);
            this.visitedList.add(nextNode);
            currentNode = nextNode;

        }
        return returnValue;
    }


    private int LTRA_F_Finder(int nextX, int nextY){
        int currentVal = this.grid.getCell(nextX,nextY).isGrass() ? 2 : 1;

        return this.heuristicArea[nextX][nextY].heuristic + currentVal;
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
     * to reach the final goal(Only the best path)
     * @return
     */
    public int[] getStepsMatrix() {

        Nodes nodeback = visitedList.pollLast();
        int counter = 0;

        while (!visitedList.isEmpty()) {
            stepsMatrix[counter] = nodeback.curX * this.grid.getNumOfColumns() + nodeback.curY;
            counter++;
            nodeback = visitedList.pollLast();
        }

        return stepsMatrix;
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
