package bd2.gui.AddDataByForm;

import bd2.gui.SeeDataByScrolling.HorsesScrollGUI;
import bd2.logic.getInfoById;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import kotlin.Pair;

import java.net.http.HttpResponse;
import java.util.HashMap;

import static bd2.DBRequests.base_url;
import static bd2.DBRequests.postMethod;

public class AddHorseGUI extends AddDataTemplate {

    public AddHorseGUI(int userId, String userType) {
        super(userId, userType, "Add Horse");
    }

    public AddHorseGUI(int userId, String userType, int editedElementId) {
        super(userId, userType, "Edit Horse");
        this.editedElementId = editedElementId;
        this.editMode = true;
    }

    @Override
    protected void createCustomGUI() {
        super.createCustomGUI();
        if (this.editMode) {
            populateFieldValues(getHorseDataFromDB(this.editedElementId));
        }
    }

    // [MOCK]
    HashMap<String, String> getHorseDataFromDB(int elementId) {
		HashMap<String, String> horseMap = new HashMap<>();

        try {
            HttpResponse<String> response = getInfoById.getInfo(elementId, "horses/get_by_id/");
			
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

            JsonArray jsonHorsesArray = jsonObject.getAsJsonArray("horses");
			JsonObject horse = jsonHorsesArray.get(0).getAsJsonObject();
            String breed = horse.get("breed").getAsString();
            String origin = horse.get("origin").getAsString();
            String height = horse.get("height").getAsString();
            String age = horse.get("age").getAsString();
            String color = horse.get("color").getAsString();
            String eyeColor = horse.get("eye_color").getAsString();
            String hairStyle = horse.get("hairstyle").getAsString();

			horseMap.put("Bread", breed);
			horseMap.put("Origin", origin);
			horseMap.put("Height", height);
			horseMap.put("Age", age);
			horseMap.put("Color", color);
			horseMap.put("Eye color", eyeColor);
			horseMap.put("Hairstyle", hairStyle);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return horseMap;
    }


    @Override
    protected String[] getFieldLabels() {
        String[] fieldLabels = {"Bread", "Origin", "Height", "Age", "Color", "Eye color", "Hairstyle"};
        return fieldLabels;
    }

    @Override
    protected String[] getFieldTypes() {
        String[] fieldTypes = {"comboBoxString", "comboBoxString", "text", "text", "comboBoxString", "comboBoxString", "comboBoxString"};
        return fieldTypes;
    }

    @Override
    protected Object[] getFieldParameters() {
        String[][] breads = new String[][]{{"Arab", "Thoroughbred", "Appaloosa", "Clydesdale", "Morgan", "Andalusian", "Friesian", "Shire", "Paint Horse", "Tennessee Walking Horse"}};
        String[][] origins = new String[][]{{"Arabian Peninsula", "England", "United States", "Scotland", "Spain", "Netherlands", "France", "Portugal", "Russia", "Australia"}};
        String[][] colors = new String[][]{{"Bay", "Black", "Chestnut", "Grey", "Palomino", "Buckskin", "Dun", "Roan", "Pinto"}};
        String[][] eye_colors = new String[][]{{"Brown", "Hazel", "Blue", "Amber", "Green", "Dark Brown", "Light Brown", "Gray", "Blue-Green", "Gold"}};
        String[][] hairstyles = new String[][]{{"Braided", "Running Braid", "Button Braids", "Hunter Braids", "Roached Mane", "Natural Flowing", "Docked Tail", "Braided Tail", "Tail Sets", "Fishtail Braid"}};

        Object[] fieldParameters = {breads, origins, 10, 10, colors, eye_colors, hairstyles};
        return fieldParameters;
    }

    @Override
    protected void undoBtnClickedAction() {
        new HorsesScrollGUI(userId, userType).createGUI();
        frame.setVisible(false);
    }

    @Override
    protected Pair<Integer, String> addToDB(HashMap<String, String> textFieldsValues) {
        String url = base_url + "horses/" + (this.editMode ? "update/" : "add/");

        JsonObject horse = new JsonObject();
        horse.addProperty("id", editedElementId);
        horse.addProperty("breed", textFieldsValues.get("Bread"));
        horse.addProperty("origin", textFieldsValues.get("Origin"));
        horse.addProperty("height", textFieldsValues.get("Height"));
        horse.addProperty("age", textFieldsValues.get("Age"));
        horse.addProperty("color", textFieldsValues.get("Color"));
        horse.addProperty("eye_color", textFieldsValues.get("Eye color"));
        horse.addProperty("hairstyle", textFieldsValues.get("Hairstyle"));
        JsonArray horsesArray = new JsonArray();
        horsesArray.add(horse);

        JsonObject data = new JsonObject();
        data.add("horses", horsesArray);

        Pair<Integer, JsonObject> response = postMethod(url, data);
        if (response != null) {
            if (response.getFirst() == 200 || response.getFirst() == 201) {
                return new Pair<>(response.getFirst(), "");
            }
            else {
                String errorMsg = response.getSecond().get("error").getAsString();
                return new Pair<>(response.getFirst(), errorMsg);
            }
        }
        else {
            return new Pair<>(-1, "Unknown error");
        }
    }


    public static void main(String[] args) {
        new AddHorseGUI(-1, "None").createGUI();
    }
}
