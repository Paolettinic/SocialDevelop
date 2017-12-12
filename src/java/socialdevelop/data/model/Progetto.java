/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socialdevelop.data.model;
import it.univaq.f4i.iw.framework.data.DataLayerException;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Davide De Marco
 */
public interface Progetto {
    
    int getKey();
    
    void setNome();
    
    String getNome();
    
    void setDescrizione();
    
    String getDescrizione();
    
    void setTask(List<Task> task);
    
    List<Task> getTask() throws DataLayerException;
    
    void setDiscussione(Map<Discussione, Integer> discussione);
    
    Map<Discussione, Integer> getDiscussione() throws DataLayerException;
    
    void setUtente(Utente utente);
    
    Utente getUtente() throws DataLayerException;
    
    void copyFrom(Progetto progetto) throws DataLayerException;
    
    void setDirty(boolean dirty);

    boolean isDirty();
    
}
