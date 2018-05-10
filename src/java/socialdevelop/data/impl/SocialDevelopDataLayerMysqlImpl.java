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
 * @author Mario Vetrini
 * @author Nicolò Paoletti
 * @author Davide De Marco
 */
public class SocialDevelopDataLayerMysqlImpl extends DataLayerMysqlImpl implements SocialDevelopDataLayer {
    // select, insert, update, delete, generiche funzionalità del sito, ecc...
    private PreparedStatement sCurriculumByID, iCurriculum, uCurriculum, dCurriculum;
    private PreparedStatement sDiscussioneByID, sDiscussioniByTask, iDiscussione, uDiscussione, dDiscussione,countDiscussioniByTask;
    private PreparedStatement sFileByID, sFilesByUtente, iFile, uFile, dFile;
    private PreparedStatement sImmagineByID, iImmagine, uImmagine, dImmagine;
    private PreparedStatement sInvitoByID, sInvitiByUtente, sInvitiByTask, iInvito, uInvito, dInvito;
    private PreparedStatement sMessaggioByID, sMessaggiByDiscussione, iMessaggio, uMessaggio, dMessaggio;
    private PreparedStatement sProgettoByID, sProgettiByUtente, sProgettiByFiltro, iProgetto, uProgetto, dProgetto,countProgettiByFiltro;
    private PreparedStatement sSkillByID, sSkills, sSkillsByTipo, sSkillsByUtente, sSkillsByTask, sSkillsFiglie, iSkill, uSkill, dSkill;
    private PreparedStatement sTaskByID, sTasksByUtente, sTasksByProgetto, sTasksByTipo, sTasksBySkill, iTask, uTask, dTask;
    private PreparedStatement sTipoByID, sTipi, sTipiBySkill, iTipo, uTipo, dTipo;
    private PreparedStatement sUtenteByID, sUtentiByTask, sUtentiByFiltro, sUtentiBySkill, iUtente, uUtente, dUtente,countUtentiByFiltro;
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
            
