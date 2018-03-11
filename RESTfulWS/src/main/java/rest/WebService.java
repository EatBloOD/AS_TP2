package rest;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import rest.PersistenceManager;



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
	
    public WebService()
    {
        PersistenceManager pm = new PersistenceManager();
        em = pm.getEntityManager();

        System.out.println("WebService created");
    }
    
    @POST
    @Path("/helloWorld")
    @Consumes("application/json")
    public Response helloWorld()
    {
    	
        return Response.status(401).build();
    } 

	 
}
