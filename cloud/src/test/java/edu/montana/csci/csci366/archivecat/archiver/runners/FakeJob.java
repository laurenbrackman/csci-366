package edu.montana.csci.csci366.archivecat.archiver.runners;

import edu.montana.csci.csci366.archivecat.archiver.jobs.AbstractDownloadJob;
import edu.montana.csci.csci366.archivecat.archiver.jobs.DownloadJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class FakeJob implements DownloadJob {
    private static Logger LOGGER = LoggerFactory.getLogger(FakeJob.class);
    private static AtomicInteger COUNTER = new AtomicInteger();

    private Integer _id = COUNTER.incrementAndGet();
    private Integer _sleepFor = null;
    private Long _finishedAt;

    FakeJob() {
    }

    FakeJob(int sleepFor) {
        _sleepFor = sleepFor;
    }

    public boolean isFinished() {
        return _finishedAt != null;
    }

    public long getFinishedAt() {
        return _finishedAt;
    }


    @Override
    public void run() {
        LOGGER.info("Starting job for " + this);
        if (_sleepFor != null) {
            try {
                Thread.sleep(_sleepFor);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        _finishedAt = System.currentTimeMillis();
        LOGGER.info("Finished job for " + this + " at " + _finishedAt);
    }

    @Override
    public void downloadResource() throws Exception {
        // ignore
    }

    @Override
    public void updateElement() {
        // ignore
    }

    @Override
    public String toString() {
        return "FakeJob " + _id;
    }
}
