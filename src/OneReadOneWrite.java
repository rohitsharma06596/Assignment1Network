import java.io.IOException;
import java.util.HashMap;

public class OneReadOneWrite extends Thread {
    OneReadOneWrite() throws IOException {
        HashMap<String, String> parsedData = new HashMap<String, String>();
        parsedData.put("protocol", "httpfs");
        parsedData.put("request", "fileread");
        parsedData.put("URL", "http://localhost:8080");
        parsedData.put("file", "TestFile2");
        HTTPCClient instance1 = new HTTPCClient(parsedData);
        System.out.println("message" + "This is a test to the one read one write concurrency test written by first user");
        instance1.FILEGETRequest();
        Thread t = new Thread(this);
        t.start();



    }
    public void run(){
        HashMap<String, String> parsedData = new HashMap<String, String>();
        parsedData.put("protocol", "httpfs");
        parsedData.put("request", "filepost");
        parsedData.put("URL", "http://localhost:8080");
        parsedData.put("file", "TestFile2");
        parsedData.put("message", "This is a test to the one read one write concurrency test written by second user");
        HTTPCClient instance2 = new HTTPCClient(parsedData);
        try {
            instance2.FILEPOSTRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
