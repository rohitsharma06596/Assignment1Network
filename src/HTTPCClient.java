import java.io.*;
import java.net.HttpURLConnection;
import java.net.SocketAddress;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class HTTPCClient {

    // readFully reads until the request is fulfilled or the socket is closed
    private static void readFully(SocketChannel socket, ByteBuffer buf, int size) throws IOException {
        while (buf.position() < size) {
            int n = socket.read(buf);
            if (n == -1) {
                break;
            }
        }
        if (buf.position() != size) {
            throw new EOFException();
        }
    }

    private static void readEchoAndRepeat(SocketChannel socket) throws IOException {
        Charset utf8 = StandardCharsets.UTF_8;
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            ByteBuffer buf = utf8.encode(line);
            int n = socket.write(buf);
            buf.clear();

            // Receive all what we have sent
            readFully(socket, buf, n);
            buf.flip();
            System.out.println("Replied: " + utf8.decode(buf));
        }
    }

    private static void runClient(SocketAddress endpoint) throws IOException {
        try (SocketChannel socket = SocketChannel.open()) {
            socket.connect(endpoint);
            System.out.println("Type any thing then ENTER. Press Ctrl+C to terminate");
            readEchoAndRepeat(socket);
        }
    }

    public static void main(String[] args) throws IOException {
//        OptionParser parser = new OptionParser();
//        parser.acceptsAll(asList("host", "h"), "EchoServer hostname")
//                .withOptionalArg()
//                .defaultsTo("localhost");
//
//        parser.acceptsAll(asList("port", "p"), "EchoServer listening port")
//                .withOptionalArg()
//                .defaultsTo("8007");
//
//        OptionSet opts = parser.parse(args);
//
//        String host = (String) opts.valueOf("host");
//        int port = Integer.parseInt((String) opts.valueOf("port"));
//
//        SocketAddress endpoint = new InetSocketAddress(host, port);
//        runClient(endpoint);
        //MyGETRequest();
        MyPOSTRequest();
    }

    public static void MyGETRequest() throws IOException {
        URL urlForGetRequest = new URL("http://httpbin.org/get?course=networking&assignment=1");
        String readLine = null;
        HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
        conection.setRequestMethod("GET");
        conection.setRequestProperty("userId", "a1bcdef"); // set userId its a sample here
        int responseCode = conection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in .readLine()) != null) {
                response.append(readLine);
            } in .close();
            // print result
            System.out.println("JSON String Result " + response.toString());
            //GetAndPost.POSTRequest(response.toString());
        } else {
            System.out.println("GET NOT WORKED");
        }
    }

    public static void MyPOSTRequest() throws IOException {
        final String POST_PARAMS = "{\n" + "\"userId\": 101,\r\n" +
                "    \"id\": 101,\r\n" +
                "    \"title\": \"Test Title\",\r\n" +
                "    \"body\": \"Test Body\"" + "\n}";
        System.out.println(POST_PARAMS);
        URL obj = new URL("http://httpbin.org/post");
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("userId", "a1bcdefgh");
        postConnection.setRequestProperty("Content-Type", "application/json");
        postConnection.setDoOutput(true);
        OutputStream os = postConnection.getOutputStream();
        os.write(POST_PARAMS.getBytes());
        os.flush();
        os.close();
        int responseCode = postConnection.getResponseCode();
        System.out.println("POST Response Code :  " + responseCode);
        System.out.println("POST Response Message : " + postConnection.getResponseMessage());
        if (responseCode == HttpURLConnection.HTTP_CREATED) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    postConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in .readLine()) != null) {
                response.append(inputLine);
            } in .close();
            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("POST NOT WORKED");
        }
    }
}
