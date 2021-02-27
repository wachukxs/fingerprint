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
public class DoAgain implements Engine.EnrollmentCallback {

    private Reader reader = null;
    Engine engine = null;
    int count = 0;

    @Override
    public PreEnrollmentFmd GetFmd(Fmd.Format format) {
        PreEnrollmentFmd prefmd = new Engine.PreEnrollmentFmd();

        try {
            System.out.println("Place your finger for reading. " + ++count);
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
    }
    
    public Fmd readFingerprint() {
        Reader.CaptureResult cr;
        Fmd fmd = null;
        int tryingcount = 1;
        try {
            do {
                System.out.println(String.format("trying to read finger for the %d time", tryingcount));
                cr = reader.Capture(Fid.Format.ANSI_381_2004, Reader.ImageProcessing.IMG_PROC_DEFAULT, 500, 1000 * 9); // -1 for last argument means block until we get finger print

                System.out.println("cr quality:");
                System.out.println(cr.quality);

                tryingcount++;
            } while (!cr.quality.equals(Reader.CaptureQuality.GOOD) && tryingcount < 5);

            if (cr.quality.equals(Reader.CaptureQuality.GOOD)) {
                fmd = engine.CreateFmd(cr.image, Fmd.Format.ANSI_378_2004);
                
            } else {
                // Try the whole thing again
                System.out.println("Failed to read fingerprint.");
            }
        } catch (UareUException e) {
            e.printStackTrace();
        }
        
        return fmd;
    }

    public boolean compare(Fmd fmd_1, Fmd fmd_2) {
        
        boolean match = false;
        Fmd[] m_fmds = new Fmd[2]; // two FMDs to perform comparison (for verification)

        try {

            m_fmds[0] = fmd_1;

            m_fmds[1] = fmd_2;

            if (null != m_fmds[0] && null != m_fmds[1]) { // perform comparison

                int falsematch_rate = engine.Compare(m_fmds[0], 0, m_fmds[1], 0);

                int target_falsematch_rate = Engine.PROBABILITY_ONE / 100000; // target rate is 0.00001

                if (falsematch_rate < target_falsematch_rate) {
                    System.out.println("Fingerprints matched.");
                    System.out.println(String.format("dissimilarity score: 0x%x.", falsematch_rate));
                    System.out.println(String.format("false match rate: %e.", (double) (falsematch_rate / Engine.PROBABILITY_ONE)));
                    
                    match = true;
                } else {
                    System.out.println("Fingerprints did not match.");
                }
            }

        } catch (UareUException e) {
            System.out.println("Engine.CreateFmd() error: " + e);
            e.printStackTrace();
        }
        
        return match;
    }

    public DoAgain() {
        try {
            ReaderCollection rc = UareUGlobal.GetReaderCollection();

            rc.GetReaders();
            engine = UareUGlobal.GetEngine();
            reader = rc.get(0);

            reader.Open(Reader.Priority.EXCLUSIVE);
        } catch (UareUException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws UareUException {

        DoAgain one = new DoAgain();
        
        Fmd fmd_1 = one.readFingerprint();
        
        Fmd fmd_2 = one.runEnrollment();
        
        boolean i = one.compare(fmd_1, fmd_2);
        
        System.out.println("\nmatch? " + i);
        
        one.reader.Close();

    }

    public Fmd runEnrollment() {
        Fmd enrollmentFmd = null;
        try {
            enrollmentFmd = engine.CreateEnrollmentFmd(Fmd.Format.ANSI_378_2004, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enrollmentFmd;
    }
    
    public Reader.Status getReaderStatus(){
        
        Reader.Status rs = null;
        try {
            rs = reader.GetStatus();
        } catch (UareUException e) {
            e.printStackTrace();
        }
        
        return rs;
    }

}
