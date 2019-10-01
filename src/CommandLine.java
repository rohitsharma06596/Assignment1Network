import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static java.sql.Types.NULL;

public class CommandLine {

    public static void parse(String buffer){

    }

    public static void main(String args[]){
        String temp = "Default";
        while(true) {
            try {
                DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                Date dateObj = new Date();
                System.out.println("\n");
                System.out.print(df.format(dateObj)+" LocalComp-HTTPCPrompt:~ ");
                byte[] b = new byte[1024];
                for (int r; (r = System.in.read(b)) != -1; ) {
                    String buffer = new String(b, 0, r);
                    System.out.println("read: " + buffer);
                    parse(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
