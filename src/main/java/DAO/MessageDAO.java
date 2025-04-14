package DAO;

import Util.ConnectionUtil;
import Model.Account;
import Model.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    
    public Message validateMessage(Message message){
        if (message.getMessage_text() == "" || message.getMessage_text().length() > 255){
            return null;
        }
        Connection connection = ConnectionUtil.getConnection();
        try{
            // check whether this message is posted by a registered user
            String sql = "SELECT * FROM account WHERE account_id = ?";
            PreparedStatement preparedStatment = connection.prepareStatement(sql);
            preparedStatment.setInt(1, message.getPosted_by());
            ResultSet result = preparedStatment.executeQuery();
            if (result.next()){
                // Insert the message to the database
                String insertion = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES(?,?,?)";
                PreparedStatement preparedInsertion = connection.prepareStatement(insertion);
                preparedInsertion.setInt(1, message.getPosted_by());
                preparedInsertion.setString(2, message.getMessage_text());
                preparedInsertion.setLong(3, message.getTime_posted_epoch());
                preparedInsertion.executeUpdate();

                // Get the message just inserted.
                String extraction = "SELECT * FROM message WHERE posted_by = ? AND message_text = ? AND time_posted_epoch = ?";
                PreparedStatement preparedExtraction = connection.prepareStatement(extraction);
                preparedExtraction.setInt(1, message.getPosted_by());
                preparedExtraction.setString(2, message.getMessage_text());
                preparedExtraction.setLong(3, message.getTime_posted_epoch());
                ResultSet newMessage = preparedExtraction.executeQuery();
                newMessage.next();
                Message body = new Message(newMessage.getInt("message_id"),
                                        newMessage.getInt("posted_by"),
                                        newMessage.getString("message_text"),
                                        newMessage.getLong("time_posted_epoch"));
                return body;
            }else{
                return null;
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessage(String id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement preparedStatment = connection.prepareStatement(sql);
            preparedStatment.setInt(1, Integer.parseInt(id));
            ResultSet result = preparedStatment.executeQuery();
            if(result.next()){
                Message deletedMessage = new Message(result.getInt("message_id"),
                                            result.getInt("posted_by"),
                                            result.getString("message_text"),
                                            result.getLong("time_posted_epoch"));
                String deletion = "DELETE FROM message WHERE message_id = ?";
                PreparedStatement preparedDeletion= connection.prepareStatement(deletion);
                preparedDeletion.setInt(1, Integer.parseInt(id));
                preparedDeletion.executeUpdate();
                return deletedMessage;
            }else{
                return null;
            }  
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> retrieveMessageByUserId(String id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> result = new ArrayList<>();
        try{
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement preparedStatment = connection.prepareStatement(sql);
            preparedStatment.setInt(1, Integer.parseInt(id));
            ResultSet allMessages = preparedStatment.executeQuery();
            while(allMessages.next()){
                Message message = new Message(allMessages.getInt("message_id"),
                                        allMessages.getInt("posted_by"),
                                        allMessages.getString("message_text"),
                                        allMessages.getLong("time_posted_epoch"));
                result.add(message);
            }
            return result;

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> retrieveAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> result = new ArrayList<>();
        try{
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatment = connection.prepareStatement(sql);
            ResultSet allMessages = preparedStatment.executeQuery();
            while(allMessages.next()){
                Message message = new Message(allMessages.getInt("message_id"),
                                        allMessages.getInt("posted_by"),
                                        allMessages.getString("message_text"),
                                        allMessages.getLong("time_posted_epoch"));
                result.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return result;

    }

    public Message retrieveMessageByMessageId(String id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement preparedStatment = connection.prepareStatement(sql);
            preparedStatment.setInt(1, Integer.parseInt(id));
            ResultSet result = preparedStatment.executeQuery();
            if(result.next()){
                Message message = new Message(result.getInt("message_id"),
                                        result.getInt("posted_by"),
                                        result.getString("message_text"),
                                        result.getLong("time_posted_epoch"));
                return message;
            }else{
                return null;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;

    }

    public Message updateMessageByMessageId(Message message, String id){
        if (message.getMessage_text() == "" || message.getMessage_text().length() > 255){
            return null;
        }
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement preparedStatment = connection.prepareStatement(sql);
            preparedStatment.setInt(1, Integer.parseInt(id));
            ResultSet result = preparedStatment.executeQuery();
            if(result.next()){
                String updation = "UPDATE message SET message_text = ? WHERE message_id = ?";
                PreparedStatement preparedUpdation = connection.prepareStatement(updation);
                preparedUpdation.setString(1, message.getMessage_text());
                preparedUpdation.setInt(2, Integer.parseInt(id));
                preparedUpdation.executeUpdate();

                String extraction = "SELECT * FROM message WHERE message_id = ?";
                PreparedStatement preparedExtraction = connection.prepareStatement(extraction);
                preparedExtraction.setInt(1, Integer.parseInt(id));
                ResultSet newMessage = preparedExtraction.executeQuery();
                newMessage.next();
                Message body = new Message(newMessage.getInt("message_id"),
                                        newMessage.getInt("posted_by"),
                                        newMessage.getString("message_text"),
                                        newMessage.getLong("time_posted_epoch"));
                return body;
            }else{
                return null;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
