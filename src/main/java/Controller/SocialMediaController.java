package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.post("login", this::loginHandler);
        app.post("register", this::registerHandler);
        app.post("messages", this::createMessageHandler);
        app.delete("/messages/{id}", this::deleteMessageHandler);
        app.get("/accounts/{accountId}/messages", this::retrieveMessagesForUserHandler);
        app.get("messages", this::retrieveAllMessages);
        app.get("/messages/{messageId}", this::retrieveMessageByMessageId);
        app.patch("/messages/{messageId}", this::updateMessageHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void loginHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account checkedLogin = accountService.login(account);
        if(checkedLogin != null){
            context.json(mapper.writeValueAsString(checkedLogin));
            context.status(200);
        }else{
            context.status(401);
        }
        
    }

    private void registerHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account checkedRegister = accountService.register(account);
        if(checkedRegister != null){
            context.json(mapper.writeValueAsString(checkedRegister));
            context.status(200);
        }else{
            context.status(400);
        }
        
    }

    private void createMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message createdMessage = messageService.createMessage(message);
        if(createdMessage != null){
            context.json(mapper.writeValueAsString(createdMessage));
            context.status(200);
        }else{
            context.status(400);
        }
        
    }

    private void deleteMessageHandler(Context context) throws JsonProcessingException{
        String id = context.pathParam("id");
        ObjectMapper mapper = new ObjectMapper();
        Message deletedMessage = messageService.deleteMessage(id);
        if(deletedMessage != null){
            context.json(mapper.writeValueAsString(deletedMessage));
        }
        context.status(200);
        
    }

    private void retrieveMessagesForUserHandler(Context context) throws JsonProcessingException{
        String id = context.pathParam("accountId");
        ObjectMapper mapper = new ObjectMapper();
        List<Message> retrievedMessage = messageService.retrieveMessageForUser(id);
        if(retrievedMessage != null){
            context.json(mapper.writeValueAsString(retrievedMessage));
        }
        context.status(200);
        
    }

    private void retrieveAllMessages(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        List<Message> retrievedMessage = messageService.retrieveAllMessages();
        context.json(mapper.writeValueAsString(retrievedMessage));
        context.status(200);
        
    }

    private void retrieveMessageByMessageId(Context context) throws JsonProcessingException{
        String id = context.pathParam("messageId");
        ObjectMapper mapper = new ObjectMapper();
        Message retrievedMessage = messageService.retrieveMessageByMessageId(id);
        if(retrievedMessage != null){
            context.json(mapper.writeValueAsString(retrievedMessage));
        }
        context.status(200);
        
    }

    private void updateMessageHandler(Context context) throws JsonProcessingException{
        String id = context.pathParam("messageId");
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message updatedMessage = messageService.updateMessageByMessageId(message, id);
        if(updatedMessage != null){
            context.json(mapper.writeValueAsString(updatedMessage));
            context.status(200);
        }else{
            context.status(400);

        }
    }
}