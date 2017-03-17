package info.cameronlund.ebot.commands.implementations;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import info.cameronlund.ebot.HttpsRequest;
import info.cameronlund.ebot.commands.CommandCall;
import info.cameronlund.ebot.commands.SmartCommand;
import info.cameronlund.ebot.commands.arguments.LangArg;
import info.cameronlund.ebot.commands.arguments.StringArg;

//Super Secret Api key: trnsl.1.1.20170316T223724Z.8238214aa838c9dc.681e23d2bdb0abd2b0d2d9e52b10f212bc257a39
public class TranslateCommand extends SmartCommand {

    public TranslateCommand(String command) {
        super(command);
        this.addArgument("to", new LangArg());
        this.addArgument("from", new LangArg()).setOptional(true).continueIfMissing(true);
        this.addArgument("message", new StringArg());
    }

    public void onCommand(CommandCall call) {
        String message = (String) getArg("message");
        boolean withFrom = hasArg("from");
        if (withFrom)
            message = translate((String) getArg("to"),(String) getArg("from"),message);
        else
            message = translate((String) getArg("to"),message);
        call.sendMessage(message);
    }

    private String translate(String to, String from, String str) {
        HttpsRequest req = new HttpsRequest("https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1." +
                "20170316T223724Z.8238214aa838c9dc.681e23d2bdb0abd2b0d2d9e52b10f212bc257a39&text=" + str + "&lang=" + from + "-" + to);
        return getResponseText(req);
    }

    private String translate(String to, String str) {
        HttpsRequest req = new HttpsRequest("https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1." +
                "20170316T223724Z.8238214aa838c9dc.681e23d2bdb0abd2b0d2d9e52b10f212bc257a39&text=" + str + "&lang=" + to);
        return getResponseText(req);
    }

    private String getResponseText(HttpsRequest req) {
        JsonObject res = new JsonParser().parse(req.getRawResponse()).getAsJsonObject();
        return res.getAsJsonArray("text").get(0).getAsJsonPrimitive().getAsString();
    }
}
