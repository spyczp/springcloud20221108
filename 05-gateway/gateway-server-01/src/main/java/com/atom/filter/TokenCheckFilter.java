package com.atom.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
public class TokenCheckFilter implements GlobalFilter, Ordered {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    //写个常量，保存所有允许放行的路径
    public static final List<String> ALLOW_PATH = Arrays.asList("/login-service/doLogin", "myUrl", "/doLogin");

    /**
     * 和前端约定好token放在哪里。一般是放在请求头里， key Authorization value bearer token
     * 1.拿到请求url
     * 2.判断放行
     * 3.拿到请求头
     * 4.拿到token
     * 5.校验
     * 6.放行/拦截
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.拿到请求url
        ServerHttpRequest request = exchange.getRequest();
        //拿到uri
        String path = request.getURI().getPath();
        //2.判断放行
        if(ALLOW_PATH.contains(path)){
            return chain.filter(exchange);
        }
        //能到这里，说明请求路径不允许放行
        //3.拿到请求头
        HttpHeaders headers = request.getHeaders();
        List<String> authorization = headers.get("Authorization");
        //4.拿到token
        if(!CollectionUtils.isEmpty(authorization)){
            //集合不为空
            String token = authorization.get(0);
            //5.校验
            if(StringUtils.hasText(token)){
                //token存在
                // 把约定好的前缀替换掉 ： bearer token
                String realToken = token.replaceFirst("bearer ", "");
                if(StringUtils.hasText(realToken) && stringRedisTemplate.hasKey(realToken)){
                    //如果token不为空，且redis上面能找到这个token，则放行
                    return chain.filter(exchange);
                }
            }
        }

        //到这里，校验不通过
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set("content-type", "application/json;charset=utf8");

        //创建验证不通过的返回数据
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", HttpStatus.UNAUTHORIZED.value());
        map.put("msg", "用户未授权");

        ObjectMapper objectMapper = new ObjectMapper();
        //把map转换成byte数组
        byte[] bytes = new byte[0];
        try {
            bytes = objectMapper.writeValueAsBytes(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //封装bytes数组到数据包
        DataBuffer data = response.bufferFactory().wrap(bytes);

        return response.writeWith(Mono.just(data));
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
