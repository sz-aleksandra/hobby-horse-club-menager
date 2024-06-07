package pap.logic.favourites;

import pap.db.dao.ClientDAO;
import pap.db.dao.FavouriteOfferDAO;
import pap.db.dao.OfferDAO;
import pap.db.entities.Client;
import pap.db.entities.FavouriteHotel;
import pap.db.entities.FavouriteOffer;
import pap.db.entities.Offer;

public class AddOfferToFavourites {
    private FavouriteOffer fo = new FavouriteOffer();

    public AddOfferToFavourites(Integer offerId, Integer clientId) {
        this.fo.setClient(new ClientDAO().findById(clientId));
        this.fo.setOffer(new OfferDAO().findById(offerId));
    }

    public void insert() {
        new FavouriteOfferDAO().create(fo);
    }
}
