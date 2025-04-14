package DAO;

import Util.ConnectionUtil;
import Model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    
    public Account checkInformation(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement preparedStatment = connection.prepareStatement(sql);
            preparedStatment.setString(1, account.getUsername());
            ResultSet result = preparedStatment.executeQuery();
            while(result.next()){
                if(result.getString("password").equals(account.getPassword())){
                    Account currentAccount = new Account(result.getInt("account_id"),
                                                    result.getString("username"),
                                                    result.getString("password"));
                    return currentAccount;
                }else{
                    return null;
                }
                
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account signUp(Account account){
        if (account.getUsername() == ""){
            return null;
        }else if(account.getPassword().length() < 4){
            return null;
        }
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement preparedStatment = connection.prepareStatement(sql);
            preparedStatment.setString(1, account.getUsername());
            ResultSet result = preparedStatment.executeQuery();
            if(result.next()){
                return null;
            }else{
                // since the user doesn't provide the id of the account, we need to insert the account to
                // the datbase and then find the data of that account after insertion again. So three sql statments
                // will be needed for this question.
                String insertion = "INSERT INTO account(username, password) VALUES (?,?)";
                PreparedStatement preparedInsertion= connection.prepareStatement(insertion);
                preparedInsertion.setString(1, account.getUsername());
                preparedInsertion.setString(2, account.getPassword());
                preparedInsertion.executeUpdate();

                String extraction = "SELECT * FROM account WHERE username = ?";
                PreparedStatement preparedExtraction = connection.prepareStatement(extraction);
                preparedExtraction.setString(1, account.getUsername());
                ResultSet newAccount = preparedExtraction.executeQuery();
                newAccount.next();
                Account currentAccount = new Account(newAccount.getInt("account_id"),
                                                    newAccount.getString("username"),
                                                    newAccount.getString("password"));
                return currentAccount;
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
