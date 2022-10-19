package semantics;

import java.util.HashSet;
import java.util.Set;

import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;

import extensions.ExtensionEnumerator;
import parser.AFDataStructures;

public class Preferred extends Semantics{
    
    protected Admissibility admissibility;

    public Preferred(AFDataStructures structures){
        super(structures);
        latexGenericFormula = "adm_{Ar, att} := \\underset{a \\in Ar}{\\land} ( ( v_a \\rightarrow \\underset{(b, a) \\in att}{\\land} \\neg v_b) \\land (v_a \\rightarrow \\underset{(b, a) \\in att}{\\land} (\\underset{(c, b) \\in att}{\\lor} v_c)))";
        
        latexFormulaHeader = "adm_{Ar, att} := ";
        admissibility = new Admissibility(structures);

        explanation = ""; //TODO
    }

    public IVec<IVecInt> calculateReduction(){
        return admissibility.calculateReduction();
    }

    public Set<Set<String>> getExtensions() throws Exception{
        IVec<IVecInt> clauses = this.calculateReduction();
        ExtensionEnumerator extensionEnumerator = new ExtensionEnumerator(arguments, clauses);
        Set<Set<String>> extensions = extensionEnumerator.getExtensions();

        Set<Set<String>> preferredExtensions = new HashSet<Set<String>>();
        boolean isPreferredExtension;
        for(Set<String> extension1 : extensions){
            isPreferredExtension = true;
            for(Set<String> extension2: extensions){
                if((extension1 != extension2) && (extension2.containsAll(extension1))){
                    isPreferredExtension = false;
                    break;
                }
            }
            if(isPreferredExtension){
                preferredExtensions.add(extension1);
            }
        }

        return preferredExtensions;
    }
}
