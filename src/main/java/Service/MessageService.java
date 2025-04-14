package Service;

import Model.Message;

import java.util.List;
import DAO.MessageDAO;

public class MessageService {
    public MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public Message createMessage(Message message){
        Message result = messageDAO.validateMessage(message);
        return result;
    }

    public Message deleteMessage(String id){
        Message result = messageDAO.deleteMessage(id);
        return result;
    }

    public List<Message> retrieveMessageForUser(String id){
        List<Message> result = messageDAO.retrieveMessageByUserId(id);
        return result;
    }

    public List<Message> retrieveAllMessages(){
        List<Message> result = messageDAO.retrieveAllMessages();
        return result;
    }

    public Message retrieveMessageByMessageId(String id){
        Message result = messageDAO.retrieveMessageByMessageId(id);
        return result;
    }

    public Message updateMessageByMessageId(Message message, String id){
        Message result = messageDAO.updateMessageByMessageId(message, id);
        return result;
    }
}
