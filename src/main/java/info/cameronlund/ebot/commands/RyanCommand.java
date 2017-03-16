package info.cameronlund.ebot.commands;

import info.cameronlund.ebot.commands.arguments.BooleanArg;
import info.cameronlund.ebot.commands.arguments.StringArg;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class RyanCommand extends SmartCommand {

    public RyanCommand(String command) {
        super(command);
    }

    public void onCommand(CommandCall call) {
        try {
            call.getHandler().sendMessage("***It has too much give!***",call.getEvent());
        } catch (DiscordException e) {
            e.printStackTrace();
        } catch (MissingPermissionsException e) {
            e.printStackTrace();
        } catch (RateLimitException e) {
            e.printStackTrace();
        }
    }
}
