package info.cameronlund.ebot.commands.implementations;

import info.cameronlund.ebot.commands.CommandCall;
import info.cameronlund.ebot.commands.SmartCommand;

public class RyanCommand extends SmartCommand {

    public RyanCommand(String command) {
        super(command);
    }

    public void onCommand(CommandCall call) {
        call.sendMessage("***It has too much give!***");
    }
}