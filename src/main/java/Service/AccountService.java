package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {
    public AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account login(Account account){
        Account result = accountDAO.checkInformation(account);
        return result;
    }

    public Account register(Account account){
        Account result = accountDAO.signUp(account);
        return result;
    }
}
