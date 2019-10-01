import java.io.*;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class HTTPCClient {


    public static void main(String[] args) throws IOException {

        String url = "http://httpbin.org/get?course=networking&assignment=1";
        String url1 = "http://httpbin.org/post";
        GETRequest(url);
        POSTRequest(url1);
    }

    public static void GETRequest(String url) throws IOException {

        //Creating connection and request for httpc

        URL urlForGetRequest = new URL(url);
        String readLine = null;
        HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
        connection.setRequestMethod("GET");

        //Setting request header

        //connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("accept", "application/json");
        //String contentType = connection.getHeaderField("Content-Type");


        //Adding query parameters
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("q", "world");
        connection.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
        out.flush();
        out.close();



        //Configuring time outs (Extra property)

        connection.setConnectTimeout(5000000);
        connection.setReadTimeout(5000000);



        //Reading the response code

        try {
            int responseCode = connection.getResponseCode();

            //verbose option
            StringBuffer response = new StringBuffer();
            connection.getHeaderFields().entrySet().stream()
                    .filter(entry -> entry.getKey() != null)
                    .forEach(entry -> {
                        response.append(entry.getKey()).append(": ");
                        List headerValues = entry.getValue();
                        Iterator it = headerValues.iterator();
                        if (it.hasNext()) {
                            response.append(it.next());
                            while (it.hasNext()) {
                                response.append(", ").append(it.next());
                            }
                        }
                        response.append("\n");
                    });

            response.append("\n");

            //connection output
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                while ((readLine = in.readLine()) != null) {
                    response.append(readLine);
                    response.append("\n");
                }
                in.close();
                // print result
                System.out.println(response.toString());
            } else {
                System.out.println("GET NOT WORKED");
            }
        }
        catch (Exception e){
            System.out.println("CONNECTION TIMED OUT");
        }
        connection.disconnect();
    }


    public static void POSTRequest(String url) throws IOException {

//        final String POST_PARAMS = "{\n" + "\"userId\": 101,\r\n" +
//                "    \"id\": 101,\r\n" +
//                "    \"title\": \"Test Title\",\r\n" +
//                "    \"body\": \"Test Body\"" + "\n}";
        final String POST_PARAMS = "{\n" + "\"assignment\": 1"  + "\n}";
        System.out.println(POST_PARAMS);
        URL obj = new URL(url);
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("POST");
        //postConnection.setRequestProperty("userId", "a1bcdefgh");
        postConnection.setRequestProperty("Content-Type", "application/json");
        postConnection.setDoOutput(true);
        OutputStream os = postConnection.getOutputStream();
        os.write(POST_PARAMS.getBytes());
        os.flush();
        os.close();
        int responseCode = postConnection.getResponseCode();

        //Configuring time outs (Extra property)

        postConnection.setConnectTimeout(5000000);
        postConnection.setReadTimeout(5000000);

        //verbose option
        try {
            StringBuffer response = new StringBuffer();
            postConnection.getHeaderFields().entrySet().stream()
                    .filter(entry -> entry.getKey() != null)
                    .forEach(entry -> {
                        response.append(entry.getKey()).append(": ");
                        List headerValues = entry.getValue();
                        Iterator it = headerValues.iterator();
                        if (it.hasNext()) {
                            response.append(it.next());
                            while (it.hasNext()) {
                                response.append(", ").append(it.next());
                            }
                        }
                        response.append("\n");
                    });
            System.out.println("POST Response Code :  " + responseCode);
            System.out.println("POST Response Message : " + postConnection.getResponseMessage());
            if (responseCode == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        postConnection.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                    response.append("\n");
                }
                in.close();
                // print result
                System.out.println(response.toString());
            } else {
                System.out.println("POST NOT WORKED");
            }
        }
        catch(Exception e){
            System.out.println("CONNECTION TIMED OUT");
        }
    }
}
