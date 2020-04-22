package AlgoPackage;

import MainPackage.*;

import java.util.LinkedList;


public class BFS {

    private int[] stepsMatrix;
    private Grid grid;
    private int curCost;
    private boolean flag=false;
    private int startingX;
    private int startingY;
    private int returnValue = -1;

    LinkedList <Nodes> visitedList = new LinkedList<>();


    static class Nodes {

        int curX;
        int curY;
        int bestPathCost;
        private Nodes parent;
        private boolean visited;

        public Nodes(int curX,int curY,boolean visited,int bestPathCost){

            this.curX = curX;
            this.curY = curY;
            this.visited = visited;
            this.bestPathCost = bestPathCost;
        }

        public String toString(){
            return "Position: (" + this.curX + "," + this.curX + ") - " + "Visited: " + this.visited + "\nCost till now: " + bestPathCost;
        }

        /**
         * Setters Getters for parents
         * @return
         */
        public Nodes getParent() {
            return parent;
        }

        public void setParent(Nodes parent) {
            this.parent = parent;
        }



    }


    public BFS(Grid grid,int startingX, int startingY){
        this.grid = grid;
        this.startingX = startingX;
        this.startingY = startingY;
        this.stepsMatrix = new int[(this.grid.getNumOfColumns()+1)*(this.grid.getNumOfRows()+1)];
    }


    //TODO add for everything the same direction in the end. Change return and add comment.
    public int BFS_search(){

        LinkedList <Nodes> queue = new LinkedList<Nodes>();

        Nodes start = new Nodes(this.startingX,this.startingY,false,1);

        queue.add(start);
        this.visitedList.add(start);
        this.grid.setCellVisited(this.startingX,this.startingY);

        /**
         * Added some extra functionality by including flag variable.
         * Flag is triggered when we find that the next node to be generated
         * will be the termination point. In that way we do not expand all the nodes for a level
         * but as soon as we reach our goal the computations stop.(Less sources used)
         */
        while(!queue.isEmpty() && !this.flag){
            Nodes curNode = queue.poll();

            //Direction: UP,RIGHT, LEFT, DOWN
            if(isValid(curNode.curX,curNode.curY-1)){
                this.grid.setCellVisited(curNode.curX,curNode.curY-1);
                Nodes temp = new Nodes(curNode.curX,curNode.curY-1,true,curNode.bestPathCost + this.curCost);
                queue.add(temp);
                temp.setParent(curNode);

                if(flag){
                    this.visitedList.addLast(temp);
                    returnValue = curNode.bestPathCost+1;
                    continue;
                }else {
                    this.visitedList.add(temp);
                }

            }

            if(isValid(curNode.curX,curNode.curY+1)){
                this.grid.setCellVisited(curNode.curX,curNode.curY+1);
                Nodes temp = new Nodes(curNode.curX,curNode.curY+1,true,curNode.bestPathCost + this.curCost);
                queue.add(temp);
                temp.setParent(curNode);

                if(flag){
                    this.visitedList.addLast(temp);
                    returnValue = curNode.bestPathCost+1;
                    continue;
                }else {
                    this.visitedList.add(temp);
                }
            }

            if(isValid(curNode.curX-1,curNode.curY)){
                this.grid.setCellVisited(curNode.curX-1,curNode.curY);
                Nodes temp = new Nodes(curNode.curX-1,curNode.curY,true,curNode.bestPathCost + this.curCost);
                queue.add(temp);
                temp.setParent(curNode);

                if(flag){
                    this.visitedList.addLast(temp);
                    returnValue = curNode.bestPathCost+1;
                    continue;
                }else {
                    this.visitedList.add(temp);
                }
            }

            if(isValid(curNode.curX+1,curNode.curY)){
                this.grid.setCellVisited(curNode.curX+1,curNode.curY);

                Nodes temp = new Nodes(curNode.curX+1,curNode.curY,true,curNode.bestPathCost + this.curCost);
                queue.add(temp);
                temp.setParent(curNode);

                if(flag){
                    this.visitedList.addLast(temp);
                    returnValue = curNode.bestPathCost+1;
                    continue;
                }else {
                    this.visitedList.add(temp);
                }
            }

        }
        return returnValue;
    }


    private boolean isValid(int nextX, int nextY){
        try {

            if(this.grid.getCell(nextX,nextY).isWall() || this.grid.isCellVisited(nextX,nextY) || nextX < 0 || nextX >= this.grid.getNumOfRows() || nextY < 0 || nextY >= this.grid.getNumOfColumns() ){
                this.curCost = -1;
                return false;
            }
        }catch (ArrayIndexOutOfBoundsException ao){
            this.curCost = -1;
            return false;
        }
        //Check if we have grass or land in that node and assign values respectively
        this.curCost = this.grid.getCell(nextX,nextY).getCost();

        //Check termination point
        if(grid.getCell(nextX,nextY).isTerminal()){
            this.flag = true;
        }
        return true;

    }

    /**
     * Function created for visualization purposes in the Drawing. It demanded a 1d array with all the steps required
     * to reach the final goal(Only the best path)
     * @return
     */
    public int[] getStepsMatrix() {

        Nodes nodeback = visitedList.getLast();

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
     * to reach the final goal(All visited nodes). Mode parameter should be 1 if we wish to visualize the grid. In case
     * we want only to get total cost assign to zero
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
