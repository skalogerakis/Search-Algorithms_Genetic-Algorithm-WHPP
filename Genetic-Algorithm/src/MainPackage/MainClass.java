package MainPackage;


import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.demo.charts.ExampleChart;
import utils.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MainClass {



    final static int x_Axis_Days = 14;
    final static int y_Axis_Employees = 30;
    final static int ITERATIONS = 15;
    final static double p_sel = 0.05; // selection probability
    final static double p_cross = 0.15; //crossover probability
    final static double p_mut = 0.15; //mutation probability

    static int population = 5000;


    public static void main(String args[]) {

        //Lists used to store best and average values of each generation
        ArrayList<Double> bestChrom = new ArrayList<Double>();
        ArrayList<Double> avgChrom = new ArrayList<Double>();

        Population mypop = new Population();

        //Generate population in pseudorandom fashion
        ArrayList<int[][]> _initpopulation = mypop.population_generator(population,x_Axis_Days,y_Axis_Employees);

        Constraints constraints = new Constraints(x_Axis_Days,y_Axis_Employees, _initpopulation);

        //From constraint class first check feasibility(hard constraints) and then fitness(soft constraints)
        ArrayList<Statistics> populationData = constraints.constraintChecker();

        //An attempted implementation with not the best results
//        List<Statistics> elitehalf = populationData.subList(0,(int)((populationData.size()/2)*0.1)+1);
//        List<Statistics> secondhalf = populationData.subList((int)((populationData.size()/2)*0.1)+1,populationData.size());



        bestChrom.add((double)Collections.min(populationData, Statistics.scoreComparator).getScore());
        avgChrom.add(populationData.stream().mapToDouble(val -> val.getScore()).average().orElse(0.0));
//        System.out.println("Population size "+populationData.size());
//        System.out.println("\n\n");


        for(int s = 0; s < ITERATIONS ; s++){
            ArrayList<int[][]> nextgenpop = new ArrayList<>();
            //ArrayList<int[][]> halfpopulation = new ArrayList<>();
//            ArrayList<Statistics> halfpopulationData = new ArrayList<>();
            //System.out.println(s);

            for(int i = 0; i < populationData.size(); i++){

                double p_sel_roll = new Random().nextDouble();//0.05;
                double p_cross_roll = new Random().nextDouble(); //0.15;
                double p_mut_roll = new Random().nextDouble();//0.15;
                
                int parent1= -1;
                int parent2= -1;

                if(i < (populationData.size()/2)*0.1){
                    //Statistics dataStats = elitehalf.get(i);
                    Statistics dataStats = populationData.get(i);
                    nextgenpop.add(dataStats.getPopulation());
                    continue;
//                }else {
                    //halfpopulation.add(dataStats.getPopulation());
                    //halfpopulationData.add(dataStats);

                }

                //In case the selection does not proceed, simply add a new random VALID population
                if(p_sel_roll < p_sel){
                    nextgenpop.add(new Population().randomValidPopulation(x_Axis_Days,y_Axis_Employees));
                    continue;
                }
                Selection newSelection = new Selection();


                do{
                    parent1 = newSelection.rouletteWheelSelectionF(populationData);
                    parent2 = newSelection.rouletteWheelSelectionF(populationData);

                }while (parent1 == parent2);

//                    Statistics par1 = secondhalf.get(parent1);
//                    Statistics par2 = secondhalf.get(parent2);
                int[][] child = populationData.get(parent1).getPopulation();

                //TODO maybe add a check here about parent values
                /**
                 * Checked what is better when crossover does not happen(whether to generate new random or simply use a parent)
                 * And it was much better to simply pass the parent in the next generation
                 */


                if(p_cross_roll > p_cross){
                    Statistics par1 = populationData.get(parent1);
                    Statistics par2 = populationData.get(parent2);
                    Crossover crossover = new Crossover(par1.getPopulation(),par2.getPopulation(),x_Axis_Days,y_Axis_Employees);

                    child = crossover.singlePointCross();

                    if(p_mut_roll > p_mut){
                        Mutation mutation = new Mutation(child, x_Axis_Days, y_Axis_Employees);
                        child = mutation.twoPointSwappingSameRow();
//                        child = mutation.InverseDays();
                    }

                }
                nextgenpop.add(child);

            }

            //System.out.println("Population size "+nextgenpop.size());
            Constraints newConstr = new Constraints(x_Axis_Days,y_Axis_Employees, nextgenpop);
            if(nextgenpop.size() <= 1) break;


            populationData = newConstr.constraintChecker();
//            elitehalf = populationData.subList(0,(int)((populationData.size()/2)*0.1)+1);
//            secondhalf = populationData.subList((int)((populationData.size()/2)*0.1)+1,populationData.size());
            //Add the best and the average score of each generation in our array
            bestChrom.add((double)Collections.min(populationData, Statistics.scoreComparator).getScore());
            avgChrom.add(populationData.stream().mapToDouble(val -> val.getScore()).average().orElse(0.0));


            System.out.println("-----------Generation completed "+(s+1)+" -------------");
            System.out.println("Population size "+populationData.size());
            System.out.println("\n\n");
        }

        System.out.println("Best and average score per generation");
        for(int g = 0; g < bestChrom.size(); g++){
            System.out.println("Generation :"+ g + ", Best score: "+bestChrom.get(g)+", Average score: "+avgChrom.get(g));
        }

        // Plotting usign XChart https://github.com/knowm/XChart(Jar already included)
        //Show my chart
        ExampleChart<XYChart> myChart = new Chart(bestChrom, "Best");
        XYChart chart = myChart.getChart();
        new SwingWrapper<XYChart>(chart).displayChart();

        ExampleChart<XYChart> myChart2 = new Chart(avgChrom, "Average");
        XYChart chart2 = myChart2.getChart();
        new SwingWrapper<XYChart>(chart2).displayChart();


    }




}
