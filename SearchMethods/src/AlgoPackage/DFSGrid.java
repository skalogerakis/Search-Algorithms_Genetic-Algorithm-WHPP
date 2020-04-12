package AlgoPackage;

import MainPackage.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//Based on pseudocode DFS iterative in that page
//https://www.hackerearth.com/practice/algorithms/graphs/depth-first-search/tutorial/

public class DFSGrid {
    private static final int RIGHT = 1;
    private static final int LEFT = -1;
    private static final int UP = -1;
    private static final int DOWN = 1;
    private boolean flag = false;
    private int[] returnValue = {0,-1};
    private Grid grid;

    public DFSGrid(Grid grid){
        this.grid = grid;
    }

    public int[] DFSearch(){


        LinkedList<Node> stack = new LinkedList<>();

        //We start from -1 cost as the first iteration will add 1 to the starting point.
        //To get exact result we 'omit' the first iteration that way
        Node start = new Node(grid.getStart()[0],grid.getStart()[1],false,-1);

        stack.add(start);
        start.setVisited(true);
        grid.getCell(start.getX(),start.getY()).setVisited(true);

        while (!stack.isEmpty()){
            Node current = stack.poll();

            //System.out.println(current.toString());

            if(grid.getCell(current.getX(),current.getY()).isTerminal()) {
                returnValue[0] = 1;
                returnValue[1] = current.getPath_till_cost() + 1;
                return returnValue;
            }

            current.setVisited(true);
            grid.getCell(current.getX(),current.getY()).setVisited(true);

            //choose right cost depending on the terrain
            int curCost = this.grid.getCell(current.getX(),current.getY()).isGrass() ? this.grid.getGrass_cost() : this.grid.getLand_cost();
            current.setPath_till_cost(current.getPath_till_cost() + curCost);

            //TODO MAYBE GET RID OF THAT
            List<Node> neighbours = new ArrayList<>();

            if(isValid(current.getX() + LEFT, current.getY())){
                neighbours.add(new Node(current.getX() + LEFT, current.getY(),false,current.getPath_till_cost()));
                stack.push(new Node(current.getX() + LEFT, current.getY(),false,current.getPath_till_cost()));
            }

            if(isValid(current.getX(),current.getY() + DOWN)){
                neighbours.add(new Node(current.getX(), current.getY() + DOWN,false,current.getPath_till_cost()));
                stack.push(new Node(current.getX(), current.getY() + DOWN,false,current.getPath_till_cost()));
            }


            if(isValid(current.getX() + RIGHT,current.getY())){
                neighbours.add(new Node(current.getX() + RIGHT, current.getY(),false,current.getPath_till_cost()));
                stack.push(new Node(current.getX() + RIGHT, current.getY(),false,current.getPath_till_cost()));
            }


            if(isValid(current.getX(),current.getY() + UP)){
                neighbours.add(new Node(current.getX(), current.getY() + UP,false,current.getPath_till_cost()));
                stack.push(new Node(current.getX(), current.getY() + UP,false,current.getPath_till_cost()));
            }


        }


        return returnValue;

    }

    //isValid function is responsible to check if next available node is accessible(in a similar fashion to BFS)
    private boolean isValid(int nextX, int nextY){
        try {
            if(this.grid.getCell(nextX,nextY).isWall() || this.grid.getCell(nextX,nextY).isVisited() || nextX < 0 || nextX >= this.grid.getNumOfRows() || nextY < 0 || nextY >= this.grid.getNumOfColumns() ){
                return false;
            }

        }catch (ArrayIndexOutOfBoundsException ao){
            return false;
        }

        return true;

    }


}
