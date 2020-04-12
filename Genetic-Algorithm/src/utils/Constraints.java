package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Constraints {

    /**
     * Hard Constraints as defined from assignment
     */
    static int[][] hardConstraints = {
            {10, 10, 5, 5, 5, 5, 5, 10, 10, 5, 5, 5, 5, 5},
            {10, 10, 10, 5, 10, 5, 5, 10, 10, 10, 5, 10, 5, 5},
            {5, 5, 5, 5, 5, 5, 5, 10, 10, 10, 5, 10, 5, 5}
    };



    int x_Days;
    int y_Employees;
    ArrayList<int[][]> population;

    public Constraints(int x_Days, int y_Employees, ArrayList<int[][]> population){
        this.x_Days = x_Days;
        this.y_Employees = y_Employees;
        this.population = population;
    }

    public void constraintChecker(){
        feasibility();
    }

    private void feasibility(){
        int totalOneCounter=0;
        int totalTwoCounter=0;
        int totalThreeCounter=0;
        int itercounter=0;
        boolean flag =false;
//        for(int[][] pop : population){
        System.out.println("Init size "+this.population.size());

        for (int k = 0; k < this.population.size();k++){
            int[][] pop = this.population.get(k);

            //System.out.println(itercounter++);
            for(int i=0; i < this.x_Days; i++){
                for(int j=0; j< this.y_Employees; j++){

                    if(pop[i][j] == 1){
                        totalOneCounter++;
                    }else if(pop[i][j] == 2){
                        totalTwoCounter++;
                    }else if(pop[i][j] == 3){
                        totalThreeCounter++;
                    }

                }
                //Check if hard constraints are met. In the case above where there are not pop the element from the list. Leave only the
                //ones we can use
                //if(hardConstraints[0][i] != totalOneCounter || hardConstraints[1][i] != totalTwoCounter || hardConstraints[2][i] != totalThreeCounter){
//                System.out.println(hardConstraints[0][i]);
//                System.out.println(hardConstraints[1][i]);
//                System.out.println(hardConstraints[2][i]);

                //System.out.println(i);

                //System.out.println();
                if(hardConstraints[0][i] == totalOneCounter && hardConstraints[1][i] == totalTwoCounter && hardConstraints[2][i] == totalThreeCounter){
                    System.out.println("FOUND ONE");
                    itercounter++;
                    continue;
                }else{
                    //this.population.remove(k);
                    this.population.set(k, null);
//                    flag = true;
                    //make counters zero again and go to the next population
                    totalOneCounter=0;
                    totalTwoCounter=0;
                    totalThreeCounter=0;
                    break;
                }

            }
//            if(flag == true){
//                this.population.set(k, null);
//                flag = false;
//            }

        }

        System.out.println("ITER "+itercounter);
        System.out.println(this.population.size());
        this.population.removeIf(Objects::isNull);
        System.out.println("Final size "+this.population.size());

    }


}
