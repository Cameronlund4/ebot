package info.cameronlund.ebot.commands.implementations;

import info.cameronlund.ebot.builder.ResponseBuilder;
import info.cameronlund.ebot.commands.CommandCall;
import info.cameronlund.ebot.commands.SmartCommand;
import info.cameronlund.ebot.commands.arguments.StringArg;
import info.cameronlund.ebot.commands.arguments.UserArg;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IUser;

public class UserCommand extends SmartCommand {
    public UserCommand(String command, IDiscordClient client) {
        super(command);
        this.addArgument("user", new UserArg(client));
        this.addArgument("message", new StringArg());
    }

    @Override
    public void onCommand(CommandCall command) {
        System.out.println(getArg("message"));
        if (!hasArg("user")) {
            command.sendMessage(new ResponseBuilder().mention(command.getSender())
                    .withText(" I couldn't find that user!").build());
            return;
        }
        command.sendMessage(((IUser) getArg("user")).getName() + " " + getArg("message"));
    }
}
