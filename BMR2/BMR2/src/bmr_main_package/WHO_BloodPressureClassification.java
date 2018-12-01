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
@XmlRootElement(name="WHO_BloodPressureClassification")
public class WHO_BloodPressureClassification
{
    private List<Category> categories = new ArrayList<>();
    
       
    @XmlElements(@XmlElement(name="Category"))
    public List<Category> getCategories() {
        return categories;
    }
 
    public void setCategories(List<Category> all) {
        this.categories = all;
    }
    
}
