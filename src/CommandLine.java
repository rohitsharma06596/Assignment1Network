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
            try {
                DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                Date dateObj = new Date();
                byte[] b = new byte[1024];
                System.out.print(df.format(dateObj)+" LocalComp-HTTPCPrompt:~ ");
                for (int r; (r = System.in.read(b)) != -1; ) {
                    String buffer = new String(b, 0, r);
                    parse(buffer);
                    System.out.println("read: " + buffer);
                    DateFormat dfnew = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                    Date dateObjnew = new Date();
                    System.out.println("\n");
                    System.out.print(dfnew.format(dateObjnew)+" LocalComp-HTTPCPrompt:~ ");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

}
