package info.cameronlund.ebot.commands.implementations;

import info.cameronlund.ebot.commands.CommandCall;
import info.cameronlund.ebot.commands.SmartCommand;
import info.cameronlund.ebot.commands.arguments.BooleanArg;
import info.cameronlund.ebot.commands.arguments.StringArg;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class BackwardsCommand extends SmartCommand {

    public BackwardsCommand(String command) {
        super(command);
        this.addArgument("message", new StringArg());
    }

    public void onCommand(CommandCall call) {
        String message = (String) getArg("message");
        message = reverseStr(message);
            call.sendMessage(message);
    }

    private String reverseStr(String str) {
        String output = "";
        for(int i = str.length()-1; i >= 0; i--){
            output+=str.charAt(i);
        }
        return output;
    }
}
