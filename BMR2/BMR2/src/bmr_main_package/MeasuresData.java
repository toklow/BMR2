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
public class MeasuresData
{

    // Class attributes
    private String xmlFileName = "";
    private BMR_Measures bmrMeasures = null;

       
    public BMR_Measures getBMR_Measures()
    {
        return bmrMeasures;
    }

    public void setBMR_Measures(BMR_Measures bmrMeasures)
    {
        this.bmrMeasures = bmrMeasures;
    }
    
    
    // Accessors to class attributes and public methods
    public void ReadMeasuresXmlFile(String fileName)
    {
        this.xmlFileName = fileName;
        if (CommonDefs.CheckFileExistence(this.xmlFileName) == true)
        {
            xml2Measures();
        }
    }

    public void WriteMeasuresXmlFile()
    {
        if (CommonDefs.CheckFileExistence(this.xmlFileName) == false)
        {
            // Creates folder only. File is created by file writing function - measures2Xml
            CommonDefs.CreateFolder(this.xmlFileName);
        }
        measures2Xml();
    }

    // Read XML file. Store read data in this.bmrMeasures
    private void xml2Measures()
    {
        try
        {
            JAXBContext jc = JAXBContext.newInstance(BMR_Measures.class);
            Unmarshaller u = jc.createUnmarshaller();

            // Read XML file
            this.bmrMeasures = (BMR_Measures) u.unmarshal(new File(this.xmlFileName));
        } catch (JAXBException e)
        {
            System.out.println("Exception: " + e.getMessage());
        } catch (Exception e)
        {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    // Write this.bmrMeasures to XML file
    private void measures2Xml()
    {
        try
        {
            JAXBContext jc = JAXBContext.newInstance(BMR_Measures.class);
            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Write XML file
            m.marshal(this.bmrMeasures, new File(this.xmlFileName));

        } catch (JAXBException e)
        {
            System.out.println("Exception: " + e.getMessage());
        } catch (Exception e)
        {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
