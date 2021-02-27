/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package show;

import com.digitalpersona.uareu.Fid;
import com.digitalpersona.uareu.Fid.Fiv;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author NWACHUKWU
 */
public class FingerImage extends JPanel {

    private static final long serialVersionUID = 5;
    private BufferedImage m_image;

    public void showImage(Fid image) {
        
        // maybe to do, set the icon of the jFrame
        System.out.println(String.format("the image: %s", image));
        Fiv view = image.getViews()[0];
        m_image = new BufferedImage(view.getWidth(), view.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        m_image.getRaster().setDataElements(0, 0, view.getWidth(), view.getHeight(), view.getImageData());
        

        JFrame frame = new JFrame();
        frame.getContentPane().add(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setSize(new Dimension(view.getWidth() + 15, view.getHeight() + 40));
        // or
        frame.setSize(view.getWidth() + 15, view.getHeight() + 40); // width, height
        frame.setVisible(true);
        // view.getWidth(): 252, view.getHeight(): 324
        System.out.println("image.getViews().length is " + image.getViews().length);
        System.out.println("view.getImageData().length is " + view.getImageData().length);
        System.out.println(String.format("view.getWidth(): %d, view.getHeight(): %d", view.getWidth(), view.getHeight()));
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(m_image, 0, 0, null);
    }

}
