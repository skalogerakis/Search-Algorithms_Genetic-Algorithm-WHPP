package AlgoPackage;

import MainPackage.*;

import java.util.LinkedList;


public class BFSGrid {

    private int[] stepsMatrix;
    private Grid grid;
    private int curCost;
    private boolean flag=false;
    private int[] returnValue = {0,-1};

    LinkedList <Nodes> visited = new LinkedList<>();


    static class Nodes {

        int curX;
        int curY;
        private boolean visited;
        private int path_till_cost;
        private Nodes parent;

        public Nodes(int curX,int curY,boolean visited,int path_till_cost){

            this.curX = curX;
            this.curY = curY;
            this.visited = visited;
            this.path_till_cost = path_till_cost;
        }

        public String toString(){
            return "Position: (" + this.curX + "," + this.curX + ") - " + "Visited: " + this.visited + "\nCost till now: " + getPath_till_cost();
        }

        public int getPath_till_cost() {
            return path_till_cost;
        }

        public Nodes getParent() {
            return parent;
        }

        public void setParent(Nodes parent) {
            this.parent = parent;
        }


    }




    public BFSGrid(Grid grid){
        this.grid = grid;
        this.stepsMatrix = new int[(this.grid.getNumOfColumns()+1)*(this.grid.getNumOfRows()+1)];
    }


    public int[] BFSearch(){

        LinkedList <Nodes> queue = new LinkedList<Nodes>();

        Nodes start = new Nodes(grid.getStart()[0],grid.getStart()[1],false,1);

        queue.add(start);
        this.visited.add(start);
        grid.getCell(start.curX,start.curY).setVisited(true);

        /**
         * Added some extra functionality by including flag variable.
         * Flag is triggered when we find that the next node to be generated
         * will be the termination point. In that way we do not expand all the nodes for a level
         * but as soon as we reach our goal the computations stop.(Less sources used)
         */
        while(!queue.isEmpty() && !this.flag){
            Nodes curNode = queue.poll();



            if(isValid(curNode.curX-1,curNode.curY)){
                grid.getCell(curNode.curX-1,curNode.curY).setVisited(true);
                Nodes temp = new Nodes(curNode.curX-1,curNode.curY,true,curNode.getPath_till_cost() + this.curCost);
                //System.out.println("NEW "+temp.toString());
                queue.add(temp);
                temp.setParent(curNode);

                if(flag){
                    this.visited.addLast(curNode);
                    returnValue[1] = curNode.getPath_till_cost()+1;
                    continue;
                }else {
                    this.visited.add(curNode);
                }
            }

            if(isValid(curNode.curX,curNode.curY-1)){
                grid.getCell(curNode.curX,curNode.curY-1).setVisited(true);
                //this.visited.add(curNode);
                Nodes temp = new Nodes(curNode.curX,curNode.curY-1,true,curNode.getPath_till_cost() + this.curCost);
                //System.out.println("NEW "+temp.toString());
                queue.add(temp);
                temp.setParent(curNode);

                if(flag){
                    this.visited.addLast(curNode);
                    returnValue[1] = curNode.getPath_till_cost()+1;
                    continue;
                }else {
                    this.visited.add(curNode);
                }

            }



            if(isValid(curNode.curX,curNode.curY+1)){
                grid.getCell(curNode.curX,curNode.curY+1).setVisited(true);
                Nodes temp = new Nodes(curNode.curX,curNode.curY+1,true,curNode.getPath_till_cost() + this.curCost);
                //System.out.println("NEW "+temp.toString());
                queue.add(temp);
                temp.setParent(curNode);

                if(flag){
                    this.visited.addLast(curNode);
                    returnValue[1] = curNode.getPath_till_cost()+1;
                    continue;
                }else {
                    this.visited.add(curNode);
                }
            }



            if(isValid(curNode.curX+1,curNode.curY)){
                grid.getCell(curNode.curX+1,curNode.curY).setVisited(true);

                Nodes temp = new Nodes(curNode.curX+1,curNode.curY,true,curNode.getPath_till_cost() + this.curCost);
                //System.out.println("NEW "+temp.toString());
                queue.add(temp);
                temp.setParent(curNode);

                if(flag){
                    this.visited.addLast(curNode);
                    returnValue[1] = curNode.getPath_till_cost()+1;
                    continue;
                }else {
                    this.visited.add(curNode);
                }
            }

        }
        return returnValue;
    }


    private boolean isValid(int nextX, int nextY){
        try {
            if(this.grid.getCell(nextX,nextY).isWall() || this.grid.getCell(nextX,nextY).isVisited() || nextX < 0 || nextX >= this.grid.getNumOfRows() || nextY < 0 || nextY >= this.grid.getNumOfColumns() ){
                this.curCost = -1;
                return false;
            }

        }catch (ArrayIndexOutOfBoundsException ao){
            this.curCost = -1;
            return false;
        }
        //Check if we have grass or land in that node and assign values respectively
        this.curCost = this.grid.getCell(nextX,nextY).isGrass() ? this.grid.getGrass_cost() : this.grid.getLand_cost() ;

        //Check termination point
        if(grid.getCell(nextX,nextY).isTerminal()){
            this.flag = true;
            returnValue[0] = 1;
        }
        return true;

    }

    /**
     * Function created for visualization purposes in the Drawing. It demanded a 1d array with all the steps required
     * to reach the final goal
     * @return
     */
    public int[] getStepsMatrix() {

        Nodes nodeback = visited.getLast();

        int counter = 0;

        while (nodeback != null) {
            stepsMatrix[counter] = nodeback.curX * this.grid.getNumOfColumns() + nodeback.curY;
            counter++;

            for (int i = 0; i < visited.size(); i++) {
                try {
                    if (nodeback.getParent().curX == visited.get(i).curX && nodeback.getParent().curY == visited.get(i).curY) {
                        nodeback = visited.get(i);
                    }
                } catch (NullPointerException io) {
                    nodeback = null;
                    break;
                }

            }

        }
        return stepsMatrix;
    }

}
