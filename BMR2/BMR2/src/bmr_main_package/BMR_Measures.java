/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bmr_main_package;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Krzysztof
 */
@XmlRootElement(name = "BMR_Measures")
public class BMR_Measures
{

    // Class attributes
    private List<Measure> allMeasures = new ArrayList<>();

    // Accessors to class attributes and public methods
    @XmlElements(
            @XmlElement(name = "Measure"))
    public List<Measure> getAllMeasures()
    {
        return allMeasures;
    }

    public void setAllMeasures(List<Measure> all)
    {
        this.allMeasures = all;
    }

    // Add new measure
    public void addNewMeasure(Measure measure)
    {
        this.allMeasures.add(measure);
    }

    // Sort all measures
    public void sortMeasures()
    {
        this.allMeasures.sort(null);
    }

    public Measure getMeasure(int itemNo)
    {
        Measure measure = null;

        if (itemNo >= 0 && itemNo < allMeasures.size())
        {
            measure = allMeasures.get(itemNo);
        }

        return measure;
    }

    public void removeMeasure(int itemNo)
    {
        if (itemNo >= 0 && itemNo < allMeasures.size())
        {
            allMeasures.remove(itemNo);
        }
    }

}
