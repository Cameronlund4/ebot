package info.cameronlund.ebot.commands.implementations.vexcommands;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import info.cameronlund.ebot.commands.CommandCall;
import info.cameronlund.ebot.commands.arguments.TeamArg;
import sx.blah.discord.util.EmbedBuilder;
import java.awt.*;

public class RankCommand extends VexQueryCommand {

    public RankCommand(String command) {
        super(command, "get_skills");
        this.addArgument("team", new TeamArg());
    }

    @Override
    public void onCommand(CommandCall call) {
        JsonObject response = query("&team=" + getArg("team") + "&season_rank=true");
        JsonArray results = getResults(response);

        if (results.size() < 3) {
            call.sendMessage("No rank could be found for team **"+getArg("team")+"**");
            return;
        }

        String description = "";
        description += "Driver: " + getJsonByType(0,results).get("score").getAsInt() + " ([#" +
                getJsonByType(0,results).get("season_rank").getAsInt() + "](https://vexdb.io/skills/VRC/Starstruck/Driver?p=" +
                getPage(getJsonByType(0,results).get("season_rank").getAsInt()) + ") in the world)\n";
        description += "Programming: " + getJsonByType(1,results).get("score").getAsInt() + " ([#" +
                getJsonByType(1,results).get("season_rank").getAsInt() + "](https://vexdb.io/skills/VRC/Starstruck/Programming?p=" +
                getPage(getJsonByType(1,results).get("season_rank").getAsInt()) + ") in the world)\n";
        description += "Overall: " + getJsonByType(2,results).get("score").getAsInt() + " ([#" +
                getJsonByType(2,results).get("season_rank").getAsInt() + "](https://vexdb.io/skills/VRC/Starstruck/Robot?p=" +
                getPage(getJsonByType(2,results).get("season_rank").getAsInt()) + ") in the world)";


        EmbedBuilder builder = new EmbedBuilder().withColor(Color.blue);

        builder.withDesc(description);

        call.sendEmbedMessage("__**Skills ranks for team " + getArg("team") + "**__", builder.build());
    }

    private int getPage(int rank) {
        return (int) Math.ceil((rank-1)/50) + 1;
    }

    private JsonObject getJsonByType(int type, JsonArray arr) {
        if(type >= 0 && type <= 2)
            for(int i = 0; i < 3; i ++)
                if(arr.get(i).getAsJsonObject().get("type").getAsInt() == type)
                    return arr.get(i).getAsJsonObject();
        return null;
    }

}
