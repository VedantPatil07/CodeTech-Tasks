import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherFetcher{
    public static void main(String[] args)  { 
        // Replace with your OpenWeatherMap API key 
        String apiKey = "YOUR_API_KEY";
        String city = "Mumbai";
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";

        try {
            // Create HTTP client and request
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            // Send request and get response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Print raw JSON response
            System.out.println("Weather Data (Raw JSON):");
            System.out.println(response.body());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
