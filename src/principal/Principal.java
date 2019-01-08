package principal;

import modelo.Modelo;
import vista.Vista;
import controlador.Controlador;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Principal {

    public static void main(String[] args) {

        try {

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Error: "+e);
        }

        Modelo m = new Modelo();
        Vista v = new Vista();
        Controlador c = new Controlador(m, v);
        c.iniciar();

    }

}
