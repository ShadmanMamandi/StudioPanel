package ir.valinejad.commons;

import ir.valinejad.dao.PhotoDAO;
import ir.valinejad.entity.Wedding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by shiva on 5/23/17.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println(getDate());
    }

    public static String getDate() throws Exception {
        Date currentDate = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(currentDate);
    }

}
