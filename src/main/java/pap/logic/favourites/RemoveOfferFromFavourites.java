package pap.logic.favourites;

import pap.db.dao.ClientDAO;
import pap.db.dao.FavouriteOfferDAO;
import pap.db.dao.OfferDAO;
import pap.db.entities.Client;
import pap.db.entities.FavouriteOffer;
import pap.db.entities.Offer;

public class RemoveOfferFromFavourites {
    private FavouriteOffer fo = new FavouriteOffer();

    public RemoveOfferFromFavourites(Integer offerId, Integer clientId) {
        this.fo.setClient(new ClientDAO().findById(clientId));
        this.fo.setOffer(new OfferDAO().findById(offerId));
    }

    public void remove() {
        new FavouriteOfferDAO().delete(fo);
    }
}
