package pap.logic.guiAction;

import pap.db.entities.Offer;
import pap.logic.SearchOffers;
import java.util.HashMap;
import java.util.List;

public class FindDisplayOffers {

    public final List<Offer> offers;

    public FindDisplayOffers(){
        offers = filterOffers();
    }

    public Integer[] getFittingElementsIds() {
        int offersCount = offers.size();
        Integer[] ids = new Integer[offersCount];
        for (int i = 0; i < offersCount; i++) {
            ids[i] = offers.get(i).getOfferId();
        }
        return ids;
    }

    public List<Offer> filterOffers(){
        //TODO: filters
        return SearchOffers.getAllOffers();
    }

    public HashMap<String, String> getElementInfo(int id){
        HashMap<String, String> elInfo = new HashMap<String, String>();

        Offer offer = offers.get(0);
        for (Offer nextOffer : offers){
            if(nextOffer.getOfferId() == id) {
                offer = nextOffer;
                break;
            }
        }

        elInfo.put("name", offer.getName());
        elInfo.put("room_type", offer.getRoomType());
        elInfo.put("rooms", String.valueOf(offer.getRoomNumber()));
        elInfo.put("bathrooms", String.valueOf(offer.getBathroomNumber()));
        elInfo.put("price", String.format("%.2f", offer.getPrice()) + " PLN");

        return elInfo;
    }
}
