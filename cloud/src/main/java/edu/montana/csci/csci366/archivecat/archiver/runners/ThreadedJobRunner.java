package edu.montana.csci.csci366.archivecat.archiver.runners;

import edu.montana.csci.csci366.archivecat.archiver.jobs.AbstractDownloadJob;
import edu.montana.csci.csci366.archivecat.archiver.jobs.DownloadJob;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ThreadedJobRunner implements DownloadJobRunner {
    public void executeJobs(List<? extends DownloadJob> downloadJobs) {
        // TODO - run each job in its own thread.  Use a CountdownLatch
        //        to ensure that all threads complete before exiting this
        //        method
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
