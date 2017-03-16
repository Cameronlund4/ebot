package info.cameronlund.ebot;

import com.sun.org.apache.xalan.internal.xsltc.runtime.MessageHandler;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.modules.IModule;

public class DerpCommandModule implements IModule {
    public static IDiscordClient client;

    public boolean enable(IDiscordClient dclient) {
        client = dclient;
        EventDispatcher dispatcher = client.getDispatcher();
        dispatcher.registerListener(new MessageHandler());
        return true;
    }

    public void disable() {

    }

    public String getName() {
        return null;
    }

    public String getAuthor() {
        return null;
    }

    public String getVersion() {
        return null;
    }

    public String getMinimumDiscord4JVersion() {
        return null;
    }
}
