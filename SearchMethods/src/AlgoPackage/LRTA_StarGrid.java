package AlgoPackage;


import MainPackage.*;

import java.util.ArrayList;
import java.util.List;

//http://turing.cs.pub.ro/blia_2003/Real-time_search_1.htm

public class LRTA_StarGrid {

    private Nodes[][] searchArea;
    private Nodes initialNode;
    private Nodes finalNode;
    private Grid grid;

    static class Nodes {

        private int x;
        private int y;
        private boolean visited;
        private int path_till_cost;
        private Nodes parent;
        private int f = 0;
        private int g;
        private int h;
        private boolean isStarting;
        private boolean isTerminal;

        public Nodes(int posx,int posy){

            this.x = posx;
            this.y = posy;

        }

        public String toString(){
            return "Position: (" + this.getX() + "," + this.getY() + ") - " + "Visited: " + this.isVisited() + "\nCost till now: " + getPath_till_cost();
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public boolean isVisited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        public int getPath_till_cost() {
            return path_till_cost;
        }

        public void setPath_till_cost(int path_till_cost) {
            this.path_till_cost = path_till_cost;
        }

        //TODO CHANGE THAT
        public int calculateHeuristic(Nodes finalNode) {  this.h = Math.abs(finalNode.getX() - getX()) + Math.abs(finalNode.getY() - getY());
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

        public int getG() {
            return g;
        }

        public void setG(int g) {
            this.g = g;
        }

        public int getF() {
            return f;
        }

        public void setF(int f) {
            this.f = f;
        }

        public boolean isStarting() {
            return isStarting;
        }

        public void setStarting(boolean starting) {
            isStarting = starting;
        }

        public boolean isTerminal() {
            return isTerminal;
        }

        public void setTerminal(boolean terminal) {
            isTerminal = terminal;
        }
    }

    public LRTA_StarGrid(Grid grid) {
        this.grid = grid;
        this.searchArea = new Nodes[grid.getNumOfRows()][grid.getNumOfColumns()];
        initialNode = new Nodes(grid.getStart()[0],grid.getStart()[1]);
        initialNode.setStarting(true);
        finalNode = new Nodes(grid.getTerminal()[0],grid.getTerminal()[1]);
        finalNode.setTerminal(true);

    }

    private void nodeInitializer() {

        for (int i = 0; i < this.grid.getNumOfRows(); i++) {
            for (int j = 0; j < this.grid.getNumOfColumns(); j++) {


                if(!this.grid.getCell(i,j).isWall()){
                    Nodes node = new Nodes(i,j);
                    node.calculateHeuristic(getFinalNode());
                    this.searchArea[i][j] = node;
                }

            }
        }
    }


    public int[] LRTA_StarSearch() {

        nodeInitializer();

        int[] returnValue = {0,-1};
        Nodes currentNode = initialNode;
        int counter=0;

        while(currentNode != null){

            if (this.grid.getCell(currentNode.getX(),currentNode.getY()).isTerminal()){
                returnValue[0] = 1;
                returnValue[1] = currentNode.getPath_till_cost();
                return returnValue;
            }


            int minFValue = Integer.MAX_VALUE;
            List<Nodes> adjacentNodes = getValidAdjacents(currentNode);
            Nodes nextNode = null;
            for (Nodes n : adjacentNodes) {
                int fValue = getLRTACost(n);
                n.setVisited(true);
                if (fValue < minFValue){
                    minFValue = fValue;
                    nextNode = n;
                }
            }
            this.searchArea[currentNode.getX()][currentNode.getY()].setH(minFValue);

            int cost = this.grid.getCell(nextNode.getX(),nextNode.getY()).isGrass() ? 2 : 1;

            nextNode.setPath_till_cost(currentNode.getPath_till_cost() + cost);

            currentNode = nextNode;
            counter++;
        }
        return returnValue;
    }

    private List<Nodes> getValidAdjacents(Nodes node) {

        List<Nodes> neighbours = new ArrayList<>();

        if(isValid(node.getX(),node.getY()-1)){
            neighbours.add(this.searchArea[node.getX()][node.getY() - 1]);
        }


        if(isValid(node.getX()-1,node.getY())){
            neighbours.add(this.searchArea[node.getX() - 1][node.getY()]);
        }

        if(isValid(node.getX(), node.getY() + 1)){
            neighbours.add(this.searchArea[node.getX()][node.getY() + 1]);
        }

        if(isValid(node.getX()+1,node.getY())){
            neighbours.add(this.searchArea[node.getX() + 1][node.getY()]);
        }
        return neighbours;

    }

    private int getLRTACost(Nodes candNext){
        int fValue;
        int kValue;


        int hValue = this.searchArea[candNext.getX()][candNext.getY()].getH();

        kValue = this.grid.getCell(candNext.getX(),candNext.getY()).isGrass() ? 2 : 1;
        fValue = hValue + kValue;

        return fValue;
    }

    public Nodes getFinalNode() { return finalNode; }


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

}
