package pap.gui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MockClass {
    //mock functions
    void addOfferToFavourites(int userId, int offerId) {System.out.println("Add " + offerId + " to favourites");}
    void removeOfferFromFavourites(int userId, int offerId) {System.out.println("Remove " + offerId + " from favourites");}
    boolean isOfferInFavourites(int userId, int offerId) {if (offerId == 9) return true; return false;};
}

public class FavouritesButtonCreator {

    public static FavouritesButton createFavouritesButton(int buttonSize, int imgSize, int elementId, int userId) {
        FavouritesButton favouritesButton = new FavouritesButton(buttonSize, imgSize, elementId);
        if (new MockClass().isOfferInFavourites(userId, elementId)) {
            favouritesButton.changeState();
        }
        return favouritesButton;
    }

    public static void favouritesBtnClicked(FavouritesButton favouritesButton, int userId) {
        if (favouritesButton.state.equals("base_state")) {
            new MockClass().addOfferToFavourites(userId, favouritesButton.elementId);
        } else {
            new MockClass().removeOfferFromFavourites(userId, favouritesButton.elementId);
        }
        favouritesButton.changeState();
    }
}
