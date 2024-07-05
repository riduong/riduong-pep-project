/* 
You will need to design and create your own Service classes from scratch.
You should refer to prior mini-project lab examples and course material for guidance. */

package Service;
import java.util.*;
import DAO.*;
import Model.*;

public class AccountService {
    AccountDAO accountServiceDAO;

    public AccountService() {
        accountServiceDAO = new AccountDAO();
    }

    public Account registerAccount(Account account) {
        // Registration Restraints
        if(account.getUsername().equals("") || account.getPassword().length() < 4) {
            return null;
        }
        
        else {
            return accountServiceDAO.registerAccount(account);
        }
    }

    public Account verifyAccount(Account account) {
        return accountServiceDAO.verifyAccount(account);
    }

    public Message createNewMessage(Message message) {
        if(message.getMessage_text() != "" && 
        message.getMessage_text().length() < 255 &&
        message.getPosted_by() > 0) {
            return accountServiceDAO.createMessage(message);
        }
        return null;
    }

    public List<Message> getAllMessages() {
        return accountServiceDAO.getAllMessages();
    }

    public Message getMessageByID(int id) {
        return accountServiceDAO.getMessageByID(id);
    }

    public Message deleteMessageByID(int id) {
        try {
        return accountServiceDAO.deleteMessageByID(id);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public Message updateMessageByID(Message message) {
        if (message.getMessage_text().length() > 254 || message.getMessage_text().equals("")) {
            return null;
        }
        else {
            return accountServiceDAO.updateMessageByID(message.getMessage_id(),message.getMessage_text());
        }
    }


    public List<Message> getAllMessagesFromUser(int id) {
        return accountServiceDAO.getAllMessagesFromUser(id);
    }
    
}
