package com.edu.ustb.entities;

public class FaceTool {
    private Double score;

    public FaceTool(Double score) {
        this.score = score;
    }

    public FaceTool() {
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "FaceTool{" +
                "score=" + score +
                '}';
    }
}
