package info.cameronlund.ebot.commands.implementations;

import info.cameronlund.ebot.PermManager;
import info.cameronlund.ebot.builder.ResponseBuilder;
import info.cameronlund.ebot.commands.CommandCall;
import info.cameronlund.ebot.commands.SmartCommand;
import info.cameronlund.ebot.commands.arguments.StringArg;
import info.cameronlund.ebot.commands.arguments.UserArg;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.ArrayList;

public class UnmuteCommand extends SmartCommand {

    IDiscordClient client;
    PermManager pmanager;

    public UnmuteCommand(String command, IDiscordClient client, PermManager pmanager) {
        super(command);
        this.client = client;
        this.pmanager = pmanager;
        this.addArgument("user", new UserArg(client));
        this.addArgument("message", new StringArg()).setOptional(true).continueIfMissing(true).setDefault("Being a better boy");
    }

    @Override
    public void onCommand(CommandCall command) {
        IGuild guild = client.getGuildByID("291026622046273538");
        if(hasPerms(command, "291940898571419660", "291027779477176320")) {
            if (!hasArg("user")) {
                command.sendMessage(new ResponseBuilder().mention(command.getSender())
                        .withText(" I couldn't find that user!").build());
                return;
            }
            command.sendMessage(((IUser) getArg("user")).getName() + " has been unmuted for: " + getArg("message"));
            try {
                if(!((IUser) getArg("user")).getID().equals(command.getSender().getID()))
                   ((IUser) getArg("user")).getOrCreatePMChannel().sendMessage("You have been unmuted in the CHE Robotics Server for: " + getArg("message"));
            } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                e.printStackTrace();
            }
            pmanager.unmute((IUser) getArg("user"));
        } else {
            command.sendMessage("Sorry, but you do not have permissions to run this command " + command.getSender().getDisplayName(guild));
        }
    }

    private boolean hasPerms(CommandCall command, String... allowedRoles) {
        IGuild guild = client.getGuildByID("291026622046273538");
        ArrayList<IRole> roles = (ArrayList) command.getSender().getRolesForGuild(guild);
        for(IRole role : roles)
            for(int i = 0; i < allowedRoles.length; i ++)
                if(role.getID().equalsIgnoreCase(allowedRoles[i]))
                    return true;
        return false;
    }

}
