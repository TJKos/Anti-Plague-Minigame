package Model;

import Misc.Score;

import javax.swing.*;
import java.util.Vector;

public class HighScoresModel extends AbstractListModel {
    public Vector<Score> scores;

    public HighScoresModel(Vector<Score> scores) {
        this.scores = scores;
    }

    @Override
    public int getSize() {
        return scores.size();
    }

    @Override
    public Object getElementAt(int index) {
        return scores.get(index);
    }
}