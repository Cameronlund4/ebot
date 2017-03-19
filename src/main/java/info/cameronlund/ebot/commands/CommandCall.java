package info.cameronlund.ebot.commands;

import info.cameronlund.ebot.CommandManager;
import info.cameronlund.ebot.commands.arguments.CommandArg;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.LinkedHashMap;
import java.util.Map;

// TODO Javadoc
public class CommandCall {
    private String cmd;
    private String alias;
    private LinkedHashMap<String, CommandArg> smartArgs;
    private String[] args;
    private IUser sender;

    public IUser getSender() {
        return sender;
    }

    public CommandManager getManager() {
        return manager;
    }

    public MessageReceivedEvent getEvent() {
        return event;
    }

    private CommandManager manager;
    private MessageReceivedEvent event;

    public CommandCall(CommandManager manager, MessageReceivedEvent event, IUser sender, String cmd, String alias, String[] args, LinkedHashMap<String,
            CommandArg> smartArgs) {
        this.smartArgs = smartArgs;
        this.sender = sender;
        this.cmd = cmd;
        this.alias = alias;
        this.args = args;
        this.manager = manager;
        this.event = event;
    }

    /**
     * Shit is this a messy method, but it's gotta be what it's gotta be. This method loops the arguments, and <\br>
     * and determines whether or not we have the right stuff to run the command, and if so sets the values of the
     * args.
     *
     * @return Whether or not we're good to run
     */
    public boolean processArgs() {
        String[] textArgs = args.clone(); // Make sure we don't kill old args

        // Cache any permissions we've checked, since permission checking can be extensive
        //HashMap<Permission, Boolean> permissions = new HashMap<Permission, Boolean>();
        argcheck:
        for (Map.Entry<String, CommandArg> argSet : smartArgs.entrySet()) {
            CommandArg arg = argSet.getValue();
            arg.clearResult();

            // First, let's see if we even have valid input
            if (!arg.hasValidInput(textArgs)) { // If we don't have correct arguments
                // NOTE: This is the same as below but can't be methodized due to returns and continues
                if (!arg.isContinueIfMissing()) { // And if we don't want to continue
                    if (!arg.isOptional()) { // And if this wasn't optional, tell the person
                        // TODO Output better missing args error
                        try {
                            manager.sendMessage(sender.mention
                                    (true) + " you're missing  or have invalid input for " +
                                    "the argument `" + argSet.getKey()
                                    + "`. Please include it to run the command.", event);
                        } catch (DiscordException | MissingPermissionsException | RateLimitException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }
                    return true; // But if it was optional, just say we can run
                } else { // But if we do want to continue
                    continue; // Check the next arg
                }
                // END NOTE
            }

            /*// Then if we need to, check their permissions
            if (arg.hasPermissions()) {
                for (Object pO : arg.getPermissions()) {
                    Permission p = (Permission) pO;
                    if (permissions.containsKey(p) ? permissions.get(p) : sender.hasPermission(p)) { // We have perm
                        if (!permissions.containsKey(p))
                            permissions.put(p, true);
                    } else { // We don't have perm
                        if (!permissions.containsKey(p))
                            permissions.put(p, false);
                        // NOTE: This is the same as above but can't be methodized due to returns and continues
                        if (!arg.isContinueIfMissing()) { // And if we don't want to continue
                            if (!arg.isOptional()) { // And if this wasn't optional, tell the person
                                // TODO Output better missing perms error
                                sender.sendMessage("Missing permission " + p + " so you cant bro");
                                return false;
                            }
                            return true; // But if it was optional, just say we can run
                        } else { // But if we do want to continue
                            continue argcheck; // Check the next arg
                        }
                        // END NOTE
                    }
                }
            }*/

            // Okay, everything checks out and we can use this. Let's process the argument and move on
            textArgs = arg.processArgs(textArgs);
        }

        return true;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void sendMessage(String message) {
        // TODO Output better missing args error
        try {
            manager.sendMessage(message, event);
        } catch (DiscordException | MissingPermissionsException | RateLimitException e) {
            e.printStackTrace();
        }
    }

    public void sendEmbedMessage(String message, EmbedObject obj) {
        // TODO Output better missing args error
        try {
            manager.sendEmbedMessage(message, event, obj);
        } catch (DiscordException | MissingPermissionsException | RateLimitException e) {
            e.printStackTrace();
        }
    }

    public void deleteMessage() {
        try {
            event.getMessage().delete();
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            e.printStackTrace();
        }
    }
}
