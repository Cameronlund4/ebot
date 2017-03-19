package info.cameronlund.ebot.commands;

import info.cameronlund.ebot.CommandManager;
import info.cameronlund.ebot.commands.arguments.CommandArg;
import info.cameronlund.ebot.commands.arguments.NamedArg;
import info.cameronlund.ebot.commands.arguments.StringArg;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;

import java.awt.*;
import java.util.LinkedHashMap;

// TODO Add in aliases
// TODO Set descriptions and stuff
// TODO Javadoc
public abstract class SmartCommand implements SmartCommandable {
    private boolean enabled = false;
    private String command;
    private String description;
    private SmartCommandable commandable;
    private LinkedHashMap<String, CommandArg> smartArgs = new LinkedHashMap<String, CommandArg>();

    public SmartCommand(String command) {
        commandable = this;
        this.command = command;
    }

    public SmartCommand(String command, SmartCommandable commandable, NamedArg... args) {
        this.commandable = commandable;
        this.command = command;
        if (args.length == 0)
            addArgument("args", new StringArg().setOptional(true));
        else
            for (NamedArg arg : args) {
                addArgument(arg.getName(), arg.getArg());
            }
    }

    public CommandArg addArgument(String name, CommandArg arg) {
        smartArgs.put(name, arg);
        return smartArgs.get(name); // For a builder type interaction
    }

    public abstract void onCommand(CommandCall call);

    @Override
    public void onCommand(SmartCommand command, CommandCall call) {
        onCommand(call);
    }

    public boolean onCommand(CommandManager manager, MessageReceivedEvent event, IUser sender, String cmd, String alias, String[] args) {
        CommandCall call = new CommandCall(manager, event, sender, cmd, alias, args, smartArgs);

        // Alright, we want to call this. Process args.
        boolean argSuccess = call.processArgs(); // Check to make sure we're good on args

        if (!argSuccess) // If args didn't process successfully
            return true; // Just return. Arg processing should have messaged the sender

        try {
            commandable.onCommand(this, call); // Run the command, we're ready
        } catch (Exception e) {
            call.sendEmbedMessage("", new EmbedBuilder().withColor(Color.red)
                    .withImage("http://i.imgur.com/ytXXWJ1.png").withDescription("Uh oh, something went wrong...\n`" +
                            e.getMessage() + "`").build());
            e.printStackTrace();
        }
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
