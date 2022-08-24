
import framwork.Invocation;
import framwork.Protocol;
import framwork.ProtocolFactory;
import framwork.URL;
import register.Register;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory<T> {
    public static <T>T getProxy(final Class interfaceName){
        return (T) Proxy.newProxyInstance(interfaceName.getClassLoader(), new Class[]{interfaceName}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Protocol protocol= ProtocolFactory.getProtocol();
                Invocation invocation=new Invocation(HelloService.class.getName(),"sayHello",new Object[]{"123123"},new Class[]{String.class});
                URL url= Register.random(interfaceName.getName());
                return protocol.send(url,invocation);
            }
        });
    }
}