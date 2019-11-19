import java.io.IOException;
import java.util.HashMap;

public class DoubleWriterTest extends Thread {

   DoubleWriterTest() throws IOException {
        HashMap<String, String> parsedData = new HashMap<String, String>();
        parsedData.put("protocol", "httpfs");
        parsedData.put("request", "filepost");
        parsedData.put("URL", "http://localhost:8080");
        parsedData.put("file", "TestFile2");
        parsedData.put("message", "This is a test to the double write concurrency test written by first user");
        HTTPCClient instance1 = new HTTPCClient(parsedData);
        instance1.FILEPOSTRequest();
        Thread t = new Thread(this);
        t.start();



    }
    public void run(){
        HashMap<String, String> parsedData = new HashMap<String, String>();
        parsedData.put("protocol", "httpfs");
        parsedData.put("request", "filepost");
        parsedData.put("URL", "http://localhost:8080");
        parsedData.put("file", "TestFile2");
        parsedData.put("message", "This is a test to the double write concurrency test written by second user");
        HTTPCClient instance2 = new HTTPCClient(parsedData);
        try {
            instance2.FILEPOSTRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
