package com.atom.model;

import lombok.Data;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 断路器模型
 */
@Data
public class Crocodile {

    /**
     * 最大请求时间
     */
    public static final Integer WINDOW_TIME = 20;

    /**
     * 最大失败次数
     */
    public static final Integer MAX_FAIL_COUNT = 3;

    /**
     * 断路器的状态
     */
    private CrocodileStatus crocodileStatus = CrocodileStatus.CLOSE;

    /**
     * 当前失败次数
     * i++存在线程不安全情况
     * AtomicInteger可以保证线程安全
     */
    private AtomicInteger currentFailCount = new AtomicInteger(0);

    private Object lock = new Object();

    /**
     * 线程池
     */
    private ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
            4,
            8,
            30,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(2000),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
    );

    /**
     * 现在的代码，没有对失败次数的时间跨度做限制，即如果10年累计出现3次错误，也会打开断路器。
     * 所以，在这里新建一个线程，隔一段时间来把失败次数清零。
     * 比如，隔20s清零一次，意思是：20s内，如果累计出现3次失败，则会打开断路器。
     */
    {
        poolExecutor.execute(()->{
            while (true){
                try {//睡20s
                    TimeUnit.SECONDS.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //断路器关闭的时候，我们才需要对失败次数进行定期清零。20s后，把失败次数清零
                if(CrocodileStatus.CLOSE.equals(crocodileStatus)){
                    currentFailCount.set(0);
                }else{
                    //当断路器打开，没有记录失败次数，也就不需要对失败次数清零了。
                    synchronized (lock){
                        try {
                            //让这个线程处于等待状态
                            lock.wait();
                            System.out.println("清零线程被唤醒了");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        });
    }

    /**
     * 记录失败的次数
     */
    public void addFailCount() {

        int i = currentFailCount.incrementAndGet();
        if(i > MAX_FAIL_COUNT){
            //如果超过最大失败次数,打开断路器
            crocodileStatus = CrocodileStatus.OPEN;
            //当断路器打开后，就不能访问远程服务了。所以，等待一段时间，我们把断路器设置成半开状态，从而有机会再次尝试访问远程服务。
            poolExecutor.execute(()->{
                try {
                    //断路器打开后，过了指定的时间，把断路器设置成半开状态。
                    TimeUnit.SECONDS.sleep(WINDOW_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //把断路器设置成半开状态
                crocodileStatus = CrocodileStatus.HALF_OPEN;
                //重置失败次数为0
                currentFailCount.set(0);
            });
        }
    }
}
