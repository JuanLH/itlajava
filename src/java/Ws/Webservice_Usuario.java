 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ws;

import dto.Respuesta;
import dto.Usuario;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import clases.CheckToken;
import com.google.gson.Gson;
import db.DB;
import dto.Respuesta;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Estudiante
 */
@Path("/usuario")
public class Webservice_Usuario {

    @Context
    private UriInfo context;
    CheckToken checktocken = new CheckToken();
    DB dbase = new DB("itla2","itlajava","12345678@itla");

    /**
     * Creates a new instance of Webservice_Usuario
     */
    public Webservice_Usuario() throws Exception {
    
    }

    /**
     * Retrieves representation of an instance of Ws.Webservice_Usuario
     * @return an instance of java.lang.String
     */
    
    @PUT // Metodo para modificar la contra del  Usuario By : Juan Luis Hiciano
    @Path("/modificar_pass")
    @Consumes("application/json")
    //metodo para cambiar contra de un usuario
    public String Mod_pass_usu(
            @FormParam("token")String token,
            @FormParam("pass")String pass,
            @FormParam("id") int id) throws Exception
    {
      
        
        Respuesta resp = new Respuesta();
       
        if (checktocken.checktocken2(token)==0) 
        { 
            resp.setId(2);
            resp.setMensaje("Lo Sentimos Usuario Desactivado, Comuniquese Con el Administrador, Gracias");
            return resp.ToJson(resp);                        
        }  
        
        Usuario user = new Usuario();
        return user.mod_pass(pass ,id);
        
    }
    
    @POST
    @Path("/insertar_usuario")
    @Produces("application/json")
    public String insertar_producto(
        @FormParam("token") String token ,
        @FormParam("Gson") String gson) throws Exception  {
        
         
        Respuesta respo  = new Respuesta();
        CheckToken ctoken = new CheckToken();
        
        
            if (ctoken.checktocken2(token)==0){
                respo.setId(2);
                respo.setMensaje("El token no esta activo"); 
                return  respo.ToJson(respo);
            }
        
        
        Usuario product = new Usuario();
        //respuestaBD es para saber si el pro es de bd y saber el error de la misma
        String respuestasBD=product.insertar_t_usuarios(gson);
        if(respuestasBD.equals("1")){
        
        respo.setId(1);
        respo.setMensaje("Hecho");
        return  respo.ToJson(respo);
        }
        respo.setId(-1);
        respo.setMensaje(respuestasBD);
        return  respo.ToJson(respo);
    }
    
    @GET
    @Path("/getusuario/{token}/{id}")
    @Produces("application/json")
    public String getUsuario(
            @PathParam("token") String token,
            @PathParam("id") int id) throws Exception
    {
        Respuesta respon = new Respuesta();
        CheckToken check = new CheckToken();
        String sql;
        DB dbase = new DB("itla2","itlajava","12345678@itla");//instancia del objeto  DB
        if(check.checktocken2(token)==0)
        {
            respon.setId(2);
            respon.setMensaje("El token ha sido desactivado");
            return respon.ToJson(respon);
        }
        
        sql="select * from t_usuario where f_id="+id+" ";
        ResultSet rs = dbase.execSelect(sql);
        
        try
        {
            if(!rs.next())
            {
                respon.setId(0);
                respon.setMensaje("no hay Usuarios registrados actualmente");
                return respon.ToJson(respon);
            }
            while(rs.next())
            {
                Usuario usu = new Usuario();
                
                usu.setF_id(rs.getInt(1));
                usu.setF_nombre(rs.getString(2));
                usu.setF_apellido(rs.getString(3));
                usu.setF_usuario(rs.getString(4));
                usu.setF_clave(rs.getString(5));
                usu.setF_proceso(rs.getBoolean(6));
                usu.setF_activo(rs.getBoolean(7));
                
                respon.setId(1);
                respon.setMensaje(respon.ToJson(usu));
                
            }
        
        }
        catch(SQLException e)
        {
            respon.setId(-1);
            respon.setMensaje("error de la base de datos "+e.getMessage()+" ");
            return respon.ToJson(respon); 
        }
        
         dbase.CerrarConexion();
         return respon.ToJson(respon);//retorna el cliente que se iso en el while.
            //fin del metodo usuario que busca por id made por José Aníbal Moronta
        
        
        
    }
    
    
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
    
    

    /**
     * PUT method for updating or creating an instance of Webservice_Usuario
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
    
    
    

    
}
