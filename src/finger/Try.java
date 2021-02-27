/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finger;

import com.digitalpersona.uareu.*;
import com.digitalpersona.uareu.Fid.Fiv;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author NWACHUKWU
 */
public class Try {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {

            /**
             * Main Access Point
             *
             * The main access point to the U.are.U Java library is the
             * UareUGlobal class. This is a static class, which allows you to
             * acquire references to the classes related to fingerprint readers
             * and to the FingerJet Engine:
             *
             * To acquire a reference to ReaderCollection use the
             * GetReaderCollection() method
             *
             * The ReaderCollection interface provides a list of the readers
             * connected to the machine
             */
            ReaderCollection rc = UareUGlobal.GetReaderCollection();

            /**
             * A list of available readers can be acquired any time with
             * GetReaders() method.
             */
            rc.GetReaders();

            // Apparently, it's zero-based indexed
            rc.get(0); // get the first scanner
            // does rc.get(o) set through out the life cycle of the app or it get's changed by Reader reader = rc.get(i);
            for (int i = 0; i < rc.size(); i++) {
                Reader reader = rc.get(i);

                // print out descriptions
                System.out.println(String.format("Fingerprint Scanner %d name is %s", i, reader.GetDescription().name));
                System.out.println(String.format("Fingerprint Scanner %s Id is %s", i, reader.GetDescription().id));
                System.out.println(String.format("Fingerprint Scanner %s serial number is %s", i, reader.GetDescription().serial_number));
                System.out.println(String.format("Fingerprint Scanner %s modality is %s", i, reader.GetDescription().modality));
                System.out.println(String.format("Fingerprint Scanner %s technology is %s", i, reader.GetDescription().technology));
                System.out.println(String.format("Fingerprint Scanner %s version is %s", i, reader.GetDescription().version));

                System.out.println(String.format("Fingerprint Scanner %s product name is %s", i, reader.GetDescription().id.product_name));

                
                reader.Open(Reader.Priority.EXCLUSIVE);
                System.out.println("reader opened");

                //acquire capabilities
                Reader.Capabilities readerCapabilities = reader.GetCapabilities();

                //print out capabilities
                System.out.println();
                System.out.println();
                System.out.println();

                Reader.Status rs = reader.GetStatus();
                if (rs.status == Reader.ReaderStatus.NEED_CALIBRATION) {

                }
                System.out.println(String.format("reader status: %s : finger detected: %b", rs.status, rs.finger_detected));
                System.out.println("reader vendor data:");

                // Google why System.out.write() is preventing the application from running further, it just quits and says BUILD SUCCESSFULLY
                // System.out.write(rs.vendor_data); 
                System.out.println(String.format("Vendor data %s", Arrays.toString(rs.vendor_data)));

                System.out.println(String.format("can capture image: %b", readerCapabilities.can_capture));

                System.out.println(String.format("can stream image: %b", readerCapabilities.can_stream));

                System.out.println(String.format("can extract features: %b", readerCapabilities.can_extract_features));

                System.out.println(String.format("can match: %b", readerCapabilities.can_match));

                System.out.println(String.format("can identify: %b", readerCapabilities.can_identify));

                System.out.println(String.format("has fingerprint storage: %b", readerCapabilities.has_fingerprint_storage));

                System.out.println(String.format("indicator type: %d", readerCapabilities.indicator_type));

                System.out.println(String.format("has power management: %b", readerCapabilities.has_power_management));

                System.out.println(String.format("has calibration: %b", readerCapabilities.has_calibration));

                System.out.println(String.format("PIV compliant: %b", readerCapabilities.piv_compliant));

                for (int ii = 0; ii < readerCapabilities.resolutions.length; ii++) {
                    System.out.println(String.format("resolution: %d dpi", readerCapabilities.resolutions[ii]));

                }

                try {
                    // capture sth
                    /**
                     * Captures a fingerprint image.
                     *
                     * This function captures a fingerprint image from the
                     * opened reader device. This function signals the device
                     * that a fingerprint is expected and waits until a
                     * fingerprint is received. This function blocks until an
                     * image is captured, capture fails or timeout is expired.
                     * This function cannot be called in streaming mode.
                     */
                    System.out.println("What is happening");
                    System.out.println(DateFormat.getTimeInstance(DateFormat.LONG).format(new Date()));
                    
                    String fn = JOptionPane.showInputDialog("What is your first name");
                    String ln = JOptionPane.showInputDialog("What is your last name");
                    
                    System.out.println("firstname: " + fn + " lastname: " + ln);
                    
                    Reader.CaptureResult cr;
                    int tryingcount = 1;
                    do {
                        System.out.println(String.format("trying to read finger for the %d time", tryingcount));
                        cr = reader.Capture(Fid.Format.ANSI_381_2004, Reader.ImageProcessing.IMG_PROC_DEFAULT, 500, 1000 * 9); // -1 for last argument means block until we get finger print
//                    reader.StartStreaming();
//                    Reader.CaptureResult cr = reader.GetStreamImage(Fid.Format.ANSI_381_2004, Reader.ImageProcessing.IMG_PROC_PIV, 500);
//                    reader.StopStreaming();

                        System.out.println("cr quality:");
                        System.out.println(cr.quality);

                        tryingcount++;
                        if (tryingcount == 4) {
                            break;
                        }
                    } while (!cr.quality.equals(Reader.CaptureQuality.GOOD));

                    System.out.println(DateFormat.getTimeInstance(DateFormat.LONG).format(new Date()));
                    System.out.println("final cr quality:");
                    System.out.println(cr.quality);

                    System.out.println("result score");
                    System.out.println(cr.score);

                    System.out.println("We a");
                    // Constants describing result of the capture operation.
                    Reader.CaptureQuality resultOfScan = cr.quality;

                    // System.out.println("CR image:");
                    // System.out.println(cr.image); // of type Fid
                    if (cr.image != null) { // always check if cr.image is null before calling cr.image.getData() else app terminates
                        System.out.println("inside");
                        // use imagePanel
                        Fid k = cr.image;
                        // System.out.println(String.format("k: %d", k));
                        
                        System.out.println(String.format("k.getCbeffId(): %d", k.getCbeffId()));
                        System.out.println(String.format("k.getFingerCnt(): %d", k.getFingerCnt()));
                        System.out.println(String.format("k.getImageResolution(): %d", k.getImageResolution()));
                        

                        byte[] imgData = k.getData(); // we shouldn't save this
                        
                        // db.Save.saveDetails(k.getViews()[0].getImageData()); // save to db
                        // OR
                        db.Save.saveDetails(imgData);
                        show.FingerByteImage.ToImage(k); // let's see it // formerly imgData
                        System.out.println(String.format("byte[] imgData length: %d", imgData.length));
                        // Try.getImage(imgData);

                        // Fid.Format o = k.getFormat(); // Format is either Fid.Format.ANSI_381_2004 or ...
                        // System.out.println(String.format("Fid.Format: %d", o));
                        
                        show.FingerImage sj = new show.FingerImage();
                        sj.showImage(k);


                        // to extract features and do comparison
                        Engine engine = UareUGlobal.GetEngine();
                        Fmd[] m_fmds = new Fmd[2]; //two FMDs to perform comparison
                        try {
                            Fmd fmd = engine.CreateFmd(cr.image, Fmd.Format.ANSI_378_2004);
                            // db.Save.saveDetails(fmd.getData());
                            
                            // db.Save.saveDetails(fmd.getViews()[0].getData());
                            
                            m_fmds[0] = fmd;
                            
                            System.out.println("fmd format:" + fmd.getFormat());
                            System.out.println("fmd .getResolution():" + fmd.getResolution());
                            System.out.println("fmd.getWidth():" + fmd.getWidth());
                            System.out.println("fmd.getHeight():" + fmd.getHeight());
                            System.out.println("fmd.getCbeffId():" + fmd.getCbeffId());
                            
                            // CreateFmd(byte[] data, int width, int height, int resolution, int finger_position, int cbeff_id, Fmd.Format format)
                            // TODO: get m_fmds[1] from database
                            
                            m_fmds[1] = engine.CreateFmd(db.Get.getDetails("fas", "fas", 50, "ASFDF44", 4334), 252, 324, 500, 1, 3407615, Fmd.Format.ANSI_378_2004);
                            
                            // m_fmds[1] = engine.CreateFmd(db.Get.getDetails("fas", "fas", 44, "ASFDF44", 4334), 252, 324, 197, 1, 3407615, Fmd.Format.ANSI_378_2004);
                            
                            if (null != m_fmds[0] && null != m_fmds[1]) { //perform comparison
                                int falsematch_rate = engine.Compare(m_fmds[0], 0, m_fmds[1], 0);

                                int target_falsematch_rate = Engine.PROBABILITY_ONE / 100000; //target rate is 0.00001
                                
                                if (falsematch_rate < target_falsematch_rate) {
                                    System.out.println("Fingerprints matched.");
                                    System.out.println(String.format("dissimilarity score: 0x%x.", falsematch_rate));
                                    
                                    System.out.println(String.format("false match rate: %e.", (double) (falsematch_rate / Engine.PROBABILITY_ONE)));
                                    
                                } else {
                                    System.out.println("Fingerprints did not match.");
                                }
                            }

                        } catch (UareUException e) {
                            System.out.println("Engine.CreateFmd() error: " + e);
                            e.printStackTrace();
                        }
                    }

                } catch (UareUException e) {
                    System.out.println("Error trying to capture fingerprint image:");

                    // https://stackoverflow.com/a/1149712
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);
                    String sStackTrace = sw.toString(); // stack trace as a string
                    System.out.println(sStackTrace);

                    System.out.println(e.getMessage());
                }

                //close reader
                reader.Close();
            }

        } catch (UareUException e) {
            System.out.println(String.format("UareUGlobal.getReaderCollection() Error %s", e));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void getImage(byte[] imageData) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
        BufferedImage byteImage2 = ImageIO.read(bis);
        ImageIO.write(byteImage2, "png", new File("output.png"));
        System.out.println("image created");
    }

}
