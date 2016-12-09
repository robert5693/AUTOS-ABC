
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import org.edisoncor.gui.util.Avatar;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author usuario-dintev
 */
public class Manejadora_Eventos implements ActionListener {

    Interfaz interfaz;

    public Manejadora_Eventos() throws IOException {
        interfaz = new Interfaz(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource().equals(interfaz.jButton12)) {
            List<Avatar> avatares = new ArrayList<>();
            avatares.add(new Avatar("menu 1", loadImage("/Fotos/1.jpg")));
            avatares.add(new Avatar("menu 2", loadImage("/Fotos/2.jpg")));
            avatares.add(new Avatar("menu 2", loadImage("/Fotos/3.jpg")));
            avatares.add(new Avatar("menu 2", loadImage("/Fotos/4.jpg")));
            avatares.add(new Avatar("menu 14", loadImage("/Fotos/5.jpg")));
            avatares.add(new Avatar("menu 14", loadImage("/Fotos/6.jpg")));
            interfaz.panelAvatarChooser1.setAvatars(avatares);

            interfaz.jFrameGerente.setVisible(true);
            interfaz.jCheckBoxMenuItem1.setState(false);
            interfaz.jFrameGerente.pack();
        }
    }

    private static Image loadImage(String fileName) {
        try {
            return ImageIO.read(JFrame.class.getResource(fileName));
        } catch (IOException ex) {
            return null;
        }
    }
}
