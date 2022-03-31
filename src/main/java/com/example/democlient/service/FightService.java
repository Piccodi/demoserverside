package com.example.democlient.service;
// old
import com.example.democlient.model.Game;
import com.example.democlient.model.Player;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;

@Service
public class FightService {

    private final int rounds = 3;
    private final int timeout = 30000;
    private final int timeBetweenRounds = 15000;
    private WebSocketService wss;

    @Async
    public void fight (Player player1, Player player2) throws IOException, InterruptedException {

        //player1.setMatch(true);
        //player2.setMatch(true);

        long timeoutStart;

        var gson = new Gson();
        var game = new Game();
        var stats1 = new Mine();
        var stats2 = new Mine();

        stats1.setUsername(player1.getUsername());
        stats2.setUsername(player2.getUsername());
        stats1.setScore(0);
        stats2.setScore(0);

        int round = 1;

        String jsonStart =  " { \"type\" : \"state\", \"state\" : \"game\", \"game\" : {"; //}}
        String jsonGamePart1;
        String jsonGamePart2;
        String json1Round;
        String json2Round;

       // player1.setMyBlock(0);
        //player1.setMyKick(0);
        //player2.setMyBlock(0);
       // player2.setMyKick(0);

        game.setRoundCompleted(false);
        game.setCompleted(false);
        game.setTimeout(timeout);
        game.setTimeBetweenRounds(timeBetweenRounds);
        game.setTimeoutPassed(0);
        game.setRound(round);

        for(int i = 0; i < rounds * 2; i++) {

            timeoutStart = System.currentTimeMillis();

            jsonGamePart1 = "\"roundCompleted\" : " + game.isRoundCompleted() + "," +
                    "\"completed\" : " + game.isCompleted() + "," +
                    "\"timeout\" : " + game.getTimeout() + ",";

            jsonGamePart2 = "\"round\" : " + round + "," +
                    "\"mine\" : " ; //{}

            //stats1.setBlock(player1.getMyBlock());
           // stats1.setKick(player1.getMyKick());
            //stats2.setBlock(player2.getMyBlock());
            //stats2.setKick(player2.getMyKick());

            if(game.isRoundCompleted()){//результаты раунда
                if(stats2.getKick() != 0 && stats1.getBlock() != stats2.getKick()){
                    stats2.setHit(true);
                    stats2.setScore(stats2.getScore() + 1);
                }
                 else if(stats1.getKick() != 0 && stats2.getBlock() != stats1.getKick()){
                    stats1.setHit(true);
                    stats1.setScore(stats1.getScore() + 1);
                } else { stats1.setHit(false);
                    stats2.setHit(false);
                 }
                round++;
            }
            if(game.isCompleted()){
                if(stats1.getScore() > stats2.getScore()){
                    stats1.setWinner(true);
                }
                else if (stats2.getScore() > stats1.getScore()){
                    stats2.setWinner(true);
                }
                else{
                    stats1.setWinner(true);
                    stats2.setWinner(true);
                }
            }

            json1Round = gson.toJson(stats1) + ",\"enemy\" : " + gson.toJson(stats2) + "}}" ;
            json2Round = gson.toJson(stats2) + ",\"enemy\" : " + gson.toJson(stats1) + "}}" ;

            if(player1.getWss().isOpen()) {
                player1.getWss().sendMessage(new TextMessage(jsonStart +
                        jsonGamePart1 +
                        "\"timeoutPassed\" : " +
                        (System.currentTimeMillis() - timeoutStart) + "," +
                        jsonGamePart2 +
                        json1Round));
            }
            //todo rework set state
            //player1.setState( new String[]{jsonStart, jsonGamePart1 , jsonGamePart2 , json1Round});
            //player1.setTimeoutStart(timeoutStart);

            System.out.println(jsonStart + jsonGamePart1 + "\"timeoutPassed\" : "
                    + (System.currentTimeMillis() - timeoutStart) + "," + jsonGamePart2 + json1Round);

            if(player2.getWss().isOpen()) {
                player2.getWss().sendMessage(new TextMessage(jsonStart +
                        jsonGamePart1 +
                        "\"timeoutPassed\" : " +
                        (System.currentTimeMillis() - timeoutStart) + "," +
                        jsonGamePart2 +
                        json2Round));
            }
            //todo rework set state
            //player2.setState( new String[]{jsonStart, jsonGamePart1 , jsonGamePart2 , json2Round});
            //player2.setTimeoutStart(timeoutStart);

            System.out.println(jsonStart + jsonGamePart1 + "\"timeoutPassed\" : "
                    + (System.currentTimeMillis() - timeoutStart) + "," + jsonGamePart2 + json2Round);

            /*if(game.isRoundCompleted()){
                player1.setMyBlock(0);
                player1.setMyKick(0);
                player2.setMyBlock(0);
                player2.setMyKick(0);
            }
             */
            Thread.sleep(game.getTimeout());

            if(game.isRoundCompleted()){
                game.setRoundCompleted(false);
                game.setTimeout(timeout);
            }
            else{
                game.setRoundCompleted(true);
                game.setTimeout(timeBetweenRounds);
            }

            if(round == rounds) {
                game.setCompleted(true);
               /// player1.setGameCompleted(true);
               /////// player2.setGameCompleted(true);
            }
        }

        var textMess = new TextMessage("{\"type\" : \"state\", \"state\" : \"init\", \"waitingCount\" : \""
                + wss.getQueue().size() + "\"}" );

        player1.getWss().sendMessage(textMess);
        player2.getWss().sendMessage(textMess);

    }

    @Autowired
    public void setWss(WebSocketService wss) {
        this.wss = wss;
    }
}
