/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.zabalburu.daw1.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.pool.OracleDataSource;

/**
 *
 * @author ichueca
 */
public final class Conexion {
    private static Connection cnn = null;
    
    private Conexion(){}
    
    public static Connection getConexion(){
        if (cnn == null){
            try {
                // Obtener una conexión
                OracleDataSource ds = new OracleDataSource();
                ds.setDriverType("thin");
                ds.setServerName("localhost");
                ds.setPortNumber(1522); // 1521
                ds.setDatabaseName("xe"); // xe --> Express / ORCL --> Standard
                ds.setUser("daw1");
                ds.setPassword("daw1daw1");
                cnn = ds.getConnection();
            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return cnn;
    }
    
    public static void cerrarConexion(){
        try {
            cnn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
