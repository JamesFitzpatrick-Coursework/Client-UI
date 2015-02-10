package uk.co.thefishlive.maths.json;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 *
 */
public class GsonInstance {

    private static Gson instance;
    private static GsonBuilder builder = new GsonBuilder();

    public static Gson get() {
        if (!isBuilt()) {
            buildInstance();
        }

        return instance;
    }

    public static void buildInstance() {
        instance = builder
                .setDateFormat("yyyy-MM-dd'T'HH:mmZ")
                .setPrettyPrinting()
                .create();

        builder = null;
    }

    public static void registerAdapter(Type type, JsonAdapter<?> adapter) {
        Preconditions.checkState(!isBuilt(), "Cannot register adapter after building instance");
        builder.registerTypeAdapter(type, adapter);
    }

    public static boolean isBuilt() {
        return builder == null;
    }

}
