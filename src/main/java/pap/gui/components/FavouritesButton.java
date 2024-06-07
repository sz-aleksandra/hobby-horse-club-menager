package pap.gui.components;


public class FavouritesButton extends TwoImgsButton {

    public int elementId;

    public FavouritesButton(int buttonSize, int imgSize, int elementId) {
        super(buttonSize, buttonSize, imgSize, imgSize, "/icons/favourite.png", "/icons/favourite_filled.png");
        this.elementId = elementId;
    }
}
