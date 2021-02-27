/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pay;

import com.flutterwave.rave.java.entry.cardPayment;
import com.flutterwave.rave.java.entry.transfers;
import com.flutterwave.rave.java.payload.cardLoad;

/**
 *
 * @author NWACHUKWU
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    
    private static String PBFPubKey = "FLWPUBK-cf2b3d8af1418e72ecb501098eba6074-X";
    private static String publickey = "FLWPUBK-cf2b3d8af1418e72ecb501098eba6074-X";
    private static String country = "NG";
    
    public static void main(String[] args) {
        // TODO code application logic here

        cardPayment cardPayment = new cardPayment();
        cardLoad cardload = new cardLoad();
        cardload.setPublic_key(PBFPubKey);

        transfers transfers = new transfers();
        
        
        
        // String response = transfers.doGetBankList(country, publickey);

        // System.out.println("response:" + response);
    }

}
