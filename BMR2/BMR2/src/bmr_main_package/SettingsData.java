/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bmr_main_package;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Krzysztof
 */
public class SettingsData
{

    // Class attributes
    private String xmlFileName = "";
    private BMR_Settings bmrSettings = null;

    // Constructors
    public SettingsData()
    {
        // Defult value
        xmlFileName = CommonDefs.defaultPath + CommonDefs.fileSeparator + CommonDefs.defaultFolder + CommonDefs.fileSeparator + CommonDefs.defaultFile;
    }

    // Accessors to class attributes and public methods
    public String getXMLFileName()
    {
        return xmlFileName;
    }

    public void setXMLFileName(String fileName)
    {
        this.xmlFileName = fileName;
    }

    public BMR_Settings getBMR_Settings()
    {
        return bmrSettings;
    }

    public void setBMR_Settings(BMR_Settings bmrSettings)
    {
        this.bmrSettings = bmrSettings;
    }

    public void ReadSettingsXmlFile(String fileName)
    {
        boolean fileFound = false;

        if ((fileFound = CommonDefs.CheckFileExistence(fileName)) == true)
        {
            this.xmlFileName = fileName;
        } else
        {
            fileFound = CommonDefs.CheckFileExistence(this.xmlFileName);
        }

        if (fileFound == true)
        {
            xml2Settings();
        } else
        {
            // Default settings
            String xmlMeasuresFile = CommonDefs.defaultPath + CommonDefs.fileSeparator + CommonDefs.defaultFolder + CommonDefs.fileSeparator + CommonDefs.defaultMeasuresFile;
            bmrSettings = new BMR_Settings("Enter your name", xmlMeasuresFile, MeasureScope.ALL.toString());
        }
    }

    public void WriteSettingsXmlFile()
    {
        if (CommonDefs.CheckFileExistence(this.xmlFileName) == false)
        {
            // Creates folder only. File is created by file writing function - settings2Xml
            CommonDefs.CreateFolder(this.xmlFileName);
        }
        settings2Xml();
    }


    // Read XML file. Store read data in this.bmrSettings
    private void xml2Settings()
    {
        try
        {
            JAXBContext jc = JAXBContext.newInstance(BMR_Settings.class);
            Unmarshaller u = jc.createUnmarshaller();

            // Read XML file
            this.bmrSettings = (BMR_Settings) u.unmarshal(new File(this.xmlFileName));
        } catch (JAXBException e)
        {
            System.out.println("Exception: " + e.getMessage());
        } catch (Exception e)
        {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    // Write this.bmrSettings to XML file
    private void settings2Xml()
    {
        try
        {
            JAXBContext jc = JAXBContext.newInstance(BMR_Settings.class);
            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Write XML file
            m.marshal(this.bmrSettings, new File(this.xmlFileName));

        } catch (JAXBException e)
        {
            System.out.println("Exception: " + e.getMessage());
        } catch (Exception e)
        {
            System.out.println("Exception: " + e.getMessage());
        }
    }

}
