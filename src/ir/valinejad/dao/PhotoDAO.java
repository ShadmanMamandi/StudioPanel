package ir.valinejad.dao;

import ir.valinejad.commons.HibernateUtil;
import ir.valinejad.entity.Photo;

import javax.servlet.ServletContext;
import java.util.*;

/**
 * Created by arash on 5/9/17.
 * a.valinejad@gmail.com
 */
public class PhotoDAO {
    public static final String TABLE_NAME = "Photo";

    public static void delete(Long id) throws Exception {
        new HibernateUtil().delete(findById(id));
    }

    public static Photo findById(Long id) throws Exception {
        return (Photo) new HibernateUtil().findById(Photo.class, id);
    }

    public static String getSubImageFolder(Date date, Long fkStudioId, Long fkWeddingId) throws Exception {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return String.valueOf(fkStudioId) + "/" + String.valueOf(calendar.get(Calendar.YEAR)) + "/" + String.valueOf(fkWeddingId) + "/";
    }

    public static String getWebImageFolder() {
        return "ib/";
    }

    public static String getBaseImageFolder(ServletContext application) throws Exception {
        return application.getRealPath("/") + getWebImageFolder();
    }

    public static Photo save(String photoUrl, String content, Long fkWeddingId) throws Exception {
        Photo photo = new Photo();
        photo.setPhoto_url(photoUrl);
        photo.setContent(content);
        System.out.println("-----------------------------------------------------------");
        System.out.println("-----------------------------------------------------------");
        System.out.println("-----------------------------------------------------------");

        System.out.println(content);
        System.out.println("-----------------------------------------------------------");
        System.out.println("-----------------------------------------------------------");
        System.out.println("-----------------------------------------------------------");
        photo.setWedding(WeddingDAO.findById(fkWeddingId));
        return (Photo) new HibernateUtil().save(photo);
    }

    public static List<Photo> findByWedding(final Long fkWeddingId) throws Exception {
        return (List<Photo>) new HibernateUtil().findListByCondition(TABLE_NAME, "t", "id", "t.wedding.id=?", new LinkedList() {{
            add(fkWeddingId);
        }}, false);
    }
}
