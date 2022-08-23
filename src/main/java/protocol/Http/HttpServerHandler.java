package protocol.Http;

import framework.Invocation;
import framework.URL;
import org.apache.commons.io.IOUtils;
import register.Register;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

/**
 * Created by next on 2022/8/23.
 */
public class HttpServerHandler {
    public void handler(HttpServletRequest req, HttpServletResponse res){
        try {
            InputStream inputStream=req.getInputStream();
            ObjectInputStream ois=new ObjectInputStream(inputStream);
            Invocation invocation=(Invocation) ois.readObject();

            //寻找实现类
            String interfaceName=invocation.getInterfaceName();
            URL url=new URL("localhost",8080);
            Class implClass=Register.get(url,interfaceName);

            Method method=implClass.getMethod(invocation.getMethodName(),invocation.getParamTypes());
            String result= (String) method.invoke(implClass.newInstance(),invocation.getParams());

            OutputStream outputStream=res.getOutputStream();
            IOUtils.write(result,outputStream);
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
