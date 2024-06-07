package pap.logic.ratings;

import pap.db.dao.RatingDAO;
import pap.db.entities.Client;
import pap.db.entities.Rating;

import java.util.List;

public class GetAllRatingsForClient {
    private Client client;
    public GetAllRatingsForClient(Client client) {
        this.client = client;
    }

    public List<Rating> getAllRatings() {
        return new RatingDAO().getRatingsForClient(client.getClientId());
    }
}
