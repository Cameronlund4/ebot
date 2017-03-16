package info.cameronlund.ebot.commands.arguments;

/**
 * Created by MichaelRyan on 3/16/17.
 */

public class LangArg extends CommandArg<String> {

    private String[] codes;

    public String[] processArgs(String[] inputArgs) {
        // Assume we have valid input since this is getting called
        String result = "";
        for (String part : inputArgs)
            result += " "+(part.replaceAll("/", ""));
        result = result.trim(); // Get rid of leading space
        setResult(result);

        return new String[0];
    }

    public boolean hasValidInput(String[] inputArgs) {
        return inputArgs.length == 2;
    }
}