package socialdevelop.data.impl;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.data.DataLayerMysqlImpl;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.sql.DataSource;
import socialdevelop.data.model.Discussione;
import socialdevelop.data.model.FileSD;
import socialdevelop.data.model.Invito;
import socialdevelop.data.model.Messaggio;
import socialdevelop.data.model.Progetto;
import socialdevelop.data.model.Skill;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Task;
import socialdevelop.data.model.Tipo;
import socialdevelop.data.model.Utente;

/**
 * @authors Mario Vetrini, Nicolò Paoletti, Davide De Marco
 */
public class SocialDevelopDataLayerMysqlImpl extends DataLayerMysqlImpl implements SocialDevelopDataLayer {
    
    private PreparedStatement sCurriculumByID, iCurriculum, uCurriculum, dCurriculum;
    private PreparedStatement sDiscussioneByID, sDiscussioniByTask, iDiscussione, uDiscussione, dDiscussione;
    private PreparedStatement sFileByID, sFilesByUtente, iFile, uFile, dFile;
    private PreparedStatement sImmagineByID, iImmagine, uImmagine, dImmagine;
    private PreparedStatement sInvitoByID, sInvitiByUtente, sInvitiByTask, iInvito, uInvito, dInvito;
    private PreparedStatement sMessaggioByID, sMessaggiByDiscussione, iMessaggio, uMessaggio, dMessaggio;
    private PreparedStatement sProgettoByID, sProgettiByUtente, sProgettiByFiltro, iProgetto, uProgetto, dProgetto;
    private PreparedStatement sSkillByID, sSkillsByTipo, sSkillsByUtente, sSkillsByTask, sSkillsFiglie, iSkill, uSkill, dSkill;
    private PreparedStatement sTaskByID, sTasksByUtente, sTasksByProgetto, sTasksByTipo, sTasksBySkill, iTask, uTask, dTask;
    private PreparedStatement sTipoByID, sTipiBySkill, iTipo, uTipo, dTipo;
    private PreparedStatement sUtenteByID, sUtentiByTask, sUtentiByFiltro, sUtentiBySkills, iUtente, uUtente, dUtente;
    // funzionalità del sito
    
    public SocialDevelopDataLayerMysqlImpl(DataSource datasource) throws SQLException, NamingException {
        super(datasource);
    }
    
