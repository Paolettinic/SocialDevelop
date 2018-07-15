package socialdevelop.data.model;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

/**
 * @author Mario Vetrini
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
    
    Curriculum getCurriculum() throws DataLayerException;
    
    void setCurriculum(Curriculum curriculum);
    
    Immagine getImmagine() throws DataLayerException;
    
    void setImmagine(Immagine immagine);
    
    Map<Skill, Integer> getSkills() throws DataLayerException;
    
    void setSkills(Map<Skill, Integer> skills);
    
    Map<Task, Integer> getTasks() throws DataLayerException;
    
    void setTasks(Map<Task, Integer> tasks);
    
    List<Progetto> getProgetti() throws DataLayerException;
    
    void setProgetti(List<Progetto> progetti);
    
    List<Invito> getInviti() throws DataLayerException;
    
    void setInviti(List<Invito> inviti);
    
    // ----------
    
    void copyFrom(Utente utente) throws DataLayerException;
    
    void setDirty(boolean dirty);
    
    boolean isDirty();
    
}
