package com.example.democlient.service;

import com.example.democlient.model.WebSocketConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Random;

@Service
public class GameService {

    private FightService fightService;
    private WebSocketService wss;

    @Autowired
    public void setFightService(FightService fightService) {
        this.fightService = fightService;
    }

    @Autowired
    public void setWss(WebSocketService wss) {
        this.wss = wss;
    }

    @Async
    @Scheduled(fixedDelay = 1000)
    public void createCouplesForFight() throws IOException, InterruptedException {

        if(wss.getQueue().size() >= 2){
            var queue = wss.getQueue();

            var size = queue.size();
            int randomIndex;
            for (int i = 0; i < size; i += 2) {
                if (queue.size() <= 2) {
                    if (queue.size() <= 1) break;
                    fightService.fight(queue.get(0), queue.get(1));
                    queue.remove(1);
                    queue.remove(0);
                    break;
                }
                else {
                    randomIndex = new Random().nextInt(size - 1);
                    System.out.println( i + "___ rand ind = " + randomIndex);
                    if (randomIndex != 0) {
                        fightService.fight(queue.get(0), queue.get(randomIndex));
                        queue.remove(randomIndex);
                        queue.remove(0);
                    }
                }
            }
        }
    }
    @Async
    @Scheduled(fixedRate = 20000)
    public void removeAFK(){
        if(wss.getPlayers().size() > 0){
            wss.getPlayers().removeIf(pl -> !pl.getWss().isOpen());
        }
    }
}

