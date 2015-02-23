package uk.co.thefishlive.maths.config;

import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import uk.co.thefishlive.auth.session.Session;
import uk.co.thefishlive.auth.user.UserProfile;
import uk.co.thefishlive.maths.Main;
import uk.co.thefishlive.meteor.data.AuthToken;
import uk.co.thefishlive.meteor.session.MeteorSession;
import uk.co.thefishlive.auth.session.exception.SessionException;
import uk.co.thefishlive.meteor.user.MeteorUserProfile;
import uk.co.thefishlive.meteor.utils.SerialisationUtils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 */
public class AuthDatabase implements ConfigFile {

    private static final Gson GSON = SerialisationUtils.getGsonInstance();
    private static final Logger LOGGER = LogManager.getLogger();

    private final File file;

    private Session session;

    public AuthDatabase(File file) {
        this.file = file;
    }

    @Override
    public File getFile() {
        return this.file;
    }

    @Override
    public void load() {
        LOGGER.info("Loading stored session from {} ", this.file.getAbsolutePath());
        if (!this.file.exists()) {
            return;
        }

        JsonParser parser = new JsonParser();

        try (FileReader reader = new FileReader(this.file)) {
            JsonElement element = parser.parse(reader);

            if (!element.isJsonObject()) {
                return;
            }

            JsonObject session = element.getAsJsonObject();

            JsonObject user = session.getAsJsonObject("user");
            UserProfile profile = new MeteorUserProfile(
                    AuthToken.decode(user.get("id").getAsString()),
                    user.get("name").getAsString(),
                    user.get("display-name").getAsString()
            );

            this.session = new MeteorSession(
                    Main.getInstance().getAuthHandler().getSessionHandler(),
                    profile,
                    null,
                    AuthToken.decode(session.get("refresh-token").getAsString())
            );

            this.session = this.session.refreshSession();

            LOGGER.info("Loaded session {}", this.session.toString());
            Main.getInstance().getAuthHandler().setActiveSession(this.session);
        } catch (IOException | SessionException e) {
            Throwables.propagate(e);
        }
    }

    @Override
    public void save() {
        LOGGER.info("Saving stored session ({}) to {}", session.toString(), this.file.getAbsolutePath());
        JsonObject json = new JsonObject();

        JsonObject user = new JsonObject();
        user.addProperty("id", this.session.getProfile().getId().toString());
        user.addProperty("name", this.session.getProfile().getName());
        user.addProperty("display-name", this.session.getProfile().getDisplayName());
        json.add("user", user);

        json.addProperty("refresh-token", ((MeteorSession) session).getRefreshToken().toString());

        if (!this.file.getParentFile().exists()) this.file.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(this.file)) {
            GSON.toJson(json, writer);
        } catch (IOException e) {
            Throwables.propagate(e);
        }
    }

    public Session getSession() {
        return session;
    }

    public void updateSession(Session session) {
        LOGGER.info("Updating stored session to {}", session.toString());
        this.session = session;
    }
}
