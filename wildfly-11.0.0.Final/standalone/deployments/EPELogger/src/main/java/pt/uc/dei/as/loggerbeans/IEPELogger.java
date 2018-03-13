package pt.uc.dei.as.loggerbeans;

import javax.ejb.Remote;

@Remote
public interface IEPELogger {

    int getLoginAction();

    int getNewOrderAction();

    int getShippingAction();

    void info(String infoMessage, int id);

    void warning(String warningMessage, int id);

    void error(String errorMessage, int id);
}
