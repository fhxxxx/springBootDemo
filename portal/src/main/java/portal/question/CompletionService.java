package portal.question;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 并发调三个方法，实现只要有一个成功就立即成功，否则等都失败才失败
 */
public class CompletionService {

    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(20,
                new BasicThreadFactory.Builder().namingPattern("thread%d").build());

        java.util.concurrent.CompletionService completionService = new ExecutorCompletionService(scheduledExecutorService);


    }
}
