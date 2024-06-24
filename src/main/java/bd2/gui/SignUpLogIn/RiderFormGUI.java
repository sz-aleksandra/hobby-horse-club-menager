package bd2.gui.SignUpLogIn;

import java.awt.List;
import java.net.http.HttpResponse;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import bd2.logic.*;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import kotlin.Pair;

import com.google.gson.*;

public class RiderFormGUI extends RegisterUserFormTemplate {

    public RiderFormGUI(int userId, String userType) {
        super(userId, userType);
        pageName = "Create Rider Account";
    }

    @Override
    protected String[] getFieldLabels() {
        String[] fieldLabels = {"Username", "Password", "Name", "Surname", "Date of birth", "Email", "Phone number", "Country", "City", "Street", "Street number", "Postal Code", "Parent consent", "Horse no", "Group", "License level"};
        return fieldLabels;
    }

    @Override
    protected String[] getFieldTypes() {
        String[] fieldTypes = {"text", "password", "text", "text", "comboBoxDate", "text", "text", "text", "text", "text", "text", "text", "text", "comboBoxString", "comboBoxString", "comboBoxString"};
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
		getMapsnIds mapsNIds = new getMapsnIds();
		HashMap<String, Integer> groups = mapsNIds.getGroupMap();
		ArrayList<Integer> horses = mapsNIds.getHorseIds();

		String[][] groupsBox = { new ArrayList<>(groups.keySet()).toArray(new String[0]) };
		String[][] horseIds = { horses.stream().map(Object::toString).toArray(String[]::new) };

        Object[] fieldParameters = {15, 15, 15, 15, new Integer[][]{days, months, years}, 20, 10, 15, 15, 15, 6, 6, 20, horseIds, groupsBox, new String[][]{{"No licence", "Licence Level 1", "Licence Level 2", "Licence Level 3", "Licence Level 4", "Licence Level 5"}}};
        return fieldParameters;
    }

	@Override
    protected Pair<Integer, String> createUser(HashMap<String, String> textFieldsValues) {
        return addRider.add(textFieldsValues);
    }


    public static void main(String[] args) {
        new RiderFormGUI(-1, "None").createGUI();
	}
}