package info.cameronlund.ebot.commands.arguments;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IUser;

public class UserArg extends SingleCommandArg<IUser> {
    private IDiscordClient client;

    public UserArg(IDiscordClient client) {
        this.client = client;
    }

    @Override
    public void processArgs(String string) {
        System.out.println("Before: " + string + " After: " + string.substring(3, string.length() - 1));
        if(string.charAt(2) == '!')
            setResult(client.getUserByID(string.substring(3, string.length() - 1)));
        else
            setResult(client.getUserByID(string.substring(2, string.length() - 1)));
    }

    @Override
    public boolean hasValidInput(String string) {
        System.out.println(string);
        if (!(string.startsWith("<@") && string.endsWith(">")))
            return false;
        return true;
    }
}
