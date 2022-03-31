package com.example.democlient.model;

import org.springframework.web.socket.WebSocketSession;

public class Player {

    private final String username;
    private WebSocketSession wss;
    private State state;
    private Boxer boxer;

    public Player(String username, WebSocketSession wss) {
        this.username = username;
        this.wss = wss;
        this.state = new State();
    }

    public String getUsername() {
        return username;
    }

    public WebSocketSession getWss() {
        return wss;
    }

    public void setWss(WebSocketSession wss) {
        this.wss = wss;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Boxer getBoxer() {
        return boxer;
    }

    public void setBoxer(Boxer boxer) {
        this.boxer = boxer;
    }
}
