package edu.montana.csci.csci366.archivecat.archiver;

import edu.montana.csci.csci366.archivecat.archiver.Archive;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArchiveTest {

    @BeforeEach
    @AfterEach
    public void clearArchive() {
        Archive.clearAll();
    }

    @Test
    public void testSHAIsGeneratedCorrectly() {
        Archive archive = new Archive("demo");
        String str = archive.computeSHA1("foo");
        assertEquals(str, "0beec7b5ea3f0fdbc95d0dd47f3c5bc275da8a33");
    }

    @Test
    public void testCanReadAndWriteFilesToArchive() throws IOException {
        Archive archive = new Archive("demo");
        archive.saveFile("test", new byte[]{1});
        byte[] fromArchive = Archive.getContent(archive.getBaseSHA() + "/test");
        assertEquals(1, fromArchive.length);
        assertEquals(((byte) 1), fromArchive[0]);
    }

    @Test
    public void saveFileReturnsFullPathToContent() throws IOException {
        Archive archive = new Archive("demo");
        String fullPath = archive.saveFile("test", new byte[]{1});
        assertEquals(fullPath, "archives/" + archive.getBaseSHA() + "/test");
    }


}
