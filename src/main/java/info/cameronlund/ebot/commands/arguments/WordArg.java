package info.cameronlund.ebot.commands.arguments;

public class WordArg extends SingleCommandArg<String> {
    @Override
    public void processArgs(String arg) {
        // Assume we have valid input since this is getting called
        String result = (arg.replaceAll("/", ""));
        result = result.trim();
        setResult(result);
    }

    @Override
    public boolean hasValidInput(String string) {
        return true; // Super already checks for arg
    }
}
