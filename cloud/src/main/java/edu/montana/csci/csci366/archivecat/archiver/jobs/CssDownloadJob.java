package edu.montana.csci.csci366.archivecat.archiver.jobs;

import edu.montana.csci.csci366.archivecat.archiver.Archive;
import org.jsoup.nodes.Element;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CssDownloadJob extends AbstractDownloadJob {
    private String _fullPathToCss;

    public CssDownloadJob(Element element, Archive archive) {
        super(element, archive);
    }

    @Override
    public void downloadResource() throws Exception {
        // TODO - download the CSS resource to a local file
        //        hint: generate a SHA of the URL for the CSS file to create a
        //              unique file name
        var baseName = getArchive().computeSHA1(getURL());
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(URI.create(getURL())).build();

        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

        _fullPathToCss = getArchive().saveFile(baseName + ".css", response.body());
    }

    @Override
    public void updateElement() {
        getElement().attr("href", "/" + _fullPathToCss);
    }

    @Override
    protected String getURL() {
        // TODO implement
        return getElement().attr("abs:href");
    }
}