    @Override
    public void init() throws DataLayerException {
        try {
            super.init();
            // select, insert, update e delete di tutte le entità più generiche funzionalità del sito
            sCurriculumByID = connection.prepareStatement("SELECT * FROM curriculum WHERE ID=?");
            iCurriculum = connection.prepareStatement("INSERT INTO curriculum", Statement.RETURN_GENERATED_KEYS);
            uCurriculum = connection.prepareStatement("UPDATE curriculum SET WHERE ID=?");
            dCurriculum = connection.prepareStatement("DELETE FROM curriculum WHERE ID=?");
            sDiscussioneByID = connection.prepareStatement("SELECT * FROM discussioni WHERE ID=?");
            sDiscussioniByTask = connection.prepareStatement("");
            iDiscussione = connection.prepareStatement("INSERT INTO discussioni", Statement.RETURN_GENERATED_KEYS);
            uDiscussione = connection.prepareStatement("UPDATE discussioni SET WHERE ID=?");
            iDiscussione = connection.prepareStatement("DELETE FROM discussioni WHERE ID=?");
            sFileByID = connection.prepareStatement("SELECT * FROM files WHERE ID=?");
            sFilesByUtente = connection.prepareStatement("");
            iFile = connection.prepareStatement("INSERT INTO files", Statement.RETURN_GENERATED_KEYS);
            uFile = connection.prepareStatement("UPDATE files SET WHERE ID=?");
            dFile = connection.prepareStatement("DELETE FROM files WHERE ID=?");
            sImmagineByID = connection.prepareStatement("SELECT * FROM immagini WHERE ID=?");
            iImmagine = connection.prepareStatement("INSERT INTO immagini", Statement.RETURN_GENERATED_KEYS);
            uImmagine = connection.prepareStatement("UPDATE immagini SET WHERE ID=?");
            dImmagine = connection.prepareStatement("DELETE FROM immagini WHERE ID=?");
            sInvitoByID = connection.prepareStatement("SELECT * FROM inviti WHERE ID=?");
            sInvitiByUtente = connection.prepareStatement("");
            sInvitiByTask = connection.prepareStatement("");
            iInvito = connection.prepareStatement("INSERT INTO inviti", Statement.RETURN_GENERATED_KEYS);
            uInvito = connection.prepareStatement("UPDATE inviti SET WHERE ID=?");
            dInvito = connection.prepareStatement("DELETE FROM inviti WHERE ID=?");
            sMessaggioByID = connection.prepareStatement("SELECT * FROM messaggi WHERE ID=?");
            sMessaggiByDiscussione = connection.prepareStatement("");
            iMessaggio = connection.prepareStatement("INSERT INTO messaggi", Statement.RETURN_GENERATED_KEYS);
            uMessaggio = connection.prepareStatement("UPDATE messaggi SET WHERE ID=?");
            dMessaggio = connection.prepareStatement("DELETE FROM messaggi WHERE ID=?");
            sProgettoByID = connection.prepareStatement("SELECT * FROM progetti WHERE ID=?");
            sProgettiByUtente = connection.prepareStatement("");
            sProgettiByFiltro = connection.prepareStatement("");
            iProgetto = connection.prepareStatement("INSERT INTO progetti", Statement.RETURN_GENERATED_KEYS);
            uProgetto = connection.prepareStatement("UPDATE progetti SET WHERE ID=?");
            dProgetto = connection.prepareStatement("DELETE FROM progetti WHERE ID=?");
            sSkillByID = connection.prepareStatement("SELECT * FROM skills WHERE ID=?");
            sSkillsByTipo = connection.prepareStatement("");
            sSkillsByUtente = connection.prepareStatement("");
            sSkillsByTask = connection.prepareStatement("");
            sSkillsFiglie = connection.prepareStatement("");
            iSkill = connection.prepareStatement("INSERT INTO skills", Statement.RETURN_GENERATED_KEYS);
            uSkill = connection.prepareStatement("UPDATE skills SET WHERE ID=?");
            dSkill = connection.prepareStatement("DELETE FROM skills WHERE ID=?");
            sTaskByID = connection.prepareStatement("SELECT * FROM tasks WHERE ID=?");
            sTasksByUtente = connection.prepareStatement("");
            sTasksByProgetto = connection.prepareStatement("");
            sTasksByTipo = connection.prepareStatement("");
            sTasksBySkill = connection.prepareStatement("");
            iTask = connection.prepareStatement("INSERT INTO tasks", Statement.RETURN_GENERATED_KEYS);
            uTask = connection.prepareStatement("UPDATE tasks SET WHERE ID=?");
            dTask = connection.prepareStatement("DELETE FROM tasks WHERE ID=?");
            sTipoByID = connection.prepareStatement("SELECT * FROM tipi WHERE ID=?");
            sTipiBySkill = connection.prepareStatement("");
            iTipo = connection.prepareStatement("INSERT INTO tipi", Statement.RETURN_GENERATED_KEYS);
            uTipo = connection.prepareStatement("UPDATE tipi SET WHERE ID=?");
            dTipo = connection.prepareStatement("DELETE FROM tipi WHERE ID=?");
            sUtenteByID = connection.prepareStatement("SELECT * FROM utenti WHERE ID=?");
            sUtentiByTask = connection.prepareStatement("");
            sUtentiByFiltro = connection.prepareStatement("");
            sUtentiBySkills = connection.prepareStatement("");
            iUtente = connection.prepareStatement("INSERT INTO utenti", Statement.RETURN_GENERATED_KEYS);
            uUtente = connection.prepareStatement("UPDATE utenti SET WHERE ID=?");
            dUtente = connection.prepareStatement("DELETE FROM utenti WHERE ID=?");
        } catch (SQLException ex) {
            throw new DataLayerException("Error initializing SocialDevelop data layer", ex);
        }
    }

