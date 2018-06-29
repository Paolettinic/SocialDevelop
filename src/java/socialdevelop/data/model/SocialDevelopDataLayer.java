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
    
    void salvaCurriculum(FileSD curriculum) throws DataLayerException;
    
    void salvaDiscussione(Discussione discussione) throws DataLayerException;
    
    void salvaFileSD(FileSD filesd) throws DataLayerException;
    
    void salvaImmagine(FileSD immagine) throws DataLayerException;
    
    void salvaInvito(Invito invito) throws DataLayerException;
    
    void salvaMessaggio(Messaggio messaggio) throws DataLayerException;
    
    void salvaProgetto(Progetto progetto) throws DataLayerException;
    
    void salvaSkill(Skill skill) throws DataLayerException;
    
    void salvaTask(Task task) throws DataLayerException;
    
    void salvaTipo(Tipo tipo) throws DataLayerException;
    
    void salvaUtente(Utente utente) throws DataLayerException;
    
    void salvaAppartenenti(int ext_skill, int ext_tipo) throws DataLayerException;
    
    void salvaCoprenti(int voto, int ext_utente, int ext_task) throws DataLayerException;
    
    void salvaPreparazioni(int livello, int ext_utente, int ext_skill) throws DataLayerException;
    
    void salvaRequisiti(int livello, int ext_skill, int ext_task) throws DataLayerException;
    
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
    
    void eliminaAppartenenti(int ext_skill, int ext_tipo) throws DataLayerException;
    
    void eliminaCoprenti(int ext_utente, int ext_task) throws DataLayerException;
    
    void eliminaPreparazioni(int ext_utente, int ext_skill) throws DataLayerException;
    
    void eliminaRequisiti(int ext_skill, int ext_task) throws DataLayerException;
    
    // ----------
    
    FileSD getCurriculum(int curriculum_key) throws DataLayerException;
    
    Discussione getDiscussione(int discussione_key) throws DataLayerException;
    
    int getCountDiscussioni(Task task) throws DataLayerException;
    
    List<Discussione> getDiscussioni(Task task, int first, int perPage) throws DataLayerException;
    
    FileSD getFile(int file_key) throws DataLayerException;
    
    List<FileSD> getFiles(Utente utente) throws DataLayerException;
    
    FileSD getImmagine(int immagine_key) throws DataLayerException;
    
    Invito getInvito(int invito_key) throws DataLayerException;
    
    List<Invito> getInviti(Utente utente) throws DataLayerException;
    
    List<Invito> getInviti(Task task) throws DataLayerException;
    
    Messaggio getMessaggio(int messaggio_key) throws DataLayerException;
    
    List<Messaggio> getMessaggi(Discussione discussione) throws DataLayerException;
    
    Progetto getProgetto(int progetto_key) throws DataLayerException;
    
    List<Progetto> getProgetti(Utente utente) throws DataLayerException;
    
    List<Progetto> getProgetti(String filtro ,int first, int perPage) throws DataLayerException;
    
    int getCountProgetti(String filtro) throws DataLayerException;
    
    Skill getSkill(int skill_key) throws DataLayerException;
    
    List<Skill> getSkills() throws DataLayerException;
    
    List<Skill> getSkills(Tipo tipo) throws DataLayerException;
    
    Map<Skill, Integer> getSkills(Utente utente) throws DataLayerException;
    
    Map<Skill, Integer> getSkills(Task task) throws DataLayerException;
    
    List<Skill> getSkillsfiglie(Skill skill) throws DataLayerException;
    
    Task getTask(int task_key) throws DataLayerException;
    
    Map<Task, Integer> getTasks(Utente utente) throws DataLayerException;
    
    List<Task> getTasks(Progetto  progetto) throws DataLayerException;
    
    List<Task> getTasks(Tipo tipo) throws DataLayerException;
    
    Map<Task, Integer> getTasks(Skill skill) throws DataLayerException;
    
    Tipo getTipo(int tipo_key) throws DataLayerException;
    
    List<Tipo> getTipi() throws DataLayerException;
    
    List<Tipo> getTipi(Skill skill) throws DataLayerException;
    
    Utente getUtente(int utente_key) throws DataLayerException;
    
    Map<Utente, Integer> getUtenti(Task task) throws DataLayerException;
    
    List<Utente> getUtenti(String filtro, Map<Integer,Integer> skills, int first, int perPage) throws DataLayerException;
    
    int getCountUtenti(String filtro, Map<Integer,Integer> skills) throws DataLayerException;
    
    Map<Utente, Integer> getUtenti(Map<Skill, Integer> skills) throws DataLayerException;
    
}
