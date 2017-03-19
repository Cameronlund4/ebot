package info.cameronlund.ebot.commands;

public interface SmartCommandable {

    void onCommand(SmartCommand command, CommandCall call);

}
