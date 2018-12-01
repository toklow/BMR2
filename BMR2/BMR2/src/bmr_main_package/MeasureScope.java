/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bmr_main_package;

/**
 *
 * @author Krzysztof
 */
public enum MeasureScope
{
    PRESSURE("Blood pressure only"), GLUCOSE("Glucose only"), ALL("Blood pressure and glucose");
    
    private final String description;
    
    private MeasureScope(String s)
    {
        this.description = s;
    }
    
    public String getDescription()
    {
        return this.description;
    }
    
}
