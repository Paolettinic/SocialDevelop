package socialdevelop.data.model;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.util.List;

/**
 * @author Davide De Marco
 */
public interface Progetto {
    
    int getKey();
    
    void setNome(String nome);
    
    String getNome();
    
    void setDescrizione(String descrizione);
    
    String getDescrizione();
    
    void setTasks(List<Task> tasks);
    
    List<Task> getTasks() throws DataLayerException;
    
    void setUtente(Utente utente);
    
    Utente getUtente() throws DataLayerException;
    
    void copyFrom(Progetto progetto) throws DataLayerException;
    
    void setDirty(boolean dirty);

    boolean isDirty();
    
}
