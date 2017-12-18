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
    
    Map<Discussione, Integer> getDiscussioniByTask(Task task) throws DataLayerException;
    
    FileSD getFile(int file_key) throws DataLayerException;
    
    List<FileSD> getFilesByUtente(int utente_key) throws DataLayerException;
    
    FileSD getImmagine(int immagine_key) throws DataLayerException;
    
    Invito getInvito(int invito_key) throws DataLayerException;
    
    List<Invito> getInvitiByUtente(int utente_key) throws DataLayerException;
    
    List<Invito> getInvitiByTask(int task_key) throws DataLayerException;
    
    Messaggio getMessaggio(int messaggio_key) throws DataLayerException;
    
    List<Messaggio> getMessaggioByDiscussione(Discussione discussione);
    
    Progetto getProgetto(int progetto_key) throws DataLayerException;
    
    List<Progetto> getProgettiByUtente(int utente_key);

    Skill getSkill(int skill_key) throws DataLayerException;
    
    List<Skill> getSkillsByTipo(int tipo_key) throws DataLayerException;
    
    Map<Skill, Integer> getSkillsByUtente(int utente_key);
    
    Map<Skill, Integer> getSkillsByTask(int task_key);
    
    List<Skill> getSkillsfiglie(int skill_key);
    
    Task getTask(int task_key) throws DataLayerException;
    
    Map<Task, Integer> getTasksByUtente(int utente_key);
    
    List<Task> getTasksByProgetto(int  progetto_key);
    
    List<Task> getTasksByTipo(int tipo_key);
    
    Map<Task, Integer> getTasksBySkill(int skill_key);
    
    Tipo getTipo(int tipo_key) throws DataLayerException;
    
    List<Tipo> getTipiBySkill(Skill skill);
    
    Utente getUtente(int utente_key) throws DataLayerException;
    
    Map<Utente, Integer> getUtentiByTask(int task_key);
    
    Map<Utente, Integer> getUtentiBySkill(int skill_key);
    
}
