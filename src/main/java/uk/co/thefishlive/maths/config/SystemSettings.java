package uk.co.thefishlive.maths.config;

import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.co.thefishlive.auth.data.Token;
import uk.co.thefishlive.auth.session.Session;
import uk.co.thefishlive.auth.user.UserProfile;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.meteor.data.AuthToken;
import uk.co.thefishlive.meteor.session.MeteorSession;
import uk.co.thefishlive.meteor.user.MeteorUserProfile;
import uk.co.thefishlive.meteor.utils.SerialisationUtils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 */
public class SystemSettings implements ConfigFile {

    private static final Gson GSON = SerialisationUtils.getGsonInstance();
    private static final Logger LOGGER = LogManager.getLogger();

    private final File file;

    private Token clientId;

    public SystemSettings(File file) {
        this.file = file;
    }

    @Override
    public File getFile() {
        return this.file;
    }

    @Override
    public void load() {
        LOGGER.info("Loading system settings from {} ", this.file.getAbsolutePath());
        if (!this.file.exists()) {
            initializeDefaultValues();
            this.save();
            return;
        }

        JsonParser parser = new JsonParser();

        try (FileReader reader = new FileReader(this.file)) {
            JsonElement element = parser.parse(reader);

            if (!element.isJsonObject()) {
                return;
            }

            JsonObject config = element.getAsJsonObject();

            this.clientId = AuthToken.decode(config.get("client-id").getAsString());

            LOGGER.info("Loaded client id {}", this.clientId.toString());
        } catch (IOException e) {
            Throwables.propagate(e);
        }
    }

    @Override
    public void save() {
        LOGGER.info("Saving system settings");
        JsonObject json = new JsonObject();

        json.addProperty("client-id", this.clientId.toString());

        if (!this.file.getParentFile().exists()) this.file.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(this.file)) {
            GSON.toJson(json, writer);
        } catch (IOException e) {
            Throwables.propagate(e);
        }
    }

    private void initializeDefaultValues() {
        this.clientId = AuthToken.generateRandom("client-id");
    }

    public Token getClientId() {
        return this.clientId;
    }
}
