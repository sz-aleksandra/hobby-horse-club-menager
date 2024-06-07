package pap.logic.login;
import jakarta.persistence.NoResultException;
import pap.db.dao.ClientDAO;
import pap.db.entities.Client;
import java.util.*;

public class ClientLogin {
    private final String username;
    private final String password;
    private List <Integer> codes;
    public ClientLogin(String username, String password) {
        this.username = username;
        this.password = password;
        this.codes = new ArrayList<>();
    }

    public Client getUserAccount() {
        try {
            Client user = new ClientDAO().findByUsername(username);
            if (password.equals(user.getPassword())) {
                if (user.isActive()) {
                    return user;
                } else {
                    codes.add(403);
                }
            } else {
                codes.add(402);
            }
            return new Client();
        } catch (NoResultException e) {
            codes.add(401);
            return new Client();
        } catch (Exception exception) {
            codes.add(1);
            return new Client();
        }
    }

    public List <Integer> getErrorCodes() {
        return codes;
    }
}
