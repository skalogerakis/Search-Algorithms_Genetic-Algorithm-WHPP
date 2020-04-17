package utils;

import java.util.Comparator;

public class Statistics {
    int[][] population;
    int score;
    int id;

    public Statistics(int id, int score, int[][] population){
        this.id = id;
        this.score = score;
        this.population = population;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int Id) {
        this.id = Id;
    }

    public int[][] getPopulation() {
        return population;
    }

    public void setPopulation(int[][] population) {
        this.population = population;
    }

    public static Comparator<Statistics> scoreComparator = new Comparator<Statistics>() {

        public int compare(Statistics s1, Statistics s2) {
            int score1 = s1.getScore();
            int score2 = s2.getScore();

            //ascending order
            return score1 - score2;

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }};
}
