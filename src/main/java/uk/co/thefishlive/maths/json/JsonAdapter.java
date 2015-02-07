package uk.co.thefishlive.maths.json;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

public interface JsonAdapter<T> extends JsonDeserializer<T>, JsonSerializer<T> {
}
