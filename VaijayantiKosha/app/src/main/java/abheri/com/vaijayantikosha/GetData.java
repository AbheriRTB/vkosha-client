package abheri.com.vaijayantikosha;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetData implements Runnable {


    String ppadam_text = "";
    String returnStr = "";
    String errorStr = "";
    int statusCode=400;

    public GetData(String ppadam) {
        ppadam_text = ppadam;
    }

    @Override
    public void run() {
        try {
            HttpURLConnection urlConnection;
            BufferedInputStream inputStream;

            /* forming th java.net.URL object */
            /* http://abheri.pythonanywhere.com/vkosha/findword/देव */
            String uri = "http://abheri.pythonanywhere.com/vkosha/findword/" + ppadam_text;
            URL url = new URL(uri);
            urlConnection = (HttpURLConnection) url.openConnection();

            /* optional request header */
            //urlConnection.setRequestProperty("Content-Type", "application/json");

            /* optional request header */
            //urlConnection.setRequestProperty("Accept", "application/json");

            /* for Get request */
            urlConnection.setRequestMethod("GET");
            statusCode = urlConnection.getResponseCode();
            System.out.println(statusCode);
            /* 200 represents HTTP OK */
            if (statusCode == 200) {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                returnStr = convertInputStreamToString(inputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorStr = e.toString();
        }

    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        /* Close Stream */
        if (null != inputStream) {
            inputStream.close();
        }
        return result;
    }

    public String getReturnStr(){
        return returnStr;
    }

    public String getErrorStr(){
        return errorStr;
    }

    public int getStatusCode(){
        return statusCode;
    }

}
