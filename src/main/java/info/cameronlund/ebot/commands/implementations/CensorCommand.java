package info.cameronlund.ebot.commands.implementations;

import info.cameronlund.ebot.commands.CommandCall;
import info.cameronlund.ebot.commands.SmartCommand;
import info.cameronlund.ebot.commands.arguments.StringArg;

public class CensorCommand extends SmartCommand {

    public CensorCommand(String command) {
        super(command);
        this.addArgument("message", new StringArg());
    }

    public void onCommand(CommandCall call) {
        call.deleteMessage();
        call.sendMessage(call.getSender().getNicknameForGuild(call.getEvent().getClient().getGuilds().get(0)) + ": " +
                starStr((String) getArg("message")));
    }

    // Replace every character with a star or space
    private String starStr(String str) {
        String output = "";
        for (int i = 0; i < str.length(); i++) {
            output += str.charAt(i) == ' ' ? " " : "\\*";
        }
        return output;
    }
}
