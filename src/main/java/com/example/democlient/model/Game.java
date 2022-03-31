package com.example.democlient.model;

public class Game {
    private boolean completed;
    private boolean roundCompleted;
    private int timeout;// mb not need
    private int timeBetweenRounds;// mb not need
    private int timeoutPassed;
    private int round;
    private Boxer my;
    private Boxer enemy;

    public Boxer getMy() {
        return my;
    }

    public void setMy(Boxer my) {
        this.my = my;
    }

    public Boxer getEnemy() {
        return enemy;
    }

    public void setEnemy(Boxer enemy) {
        this.enemy = enemy;
    }

    public int getRound() {
        return round;
    }

    public int getTimeBetweenRounds() {
        return timeBetweenRounds;
    }

    public void setTimeBetweenRounds(int timeBetweenRounds) {
        this.timeBetweenRounds = timeBetweenRounds;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public boolean isRoundCompleted() {
        return roundCompleted;
    }

    public void setRoundCompleted(boolean roundCompleted) {
        this.roundCompleted = roundCompleted;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getTimeoutPassed() {
        return timeoutPassed;
    }

    public void setTimeoutPassed(int timeoutPassed) {
        this.timeoutPassed = timeoutPassed;
    }

}
