package info.cameronlund.ebot.commands.implementations.vexcommands;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import info.cameronlund.ebot.HttpsRequest;
import info.cameronlund.ebot.commands.SmartCommand;

public abstract class VexQueryCommand extends SmartCommand {
    private final String SEASON = "Starstruck";
    private final String api;

    public VexQueryCommand(String command, String api) {
        super(command);
        this.api = api;
    }

    protected JsonObject query(String ending) {
        return query(api,ending);
    }

    protected JsonObject query(String api, String ending) {
        HttpsRequest req = new HttpsRequest("https://api.vexdb.io/v1/"+api+"?season=" + SEASON + ending);
        return new JsonParser().parse(req.getRawResponse()).getAsJsonObject();
    }

    protected JsonArray getResults(JsonObject object) {
        return object.getAsJsonArray("result");
    }
}
