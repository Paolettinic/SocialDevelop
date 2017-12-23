package socialdevelop.data.model;

import it.univaq.f4i.iw.framework.data.DataLayer;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.util.List;
import java.util.Map;

/**
 * @authors Nicolò Paoletti, Mario Vetrini
 */
public interface SocialDevelopDataLayer extends DataLayer {
    
    FileSD creaCurriculum();
    
    Discussione creaDiscussione();
    
    FileSD creaFile();
    
    FileSD creaImmagine();
	
    Invito creaInvito();
    
    Messaggio creaMessaggio();
    
    Progetto creaProgetto();
    
    Skill creaSkill();
    
    Task creaTask();

    Tipo creaTipo();
    
    Utente creaUtente();
    
    // ----------
    
    int salvaCurriculum(FileSD curriculum) throws DataLayerException;
    
    int salvaDiscussione(Discussione discussione) throws DataLayerException;
    
    int salvaFileSD(FileSD filesd) throws DataLayerException;
    
    int salvaImmagine(FileSD immagine) throws DataLayerException;
    
    int salvaInvito(Invito invito) throws DataLayerException;
    
    int salvaMessaggio(Messaggio messaggio) throws DataLayerException;
    
    int salvaProgetto(Progetto progetto) throws DataLayerException;
    
    int salvaSkill(Skill skill) throws DataLayerException;
    
    int salvaTask(Task task) throws DataLayerException;
    
    int salvaTipo(Tipo tipo) throws DataLayerException;
    
    int salvaUtente(Utente utente) throws DataLayerException;
    
    // ----------
    
    void eliminaCurriculum(FileSD curriculum) throws DataLayerException;
    
    void eliminaDiscussione(Discussione discussione) throws DataLayerException;
    
    void eliminaFileSD(FileSD filesd) throws DataLayerException;
    
    void eliminaImmagine(FileSD immagine) throws DataLayerException;
    
    void eliminaInvito(Invito invito) throws DataLayerException;
    
    void eliminaMessaggio(Messaggio messaggio) throws DataLayerException;
    
    void eliminaProgetto(Progetto progetto) throws DataLayerException;
    
    void eliminaSkill(Skill skill) throws DataLayerException;
    
    void eliminaTask(Task task) throws DataLayerException;
    
    void eliminaTipo(Tipo tipo) throws DataLayerException;
    
    void eliminaUtente(Utente utente) throws DataLayerException;
    
    // ----------
    
    FileSD getCurriculum(int curriculum_key) throws DataLayerException;
    
    Discussione getDiscussione(int discussione_key) throws DataLayerException;
    
    Map<Discussione, Integer> getDiscussioni(Task task) throws DataLayerException;
    
    FileSD getFile(int file_key) throws DataLayerException;
    
    List<FileSD> getFiles(Utente utente) throws DataLayerException;
    
    FileSD getImmagine(int immagine_key) throws DataLayerException;
    
    Invito getInvito(int invito_key) throws DataLayerException;
    
    List<Invito> getInviti(Utente utente) throws DataLayerException;
    
    List<Invito> getInviti(Task task) throws DataLayerException;
    
    Messaggio getMessaggio(int messaggio_key) throws DataLayerException;
    
    List<Messaggio> getMessaggi(Discussione discussione);
    
    Progetto getProgetto(int progetto_key) throws DataLayerException;
    
    List<Progetto> getProgetti(Utente utente);
    
    List<Progetto> getProgetti(String filtro);

    Skill getSkill(int skill_key) throws DataLayerException;
    
    List<Skill> getSkills(Tipo tipo) throws DataLayerException;
    
    Map<Skill, Integer> getSkills(Utente utente);
    
    Map<Skill, Integer> getSkills(Task task);
    
    List<Skill> getSkillsfiglie(int skill_key);
    
    Task getTask(int task_key) throws DataLayerException;
    
    Map<Task, Integer> getTasks(Utente utente);
    
    List<Task> getTasks(Progetto  progetto);
    
    List<Task> getTasks(Tipo tipo);
    
    Map<Task, Integer> getTasks(Skill skill);
    
    Tipo getTipo(int tipo_key) throws DataLayerException;
    
    List<Tipo> getTipi(Skill skill);
    
    Utente getUtente(int utente_key) throws DataLayerException;
    
    Map<Utente, Integer> getUtenti(Task task);
    
    List<Utente> getUtenti(String filtro);
    
    Map<Utente, Integer> getUtenti(Map<Skill, Integer> skills);
    
}
