package com.example.democlient.model;

import org.springframework.web.socket.WebSocketSession;

public class WebSocketConnection {
    //user parameters
    private final String username;
    private WebSocketSession wss;
    private String[] state;
    private boolean match;

    //round parameters
    private int myKick;
    private int myBlock;
    private int enemyKick;
    private int enemyBlock;

    //game parameters
    private boolean gameCompleted;
    private int timeout;
    private long timeoutStart;
    private int timeoutPassed;


    public WebSocketConnection(String username, WebSocketSession wss) {
        this.username = username;
        this.wss = wss;
    }

    public boolean isGameCompleted() {
        return gameCompleted;
    }

    public void setGameCompleted(boolean gameCompleted) {
        this.gameCompleted = gameCompleted;
    }

    public void setWss(WebSocketSession wss) {
        this.wss = wss;
    }

    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {this.match = match;}

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public long getTimeoutStart() {
        return timeoutStart;
    }

    public void setTimeoutStart(long timeoutStart) {
        this.timeoutStart = timeoutStart;
    }

    public int getTimeoutPassed() {
        return timeoutPassed;
    }

    public void setTimeoutPassed(int timeoutPassed) {
        this.timeoutPassed = timeoutPassed;
    }

    public String[] getState() {
        return state;
    }

    public void setState(String[] state) {
        this.state = state;
    }

    public String getUsername() {
        return username;
    }

    public int getEnemyKick() {
        return enemyKick;
    }

    public void setEnemyKick(int enemyKick) {
        this.enemyKick = enemyKick;
    }

    public int getEnemyBlock() {
        return enemyBlock;
    }

    public void setEnemyBlock(int enemyBlock) {
        this.enemyBlock = enemyBlock;
    }

    public int getMyKick() {
        return myKick;
    }

    public void setMyKick(int kick) {
        this.myKick = kick;
    }

    public int getMyBlock() {
        return myBlock;
    }

    public void setMyBlock(int block) {
        this.myBlock = block;
    }

    public WebSocketSession getWss() {
        return wss;
    }

}
