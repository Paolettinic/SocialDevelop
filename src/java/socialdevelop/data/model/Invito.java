/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socialdevelop.data.model;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.util.GregorianCalendar;
/**
 *
 * @author Nicolò Paoletti
 */
public interface Invito {
	
	int getKey();
	
	Utente getUser() throws DataLayerException;
	
	void setUser(Utente user);
	
	String getMessage();
	
	void setMessage(String message);
	
	boolean getStatus();
	
	void setStatus(boolean status);
	
	boolean isOffer();
	
	void setOffer(boolean isOffer);
	
	Task getTask() throws DataLayerException;
	
	void setTask(Task task);
	
	Progetto getProgetto() throws DataLayerException;
	
	void setProgetto(Progetto progetto);
	
	GregorianCalendar getDate();
	
	void setDate(GregorianCalendar date);
	
}
