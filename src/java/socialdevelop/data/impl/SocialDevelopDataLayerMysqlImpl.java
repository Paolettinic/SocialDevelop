package socialdevelop.data.impl;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.data.DataLayerMysqlImpl;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
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
    // select, insert, update, delete, generiche funzionalità del sito, ecc...
    private PreparedStatement sCurriculumByID, iCurriculum, uCurriculum, dCurriculum;
    private PreparedStatement sDiscussioneByID, sDiscussioniByTask, iDiscussione, uDiscussione, dDiscussione;
    private PreparedStatement sFileByID, sFilesByUtente, iFile, uFile, dFile;
    private PreparedStatement sImmagineByID, iImmagine, uImmagine, dImmagine;
    private PreparedStatement sInvitoByID, sInvitiByUtente, sInvitiByTask, iInvito, uInvito, dInvito;
    private PreparedStatement sMessaggioByID, sMessaggiByDiscussione, iMessaggio, uMessaggio, dMessaggio;
    private PreparedStatement sProgettoByID, sProgettiByUtente, sProgettiByFiltro, iProgetto, uProgetto, dProgetto;
    private PreparedStatement sSkillByID, sSkills, sSkillsByTipo, sSkillsByUtente, sSkillsByTask, sSkillsFiglie, iSkill, uSkill, dSkill;
    private PreparedStatement sTaskByID, sTasksByUtente, sTasksByProgetto, sTasksByTipo, sTasksBySkill, iTask, uTask, dTask;
    private PreparedStatement sTipoByID, sTipi, sTipiBySkill, iTipo, uTipo, dTipo;
    private PreparedStatement sUtenteByID, sUtentiByTask, sUtentiByFiltro, sUtentiBySkill, iUtente, uUtente, dUtente;
    private PreparedStatement sAppartenenti, iAppartenenti, uAppartenenti, dAppartenenti;
    private PreparedStatement sCoprenti, iCoprenti, uCoprenti, dCoprenti;
    private PreparedStatement sPreparazioni, iPreparazioni, uPreparazioni, dPreparazioni;
    private PreparedStatement sRequisiti, iRequisiti, uRequisiti, dRequisiti;
    
    public SocialDevelopDataLayerMysqlImpl(DataSource datasource) throws SQLException, NamingException {
        super(datasource);
    }
    
    @Override
    public void init() throws DataLayerException {
        try {
            super.init();
            
            sCurriculumByID = connection.prepareStatement("SELECT * FROM curriculum WHERE ID=?");
            iCurriculum = connection.prepareStatement("INSERT INTO curriculum", Statement.RETURN_GENERATED_KEYS);
            uCurriculum = connection.prepareStatement("UPDATE curriculum SET WHERE ID=?");
            dCurriculum = connection.prepareStatement("DELETE FROM curriculum WHERE ID=?");
            
            sDiscussioneByID = connection.prepareStatement("SELECT * FROM discussioni WHERE ID=?");
            sDiscussioniByTask = connection.prepareStatement("");
            iDiscussione = connection.prepareStatement("INSERT INTO discussioni", Statement.RETURN_GENERATED_KEYS);
            uDiscussione = connection.prepareStatement("UPDATE discussioni SET WHERE ID=?");
            dDiscussione = connection.prepareStatement("DELETE FROM discussioni WHERE ID=?");
            
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
            
            sSkillByID = connection.prepareStatement("SELECT * FROM skills WHERE id =?");
            sSkills = connection.prepareStatement("SELECT * FROM skills");
            sSkillsByTipo = connection.prepareStatement("SELECT skills.* FROM skills INNER JOIN appartenenti ON skills.id = appartenenti.ext_skill WHERE appartenenti.ext_tipo = ?");
            sSkillsByUtente = connection.prepareStatement("SELECT skills.* FROM skills INNER JOIN preparazioni ON skills.id = preparazioni.ext_skill WHERE preparazioni.ext_utente = ?");
            sSkillsByTask = connection.prepareStatement("SELECT skills.* FROM skills INNER JOIN requisiti ON skills.id = requisiti.ext_skill WHERE requisiti.ext_task = ?");
            sSkillsFiglie = connection.prepareStatement("SELECT * FROM skills WHERE ext_padre = ?");
            iSkill = connection.prepareStatement("INSERT INTO skills (nome, ext_padre) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS);
            uSkill = connection.prepareStatement("UPDATE skills SET nome = ?, ext_padre = ? WHERE id = ?");
            dSkill = connection.prepareStatement("DELETE FROM skills WHERE id = ?");
            
            sTaskByID = connection.prepareStatement("SELECT * FROM tasks WHERE id = ?");
            sTasksByUtente = connection.prepareStatement("SELECT tasks.* FROM tasks INNER JOIN coprenti ON tasks.id = coprenti.ext_task WHERE coprenti.ext_utente = ?");
            sTasksByProgetto = connection.prepareStatement("SELECT * FROM tasks WHERE tasks.ext_progetto = ?");
            sTasksByTipo = connection.prepareStatement("SELECT * FROM tasks WHERE tasks.ext_tipo = ?");
            sTasksBySkill = connection.prepareStatement("SELECT tasks.* FROM tasks INNER JOIN requisiti ON tasks.id = requisiti.ext_task WHERE requisiti.ext_skill = ?");
            iTask = connection.prepareStatement("INSERT INTO tasks (nome, descrizione, chiuso, numero_corrente_collaboratori, numero_massimo_collaboratori, data_inizio, data_fine, ext_progetto, ext_tipo) VALUES(?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            uTask = connection.prepareStatement("UPDATE tasks SET nome = ?, descrizione = ?, chiuso = ?, numero_corrente_collaboratori = ?, numero_massimo_collaboratori = ?, data_inizio = ?, data_fine = ?, ext_progetto = ?, ext_tipo = ? WHERE id = ?");
            dTask = connection.prepareStatement("DELETE FROM tasks WHERE id = ?");
            
            sTipoByID = connection.prepareStatement("SELECT * FROM tipi WHERE ID=?");
            sTipi = connection.prepareStatement("SELECT * FROM tipi");
            sTipiBySkill = connection.prepareStatement("");
            iTipo = connection.prepareStatement("INSERT INTO tipi", Statement.RETURN_GENERATED_KEYS);
            uTipo = connection.prepareStatement("UPDATE tipi SET WHERE ID=?");
            dTipo = connection.prepareStatement("DELETE FROM tipi WHERE ID=?");
            
            sUtenteByID = connection.prepareStatement("SELECT * FROM utenti WHERE id = ?");
            sUtentiByTask = connection.prepareStatement("SELECT utenti.*, coprenti.voto FROM utenti INNER JOIN coprenti ON utente.id = coprenti.ext_utente WHERE coprenti.ext_task = ?");
            sUtentiByFiltro = connection.prepareStatement("SELECT * FROM utenti WHERE name LIKE ? OR cognome LIKE ? OR username = ?");
            sUtentiBySkill = connection.prepareStatement("SELECT utenti.*, preparazioni.livello FROM utenti INNER JOIN preparazioni ON utente.id = preparazioni.ext_utente WHERE preparazioni.ext_skill = ? AND preparazioni.livello >= ?");
            iUtente = connection.prepareStatement("INSERT INTO utenti (nome, cognome, username, email, data_nascita, password, biografia, ext_curriculum, ext_immagine) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            uUtente = connection.prepareStatement("UPDATE utenti SET nome = ?, cognome = ?, username = ?, email = ?, data_nascita = ?, password = ?, biografia = ?, ext_curriculum = ?, ext_immagine = ? WHERE id = ?");
            dUtente = connection.prepareStatement("DELETE FROM utenti WHERE id = ?");
            
            sAppartenenti = connection.prepareStatement("SELECT * FROM appartenenti WHERE ext_skill = ? AND ext_tipo = ?");
            iAppartenenti = connection.prepareStatement("INSERT INTO appartenenti (ext_skill, ext_tipo) VALUES(?, ?)");
            uAppartenenti = connection.prepareStatement("UPDATE appartenenti SET ext_skill = ?, ext_tipo = ? WHERE ext_skill = ? AND ext_tipo = ?");
            dAppartenenti = connection.prepareStatement("DELETE FROM appartenenti WHERE ext_skill = ? AND ext_tipo = ?");
            
            sCoprenti = connection.prepareStatement("SELECT * FROM coprenti WHERE ext_utente = ? AND ext_task = ?");
            iCoprenti = connection.prepareStatement("INSERT INTO coprenti (voto, ext_utente, ext_task) VALUES (?, ?, ?)");
            uCoprenti = connection.prepareStatement("UPDATE coprenti SET voto = ?, ext_utente = ?, ext_task = ? WHERE ext_utente = ? AND ext_task = ?");
            dCoprenti = connection.prepareStatement("DELETE FROM coprenti WHERE ext_utente = ? AND ext_task = ?");
            
            sPreparazioni = connection.prepareStatement("SELECT * FROM preparazioni WHERE ext_utente = ? AND ext_skill = ?");
            iPreparazioni = connection.prepareStatement("INSERT INTO preparazioni (livello, ext_utente, ext_skill) VALUES (?, ?, ?)");
            uPreparazioni = connection.prepareStatement("UPDATE preparazioni SET livello = ?, ext_utente = ?, ext_skill = ? WHERE ext_utente = ? AND ext_skill = ?");
            dPreparazioni = connection.prepareStatement("DELETE FROM preparazioni WHERE ext_utente = ? AND ext_skill = ?");
            
            sRequisiti = connection.prepareStatement("SELECT * FROM requisiti WHERE ext_skill = ? AND ext_task = ?");
            iRequisiti = connection.prepareStatement("INSERT INTO requisiti (livello, ext_skill, ext_task) VALUES (?, ?, ?)");
            uRequisiti = connection.prepareStatement("UPDATE requisiti SET livello = ?, ext_skill = ?, ext_task = ? WHERE ext_skill = ? AND ext_task = ?");
            dRequisiti = connection.prepareStatement("DELETE FROM requisiti WHERE id = ?");
            
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
        return new SkillImpl(this);
    }
    
    public Skill creaSkill(ResultSet rs) throws DataLayerException {
        try {
            SkillImpl skill = new SkillImpl(this);
            
            skill.setKey(rs.getInt("id"));
            skill.setNome(rs.getString("nome"));
            skill.setSkillPadreKey(rs.getInt("ext_padre"));
            
            return skill;
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile creare la skill dal ResultSet", ex);
        }
    }
    
    @Override
    public Task creaTask() {
        return new TaskImpl(this);
    }
    
    public Task creaTask(ResultSet rs) throws DataLayerException {
        try {
            Date data_inizio_sql;
            Date data_fine_sql;
            TaskImpl task = new TaskImpl(this);
            
            task.setKey(rs.getInt("id"));
            task.setNome(rs.getString("nome"));
            task.setDescrizione(rs.getString("descrizione"));
            task.setChiuso(rs.getBoolean("chiuso"));
            task.setNumeroCorrenteCollaboratori(rs.getInt("numero_corrente_collaboratori"));
            task.setNumeroMassimoCollaboratori(rs.getInt("numero_massimo_collaboratori"));
            task.setProgettoKey(rs.getInt("ext_progetto"));
            task.setTipoKey(rs.getInt("ext_tipo"));
            
            data_inizio_sql = rs.getDate("data_inizio");
            GregorianCalendar data_inizio = new GregorianCalendar();
            data_inizio.setTime(data_inizio_sql);
            task.setDataInizio(data_inizio);
            
            data_fine_sql = rs.getDate("data_fine");
            GregorianCalendar data_fine = new GregorianCalendar();
            data_fine.setTime(data_fine_sql);
            task.setDataFine(data_fine);
            
            return task;
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile creare il task dal ResultSet", ex);
        }
        
    }
    
    @Override
    public Tipo creaTipo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Utente creaUtente() {
        return new UtenteImpl(this);
    }
    
    public Utente creaUtente(ResultSet rs) throws DataLayerException {
        try {
            Date data_nascita_sql;
            UtenteImpl utente = new UtenteImpl(this);
            
            utente.setKey(rs.getInt("id"));
            utente.setNome(rs.getString("nome"));
            utente.setCognome(rs.getString("cognome"));
            utente.setUsername(rs.getString("username"));
            utente.setEmail(rs.getString("email"));
            utente.setPassword(rs.getString("password"));
            utente.setBiografia(rs.getString("biografia"));
            utente.setCurriculumKey(rs.getInt("ext_curriculum"));
            utente.setImmagineKey(rs.getInt("ext_immagine"));
            
            data_nascita_sql = rs.getDate("birthdate");
            GregorianCalendar data_nascita = new GregorianCalendar();
            data_nascita.setTime(data_nascita_sql);
            utente.setDataNascita(data_nascita);
            
            return utente;
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile creare l'utente dal ResultSet", ex);
        }
    }
    
    @Override
    public void salvaCurriculum(FileSD curriculum) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void salvaDiscussione(Discussione discussione) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void salvaFileSD(FileSD filesd) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void salvaImmagine(FileSD immagine) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void salvaInvito(Invito invito) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void salvaMessaggio(Messaggio messaggio) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void salvaProgetto(Progetto progetto) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void salvaSkill(Skill skill) throws DataLayerException {
        int key = skill.getKey();
        try {
            if (skill.getKey() > 0) { // update
                if (!skill.isDirty()) { // non facciamo nulla se l'oggetto non ha subito modifiche
                    return;
                }
                uSkill.setString(1, skill.getNome());
                if(skill.getPadre() != null) {
                    uSkill.setInt(2, skill.getPadre().getKey());
                } else {
                    uSkill.setNull(2, java.sql.Types.INTEGER);
                }
                uSkill.setInt(3, skill.getKey());
                uSkill.executeUpdate();
            } else { // insert
                iSkill.setString(1, skill.getNome());
                if(skill.getPadre() != null){
                    iSkill.setInt(2, skill.getPadre().getKey());
                } else {
                    iSkill.setNull(2, java.sql.Types.INTEGER);
                }
                if (iSkill.executeUpdate() == 1) {
                    try (ResultSet keys = iSkill.getGeneratedKeys()) {
                        if (keys.next()) {
                            key = keys.getInt(1);
                        }
                    }
                }
            }
            if (key > 0) {
                skill.copyFrom(getSkill(skill.getKey()));
            }
            skill.setDirty(false);
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile salvare la skill", ex);
        }
    }
    
    @Override
    public void salvaTask(Task task) throws DataLayerException {
        int key = task.getKey();
        try {
            if (key > 0) { // update
                if (!task.isDirty()) { //non facciamo nulla se l'oggetto non ha subito modifiche
                    return;
                }
                uTask.setString(1, task.getNome());
                uTask.setString(2, task.getDescrizione());
                uTask.setBoolean(3, task.getChiuso());
                uTask.setInt(4, task.getNumeroCorrenteCollaboratori());
                uTask.setInt(5, task.getNumeroMassimoCollaboratori());
                Date data_inizio = new Date(task.getDataInizio().getTimeInMillis());
                uTask.setDate(6, data_inizio);
                Date data_fine = new Date(task.getDataFine().getTimeInMillis());
                uTask.setDate(7, data_fine);
                if (task.getProgetto() != null) {
                    uTask.setInt(8, task.getProgetto().getKey());
                } else {
                    uTask.setNull(8, java.sql.Types.INTEGER);
                }
                if(task.getTipo() != null) {
                    uTask.setInt(9, task.getProgetto().getKey());
                } else {
                    uTask.setNull(9, java.sql.Types.INTEGER);
                }
                uTask.setInt(10, task.getKey());
                uTask.executeUpdate();
            } else { // insert
                iTask.setString(1, task.getNome());
                iTask.setString(2, task.getDescrizione());
                iTask.setBoolean(3, task.getChiuso());
                iTask.setInt(4, task.getNumeroCorrenteCollaboratori());
                iTask.setInt(5, task.getNumeroMassimoCollaboratori());
                Date data_inizio = new Date(task.getDataInizio().getTimeInMillis());
                iTask.setDate(6, data_inizio);
                Date data_fine = new Date(task.getDataFine().getTimeInMillis());
                iTask.setDate(7, data_fine);
                if (task.getProgetto() != null) {
                    iTask.setInt(8, task.getProgetto().getKey());
                } else {
                    iTask.setNull(8, java.sql.Types.INTEGER);
                }
                if(task.getTipo() != null) {
                    iTask.setInt(9, task.getProgetto().getKey());
                } else {
                    iTask.setNull(9, java.sql.Types.INTEGER);
                }
                if (iTask.executeUpdate() == 1) {
                    try (ResultSet keys = iTask.getGeneratedKeys()) {
                        if (keys.next()) {
                            key = keys.getInt(1);
                        }
                    }
                }
            }
            if (key > 0) {
                task.copyFrom(getTask(key));
            }
            task.setDirty(false);
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile salvare il task", ex);
        }
    }
    
    @Override
    public void salvaTipo(Tipo tipo) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void salvaUtente(Utente utente) throws DataLayerException {
        int key = utente.getKey();
        try {
            if (key > 0) { // update
                if (!utente.isDirty()) { // non facciamo nulla se l'oggetto non ha subito modifiche
                    return;
                }
                uUtente.setString(1, utente.getNome());
                uUtente.setString(2, utente.getCognome());
                uUtente.setString(3, utente.getUsername());
                uUtente.setString(4, utente.getEmail());
                if(utente.getDataNascita()!= null) {
                    Date data_nascita = new Date(utente.getDataNascita().getTimeInMillis());
                    uUtente.setDate(5, data_nascita);
                } else {
                    uUtente.setDate(5, null);
                }
                uUtente.setString(6, utente.getPassword());
                uUtente.setString(7, utente.getBiografia());
                if(utente.getCurriculum() != null) {
                    uUtente.setInt(6, utente.getCurriculum().getKey());
                } else {
                    uUtente.setNull(6, java.sql.Types.INTEGER);
                }
                if(utente.getImmagine() != null) {
                    uUtente.setInt(7, utente.getImmagine().getKey());
                } else {
                    uUtente.setNull(7, java.sql.Types.INTEGER);
                }
                uUtente.setInt(8, utente.getKey());
                uUtente.executeUpdate();
            } else { //insert
                iUtente.setString(1, utente.getNome());
                iUtente.setString(2, utente.getCognome());
                iUtente.setString(3, utente.getUsername());
                iUtente.setString(4, utente.getEmail());
                if(utente.getDataNascita()!= null) {
                    Date data_nascita = new Date(utente.getDataNascita().getTimeInMillis());
                    iUtente.setDate(5, data_nascita);
                } else {
                    iUtente.setDate(5, null);
                }
                iUtente.setString(6, utente.getPassword());
                iUtente.setString(7, utente.getBiografia());
                if(utente.getCurriculum() != null) {
                    iUtente.setInt(6, utente.getCurriculum().getKey());
                } else {
                    iUtente.setNull(6, java.sql.Types.INTEGER);
                }
                if(utente.getImmagine() != null) {
                    iUtente.setInt(7, utente.getImmagine().getKey());
                } else {
                    iUtente.setNull(7, java.sql.Types.INTEGER);
                }
                if (iUtente.executeUpdate() == 1) {
                    try (ResultSet keys = iUtente.getGeneratedKeys()) {
                        if (keys.next()) {
                            key = keys.getInt(1);
                        }
                    }
                }
            }
            if (key > 0) {
                utente.copyFrom(getUtente(key));
            }
            utente.setDirty(false);
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile salvare l'utente", ex);
        }
    }
    
    @Override
    public void salvaAppartenenti(int ext_skill, int ext_tipo) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void salvaCoprenti(int ext_utente, int task, int voto) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void salvaPreparazioni(int ext_utente, int ext_skill, int livello) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void salvaRequisiti(int ext_skill, int ext_task, int livello) throws DataLayerException {
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
        int key = skill.getKey();
        try {
            dSkill.setInt(1, key);
            dSkill.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile cancellare la skill", ex);
        }
    }
    
    @Override
    public void eliminaTask(Task task) throws DataLayerException {
        int key = task.getKey();
        try {
            dTask.setInt(1, key);
            dTask.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile cancellare il task", ex);
        }
    }
    
    @Override
    public void eliminaTipo(Tipo tipo) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void eliminaUtente(Utente utente) throws DataLayerException {
        int key = utente.getKey();
        try {
            dUtente.setInt(1, key);
            dUtente.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile cancellare il task", ex);
        }
    }
    
    @Override
    public void eliminaCoprenti(int ext_utente, int task) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void eliminaPreparazioni(int ext_utente, int ext_skill) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void eliminaRequisiti(int ext_skill, int ext_task) throws DataLayerException {
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
        try {
            sSkillByID.setInt(1, skill_key);
            try (ResultSet rs = sSkillByID.executeQuery()) {
                if (rs.next()) {
                    return creaSkill(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare la skill", ex);
        }
        return null;
    }
    
    @Override
    public List<Skill> getSkills() throws DataLayerException {
        List<Skill> result = new ArrayList();
        try (ResultSet rs = sSkills.executeQuery()) {
            while (rs.next()) {
                result.add(creaSkill(rs));
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare le skill", ex);
        }
        return result;
    }
    
    @Override
    public List<Skill> getSkills(Tipo tipo) throws DataLayerException {
        List<Skill> result = new ArrayList();
        try {
            sSkillsByTipo.setInt(1, tipo.getKey());
            
            try (ResultSet rs = sSkillsByTipo.executeQuery()) {
                while (rs.next()) {
                    result.add(creaSkill(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare le skill", ex);
        }
        return result;
    }
    
    @Override
    public Map<Skill, Integer> getSkills(Utente utente) throws DataLayerException {
        Map<Skill, Integer> result = new HashMap<>();
        try {
            sSkillsByUtente.setInt(1, utente.getKey());
            try (ResultSet rs = sSkillsByUtente.executeQuery()) {
                while (rs.next()) {
                    result.put(creaSkill(rs), rs.getInt("livello"));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare le skill", ex);
        }
        return result;
    }
    
    @Override
    public Map<Skill, Integer> getSkills(Task task) throws DataLayerException {
        Map<Skill, Integer> result = new HashMap<>();
        try {
            sSkillsByTask.setInt(1, task.getKey());
            try (ResultSet rs = sSkillsByTask.executeQuery()) {
                while (rs.next()) {
                    result.put(creaSkill(rs), rs.getInt("livello"));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare le skill", ex);
        }
        return result;
    }
    
    @Override
    public List<Skill> getSkillsfiglie(Skill skill) throws DataLayerException {
        List<Skill> result = new ArrayList();
        try {
            sSkillsFiglie.setInt(1, skill.getKey());
            try (ResultSet rs = sSkillsFiglie.executeQuery()) {
                while (rs.next()) {
                    result.add(creaSkill(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare le skill", ex);
        }
        return result;
    }
    
    @Override
    public Task getTask(int task_key) throws DataLayerException {
        try {
            sTaskByID.setInt(1, task_key);
            try (ResultSet rs = sTaskByID.executeQuery()) {
                if (rs.next()) {
                    return creaTask(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare il task", ex);
        }
        return null;
    }
    
    @Override
    public Map<Task, Integer> getTasks(Utente utente) throws DataLayerException {
        Map<Task, Integer> result = new HashMap<>();
        try {
            sTasksByUtente.setInt(1, utente.getKey());
            try (ResultSet rs = sTasksByUtente.executeQuery()) {
                while (rs.next()) {
                    result.put(creaTask(rs), rs.getInt("voto"));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i task", ex);
        }
        return result;
    }
    
    @Override
    public List<Task> getTasks(Progetto progetto) throws DataLayerException {
        List<Task> result = new ArrayList();
        try {
            sTasksByProgetto.setInt(1, progetto.getKey());
            try (ResultSet rs = sTasksByProgetto.executeQuery()) {
                while (rs.next()) {
                    result.add(creaTask(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i task", ex);
        }
        return result;
    }
    
    @Override
    public List<Task> getTasks(Tipo tipo) throws DataLayerException {
        List<Task> result = new ArrayList();
        try {
            sTasksByTipo.setInt(1, tipo.getKey());
            try (ResultSet rs = sTasksByTipo.executeQuery()) {
                while (rs.next()) {
                    result.add(creaTask(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i task", ex);
        }
        return result;
    }
    
    @Override
    public Map<Task, Integer> getTasks(Skill skill) throws DataLayerException {
        Map<Task, Integer> result = new HashMap<>();
        try {
            sTasksBySkill.setInt(1, skill.getKey());
            try (ResultSet rs = sTasksBySkill.executeQuery()) {
                while (rs.next()) {
                    result.put(creaTask(rs), rs.getInt("livello"));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i task", ex);
        }
        return result;
    }
    
    @Override
    public Tipo getTipo(int tipo_key) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<Tipo> getTipi() throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<Tipo> getTipi(Skill skill) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Utente getUtente(int utente_key) throws DataLayerException {
        try {
            sUtenteByID.setInt(1, utente_key);
            try (ResultSet rs = sTaskByID.executeQuery()) {
                if (rs.next()) {
                    return creaUtente(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare l'utente", ex);
        }
        return null;
    }
    
    @Override
    public Map<Utente, Integer> getUtenti(Task task) throws DataLayerException {
        Map<Utente, Integer> result = new HashMap<>();
        try {
            sUtentiByTask.setInt(1, task.getKey());
            try (ResultSet rs = sUtentiByTask.executeQuery()) {
                while (rs.next()) {
                    result.put(creaUtente(rs), rs.getInt("livello"));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare gli utenti", ex);
        }
        return result;
    }
    
    @Override
    public List<Utente> getUtenti(String filtro) throws DataLayerException {
        List<Utente> result = new ArrayList();
        try {
            sUtentiByFiltro.setString(1, filtro);
            try (ResultSet rs = sUtentiByFiltro.executeQuery()) {
                while (rs.next()) {
                    result.add(creaUtente(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare gli utenti", ex);
        }
        return result;
    }
    
    @Override
    public Map<Utente, Integer> getUtenti(Map<Skill, Integer> skills) throws DataLayerException {
        Map<Utente, Integer> result = new HashMap<>();
        try {
            for(Map.Entry<Skill, Integer> entry : skills.entrySet()) {
                Skill skill = entry.getKey();
                int livello = entry.getValue();
                sUtentiBySkill.setInt(1, skill.getKey());
                try (ResultSet rs = sUtentiBySkill.executeQuery()) {
                    while (rs.next()) {
                        result.put(getUtente(rs.getInt("utente.id")), livello);
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare gli utenti", ex);
        }
        return result;
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
            dDiscussione.close();
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
            sSkills.close();
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
            sTipi.close();
            sTipiBySkill.close();
            iTipo.close();
            uTipo.close();
            dTipo.close();
            sUtenteByID.close();
            sUtentiByTask.close();
            sUtentiByFiltro.close();
            sUtentiBySkill.close();
            iUtente.close();
            uUtente.close();
            dUtente.close();
            sAppartenenti.close();
            iAppartenenti.close();
            uAppartenenti.close();
            dAppartenenti.close();
            sCoprenti.close();
            iCoprenti.close();
            uCoprenti.close();
            dCoprenti.close();
            sPreparazioni.close();
            iPreparazioni.close();
            uPreparazioni.close();
            dPreparazioni.close();
            sRequisiti.close();
            iRequisiti.close();
            uRequisiti.close();
            dRequisiti.close();
        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }
}
