package pt.uc.dei.as.loggerbeans;

import javax.ejb.Remote;

@Remote
public interface IEPELogger {


    void loginInfo(String username, int action);

    void orderInfo(String employer, int idOrder);

    void shippingInfo(int idOrder, int shippingState);

}
