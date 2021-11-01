package edu.montana.csci.csci366.archivecat.archiver.runners;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ThreadPoolJobRunnerTest {

    @Test
    public void ensureAllJobsAreRun() {
        ThreadPoolJobRunner runner = new ThreadPoolJobRunner();
        List<FakeJob> fakeJobs = Arrays.asList(new FakeJob(), new FakeJob(), new FakeJob());
        runner.executeJobs(fakeJobs);

        for (FakeJob fakeJob : fakeJobs) {
            assertTrue(fakeJob.isFinished());
        }
    }

    @Test
    public void ensureAllJobsAreRunWithSleeping() {
        ThreadPoolJobRunner runner = new ThreadPoolJobRunner();
        List<FakeJob> fakeJobs = Arrays.asList(new FakeJob(), new FakeJob(100), new FakeJob());
        runner.executeJobs(fakeJobs);

        for (FakeJob fakeJob : fakeJobs) {
            assertTrue(fakeJob.isFinished());
        }
    }

    @Test
    public void ensureJobsExecuteConcurrently() {
        ThreadPoolJobRunner runner = new ThreadPoolJobRunner();
        FakeJob fakeJob1 = new FakeJob();
        FakeJob fakeJob2 = new FakeJob(100);
        FakeJob fakeJob3 = new FakeJob();
        List<FakeJob> fakeJobs = Arrays.asList(fakeJob1, fakeJob2, fakeJob3);
        runner.executeJobs(fakeJobs);

        for (FakeJob fakeJob : fakeJobs) {
            assertTrue(fakeJob.isFinished());
        }

        assertTrue(fakeJob1.getFinishedAt() < fakeJob2.getFinishedAt());
        assertTrue(fakeJob3.getFinishedAt() < fakeJob2.getFinishedAt());
    }

}
