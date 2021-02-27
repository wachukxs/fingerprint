/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package show;

import com.digitalpersona.uareu.Fid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import javax.imageio.ImageIO;

/**
 *
 * @author NWACHUKWU
 */
public class FingerByteImage {

    public static void ToImage(Fid finger_image_data) {
        try {
            /**
             * 
             * ByteArrayInputStream bis = new ByteArrayInputStream(finger_byte_data);
             * OR
             * InputStream bis = new ByteArrayInputStream(finger_byte_data); // finger_byte_data was byte array from finger_image_data.getViews()[0].getData() and .getImageData() and even finger_image_data.getData()
             * BufferedImage bImage2 = ImageIO.read(bis);
             * 
             * ----- was returning error
             * 
             * 
             * OK, here's what is happening.
             *
             * On line 33, the ImageIO.read method is returning null. That
             * results in the exception later when you try and write the image.
             *
             * The Javadocs for the ImageIO.read method say:
             *
             * Returns a BufferedImage as the result of decoding a supplied
             * InputStream with an ImageReader chosen automatically from among
             * those currently registered. The InputStream is wrapped in an
             * ImageInputStream. If no registered ImageReader claims to be able
             * to read the resulting stream, null is returned.
             *
             *
             *
             * The ImageIO class identifies a suitable ImageReader that can read
             * the stream - presumably based on the format of the data in the
             * stream. There are no suitable readers registered, so the method
             * returns null.
             *
             * What I think is happening (although this is an educated guess) is
             * that your steg_process has messed with the bytes enough that the
             * ImageIO class can no longer recognise it as a JPEG. This is
             * consistent with the way it works if you comment out the call to
             * steg_process.
             *
             * If that's correct, how you fix it is another matter. I don't know
             * enough about JPEG formatting to sensibly comment.
             *
             * Edit: you may have noticed that long code lines mess up the width
             * of the forum pages. I've put a line break in one of your comments
             * to fix it.
             *
             *
             * https://coderanch.com/t/533840/java/java-lang-IllegalArgumentException-im-null
             */
            
            Fid.Fiv view = finger_image_data.getViews()[0];
            BufferedImage bImage = new BufferedImage(view.getWidth(), view.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            bImage.getRaster().setDataElements(0, 0, view.getWidth(), view.getHeight(), view.getImageData());
            
            File _image = new File("C:\\Users\\NWACHUKWU\\Documents\\NetBeansProjects\\Finger\\fingerimages\\", new Date().getTime()+".png");
            System.out.println("physical image Absolute path: " + _image.getAbsolutePath());
            ImageIO.write(bImage, "png", _image);
            System.out.println("image created");

        } catch (Exception e) {
            System.out.println("FingerByteImage.java error:");
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            e.printStackTrace();

            System.out.println();

            System.out.println(e.getMessage());
        }
    }
}
