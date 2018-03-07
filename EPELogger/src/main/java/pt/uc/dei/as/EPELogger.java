package pt.uc.dei.as;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static pt.uc.dei.as.Util.*;

public class EPELogger {

    private static EPELogger loggerSingleton = null;

    private Date date = new Date();
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private EPELogger() {
        initFiles();
    }

    public static EPELogger getInstance() {
        if (loggerSingleton == null)
            loggerSingleton = new EPELogger();
        return loggerSingleton;
    }

    private void initFiles() {
        FileWriter out = null;
        try {
            out = openFileWriter(AUTHENTICATIONS_RECORD_FILE);
            out.write(String.format(AUTHENTICATIONS_FILE_FORMAT,
                    "TIME:", "USERNAME:", "LOGIN(true)/LOGOUT(false):"));
            out.flush();
            out.close();
            out = openFileWriter(ORDERS_RECORD_FILE);
            out.write(String.format(ORDERS_FILE_FORMAT,
                    "TIME:", "USERNAME:", "ORDER_ID:"));
            out.flush();
            out.close();
            out = openFileWriter(SHIPPINGS_RECORD_FILE);
            out.write(String.format(SHIPPINGS_FILE_FORMAT,
                    "TIME:", "USERNAME:", "ORDER_NAME:", "ITEM_NAMES:"));
            out.flush();
            out.close();
        } catch (NullPointerException | IOException ex) {
            ex.printStackTrace();
        }
    }

    private FileWriter openFileWriter(String fileName) {
        FileWriter out = null;
        try {
            File file = new File(fileName);
            out = new FileWriter(file, true);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return out;
    }

    public void logAuthentication(String username, boolean isLogged) {
        FileWriter out = openFileWriter(AUTHENTICATIONS_RECORD_FILE);
        if (out != null) {
            try {
                out.write(String.format(AUTHENTICATIONS_FILE_FORMAT,
                        sdf.format(date.getTime()), username, isLogged));
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void logOrder(String username, int orderId) {
        FileWriter out = openFileWriter(ORDERS_RECORD_FILE);
        if (out != null) {
            try {
                out.write(String.format(ORDERS_FILE_FORMAT,
                        sdf.format(date.getTime()), username, orderId));
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // TODO: Adicionar outros parametros ao método consoante o necessário!!!
    public void logShipping(String username, String shippingName) {
        FileWriter out = openFileWriter(SHIPPINGS_RECORD_FILE);
        if (out != null) {
            try {
                out.write(String.format(SHIPPINGS_FILE_FORMAT,
                        sdf.format(date.getTime()), username, shippingName));
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