    @Override
    public FileSD creaCurriculum() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Discussione creaDiscussione() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FileSD creaFile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FileSD creaImmagine() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Invito creaInvito() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Messaggio creaMessaggio() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Progetto creaProgetto() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Skill creaSkill() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Task creaTask() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Tipo creaTipo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Utente creaUtente() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int salvaCurriculum(FileSD curriculum) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int salvaDiscussione(Discussione discussione) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int salvaFileSD(FileSD filesd) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int salvaImmagine(FileSD immagine) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int salvaInvito(Invito invito) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int salvaMessaggio(Messaggio messaggio) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int salvaProgetto(Progetto progetto) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int salvaSkill(Skill skill) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int salvaTask(Task task) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int salvaTipo(Tipo tipo) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int salvaUtente(Utente utente) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminaCurriculum(FileSD curriculum) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminaDiscussione(Discussione discussione) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminaFileSD(FileSD filesd) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminaImmagine(FileSD immagine) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminaInvito(Invito invito) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminaMessaggio(Messaggio messaggio) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminaProgetto(Progetto progetto) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminaSkill(Skill skill) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminaTask(Task task) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminaTipo(Tipo tipo) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminaUtente(Utente utente) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FileSD getCurriculum(int curriculum_key) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Discussione getDiscussione(int discussione_key) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Discussione, Integer> getDiscussioni(Task task) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FileSD getFile(int file_key) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FileSD> getFiles(Utente utente) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FileSD getImmagine(int immagine_key) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Invito getInvito(int invito_key) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Invito> getInviti(Utente utente) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Invito> getInviti(Task task) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Messaggio getMessaggio(int messaggio_key) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Messaggio> getMessaggi(Discussione discussione) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Progetto getProgetto(int progetto_key) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Progetto> getProgetti(Utente utente) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Progetto> getProgetti(String filtro) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Skill getSkill(int skill_key) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Skill> getSkills(Tipo tipo) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Skill, Integer> getSkills(Utente utente) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Skill, Integer> getSkills(Task task) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Skill> getSkillsfiglie(int skill_key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Task getTask(int task_key) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Task, Integer> getTasks(Utente utente) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Task> getTasks(Progetto progetto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Task> getTasks(Tipo tipo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Task, Integer> getTasks(Skill skill) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Tipo getTipo(int tipo_key) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Tipo> getTipi(Skill skill) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Utente getUtente(int utente_key) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Utente, Integer> getUtenti(Task task) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Utente, Integer> getUtenti(String filtro) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Utente, Integer> getUtenti(Map<Skill, Integer> skills) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void destroy() { // meglio chiudere i PreparedStamenent
        try {
            sCurriculumByID.close();
            iCurriculum.close();
            uCurriculum.close();
            dCurriculum.close();
            sDiscussioneByID.close();
            sDiscussioniByTask.close();
            iDiscussione.close();
            uDiscussione.close();
            iDiscussione.close();
            sFileByID.close();
            sFilesByUtente.close();
            iFile.close();
            uFile.close();
            dFile.close();
            sImmagineByID.close();
            iImmagine.close();
            uImmagine.close();
            dImmagine.close();
            sInvitoByID.close();
            sInvitiByUtente.close();
            sInvitiByTask.close();
            iInvito.close();
            uInvito.close();
            dInvito.close();
            sMessaggioByID.close();
            sMessaggiByDiscussione.close();
            iMessaggio.close();
            uMessaggio.close();
            dMessaggio.close();
            sProgettoByID.close();
            sProgettiByUtente.close();
            sProgettiByFiltro.close();
            iProgetto.close();
            uProgetto.close();
            dProgetto.close();
            sSkillByID.close();
            sSkillsByTipo.close();
            sSkillsByUtente.close();
            sSkillsByTask.close();
            sSkillsFiglie.close();
            iSkill.close();
            uSkill.close();
            dSkill.close();
            sTaskByID.close();
            sTasksByUtente.close();
            sTasksByProgetto.close();
            sTasksByTipo.close();
            sTasksBySkill.close();
            iTask.close();
            uTask.close();
            dTask.close();
            sTipoByID.close();
            sTipiBySkill.close();
            iTipo.close();
            uTipo.close();
            dTipo.close();
            sUtenteByID.close();
            sUtentiByTask.close();
            sUtentiByFiltro.close();
            sUtentiBySkills.close();
            iUtente.close();
            uUtente.close();
            dUtente.close();
        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }

}
