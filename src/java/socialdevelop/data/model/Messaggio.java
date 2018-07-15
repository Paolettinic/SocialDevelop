package socialdevelop.data.model;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.util.GregorianCalendar;

/**
 * @author Davide De Marco
 */

public interface Messaggio {
    
    int getKey();
    
    void setTesto(String testo);
    
    String getTesto();
    
    void setData(GregorianCalendar data);
    
    GregorianCalendar getData();
    
    void setUtente(Utente utente);
    
    Utente getUtente() throws DataLayerException;
    
    void setDiscussione(Discussione discussione);
    
    Discussione getDiscussione() throws DataLayerException;
    
    void copyFrom(Messaggio messaggio) throws DataLayerException;
    
    void setDirty(boolean dirty);

    boolean isDirty();
    
}
