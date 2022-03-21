package com.example.democlient.service;

import com.example.democlient.model.WebSocketConnection;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

@Service
public class WebSocketService {

    private List<WebSocketConnection> queue = new LinkedList<>();
    private Set<WebSocketConnection> players = new HashSet<>();

    public List<WebSocketConnection> getQueue() {
        return queue;
    }

    public Set<WebSocketConnection> getPlayers() {
        return players;
    }

    public void addPlayerConnection(WebSocketConnection wsc){
        players.add(wsc);
    }

    public void join(WebSocketConnection wsc){
        queue.add(wsc);
    }

    public void undoJoin(WebSocketConnection wsc){
        queue.remove(wsc);
    }

    public boolean playerAlreadyJoined(String username) {
        for ( WebSocketConnection wsc : players) {
            if(wsc.getUsername().equals(username)) return true;
        }
        return false;
    }

    public Optional<WebSocketConnection> findBySession(WebSocketSession session) {
        for (WebSocketConnection wsc: players) {
            if(session == wsc.getWss()) return Optional.of(wsc);
        }
        return Optional.empty();
    }

    public WebSocketConnection findByUsername(String username) {

        for (WebSocketConnection wsc: players) {
            if(wsc.getUsername().equals(username)) return wsc;
        }
        return null;
    }

    public String getCurrentState(String[] strings, long timeoutStart){
        return strings[0] +
                strings[1] +
                "\"timeoutPassed\" : " +(System.currentTimeMillis() - timeoutStart) + "," +
                strings[2] +
                strings[3];
    }
}
