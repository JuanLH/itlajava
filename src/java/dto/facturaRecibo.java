/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import com.google.gson.Gson;
import db.DB;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author HiraldoTran
 */
public class facturaRecibo {
    private int f_id_t_venta_factura;
    private String f_tipo_factura_t_venta_factura;
    private int f_monto;
    private String f_fecha;
    private int f_id_t_Recibo_venta_factura;

    public int getF_id_t_venta_factura() {
        return f_id_t_venta_factura;
    }

    public void setF_id_t_venta_factura(int f_id_t_venta_factura) {
        this.f_id_t_venta_factura = f_id_t_venta_factura;
    }

    public String getF_tipo_factura_t_venta_factura() {
        return f_tipo_factura_t_venta_factura;
    }

    public void setF_tipo_factura_t_venta_factura(String f_tipo_factura_t_venta_factura) {
        this.f_tipo_factura_t_venta_factura = f_tipo_factura_t_venta_factura;
    }

    public int getF_monto() {
        return f_monto;
    }

    public void setF_monto(int f_monto) {
        this.f_monto = f_monto;
    }

    public String getF_fecha() {
        return f_fecha;
    }

    public void setF_fecha(String f_fecha) {
        this.f_fecha = f_fecha;
    }

    public int getF_id_t_Recibo_venta_factura() {
        return f_id_t_Recibo_venta_factura;
    }

    public void setF_id_t_Recibo_venta_factura(int f_id_t_Recibo_venta_factura) {
        this.f_id_t_Recibo_venta_factura = f_id_t_Recibo_venta_factura;
    }

    
    public String insertar_factura_recibo(String informacion) throws Exception{
        
        DB dbase = new DB("itla2","admini3lwux2","aLXsCK8L2Pmy");
        
        String sql = "INSERT INTO public.t_factura_recibo(f_tipo_factura_t_venta_factura,f_monto,f_id_t_recibo_venta_factura)";
        sql+="VALUES (?,?,?)";
        try{
        Gson json = new Gson();
        facturaRecibo info = json.fromJson(informacion, facturaRecibo.class);
        
        PreparedStatement p = DB.conexion.prepareStatement(sql);
        
        p.setString(1,info.getF_tipo_factura_t_venta_factura());
        p.setInt(2, info.getF_monto());
        
        p.setInt(3, info.getF_id_t_Recibo_venta_factura());
        p.execute();
        
        dbase.CerrarConexion();
        return "1";
        }catch(SQLException e){
            return "-1 "+e.getMessage();
        }
    
    }
    
    
}
