package info.cameronlund.ebot.commands.arguments;

public class NamedArg {
    private CommandArg arg;
    private String name;

    public NamedArg(String name, CommandArg arg) {
        this.arg = arg;
        this.name = name;
    }

    public CommandArg getArg() {
        return arg;
    }

    public String getName() {
        return name;
    }
}
