/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bmr_main_package;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Krzysztof
 */
@XmlRootElement(name = "Settings")
public class Settings
{
       
    // <Settings MeasureScope="PRESSURE" MeasuresXmlFile="Nazwa pliku" Name="Krzysztof Tokłowicz test"/>
    private String name = "";
    private String measuresXmlFile = "";
    private String measureScope = "";       

    public Settings()
    {
        this.name = "";
        this.measuresXmlFile = "";
        this.measureScope = MeasureScope.ALL.toString();
    }
    public Settings(String name, String fileName, String measureScope)
    {
        this.name = name;
        this.measuresXmlFile = fileName;
        this.measureScope = measureScope;
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

    
    @XmlAttribute(name = "MeasuresXmlFile")
    public String getMeasuresXmlFile()
    {
        return measuresXmlFile;
    }

    public void setMeasuresXmlFile(String s)
    {
        this.measuresXmlFile = s;
    }

    @XmlAttribute(name = "MeasureScope")
    public String getMeasureScope()
    {
        return measureScope;
    }

    public void setMeasureScope(String s)
    {
        this.measureScope = s;
    }

}
