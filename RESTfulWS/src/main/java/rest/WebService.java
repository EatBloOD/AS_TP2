package rest;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

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

    /*@EJB
    IEPELogger remoteLoggingEJB;
	*/

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
}
