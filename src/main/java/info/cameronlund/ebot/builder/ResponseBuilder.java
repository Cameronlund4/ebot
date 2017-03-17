package info.cameronlund.ebot.builder;

import sx.blah.discord.handle.obj.IUser;

public class ResponseBuilder {
    private String message = "";

    public ResponseBuilder withText(String message) {
        this.message += message;
        return this;
    }

    public ResponseBuilder bold() {
        this.message += "**";
        return this;
    }

    public ResponseBuilder italic() {
        this.message += "*";
        return this;
    }

    public ResponseBuilder strike() {
        this.message += "~~";
        return this;
    }

    public ResponseBuilder codeblock() {
        this.message += "```";
        return this;
    }

    public ResponseBuilder mention(IUser user) {
        this.message += user.mention();
        return this;
    }

    public String build() {
        return message;
    }
}
