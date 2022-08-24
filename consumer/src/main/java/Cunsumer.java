
public class Cunsumer {
    public static void main(String[] args) {
        HelloService helloService=ProxyFactory.getProxy(HelloService.class);
        String result=helloService.sayHello("12344");
        System.out.println(result);
    }
}
