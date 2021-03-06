/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ws;

import clases.CheckToken;
import com.google.gson.Gson;
import db.DB;
import dto.Producto;
import dto.Respuesta;
import dto.alquilerFactura;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author COMPAQ
 */
@Path("alquiler_factura")
public class WebService_Alquiler_factura {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of WebService_Alquiler_factura
     */
    public WebService_Alquiler_factura() {
    }

    /**
     * Retrieves representation of an instance of Ws.WebService_Alquiler_factura
     * @return an instance of java.lang.String
     */
   
    
    /*Metodo que inserta en alquiler_factura*/
    @POST
    @Path("/insertar_alquiler_factura")
    @Produces("application/json")
    public String  insertar_alquiler_factura(
            @FormParam("token") String token, 
            @FormParam("Gson") String gson) throws Exception{
        
        Respuesta respo  = new Respuesta();
        CheckToken ctoken = new CheckToken();
        if (ctoken.checktocken2(token)==0){
            respo.setId(2);
            respo.setMensaje("El token no esta activo");
            return respo.ToJson(respo);

        }
        
        alquilerFactura afactura = new alquilerFactura();
        String respuestasBD=afactura.insertar_alquiler_factura(gson);
          if(respuestasBD.equals("1")){
        
        respo.setId(1);
        respo.setMensaje("Hecho");
        return  respo.ToJson(respo);
        }
        respo.setId(-1);
        respo.setMensaje(respuestasBD);
        return  respo.ToJson(respo);
        
                
    
    }
    /*fin Metodo que inserta en alquiler_factura made by:José Aníbal Moronta Mejía*/
    
    
    
    /*inicio del metodo que busca detallealquilerFactua por el id*/
    @GET
    @Path("/getalquilerfactura_id/{token}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAlquilerFactura_Id(@PathParam("token") String token,@PathParam("id")int id) throws Exception{
        
        Respuesta respon = new Respuesta();
        CheckToken check = new CheckToken();
       
      
        //instancie el objeto de DB
       DB dbase = new DB("itla2","itlajava","12345678@itla");
          
       if (check.checktocken2(token)==0) 
       { 
         respon.setId(2);
         respon.setMensaje("Lo Sentimos Token Desactivado, Comuniquese Con el Administrador, Gracias");
         return respon.ToJson(respon);
                     
       }            
                 
       //realizo el sql de busqueda
       String sql="SELECT f_id,f_tipo_factura,f_id_t_cliente,f_id_t_usuarios,f_fecha,f_hecha_por,f_monto,f_balance,f_pagada";
       sql+=" FROM public.t_alquiler_factura where f_id ="+ id ;
        
  
        try
        {
            ResultSet rs = dbase.execSelect(sql);   
           
            if (rs.next())
            {  
    
                alquilerFactura af = new alquilerFactura();
                    
                af.setF_id(rs.getInt(1));
                af.setF_tipo_factura(rs.getString(2));
                af.setF_id_cliente(rs.getInt(3));
                af.setF_fecha(rs.getString(5));
                af.setF_hecha_por(rs.getString(6));
                af.setF_id_usuario(rs.getInt(4));
                af.setF_monto(rs.getInt(7));
                af.setF_balance(rs.getInt(8));
                af.setF_pagada(rs.getBoolean(9));
                
                
                //asigno elrs a la lista
                respon.setId(1);
                respon.setMensaje(respon.ToJson(af));
                 
            }
        } 
        catch (SQLException e) 
        {
            //si falla un error de base de datos
             respon.setId(-1);
             respon.setMensaje("Error de la base de datos "+e.getMessage());
             return respon.ToJson(respon);    
        }
         dbase.CerrarConexion();
        return respon.ToJson(respon);
    
    }
    
     //*fin  del metodo que busca detalle alquilerFactua por el id made by :José Aníbal Moronta* 
   
    
    @POST  //metodo solo para el Servlet
    @Path("/getquilerfactura_id")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAlquilerFactura_Id(
            @FormParam("id")int id) throws Exception{
        
        Respuesta respon = new Respuesta();
   
       DB dbase = new DB("itla2","itlajava","12345678@itla"); //instancie el objeto de DB
          
              
                 
       //realizo el sql de busqueda
       String sql="SELECT f_id,f_tipo_factura,f_id_t_cliente,f_id_t_usuarios,f_fecha,f_hecha_por,f_monto,f_balance,f_pagada";
       sql+="FROM public.t_alquiler_factura where f_id ="+ id ;
        
  
        try
        {
            ResultSet rs = dbase.execSelect(sql);   
            if(!rs.next())
            {
                     
                Respuesta respo = new Respuesta();
                     
                respo.setId(0);
                respo.setMensaje("No hay registros actualmente en la base de datos");
                return respo.ToJson(respo);
                 
            }
            while (rs.next())
            {  
                 
                
                alquilerFactura af = new alquilerFactura();
                    
                af.setF_id(rs.getInt(1));
                af.setF_tipo_factura(rs.getString(2));
                af.setF_id_cliente(rs.getInt(3));
                af.setF_fecha(rs.getString(4));
                af.setF_hecha_por(rs.getString(5));
                af.setF_id_usuario(rs.getInt(6));
                af.setF_monto(rs.getInt(7));
                af.setF_balance(rs.getInt(8));
                af.setF_pagada(rs.getBoolean(9));    
                
                
                //Asigno los datos a el Objeto Respuesta
                respon.setId(1);
                respon.setMensaje(respon.ToJson(af));
            }
        }
        catch (SQLException e) 
        {
            //si falla un error de base de datos
             respon.setId(-1);
             respon.setMensaje("Error de la base de datos "+e.getMessage());
             return respon.ToJson(respon);
                      
        }
         //convierto la lista a Gson
        dbase.CerrarConexion();      
        //retorno el json
        return respon.ToJson(respon);     

    }
    
    
    
    /**
     * PUT method for updating or creating an instance of WebService_Alquiler_factura
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    
}
