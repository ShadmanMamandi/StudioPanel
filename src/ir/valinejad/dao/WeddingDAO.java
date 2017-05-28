package ir.valinejad.dao;

import ir.valinejad.commons.HibernateUtil;
import ir.valinejad.entity.Wedding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by arash on 5/9/17.
 * a.valinejad@gmail.com
 */
public class WeddingDAO {

    public static final String TABLE_NAME = "Wedding";

    public static Wedding findById(long id) throws Exception {
        return (Wedding) new HibernateUtil().findById(Wedding.class, id);
    }

    public static Wedding save(Wedding wedding) throws Exception {
        return (Wedding) new HibernateUtil().save(wedding);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    public static void delete(Long id) throws Exception {
        new HibernateUtil().delete(findById(id));
    }

    public static List<Wedding> findAll(final Long studioId) throws Exception {
        return (List<Wedding>) new HibernateUtil().findListByCondition(TABLE_NAME, "t", "id desc", "t.studio.id=?", new LinkedList() {{
            add(studioId);
        }}, false);
    }

    public static Wedding increaseAssetVersion(Wedding wedding) throws Exception {
        wedding.setAssetVersion(wedding.getAssetVersion() + 1);
        return save(wedding);
    }

    public static String getDate() throws Exception {
        Date currentDate = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        return formatter.format(currentDate);
    }

}
