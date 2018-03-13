package pt.uc.dei.as.loggerbeans;

import javax.ejb.Stateless;
import java.io.Serializable;

@Stateless
public class EPELogger implements IEPELogger, Serializable {

    private static final long serialVersionUID = 1L;

    private final int loginAction = 1;
    private final int newOrderAction = 2;
    private final int shippingAction = 3;

    @Override
    public int getLoginAction() {
        return loginAction;
    }

    @Override
    public int getNewOrderAction() {
        return newOrderAction;
    }

    @Override
    public int getShippingAction() {
        return shippingAction;
    }

    @Override
    public void info(String infoMessage, int id) {
        Utils.getLogger(id).info(infoMessage);
    }

    @Override
    public void warning(String warningMessage, int id) {
        Utils.getLogger(id).warn(warningMessage);
    }

    @Override
    public void error(String errorMessage, int id) {
        Utils.getLogger(id).error(errorMessage);
    }
    

}
