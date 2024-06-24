package bd2.gui.AddDataByForm;

import bd2.gui.SeeDataByScrolling.TournamentsScrollGUI;
import kotlin.Pair;

import java.time.Year;
import java.util.HashMap;

public class AddTournamentGUI extends AddDataTemplate {

    public AddTournamentGUI(int userId, String userType) {
        super(userId, userType, "Add Tournament");
    }

    @Override
    protected String[] getFieldLabels() {
        String[] fieldLabels = {"Name", "Date", "Country", "City", "Street", "Street number", "Postal Code"};
        return fieldLabels;
    }

    @Override
    protected String[] getFieldTypes() {
        String[] fieldTypes = {"text", "comboBoxDate", "text", "text", "text", "text", "text"};
        return fieldTypes;
    }

    @Override
    protected Object[] getFieldParameters() {

        Integer[] days = new Integer[31];
        for (int i=0; i < days.length; i++) {
            days[i] = i+1;
        }
        Integer[] months = new Integer[12];
        for (int i=0; i < months.length; i++) {
            months[i] = i+1;
        }

        int baseYear = Year.now().getValue();
        Integer[] years = new Integer[110];
        for (int i=0; i < years.length; i++) {
            years[i] = baseYear-i;
        }

        Object[] fieldParameters = {40, new Integer[][]{days, months, years}, 15, 15, 15, 6, 6};
        return fieldParameters;
    }

    @Override
    protected void undoBtnClickedAction() {
        new TournamentsScrollGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    @Override
    protected Pair<Integer, String> addToDB(HashMap<String, String> textFieldsValues) {
        /*new AddNewUser(textFieldsValues.get("Username"), textFieldsValues.get("Password"), textFieldsValues.get("Name"), textFieldsValues.get("Surname"),
                textFieldsValues.get("Email"), textFieldsValues.get("Phone number"), textFieldsValues.get("Country"), textFieldsValues.get("City"),
                textFieldsValues.get("Street"), textFieldsValues.get("Postal Code"), textFieldsValues.get("Street number"), LocalDate.parse(textFieldsValues.get("Date of birth")),
                textFieldsValues.get("Nationality"), textFieldsValues.get("Gender"), true).insertIntoDatabase(); [MOCK]*/
        return null;
    }


    public static void main(String[] args) {
        new AddTournamentGUI(-1, "None").createGUI();
    }
}
