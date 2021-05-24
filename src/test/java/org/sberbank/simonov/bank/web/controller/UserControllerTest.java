package org.sberbank.simonov.bank.web.controller;

import com.google.gson.reflect.TypeToken;
import org.junit.Test;
import org.sberbank.simonov.bank.data.UserTestData;
import org.sberbank.simonov.bank.model.User;
import org.sberbank.simonov.bank.to.UserTo;

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
import static org.sberbank.simonov.bank.data.UserTestData.*;
import static org.sberbank.simonov.bank.web.Dispatcher.*;

public class UserControllerTest extends CardControllerTest {

    @Test
    public void getAllCounterparties() throws IOException {
        URL url = new URL("http://localhost:8080/bank/rest/profile/users/1?counterparties=true");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(GET);
        connection.setDoInput(true);
        connection.setRequestProperty("Authorization", USER_1_CREDENTIALS);

        List<UserTo> actual = GSON.fromJson(
                new InputStreamReader(connection.getInputStream()),
                new TypeToken<ArrayList<UserTo>>() {
                }.getType()
        );
        assertEquals(connection.getResponseCode(), OK_CODE);
        assertThat(actual, contains(USER_TO_2, USER_TO_3));
    }

    @Test
    public void getById() throws IOException {
        URL url = new URL("http://localhost:8080/bank/rest/profile/users/1");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(GET);
        connection.setDoInput(true);
        connection.setRequestProperty("Authorization", USER_1_CREDENTIALS);

        UserTo actual = GSON.fromJson(
                new InputStreamReader(connection.getInputStream()),
                UserTo.class
        );
        assertEquals(connection.getResponseCode(), OK_CODE);
        assertEquals(USER_TO_1, actual);
    }

    @Test
    public void create() throws IOException {
        User created = UserTestData.created();

        URL url = new URL("http://localhost:8080/bank/rest/admin/users");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(POST);
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", EMPL_1_CREDENTIALS);

        byte[] out = GSON.toJson(created).getBytes(UTF_8);
        int length = out.length;
        connection.setFixedLengthStreamingMode(length);
        connection.connect();

        try (OutputStream outputStream = connection.getOutputStream()) {
            outputStream.write(out);
        }

        created.setId(NEW_USER_ID);

        assertEquals(connection.getResponseCode(), CREATED_CODE);

        url = new URL(String.format("%s/%d", "http://localhost:8080/bank/rest/profile/users", NEW_USER_ID));
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(GET);
        connection.setDoInput(true);
        connection.setRequestProperty("Authorization", USER_1_CREDENTIALS);

        UserTo received = GSON.fromJson(new InputStreamReader(connection.getInputStream()), UserTo.class);

        assertEquals(received, new UserTo(created));
    }
}