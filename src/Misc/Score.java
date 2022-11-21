package Misc;

import java.io.Serializable;

public class Score implements Serializable {
    String name;
    int score;
    String difficulty;

    public Score(String name, int score, int difficultyChoice) {
        this.name = name;
        this.score = score;
        this.difficulty = difficultyChoice == 0 ? "Easy" : difficultyChoice == 1 ? "Medium" : "Hard";
    }

    @Override
    public String toString() {
        return "Name: " + name + " | Score: " + score + " | Difficulty: " + difficulty;
    }

    public int getScore() {
        return score;
    }
}
