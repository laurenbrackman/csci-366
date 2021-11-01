package edu.montana.csci.csci366.archivecat.archiver.jobs;

public interface DownloadJob extends Runnable {
    /*
     * downloadResource() should, well, download the given resource, by URL.  It should
     * make use of the java.net.http.HttpClient.
     *
     * This should be written such that it can potentially be run in parallel with other
     * downloads
     * */
    void downloadResource() throws Exception;

    /*
     * updateElement() should update the element with the archived path of the content (e.g. an <img> tag
     * should update the `src` attribute)
     * */

    void updateElement();
}
