package pt.uc.dei.as.loggerbeans;

import javax.ejb.Stateless;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Stateless
public class EPELogger implements IEPELogger, Serializable {

    private static final long serialVersionUID = 1L;

    private Date date = new Date();
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final String AUTHENTICATIONS_RECORD_FILE = "01_authentication_records";
    private static final String ORDERS_RECORD_FILE = "02_new_orders_operations_records";
    private static final String SHIPPING_RECORD_FILE = "03_shipping_records";

    private static final String AUTHENTICATIONS_FILE_FORMAT = "DATE: %30s USER: %20s ACTION: %10s%n";
    private static final String ORDERS_FILE_FORMAT = "DATE: %30s EMPLOYER: %20s ORDER_ID: %10s%n";
    private static final String SHIPPING_FILE_FORMAT = "DATE: %30s ORDER_ID: %10s SHIPPING_STATE: %20s%n";

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

    @Override
    public void loginInfo(String username, int action) {
        FileWriter out = openFileWriter(AUTHENTICATIONS_RECORD_FILE);
        if (out != null) {
            try {
                out.write(String.format(AUTHENTICATIONS_FILE_FORMAT,
                        sdf.format(date.getTime()), username, action == 0 ? "LOGIN" : "LOGOUT"));
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void orderInfo(String employer, int idOrder) {
        FileWriter out = openFileWriter(ORDERS_RECORD_FILE);
        if (out != null) {
            try {
                out.write(String.format(ORDERS_FILE_FORMAT,
                        sdf.format(date.getTime()), employer, idOrder));
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void shippingInfo(int idOrder, int shippingState) {
        FileWriter out = openFileWriter(SHIPPING_RECORD_FILE);
        if (out != null) {
            try {
                out.write(String.format(SHIPPING_FILE_FORMAT,
                        sdf.format(date.getTime()), idOrder, shippingState == 0 ? "NOT_SHIPPED" : "SHIPPED"));
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
