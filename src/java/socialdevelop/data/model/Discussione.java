/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socialdevelop.data.model;
import it.univaq.f4i.iw.framework.data.DataLayerException;

import java.util.List;
import java.util.Map;
import java.util.Calendar;

/**
 *
 * @author Davide De Marco
 */
public interface Discussione {
    
    int getId();
    
    void setTitolo();
    
    String getTitolo();
    
    void setPubblica();
    
    int getPubblica();
    
    void setData();
    
    Calendar getData();
    
    void setMessaggio(List<Messaggio> messaggio);
    
    List<Messaggio> getMessaggio() throws DataLayerException;
    
    void setProgetto(Progetto progetto);
    
    Progetto getProgetto() throws DataLayerException;
    
    void setTask(Task task);
    
    Task getTask() throws DataLayerException;
    
    void setUtente(Utente utente);
    
    Utente getUtente() throws DataLayerException;
    
    void copyFrom(Discussione discussione) throws DataLayerException;
    
    void setDirty(boolean dirty);

    boolean isDirty(); 
    
}