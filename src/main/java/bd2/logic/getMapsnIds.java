package bd2.logic;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class getMapsnIds {
	public HashMap<String, Integer> getGroupMap() {
		HashMap<String, Integer> groupMap = new HashMap<>();

		try {
			HttpResponse<String> response = getAllInfo.getInfo("groups/get_all/");

			JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
			JsonArray jsonGroupsArray = jsonObject.getAsJsonArray("groups");

			for (int i = 0; i < jsonGroupsArray.size(); i++) {
				JsonObject groups = jsonGroupsArray.get(i).getAsJsonObject();
				int groupId = groups.get("id").getAsInt();
				String groupName = groups.get("name").getAsString();
				groupMap.put(groupName, groupId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return groupMap;
	}

	public ArrayList<Integer> getHorseIds() {
		ArrayList<Integer> horseIds = new ArrayList<>();

		try {
			HttpResponse<String> response = getAllInfo.getInfo("horses/get_all/");

			JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
			JsonArray jsonHorsesArray = jsonObject.getAsJsonArray("horses");

			for (int i = 0; i < jsonHorsesArray.size(); i++) {
				JsonObject horses = jsonHorsesArray.get(i).getAsJsonObject();
				int horseId = horses.get("id").getAsInt();
				horseIds.add(horseId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return horseIds;
	}

	public HashMap<String, Integer> getLicenceMap() {
		HashMap<String, Integer> licenseMap = new HashMap<>();

		try {
			HttpResponse<String> response = getAllInfo.getInfo("licences/get_all/");

			JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
			JsonArray jsonLicencesArray = jsonObject.getAsJsonArray("licences");

			for (int i = 0; i < jsonLicencesArray.size(); i++) {
				JsonObject licences = jsonLicencesArray.get(i).getAsJsonObject();
				int licenceId = licences.get("id").getAsInt();
				String licenceName = licences.get("licence_level").getAsString();
				licenseMap.put(licenceName, licenceId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return licenseMap;
	}
}
