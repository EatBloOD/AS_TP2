package rest;

import javax.ejb.EJB;
import javax.ejb.PostActivate;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.print.attribute.standard.Media;
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
import java.util.List;

@Path("/1.0")
public class WebService {
    // TODO: Implementar o web service GET && POST (Login + Orders)
    //
    // LOGIN:
    // Desserializar os dados Login fazer a query à DB
    //
    // ORDERS:
    // Averiguar de que order se trata e fazer o devido Log

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
    @Path("listAllOrders")
    public Response listAllOrders(){
        
        TypedQuery<Order> query = em.createNamedQuery("Order.findAll", Order.class);

        Gson gson = new Gson();
        String json  = gson.toJson(query);

        return Response.ok(json, MediaType.APPLICATION_JSON).build();

    }

    @POST
    @Path("newOrder")
    @Consumes("application/json")
    public void newOrder(Order newOrder){
        System.out.println("Begin new order..." + newOrder.getClient().toString());
        System.out.println("Employee:" + newOrder.getEmploye().toString());


        newOrder.getEmploye().addOrder(newOrder);
        
        em.getTransaction().begin();
        em.persist(newOrder.getClient());
        em.persist(newOrder);


        for (Item i : newOrder.getItems()) {
            i.setOrder(newOrder);
            i.getId().setOrders_idOrders(newOrder.getIdOrders());
            i.getProduct().setProducts_Stock(i.getProduct().getProducts_Stock() + i.getQuantityVariation());
        }


        em.merge(newOrder);
        em.getTransaction().commit();

    }

    

}
