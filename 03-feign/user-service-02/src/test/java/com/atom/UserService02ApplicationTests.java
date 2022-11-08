package com.atom;

import com.atom.controller.UserController;
import com.atom.feign.UserOrderFeign;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@SpringBootTest
class UserService02ApplicationTests {

    @Resource
    private RestTemplate restTemplate;

    @Test
    void contextLoads() {
        UserOrderFeign userOrderFeign = (UserOrderFeign) Proxy.newProxyInstance(UserController.class.getClassLoader(), new Class[]{UserOrderFeign.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                String methodName = method.getName();
                GetMapping methodAnnotation = method.getAnnotation(GetMapping.class);
                String[] values = methodAnnotation.value();
                String path = values[0];

                Class<?> declaringClass = method.getDeclaringClass();
                String className = declaringClass.getName();
                FeignClient classAnnotation = declaringClass.getAnnotation(FeignClient.class);
                String applicationName = classAnnotation.value();

                String url = "http://" + applicationName + path;
                System.out.println(url);

                String result = restTemplate.getForObject(url, String.class);
                return result;
            }
        });

        String result = userOrderFeign.doOrder();
        System.out.println(result);
    }

}
