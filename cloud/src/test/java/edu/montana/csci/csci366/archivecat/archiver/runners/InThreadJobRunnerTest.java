package edu.montana.csci.csci366.archivecat.archiver.runners;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InThreadJobRunnerTest {

    @Test
    public void ensureAllJobsAreRun() {
        InThreadJobRunner runner = new InThreadJobRunner();
        List<FakeJob> fakeJobs = Arrays.asList(new FakeJob(), new FakeJob(), new FakeJob());
        runner.executeJobs(fakeJobs);

        for (FakeJob fakeJob : fakeJobs) {
            assertTrue(fakeJob.isFinished());
        }
    }

    @Test
    public void ensureAllJobsAreRunWithSleeping() {
        InThreadJobRunner runner = new InThreadJobRunner();
        List<FakeJob> fakeJobs = Arrays.asList(new FakeJob(), new FakeJob(100), new FakeJob());
        runner.executeJobs(fakeJobs);

        for (FakeJob fakeJob : fakeJobs) {
            assertTrue(fakeJob.isFinished());
        }
    }

}
