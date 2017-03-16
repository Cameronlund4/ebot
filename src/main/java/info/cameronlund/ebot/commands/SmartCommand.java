package info.cameronlund.ebot.commands;

import info.cameronlund.ebot.CommandHandler;
import info.cameronlund.ebot.commands.arguments.CommandArg;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;

import java.util.LinkedHashMap;

// TODO Add in aliases
// TODO Set descriptions and stuff
// TODO Javadoc
public abstract class SmartCommand {
    private boolean enabled = false;
    private String command;
    private String description;
    private LinkedHashMap<String, CommandArg> smartArgs = new LinkedHashMap<String, CommandArg>();

    public SmartCommand(String command) {
        this.command = command;
    }

    public CommandArg addArgument(String name, CommandArg arg) {
        smartArgs.put(name, arg);
        return smartArgs.get(name); // For a builder type interaction
    }

    public abstract void onCommand(CommandCall command);

    public boolean onCommand(CommandHandler handler, MessageReceivedEvent event, IUser sender, String cmd, String alias, String[] args) {
        CommandCall call = new CommandCall(handler, event, sender, cmd, alias, args, smartArgs);

        // Alright, we want to call this. Process args.
        boolean argSuccess = call.processArgs(); // Check to make sure we're good on args
        if (!argSuccess) // If args didn't process successfully
            return true; // Just return. Arg processing should have messaged the sender

        onCommand(call); // Run the command, we're ready
        return true;
    }

    public String getCommand() {
        return command;
    }

    public boolean isEnabled() {
        return enabled;
    }

    protected void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CommandArg getArgObject(String arg) {
        return smartArgs.get(arg);
    }

    public boolean hasArg(String arg) {
        return getArgObject(arg).hasResult();
    }

    public Object getArg(String arg) {
        return getArgObject(arg).getResult();
    }
}
