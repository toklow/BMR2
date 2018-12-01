/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bmr_main_package;

import java.util.Date;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Krzysztof
 */
@XmlRootElement(name = "Measure")
public class Measure implements Comparable
{

    /*
    Class attributes - Values stored in xml file
<Measure Date="2016-12-24" Hour="14:23" PressureCategory="3" Systolic="140" Diastolic="90" Pulse="70" Arrhythmia="NO" Glucose="100" EmptyStomach="NO"/>
     */
    private String date = "";
    private String hour = "";
    private String systolic = "";
    private String diastolic = "";
    private String pulse = "";
    private String arrhythmia = "";
    private String glucose = "";
    private String emptyStomach = "";
    private String comment = "";

    public Measure()
    {
        this.date = "";
        this.arrhythmia = CommonDefs.YES_NO.NO.toString();
        this.emptyStomach = CommonDefs.YES_NO.NO.toString();
    }

    public Measure(boolean newMeasure)
    {
        Date date = CommonDefs.getNow();
        this.date = CommonDefs.dateToString(date);
        this.hour = CommonDefs.hourToString(date);

        this.arrhythmia = CommonDefs.YES_NO.NO.toString();
        this.emptyStomach = CommonDefs.YES_NO.NO.toString();
    }

    @XmlAttribute(name = "Date")
    public String getDate()
    {
        return date;
    }

    public Date getDateAsDate()
    {
        return CommonDefs.stringToDate(this.date);
    }

    public void setDate(String s)
    {
        this.date = s;
    }

    public void setDate(Date date)
    {
        this.date = CommonDefs.dateToString(date);
    }

    @XmlAttribute(name = "Hour")
    public String getHour()
    {
        return hour;
    }

    public Date getHourAsDate()
    {
        return CommonDefs.stringToHour(this.hour);
    }

    public void setHour(String s)
    {
        this.hour = s;
    }

    public void setHour(Date hour)
    {
        this.hour = CommonDefs.hourToString(hour);
    }

    @XmlAttribute(name = "Systolic")
    public String getSystolic()
    {
        return systolic;
    }
    
    public int getSystolicAsInt()
    {
        return CommonDefs.stringToInt(this.systolic);
    }
    
    public void setSystolic(String s)
    {
        this.systolic = s;
    }

    @XmlAttribute(name = "Diastolic")
    public String getDiastolic()
    {
        return diastolic;
    }
    
    public int getDiastolicAsInt()
    {
        return CommonDefs.stringToInt(this.diastolic);
    }
    
    public void setDiastolic(String s)
    {
        this.diastolic = s;
    }

    @XmlAttribute(name = "Pulse")
    public String getPulse()
    {
        return pulse;
    }
    
    public int getPulseAsInt()
    {
        return CommonDefs.stringToInt(this.pulse);
    }
    
    public void setPulse(String s)
    {
        this.pulse = s;
    }

    @XmlAttribute(name = "Arrhythmia")
    public String getArrhythmia()
    {
        return arrhythmia;
    }

    public boolean getArrhythmiaAsBoolean()
    {
        if (this.arrhythmia.length() == 0)
        {
            this.arrhythmia = CommonDefs.YES_NO.NO.toString();
        }

        if (CommonDefs.YES_NO.valueOf(this.arrhythmia) == CommonDefs.YES_NO.YES)
        {
            return true;
        } else
        {
            return false;
        }
    }

    public String getArrhythmiaAsYes()
    {
        if (this.arrhythmia.length() == 0)
        {
            this.arrhythmia = CommonDefs.YES_NO.NO.toString();
        }

        if (CommonDefs.YES_NO.valueOf(this.arrhythmia) == CommonDefs.YES_NO.YES)
        {
            return arrhythmia;
        } else
        {
            return "";
        }
    }

    public void setArrhythmia(String s)
    {
        this.arrhythmia = s;
    }

    public void setArrhythmia(boolean b)
    {
        if (b == true)
        {
            this.arrhythmia = CommonDefs.YES_NO.YES.toString();
        } else
        {
            this.arrhythmia = CommonDefs.YES_NO.NO.toString();
        }
    }

    @XmlAttribute(name = "Glucose")
    public String getGlucose()
    {
        return glucose;
    }
    
    public int getGlucoseAsInt()
    {
        return CommonDefs.stringToInt(this.glucose);
    }

    public void setGlucose(String s)
    {
        this.glucose = s;
    }

    @XmlAttribute(name = "EmptyStomach")
    public String getEmptyStomach()
    {
        return emptyStomach;
    }

    public boolean getEmptyStomachAsBoolean()
    {
        if (CommonDefs.YES_NO.valueOf(this.emptyStomach) == CommonDefs.YES_NO.YES)
        {
            return true;
        } else
        {
            return false;
        }

    }

    public String getEmptyStomachAsYes()
    {
        if (this.emptyStomach.length() == 0)
        {
            this.emptyStomach = CommonDefs.YES_NO.NO.toString();
        }

        if (CommonDefs.YES_NO.valueOf(this.emptyStomach) == CommonDefs.YES_NO.YES)
        {
            return emptyStomach;
        } else
        {
            return "";
        }

    }

    public void setEmptyStomach(String s)
    {
        this.emptyStomach = s;
    }

    public void setEmptyStomach(boolean b)
    {
        if (b == true)
        {
            this.emptyStomach = CommonDefs.YES_NO.YES.toString();
        } else
        {
            this.emptyStomach = CommonDefs.YES_NO.NO.toString();
        }
    }

        
    @XmlAttribute(name = "Comment")
    public String getComment()
    {
        return comment;
    }

    public void setComment(String s)
    {
        this.comment = s;
    }
    
        
    public Date getDateHourAsDate()
    {
        return CommonDefs.stringToDateHour(this.date + " " + this.hour);
    }
    
    private String getDateHour()
    {
        return this.date + this.hour;
    }

    public int compareTo(Object o)
    {
        Measure otherMeasure = (Measure) o;
        return -this.getDateHour().compareTo(otherMeasure.getDateHour()); // -x inversed compare (sorting by decreasing value)
    }

    
    public Category getCategoryWHO()
    {
        Category item = null;

        // Array sorted in descending order
        for (int i = 0; i < CommonDefs.categoriesWHO.length; i++)
        {
            item = CommonDefs.categoriesWHO[i];
            if (this.getSystolicAsInt() >= CommonDefs.stringToInt(item.getSystolicFrom())
                    || this.getDiastolicAsInt() >= CommonDefs.stringToInt(item.getDiastolicFrom()))
            {
                break;
            }
        }

        return item;
    }
    
    
    public Category getCategoryWHO(String systolic, String diastolic)
    {
        Category item = null;

        // Array sorted in descending order
        for (int i = 0; i < CommonDefs.categoriesWHO.length; i++)
        {
            item = CommonDefs.categoriesWHO[i];
            if (CommonDefs.stringToInt(systolic) >= CommonDefs.stringToInt(item.getSystolicFrom())
                    || CommonDefs.stringToInt(diastolic) >= CommonDefs.stringToInt(item.getDiastolicFrom()))
            {
                break;
            }
        }

        return item;
    }
    
}
