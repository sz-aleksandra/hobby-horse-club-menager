package pap.gui;

        import pap.logic.add.AddNewOwner;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;

public class AddHotelGUI extends AddOwnerPropertyFormTemplate {

    public AddHotelGUI(int userId, String userType) {
        super(userId, userType);
        pageName = "Add Hotel";
    }

    @Override
    String[] getFieldLabels() {
        String[] fieldLabels = {"Hotel name", "Hotel description", "Hotel email", "Hotel phone number", "Hotel bank account number", "Country", "City", "Street", "Street number", "Postal Code"};
        return fieldLabels;
    }

    @Override
    String[] getFieldTypes() {
        String[] fieldTypes = {"text", "text", "text", "text", "text", "text", "text", "text", "text", "text"};
        return fieldTypes;
    }

    @Override
    Object[] getFieldParameters() {

        Object[] fieldParameters = {15, 60, 20, 10, 20, 15, 15, 15, 6, 6};

        return fieldParameters;
    }

    @Override
    List<Integer> validatePropertyData(HashMap<String, String> textFieldsValues) {
//        List <Integer> errorCodes = new OwnerValidator(textFieldsValues.get("Username"), textFieldsValues.get("Password"), textFieldsValues.get("Company name"),
//                textFieldsValues.get("Email"), textFieldsValues.get("Phone number"), textFieldsValues.get("Country"), textFieldsValues.get("City"),
//                textFieldsValues.get("Street"), textFieldsValues.get("Postal Code"), textFieldsValues.get("Street number"), textFieldsValues.get("NIP")).validateOwnerCredentials();
        List <Integer> errorCodes = new ArrayList();
        return errorCodes;
    }

    @Override
    void addProperty(HashMap<String, String> textFieldsValues) {
//        new AddNewOwner(textFieldsValues.get("Username"), textFieldsValues.get("Password"), textFieldsValues.get("Company name"),
//                textFieldsValues.get("Email"), textFieldsValues.get("Phone number"), textFieldsValues.get("Country"), textFieldsValues.get("City"),
//                textFieldsValues.get("Street"), textFieldsValues.get("Postal Code"), textFieldsValues.get("Street number"), textFieldsValues.get("NIP"),
//                false, true).insertIntoDatabase();
        ;
    }

    @Override
    void setFinishFormButtonText() {
        finishFormButtonText = "Add hotel";
    }

    public static void main(String[] args) {
//        new AddHotelGUI(-1, "None").createGUI();
        new AddHotelGUI(3, "Owner").createGUI();
    }
}
