package info.cameronlund.ebot.commands.arguments;

public class TeamArg extends CommandArg<String> {

    public String[] processArgs(String[] inputArgs) {
        String team = inputArgs[0].toUpperCase().trim();
        setResult(team);
        return removeStartArgs(inputArgs,0);
    }

    public boolean hasValidInput(String[] inputArgs) {
        if(inputArgs.length>0){
            int numLetters = 0;
            for(int i = 0; i < inputArgs[0].length(); i++)
                if(inputArgs[0].toLowerCase().charAt(i) >= 97 && inputArgs[0].toLowerCase().charAt(i) >= 122)
                    numLetters++;
            return (numLetters < 2 && inputArgs[0].length()<=7);
        }
        return false;
    }

}
