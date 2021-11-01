package edu.montana.csci.csci366.archivecat.archiver.jobs;

import edu.montana.csci.csci366.archivecat.archiver.Archive;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDownloadJob implements  DownloadJob {

    private static Logger LOGGER = LoggerFactory.getLogger(AbstractDownloadJob.class);

    private final Element _element;
    private final Archive _archive;

    /**
     * @return the *full* (not relative) URL of the content that should be downloaded by this element
     */
    protected abstract String getURL();

    public AbstractDownloadJob(Element element, Archive archive) {
        _element = element;
        _archive = archive;
    }

    @Override
    public void run() {
        LOGGER.info("Starting download of " + getURL() + " by " + this.getClass().getSimpleName());
        try {
            downloadResource();
            LOGGER.info("Done downloading " + getURL());
        } catch (Exception e) {
            LOGGER.error("Error downloading " + getURL(), e);
            throw new RuntimeException(e);
        }
    }

    /*
     * What pattern is this?
     */
    public static AbstractDownloadJob getJobFor(Element element, Archive archive) {
        String tagName = element.tagName();
        if (tagName.equals("img")) {
            return new ImageDownloadJob(element, archive);
        } else if (tagName.equals("link") && "stylesheet".equals(element.attr("rel"))) {
            return new CssDownloadJob(element, archive);
        } else if (tagName.equals("script") && element.hasAttr("src")) {
            return new JavascriptDownloadJob(element, archive);
        }
        return null;
    }

    public Element getElement() {
        return _element;
    }

    public Archive getArchive() {
        return _archive;
    }
}
