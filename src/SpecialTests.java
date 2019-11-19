import java.io.IOException;

public class SpecialTests {
    public static void main(String args[]) throws IOException {
        DoubleWriterTest instance1 = new DoubleWriterTest();
        System.out.println("**********************************************************************************************************************************************");
        OneReadOneWrite instance2 = new OneReadOneWrite();
        System.out.println("**********************************************************************************************************************************************");
        DoubleReadTest instance3 = new DoubleReadTest();
    }
}
