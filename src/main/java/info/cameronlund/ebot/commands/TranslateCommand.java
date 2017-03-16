package info.cameronlund.ebot.commands;

import info.cameronlund.ebot.commands.arguments.BooleanArg;
import info.cameronlund.ebot.commands.arguments.StringArg;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

/**
 * Created by MichaelRyan on 3/16/17.
 */

//Super Secret Api key: trnsl.1.1.20170316T223724Z.8238214aa838c9dc.681e23d2bdb0abd2b0d2d9e52b10f212bc257a39
public class TranslateCommand extends SmartCommand {

    public TranslateCommand(String command) {
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
