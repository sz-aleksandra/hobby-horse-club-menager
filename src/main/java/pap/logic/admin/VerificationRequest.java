package pap.logic.admin;

import pap.db.entities.Owner;
import pap.db.dao.OwnerDAO;

public class VerificationRequest {
    private Owner owner;

    public VerificationRequest(Owner owner) {
        this.owner = owner;
    }

    public void accept() {
        owner.setVerified(true);
        new OwnerDAO().update(owner);
    }

    public void decline() {
        new OwnerDAO().delete(owner);
    }
}