            sCurriculumByID = connection.prepareStatement("SELECT * FROM files WHERE id = ?");
            iCurriculum = connection.prepareStatement("INSERT INTO 'files' ('nome','percorso','ext_utente') VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uCurriculum = connection.prepareStatement("UPDATE 'files' SET 'nome' = ?, 'percorso' = ?, 'ext_utente' = ? WHERE 'id' = ?");
            dCurriculum = connection.prepareStatement("DELETE FROM 'files' WHERE 'id' = ?");
            
            sDiscussioneByID = connection.prepareStatement("SELECT * FROM discussioni WHERE id = ?");
            sDiscussioniByTask = connection.prepareStatement("SELECT * FROM discussioni WHERE ext_task = ? LIMIT ?, ?");
            countDiscussioniByTask = connection.prepareStatement("SELECT COUNT(id) as total FROM discussioni WHERE ext_task = ?");
            iDiscussione = connection.prepareStatement("INSERT INTO discussioni (titolo, pubblica, data_creazione, ext_utente, ext_task) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uDiscussione = connection.prepareStatement("UPDATE discussioni SET titolo = ?, pubblica = ?, data_creazione = ?, ext_utente = ?, ext_task = ? WHERE id = ?");
            dDiscussione = connection.prepareStatement("DELETE FROM discussioni WHERE id = ?");
            
            sFileByID = connection.prepareStatement("SELECT * FROM files WHERE id = ?");
            sFilesByUtente = connection.prepareStatement("SELECT * FROM files WHERE ext_utente = ?");
            iFile = connection.prepareStatement("INSERT INTO files ('nome','percorso','ext_utente') VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uFile = connection.prepareStatement("UPDATE 'files' SET 'nome' = ?, 'percorso' = ?,'ext_utente' = ? WHERE 'id' = ?");
            dFile = connection.prepareStatement("DELETE FROM files WHERE id = ?");
            
            sImmagineByID = connection.prepareStatement("SELECT * FROM files WHERE id = ?");
            iImmagine = connection.prepareStatement("INSERT INTO files ('nome','percorso','ext_utente') VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uImmagine = connection.prepareStatement("UPDATE 'files' SET 'nome' = ?, 'percorso' = ?,'ext_utente' = ? WHERE 'id' = ?");
            dImmagine = connection.prepareStatement("DELETE FROM files WHERE id = ?");
            
            sInvitoByID = connection.prepareStatement("SELECT * FROM inviti WHERE id= ?");
            sInvitiByUtente = connection.prepareStatement("SELECT * FROM 'inviti' WHERE 'ext_utente' = ?");
            sInvitiByTask = connection.prepareStatement("SELECT * FROM 'inviti' WHERE 'ext_task' = ?");
            iInvito = connection.prepareStatement("INSERT INTO 'inviti' ('messaggio','data','request','state','ext_user','ext_task') VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uInvito = connection.prepareStatement("UPDATE 'inviti' SET 'status' = ? WHERE 'id' = ?");
            dInvito = connection.prepareStatement("DELETE FROM inviti WHERE id = ?");
            
            sMessaggioByID = connection.prepareStatement("SELECT * FROM messaggi WHERE id = ?");
            sMessaggiByDiscussione = connection.prepareStatement("SELECT * FROM messaggi WHERE ext_discussione = ?");
            iMessaggio = connection.prepareStatement("INSERT INTO messaggi (testo, data_pubblicazione, ext_utente, ext_discussione) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uMessaggio = connection.prepareStatement("UPDATE messaggi SET testo = ?, data_pubblicazione = ?, ext_utente = ?, ext_discussione = ? WHERE id = ?");
            dMessaggio = connection.prepareStatement("DELETE FROM messaggi WHERE id = ?");
            
            sProgettoByID = connection.prepareStatement("SELECT * FROM progetti WHERE id = ?");
            sProgettiByUtente = connection.prepareStatement("SELECT * FROM progetti WHERE ext_coordinatore = ?");
            sProgettiByFiltro = connection.prepareStatement("SELECT * FROM progetti WHERE nome LIKE ? OR descrizione LIKE ? LIMIT ?, ?");
            countProgettiByFiltro = connection.prepareStatement("SELECT COUNT(id) as total FROM progetti WHERE nome LIKE ? OR descrizione LIKE ?");
            iProgetto = connection.prepareStatement("INSERT INTO progetti (nome, descrizione, ext_coordinatore) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uProgetto = connection.prepareStatement("UPDATE progetti SET nome = ?, descrizione = ?, ext_coordinatore = ? WHERE id = ?");
            dProgetto = connection.prepareStatement("DELETE FROM progetti WHERE id = ?");
            
            sSkillByID = connection.prepareStatement("SELECT * FROM skills WHERE id =?");
            sSkills = connection.prepareStatement("SELECT * FROM skills");
            sSkillsByTipo = connection.prepareStatement("SELECT skills.* FROM skills INNER JOIN appartenenti ON skills.id = appartenenti.ext_skill WHERE appartenenti.ext_tipo = ?");
            sSkillsByUtente = connection.prepareStatement("SELECT skills.* FROM skills INNER JOIN preparazioni ON skills.id = preparazioni.ext_skill WHERE preparazioni.ext_utente = ?");
            sSkillsByTask = connection.prepareStatement("SELECT skills.*,requisiti.livello FROM skills INNER JOIN requisiti ON skills.id = requisiti.ext_skill WHERE requisiti.ext_task = ?");
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
            
            sTipoByID = connection.prepareStatement("SELECT * FROM tipi WHERE id = ?");
            sTipi = connection.prepareStatement("SELECT * FROM tipi");
            sTipiBySkill = connection.prepareStatement("SELECT tipi.* FROM tipi JOIN appartenenti ON appartenenti.ext_tipo = tipi.id WHERE appartenenti.ext_skill = ?");
            iTipo = connection.prepareStatement("INSERT INTO tipi (nome) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            uTipo = connection.prepareStatement("UPDATE 'tipi' SET 'nome' = ? WHERE 'id' = ?");
            dTipo = connection.prepareStatement("DELETE FROM tipi WHERE id = ?");
            
            sUtenteByID = connection.prepareStatement("SELECT * FROM utenti WHERE id = ?");
            sUtentiByTask = connection.prepareStatement("SELECT utenti.*, coprenti.voto FROM utenti INNER JOIN coprenti ON utenti.id = coprenti.ext_utente WHERE coprenti.ext_task = ?");
            sUtentiByFiltro = connection.prepareStatement("SELECT * FROM utenti WHERE nome LIKE ? OR cognome LIKE ? OR username = ? LIMIT ?,?");
            countUtentiByFiltro = connection.prepareStatement("SELECT COUNT(id) as total FROM utenti WHERE nome LIKE ? OR cognome LIKE ? OR username = ?");
            sUtentiBySkill = connection.prepareStatement("SELECT utenti.*, preparazioni.livello FROM utenti INNER JOIN preparazioni ON utenti.id = preparazioni.ext_utente WHERE preparazioni.ext_skill = ? AND preparazioni.livello >= ?");
            iUtente = connection.prepareStatement("INSERT INTO utenti (nome, cognome, username, email, data_nascita, password, biografia, ext_curriculum, ext_immagine) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            uUtente = connection.prepareStatement("UPDATE utenti SET nome = ?, cognome = ?, username = ?, email = ?, data_nascita = ?, password = ?, biografia = ?, ext_curriculum = ?, ext_immagine = ? WHERE id = ?");
            dUtente = connection.prepareStatement("DELETE FROM utenti WHERE id = ?");
            
            sAppartenenti = connection.prepareStatement("SELECT * FROM appartenenti WHERE ext_skill = ? AND ext_tipo = ?");
            iAppartenenti = connection.prepareStatement("INSERT INTO appartenenti (ext_skill, ext_tipo) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS);
            uAppartenenti = connection.prepareStatement("UPDATE appartenenti SET ext_skill = ?, ext_tipo = ? WHERE ext_skill = ? AND ext_tipo = ?");
            dAppartenenti = connection.prepareStatement("DELETE FROM appartenenti WHERE ext_skill = ? AND ext_tipo = ?");
            
            sCoprenti = connection.prepareStatement("SELECT * FROM coprenti WHERE ext_utente = ? AND ext_task = ?");
            iCoprenti = connection.prepareStatement("INSERT INTO coprenti (voto, ext_utente, ext_task) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            uCoprenti = connection.prepareStatement("UPDATE coprenti SET voto = ?, ext_utente = ?, ext_task = ? WHERE ext_utente = ? AND ext_task = ?");
            dCoprenti = connection.prepareStatement("DELETE FROM coprenti WHERE ext_utente = ? AND ext_task = ?");
            
            sPreparazioni = connection.prepareStatement("SELECT * FROM preparazioni WHERE ext_utente = ? AND ext_skill = ?");
            iPreparazioni = connection.prepareStatement("INSERT INTO preparazioni (livello, ext_utente, ext_skill) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            uPreparazioni = connection.prepareStatement("UPDATE preparazioni SET livello = ?, ext_utente = ?, ext_skill = ? WHERE ext_utente = ? AND ext_skill = ?");
            dPreparazioni = connection.prepareStatement("DELETE FROM preparazioni WHERE ext_utente = ? AND ext_skill = ?");
            
            sRequisiti = connection.prepareStatement("SELECT * FROM requisiti WHERE ext_skill = ? AND ext_task = ?");
            iRequisiti = connection.prepareStatement("INSERT INTO requisiti (livello, ext_skill, ext_task) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            uRequisiti = connection.prepareStatement("UPDATE requisiti SET livello = ?, ext_skill = ?, ext_task = ? WHERE ext_skill = ? AND ext_task = ?");
            dRequisiti = connection.prepareStatement("DELETE FROM requisiti WHERE ext_skill = ? AND ext_task = ?");
            
        } catch (SQLException ex) {
            throw new DataLayerException("Error initializing SocialDevelop data layer", ex);
        }
    }
    
    @Override
    public FileSD creaCurriculum() {
        return new CurriculumImpl(this);
    }
    
    public FileSD creaCurriculum(ResultSet rs) throws DataLayerException{
        CurriculumImpl cur = new CurriculumImpl(this);
        try {
            cur.setKey(0);
            cur.setNome(rs.getString("nome"));
            cur.setGrandezza(rs.getInt("dimensione"));
            cur.setTipo(rs.getString("tipo"));
            cur.setUtenteKey(rs.getInt("ext_utente"));
            return cur;
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile creare l'istanza del file dal ResultSet", ex);
        }
    }
    
    @Override
    public Discussione creaDiscussione() {
        return new DiscussioneImpl(this);
    }
    
    public Discussione creaDiscussione(ResultSet rs) throws DataLayerException {
        try {
            Date data_creazione_sql;
            DiscussioneImpl discussione = new DiscussioneImpl(this);
            
            discussione.setKey(rs.getInt("id"));
            discussione.setTitolo(rs.getString("titolo"));
            discussione.setPubblica(rs.getBoolean("pubblica"));
            discussione.setTaskKey(rs.getInt("ext_task"));
            discussione.setUtenteKey(rs.getInt("ext_utente"));
            
            data_creazione_sql = rs.getDate("data_creazione");
            GregorianCalendar data_creazione = new GregorianCalendar();
            data_creazione.setTime(data_creazione_sql);
            discussione.setData(data_creazione);
            
            return discussione;
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile creare la discussione dal ResultSet", ex);
        }
    }
    
    @Override
    public FileSD creaFile() {
        return new FileSDImpl(this);
    }
    
    public FileSD creaFile(ResultSet rs) throws DataLayerException{
        FileSDImpl file = new FileSDImpl(this);
        try {
            file.setKey(rs.getInt("id"));
            file.setNome(rs.getString("nome"));
            file.setGrandezza(rs.getInt("dimensione"));
            file.setTipo(rs.getString("tipo"));
            file.setUtenteKey(rs.getInt("ext_utente"));
            return file;
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile creare l'istanza del file dal ResultSet", ex);
        }
    }
    
    @Override
    public FileSD creaImmagine() {
        return new ImmagineImpl(this);
    }
    
    public FileSD creaImmagine(ResultSet rs) throws DataLayerException{
        ImmagineImpl img = new ImmagineImpl(this);
        try {
            img.setKey(rs.getInt("id"));
            img.setNome(rs.getString("nome"));
            img.setTipo(rs.getString("percorso"));
            img.setUtenteKey(rs.getInt("ext_utente"));
            return img;
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile creare l'istanza dell'immagine dal ResultSet", ex);
        }
    }
    
    @Override
    public Invito creaInvito() {
        return new InvitoImpl(this);
    }
    
    public Invito creaInvito(ResultSet rs) throws DataLayerException{
        InvitoImpl inv = new InvitoImpl(this);
        try {
            inv.setKey(rs.getInt("id"));
            inv.setMessaggio(rs.getString("messaggio"));
            inv.setStato(rs.getString("stato"));
            inv.setOfferta(rs.getBoolean("offerta"));
            inv.setTaskKey(rs.getInt("ext_task"));
            inv.setUtenteKey(rs.getInt("ext_utente"));
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(rs.getDate("date"));
            inv.setData(gc);
            
            return inv;
        } catch(SQLException ex) {
            throw new DataLayerException("Impossibile creare l'istanza dell'invito dal ResultSet", ex);
        }
    }
    
    @Override
    public Messaggio creaMessaggio() {
        return new MessaggioImpl(this);
    }
    
    public Messaggio creaMessaggio(ResultSet rs) throws DataLayerException {
        try {
            Date data_pubblicazione_sql;
            MessaggioImpl messaggio = new MessaggioImpl(this);
            
            messaggio.setKey(rs.getInt("id"));
            messaggio.setTesto(rs.getString("testo"));
            messaggio.setUtenteKey(rs.getInt("ext_utente"));
            messaggio.setDiscussioneKey(rs.getInt("ext_discussione"));
            
            data_pubblicazione_sql = rs.getDate("data_pubblicazione");
            GregorianCalendar data_pubblicazione = new GregorianCalendar();
            data_pubblicazione.setTime(data_pubblicazione_sql);
            messaggio.setData(data_pubblicazione);
            
            return messaggio;
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile creare la discussione dal ResultSet", ex);
        }
    }
    
    @Override
    public Progetto creaProgetto() {
        return new ProgettoImpl(this);
    }
    
    public Progetto creaProgetto(ResultSet rs) throws DataLayerException {
        try {
            ProgettoImpl progetto = new ProgettoImpl(this);
            
            progetto.setKey(rs.getInt("id"));
            progetto.setNome(rs.getString("nome"));
            progetto.setDescrizione(rs.getString("descrizione"));
            progetto.setUtenteKey(rs.getInt("ext_coordinatore"));
            
            return progetto;
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile creare il progetto dal ResultSet", ex);
        }
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
        return new TipoImpl(this);
    }
    
    public Tipo creaTipo(ResultSet rs) throws DataLayerException{
        TipoImpl tipo = new TipoImpl(this);
        try {
            tipo.setKey(rs.getInt("id"));
            tipo.setNome(rs.getString("nome"));
            return tipo;
        } catch(SQLException ex) {
            throw new DataLayerException("Impossibile creare l'istanza del tipo dal ResultSet", ex);
        }
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
            
            data_nascita_sql = rs.getDate("data_nascita");
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
        int key = discussione.getKey();
        try {
            if (discussione.getKey() > 0) { // update
                if (!discussione.isDirty()) {
                    return;
                }
                uDiscussione.setString(1, discussione.getTitolo());
                uDiscussione.setBoolean(2, discussione.getPubblica());
                Date data_creazione = new Date(discussione.getData().getTimeInMillis());
                uDiscussione.setDate(3, data_creazione);
                if(discussione.getUtente()!= null) {
                    uDiscussione.setInt(4, discussione.getUtente().getKey());
                } else {
                    uDiscussione.setNull(4, java.sql.Types.INTEGER);
                }
                if (discussione.getTask()!=null) {
                    uDiscussione.setInt(5, discussione.getTask().getKey());
                } else {
                    uDiscussione.setNull(5, java.sql.Types.INTEGER);
                }
                uDiscussione.setInt(5, discussione.getKey());
                
                uMessaggio.executeUpdate();
            } else { // insert
                iDiscussione.setString(1, discussione.getTitolo());
                iDiscussione.setBoolean(2, discussione.getPubblica());
                Date data_creazione = new Date(discussione.getData().getTimeInMillis());
                iDiscussione.setDate(3, data_creazione);
                if (discussione.getUtente()!=null){
                    iDiscussione.setInt(4, discussione.getUtente().getKey());
                } else {
                    iDiscussione.setNull(4, java.sql.Types.INTEGER);
                }
                if (discussione.getTask()!=null){
                    iDiscussione.setInt(5, discussione.getTask().getKey());
                } else {
                    iDiscussione.setNull(5, java.sql.Types.INTEGER);
                }
                if (iDiscussione.executeUpdate() == 1) {
                    try (ResultSet keys = iDiscussione.getGeneratedKeys()) {
                        if (keys.next()) {
                            key = keys.getInt(1);
                        }
                    }
                }
            }
            
            if (key > 0) {
                discussione.copyFrom(getDiscussione(key));
            }
            discussione.setDirty(false);
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile salvare la discussione", ex);
        }
    }
    
    @Override
    public void salvaFileSD(FileSD filesd) throws DataLayerException {
        int key = filesd.getKey();
        try{
            if (filesd.getKey() > 0) { // update
                if (!filesd.isDirty())
                    return;
                this.uFile.setString(1, filesd.getNome());
                this.uFile.setString(2, filesd.getTipo());
                this.uFile.setInt(3, filesd.getUtente().getKey());
                this.uFile.setInt(4, key);
                this.uFile.executeUpdate();
            } else { // insert
                this.iFile.setString(1, filesd.getNome());
                this.iFile.setString(2, filesd.getTipo());
                this.iFile.setInt(3, filesd.getUtente().getKey());
                this.iFile.setInt(4, key);
                if (this.iFile.executeUpdate() == 1) {
                    try (ResultSet keys = this.iFile.getGeneratedKeys()) {
                        if (keys.next())
                            key = keys.getInt(1);
                    }
                }
            }
            if (key > 0) {
                filesd.copyFrom(getFile(key));
            }
            filesd.setDirty(false);
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile salvare il file", ex);
        }
    }
    
    @Override
    public void salvaImmagine(FileSD immagine) throws DataLayerException {
        int key = immagine.getKey();
        try {
            if (immagine.getKey() > 0) { // update
                if (!immagine.isDirty())
                    return;
                this.uImmagine.setString(1, immagine.getNome());
                this.uImmagine.setString(2, immagine.getTipo());
                this.uImmagine.setInt(3, immagine.getUtente().getKey());
                this.uImmagine.setInt(4, key);
                this.uImmagine.executeUpdate();
            } else { // insert
                this.iImmagine.setString(1, immagine.getNome());
                this.iImmagine.setString(2, immagine.getTipo());
                this.iImmagine.setInt(3, immagine.getUtente().getKey());
                this.iImmagine.setInt(4, key);
                if (this.iImmagine.executeUpdate() == 1) {
                    try (ResultSet keys = this.iFile.getGeneratedKeys()) {
                        if (keys.next())
                            key = keys.getInt(1);
                    }
                }
            }
            if (key > 0) {
                immagine.copyFrom(getImmagine(key));
            }
            immagine.setDirty(false);
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile salvare il file", ex);
        }
    }
    
    @Override
    public void salvaInvito(Invito invito) throws DataLayerException {
        int key = invito.getKey();
        try {
            if (invito.getKey() > 0) { // update
                if(!invito.isDirty())
                    return;
                this.uInvito.setInt(2, key);
                this.uInvito.setString(1,invito.getStato());
                this.uInvito.executeUpdate();
            } else { // inserimento
                this.iInvito.setString(1, invito.getMessaggio());
                if (invito.getData() != null) {
                    this.iInvito.setDate(2, new Date(invito.getData().getTimeInMillis()));
                } else {
                    this.iInvito.setNull(2, java.sql.Types.DATE);
                }
                this.iInvito.setBoolean(3, invito.getOfferta());
                this.iInvito.setString(4,invito.getStato());
                this.iInvito.setInt(4, invito.getUtente().getKey());
                this.iInvito.setInt(6, invito.getTask().getKey());
                if (this.iInvito.executeUpdate() == 1) {
                    try (ResultSet keys = this.iInvito.getGeneratedKeys()) {
                        if (keys.next())
                            key = keys.getInt(1);
                    }
                }
            }
            if (key > 0) {
                invito.copyFrom(getInvito(key));
            }
            invito.setDirty(false);
        } catch(SQLException ex) {
            throw new DataLayerException("Impossibile salvare l'invito", ex);
        }
    }
    
    @Override
    public void salvaMessaggio(Messaggio messaggio) throws DataLayerException {
        int key = messaggio.getKey();
        try {
            if (messaggio.getKey() > 0) { // update
                if (!messaggio.isDirty()) {
                    return;
                }
                uMessaggio.setString(1, messaggio.getTesto());
                Date data_pubblicazione = new Date(messaggio.getData().getTimeInMillis());
                uMessaggio.setDate(2, data_pubblicazione);
                if (messaggio.getUtente()!=null) {
                    uMessaggio.setInt(3, messaggio.getUtente().getKey());
                } else {
                    uMessaggio.setNull(3, java.sql.Types.INTEGER);
                }
                if (messaggio.getDiscussione()!=null) {
                    uMessaggio.setInt(4, messaggio.getDiscussione().getKey());
                } else {
                    uMessaggio.setNull(4, java.sql.Types.INTEGER);
                }
                uMessaggio.setInt(5, messaggio.getKey());
                
                uMessaggio.executeUpdate();
            } else { //insert
                iMessaggio.setString(1, messaggio.getTesto());
                Date data_pubblicazione = new Date(messaggio.getData().getTimeInMillis());
                iMessaggio.setDate(2, data_pubblicazione);
                if (messaggio.getUtente()!=null) {
                    iMessaggio.setInt(3, messaggio.getUtente().getKey());
                } else {
                    iMessaggio.setNull(3, java.sql.Types.INTEGER);
                }
                if (messaggio.getDiscussione()!=null) {
                    iMessaggio.setInt(4, messaggio.getDiscussione().getKey());
                } else {
                    iMessaggio.setNull(4, java.sql.Types.INTEGER);
                }
                if (iMessaggio.executeUpdate() == 1) {
                    try (ResultSet keys = iMessaggio.getGeneratedKeys()) {
                        if (keys.next()) {
                            key = keys.getInt(1);
                        }
                    }
                }
            }
            
            if (key > 0) {
                messaggio.copyFrom(getMessaggio(key));
            }
            messaggio.setDirty(false);
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile salvare il messaggio", ex);
        }
    }
    
    @Override
    public void salvaProgetto(Progetto progetto) throws DataLayerException {
        int key = progetto.getKey();
        try {
            if (progetto.getKey() > 0) { // update
                if (!progetto.isDirty()) { // non facciamo nulla se l'oggetto non ha subito modifiche
                    return;
                }
                uProgetto.setString(1, progetto.getNome());
                uProgetto.setString(2, progetto.getDescrizione());
                if (progetto.getUtente() != null) {
                    uProgetto.setInt(3, progetto.getUtente().getKey());
                } else {
                    uProgetto.setNull(3, java.sql.Types.INTEGER);
                }
                uProgetto.setInt(4, progetto.getKey());
                uProgetto.executeUpdate();
            } else { // insert
                iProgetto.setString(1, progetto.getNome());
                iProgetto.setString(2, progetto.getDescrizione());
                if (progetto.getUtente() != null) {
                    iProgetto.setInt(3, progetto.getUtente().getKey());
                } else {
                    iProgetto.setNull(3, java.sql.Types.INTEGER);
                }
                if (iProgetto.executeUpdate() == 1) {
                    try (ResultSet keys = iProgetto.getGeneratedKeys()) {
                        if (keys.next()) {
                            key = keys.getInt(1);
                        }
                    }
                }
            }
            if (key > 0) {
                progetto.copyFrom(getProgetto(progetto.getKey()));
            }
            progetto.setDirty(false);
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile salvare il progetto", ex);
        }
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
                if (skill.getPadre() != null) {
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
        int key = tipo.getKey();
        try{
            if(tipo.getKey() > 0){//update
                if(!tipo.isDirty())
                    return;
                this.uTipo.setString(1, tipo.getNome());
            }else{//inserimento
                this.iTipo.setString(1, tipo.getNome());
                if(this.iTipo.executeUpdate() == 1){
                    try(ResultSet keys = this.iTipo.getGeneratedKeys()){
                        if(keys.next())
                            key = keys.getInt(1);
                    }
                }
            }
            if(key > 0){
                tipo.copyFrom(getTipo(key));
            }
            tipo.setDirty(false);
        }catch(SQLException ex){
            throw new DataLayerException("Impossibile salvare il tipo",ex);
        }
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
                if (utente.getDataNascita()!= null) {
                    Date data_nascita = new Date(utente.getDataNascita().getTimeInMillis());
                    uUtente.setDate(5, data_nascita);
                } else {
                    uUtente.setDate(5, null);
                }
                uUtente.setString(6, utente.getPassword());
                uUtente.setString(7, utente.getBiografia());
                if (utente.getCurriculum() != null) {
                    uUtente.setInt(6, utente.getCurriculum().getKey());
                } else {
                    uUtente.setNull(6, java.sql.Types.INTEGER);
                }
                if (utente.getImmagine() != null) {
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
                if (utente.getDataNascita()!= null) {
                    Date data_nascita = new Date(utente.getDataNascita().getTimeInMillis());
                    iUtente.setDate(5, data_nascita);
                } else {
                    iUtente.setDate(5, null);
                }
                iUtente.setString(6, utente.getPassword());
                iUtente.setString(7, utente.getBiografia());
                if (utente.getCurriculum() != null) {
                    iUtente.setInt(6, utente.getCurriculum().getKey());
                } else {
                    iUtente.setNull(6, java.sql.Types.INTEGER);
                }
                if (utente.getImmagine() != null) {
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
        boolean update = false;
        try {
            uAppartenenti.setInt(1, ext_skill);
            uAppartenenti.setInt(2, ext_tipo);
            try (ResultSet rs = uAppartenenti.executeQuery()) {
                if (rs.next()) {
                    update = true;
                }
            }
            if (!update) {
                iAppartenenti.setInt(1, ext_skill);
                iAppartenenti.setInt(2, ext_tipo);
                iAppartenenti.executeQuery();
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile salvare la tupla di 'appartenenti'", ex);
        }
    }
    
    @Override
    public void salvaCoprenti(int voto, int ext_utente, int ext_task) throws DataLayerException {
        boolean update = false;
        try {
            sCoprenti.setInt(1, ext_utente);
            sCoprenti.setInt(2, ext_task);
            try (ResultSet rs = sCoprenti.executeQuery()) {
                if (rs.next()) {
                    update = true;
                }
            }
            if (update) {
                uCoprenti.setInt(1, voto);
                uCoprenti.setInt(2, ext_utente);
                uCoprenti.setInt(3, ext_task);
                uCoprenti.setInt(4, ext_utente);
                uCoprenti.setInt(5, ext_task);
                uCoprenti.executeUpdate();
            }
            else{
                iCoprenti.setInt(1, voto);
                iCoprenti.setInt(2, ext_utente);
                iCoprenti.setInt(3, ext_task);
                iCoprenti.executeQuery();
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile salvare la tupla di 'coprenti'", ex);
        }
    }
    
    @Override
    public void salvaPreparazioni(int livello, int ext_utente, int ext_skill) throws DataLayerException {
        boolean update = false;
        try {
            uPreparazioni.setInt(1, livello);
            uPreparazioni.setInt(2, ext_utente);
            uPreparazioni.setInt(3, ext_skill);
            try (ResultSet rs = uPreparazioni.executeQuery()) {
                if (rs.next()) {
                    update = true;
                }
            }
            if (!update) {
                iPreparazioni.setInt(1, livello);
                iPreparazioni.setInt(2, ext_utente);
                iPreparazioni.setInt(3, ext_skill);
                iPreparazioni.executeQuery();
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile salvare la tupla di 'preparazioni'", ex);
        }
    }
    
    @Override
    public void salvaRequisiti(int livello, int ext_skill, int ext_task) throws DataLayerException {
        boolean update = false;
        try {
            uRequisiti.setInt(1, livello);
            uRequisiti.setInt(2, ext_skill);
            uRequisiti.setInt(3, ext_task);
            try (ResultSet rs = uRequisiti.executeQuery()) {
                if (rs.next()) {
                    update = true;
                }
            }
            if (!update) {
                iRequisiti.setInt(1, livello);
                iRequisiti.setInt(2, ext_skill);
                iRequisiti.setInt(3, ext_task);
                iRequisiti.executeQuery();
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile salvare la tupla di 'requisiti'", ex);
        }
    }
    
    @Override
    public void eliminaCurriculum(FileSD curriculum) throws DataLayerException {
        int key = curriculum.getKey();
        try {
            dCurriculum.setInt(1, key);
            dCurriculum.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile eliminare il curriculum", ex);
        }
    }
    
    @Override
    public void eliminaDiscussione(Discussione discussione) throws DataLayerException {
        int key = discussione.getKey();
        try {
            dDiscussione.setInt(1, key);
            dDiscussione.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile cancellare la discussione", ex);
        }
    }
    
    @Override
    public void eliminaFileSD(FileSD filesd) throws DataLayerException {
        int key = filesd.getKey();
        try {
            dFile.setInt(1, key);
            dFile.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile eliminare il file", ex);
        }
    }
    
    @Override
    public void eliminaImmagine(FileSD immagine) throws DataLayerException {
        int key = immagine.getKey();
        try {
            dImmagine.setInt(1, key);
            dImmagine.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile eliminare l'immagine", ex);
        }
    }
    
    @Override
    public void eliminaInvito(Invito invito) throws DataLayerException {
        int key = invito.getKey();
        try {
            dInvito.setInt(1, key);
            dInvito.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile cancellare l'invito", ex);
        }
    }
    
    @Override
    public void eliminaMessaggio(Messaggio messaggio) throws DataLayerException {
        int key = messaggio.getKey();
        try {
            dMessaggio.setInt(1, key);
            dMessaggio.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile cancellare il messaggio", ex);
        }
    }
    
    @Override
    public void eliminaProgetto(Progetto progetto) throws DataLayerException {
        int key = progetto.getKey();
        try {
            dProgetto.setInt(1, key);
            dProgetto.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile cancellare il progetto", ex);
        }
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
        int key = tipo.getKey();
        try {
            dTipo.setInt(1, key);
            dTipo.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile eliminare il tipo", ex);
        }
    }
    
    @Override
    public void eliminaUtente(Utente utente) throws DataLayerException {
        int key = utente.getKey();
        try {
            dUtente.setInt(1, key);
            dUtente.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile cancellare l'utente", ex);
        }
    }
    
    @Override
    public void eliminaAppartenenti(int ext_skill, int ext_tipo) throws DataLayerException {
        try {
            dAppartenenti.setInt(1, ext_skill);
            dAppartenenti.setInt(2, ext_tipo);
            dAppartenenti.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile cancellare la tupla di 'appartenenti'", ex);
        }
    }
    
    @Override
    public void eliminaCoprenti(int ext_utente, int ext_task) throws DataLayerException {
        try {
            dCoprenti.setInt(1, ext_utente);
            dCoprenti.setInt(2, ext_task);
            dCoprenti.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile cancellare la tupla di 'coprenti'", ex);
        }
    }
    
    @Override
    public void eliminaPreparazioni(int ext_utente, int ext_skill) throws DataLayerException {
        try {
            dCoprenti.setInt(1, ext_utente);
            dCoprenti.setInt(2, ext_skill);
            dCoprenti.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile cancellare la tupla di 'preparazioni'", ex);
        }
    }
    
    @Override
    public void eliminaRequisiti(int ext_skill, int ext_task) throws DataLayerException {
        try {
            dRequisiti.setInt(1, ext_skill);
            dRequisiti.setInt(2, ext_task);
            dRequisiti.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile cancellare la tupla di 'requisiti'", ex);
        }
    }
    
    @Override
    public FileSD getCurriculum(int curriculum_key) throws DataLayerException {
        try {
            this.sCurriculumByID.setInt(1, curriculum_key);
            try(ResultSet rs = this.sCurriculumByID.executeQuery()){
                if (rs.next())
                    return creaCurriculum(rs);
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile recuperare il curriculum", ex);
        }
        return null;
    }
    
    @Override
    public Discussione getDiscussione(int discussione_key) throws DataLayerException {
        try {
            sDiscussioneByID.setInt(1, discussione_key);
            try (ResultSet rs = sDiscussioneByID.executeQuery()) {
                if (rs.next()) {
                    return creaDiscussione(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare la discussione", ex);
        }
        return null;
    }
    
    @Override
    public int getCountDiscussioni(Task task) throws DataLayerException{
       int count = -1;
       try{
        countDiscussioniByTask.setInt(1,task.getKey());
        try (ResultSet rs = countDiscussioniByTask.executeQuery()) {
            if(rs.next())
                count = rs.getInt("total");
        }
       } catch(SQLException ex){
           throw new DataLayerException("Impossibile caricare il numero totale di discussioni", ex);
       }
       return count;
    }
    
    @Override
    public List<Discussione> getDiscussioni(Task task, int first, int perPage) throws DataLayerException {
        List<Discussione> result = new ArrayList<>();
        try {
            sDiscussioniByTask.setInt(1, task.getKey());
            sDiscussioniByTask.setInt(2, first);
            sDiscussioniByTask.setInt(3, perPage);
            try (ResultSet rs = sDiscussioniByTask.executeQuery()) {
                while (rs.next()) {
                    result.add((Discussione) getDiscussione(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare le discussioni", ex);
        }
        return result;
    }
    
    @Override
    public FileSD getFile(int file_key) throws DataLayerException {
        try {
            this.sFileByID.setInt(1, file_key);
            try(ResultSet rs = this.sFileByID.executeQuery()){
                if (rs.next())
                    return creaFile(rs);
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile recuperare il file",ex);
        }
        return null;
    }
    
    @Override
    public List<FileSD> getFiles(Utente utente) throws DataLayerException {
        List<FileSD> result = new ArrayList();
        try {
            this.sFilesByUtente.setInt(1, utente.getKey());
            try (ResultSet rs = this.sFilesByUtente.executeQuery()) {
                while (rs.next()) {
                    result.add((FileSD) getFile(rs.getInt("id")));
                }
            }
        } catch(SQLException ex) {
            throw new DataLayerException("Impossibile reperire i file dell'utente", ex);
        }
        return result;
    }
    
    @Override
    public FileSD getImmagine(int immagine_key) throws DataLayerException {
        try {
            this.sImmagineByID.setInt(1, immagine_key);
            try (ResultSet rs = this.sImmagineByID.executeQuery()) {
                if (rs.next())
                    return creaImmagine(rs);
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile recuperare l'immagine", ex);
        }
        return null;
    }
    
    @Override
    public Invito getInvito(int invito_key) throws DataLayerException {
        try {
            this.sInvitoByID.setInt(1, invito_key);
            try (ResultSet rs = sInvitoByID.executeQuery()) {
                if (rs.next())
                    return creaInvito(rs);
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile recuperare l'invito", ex);
        }
        return null;
    }
    
    @Override
    public List<Invito> getInviti(Utente utente) throws DataLayerException {
        List<Invito> result = new ArrayList();
        try {
            this.sInvitiByUtente.setInt(1, utente.getKey());
            try (ResultSet rs = this.sInvitiByUtente.executeQuery()) {
                while (rs.next()) {
                    result.add((Invito) getInvito(rs.getInt("id")));
                }
            }
        } catch(SQLException ex) {
            throw new DataLayerException("Impossibile reperire gli inviti dell'utente", ex);
        }
        return result;
    }
    
    @Override
    public List<Invito> getInviti(Task task) throws DataLayerException {
        List<Invito> result = new ArrayList();
        try {
            this.sInvitiByTask.setInt(1, task.getKey());
            try (ResultSet rs = this.sInvitiByTask.executeQuery()) {
                while (rs.next()) {
                    result.add((Invito) getInvito(rs.getInt("id")));
                }
            }
        } catch(SQLException ex) {
            throw new DataLayerException("Impossibile reperire gli inviti del task", ex);
        }
        return result;
    }
    
    @Override
    public Messaggio getMessaggio(int messaggio_key) throws DataLayerException {
        try {
            sMessaggioByID.setInt(1, messaggio_key);
            try (ResultSet rs = sMessaggioByID.executeQuery()) {
                if (rs.next()) {
                    return creaMessaggio(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare il messaggio", ex);
        }
        return null;
    }
    
    @Override
    public List<Messaggio> getMessaggi(Discussione discussione) throws DataLayerException{
        List<Messaggio> result = new ArrayList();
        try {
            sMessaggiByDiscussione.setInt(1, discussione.getKey());
            try (ResultSet rs = sMessaggiByDiscussione.executeQuery()) {
                while (rs.next()) {
                    result.add(creaMessaggio(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i messaggi", ex);
        }
        return result;
    }
    
    @Override
    public Progetto getProgetto(int progetto_key) throws DataLayerException {
        try {
            sProgettoByID.setInt(1, progetto_key);
            try (ResultSet rs = sProgettoByID.executeQuery()) {
                if (rs.next()) {
                    return creaProgetto(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare il progetto", ex);
        }
        return null;
    }
    
    @Override
    public List<Progetto> getProgetti(Utente utente) throws DataLayerException {
        List<Progetto> result = new ArrayList();
        try {
            sProgettiByUtente.setInt(1, utente.getKey());
            try (ResultSet rs = sProgettiByUtente.executeQuery()) {
                while (rs.next()) {
                    result.add(creaProgetto(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i progetti", ex);
        }
        return result;
    }
    
    @Override
    public List<Progetto> getProgetti(String filtro, int first, int perPage) throws DataLayerException {
        List<Progetto> result = new ArrayList();
        try {
            sProgettiByFiltro.setString(1, "%"+filtro+"%");
            sProgettiByFiltro.setString(2, "%"+filtro+"%");
            sProgettiByFiltro.setInt(3, first);
            sProgettiByFiltro.setInt(4, perPage);
            try (ResultSet rs = sProgettiByFiltro.executeQuery()) {
                while (rs.next()) {
                    result.add(creaProgetto(rs));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i progetti", ex);
        }
        return result;
    }
    
    public int getCountProgetti(String filtro) throws DataLayerException{
        int result = 0;
        try{
            countProgettiByFiltro.setString(1, "%"+filtro+"%");
            countProgettiByFiltro.setString(2, "%"+filtro+"%");
            try(ResultSet rs = countProgettiByFiltro.executeQuery()){
                if(rs.next()){
                    result = rs.getInt("total");
                }
            }
        }catch(SQLException ex){
            throw new DataLayerException("Impossibile contare i progetti", ex);
        }
        return result;
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
        try {
            this.sTipoByID.setInt(1, tipo_key);
            try (ResultSet rs = sTipoByID.executeQuery()) {
                if (rs.next())
                    return creaTipo(rs);
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile reperire il tipo", ex);
        }
        return null;
    }
    
    @Override
    public List<Tipo> getTipi(Skill skill) throws DataLayerException{
        List<Tipo> result = new ArrayList();
        try {
            this.sTipiBySkill.setInt(1, skill.getKey());
            try (ResultSet rs = this.sTipiBySkill.executeQuery()) {
                while (rs.next()) {
                    result.add((Tipo) getTipo(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile reperire i tipi delle skill", ex);
        }
        return result;
    }
    
    @Override
    public List<Tipo> getTipi() throws DataLayerException {
        List<Tipo> result = new ArrayList();
        try {
            try (ResultSet rs = this.sTipi.executeQuery()) {
                while (rs.next()) {
                    result.add((Tipo) getTipo(rs.getInt("id")));
                }
            }
        } catch(SQLException ex) {
            throw new DataLayerException("Impossibile reperire i tipi", ex);
        }
        return result;
    }
    
    @Override
    public Utente getUtente(int utente_key) throws DataLayerException {
        try {
            sUtenteByID.setInt(1, utente_key);
            try (ResultSet rs = sUtenteByID.executeQuery()) {
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
                    result.put(creaUtente(rs), rs.getInt("coprenti.voto"));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare gli utenti", ex);
        }
        return result;
    }

    @Override
    public List<Utente> getUtenti(String filtro, int first, int perPage) throws DataLayerException {
        List<Utente> result = new ArrayList();
        try {
            sUtentiByFiltro.setString(1, "%"+filtro+"%");
            sUtentiByFiltro.setString(2, "%"+filtro+"%");
            sUtentiByFiltro.setString(3, "%"+filtro+"%");
            sUtentiByFiltro.setInt(4, first);
            sUtentiByFiltro.setInt(5, perPage);
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
    public int getCountUtenti(String filtro) throws DataLayerException {
        int result = 0;
        
        try{
            countUtentiByFiltro.setString(1, "%"+filtro+"%");
            countUtentiByFiltro.setString(2, "%"+filtro+"%");
            countUtentiByFiltro.setString(3, "%"+filtro+"%");
            try (ResultSet rs = countUtentiByFiltro.executeQuery()) {
                if (rs.next()) {
                   result = rs.getInt("total");
                }
            }
        }catch(SQLException ex){
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
