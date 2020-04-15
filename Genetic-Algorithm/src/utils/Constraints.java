package utils;

import java.util.ArrayList;
import java.util.Arrays;
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



    public ArrayList<int[][]> feasibility(){
        int totalOneCounter=0;
        int totalTwoCounter=0;
        int totalThreeCounter=0;
        int itercounter=0;
        //boolean flag =false;
//        for(int[][] pop : population){
        //System.out.println("Init size "+this.population.size());

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

                if(hardConstraints[0][i] == totalOneCounter && hardConstraints[1][i] == totalTwoCounter && hardConstraints[2][i] == totalThreeCounter){
                    //System.out.println("FOUND ONE");
                    itercounter++;
                    totalOneCounter=0;
                    totalTwoCounter=0;
                    totalThreeCounter=0;
                    continue;
                }else{
                    this.population.set(k, null);
                    //make counters zero again and go to the next population
                    totalOneCounter=0;
                    totalTwoCounter=0;
                    totalThreeCounter=0;
                    break;
                }

            }

        }

        //System.out.println("ITER "+itercounter);
        //System.out.println(this.population.size());
        this.population.removeIf(Objects::isNull);
        //System.out.println("Final size "+this.population.size());
        return population;

    }

    public int[] fitness(){

        int[] score = new int[this.population.size()];
        ArrayList<Integer> scoreList = new ArrayList<>();

        for(int i=0; i < this.population.size(); i++){
            int[][] pop = this.population.get(i);
            score[i] = 0;
            scoreList.add(i,0);
//            if(i ==0){
//                System.out.println(Arrays.deepToString(pop).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
//
//            }

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
                            score[i] += 1000;
                            scoreList.set(i, scoreList.get(i)+1000);
                            prevNight_nextMor = false;
                        }
                        //In case shift noon and next morning
                        if(prevNoon_nextMor){
                            score[i] += 800;
                            scoreList.set(i, scoreList.get(i)+800);
                            prevNoon_nextMor = false;
                        }

                        prevNight_nextNoon = false;
                        daysworkcounter++;
                        offWorkoff++;
                        if(WorkoffWork == 1){
                            score[i] += 1;
                            scoreList.set(i, scoreList.get(i)+1);
                        }
                        WorkoffWork=0;
                    }else if(pop[k][j] == 2){
                        hoursWork += 8;
                        consecutiveDays++;
                        consecutiveNights=0;
                        prevNight_nextMor = false;
                        prevNoon_nextMor = true;
                        if(prevNight_nextNoon){
                            score[i] += 800;
                            scoreList.set(i, scoreList.get(i)+800);
                            prevNight_nextNoon = false;
                        }
                        daysworkcounter++;
                        offWorkoff++;
                        if(WorkoffWork == 1){
                            score[i] += 1;
                            scoreList.set(i, scoreList.get(i)+1);
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
                            score[i] += 1;
                            scoreList.set(i, scoreList.get(i)+1);
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
                            score[i] += 1;
                            scoreList.set(i, scoreList.get(i)+1);
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
                    score[i] += 1000;
                    scoreList.set(i, scoreList.get(i)+1000);
                }

                //case employee works over 7 consecutive days
                if(consecutiveDays > 7){
                    score[i] += 1000;
                    scoreList.set(i, scoreList.get(i)+1000);
                }

                //case employee works over 4 consecutive night shifts
                if(consecutiveNights > 4){
                    score[i] += 1000;
                    scoreList.set(i, scoreList.get(i)+1000);
                }

                //case employee works over 4 night shifts must be assigned with at least two days off
                if(nightShifts >=4 && daysoff<2){
                    score[i] += 100;
                    scoreList.set(i, scoreList.get(i)+100);
                }

                //case employee works over 7 days must be assigned with at least two days off
                if(daysworkcounter >=7 && daysoff<2){
                    score[i] += 100;
                    scoreList.set(i, scoreList.get(i)+100);
                }

                //case employee works both weekends(4 days)
                if(consecutiveWeekends == 4){
                    score[i] += 1;
                    scoreList.set(i, scoreList.get(i)+1);
                }


            }
//            System.out.println(i+" SCORE "+","+score[i]);
//            continue;
        }

        //System.out.println(Arrays.deepToString(pop).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
        System.out.println("MIN VALUE "+ Collections.min( scoreList));
        System.out.println("MAX VALUE "+ Collections.max( scoreList));
        System.out.println("AVG VALUE "+ scoreList.stream().mapToDouble(val -> val).average().orElse(0.0));
//        score
        return score;

    }


}
