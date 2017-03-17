package info.cameronlund.ebot.commands.arguments;

import sx.blah.discord.handle.obj.IUser;

public class UserArg extends CommandArg<IUser>{

    @Override
    public String[] processArgs(String[] inputArgs) {
        return new String[0];
    }

    @Override
    public boolean hasValidInput(String[] inputArgs) {
        return false;
    }
}
