package info.cameronlund.ebot.commands.implementations;

import info.cameronlund.ebot.commands.CommandCall;
import info.cameronlund.ebot.commands.SmartCommand;
import info.cameronlund.ebot.commands.arguments.BooleanArg;
import info.cameronlund.ebot.commands.arguments.StringArg;

/**
 * Created by MichaelRyan on 3/17/17.
 */
public class VexCommand extends SmartCommand {

    public VexCommand(String command) {
        super(command);
        this.addArgument("bold", new BooleanArg()).setOptional(true).continueIfMissing(true);
        this.addArgument("message", new StringArg());
    }

    public void onCommand(CommandCall call) {
        String message = (String) getArg("message");
        boolean withBold = hasArg("bold") ? (Boolean) getArg("bold") : false;

        if (withBold)
            message = "**" + message + "**";
        call.sendMessage(message);
    }

}
