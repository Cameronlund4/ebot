package info.cameronlund.ebot.commands.implementations;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import info.cameronlund.ebot.HttpsRequest;
import info.cameronlund.ebot.commands.CommandCall;
import info.cameronlund.ebot.commands.SmartCommand;
import info.cameronlund.ebot.commands.arguments.BooleanArg;
import info.cameronlund.ebot.commands.arguments.StringArg;
import info.cameronlund.ebot.commands.arguments.TeamArg;
import info.cameronlund.ebot.commands.arguments.WordArg;

public class VexCommand extends SmartCommand {

    private final String SEASON = "Starstruck";

    public VexCommand(String command) {
        super(command);
        this.addArgument("command", new WordArg());
        this.addArgument("team", new TeamArg());
    }

    public void onCommand(CommandCall call) {
        //https://api.vexdb.io/v1/get_skills?season=Starstruck&type=0&season_rank=true&team=2616E
        String message = "";
        if(((String)getArg("command")).equalsIgnoreCase("rank")) {
            HttpsRequest req = new HttpsRequest("https://api.vexdb.io/v1/get_skills?season=" + SEASON + "&type=0&season_rank=true&team=" + getArg("team"));
            JsonObject res = new JsonParser().parse(req.getRawResponse()).getAsJsonObject();
            message = "The global rank for " + getArg("team") + " for season " + SEASON + " is ";
            message += res.getAsJsonArray("result").get(0).getAsJsonObject().get("season_rank").getAsInt();
        } else {
            message = "Invalid Vex Command!";
        }
        call.sendMessage(message);
    }

}
