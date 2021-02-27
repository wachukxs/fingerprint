/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finger;

import com.digitalpersona.uareu.*;
/**
 *
 * @author NWACHUKWU
 */
public class GetCapabilities {
    public void GetCapabilities(Reader reader){
        try{
			//open reader
			reader.Open(Reader.Priority.COOPERATIVE);
			
			//acquire capabilities
			Reader.Capabilities rc = reader.GetCapabilities();
			
			//close reader
			reader.Close();
			
			//print out capabilities
                        
                        String a,b = "";
			
			a = String.format("can capture image: %b\n", rc.can_capture);
			b.concat(a);
			a = String.format("can stream image: %b\n", rc.can_stream);
			b.concat(a);
			a = String.format("can extract features: %b\n", rc.can_extract_features);
			b.concat(a);
			a = String.format("can match: %b\n", rc.can_match);
			b.concat(a);
			a = String.format("can identify: %b\n", rc.can_identify);
			b.concat(a);
			a = String.format("has fingerprint storage: %b\n", rc.has_fingerprint_storage);
			b.concat(a);
			a = String.format("indicator type: %d\n", rc.indicator_type);
			b.concat(a);
			a = String.format("has power management: %b\n", rc.has_power_management);
			b.concat(a);
			a = String.format("has calibration: %b\n", rc.has_calibration);
			b.concat(a);
			a = String.format("PIV compliant: %b\n", rc.piv_compliant);
			b.concat(a);
			
			for(int i = 0; i < rc.resolutions.length; i++){
				System.out.println(String.format("resolution: %d dpi\n", rc.resolutions[i]));
				
			}
		} 
		catch(UareUException e){ 
			System.out.println("Reader.GetCapabilities()");
                        System.out.println(e);
		}
    }
}
