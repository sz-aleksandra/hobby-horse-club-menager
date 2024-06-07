package pap.logic.login;

import jakarta.persistence.NoResultException;
import pap.db.dao.AdminDAO;
import pap.db.entities.Admin;
import pap.logic.add.AddNewOwner;

import java.util.ArrayList;
import java.util.List;

public class AdminLogin {
    private final String username;
    private final String password;
    private List<Integer> codes;

    public AdminLogin(String username, String password) {
        this.username = username;
        this.password = password;
        this.codes = new ArrayList<>();
    }

    public Admin getAdminAccount() {
        try {
            Admin admin = new AdminDAO().findByUsername(username);
            if (password.equals(admin.getPassword())) {
                if (admin.isActive()) {
                    return admin;
                } else {
                    codes.add(409);
                }
            } else {
                codes.add(408);
            }
            return new Admin();
        } catch (NoResultException e) {
            codes.add(407);
            return new Admin();
        } catch (Exception exception) {
            codes.add(1);
            return new Admin();
        }
    }

    public List <Integer> getErrorCodes() {
        return codes;
    }
}
