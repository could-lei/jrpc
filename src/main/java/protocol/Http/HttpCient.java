package protocol.Http;

import framework.Invocation;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by next on 2022/8/23.
 */
public class HttpCient {
    public String post(String hostname, Integer port,Invocation invocation){
        try {
            URL url=new URL("http",hostname,port,"/");
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);

            OutputStream outputStream=httpURLConnection.getOutputStream();
            ObjectOutputStream oos=new ObjectOutputStream(outputStream);
            oos.writeObject(invocation);
            oos.flush();
            oos.close();

            InputStream inputStream=httpURLConnection.getInputStream();
            return IOUtils.toString(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
