/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socialdevelop.data.model;
import it.univaq.f4i.iw.framework.data.DataLayerException;

import java.util.Calendar;

/**
 *
 * @author Davide De Marco
 */
public interface Messaggio {
    
    int getId();
    
    void setTesto();
    
    String getTesto();
    
    void setData();
    
    Calendar getData();
    
    void setUtente(Utente utente);
    
    Utente getUtente() throws DataLayerException;
    
    void setDiscussione(Discussione discussione);
    
    Discussione getDiscussione() throws DataLayerException;
    
    void copyFrom(Messaggio messaggio) throws DataLayerException;
    
    void setDirty(boolean dirty);

    boolean isDirty();
    
}
