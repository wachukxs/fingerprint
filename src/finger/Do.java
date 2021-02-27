/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finger;

import com.digitalpersona.uareu.Engine;
import com.digitalpersona.uareu.Engine.PreEnrollmentFmd;
import com.digitalpersona.uareu.Fid;
import com.digitalpersona.uareu.Fmd;
import com.digitalpersona.uareu.Reader;
import com.digitalpersona.uareu.Reader.CaptureResult;
import com.digitalpersona.uareu.ReaderCollection;
import com.digitalpersona.uareu.UareUException;
import com.digitalpersona.uareu.UareUGlobal;

/**
 *
 * @author NWACHUKWU
 */
public class Do {

    public static void main(String[] args) throws UareUException {
        ReaderCollection rc = UareUGlobal.GetReaderCollection();

        rc.GetReaders();
        Engine engine = UareUGlobal.GetEngine();
        Reader reader = rc.get(0);
        
        reader.Open(Reader.Priority.EXCLUSIVE);
        // check for status of reader, to make sure it's good
        Reader.Status rs = reader.GetStatus();
        if (Reader.ReaderStatus.READY == rs.status) {
            
        } else {
            System.out.println("Reader status: " + rs.status);
        }
        Fmd enrollmentFmd = engine.CreateEnrollmentFmd(Fmd.Format.ANSI_378_2004, new Engine.EnrollmentCallback() {

            @Override
            public PreEnrollmentFmd GetFmd(Fmd.Format format) {
                PreEnrollmentFmd prefmd = new Engine.PreEnrollmentFmd(); // or null

                try {
                    System.out.println("Place your finger for reading.");
                    CaptureResult cr = reader.Capture(Fid.Format.ANSI_381_2004, Reader.ImageProcessing.IMG_PROC_DEFAULT, 500, 1000 * 9); // -1 for last argument means block until we get finger print
                    System.out.println("Read.");
                    System.out.println();

                    if (cr.quality.equals(Reader.CaptureQuality.GOOD)) {
                        System.out.println("cr quality: " + cr.quality);

                        Fmd fmd = engine.CreateFmd(cr.image, Fmd.Format.ANSI_378_2004);

                        //return prefmd 
                        prefmd.fmd = fmd;
                        prefmd.view_index = 0; // finger position ... must always be zero

                        // we done
                        System.out.println("We got it.");
                    } else {
                        System.out.println("We couldn't read fingerprint. Try again.");
                    }
                } catch (UareUException e) {
                    e.printStackTrace();
                }
                return prefmd;

                // throw new UnsupportedOperationException("Not supported yet.");
                // To change body of generated methods, choose Tools | Templates.
            };

        });
        
        Reader.CaptureResult cr;
        int tryingcount = 1;
        do {
            System.out.println(String.format("trying to read finger for the %d time", tryingcount));
            cr = reader.Capture(Fid.Format.ANSI_381_2004, Reader.ImageProcessing.IMG_PROC_DEFAULT, 500, 1000 * 9); // -1 for last argument means block until we get finger print

            System.out.println("cr quality:");
            System.out.println(cr.quality);

            tryingcount++;
        } while (!cr.quality.equals(Reader.CaptureQuality.GOOD) && tryingcount < 5);

        if (cr.quality.equals(Reader.CaptureQuality.GOOD)) {
            
            Fmd[] m_fmds = new Fmd[2]; // two FMDs to perform comparison (for verification)
            
            try {
                Fmd fmd = engine.CreateFmd(cr.image, Fmd.Format.ANSI_378_2004);
                
                m_fmds[0] = fmd;

                m_fmds[1] = enrollmentFmd;

                if (null != m_fmds[0] && null != m_fmds[1]) { // perform comparison
                    
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
        } else {
            // Try the whole thing again
            System.out.println("Failed to read fingerprint.");
        }

    }

}
