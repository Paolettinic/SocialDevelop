/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socialdevelop.data.model;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.io.File;

/**
 *
 * @author Nicolò Paoletti
 */
public interface FileSD {
	int getKey();
	
	String getNome();
	
	void setNome(String nome);
	
	File getPercorso();
	
	void setPercorso(File percorso);
	
	Utente getUtente() throws DataLayerException;
	
	void setUtente(Utente utente);
	
	int getUtenteKey();
	
	void setUtenteKey(int utente_key);
	
	boolean isDirty();
	
	void setDirty(boolean dirty);
	
}
