package com.example.democlient.service;
// old
import com.example.democlient.model.Player;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

@Service
public class WebSocketService {

    private List<Player> queue = new LinkedList<>();
    private Set<Player> players = new HashSet<>();

    public List<Player> getQueue() {
        return queue;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void addPlayerConnection(Player wsc){
        players.add(wsc);
    }

    public void join(Player wsc){
        queue.add(wsc);
    }

    public void undoJoin(Player wsc){
        queue.remove(wsc);
    }

    public boolean playerAlreadyJoined(String username) {
        for ( Player wsc : players) {
            if(wsc.getUsername().equals(username)) return true;
        }
        return false;
    }

    public Optional<Player> findBySession(WebSocketSession session) {
        for (Player wsc: players) {
            if(session == wsc.getWss()) return Optional.of(wsc);
        }
        return Optional.empty();
    }

    public Player findByUsername(String username) {

        for (Player wsc: players) {
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
