package info.cameronlund.ebot.commands.implementations.vexcommands;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import info.cameronlund.ebot.commands.CommandCall;
import info.cameronlund.ebot.commands.arguments.TeamArg;
import sx.blah.discord.util.EmbedBuilder;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AwardsCommand extends VexQueryCommand {
    public AwardsCommand(String command) {
        super(command, "get_awards");
        this.addArgument("team", new TeamArg());
    }

    @Override
    public void onCommand(CommandCall call) {
        JsonObject response = query("&team=" + getArg("team"));
        JsonArray results = getResults(response);

        if (results.size() == 0) {
            call.sendMessage("No awards could be found for team **"+getArg("team")+"**");
            return;
        }

        HashMap<String, List<String>> awardsAtEvent = new HashMap<>();
        for (JsonElement resultRaw : results) {
            JsonObject result = resultRaw.getAsJsonObject();
            String sku = result.get("sku").getAsString();
            awardsAtEvent.computeIfAbsent(sku, k -> new ArrayList<>());
            awardsAtEvent.get(sku).add(result.get("name").getAsString());
        }

        EmbedBuilder builder = new EmbedBuilder().withColor(Color.blue);
        String description = "";
        boolean first = true;
        for (Map.Entry<String, List<String>> event : awardsAtEvent.entrySet()) {
            if (first)
                first = false;
            else
                description+="\n";
            String sku = event.getKey();
            String eventName = getResults(query("get_events", "&sku=" + sku)).get(0).getAsJsonObject()
                    .get("name").getAsString();
            description += "["+eventName+"](https://vexdb.io/events/view/"+sku+")\n";

            for (String award : event.getValue())
                description += "✪ "+award + "\n";
        }
        builder.withDesc(description);

        call.sendEmbedMessage("__**Awards for " + getArg("team") + "**__ (" +
                results.size() + "):", builder.build());
    }
}