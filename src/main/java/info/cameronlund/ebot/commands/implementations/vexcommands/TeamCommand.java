package info.cameronlund.ebot.commands.implementations.vexcommands;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import info.cameronlund.ebot.commands.CommandCall;
import info.cameronlund.ebot.commands.arguments.TeamArg;
import sx.blah.discord.util.EmbedBuilder;

import java.awt.*;
import java.text.DecimalFormat;

public class TeamCommand extends VexQueryCommand {
    public TeamCommand(String command) {
        super(command, "get_teams");
        this.addArgument("team", new TeamArg());
    }

    @Override
    public void onCommand(CommandCall call) {
        EmbedBuilder builder = new EmbedBuilder().withColor(Color.blue);
        JsonObject response = query("&team=" + getArg("team"));
        JsonObject teamData = getResults(response).get(0).getAsJsonObject();
        String description = "[Vex Team " + getArg("team") + " - " + teamData.get("team_name").getAsString() +
                "](https://vexdb.io/teams/view/" + getArg("team") + ")\n";

        // Location, inline
        builder.appendField("Location", teamData.get("city").getAsString() + ", " +
                teamData.get("region").getAsString()+"", true);
        // Organization, inline
        builder.appendField("Organization", teamData.get("organisation").getAsString(), true);
        // Awards, inline
        builder.appendField("Awards", getResults(query("get_awards", "&team=" + getArg("team"))).size()
                + "", true);


        // Last tournament, not inline
        JsonArray events = getResults(query("get_events", "&team=" + getArg("team")));
        if (events.size() > 0) {
            JsonObject latest = null;
            JsonObject results = null;
            for (int i = 0; i < events.size(); i++) {
                latest = events.get(i).getAsJsonObject();

                JsonArray resultArray = getResults(query("get_rankings", "&team=" + getArg("team") + "&sku="
                        + latest.get("sku").getAsString()));
                if (resultArray.size() < 1)
                    continue;
                results = resultArray.get(0).getAsJsonObject();
                break;
            }

            if (latest == null || results == null) {
                builder.appendField("Last tournament", "Has not competed this season", false);
                call.sendEmbedMessage("", builder.build());
            }

            builder.appendField("Last tournament", "[" + latest.get("name").getAsString() +
                    "](https://vexdb.io/events/view/" + latest.get("sku").getAsString() + ")", false);

            // OPR / DPR / CCWM, inline
            DecimalFormat format = new DecimalFormat("0.##");
            String opr = format.format(results.get("opr").getAsDouble());
            String dpr = format.format(results.get("dpr").getAsDouble());
            String ccwm = format.format(results.get("ccwm").getAsDouble());
            builder.appendField("OPR / DPR / CCWM", opr + " / " + dpr + " / " + ccwm, true);

            // Win / Loss / Tie, inline
            String win = results.get("wins").getAsString();
            String loss = results.get("losses").getAsString();
            String tie = results.get("ties").getAsString();
            builder.appendField("Win / Loss / Tie", win + " / " + loss + " / " + tie, true);

            // WP / AP / SP / Seed, inline
            String wp = results.get("wp").getAsString();
            String ap = results.get("ap").getAsString();
            String sp = results.get("sp").getAsString();
            String seed = results.get("rank").getAsString();
            builder.appendField("WP / AP / SP / Seed", wp + " / " + ap + " / " + sp + " / " + seed, true);

            // Skills rank / points / attemps
            JsonObject skills = getJsonByType(2, getResults(query("get_skills", "&team=" + getArg("team") + "&sku="
                    + latest.get("sku").getAsString())));
            if (skills != null) {
                String attempts = skills.get("attempts").getAsString() + " Attempts";
                String rank = "#" + skills.get("rank").getAsString();
                String points = skills.get("score").getAsString() + " Points";
                builder.appendField("Skills", rank + " / " + points + " / " + attempts, true);
            } else {
                builder.appendField("Skills", "Team did not perform skills", true);
            }

            //// TRSP, inline
            //String trsp = results.get("trsp").getAsString();
            //builder.appendField("TRSP", trsp, true);

        } else {
            builder.appendField("Last tournament", "Has not competed this season", false);
        }

        builder.withDesc(description);
        call.sendEmbedMessage("", builder.build());
    }

    private JsonObject getJsonByType(int type, JsonArray arr) {
        if (type >= 0 && type <= 2)
            for (int i = 0; i < 3; i++)
                if (arr.get(i).getAsJsonObject().get("type").getAsInt() == type)
                    return arr.get(i).getAsJsonObject();
        return null;
    }
}
