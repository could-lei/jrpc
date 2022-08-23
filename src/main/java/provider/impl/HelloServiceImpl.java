package provider.impl;

import provider.api.HelloService;

/**
 * @author next
 * Created by next on 2022/8/23.
 */
public class HelloServiceImpl implements HelloService{
    public String sayHello(String name) {
        return name;
    }
}
