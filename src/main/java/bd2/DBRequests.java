package bd2;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import kotlin.Pair;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DBRequests {
    public static final String base_url = "http://127.0.0.1:8000/";

    public static Pair<Integer, JsonObject> postMethod(String url, Object data) {
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();
        String jsonData = gson.toJson(data);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonData))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
            return new Pair<>(response.statusCode(), jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
