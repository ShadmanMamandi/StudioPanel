package ir.valinejad.dao;

import ir.valinejad.commons.HibernateUtil;
import ir.valinejad.commons.Util;
import ir.valinejad.entity.Log;
import ir.valinejad.entity.Studio;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by arash on 8/20/16.
 * a.valinejad@gmail.com
 */
public class LogDAO {
    public static long log(String type, Studio studio, String title, String description){
        long id;
        try {
            Log log = new Log();
            log.setStudio(studio);
            log.setLogType(type);
            log.setTitle(title);
            log.setLogTimestamp(new Timestamp(new Date().getTime()));
            log.setDescription(description);
            id = ((Log)new HibernateUtil().save(log)).getId();
        } catch (Exception e) {
            System.out.println("*********** Exception in LogDAO.log()");
            e.printStackTrace();
            id = 0;
        }

        return id;

    }

    public static long logError(Studio studio, String title, String description, Exception e){
        long id;
        try {
            StringBuilder sb = new StringBuilder();
            if (description != null)
                sb.append(description).append("<br>\r\n");
            if (e != null)
                sb.append(e.getMessage()).append("<br>\r\n").append(Util.getStackTrace(e));
            id = log(Log.TYPE_ERROR, studio, title, sb.toString());
        } catch (Exception e1) {
            System.out.println("*********** Exception in LogDAO.logError()");
            e1.printStackTrace();
            id = 0;
        }

        return id;
    }
}
