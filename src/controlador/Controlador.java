
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import vista.Vista;
import modelo.Modelo;

public class Controlador implements ActionListener {
    
    private Modelo m;
    private Vista v;

    public Controlador(Modelo m, Vista v) {
        this.m = m;
        this.v = v;
        this.v.botonNuevo.addActionListener(this);
        this.v.botonCancelar.addActionListener(this);
        this.v.botonRegistrar.addActionListener(this);
        this.v.botonBuscar.addActionListener(this);
        this.v.botonRefrescar.addActionListener(this);
        this.v.botonLimpiar.addActionListener(this);
        this.v.botonModificar.addActionListener(this);
        this.v.botonActualizar.addActionListener(this);
        this.v.botonEliminar.addActionListener(this);
        
    }
    
    public void iniciar(){
        v.setTitle("Sistema con MVC.");
        v.pack();
        v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        v.setLocationRelativeTo(null);
        v.setVisible(true);
        //m.MostrarUsuarios();
        m.BloquearUsuario();
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        
        if(v.botonNuevo == e.getSource()){
            
            try{
                
                m.NuevoUsuario();
                
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, "No se ha podido activar los campos.");
            }
            
        }
        
        else if(v.botonCancelar == e.getSource()){
            
            try{
                
                m.BloquearUsuario();
                
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, "No se ha podido cancelar.");
            }
            
        }
        
        else if(v.botonRegistrar == e.getSource()){
            
            try{
                
                m.InsertarUsuarios();
                
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, "No se ha podido ingresar el dato.");
            }
            
        }
        
        else if(v.botonRefrescar == e.getSource()){
            
            try{
                
                m.MostrarUsuarios("");
                
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, "No se ha podido refrescar la tabla.");
            }
            
        }
        
        else if(v.botonBuscar == e.getSource()){
            
            try{
                
                String atributo = v.txt_buscar.getText().trim();
                m.MostrarUsuarios(atributo);
                
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, "No se ha encontrado el dato.");
            }
            
        }
        
        else if(v.botonLimpiar == e.getSource()){
            
            try{
                                
                m.Limpiar();
                
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, "No se ha podido limpiar los campos.");
            }
            
        }
        
        else if(v.botonModificar == e.getSource()){
            
            try{
                                
                m.ConsultarUsuarios();
                
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, "No se pudo consultar los usuarios.");
            }
            
        }
        
        else if(v.botonActualizar == e.getSource()){
            
            try{
                                
                m.ActualizarUsuarios();
                
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, "No se pudo actualizar la tabla.");
            }
            
        }
        
        else if(v.botonEliminar == e.getSource()){
            
            try{
                                
                m.EliminarUsuario();
                
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, "No se pudo eliminar la fila.");
            }
            
        }
        
    }
    
    
    
}
