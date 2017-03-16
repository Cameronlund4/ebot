package info.cameronlund.ebot.commands.arguments;

// Currently supports yes/no, true/false, t/f, 1/0
public class BooleanArg extends CommandArg<Boolean> {
    public String[] processArgs(String[] inputArgs) {
        String bool = inputArgs[0].toLowerCase();
        // If we contain one of the following, it means we're true
        setResult("t".equals(bool) || "true".equals(bool) || "yes".equals(bool) || "1".equals(bool));
        return removeStartArgs(inputArgs,0);
    }

    public boolean hasValidInput(String[] inputArgs) {
        if (inputArgs.length < 1)
            return false;
        String bool = inputArgs[0].toLowerCase();
        // If we contain any of the valid, input is valid
        return "t".equals(bool) || "f".equals(bool) ||
                "true".equals(bool) || "false".equals(bool) ||
                "yes".equals(bool) || "no".equals(bool) ||
                "1".equals(bool) || "0".equals(bool);
    }
}
