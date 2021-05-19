package org.sberbank.simonov.bank;

import com.google.gson.Gson;

public class Context {
    private static Gson gson;

    private Context() {
    }

    public static Gson getGson() {
        if (gson == null) gson = new Gson();
        return gson;
    }
}
