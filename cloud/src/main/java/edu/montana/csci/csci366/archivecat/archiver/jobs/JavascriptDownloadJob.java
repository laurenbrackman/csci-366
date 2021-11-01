package edu.montana.csci.csci366.archivecat.archiver.jobs;

import edu.montana.csci.csci366.archivecat.archiver.Archive;
import org.jsoup.nodes.Element;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class JavascriptDownloadJob extends AbstractDownloadJob {
    private String _fullPathToJS;

    public JavascriptDownloadJob(Element element, Archive archive) {
        super(element, archive);
    }

    @Override
    public void downloadResource() throws Exception {
        // TODO - implement
    }

    @Override
    public void updateElement() {
        // TODO implement
    }

    @Override
    protected String getURL() {
        // TODO implement
        return "";
    }
}
