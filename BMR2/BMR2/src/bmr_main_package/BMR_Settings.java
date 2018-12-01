/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bmr_main_package;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Krzysztof
 */
@XmlRootElement(name = "BMR_Settings")
public class BMR_Settings
{ 
    // Class attributes
    private List<Settings> allSettings = new ArrayList<>();
    private WHO_BloodPressureClassification bloodPressureClassification;

    // Class constructors
    public BMR_Settings()
    {
       int i = 0;
    }
    
    // Default data if XML file does not exist
    public BMR_Settings(String name, String fileName, String measureScope)
    {
        boolean rslt = allSettings.add(new Settings(name, fileName, measureScope));
 
        int i = 0;
        /*
        this.name = name;
        this.measuresXmlFile = fileName;
        this.measureScope = measureScope;
*/
    }
    
    
    // Accessors to class attributes and public methods
    @XmlElements(@XmlElement(name = "Settings"))
    public List<Settings> getAllSettings()
    {
        return allSettings;
    }

    public void setAllSettings(List<Settings> all)
    {
        this.allSettings = all;
    }

    @XmlElement(name = "WHO_BloodPressureClassification")
    public WHO_BloodPressureClassification getBloodPressureClassification()
    {
        return bloodPressureClassification;
    }

    public void setBloodPressureClassification(WHO_BloodPressureClassification b)
    {
        this.bloodPressureClassification = b;
    }

}
