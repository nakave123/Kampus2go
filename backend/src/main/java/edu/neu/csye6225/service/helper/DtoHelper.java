package edu.neu.csye6225.service.helper;

import edu.neu.csye6225.util.ApiException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Component
public class DtoHelper {

    public static String getUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

    public static File convertMultiPartFileToFile(final MultipartFile multipartFile) throws ApiException {
        final File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (final IOException ex) {
            throw new ApiException("Error converting the multi-part file to file= "+ ex.getMessage());
        }
        return file;
    }

//    public static File convertToFile(final InputStream dataStream) {
//        String property = System.getProperty("user.dir");
//        Path tempFile = null;
//        try {
//            tempFile = Files.createTempFile(Paths.get(property+"/src/main/resources/images/"), "Upload_", ".jpeg");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try (FileOutputStream out = new FileOutputStream(tempFile.toFile())) {
//            byte[] buffer = new byte[1024];
//            int len;
//            while ((len = dataStream.read(buffer)) != -1) {
//                out.write(buffer, 0, len);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return tempFile.toFile();
//    }
//
//    public static String getPathToResources() {
//        return System.getProperty("user.dir") + "/src/main/resources/images/";
//    }
//
//    public static boolean contentEquals(InputStream input1, InputStream input2) throws IOException
//    {
//        if (!(input1 instanceof BufferedInputStream))
//        {
//            input1 = new BufferedInputStream(input1);
//        }
//        if (!(input2 instanceof BufferedInputStream))
//        {
//            input2 = new BufferedInputStream(input2);
//        }
//
//        int ch = input1.read();
//        while (-1 != ch)
//        {
//            int ch2 = input2.read();
//            if (ch != ch2)
//            {
//                return false;
//            }
//            ch = input1.read();
//        }
//
//        int ch2 = input2.read();
//        return (ch2 == -1);
//    }
}
