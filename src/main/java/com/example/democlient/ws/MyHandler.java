package com.example.democlient.ws;

import com.example.democlient.model.Player;
import com.example.democlient.service.PlayerService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

public class MyHandler extends TextWebSocketHandler {

    //todo сделать разбиение между состоянием и ответом либо сделать обьект response
    private PlayerService playerService;

    @Autowired
    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {

        var json = new JSONObject(message.getPayload());

        // fixme учесть перезаход в игру чтоб возвращалось состояние
        if(json.getString("action").equals("login")){

            if(playerService.playerAlreadyLogin(json.getString("username"))) {
                var player = playerService.findByUsername(json.getString("username"));
                player.getWss().close();
                player.setWss(session);
                //todo player.getState();
                // todo проверить можно ли не создавать new text message, с слать сразу json
                /*if(wsc.isMatch() && !wsc.isGameCompleted()){
                    wsc.getWss().close();
                    wsc.setWss(session);
                    //wsc.getWss().sendMessage(new TextMessage(wsservice.getCurrentState(wsc.getState(), wsc.getTimeoutStart())));

                }
                else{
                    wsc.getWss().close();
                    wsc.setWss(session);
                    wsservice.getQueue().remove(wsc);
                    /*session.sendMessage(new TextMessage(НН"{\"type\" : \"state\", \"state\" : \"init\", \"waitingCount\" : \""
                            + wsservice.getQueue().size() + "\"}" ));
                    */
                //}
            }
            else{
                System.out.println(session);
                playerService.addNewPlayer(new Player(json.getString("username"), session));
                /* session.sendMessage(new TextMessage("{\"type\" : \"state\", \"state\" : \"init\", \"waitingCount\" : \""
                    + wsservice.getQueue().size() + "\"}" ));

                 */
            }

        }
        else {
            try {
                var player = playerService.findBySession(session).get();

                System.out.println(player.getWss());
                if (json.getString("action").equals("join")) {
                    playerService.joinQueue(player);
                    /*session.sendMessage(new TextMessage("{\"type\" : \"response\"," +
                            "\"requestId\" : " + json.getInt("id") + "," +
                            "\"data\" : \"OK\" }"
                    ));*/
                    //session.sendMessage(new TextMessage("{\"type\" : \"state\", \"state\" : \"wait\", \"waitingCount\" : \""
                      //      + wsservice.getQueue().size() + "\"}"));
                }
                if (json.getString("action").equals("undo_join")) {
                    playerService.undoJoinQueue(player);
                    /*session.sendMessage(new TextMessage("{\"type\" : \"response\"," +
                            "\"requestId\" : " + json.getInt("id") + "," +
                            "\"data\" : \"OK\" }"
                    ));
                    session.sendMessage(new TextMessage("{\"type\" : \"state\", \"state\" : \"init\", \"waitingCount\" : \""
                            + wsservice.getQueue().size() + "\"}"));*/
                }
                if (json.getString("action").equals("game_set_block")) {
                    /*session.sendMessage(new TextMessage("{\"type\" : \"response\"," +
                            "\"requestId\" : " + json.getInt("id") + "," +
                            "\"data\" : \"OK\" }"
                    ));*/
                    playerService.setBlock(player, json.getInt("block"));
                }
                if (json.getString("action").equals("game_set_kick")) {
                    /*session.sendMessage(new TextMessage("{\"type\" : \"response\"," +
                            "\"requestId\" : " + json.getInt("id") + "," +
                            "\"data\" : \"OK\" }"
                    ));*/
                    playerService.setKick(player, json.getInt("kick"));
                }
            }
            catch (Exception e){
                /*session.sendMessage(new TextMessage("{\"type\" : \"response\"," +
                        "\"requestId\" : " + json.getInt("id") + "," +
                        "\"error\" : true," +
                        "\"errorMessage\" : \"something goes wrong...\" }"
                        ));
                */
            }
        }
        System.out.println(message.getPayload());
        System.out.println(session.getId());

    }
}
