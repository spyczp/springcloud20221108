package com.atom.aspect;

import com.atom.model.Crocodile;
import com.atom.model.CrocodileStatus;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Random;

/**
 * 这里使用切面方法
 * 1.首先定义了一个在方法上面起作用的注解：@Crocodile
 * 2.当在任意方法上使用这个自定义注解的时候，就会调用crocodileAround切面方法。
 * 3.这个crocodileAround是around方式的切面，即在添加了@Crocodile的方法执行过程中，会执行crocodileAround这个切面方法。
 */
@Component
@Aspect
public class CrocodileAspect {

    public static HashMap<String, Crocodile> crocodileMap = new HashMap<>();

    public static Random random = new Random();

    static {
        crocodileMap.put("order-service", new Crocodile());
    }

    /**
     * 相当于拦截器
     * @param joinPoint
     * @return
     */
    @Around(value = "@annotation(com.atom.anno.Crocodile)")
    public Object crocodileAround(ProceedingJoinPoint joinPoint){
        Object result = null;

        //获取当前提供者的断路器
        Crocodile crocodile = crocodileMap.get("order-service");
        CrocodileStatus crocodileStatus = crocodile.getCrocodileStatus();
        switch (crocodileStatus){
            case CLOSE:
                //如果断路器是关闭状态，则正常访问
                try {
                    result = joinPoint.proceed();
                    //这里到这里说明没有出现异常
                    return result;
                } catch (Throwable e) {
                    e.printStackTrace();
                    //远程调用出现异常，重试次数加1
                    crocodile.addFailCount();
                    return "我是备胎-CLOSE";
                }
            case OPEN:
                return "我是备胎-OPEN";
            case HALF_OPEN:
                //生成一个随机数0-4，每个数出现的概率是相等的。所以，当数字等于1时，尝试访问远程服务，即20%的概率访问远程服务。
                int i = random.nextInt(5);
                System.out.println(i);
                if(i == 1){
                    try {
                        result = joinPoint.proceed();
                        //到这里，说明访问成功，则关闭断路器
                        crocodile.setCrocodileStatus(CrocodileStatus.CLOSE);
                        //把清零线程开启
                        synchronized (crocodile.getLock()){
                            crocodile.getLock().notifyAll();
                        }
                        return result;
                    } catch (Throwable e) {
                        e.printStackTrace();
                        return "我是备胎-HALF_OPEN";
                    }
                }else {//其它情况，则不访问远程服务，直接返回 备胎。概率是80%
                    return "我是备胎-ELSE_HALF_OPEN";
                }
            default:
                return "我是备胎-default";
        }
    }
}
