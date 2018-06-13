/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbConnection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

/**
 * Get data from ajax request
 * @author zimma
 */
public class AjaxGetMap {

    /**
     * Get Data from Ajax Request
     * @param request Request 
     * @return HashMap<String,String>
     * @throws IOException 
     */
    public HashMap<String, String> getAjaxInfo(HttpServletRequest request) throws IOException {
        InputStream is = request.getInputStream();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buf = new byte[32];
        int r = 0;
        while (r >= 0) {
            r = is.read(buf);
            if (r >= 0) {
                os.write(buf, 0, r);
            }
        }
        String s = new String(os.toByteArray(), "UTF-8");
        String decoded = URLDecoder.decode(s, "UTF-8");
        String[] elements = decoded.split("&");
        HashMap<String, String> map = new HashMap<>();
        for (String element : elements) {
            String[] pair = element.split("=");
            map.put(pair[0], pair[1]);
        }
        return map;
    }

}
