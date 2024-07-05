package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.*;

import Model.*;
import Service.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService = new AccountService();

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        //EXAMPLE
        //app.get("example-endpoint", this::exampleHandler);

        app.post("/register", this::registration);
        app.post("/login", this::login);
        app.post("/messages", this::newMessage);
        app.get("/messages", this::getAllMessage);

        // NEW

        app.get("/messages/{message_id}", this::getMessageByID);
        app.delete("/messages/{message_id}", this::deleteMessageByID);
        app.patch("messages/{message_id}", this::updateMessage);
        app.get("accounts/{account_id}/messages", this::getMessageByAccID);



        //app.post("/messages", this::)
        //app.get("messages", this::)
        
        return app;
    }

    // EXAMPLE
    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    // Response 400 if failed
    private void registration(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account registeredAccount = accountService.registerAccount(account);
        if(registeredAccount != null) {
            ctx.json(mapper.writeValueAsString(registeredAccount));
        }
        else {
            ctx.status(400);
        }
    }

    
    private void login(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loginAccount = accountService.verifyAccount(account);

        if (loginAccount != null) {
            ctx.json(mapper.writeValueAsString(loginAccount));
        }
        else {
            ctx.status(401);
        }
    }

    private void newMessage(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message messageValue = mapper.readValue(ctx.body(), Message.class);
        Message message = accountService.createNewMessage(messageValue);

        if(message != null ) {
            ctx.json(mapper.writeValueAsString(message));
        }
        else {
            ctx.status(400);
        }
    }

    private void getAllMessage(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Message> messages = accountService.getAllMessages();

        if(messages != null) {
            ctx.json(mapper.writeValueAsString(messages));
        }
        else {
            ctx.status(200);
        }
    }

    private void getMessageByID(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        int messageID = Integer.parseInt(ctx.pathParam("message_id"));

        Message message = accountService.getMessageByID(messageID);

        if(message != null) {
            ctx.json(mapper.writeValueAsString(message));
        }
    }
    
    private void deleteMessageByID(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        int messageID = Integer.parseInt(ctx.pathParam("message_id"));

        Message message = accountService.getMessageByID(messageID);

        if(message != null) {
            ctx.json(mapper.writeValueAsString(message));
        }
        else {
            ctx.status(200);
        }
    }

    private void updateMessage(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int id = Integer.parseInt(ctx.pathParam("message_id"));

        Message message = mapper.readValue(ctx.body(), Message.class);
        message.setMessage_id(id);

        message = accountService.updateMessageByID(message);

        if(message != null) {
            ctx.json(mapper.writeValueAsString(message));
        }
        else {
            ctx.status(400);
        }
    }

    private void getMessageByAccID(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        int accID = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = accountService.getAllMessagesFromUser(accID);

        if(!messages.isEmpty()) {
            ctx.json(messages);
        }
        else {
            ctx.json(messages);
            ctx.status(200);
        }

    }
   
}
