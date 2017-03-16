package info.cameronlund.ebot.commands.arguments;

import java.security.Permission;
import java.util.ArrayList;

// TODO Javadoc
public abstract class CommandArg<T> {
    private T result;
    private ArrayList<Permission> permissions;
    private boolean optional = false;
    private boolean continueIfMissing = false;

    public abstract String[] processArgs(String[] inputArgs);

    public abstract boolean hasValidInput(String[] inputArgs);

    public ArrayList<Permission> getPermissions() {
        return permissions;
    }

    public boolean hasPermissions() {
        return permissions != null;
    }

    public T getResult() {
        return result;
    }

    public boolean hasResult() {
        return result != null;
    }

    public CommandArg withPermission(Permission perm) {
        setOptional(true); // Permission specific arguments should always be optional
        if (permissions == null)
            permissions = new ArrayList<Permission>();
        permissions.add(perm);
        return this; // For a builder type layout
    }

    protected void setResult(T result) {
        this.result = result;
    }

    public CommandArg setOptional(boolean optional) {
        this.optional = optional;
        if (!optional)
            continueIfMissing = false;
        return this; // For a builder type layout
    }

    public boolean isOptional() {
        return optional;
    }

    public boolean isContinueIfMissing() {
        return continueIfMissing;
    }

    public CommandArg continueIfMissing(boolean continueIfMissing) {
        this.continueIfMissing = continueIfMissing;
        return this; // For a builder type layout
    }

    protected String[] removeArgs(String[] input, int start, int end) {
        String[] output = new String[input.length-(end-start)-1]; // How big we'll be after chopping
        int pos = 0;
        for (int i = 0; i < start; i ++) // Add before start
            output[pos++] = input[i];
        for (int i = end+1; i < input.length; i ++) // Add after end
            output[pos++] = input[i];
        return output;
    }

    protected String[] removeStartArgs(String[] input, int end) {
        return removeArgs(input, 0, end);
    }

    protected String[] removeEndArgs(String[] input, int start) {
        return removeArgs(input, start, input.length-1);
    }
}
