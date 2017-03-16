package info.cameronlund.ebot.commands;

import info.cameronlund.ebot.commands.arguments.BooleanArg;
import info.cameronlund.ebot.commands.arguments.StringArg;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class TestCommand extends SmartCommand {

    public TestCommand(String command) {
        super(command);
        this.addArgument("bold", new BooleanArg()).setOptional(true).continueIfMissing(true);
        this.addArgument("message", new StringArg());
    }

    public void onCommand(CommandCall call) {
        String message = (String) getArg("message");
        boolean withBold = hasArg("bold") ? (Boolean) getArg("bold") : false;
        if (withBold)
            message = "**" + message + "**";
        try {
            call.getHandler().sendMessage(message,call.getEvent());
        } catch (DiscordException e) {
            e.printStackTrace();
        } catch (MissingPermissionsException e) {
            e.printStackTrace();
        } catch (RateLimitException e) {
            e.printStackTrace();
        }
    }
}
