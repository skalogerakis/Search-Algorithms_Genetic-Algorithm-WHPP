package MainPackage;

import utils.*;

import java.util.ArrayList;
import java.util.Arrays;

public class MainClass {

    //TODO when done with everything else and need plotting
    // https://github.com/knowm/XChart(Jar already included)

    final static int x_Axis_Days = 14;
    final static int y_Axis_Employees = 30;
    final static int population = 5000;

    public static void main(String args[]) {

        Population mypop = new Population();
        //Generate population in pseudorandom fashion
        ArrayList<int[][]> completePopulation = mypop.population_generator(population,x_Axis_Days,y_Axis_Employees);

        //System.out.println(completePopulation.size());

        Constraints myConstr = new Constraints(x_Axis_Days,y_Axis_Employees, completePopulation);

        ArrayList<int[][]> modifiedpopulation = myConstr.feasibility();
        int[] score = myConstr.fitness();
        System.out.println("SIZEEE "+modifiedpopulation.size());

        Selection newSelection = new Selection();



        int parent1;
        int parent2;
        do{
            parent1 = newSelection.rouletteWheelSelection(modifiedpopulation,score);
            System.out.println("Parent1 "+parent1);
            parent2 = newSelection.rouletteWheelSelection(modifiedpopulation,score);
            System.out.println("Parent2 "+parent2);
        }while (parent1 == parent2);

        Crossover crossover = new Crossover(modifiedpopulation.get(parent1),modifiedpopulation.get(parent2),x_Axis_Days,y_Axis_Employees);

        //int[][] crossChild = crossover.singlePointCross();

        int[][] crossChild = crossover.multiplePointCrossover();


        System.out.println("\n\nChosen child");
        System.out.println(Arrays.deepToString(crossChild).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

        Mutation mutation = new Mutation(crossChild, x_Axis_Days, y_Axis_Employees);
//        mutation.stringSwapping();
        mutation.twoPointSwapping();
        //TODO CHECK THAT parent1 and paretn2 are not -1

    }


}
