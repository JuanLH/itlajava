/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ws;

import dto.cliente;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

import java.sql.SQLException;
import com.google.gson.Gson;
import db.DB;
import  java.sql.ResultSet;
import dto.cliente;
import java.util.ArrayList;
import javafx.scene.media.Media;
import javax.print.attribute.standard.MediaTray;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Juan Luis Hiciano
 */
@Path("cliente")
public class Webservice_Cliente {

    String host="localhost";
    String db="itla";
    String user="adminm7xt8zn";
    String pass="JaQc2-sekn7A";
    ArrayList<cliente> list= new ArrayList<cliente>();
    Gson json = new Gson();
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of WebserviceClient
     */
    public Webservice_Cliente() {
    }

    /**
     * Retrieves representation of an instance of Ws.WebserviceClient
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of WebserviceClient
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
    
    
    /*Metodo que inserta un cliente*/
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void insertar_cliente(@PathParam ("info") String informacion ) throws Exception {
      
            cliente cliente = new cliente();
            
            cliente.insertar_cliente(informacion);
    }
    /*fin del metodo made by:José Aníbal Moronta Mejía*/
    
    
    @GET
    @Produces("application/json")
    @Path("/getcliente")
    public String getCliente() throws Exception
    {
          //instancie el objeto de DB
       DB dbase = new DB("itla2","itlajava","12345678@itla");
        
        String sql="Select * from t_cliente ;";
        try{
        ResultSet rs=dbase.execSelect(sql);
        while(rs.next())
        {
            cliente cliente1= new cliente();
            
            cliente1.setId(rs.getInt(1));
            cliente1.setNombre(rs.getString(2));
            cliente1.setApellido(rs.getString(3));
            cliente1.setDireccion(rs.getString(4));
            cliente1.setCedula(rs.getString(5));
            cliente1.setTelefono1(rs.getString(6));
            cliente1.setTelefono2(rs.getString(7));
            cliente1.setEmail(rs.getString(8));
            
            //asigno el rs a la lista
            list.add(cliente1);
            
            
            
        }
        }
        catch(Exception e)
        {
            //si falla un error de base de datos
            return "-1: Error de la base de datos ";
        }
        
         //convierto la lista a Gson
        String Gson=json.toJson(list);
        
        //retorno el json
        return Gson;
    }
    
}
