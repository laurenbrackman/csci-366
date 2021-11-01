package edu.montana.csci.csci366.archivecat.archiver.runners;

import edu.montana.csci.csci366.archivecat.archiver.jobs.DownloadJob;

import java.util.List;

public interface DownloadJobRunner {
    /**
     * Should execute the given jobs in the manner appropriate for the
     * particular implementation
     *
     * @param downloadJobs
     */
    void executeJobs(List<? extends DownloadJob> downloadJobs);
}
