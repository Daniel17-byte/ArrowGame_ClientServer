package org.arrowgame.client.utils;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.arrowgame.client.responses.UserResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.arrowgame.client.responses.UserType;

public class Endpoints {
    private static final String BASE_URL = "http://localhost:9180";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String clickedStartGame() {
        return null;
    }

    public static void userRegisterMove(int row, int col) {
    }

    public static void addUser() {
    }

    public static boolean authenticate(String username, String password) {
        String url = STR."\{BASE_URL}/authenticate?username=\{username}&password=\{password}";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return Boolean.parseBoolean(response.body());
            } else {
                return false;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean register(String username, String password, String usertype) {
        String url = String.format("%s/register?username=%s&password=%s&usertype=%s", BASE_URL, username, password, usertype);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return Boolean.parseBoolean(response.body());
            } else {
                return false;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void updateUser() {
    }

    public static void deleteUser() {
    }

    public static UserResponse getUsers() {
        return null;
    }

    public static void clearBoard() {
    }

    public static void undoMove() {
    }

    public static UserResponse getUser() {
        String url = BASE_URL + "/getLoggedInUser";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), UserResponse.class);
            } else {
                return null;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
