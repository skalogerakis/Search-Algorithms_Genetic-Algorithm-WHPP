package MainPackage;


import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.demo.charts.ExampleChart;
import utils.*;

import java.util.ArrayList;
import java.util.Collections;

public class MainClass {

    //TODO when done with everything else and need plotting
    // https://github.com/knowm/XChart(Jar already included)

    final static int x_Axis_Days = 14;
    final static int y_Axis_Employees = 30;
    final static int ITERATIONS = 15;

    static int population = 5000;


    public static void main(String args[]) {

        ArrayList<Double> bestChrom = new ArrayList<Double>();
        ArrayList<Double> avgChrom = new ArrayList<Double>();

        Population mypop = new Population();

        //Generate population in pseudorandom fashion
        ArrayList<int[][]> _initpopulation = mypop.population_generator(population,x_Axis_Days,y_Axis_Employees);

        Constraints constraints = new Constraints(x_Axis_Days,y_Axis_Employees, _initpopulation);

        //From constraint class first check feasibility(hard constraints) and then fitness(soft constraints)
        ArrayList<Statistics> populationData = constraints.constraintChecker();

        bestChrom.add((double)Collections.min(populationData, Statistics.scoreComparator).getScore());
        avgChrom.add(populationData.stream().mapToDouble(val -> val.getScore()).average().orElse(0.0));
        System.out.println("SIZEEE "+populationData.size());


        //CHECHED FITNESS FEASIBILITY FOUND BEST AND AVG NOW PROCEED NEW GENERATION


        for(int s = 0; s < ITERATIONS ; s++){
            ArrayList<int[][]> nextgenpop = new ArrayList<>();
            ArrayList<int[][]> halfpopulation = new ArrayList<>();
            System.out.println(s);
            for(int i = 0; i < populationData.size(); i++){

                //APPLY EVERYTHING NO PROBABILITIES YET
                Statistics dataStats = populationData.get(i);

                if(i < populationData.size()/2){
                    nextgenpop.add(dataStats.getPopulation());
                    continue;
                }else {
                    halfpopulation.add(dataStats.getPopulation());
                }

                Selection newSelection = new Selection();

                int parent1;
                int parent2;
                do{
                    parent1 = newSelection.rouletteWheelSelectionF(halfpopulation,populationData);
                    //System.out.println("Parent1 "+parent1);
                    parent2 = newSelection.rouletteWheelSelectionF(halfpopulation,populationData);
                   // System.out.println("Parent2 "+parent2);
                }while (parent1 == parent2);

                //Crossover implementation
                //Crossover crossover = new Crossover(population.get(parent1),population.get(parent2),x_Axis_Days,y_Axis_Employees);
                Statistics par1 = populationData.get(parent1);
                Statistics par2 = populationData.get(parent2);

                Crossover crossover = new Crossover(par1.getPopulation(),par2.getPopulation(),x_Axis_Days,y_Axis_Employees);

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

                nextgenpop.add(child);

            }

            System.out.println("NEW POP "+nextgenpop.size());
            Constraints newConstr = new Constraints(x_Axis_Days,y_Axis_Employees, nextgenpop);
            if(nextgenpop.size() <= 1) break;


            populationData = newConstr.constraintChecker();
            bestChrom.add((double)Collections.min(populationData, Statistics.scoreComparator).getScore());
            avgChrom.add(populationData.stream().mapToDouble(val -> val.getScore()).average().orElse(0.0));
            System.out.println("SIZEEE "+populationData.size());

            System.out.println("Iteration completed "+s);
            System.out.println("\n\n");
            //populationData = tempScore;
        }

        System.out.println("Best and average score per generation");
        for(int g = 0; g < bestChrom.size(); g++){
            System.out.println("Generation :"+ g + ", Best score: "+bestChrom.get(g)+", Average score: "+avgChrom.get(g));
        }

        //Show my chart
        ExampleChart<XYChart> myChart = new Chart(bestChrom);
        XYChart chart = myChart.getChart();
        new SwingWrapper<XYChart>(chart).displayChart();

        ExampleChart<XYChart> myChart2 = new Chart(avgChrom);
        XYChart chart2 = myChart2.getChart();
        new SwingWrapper<XYChart>(chart2).displayChart();


    }




}
