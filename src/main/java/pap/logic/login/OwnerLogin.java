package pap.logic.login;
import jakarta.persistence.NoResultException;
import pap.db.dao.OwnerDAO;
import pap.db.entities.Owner;

import java.util.ArrayList;
import java.util.List;


public class OwnerLogin {
    private final String username;
    private final String password;
    private List<Integer> codes;

    public OwnerLogin(String username, String password) {
        this.username = username;
        this.password = password;
        this.codes = new ArrayList<>();
    }

    public Owner getOwnerAccount() {
        try {
            Owner owner = new OwnerDAO().findByUsername(username);
            if (password.equals(owner.getPassword())) {
                if (owner.isActive()) {
                    return owner;
                } else {
                    codes.add(406);
                }
            } else {
                codes.add(405);
            }
            return new Owner();
        } catch (NoResultException e) {
            codes.add(404);
            return new Owner();
        } catch (Exception exception) {
            codes.add(1);
            return new Owner();
        }
    }

    public List <Integer> getErrorCodes() {
        return codes;
    }
}
