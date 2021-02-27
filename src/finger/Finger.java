/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finger;

import com.digitalpersona.uareu.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Locale;
import javax.imageio.ImageIO;

/**
 *
 * @author NWACHUKWU
 */
public class Finger {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Trying the fingerprint thingy...");

        System.out.println(System.getProperty("os.arch"));

        System.out.println(System.getProperty("os.name").toLowerCase(Locale.ENGLISH));

        Reader.Description rd = new Reader.Description();

        System.out.println(String.format("Vendor name: %s\n", rd.id.vendor_name));

        System.out.println(String.format("Product name: %s\n", rd.id.product_name));

        System.out.println(String.format("Serial number: %s\n", rd.serial_number));

        System.out.println(String.format("USB VID: %s\n", rd.id.vendor_id));

        System.out.println(String.format("USB PID: %s\n", rd.id.product_id));

        System.out.println(String.format("USB BCD revision: %s\n", rd.version.bcd_revision));

        System.out.println(String.format("HW version: %d.%d.%d\n", rd.version.hardware_version.major, rd.version.hardware_version.minor, rd.version.hardware_version.maintenance));

        System.out.println(String.format("FW version: %d.%d.%d\n", rd.version.firmware_version.major, rd.version.firmware_version.minor, rd.version.firmware_version.maintenance));

        GetCapabilities h = new GetCapabilities();

        byte[] r = db.Get.getDetails("fsa", "asdfa", 1, "fdsafads", 43534);
        System.out.println(r.length);

        System.out.println("Time in Milliseconds: " + new Date().getTime());

        System.out.println("Current Time Stamp: " + new Timestamp(new Date().getTime()) );

        /*
        try {
            BufferedImage bImage = ImageIO.read(new File("C:\\Users\\NWACHUKWU\\Pictures\\adss\\3.jpg"));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "jpg", bos);
            byte[] data = bos.toByteArray();
            System.out.println("image uhmm " + data.length);
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            BufferedImage bImage2 = ImageIO.read(bis);
            ImageIO.write(bImage2, "jpg", new File("C:\\Users\\NWACHUKWU\\Pictures\\adss\\output.jpg"));
            System.out.println("image created");
        } catch (Exception e) {
            e.printStackTrace();
        }
         */
    }

}
