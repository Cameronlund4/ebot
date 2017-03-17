package info.cameronlund.ebot.commands.arguments;

public abstract class SingleCommandArg<T> extends CommandArg<T> {
    @Override
    public String[] processArgs(String[] inputArgs) {
        processArgs(inputArgs[0]);
        return removeStartArgs(inputArgs,0);
    }

    public abstract void processArgs(String string);

    @Override
    public boolean hasValidInput(String[] inputArgs) {
        if (inputArgs.length < 1)
            return false;
        return hasValidInput(inputArgs[0]);
    }

    public abstract boolean hasValidInput(String string);
}
