package edu.montana.csci.csci366.archivecat.archiver.jobs;

import edu.montana.csci.csci366.archivecat.archiver.Archive;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.*;
import spark.Spark;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JobsTest {

    @BeforeEach
    @AfterEach
    public void clearArchive() {
        Archive.clearAll();
    }


    @BeforeAll
    public static void setupServer() {
        Spark.port(8989);
        Spark.get("/css", (request, response) -> {
            return "some css";
        });
        Spark.get("/js", (request, response) -> {
            return "some javascript";
        });
        Spark.get("/gif", (request, response) -> {
            response.header("Content-Type", "image/gif");
            return new byte[]{1};
        });
        Spark.get("/jpeg", (request, response) -> {
            response.header("Content-Type", "image/jpeg");
            return new byte[]{1};
        });
    }

    @AfterAll
    public static void shutdownServer() {
        Spark.stop();
    }

    @Test
    public void testCSSDownload() throws Exception {
        Document parse = Jsoup.parse("<html><head><link rel='stylesheet' href='http://localhost:8989/css'></head></html>");
        Element link = parse.getElementsByTag("link").first();

        Archive testArchive = new Archive("test");

        CssDownloadJob cssDownloadJob = new CssDownloadJob(link, testArchive);
        cssDownloadJob.downloadResource();

        byte[] content = Archive.getContent(testArchive.getBaseSHA() + "/" + testArchive.computeSHA1(cssDownloadJob.getURL()) + ".css");
        assertEquals("some css", new String(content));
    }

    @Test
    public void testJSDownload() throws Exception {
        Document parse = Jsoup.parse("<html><head><script src='http://localhost:8989/js'></script></head></html>");
        Element link = parse.getElementsByTag("script").first();

        Archive testArchive = new Archive("test");

        JavascriptDownloadJob jsDownloadJob = new JavascriptDownloadJob(link, testArchive);
        jsDownloadJob.downloadResource();

        byte[] content = Archive.getContent(testArchive.getBaseSHA() + "/" + testArchive.computeSHA1(jsDownloadJob.getURL()) + ".js");
        assertEquals("some javascript", new String(content));
    }

    @Test
    public void testGifDownload() throws Exception {
        Document parse = Jsoup.parse("<html><body><img src='http://localhost:8989/gif'></body></html>");
        Element link = parse.getElementsByTag("img").first();

        Archive testArchive = new Archive("test");

        ImageDownloadJob imageDownloadJob = new ImageDownloadJob(link, testArchive);
        imageDownloadJob.downloadResource();

        byte[] content = Archive.getContent(testArchive.getBaseSHA() + "/" + testArchive.computeSHA1(imageDownloadJob.getURL()) + ".gif");
        assertEquals(content[0], 1);
    }

    @Test
    public void testJpegDownload() throws Exception {
        Document parse = Jsoup.parse("<html><body><img src='http://localhost:8989/jpeg'></body></html>");
        Element link = parse.getElementsByTag("img").first();

        Archive testArchive = new Archive("test");

        ImageDownloadJob imageDownloadJob = new ImageDownloadJob(link, testArchive);
        imageDownloadJob.downloadResource();

        byte[] content = Archive.getContent(testArchive.getBaseSHA() + "/" + testArchive.computeSHA1(imageDownloadJob.getURL()) + ".jpeg");
        assertEquals(content[0], 1);
    }

}
