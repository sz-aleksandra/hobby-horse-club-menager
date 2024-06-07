package pap.gui.AddDataByForm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddOfferGUI extends AddOwnerPropertyFormTemplate {

    public AddOfferGUI(int userId, String userType) {
        super(userId, userType);
        pageName = "Add Offer";
    }

    @Override
    protected String[] getFieldLabels() {
        String[] fieldLabels = {"Offer name", "Hotel", "Room type", "Description", "Price per night", "Room no.", "Bathroom no.", "Bed no.", "Has kitchen", "Pet friendly"};
        return fieldLabels;
    }

    @Override
    protected String[] getFieldTypes() {
        String[] fieldTypes = {"text", "comboBoxString", "comboBoxString", "text", "text", "text", "text", "text", "comboBoxString", "comboBoxString"};
        return fieldTypes;
    }

    @Override
    protected Object[] getFieldParameters() {

        // mock
        String[][] ownerHotels = new String[][]{{"Hotel One", "Hotel Two", "Hotel Three"}};
        String[][] roomTypes = new String[][]{{"Standard", "Double", "Luxury"}};
        String[][] yesNoOptions = new String[][]{{"No", "Yes"}};

        Object[] fieldParameters = {15, ownerHotels, roomTypes, 60, 7, 5, 5, 5, yesNoOptions, yesNoOptions};

        return fieldParameters;
    }

    @Override
    protected List<Integer> validatePropertyData(HashMap<String, String> textFieldsValues) {
//        List <Integer> errorCodes = new OwnerValidator(textFieldsValues.get("Username"), textFieldsValues.get("Password"), textFieldsValues.get("Company name"),
//                textFieldsValues.get("Email"), textFieldsValues.get("Phone number"), textFieldsValues.get("Country"), textFieldsValues.get("City"),
//                textFieldsValues.get("Street"), textFieldsValues.get("Postal Code"), textFieldsValues.get("Street number"), textFieldsValues.get("NIP")).validateOwnerCredentials();
        List <Integer> errorCodes = new ArrayList();
        return errorCodes;
    }

    @Override
    protected void addProperty(HashMap<String, String> textFieldsValues) {
//        new AddNewOwner(textFieldsValues.get("Username"), textFieldsValues.get("Password"), textFieldsValues.get("Company name"),
//                textFieldsValues.get("Email"), textFieldsValues.get("Phone number"), textFieldsValues.get("Country"), textFieldsValues.get("City"),
//                textFieldsValues.get("Street"), textFieldsValues.get("Postal Code"), textFieldsValues.get("Street number"), textFieldsValues.get("NIP"),
//                false, true).insertIntoDatabase();
        ;
    }

    @Override
    protected void setFinishFormButtonText() {
        finishFormButtonText = "Add offer";
    }

    public static void main(String[] args) {
//        new AddHotelGUI(-1, "None").createGUI();
        new AddOfferGUI(3, "Owner").createGUI();
    }
}
