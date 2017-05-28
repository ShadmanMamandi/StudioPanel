package ir.valinejad.commons;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by arash on 8/22/16.
 * a.valinejad@gmail.com
 */
public class FileUpload {
    private static final int THRESHOLD_SIZE = 1024 * 1024 * 3; // 3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
    private static final int REQUEST_SIZE = 1024 * 1024 * 50; // 50MB
    public static final String FILE_TYPE_1 = "123456789fileType_1_123456789";
    public static final String FILE_TYPE_2 = "123456789fileType_2_123456789";
    public static final double PX_TO_MM=25.4/72; //dpi=72 , each inch=25.4 mm

    @SuppressWarnings("rawtypes")
    //Map<httpParameter, value>
    public static Map<String, String> uploadFile1File(HttpServletRequest request, String folderName, String fileName) throws Exception {
        Map<String, String> formValues = new HashMap<String, String>();
        // configures some settings
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(THRESHOLD_SIZE);
        File f = new File(System.getProperty("java.io.tmpdir"));
        factory.setRepository(f);

        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(MAX_FILE_SIZE);
        upload.setSizeMax(REQUEST_SIZE);
        // creates the directory if it does not exist
        File uploadDir = new File(folderName);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        try {
            // parses the request's content to extract file data
            List formItems = upload.parseRequest(request);

            Iterator iter = formItems.iterator();
            // iterates over form's fields
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                // processes only fields that are not form fields
                if (!item.isFormField()) {
                    if (item.getSize() != 0) {
                        String fileType = item.getName().substring(item.getName().lastIndexOf(".") + 1);
                        if (!fileType.equalsIgnoreCase("gif") && !fileType.equalsIgnoreCase("png")) // we accept gif,png and every thing else will change to jpg (security reason)
                            fileType = "jpg";
                        formValues.put(FILE_TYPE_1, fileType);
                        String filePath = folderName + "/" + fileName + "." + formValues.get(FILE_TYPE_1);
                        File storeFile = new File(filePath);
                        // saves the file on disk
                        item.write(storeFile);
                    }
                }
                else{
                    formValues.put(item.getFieldName(), Util.solveUtfProblem(item.getString()));
                }
            }
            request.setAttribute("message",
                    "Upload has been done successfully!");
        } catch (Exception ex) {
            request.setAttribute("message",
                    "There was an error: " + ex.getMessage());
        }

        return formValues;
    }

    @SuppressWarnings("rawtypes")
    //Map<httpParameter, value>
    public static Map<String, String> uploadFile2File(HttpServletRequest request, String folderName, String fileName1, String fileName2) throws Exception {
        Map<String, String> formValues = new HashMap<String, String>();
        // configures some settings
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(THRESHOLD_SIZE);
        File f = new File(System.getProperty("java.io.tmpdir"));
        factory.setRepository(f);

        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(MAX_FILE_SIZE);
        upload.setSizeMax(REQUEST_SIZE);
        // creates the directory if it does not exist
        File uploadDir = new File(folderName);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        try {
            // parses the request's content to extract file data
            List formItems = upload.parseRequest(request);

            Iterator iter = formItems.iterator();
            // iterates over form's fields
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                // processes only fields that are not form fields
                if (!item.isFormField()) {
                    if (item.getSize() != 0) {
                        String fileName = item.getFieldName().equalsIgnoreCase("f1") ? fileName1 : fileName2;
                        String fileTypeName = item.getFieldName().equals("f1") ? FILE_TYPE_1 : FILE_TYPE_2;
                        String fileType = item.getName().substring(item.getName().lastIndexOf(".") + 1);
                        if (!fileType.equalsIgnoreCase("gif") && !fileType.equalsIgnoreCase("png") && !fileType.equalsIgnoreCase("mp4")) // we accept gif,png,mp4 and every thing else will change to jpg (security reason)
                            fileType = "jpg";
                        formValues.put(fileTypeName, fileType);
                        String filePath = folderName + "/" + fileName + "." + formValues.get(fileTypeName);
                        File storeFile = new File(filePath);
                        // saves the file on disk
                        item.write(storeFile);
                    }
                }
                else{
                    formValues.put(item.getFieldName(), Util.solveUtfProblem(item.getString()));
                }
            }
            request.setAttribute("message",
                    "Upload has been done successfully!");
        } catch (Exception ex) {
            request.setAttribute("message",
                    "There was an error: " + ex.getMessage());
        }

        return formValues;
    }

}
