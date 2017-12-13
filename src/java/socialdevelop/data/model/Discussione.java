package socialdevelop.data.model;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.util.List;
import java.util.GregorianCalendar;

/**
 * @author Davide De Marco
 */
public interface Discussione {
    
    int getKey();
    
    void setTitolo(String titolo);
    
    String getTitolo();
    
    void setPubblica(boolean pubblica);
    
    boolean getPubblica();
    
    void setData(GregorianCalendar data);
    
    GregorianCalendar getData();
    
    void setMessaggi(List<Messaggio> messaggi);
    
    List<Messaggio> getMessaggi() throws DataLayerException;
    
    void setTask(Task task);
    
    Task getTask() throws DataLayerException;
    
    void setUtente(Utente utente);
    
    Utente getUtente() throws DataLayerException;
    
    void copyFrom(Discussione discussione) throws DataLayerException;
    
    void setDirty(boolean dirty);

    boolean isDirty(); 
    
}