package edu.montana.csci.csci366.archivecat.archiver;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;

public class Archive {
    public static final byte[] EMPTY_BYTES = new byte[0];

    public static final String ARCHIVE_ROOT = "archives";
    private final String _url;
    private final String _sha;
    private final Path _root;

    public Archive(String originalURL) {
        _url = originalURL;
        _sha = computeSHA1(_url);
        try {
            _root = Files.createDirectories(Path.of(ARCHIVE_ROOT, _sha));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void clearAll() {
        Path path = Path.of(ARCHIVE_ROOT);
        if (path.toFile().exists()) {
            try {
                Files.walk(path)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static byte[] getContent(String archivePath) throws IOException {
        Path archiveFilePath = Path.of(ARCHIVE_ROOT, archivePath);
        if (Files.exists(archiveFilePath)) {
            return Files.readAllBytes(archiveFilePath);
        } else {
            return EMPTY_BYTES;
        }
    }

    public String computeSHA1(String url) {
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(url.getBytes());
            return String.format("%040x", new BigInteger(1, md.digest()));
        } catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }

    public String saveFile(String fileName, byte[] body) throws IOException {
        // safe the content to the archive root, followed by the name of this archive folder, followed by the file name
        String fullPath = ARCHIVE_ROOT + "/" + _sha + "/" + fileName;
        Path filePath = Path.of(fullPath);
        Files.write(filePath, body);
        return fullPath;
    }

    public String getRoot() {
        return _root.toAbsolutePath().toString();
    }

    public String getBaseSHA() {
        return _sha;
    }
}
