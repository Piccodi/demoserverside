package com.example.democlient.model;

public class Game {
    private boolean completed;
    private boolean roundCompleted;
    private int timeout;
    private int timeBetweenRounds;
    private int timeoutPassed;
    private int round;

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
