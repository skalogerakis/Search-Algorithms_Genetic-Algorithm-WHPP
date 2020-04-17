package MainPackage;

import utils.*;

import java.util.ArrayList;
import java.util.Arrays;

public class MainClass {

    //TODO when done with everything else and need plotting
    // https://github.com/knowm/XChart(Jar already included)

    final static int x_Axis_Days = 14;
    final static int y_Axis_Employees = 30;
    final static int ITERATIONS = 15;

    static int population = 5000;

    public static void main(String args[]) {

        Population mypop = new Population();
        //Generate population in pseudorandom fashion
        ArrayList<int[][]> _initpopulation = mypop.population_generator(population,x_Axis_Days,y_Axis_Employees);

        //System.out.println(completePopulation.size());

        Constraints initConstr = new Constraints(x_Axis_Days,y_Axis_Employees, _initpopulation);

        //From constraint class first check feasibility(hard constraints) and then fitness(soft constraints)
        ArrayList<int[][]> population = initConstr.feasibility();

        ArrayList<Stats> stats = initConstr.fitness();

        System.out.println("SIZEEE "+population.size());


        //CHECHED FITNESS FEASIBILITY FOUND BEST AND AVG NOW PROCEED NEW GENERATION


        for(int s = 0; s < ITERATIONS ; s++){
            ArrayList<int[][]> newpopulation = new ArrayList<>();
            ArrayList<int[][]> halfpopulation = new ArrayList<>();
            System.out.println(s);
            for(int i = 0; i < population.size(); i++){

                //APPLY EVERYTHING NO PROBABILITIES YET
                if(i < population.size()/2){
                    Stats ms = stats.get(i);
                    newpopulation.add(ms.getPopulation());
                    continue;
                }else {
                    Stats ms = stats.get(i);
                    halfpopulation.add(ms.getPopulation());
                }

                Selection newSelection = new Selection();

                int parent1;
                int parent2;
                do{
                    parent1 = newSelection.rouletteWheelSelectionF(halfpopulation,stats);
                    //System.out.println("Parent1 "+parent1);
                    parent2 = newSelection.rouletteWheelSelectionF(halfpopulation,stats);
                   // System.out.println("Parent2 "+parent2);
                }while (parent1 == parent2);

                //Crossover implementation
                //Crossover crossover = new Crossover(population.get(parent1),population.get(parent2),x_Axis_Days,y_Axis_Employees);
                Crossover crossover = new Crossover(population.get(parent1),population.get(parent2),x_Axis_Days,y_Axis_Employees);

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
//                child = mutation.stringSwapping();
                child = mutation.InverseDays();
                //child = mutation.twoPointSwapping();

                newpopulation.add(child);

            }

            System.out.println("NEW POP "+newpopulation.size());
            Constraints newConstr = new Constraints(x_Axis_Days,y_Axis_Employees, newpopulation);
            if(newpopulation.size() <= 1) break;

            ArrayList<int[][]> mpopulation = newConstr.feasibility();

            ArrayList<Stats> tempScore = newConstr.fitness();

            System.out.println("SIZEEE "+population.size());


            System.out.println("\n\n");
            stats = tempScore;
            population = mpopulation;
        }

        //TODO CHECK THAT parent1 and paretn2 are not -1

    }


}
