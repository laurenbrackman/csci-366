package edu.montana.csci.csci366.archivecat.archiver.runners;

import edu.montana.csci.csci366.archivecat.archiver.jobs.AbstractDownloadJob;
import edu.montana.csci.csci366.archivecat.archiver.jobs.DownloadJob;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolJobRunner implements DownloadJobRunner {
    public void executeJobs(List<? extends DownloadJob> downloadJobs) {
        // TODO implement - use a ThreadPoolExecutor with 10 threads to execute the jobs
        CountDownLatch latch = new CountDownLatch(downloadJobs.size());
        for(DownloadJob downloadJob : downloadJobs){
            var t = new Thread(new Runnable() {
                @Override
                public void run() {
                    downloadJob.run();
                    latch.countDown();
                }
            });
            t.start();;
        }
        try{
            latch.await();
        }
        catch(InterruptedException e){
            throw new RuntimeException(e);
        }
    }
}
