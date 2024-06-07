package pap.db.misc;

import pap.db.dao.OfferDAO;
import pap.db.entities.Offer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Uploader {
    public static void uploadImageToOffers(int orderId, String imagePath) throws IOException {
        Offer offer = new OfferDAO().findById(orderId);
        offer.setImageData(Files.readAllBytes(Paths.get(imagePath)));
        new OfferDAO().update(offer);
    }
}
