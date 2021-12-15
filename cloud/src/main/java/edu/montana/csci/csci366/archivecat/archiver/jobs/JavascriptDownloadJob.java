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
        var baseName = getArchive().computeSHA1(getURL());
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(URI.create(getURL())).build();

        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

        _fullPathToJS = getArchive().saveFile(baseName + ".js", response.body());
    }

    @Override
    public void updateElement() {
        // TODO implement
        getElement().attr("src", "/" + _fullPathToJS);
    }

    @Override
    protected String getURL() {
        // TODO implement
        return getElement().attr("abs:src");
    }
}
