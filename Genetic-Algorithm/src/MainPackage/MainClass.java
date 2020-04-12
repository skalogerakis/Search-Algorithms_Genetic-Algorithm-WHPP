package MainPackage;

import utils.Constraints;
import utils.Population;

import java.util.ArrayList;

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

        myConstr.constraintChecker();
    }

}
