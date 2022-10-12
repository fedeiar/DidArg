package view;

import org.scilab.forge.jlatexmath.ParseException;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

public class Latex {
    
    //Representa una fórmula matemática lógica que se mostrará en un TexIcon
    private TeXFormula formula;
    //Una implementación de Icon que pintará la formula que la creó.
    private TeXIcon icon;
    private String math;

    public Latex(){
        
    }


    public TeXIcon actualizarIconLaTex(String math, int valor){        
        try {
            this.math = math;            
            this.formula = new TeXFormula(this.math);
            this.icon = this.formula.new TeXIconBuilder().setStyle(TeXConstants.STYLE_DISPLAY)
                    .setSize(valor)
                    .setWidth(TeXConstants.UNIT_PIXEL, Float.MAX_VALUE, TeXConstants.ALIGN_LEFT)
                    .setIsMaxWidth(true)
                    .setInterLineSpacing(TeXConstants.UNIT_PIXEL, 20f).build();
            return this.icon;           
                        
        } catch (ParseException e) {
            System.err.println("Error: "+ e.getMessage());
            return this.icon = null;
        }
    
    }
}
