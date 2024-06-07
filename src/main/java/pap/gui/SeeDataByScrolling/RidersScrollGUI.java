package pap.gui.SeeDataByScrolling;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class RidersScrollGUI extends DataScrollTemplate {

    //[MOCK]
    @Override
    protected void getElementsData() {
        this.fittingElementsIds = new Integer[]{1,2,3,4,5,6,7,8,9,10,12,13,14,15,16};
        this.nrOfElements = fittingElementsIds.length;
    }

    // [MOCK]
    @Override
    protected HashMap<String, String> getElementData(int elementId) {
        HashMap<String, String> dataInfo = new HashMap<String, String>();
        dataInfo.put("name", "Ola");
        dataInfo.put("surname", "Nowak");
        dataInfo.put("username", "kochamkonieOla123");
        dataInfo.put("date_of_birth", "2009-05-10");
        dataInfo.put("email", "ola.kochamkonie.nowak@email.com");
        dataInfo.put("phone_number", "+48437987171");
        dataInfo.put("address", "ul. Krakowska 23, Warszawa 02-526, Polska");
        dataInfo.put("has_parent_consent", "yes");
        dataInfo.put("licence_nr", "R5I90032EA");
        return dataInfo;
    }

    @Override
    protected void addInfoToDataInfoPanel (int elementId, JPanel dataInfoPanel) {
        HashMap<String, String> dataInfo = getElementData(elementId);
        addJLabel(dataInfo.get("name") + " " + dataInfo.get("surname"), Color.BLACK, fontBiggerBold, dataInfoPanel, elementWidth, elementHeight);
        addJLabel("System username: " + dataInfo.get("username") + ", Date of birth: " + dataInfo.get("date_of_birth"), Color.BLACK, fontSmaller, dataInfoPanel, elementWidth, elementHeight);
        addJLabel("Contact: " + dataInfo.get("email") + ", " + dataInfo.get("phone_number"), Color.BLACK, fontSmaller, dataInfoPanel, elementWidth, elementHeight);
        addJLabel(dataInfo.get("address"), Color.BLACK, fontSmaller, dataInfoPanel, elementWidth, elementHeight);
        addJLabel("Parent consent: " + dataInfo.get("has_parent_consent") + ", Licence no: " + dataInfo.get("licence_nr"), Color.BLACK, fontSmaller, dataInfoPanel, elementWidth, elementHeight);
    }

    @Override
    protected void handleAddData() {

    }

    @Override
    protected void handleEditData() {

    }

    @Override
    protected void handleRemoveData() {

    }

    public RidersScrollGUI(int userId, String userType){
        super(userId, userType, "Riders");
        elementHeight = frameHeight*5/24;
        elementWidth = frameWidth*6/10;
    }

    public static void main(String[] args) {
        new RidersScrollGUI(-1, "None").createGUI();
    }

}
