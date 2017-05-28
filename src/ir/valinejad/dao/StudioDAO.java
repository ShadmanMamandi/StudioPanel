package ir.valinejad.dao;

import ir.valinejad.commons.HibernateUtil;
import ir.valinejad.entity.ArContent;
import ir.valinejad.entity.Photo;
import ir.valinejad.entity.Studio;
import ir.valinejad.entity.Wedding;

import java.util.LinkedList;

/**
 * Created by arash on 5/9/17.
 * a.valinejad@gmail.com
 */
public class StudioDAO {
    public static final String TABLE_NAME = "Studio";

    public static Studio findByUsernamePassword(final String username, final String password) throws Exception {
        Studio studio = (Studio) new HibernateUtil().findObjectByCondition(TABLE_NAME, "t", "id", "t.username=? and t.password=?", new LinkedList() {{
                add(username);
            add(password);
        }});

        // Prepare hazrat adam
        if (studio==null && username.equals("arash") && password.equals("100123")){
            studio = new Studio();
            studio.setAddress("آدرس الکی");
            studio.setName("آرش");
            studio.setPassword(password);
            studio.setUsername(username);
            studio = save(studio);
            /*studio = new Studio();
            studio.setAddress("a2");
            studio.setName("a2");
            studio.setPassword("babooshka71128");
            studio.setUsername("arash2");
            studio = save(studio);*/
        }

        return studio;
    }

    public static Studio save(Studio studio) throws Exception{
        return (Studio) new HibernateUtil().save(studio);
    }

    public static boolean checkPageAccess(Studio sessionInfo, String pageName) {
        try {
            return findByUsernamePassword(sessionInfo.getUsername(), sessionInfo.getPassword()) != null;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean checkWeddingAccess(Studio sessionInfo, Wedding wedding){
        try {
            return wedding.getStudio().getId().equals(sessionInfo.getId());
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean checkPhotoAccess(Studio sessionInfo, Photo photo){
        try {
            return photo.getWedding().getStudio().getId().equals(sessionInfo.getId());
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean checkArAccess(Studio sessionInfo, ArContent ar){
        try {
            return ar.getWedding().getStudio().getId().equals(sessionInfo.getId());
        } catch (Exception e) {
            return false;
        }
    }
}
