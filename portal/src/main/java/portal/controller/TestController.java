package portal.controller;/*
 * Copyright (C) 1997-2020 康成投资（中国）有限公司
 *
 * http://www.rt-mart.com
 *
 * 版权归本公司所有，不得私自使用、拷贝、修改、删除，否则视为侵权
 */


import javassist.ClassPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;

/**
 * @author gangxiang.guan
 * @create 2023/4/19 15:14
 */
@Slf4j
@RequestMapping("test")
@RestController
public class TestController {

    private static ThreadLocal<List<Integer>> listThreadlocal = ThreadLocal.withInitial(ArrayList::new);

    private static ExecutorService executor = Executors.newFixedThreadPool(3);

    private static volatile AtomicInteger num = new AtomicInteger(1);

    private static final Object lock = new Object();

    private static volatile int count = 1;

    @RequestMapping("test1")
    public void test1() {
        log.info("helloWorld");
    }

    public static void main(String[] args) throws Exception {
//        Thread thread1 = new Thread(new Print(0));
//        Thread thread2 = new Thread(new Print(1));
//        Thread thread3 = new Thread(new Print(2));
//
//        thread1.start();
//        thread2.start();
//        thread3.start();
//        testCompletableFuture();
        Integer a = 1, b = 2;
        System.out.println("before:a="+ a +", b="+ b);
        swap(a, b);
        System.out.println("after:a="+ a +", b="+ b);
    }

    //通过sync和wait（）与notifyAll()方法实现
    public static class Print implements Runnable {

        private final int index;


        public Print(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            while (count <= 100) {
                synchronized (lock) {
                    while (count % 3 != index) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Thread-" + index + " " + (char)(65+ count++ %3));

                    lock.notifyAll();

                }
            }
        }
    }

    static class Worker extends Thread{
        int index;
        List<Condition> conditions;

        public Worker(int index, List<Condition> conditions) {
            this.index = index;
            this.conditions = conditions;
        }

        private void signalNext(){
            int nextIndex = (index + 1) % conditions.size();
            conditions.get(nextIndex).signal();
        }
    }

    static void testCompletableFuture() throws InterruptedException {
        CompletableFuture future = CompletableFuture.runAsync(() -> System.out.println("sjsdfjsd" + System.currentTimeMillis()));
        Thread.sleep(1000);
        future.thenAcceptAsync(result -> System.out.println("thread 1:" + result + System.currentTimeMillis()));
        Thread.sleep(1000);
        future.thenAcceptAsync(result -> {
            System.out.println("thread 2:" + result + System.currentTimeMillis());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        future.whenComplete((result, exception) -> System.out.println("thread 3:" + result + System.currentTimeMillis()));
//        System.out.println(future.join());
    }

    private static ApplicationEventPublisher publisher;

    static void applicationContext(){
        LinkedHashMap<Object, Object> objectObjectLinkedHashMap = new LinkedHashMap<>(new HashMap<>());
    }

    public static void swap(Integer i1, Integer i2) throws NoSuchFieldException, IllegalAccessException {
        Field field = Integer.class.getDeclaredField("value");
        field.setAccessible(true);
        Integer tmp = new Integer(i1.intValue());
        field.set(i1, new Integer(i2.intValue()));
        field.set(i2, 1);
        System.out.println(i1 + "  " + i2);
        field.set(i2, new Integer(1));
        System.out.println(i1 + "  " + i2);
    }
}
