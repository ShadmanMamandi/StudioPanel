package ir.valinejad.dao;

import ir.valinejad.commons.HibernateUtil;
import ir.valinejad.entity.ArContent;
import ir.valinejad.entity.Wedding;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by arash on 5/9/17.
 * a.valinejad@gmail.com
 */
public class ArContentDAO {
    public static final String TABLE_NAME = "ArContent";

    public static ArContent findById(Long id) throws Exception{
        return (ArContent) new HibernateUtil().findById(ArContent.class, id);
    }

    public static void delete(Long id) throws Exception{
        new HibernateUtil().delete(findById(id));
    }

    public static ArContent save(Wedding wedding, String photoUrl, String videoUrl,String width,String height) throws Exception{
        ArContent ar = new ArContent();
        ar.setWedding(wedding);
        ar.setPhoto_url(photoUrl);
        ar.setVideoUrl(videoUrl);
        ar.setWidth(width);
        ar.setHeight(height);
        return (ArContent) new HibernateUtil().save(ar);
    }

    public static List<ArContent> findByWedding(final Long fkWeddingId) throws Exception {
        return (List<ArContent>) new HibernateUtil().findListByCondition(TABLE_NAME, "t", "id", "t.wedding.id=?", new LinkedList(){{add(fkWeddingId);}}, false);
    }
}
