package socialdevelop.data.impl;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.util.GregorianCalendar;
import java.util.Map;
import socialdevelop.data.model.Progetto;
import socialdevelop.data.model.Skill;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Task;
import socialdevelop.data.model.Tipo;
import socialdevelop.data.model.Utente;

/**
 * @author Mario
 */
public class TaskImpl implements Task {
    
    private int key;
    private String nome;
    private String descrizione;
    private boolean chiuso;
    private int numero_corrente_collaboratori;
    private int numero_massimo_collaboratori;
    private GregorianCalendar data_inizio;
    private GregorianCalendar data_fine;
    // ----------
    private Progetto progetto;
    private int progetto_key;
    private Tipo tipo;
    private int tipo_key;
    private Map<Skill, Integer> skills;
    private Map<Utente, Integer> utenti;
    // ----------
    protected SocialDevelopDataLayer ownerdatalayer;
    protected boolean dirty;
    
    public TaskImpl(SocialDevelopDataLayer ownerdatalayer) {
        this.ownerdatalayer = ownerdatalayer;
        // ----------
        key = 0;
        nome = "";
        descrizione = "";
        chiuso = false;
        numero_corrente_collaboratori = 0;
        numero_massimo_collaboratori = 0;
        data_inizio = null;
        data_fine = null;
        // ----------
        progetto = null;
        progetto_key = 0;
        tipo = null;
        tipo_key = 0;
        skills = null;
        utenti = null;
        // ----------
        dirty = false;
    }

    @Override
    public int getKey() {
        return key;
    }
    
    protected void setKey(int key) {
        this.key = key;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
        this.dirty = true;
    }

    @Override
    public String getDescrizione() {
        return descrizione;
    }

    @Override
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
        this.dirty = true;
    }
    
    @Override
    public boolean getChiuso() {
        return chiuso;
    }

    @Override
    public void setChiuso(boolean chiuso) {
        this.chiuso = chiuso;
        this.dirty = true;
    }

    @Override
    public int getNumeroCorrenteCollaboratori() {
        return numero_corrente_collaboratori;
    }

    @Override
    public void setNumeroCorrenteCollaboratori(int numero_corrente_collaboratori) {
        this.numero_corrente_collaboratori = numero_corrente_collaboratori;
        this.dirty = true;
    }

    @Override
    public int getNumeroMassimoCollaboratori() {
        return numero_massimo_collaboratori;
    }

    @Override
    public void setNumeroMassimoCollaboratori(int numero_massimo_collaboratori) {
        this.numero_massimo_collaboratori = numero_massimo_collaboratori;
        this.dirty = true;
    }

    @Override
    public GregorianCalendar getDataInizio() {
        return data_inizio;
    }

    @Override
    public void setDataInizio(GregorianCalendar data_inizio) {
        this.data_inizio = data_inizio;
        this.dirty = true;
    }

    @Override
    public GregorianCalendar getDataFine() {
        return data_fine;
    }

    @Override
    public void setDataFine(GregorianCalendar data_fine) {
        this.data_fine = data_fine;
        this.dirty = true;
    }
    
    // ----------

    @Override
    public Progetto getProgetto() throws DataLayerException{
        if (progetto == null && progetto_key > 0) {
            progetto = ownerdatalayer.getProgetto(progetto_key);
        }
        return progetto;
    }

    @Override
    public void setProgetto(Progetto progetto) {
        this.progetto = progetto;
        this.progetto_key = progetto.getKey();
        this.dirty = true;
    }
    
    protected void setProgettoKey(int progetto_key) {
        this.progetto_key = progetto_key;
        this.progetto = null;
    }

    @Override
    public Tipo getTipo() throws DataLayerException{
        if (tipo == null && tipo_key > 0) {
            tipo = ownerdatalayer.getTipo(tipo_key);
        }
        return tipo;
    }

    @Override
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
        this.tipo_key = tipo.getKey();
        this.dirty = true;
    }
    
    protected void setTipoKey(int tipo_key) {
        this.tipo_key = tipo_key;
        this.tipo = null;
    }
    
    @Override
    public Map<Skill, Integer> getSkills() throws DataLayerException {
        if(skills == null) {
            skills = ownerdatalayer.getSkills(this);
        }
        return skills;
    }

    @Override
    public void setSkills(Map<Skill, Integer> skills) {
        this.skills = skills;
        this.dirty = true;
    }

    @Override
    public Map<Utente, Integer> getUtenti() throws DataLayerException {
        if(utenti == null) {
            utenti = ownerdatalayer.getUtenti(this);
        }
        return utenti;
    }

    @Override
    public void setUtenti(Map<Utente, Integer> utenti) {
        this.utenti = utenti;
        this.dirty = true;
    }
    
    // ----------

    @Override
    public void copyFrom(Task task) throws DataLayerException {
        key = task.getKey();
        nome = task.getNome();
        descrizione = task.getDescrizione();
        numero_corrente_collaboratori = task.getNumeroCorrenteCollaboratori();
        numero_massimo_collaboratori = task.getNumeroMassimoCollaboratori();
        data_inizio = task.getDataInizio();
        data_fine = task.getDataFine();
        // ----------
        progetto_key = task.getProgetto().getKey();
        tipo_key = task.getTipo().getKey();
        // ----------
        this.dirty = true;
    }

    @Override
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    @Override
    public boolean isDirty() {
        return dirty;
    }
    
}
