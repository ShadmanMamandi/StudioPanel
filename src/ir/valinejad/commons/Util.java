package ir.valinejad.commons;

import ir.valinejad.dao.LogDAO;
import ir.valinejad.entity.Studio;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Kasra on 6/12/2016.
 */
public class Util {
    public static String getStackTrace(Exception e){
        try {
            StringBuilder sb = new StringBuilder();
            if (e != null){
                if (e.getCause() != null)
                    sb.append(e.getCause().getMessage()).append("--");
                StackTraceElement[] stackTrace = e.getStackTrace();
                sb.append(e.toString()).append("<br>\r\n");
                for (StackTraceElement element : stackTrace)
                    sb.append(element.toString().startsWith("ir")?"**** ":"").append(element.toString()).append("<br>\r\n");
            }

            return sb.toString();
        } catch (Exception e1) {
            e1.printStackTrace();
            return "";
        }
    }


    public static String truncateString(String s, int maxLength) throws Exception{
        if (s != null)
            return s.substring(0, Math.min(s.length(), 10));
        else
            return null;
    }


    public static int getHourOfDay() {
        Date today = new Date();
        final Calendar cal = new GregorianCalendar();
        cal.setTime(today);
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    public static boolean isHourOfDayBetween(int from, int to) {
        return getHourOfDay() >= from && getHourOfDay() <= to;
    }

    public static void wait(int minutes){
        try {
            Thread.sleep(minutes * 60 * 1000);
        } catch (InterruptedException e) {
            LogDAO.logError(null, e.getMessage(), null, e);
        }
    }

    public static String makeCombo(String name, String id, String cssClass, String[] values, String[] titles, String selectedValue, Boolean readOnly){
        StringBuilder result = new StringBuilder();

        try {
            result.append("<select name='" + name + "' id='" + (id != null ? id : name + "_id") + "' class='" + cssClass + "' " + (readOnly ? " disabled " : "") + ">");
            for (int i=0; i<titles.length; i++){
                result.append("<option value='").append(values[i]).append("' ").append(selectedValue!=null && selectedValue.equals(values[i]) ? "selected" : "").append(" >");
                result.append(titles[i]);
                result.append("</option>");
            }
            result.append("</select>");

            // add a hidden input for disabled combos (disabled combos don't be included in http request)
            if (readOnly && selectedValue != null)
                result.append("\n<input type='hidden' name='" + name + "' value='" + selectedValue + "'>");
        } catch (Exception e) {
            return "Util.makeCombo: " + e.toString();
        }

        return result.toString();
    }

    public static String solveUtfProblem(String str) throws Exception{
        if (str != null)
            return new String(str.getBytes("ISO-8859-1"), "UTF-8");
        else
            return null;
    }

    public static void scale( String srcFile, int destWidth, int destHeight, String destFile) throws Exception{

        BufferedImage src = ImageIO.read(new File(srcFile));
        if (destWidth == 0)
            destWidth = src.getWidth();
        if (destHeight == 0)
            destHeight = src.getHeight();


        ColorModel dstCM = src.getColorModel();
        BufferedImage dst = new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster( destWidth, destHeight ), dstCM.isAlphaPremultiplied(), null);

        Image scaleImage = src.getScaledInstance( destWidth, destHeight, Image.SCALE_AREA_AVERAGING );
        Graphics2D g = dst.createGraphics();
        g.drawImage( scaleImage, 0, 0, destWidth, destHeight, null );
        g.dispose();

        try {
            ImageIO.write(dst, "JPG", new File(destFile));
        } catch (IOException e) {
            LogDAO.logError(null, "Util.scale", "", e);
        }

    }

    public static String getText(String str){
        return (str==null ? "" : str);
    }

    public static boolean isEmpty(String str){
        return (str==null || str.isEmpty());
    }

    public static String getAuthorizedLink(String page, String queryString, String title, Studio sessionInfo, boolean addPTag){
        String result;

        result = (addPTag?"<p class='spec'>":"") +"<a href='index.jsp?to="+page+queryString+"' class='rm'>"+title+"</a>"+ (addPTag?"</p>":"");
/*
        try {
            if (sessionInfo.getAuthorizedPages().get(page)!=null)
                result = (addPTag?"<p class='spec'>":"") +"<a href='index.jsp?to="+page+queryString+"' class='rm'>"+title+"</a>"+ (addPTag?"</p>":"");
            else
                result = "";
        } catch (Exception e) {
            long id = LogDAO.logError(sessionInfo, "Util.getAuthorizedLink()", null, e);
            result = "خطای نامشخص! کد خطا: "  + id;
        }
*/

        return result;
    }

    public static Date removeTimeFromDateObject(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
