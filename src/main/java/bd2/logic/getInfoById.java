package bd2.logic;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class getInfoById {
	private static final HttpClient client = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();

	public static HttpResponse<String> getInfo(int userId, String url_end) throws Exception {
		JsonArray idsArray = new JsonArray();
        idsArray.add(userId);

    	JsonObject data = new JsonObject();
        data.add("ids", idsArray);

		String jsonData = gson.toJson(data);

		String url = "http://127.0.0.1:8000/" + url_end;
		
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(jsonData))
				.build();

		return client.send(request, HttpResponse.BodyHandlers.ofString());
	}
}
