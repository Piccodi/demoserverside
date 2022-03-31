package com.example.democlient.service;

import com.example.democlient.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private QueueService queueService;

    private final List<Player> players;

    {
        players = new ArrayList<Player>();
    }

    public void addNewPlayer(Player player){
        players.add(player);
    }

    public boolean playerAlreadyLogin(String username) {
        for (Player pl : players) {
            if(pl.getUsername().equals(username)) return true;
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

    public void joinQueue(Player player) throws InterruptedException {
        queueService.addPlayerToQueue(player);
    }

    public void undoJoinQueue(Player player){
        queueService.removePlayerFromQueue(player);
    }

    public void setKick(Player player ,int kick){
        player.getBoxer().setKick(kick);
    }

    public void setBlock(Player player ,int block){
        player.getBoxer().setBlock(block);
    }

    @Autowired
    public void setQueueService(QueueService queueService) {
        this.queueService = queueService;
    }
}

