package bd2.logic;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LoginHandler {
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();

	public static HttpResponse<String> login(String usernameText, String passwordText, boolean isRider) throws Exception {
		JsonObject data = new JsonObject();
		data.addProperty("login", usernameText);
		data.addProperty("password", passwordText);

		String jsonData = gson.toJson(data);

		String url;

		if (isRider) {
			url = "http://127.0.0.1:8000/riders/login_rider/";
		} else {
			url = "http://127.0.0.1:8000/employees/login_employee/";
		}

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(jsonData))
				.build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		return response;
	}
}