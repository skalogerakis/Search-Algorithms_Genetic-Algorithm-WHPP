//package AlgoPackage;
//
//import MainPackage.*;
//
//import java.util.HashSet;
//import java.util.PriorityQueue;
//import java.util.Set;
//
////Based on pseudocode
////https://gist.github.com/damienstanton/7de65065bf584a43f96a
//public class A_StarGrid {
//
//    private Nodes[][] searchArea;
//    private PriorityQueue<Nodes> openList;
//    private Set<Nodes> closedSet;
//    private Nodes initialNode;
//    private Nodes finalNode;
//    private Grid grid;
//
//    //TODO almost done here just get rid of some unused variables
//    static class Nodes implements Comparable<Nodes>{
//
//        private int x;
//        private int y;
//        private boolean visited;
//        private int path_till_cost;
//        private Nodes parent;
//        private int f = 0;
//        private int g;
//        private int h;
//
//        public Nodes(int posx,int posy){
//
//            this.x = posx;
//            this.y = posy;
//
//        }
//
//        public String toString(){
//            return "Position: (" + this.getX() + "," + this.getY() + ") - " + "Visited: " + this.isVisited() + "\nCost till now: " + getPath_till_cost();
//        }
//
//        public int getX() {
//            return x;
//        }
//
//        public int getY() {
//            return y;
//        }
//
//        public boolean isVisited() {
//            return visited;
//        }
//
//        public void setVisited(boolean visited) {
//            this.visited = visited;
//        }
//
//        public int getPath_till_cost() {
//            return path_till_cost;
//        }
//
//        public void setPath_till_cost(int path_till_cost) {
//            this.path_till_cost = path_till_cost;
//        }
//
//        //TODO CHANGE THAT
//        public int calculateHeuristic(Nodes finalNode) {
//            this.h = Math.abs(finalNode.getX() - getX()) + Math.abs(finalNode.getY() - getY());
//            return this.h;
//        }
//
//        public boolean checkBetterPath(Nodes currentNode, int cost) {
//            int gCost = currentNode.getG() + cost;
//            if (gCost < getG()) {
//                setParent(currentNode);
//                setG(gCost);
//                calculateFinalCost();
//                return true;
//            }
//            return false;
//        }
//
//        private void calculateFinalCost() {
//            int finalCost = getG() + getH();
//            setF(finalCost);
//        }
//
//        public Nodes getParent() {
//            return parent;
//        }
//
//        public void setParent(Nodes parent) {
//            this.parent = parent;
//        }
//
//        public int getH() {
//            return h;
//        }
//
//        public void setH(int h) {
//            this.h = h;
//        }
//
//        public int getG() {
//            return g;
//        }
//
//        public void setG(int g) {
//            this.g = g;
//        }
//
//        public int getF() {
//            return f;
//        }
//
//        public void setF(int f) {
//            this.f = f;
//        }
//
//        //Make nodes comparable for our priority queue
//        @Override
//        public int compareTo(Nodes o) {
//            if(this.getF() > o.getF()) {
//                return 1;
//            } else if (this.getF() < o.getF()) {
//                return -1;
//            } else {
//                return 0;
//            }
//        }
//    }
//
//
//    public A_StarGrid(Grid grid) {
//
//        this.grid = grid;
//        this.searchArea = new Nodes[this.grid.getNumOfRows()][this.grid.getNumOfColumns()];
//        initialNode = new Nodes(grid.getStart()[0],grid.getStart()[1]);
//        finalNode = new Nodes(grid.getTerminal()[0],grid.getTerminal()[1]);
//
//        //setNodes();
//        this.closedSet = new HashSet<>();
//    }
//
//    public int[] A_StarSearch() {
//
//        //todo add here node initializer
//        nodeInitializer();
//
//        this.openList = new NoDuplicates<Nodes>();
//
//        int[] returnValue = {0,-1};
//        openList.add(initialNode);
//        while (!openList.isEmpty()) {
//
//            Nodes currentNode = openList.poll();
//            //System.out.println("NEW "+currentNode.toString());
//
//            if (this.grid.getCell(currentNode.getX(),currentNode.getY()).isTerminal()) {
//                returnValue[0] = 1;
//                returnValue[1] = currentNode.getF();
//                //todo reconstruct path
//                break;
//            }
//
//            int cost = this.grid.getCell(currentNode.getX(),currentNode.getY()).isGrass() ? this.grid.getGrass_cost() : this.grid.getLand_cost() ;
//
//            if(isValid(currentNode.getX()-1,currentNode.getY())){
//                neighbourCheck(currentNode, currentNode.getY(), currentNode.getX()-1, cost);
//            }
//
//            if(isValid(currentNode.getX(),currentNode.getY()-1)){
//                neighbourCheck(currentNode, currentNode.getY() - 1, currentNode.getX(), cost);
//            }
//
//            if(isValid(currentNode.getX(), currentNode.getY() + 1)){
//                neighbourCheck(currentNode, currentNode.getY() + 1, currentNode.getX(), cost);
//            }
//
//
//            if(isValid(currentNode.getX()+1,currentNode.getY())){
//                neighbourCheck(currentNode, currentNode.getY(), currentNode.getX()+1, cost);
//            }
//
//            closedSet.add(currentNode);
//        }
//
//        return returnValue;
//    }
//
//
//    private void neighbourCheck(Nodes currentNode, int col, int row, int cost) {
//        Nodes adjacentNode = getSearchArea()[row][col];
//
//        //if neighbour in closed set
//        if(getClosedSet().contains(adjacentNode)) return;
//
//
//
//
//        if (!getOpenList().contains(adjacentNode) || adjacentNode.checkBetterPath(currentNode, cost)) {
//
//            adjacentNode.setParent(currentNode);
//            adjacentNode.setG(currentNode.getG() + cost);
//            adjacentNode.calculateFinalCost();
//
//            getOpenList().add(adjacentNode);
////            if (!getOpenList().contains(adjacentNode)) {
////                getOpenList().add(adjacentNode);
////            } else {
////                getOpenList().remove(adjacentNode);
////                getOpenList().add(adjacentNode);
////            }
//        }
//
//    }
//
//    public Nodes getFinalNode() {
//        return finalNode;
//    }
//
//    public Nodes[][] getSearchArea() {
//        return searchArea;
//    }
//
//    public PriorityQueue<Nodes> getOpenList() {
//        return this.openList;
//    }
//
//    public Set<Nodes> getClosedSet() {
//        return closedSet;
//    }
//
//    private boolean isValid(int nextX, int nextY){
//        try {
//            if(this.grid.getCell(nextX,nextY).isWall() || nextX < 0 || nextX >= this.grid.getNumOfRows() || nextY < 0 || nextY >= this.grid.getNumOfColumns() ){
//                return false;
//            }
//
//        }catch (ArrayIndexOutOfBoundsException ao){
//            return false;
//        }
//
//        return true;
//
//    }
//
//    public void nodeInitializer(){
//
//        for (int i = 0; i < this.grid.getNumOfRows(); i++) {
//            for (int j = 0; j < this.grid.getNumOfColumns(); j++) {
//                //Don't calculate heuristic values for walls(cannot be visited)
//                if(!this.grid.getCell(i,j).isWall()){
//                    Nodes node = new Nodes(i,j);
//                    node.calculateHeuristic(getFinalNode());
//                    this.searchArea[i][j] = node;
//                }
//            }
//        }
//
//    }
//
//    //Eliminate duplicates. NOTE: In final version there should not be any duplicates
//    class NoDuplicates<E> extends PriorityQueue<E> {
//
//        /**
//         * Modified class to meet our needs. What this does, is that in case the element we want
//         * does not exist in our class add it, or otherwise re-shuffle priority queue to poll the
//         * element we need
//         */
//
//        public boolean add(E e) {
//
//            boolean isAdded = false;
//            if (!super.contains(e)) {
//                isAdded = super.add(e);
//            }
//            super.remove(e);
//            super.add(e);
//            return isAdded;
//        }
//
//    }
//
//}