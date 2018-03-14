package pt.uc.dei.as.loggerbeans;

import javax.ejb.Stateless;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Calendar;

@Stateless
public class EPELogger implements IEPELogger, Serializable {

    private static final long serialVersionUID = 1L;

	@Override
	public void loginInfo(String username, int action) {
        
	}


	@Override
	public void orderInfo(String employer, int idOrder) {
		
	}


	@Override
	public void shippingInfo(int idOrder, int shippingState) {
		
	}

    
    

}
