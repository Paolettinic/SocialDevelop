/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socialdevelop.data.model;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.util.List;

/**
 *
 * @author Nicolò Paoletti
 */
public interface Tipo {
	int getKey();
	
	String getNome();
	
	void setNome(String nome);
	
	List<Skill> getSkills() throws DataLayerException;
	
	boolean isDirty();
	
	void setDirty(boolean dirty);
	
	void copyFrom(Tipo tipo) throws DataLayerException;
}
