package socialdevelop.data.model;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.util.GregorianCalendar;
import java.util.Map;

/**
 * @author Mario Vetrini
 */

public interface Task {
    
    int getKey();
    
    String getNome();
    
    void setNome(String nome);
    
    String getDescrizione();
    
    void setDescrizione(String descrizione);
    
    boolean getChiuso();
    
    void setChiuso(boolean chiuso);
    
    int getNumeroCorrenteCollaboratori();
    
    void setNumeroCorrenteCollaboratori(int numero_corrente_collaboratori);
    
    int getNumeroMassimoCollaboratori();
    
    void setNumeroMassimoCollaboratori(int numero_massimo_collaboratori);
    
    GregorianCalendar getDataInizio();
    
    void setDataInizio(GregorianCalendar data_inizio);
    
    GregorianCalendar getDataFine();
    
    void setDataFine(GregorianCalendar data_fine);
    
    // ----------
    
    Progetto getProgetto() throws DataLayerException;
    
    void setProgetto(Progetto progetto);
    
    Tipo getTipo() throws DataLayerException;
    
    void setTipo(Tipo tipo);
    
    Map<Skill, Integer> getSkills() throws DataLayerException;
    
    void setSkills(Map<Skill, Integer> skills);
    
    Map<Utente, Integer> getUtenti() throws DataLayerException;
    
    void setUtenti(Map<Utente, Integer> utenti);
    
    // ----------
    
    void copyFrom(Task task) throws DataLayerException;
    
    void setDirty(boolean dirty);
    
    boolean isDirty();
    
}
