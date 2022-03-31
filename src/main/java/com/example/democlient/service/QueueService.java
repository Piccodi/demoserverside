package com.example.democlient.service;

import com.example.democlient.model.Player;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class QueueService {

    //todo functional of queue

    private final ExecutorService executorService;
    private final BlockingQueue<Player> queue;

    {
        this.queue = new LinkedBlockingDeque<>();
        this.executorService = Executors.newCachedThreadPool();
    }

    public void addPlayerToQueue(Player p) throws InterruptedException {
        queue.add(p);
        if(queue.size() == 2){ //возможно нужен цикл для +3 или +4
            runFight(queue.take(), queue.take());
        }
    }

    public void removePlayerFromQueue(Player player){
        queue.remove(player);
    }

    public void runFight(Player p1, Player p2 ){
        var game = new GameTask(p1, p2);
        executorService.execute(game);
    }

    public BlockingQueue<Player> getQueue() {
        return queue;
    }
}
