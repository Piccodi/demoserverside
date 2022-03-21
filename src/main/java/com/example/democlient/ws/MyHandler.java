package com.example.democlient.ws;

import com.example.democlient.model.WebSocketConnection;
import com.example.democlient.service.WebSocketService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

public class MyHandler extends TextWebSocketHandler {

    private WebSocketService wsservice;

    @Autowired
    public void setWsservice(WebSocketService wsservice) {
        this.wsservice = wsservice;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {

        var json = new JSONObject(message.getPayload());

        // fixme учесть перезаход в игру чтоб возвращалось состояние
        if(json.getString("action").equals("login")){

            if(wsservice.playerAlreadyJoined(json.getString("username"))) {
                var wsc = wsservice.findByUsername(json.getString("username"));
                if(wsc.isMatch() && !wsc.isGameCompleted()){
                    wsc.getWss().close();
                    wsc.setWss(session);
                    wsc.getWss().sendMessage(new TextMessage(wsservice.getCurrentState(wsc.getState(), wsc.getTimeoutStart())));
                }
                else{
                    wsc.getWss().close();
                    wsc.setWss(session);
                    wsservice.getQueue().remove(wsc);
                    session.sendMessage(new TextMessage("{\"type\" : \"state\", \"state\" : \"init\", \"waitingCount\" : \""
                            + wsservice.getQueue().size() + "\"}" ));

                }
            }
            else{
                System.out.println(session);
                wsservice.addPlayerConnection(new WebSocketConnection(json.getString("username"), session));
                session.sendMessage(new TextMessage("{\"type\" : \"state\", \"state\" : \"init\", \"waitingCount\" : \""
                    + wsservice.getQueue().size() + "\"}" ));
            }
        }
        else {
            try {
                var playerConnection = wsservice.findBySession(session).get();

                System.out.println(playerConnection.getWss());
                if (json.getString("action").equals("join")) {
                    wsservice.join(playerConnection);
                    session.sendMessage(new TextMessage("{\"type\" : \"response\"," +
                            "\"requestId\" : " + json.getInt("id") + "," +
                            "\"data\" : \"OK\" }"
                    ));
                    session.sendMessage(new TextMessage("{\"type\" : \"state\", \"state\" : \"wait\", \"waitingCount\" : \""
                            + wsservice.getQueue().size() + "\"}"));
                }
                if (json.getString("action").equals("undo_join")) {
                    wsservice.undoJoin(playerConnection);
                    session.sendMessage(new TextMessage("{\"type\" : \"response\"," +
                            "\"requestId\" : " + json.getInt("id") + "," +
                            "\"data\" : \"OK\" }"
                    ));
                    session.sendMessage(new TextMessage("{\"type\" : \"state\", \"state\" : \"init\", \"waitingCount\" : \""
                            + wsservice.getQueue().size() + "\"}"));
                }
                if (json.getString("action").equals("game_set_block")) {
                    session.sendMessage(new TextMessage("{\"type\" : \"response\"," +
                            "\"requestId\" : " + json.getInt("id") + "," +
                            "\"data\" : \"OK\" }"
                    ));
                    playerConnection.setMyBlock(json.getInt("block"));
                }
                if (json.getString("action").equals("game_set_kick")) {
                    session.sendMessage(new TextMessage("{\"type\" : \"response\"," +
                            "\"requestId\" : " + json.getInt("id") + "," +
                            "\"data\" : \"OK\" }"
                    ));
                    playerConnection.setMyKick(json.getInt("kick"));
                }
            }
            catch (Exception e){
                session.sendMessage(new TextMessage("{\"type\" : \"response\"," +
                        "\"requestId\" : " + json.getInt("id") + "," +
                        "\"error\" : true," +
                        "\"errorMessage\" : \"something goes wrong...\" }"
                        ));

            }
        }
        System.out.println(message.getPayload());
        System.out.println(session.getId());

    }
}
