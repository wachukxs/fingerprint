/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finger;

/**
 *
 * @author NWACHUKWU
 */
public class TryDP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        DigitalPersona dp = new DigitalPersona();
        
        dp.EnrollFingerprint(1000 * 10);
        dp.readFingerPrint(1000 * 10);
    }
    
}
