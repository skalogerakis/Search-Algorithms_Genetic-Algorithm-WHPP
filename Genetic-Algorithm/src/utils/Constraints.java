package utils;

import java.util.*;

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

    /*
    This is the function called by main class. Both feasibility and fitness are implemented here
     */
    public ArrayList<Statistics> constraintChecker(){
        feasibility();
        return fitness();
    }


    /*
    Feasibility is the first test for each chromosome to pass. All constraints must pass in order
    to leave this chromosome as candidate. In case that it is invalid, make the chromosome null.
    In the end all null elements are deleted from our list
     */
    private ArrayList<int[][]> feasibility(){
        int totalOneCounter=0;
        int totalTwoCounter=0;
        int totalThreeCounter=0;
        int itercounter=0;

        for (int k = 0; k < this.population.size();k++){
            int[][] pop = this.population.get(k);

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

                if(hardConstraints[0][i] == totalOneCounter && hardConstraints[1][i] == totalTwoCounter && hardConstraints[2][i] == totalThreeCounter){
                    //System.out.println("FOUND ONE");
                    itercounter++;
                    totalOneCounter=0;
                    totalTwoCounter=0;
                    totalThreeCounter=0;
                    continue;
                }else{
                    //set invalid population to null
                    this.population.set(k, null);
                    //make counters zero again and go to the next population
                    totalOneCounter=0;
                    totalTwoCounter=0;
                    totalThreeCounter=0;
                    break;
                }

            }

        }

        //remove invalid population
        this.population.removeIf(Objects::isNull);
        return population;

    }

    /*
    Fitness function is the next check that each chromosome passes. We now check for
    soft constraints and update score table for each chromosome.
    UPDATE: The soft constraint can apply more than once in each chromosome, so that
    we can see the improvement in the end in scores.
     */
    private ArrayList<Statistics> fitness(){

        ArrayList<Statistics> statList = new ArrayList<Statistics>();

        for(int i=0; i < this.population.size(); i++){

            int[][] pop = this.population.get(i);
            Statistics mystats = new Statistics(i, 0, pop);


            int hoursWork;
            int consecutiveDays;
            int consecutiveNights;
            boolean prevNight_nextMor;
            boolean prevNoon_nextMor;
            boolean prevNight_nextNoon;
            int nightShifts;
            int daysoff;
            int daysworkcounter;
            int consecutiveWeekends;
            int offWorkoff;
            int WorkoffWork;

            for(int j=0;j<this.y_Employees;j++){
                hoursWork = 0;
                consecutiveDays = 0;
                consecutiveNights = 0;
                prevNight_nextMor=false;
                prevNoon_nextMor=false;
                prevNight_nextNoon = false;
                nightShifts = 0;
                daysoff=0;
                daysworkcounter=0;
                consecutiveWeekends=0;
                offWorkoff=2;
                WorkoffWork=2;

                for(int k=0;k<this.x_Days;k++){
                    if(pop[k][j] == 1){
                        hoursWork += 8;
                        consecutiveDays++;
                        consecutiveNights=0;
                        //In case shift night and next morning
                        if(prevNight_nextMor){
                            mystats.score += 1000;
                            prevNight_nextMor = false;
                        }
                        //In case shift noon and next morning
                        if(prevNoon_nextMor){
                            mystats.score += 800;
                            prevNoon_nextMor = false;
                        }

                        prevNight_nextNoon = false;
                        daysworkcounter++;
                        offWorkoff++;
                        if(WorkoffWork == 1){
                            mystats.score += 1;
                        }
                        WorkoffWork=0;
                    }else if(pop[k][j] == 2){
                        hoursWork += 8;
                        consecutiveDays++;
                        consecutiveNights=0;
                        prevNight_nextMor = false;
                        prevNoon_nextMor = true;
                        if(prevNight_nextNoon){
                            mystats.score += 800;
                            prevNight_nextNoon = false;
                        }
                        daysworkcounter++;
                        offWorkoff++;
                        if(WorkoffWork == 1){
                            mystats.score += 1;
                        }
                        WorkoffWork=0;
                    }else if(pop[k][j] == 3){
                        hoursWork += 10;
                        consecutiveDays++;
                        consecutiveNights++;
                        prevNight_nextMor = true;
                        prevNoon_nextMor = false;
                        prevNight_nextNoon = true;
                        nightShifts++;
                        daysworkcounter++;
                        offWorkoff++;
                        if(WorkoffWork == 1){
                            mystats.score += 1;
                        }
                        WorkoffWork=0;
                    }else{
                        consecutiveDays=0;
                        consecutiveNights=0;
                        prevNight_nextMor = false;
                        prevNoon_nextMor = false;
                        prevNight_nextNoon = false;
                        daysoff++;
                        //case employee works day off then works and then again day off
                        if(offWorkoff == 1){
                            mystats.score += 1;
                        }
                        offWorkoff = 0;
                        WorkoffWork++;
                    }

                    if((k == (this.x_Days/2 - 2) || k == (this.x_Days/2 - 1) || (k == this.x_Days - 2) || (k == this.x_Days - 1)) && pop[k][j]!=0){
                        consecutiveWeekends++;
                    }

                }
                //case employee works over 70 hours
                if(hoursWork > 70){
                    mystats.score += 1000;
                }

                //case employee works over 7 consecutive days
                if(consecutiveDays > 7){
                    mystats.score += 1000;
                }

                //case employee works over 4 consecutive night shifts
                if(consecutiveNights > 4){
                    mystats.score += 1000;
                }

                //case employee works over 4 night shifts must be assigned with at least two days off
                if(nightShifts >=4 && daysoff<2){
                    mystats.score += 100;
                }

                //case employee works over 7 days must be assigned with at least two days off
                if(daysworkcounter >=7 && daysoff<2){
                    mystats.score += 100;
                }

                //case employee works both weekends(4 days)
                if(consecutiveWeekends == 4){
                    mystats.score += 1;
                }


            }

            statList.add(mystats);
        }


        Collections.sort(statList, Statistics.scoreComparator);

        return statList;

    }


}
