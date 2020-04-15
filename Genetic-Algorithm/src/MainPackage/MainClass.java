package MainPackage;

import utils.*;

import java.util.ArrayList;
import java.util.Arrays;

public class MainClass {

    //TODO when done with everything else and need plotting
    // https://github.com/knowm/XChart(Jar already included)

    final static int x_Axis_Days = 14;
    final static int y_Axis_Employees = 30;
    static int population = 5000;

    public static void main(String args[]) {

        Population mypop = new Population();
        //Generate population in pseudorandom fashion
        ArrayList<int[][]> completePopulation = mypop.population_generator(population,x_Axis_Days,y_Axis_Employees);

        //System.out.println(completePopulation.size());

        Constraints myConstr = new Constraints(x_Axis_Days,y_Axis_Employees, completePopulation);

        ArrayList<int[][]> modpopulation = myConstr.feasibility();
        int[] score = myConstr.fitness();
        System.out.println("SIZEEE "+modpopulation.size());



        //CHECHED FITNESS FEASIBILITY FOUND BEST AND AVG NOW PROCEED NEW GENERATION


        for(int s = 0; s < 11 ; s++){
            ArrayList<int[][]> newpopulation = new ArrayList<>();
            for(int i = 0; i < population/2; i++){

                //APPLY EVERYTHING NO POSSIBILITIES

                Selection newSelection = new Selection();

                int parent1;
                int parent2;
                do{
                    parent1 = newSelection.rouletteWheelSelectionF(modpopulation,score);
                    //System.out.println("Parent1 "+parent1);
                    parent2 = newSelection.rouletteWheelSelectionF(modpopulation,score);
                    //System.out.println("Parent2 "+parent2);
                }while (parent1 == parent2);

                Crossover crossover = new Crossover(modpopulation.get(parent1),modpopulation.get(parent2),x_Axis_Days,y_Axis_Employees);

                int[][] child = crossover.singlePointCross();

//            System.out.println("AFTER "+modpopulation.size());
                //
                //        int[][] crossChild = crossover.multiplePointCrossover();
                //
                //
                //        System.out.println("\n\nChosen child");
                //        System.out.println(Arrays.deepToString(crossChild).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
                //
                Mutation mutation = new Mutation(child, x_Axis_Days, y_Axis_Employees);
                //mutation.stringSwapping();
                //child = mutation.twoPointSwapping();

                newpopulation.add(child);
                //newpopulation.add(i, child);

            }

            System.out.println("NEW POP "+newpopulation.size());
            Constraints newConstr = new Constraints(x_Axis_Days,y_Axis_Employees, newpopulation);

            ArrayList<int[][]> mpopulation = newConstr.feasibility();
            int[] scores = newConstr.fitness();
            System.out.println("SIZEEE "+mpopulation.size());
            System.out.println("\n\n");
            population = mpopulation.size();
            modpopulation = mpopulation;
        }
//        ArrayList<int[][]> newpopulation = new ArrayList<>();
//        for(int i = 0; i < population/2; i++){
//
//            //APPLY EVERYTHING NO POSSIBILITIES
//
//            Selection newSelection = new Selection();
//
//            int parent1;
//            int parent2;
//            do{
//                parent1 = newSelection.rouletteWheelSelectionF(modpopulation,score);
//                //System.out.println("Parent1 "+parent1);
//                parent2 = newSelection.rouletteWheelSelectionF(modpopulation,score);
//                //System.out.println("Parent2 "+parent2);
//            }while (parent1 == parent2);
//
//            Crossover crossover = new Crossover(modpopulation.get(parent1),modpopulation.get(parent2),x_Axis_Days,y_Axis_Employees);
//
//            int[][] child = crossover.singlePointCross();
//
////            System.out.println("AFTER "+modpopulation.size());
//    //
//    //        int[][] crossChild = crossover.multiplePointCrossover();
//    //
//    //
//    //        System.out.println("\n\nChosen child");
//    //        System.out.println(Arrays.deepToString(crossChild).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
//    //
//            Mutation mutation = new Mutation(child, x_Axis_Days, y_Axis_Employees);
//            //mutation.stringSwapping();
//            //child = mutation.twoPointSwapping();
//
//            newpopulation.add(child);
//            //newpopulation.add(i, child);
//
//        }
//
//        System.out.println("NEW POP "+newpopulation.size());
//        Constraints newConstr = new Constraints(x_Axis_Days,y_Axis_Employees, newpopulation);
//
//        ArrayList<int[][]> mpopulation = newConstr.feasibility();
//        int[] scores = newConstr.fitness();
//        System.out.println("SIZEEE "+mpopulation.size());
//        System.out.println("\n\n");
//        population = mpopulation.size();
//        modpopulation = mpopulation;

//        Selection newSelection = new Selection();



//        int parent1;
//        int parent2;
//        do{
//            parent1 = newSelection.rouletteWheelSelectionF(modifiedpopulation,score);
//            System.out.println("Parent1 "+parent1);
//            parent2 = newSelection.rouletteWheelSelectionF(modifiedpopulation,score);
//            System.out.println("Parent2 "+parent2);
//        }while (parent1 == parent2);
//
////        Crossover crossover = new Crossover(modifiedpopulation.get(parent1),modifiedpopulation.get(parent2),x_Axis_Days,y_Axis_Employees);
////
////        //int[][] crossChild = crossover.singlePointCross();
////
////        int[][] crossChild = crossover.multiplePointCrossover();
////
////
////        System.out.println("\n\nChosen child");
////        System.out.println(Arrays.deepToString(crossChild).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
////
////        Mutation mutation = new Mutation(crossChild, x_Axis_Days, y_Axis_Employees);
//////        mutation.stringSwapping();
////        mutation.twoPointSwapping();
        //TODO CHECK THAT parent1 and paretn2 are not -1

    }


}
