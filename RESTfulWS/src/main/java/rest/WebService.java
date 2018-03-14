package rest;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.*;
import javax.ws.rs.core.MediaType;
import pt.uc.dei.as.loggerbeans.IEPELogger;
import com.google.gson.Gson;
import data.*;
import entity.*;

@Path("/1.0")
public class WebService {
    
    private EntityManager em;

    @EJB
    IEPELogger remoteEPELogger;

    public WebService() {
        PersistenceManager pm = new PersistenceManager();
        em = pm.getEntityManager();

        System.out.println("WebService created");
    }

    /*
     *  This method is only for testing purposes
     * url: http://localhost:8080/RESTfulWS/rest/1.0/helloWorld
     * */
    @GET
    @Path("/helloWorld")
    public String helloWorld() {
        return "<h1>Hello World</h1>";
    }

    @POST
    @Path("/login")
    @Consumes("application/json")
    public Response Login(Login login) {

        TypedQuery<Employer> queryC = em.createNamedQuery("Employers.findEmployer", Employer.class);
        Employer e;
        queryC.setParameter("employers_Name", login.getUsername());

        try {
            e = queryC.getSingleResult();

            if (e.getEmployers_Password().equals(login.getPassword())) {

                Gson gson = new Gson();
                
                e.setEmployers_Password(null);

                String json = gson.toJson(e);

                System.out.println(json);
                
                remoteEPELogger.loginInfo(e.getEmployers_Name(), 0);

                return Response.ok(json, MediaType.APPLICATION_JSON).build();
                
            } else {
                
                return Response.status(400).build();
            }
        } catch (NoResultException nre) {
            
            return Response.status(404).build();
        }
    }

    @POST
    @Path("newOrder")
    @Consumes("application/json")
    public Response newOrder(Order newOrder){
        
        remoteEPELogger.orderInfo(newOrder.getEmploye().getEmployers_Name(), newOrder.getIdOrders());

        return Response.ok().build();

    }

    @POST
    @Path("orderShipped")
    @Consumes("application/json")
    public Response orderShipped(Order order){
        remoteEPELogger.shippingInfo(order.getIdOrders(), ((Byte) order.getOrders_Shipped()).intValue());
        
        return Response.ok().build();
    }

    

}
