package com.atom;

import com.atom.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class RestTemplate03ApplicationTests {

    private RestTemplate restTemplate = new RestTemplate();
    private String url = "http://localhost:8080/testPost2";

    @Test
    void testGet() {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/testGet?name=czp";
        //ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class);
        //System.out.println(entity);
        String object = restTemplate.getForObject(url, String.class);
        System.out.println(object);
    }

    @Test
    void testPost1(){
        User user = new User("张三", 29, 3000D);
        String result = restTemplate.postForObject(url, user, String.class);
        System.out.println(result);
    }

    @Test
    void testPost2(){
        LinkedMultiValueMap<Object, Object> map = new LinkedMultiValueMap<>();
        map.add("name", "李四");
        map.add("age", 30);
        map.add("price", 5000D);
        String result = restTemplate.postForObject(url, map, String.class);
        System.out.println(result);
    }
}
