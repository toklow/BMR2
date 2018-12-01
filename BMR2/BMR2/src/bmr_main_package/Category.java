/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bmr_main_package;

import java.awt.Color;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author Krzysztof
 */
@XmlRootElement(name = "Category")
public class Category
{

    // <Category Id="5" Name= "Severe hypertension (Grade 3)" Color="Red" SystolicFrom="180" DiastolicFrom="110"/>
    private String id;
    private String name;
    private String color;
    private String systolicFrom;
    private String diastolicFrom;
            

    public Category()
    {
        this.id = "";
        this.name = "";
        this.color = "";
        this.systolicFrom = "";
        this.diastolicFrom = "";
        
    }
    public Category(String id, String name, String color, String systolic, String diastolic)
    {
        this.id = id;
        this.name = name;
        this.color = color;
        this.systolicFrom = systolic;
        this.diastolicFrom = diastolic;
    }


    @XmlAttribute(name = "Id")
    public String getId()
    {
        return id;
    }

    public void setId(String s)
    {
        this.id = s;
    }    
    
    
    @XmlAttribute(name = "Name")
    public String getName()
    {
        return name;
    }

    public void setName(String s)
    {
        this.name = s;
    }

    
    @XmlAttribute(name = "Color")
    public String getColor()
    {
        return color;
    }

    public void setColor(String s)
    {
        this.color = s;
    }    

    
    @XmlAttribute(name = "SystolicFrom")
    public String getSystolicFrom()
    {
        return systolicFrom;
    }

    public void setSystolicFrom(String s)
    {
        this.systolicFrom = s;
    }        
    
    
    @XmlAttribute(name = "DiastolicFrom")
    public String getDiastolicFrom()
    {
        return diastolicFrom;
    }

    public void setDiastolicFrom(String s)
    {
        this.diastolicFrom = s;
    } 
    
    public Color getColorObject()
    {
        Color rslt = Color.white;
        
        switch (this.color)
        {
            case "Red":
                rslt = Color.red;
                break;
            case "Orange":
                rslt = Color.orange;
                break; 
            case "Yellow":
                rslt = Color.yellow;
                break;                 
            case "Green":
                rslt = Color.green;
                break;         
            default:
                rslt = Color.white;
                break;          
        }
        
        return rslt;
    }
    
}
