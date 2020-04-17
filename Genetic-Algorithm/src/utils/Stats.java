package utils;

import java.util.Comparator;

public class Stats {
    int[][] population;
    int score;
    int id;

    public Stats(int id, int score, int[][] population){
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

    public static Comparator<Stats> scoreComparator = new Comparator<Stats>() {

        public int compare(Stats s1, Stats s2) {
            int score1 = s1.getScore();
            int score2 = s2.getScore();

            //ascending order
            return score1 - score2;

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }};
}
