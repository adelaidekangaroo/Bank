package org.sberbank.simonov.bank.web.controller;

import com.google.gson.reflect.TypeToken;
import org.junit.Test;
import org.sberbank.simonov.bank.model.Card;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.sberbank.simonov.bank.data.CardTestData.*;
import static org.sberbank.simonov.bank.data.UserTestData.EMPL_1_CREDENTIALS;
import static org.sberbank.simonov.bank.data.UserTestData.USER_1_CREDENTIALS;
import static org.sberbank.simonov.bank.web.RequestMethod.*;
import static org.sberbank.simonov.bank.web.ResponseCode.*;

public class CardControllerTest extends InitControllerTest {

    @Test
    public void getAllByUser() throws IOException {
        URL url = new URL("http://localhost:8080/bank/rest/profile/users/1/cards");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(GET);
        connection.setDoInput(true);
        connection.setRequestProperty("Authorization", USER_1_CREDENTIALS);

        List<Card> actual = GSON.fromJson(
                new InputStreamReader(connection.getInputStream()),
                new TypeToken<ArrayList<Card>>() {
                }.getType()
        );
        assertEquals(connection.getResponseCode(), OK_CODE);
        assertThat(actual, contains(CARD_1, CARD_2));
    }

    @Test
    public void getAllUnconfirmed() throws IOException {
        URL url = new URL("http://localhost:8080/bank/rest/profile/users/cards?notconfirmed=true");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(GET);
        connection.setDoInput(true);
        connection.setRequestProperty("Authorization", EMPL_1_CREDENTIALS);

        List<Card> actual = GSON.fromJson(
                new InputStreamReader(connection.getInputStream()),
                new TypeToken<ArrayList<Card>>() {
                }.getType()
        );
        assertEquals(connection.getResponseCode(), OK_CODE);
        assertThat(actual, contains(CARD_1, CARD_2, CARD_3, CARD_4));
    }

    @Test
    public void getById() throws IOException {
        URL url = new URL("http://localhost:8080/bank/rest/profile/users/1/cards/1");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(GET);
        connection.setDoInput(true);
        connection.setRequestProperty("Authorization", USER_1_CREDENTIALS);

        Card actual = GSON.fromJson(
                new InputStreamReader(connection.getInputStream()),
                Card.class
        );
        assertEquals(connection.getResponseCode(), OK_CODE);
        assertEquals(CARD_1, actual);
    }

    @Test
    public void create() throws IOException {
        Card created = created();

        URL url = new URL("http://localhost:8080/bank/rest/profile/users/1/cards");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(POST);
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", USER_1_CREDENTIALS);

        byte[] out = GSON.toJson(created).getBytes(UTF_8);
        int length = out.length;
        connection.setFixedLengthStreamingMode(length);
        connection.connect();

        try (OutputStream outputStream = connection.getOutputStream()) {
            outputStream.write(out);
        }

        created.setId(NEW_CARD_ID);

        assertEquals(connection.getResponseCode(), CREATED_CODE);

        url = new URL(String.format("%s/%d", url.toString(), NEW_CARD_ID));
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(GET);
        connection.setDoInput(true);
        connection.setRequestProperty("Authorization", USER_1_CREDENTIALS);

        Card received = GSON.fromJson(new InputStreamReader(connection.getInputStream()), Card.class);

        assertEquals(received, created);
    }

    @Test
    public void update() throws IOException {
        Card updated = updated();

        URL url = new URL("http://localhost:8080/bank/rest/admin/users/1/cards/1");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(PUT);
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", EMPL_1_CREDENTIALS);

        byte[] out = GSON.toJson(updated).getBytes(UTF_8);
        int length = out.length;
        connection.setFixedLengthStreamingMode(length);
        connection.connect();

        try (OutputStream outputStream = connection.getOutputStream()) {
            outputStream.write(out);
        }

        assertEquals(connection.getResponseCode(), NO_CONTENT_CODE);

        url = new URL("http://localhost:8080/bank/rest/profile/users/1/cards/1");
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(GET);
        connection.setDoInput(true);
        connection.setRequestProperty("Authorization", USER_1_CREDENTIALS);

        Card received = GSON.fromJson(new InputStreamReader(connection.getInputStream()), Card.class);

        assertEquals(received, updated);
    }
}