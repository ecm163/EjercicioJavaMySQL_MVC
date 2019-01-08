package modelo;

import vista.Vista;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Modelo {

    Connection cc;
    Connection cn = Conexion();
    Vista v = new Vista();

    public Connection Conexion() {

        try {

            Class.forName("com.mysql.jdbc.Driver");
            cc = DriverManager.getConnection("jdbc:mysql://localhost/sistemamvc", "root", "");
            System.out.println("Hecha la conexión con éxito.");

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return cc;

    }

    public void IniciarModelo() {

        BloquearUsuario();
        //MostrarUsuarios();

    }

    public void Limpiar() {

        v.txt_nombre.setText("");
        v.txt_apellido.setText("");
        v.txt_telefono.setText("");
        v.txt_domicilio.setText("");
        v.txt_edad.setText("");
        v.txt_buscar.setText("");

    }

    

    public void MostrarUsuarios(String atributo) {

        DefaultTableModel tabla = new DefaultTableModel();
        tabla.addColumn("Identificador");
        tabla.addColumn("Nombres");
        tabla.addColumn("Apellidos");
        tabla.addColumn("Teléfonos");
        tabla.addColumn("Domicilios");
        tabla.addColumn("Edades");

        v.Tabla.setModel(tabla);

        String sql = "SELECT * FROM usuarios";

        if (atributo.equals("")) {
            sql = "SELECT * FROM usuarios";
        } else {
            sql = "SELECT * FROM usuarios WHERE Id = " + atributo;
        }

        //atributo = "";
        String datos[] = new String[6];

        try {

            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);

                tabla.addRow(datos);

                v.Tabla.setModel(tabla);
                v.Tabla.setVisible(true);

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error: " + e + ", no se puede mostrar la tabla.");
        }

    }

    public void InsertarUsuarios() {

        try {

            String nombre = v.txt_nombre.getText().trim();
            String apellido = v.txt_apellido.getText().trim();
            String telefono = v.txt_telefono.getText().trim();
            String domicilio = v.txt_domicilio.getText().trim();
            String edad = v.txt_edad.getText().trim();

            if (nombre.equals("") || apellido.equals("") || telefono.equals("")
                    || domicilio.equals("") || edad.equals("")) {

                JOptionPane.showMessageDialog(null, "Error, hay campos vacíos.");

            } else {

                PreparedStatement ppt = cn.prepareStatement("INSERT INTO usuarios"
                        + "(nombre, apellido, telefono, domicilio, edad) "
                        + "VALUES(?,?,?,?,?)");
                ppt.setString(1, v.txt_nombre.getText().trim());
                ppt.setString(2, v.txt_apellido.getText().trim());
                ppt.setString(3, v.txt_telefono.getText().trim());
                ppt.setString(4, v.txt_domicilio.getText().trim());
                ppt.setString(5, v.txt_edad.getText().trim());

                ppt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Inserción en la base de datos exitosa.");
                //Limpiar();
                MostrarUsuarios("");

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Inserción en la base de datos fallida.");
        }

    }
    
    public void ConsultarUsuarios() {

        v.botonActualizar.setEnabled(true);
        v.botonRegistrar.setEnabled(false);

        int fila = v.Tabla.getSelectedRow();

        if (fila >= 0) {

            v.txt_buscar.setText(v.Tabla.getValueAt(fila, 0).toString());
            v.txt_nombre.setText(v.Tabla.getValueAt(fila, 1).toString());
            v.txt_apellido.setText(v.Tabla.getValueAt(fila, 2).toString());
            v.txt_telefono.setText(v.Tabla.getValueAt(fila, 3).toString());
            v.txt_domicilio.setText(v.Tabla.getValueAt(fila, 4).toString());
            v.txt_edad.setText(v.Tabla.getValueAt(fila, 5).toString());

        } else {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna fila, "
                    + "haga clic en una fila para seleccionarla si aún quiere"
                    + " modificar la tabla.");
        }

    }

    public void ActualizarUsuarios() {

        try {

            String Id = v.txt_buscar.getText().trim();
            String nombre = v.txt_nombre.getText().trim();
            String apellido = v.txt_apellido.getText().trim();
            String telefono = v.txt_telefono.getText().trim();
            String domicilio = v.txt_domicilio.getText().trim();
            String edad = v.txt_edad.getText().trim();

            if (nombre.equals("") || apellido.equals("") || telefono.equals("")
                    || domicilio.equals("") || edad.equals("")) {

                JOptionPane.showMessageDialog(null, "Error, hay campos vacíos.");

            } else {

                PreparedStatement ppt = cn.prepareStatement("UPDATE usuarios SET "
                        + "nombre = '" + nombre + "', "
                        + "apellido = '" + apellido + "',"
                        + "telefono = '" + telefono + "',"
                        + "domicilio = '" + domicilio + "',"
                        + "edad = '" + edad + "'"
                        + "WHERE Id = " + Id + "");

                ppt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Modificación realizada con éxito.");
                MostrarUsuarios("");
                NuevoUsuario();

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo actualizar la tabla.");
        }

    }

    public void EliminarUsuario() {

        int fila = v.Tabla.getSelectedRow();
        
        int Id = Integer.parseInt(v.Tabla.getValueAt(fila, 0).toString());
        
        if (fila >= 0) {

            try {
                
                    
                PreparedStatement pps = cn.prepareStatement("DELETE FROM usuarios WHERE"
                        + " Id = " + Id + "");

                int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro de que "
                        + "desea eliminar la fila " + (fila+1) + " con el Id = "
                        + Id + " de la base de datos?");
                
                if (resp == 0) {
                    
                    pps.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Fila "+ (fila+1) +" eliminada.");
                    MostrarUsuarios("");
                    NuevoUsuario();
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "No se ha podido eliminar la fila " + (fila+1) + 
                        "con Id = " + Id + ".");
            }

        } 
        else {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna fila, "
                    + "haga clic en la fila para seleccionarla si aún quiere"
                    + " eliminarla de la tabla.");
        }

    }

    public void NuevoUsuario() {

        Limpiar();
        v.txt_nombre.setEnabled(true);
        v.txt_apellido.setEnabled(true);
        v.txt_telefono.setEnabled(true);
        v.txt_domicilio.setEnabled(true);
        v.txt_edad.setEnabled(true);

        v.botonCancelar.setEnabled(true);
        v.botonActualizar.setEnabled(false);
        v.botonRegistrar.setEnabled(true);        
        v.botonLimpiar.setEnabled(true);
        
        v.botonBuscar.setEnabled(true);
        v.botonRefrescar.setEnabled(true);
        v.botonModificar.setEnabled(true);
        v.botonEliminar.setEnabled(true);

        MostrarUsuarios("");
    }

    public void BloquearUsuario() {

        v.txt_nombre.setEnabled(false);
        v.txt_apellido.setEnabled(false);
        v.txt_telefono.setEnabled(false);
        v.txt_domicilio.setEnabled(false);
        v.txt_edad.setEnabled(false);

        v.botonCancelar.setEnabled(false);
        v.botonActualizar.setEnabled(false);
        v.botonRegistrar.setEnabled(false);
        v.botonLimpiar.setEnabled(false);
        
        v.botonBuscar.setEnabled(false);
        v.botonRefrescar.setEnabled(false);
        v.botonModificar.setEnabled(false);
        v.botonEliminar.setEnabled(false);

        v.Tabla.setVisible(false);
        Limpiar();

    }

}
