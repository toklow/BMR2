/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bmr_main_package;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;

/**
 *
 * @author Krzysztof
 */
public class CommonDefs
{

    public static final String defaultPath = System.getProperty("user.home");
    public static final String defaultFolder = "BMR";
    public static final String defaultFile = "BMR_Settings.xml";
    public static final String defaultMeasuresFile = "BMR_Measures.xml";
    public static final String defaultXlsFile = "BMR_Measures.xls";
    public static final String fileSeparator = System.getProperty("file.separator");
    public static final DateFormat localeDateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
    public static final DateFormat localeHourFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
    public static final SimpleDateFormat localeDateHourFormat = (SimpleDateFormat)DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);

    /*
    <WHO_BloodPressureClassification>
        <Category Color="Red" DiastolicFrom="110" Id="5" Name="Severe hypertension (Grade 3)" SystolicFrom="180"/>
        <Category Color="Orange" DiastolicFrom="100" Id="4" Name="Moderate hypertension (Grade 2)" SystolicFrom="160"/>
        <Category Color="Yellow" DiastolicFrom="90" Id="3" Name="Mild hypertension (Grade 1)" SystolicFrom="140"/>
        <Category Color="Green" DiastolicFrom="85" Id="2" Name="High normal" SystolicFrom="130"/>
        <Category Color="Green" DiastolicFrom="80" Id="1" Name="Normal" SystolicFrom="120"/>
        <Category Color="Green" DiastolicFrom="0" Id="0" Name="Optimal" SystolicFrom="0"/>
    </WHO_BloodPressureClassification>
     */
    public static final Category[] categoriesWHO = new Category[]
    {
        new Category("5", "Severe hypertension (Grade 3)", "Red", "180", "110"),
        new Category("4", "Moderate hypertension (Grade 2)", "Orange", "160", "100"),
        new Category("3", "Mild hypertension (Grade 1)", "Yellow", "140", "90"),
        new Category("2", "High normal", "Green", "130", "85"),
        new Category("1", "Normal", "Green", "120", "80"),
        new Category("0", "Optimal", "Green", "0", "0")
    };

    public static enum YES_NO
    {
        YES, NO
    };

    public static enum FIELDS_COPY
    {
        EMPTY_TO_FIELDS, DATA_TO_FIELDS, FIELDS_TO_DATA
    };

    // Create file folder if needed
    public static boolean CreateFolder(String fileName)
    {
        String folder = new File(fileName).getParent();
        File file = new File(folder);
        boolean rslt = true;

        if (file.isDirectory() == false)
        {
            rslt = file.mkdir();
        }
        return rslt;
    }

    // Private methods
    public static boolean CheckFileExistence(String fileName)
    {
        File f = null;
        boolean rslt = false;

        try
        {
            f = new File(fileName);
            rslt = f.exists();
        } catch (Exception e)
        {
            return false;
        }
        return rslt;
    }

    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    
    public static boolean YesNoBox(String infoMessage, String titleBar)
    {
        boolean rslt = false;
        
        int dialogResult = JOptionPane.showConfirmDialog (null, infoMessage, titleBar, JOptionPane.YES_NO_OPTION);
        if(dialogResult == JOptionPane.YES_OPTION)
        {
            rslt = true;
        }
        return rslt;
    }
    
    public static int stringToInt(String s)
    {
        int rslt = 0;

        try
        {
            rslt = Integer.parseInt(s);
        } catch (Exception e)
        {
            return 0;
        }
        return rslt;
    }

    
    public static Date stringToDateHour(String sDate)
    {
        Date rsltDate = getNow();

        if (sDate.length() > 0)
        {
            try
            {
                rsltDate = localeDateHourFormat.parse(sDate);
            } catch (ParseException e)
            {
                System.out.println(e.getMessage());
                return rsltDate;
            }
        }

        return rsltDate;
    }    
    
    public static Date stringToDate(String sDate)
    {
        Date rsltDate = getNow();

        if (sDate.length() > 0)
        {
            try
            {
                rsltDate = localeDateFormat.parse(sDate);
            } catch (ParseException e)
            {
                System.out.println(e.getMessage());
                return rsltDate;
            }
        }

        return rsltDate;
    }

   
    public static Date stringToHour(String sHour)
    {
        Date rsltDate = getNow();

        if (sHour.length() > 0)
        {
            try
            {
                rsltDate = localeHourFormat.parse(sHour);
            } catch (ParseException e)
            {
                System.out.println(e.getMessage());
            }
        }

        return rsltDate;
    }

    // Converts Date to Locale string
    public static String dateToString(Date date)
    {
        return localeDateFormat.format(date);
        // return localeDateFormat.parse(date);
        //return CommonDefs.dateToString(date, "yyyyMMdd");
    }

    // Converts Hours from Date to 24 hours string
    public static String hourToString(Date date)
    {
        return localeHourFormat.format(date);
        //return CommonDefs.dateToString(date, "HH:mm");
    }

    // String format examples: yyyyMMdd, HH:mm
    private static String dateToString(Date date, String format)
    {
        SimpleDateFormat ft = new SimpleDateFormat(format);
        return ft.format(date);
    }

    public static Date getNow()
    {
        Calendar cal = Calendar.getInstance();
        //cal.set(Calendar.HOUR_OF_DAY, 0);
        //cal.set(Calendar.MINUTE, 0);
        //cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    public static Calendar dateToCalendar(Date date)
    {
        //Date dat = this.localeDateFormat.parse(this.comboDate.getText().trim()); 
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal;
    }
    
    
    /*    
    public class DateFormatExample {
    public static void main(String[] args) {
        Date date = Calendar.getInstance().getTime();

        // Display a date in day, month, year format
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String today = formatter.format(date);
        System.out.println("Today : " + today);

        // Display date with day name in a short format
        formatter = new SimpleDateFormat("EEE, dd/MM/yyyy");
        today = formatter.format(date);
        System.out.println("Today : " + today);

        // Display date with a short day and month name
        formatter = new SimpleDateFormat("EEE, dd MMM yyyy");
        today = formatter.format(date);
        System.out.println("Today : " + today);

        // Formatting date with full day and month name and show time up to
        // milliseconds with AM/PM
        formatter = new SimpleDateFormat("EEEE, dd MMMM yyyy, hh:mm:ss.SSS a");
        today = formatter.format(date);
        System.out.println("Today : " + today);
    }
}
     */
}
