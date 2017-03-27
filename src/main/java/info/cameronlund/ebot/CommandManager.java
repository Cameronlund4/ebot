package info.cameronlund.ebot;

import info.cameronlund.ebot.commands.CommandCall;
import info.cameronlund.ebot.commands.SmartCommand;
import info.cameronlund.ebot.commands.SmartCommandable;
import info.cameronlund.ebot.commands.arguments.NamedArg;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private IDiscordClient client;
    private HashMap<String, SmartCommand> commands = new HashMap<>();
    private PermManager pmanager;

    public CommandManager(IDiscordClient client, PermManager pmanager) {
        this.client = client;
        this.pmanager = pmanager;
    }

    @EventSubscriber
    public void OnMesageEvent(MessageReceivedEvent event) throws DiscordException, MissingPermissionsException, RateLimitException {
        IMessage message = event.getMessage(); // Get the message
        if(!pmanager.isMuted(message.getAuthor())){
            for (Map.Entry<String, SmartCommand> set : commands.entrySet()) {
                if (message.getContent().toLowerCase().startsWith("!"+set.getKey().toLowerCase())) {
                    String[] argsL = message.getContent().split("\\s");
                    String[] args = new String[argsL.length-1];
                    System.arraycopy(argsL, 1, args, 0, argsL.length - 1);
                    set.getValue().onCommand(this, event, event.getMessage().getAuthor(),set.getKey(),set.getKey(), args);
                }
            }
        } else {
            message.delete();
        }
    }

    public void addCommand(String command, SmartCommand smartCommand) {
        commands.put(command, smartCommand);
    }

    public void addCommand(String command, SmartCommandable commandable, NamedArg... args) {
        commands.put(command, new SmartCommand(command, commandable, args) {
            @Override
            public void onCommand(CommandCall command) {
                // This is ignored
            }
        });
    }

    public void sendMessage(String message, MessageReceivedEvent event) throws DiscordException, MissingPermissionsException, RateLimitException {
        new MessageBuilder(client).appendContent(message).withChannel(event.getMessage().getChannel()).build();
    }

    public void sendEmbedMessage(String message, MessageReceivedEvent event, EmbedObject obj) throws DiscordException, MissingPermissionsException, RateLimitException {
        new MessageBuilder(client).appendContent(message).withChannel(event.getMessage().getChannel()).withEmbed(obj).build();
    }
}
