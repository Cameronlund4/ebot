package info.cameronlund.ebot;

import info.cameronlund.ebot.commands.BackwardsCommand;
import info.cameronlund.ebot.commands.BadWordCommand;
import info.cameronlund.ebot.commands.RyanCommand;
import info.cameronlund.ebot.commands.TestCommand;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.util.DiscordException;

public class Main {
    public static void main(String[] args) {
        IDiscordClient client = createClient("MjkxNzI5MTA4MDg0MTk1MzM4.C6txGw.Q5MXj3_AUfkBuiOaYD0LRVoVq40",
                true);
        EventDispatcher dispatcher = client.getDispatcher(); // Gets the EventDispatcher instance for this client instance
        new DerpCommandModule().enable(client);
        CommandHandler handler = new CommandHandler();
        handler.addCommand("boldtest",new TestCommand("boldtest"));
        handler.addCommand("ryan", new RyanCommand("ryan"));
        handler.addCommand("backwards", new BackwardsCommand("backwards"));
        handler.addCommand("badword", new BadWordCommand("badword"));
        dispatcher.registerListener(handler); // Registers the IListener example class from above
    }

    public static IDiscordClient createClient(String token, boolean login) { // Returns a new instance of the Discord client
        ClientBuilder clientBuilder = new ClientBuilder(); // Creates the ClientBuilder instance
        clientBuilder.withToken(token); // Adds the login info to the builder
        try {
            if (login) {
                return clientBuilder.login(); // Creates the client instance and logs the client in
            } else {
                return clientBuilder.build(); // Creates the client instance but it doesn't log the client in yet, you would have to call client.login() yourself
            }
        } catch (DiscordException e) { // This is thrown if there was a problem building the client
            e.printStackTrace();
            return null;
        }
    }
}
