package edu.montana.csci.csci366.archivecat.archiver.jobs;

import edu.montana.csci.csci366.archivecat.archiver.Archive;
import org.jsoup.nodes.Element;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ImageDownloadJob extends AbstractDownloadJob {

    private String _fullPathToImage;

    public ImageDownloadJob(Element element, Archive archive) {
        super(element, archive);
    }

    @Override
    public void downloadResource() throws Exception {
        // TODO implement - hint: use the 'Content-Type' header of the
        //                  response to determine the type of image and
        //                  give it the correct file ending
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
