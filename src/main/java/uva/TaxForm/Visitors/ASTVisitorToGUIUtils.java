package uva.TaxForm.Visitors;

import java.awt.Component;
import java.awt.Container;

public abstract class ASTVisitorToGUIUtils {
	
	public static Component getComponentByName(Container container, String name) {
		Component returnComp = null;
		boolean abort = false;
		Component[] comps = container.getComponents();
		
		for (int i=0; i<comps.length && !abort; i++) {
			//System.out.println(comps[i].getName());
			if (name.equals(comps[i].getName())) {
				returnComp = comps[i];
				abort = true;
			} else {
				Container cont = (Container) comps[i];
				returnComp = getComponentByName(cont, name);
				if (returnComp != null) {
					abort = true;
				}
			}
		}
		return returnComp;
	}
}
