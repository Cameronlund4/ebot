package info.cameronlund.ebot.commands;

import info.cameronlund.ebot.commands.arguments.BooleanArg;
import info.cameronlund.ebot.commands.arguments.StringArg;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class BadWordCommand extends SmartCommand {

    public BadWordCommand(String command) {
        super(command);
        this.addArgument("message", new StringArg());
    }

    public void onCommand(CommandCall call) {
        String message = (String) getArg("message");
        message = starStr(message);
        try {
            call.getEvent().getMessage().delete();
            call.getHandler().sendMessage(message,call.getEvent());
        } catch (DiscordException e) {
            e.printStackTrace();
        } catch (MissingPermissionsException e) {
            e.printStackTrace();
        } catch (RateLimitException e) {
            e.printStackTrace();
        }
    }

    //If you didn't notice this is just a repurposed reverse string
    private String starStr(String str) {
        String output = "";
        for(int i = 0; i < str.length(); i++){
            output+=str.charAt(i) == ' ' ? " " : "✗";
        }
        return output;
    }
}
