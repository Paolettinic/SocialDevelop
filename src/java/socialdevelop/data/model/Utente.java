package socialdevelop.data.model;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

/**
 * @author Mario
 */
public interface Utente {
    
    int getKey();
    
    String getNome();
    
    void setNome(String nome);
    
    String getCognome();
    
    void setCognome(String cognome);
    
    String getUsername();
    
    void setUsername(String username);
    
    String getEmail();
    
    void setEmail(String email);
    
    GregorianCalendar getDataNascita();
    
    void setDataNascita(GregorianCalendar datanascita);
    
    String getPassword();
    
    void setPassword(String password);
    
    String getBiografia();
    
    void setBiografia(String biografia);
    
    // ----------
    
    FileSD getCurriculum() throws DataLayerException;

    void setCurriculum(FileSD curriculum);
    
    FileSD getImmagine() throws DataLayerException;

    void setImmagine(FileSD immagine);
    
    Map<Skill, Integer> getSkills() throws DataLayerException;

    void setSkills(Map<Skill, Integer> skills);
    
    Map<Task, Integer> getTasks() throws DataLayerException;

    void setTasks(Map<Task, Integer> tasks);
    
    List<Progetto> getProgetti() throws DataLayerException;

    void setProgetti(List<Progetto> progetti);
    
    // ----------
    
    void copyFrom(Utente utente) throws DataLayerException;
    
    void setDirty(boolean dirty);

    boolean isDirty();
    
}
