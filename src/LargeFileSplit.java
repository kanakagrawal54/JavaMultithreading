//import org.apache.*;

        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.nio.file.Files;
        import java.util.ArrayList;
        import java.util.List;

class LargeFileSplit {

    private final static String TEMP_DIRECTORY = "D:\\temp\\";

    /**
     * Splits the given file into smaller files of specified size
     * @param largeFile the large file
     * @param maxChunkSize the size of the smaller files
     * @return list of chunked files
     * @throws IOException for file related errors
     */
    public List<File> splitBySize(File largeFile, int maxChunkSize) throws IOException {
        List<File> list = new ArrayList<>();
        try (InputStream in = Files.newInputStream(largeFile.toPath())) {
            final byte[] buffer = new byte[maxChunkSize];
            int dataRead = in.read(buffer);
            while (dataRead > -1) {
                File fileChunk = stageFile(buffer, dataRead);
                list.add(fileChunk);
                dataRead = in.read(buffer);
            }
        }
        return list;
    }

    /**
     * Splits the given file into specified number of smaller files
     * @param largeFile the large file
     * @param noOfFiles the number of files
     * @return list of chunked files
     * @throws IOException for file related errors
     */
    public List<File> splitByNumberOfFiles(File largeFile, int noOfFiles) throws IOException {
        return splitBySize(largeFile, getSizeInBytes(largeFile.length(), noOfFiles));
    }

    /**
     * Combines the given files in the order specified by the list
     * @param list the list of files to combine
     * @return a large file
     * @throws IOException for file related errors
     */
    public File join(List<File> list) throws IOException {
        File outPutFile = File.createTempFile("temp-", "-unsplit", new File(TEMP_DIRECTORY));
        FileOutputStream fos = new FileOutputStream(outPutFile);
        for (File file : list) {
            Files.copy(file.toPath(), fos);
        }
        fos.close();
        return outPutFile;
    }

    private File stageFile(byte[] buffer, int length) throws IOException {
        File outPutFile = File.createTempFile("temp-", "-split", new File(TEMP_DIRECTORY));
        try (FileOutputStream fos = new FileOutputStream(outPutFile)) {
            fos.write(buffer, 0, length);
        }
        return outPutFile;
    }

    private int getSizeInBytes(long totalBytes, int numberOfFiles) {
        if (totalBytes % numberOfFiles != 0) {
            totalBytes = ((totalBytes / numberOfFiles) + 1) * numberOfFiles;
        }
        long x = totalBytes / numberOfFiles;
        if (x > Integer.MAX_VALUE) {
            throw new NumberFormatException("Byte chunk too large");

        }
        return (int) x;
    }
}

//public class LargeFileSplitDriver {
//
//
//    public static void main(String[] args) throws IOException {
//        File input = new File("C:\\Users\\kthsi\\Downloads\\SampleCSVFile_5300kb.csv");
//        LargeFileSplit util = new LargeFileSplit();
//        File outPut1 = util.join(util.splitBySize(input, 1024_000));
//
//        try (InputStream in = Files.newInputStream(input.toPath()); InputStream out = Files.newInputStream(outPut1.toPath())) {
//            System.out.println(IOUtils.contentEquals(in, out));
//        }
//
//        File outPut2 = util.join(util.splitByNumberOfFiles(input, 17));
//
//
//        try (InputStream in = Files.newInputStream(input.toPath()); InputStream out = Files.newInputStream(outPut2.toPath())) {
//            System.out.println(IOUtils.contentEquals(in, out));
//        }
//
//    }
//}