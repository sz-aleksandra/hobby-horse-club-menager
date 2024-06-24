package bd2.logic;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class deactivateAccount {
	private static final HttpClient client = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();

	public static HttpResponse<String> deactivate(int userId, boolean isRider) throws Exception {

		JsonObject data = new JsonObject();
		data.addProperty("id", userId);
		
		String jsonData = gson.toJson(data);

		String url;
		if (isRider) {
			url = "http://127.0.0.1:8000/riders/deactivate_account/";
		} else {
			url = "http://127.0.0.1:8000/employees/deactivate_account/";
		}

		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create(url))
			.header("Content-Type", "application/json")
			.POST(HttpRequest.BodyPublishers.ofString(jsonData))
			.build();

		return client.send(request, HttpResponse.BodyHandlers.ofString());
	}
}
