package socialdevelop.data.model;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.util.GregorianCalendar;

/**
 * @author Nicolò Paoletti
 */
public interface Invito {
	
    int getKey();
    
    String getMessaggio();

    void setMessaggio(String messaggio);
    
    GregorianCalendar getData();

    void setData(GregorianCalendar date);
    
    String getStato();

    void setStato(String stato);
    
    boolean getOfferta();

    void setOfferta(boolean offerta);

    Utente getUtente() throws DataLayerException;

    void setUtente(Utente user);

    Task getTask() throws DataLayerException;

    void setTask(Task task);

    void copyFrom(Invito invito) throws DataLayerException;
    
    void setDirty(boolean dirty);

    boolean isDirty();
        
}
