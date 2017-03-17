package info.cameronlund.ebot;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by MichaelRyan on 3/16/17.
 */
public class HttpsRequest {

    private URL url;

    public HttpsRequest(String url) {
        try {
            this.url = new URL(url);
        } catch(MalformedURLException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public String getRawResponse() {
        try {
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            BufferedReader br =
                    new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));

            String input, output = "";

            while ((input = br.readLine()) != null)
                output += input;

            br.close();
            conn.getInputStream().close();
            return output;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
