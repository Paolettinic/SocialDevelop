package socialdevelop.data.impl;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Progetto;
import socialdevelop.data.model.Utente;
import socialdevelop.data.model.Task;
import java.util.List;

/**
 * @author Davide De Marco
 */

public class ProgettoImpl implements Progetto {
    
    private int key;
    private String nome;
    private String descrizione;
    
    private Utente utente;
    private int utente_key;
    private List<Task> tasks;
    
    protected SocialDevelopDataLayer ownerdatalayer;
    protected boolean dirty;
    
    public ProgettoImpl(SocialDevelopDataLayer ownerdatalayer) {
        this.ownerdatalayer = ownerdatalayer;
        key = 0;
        nome = "";
        descrizione = "";
        utente = null;
        utente_key = 0;
        tasks = null;
        dirty = false;
    }
    
    protected void setKey(int key) {
        this.key = key;
    }
    
    @Override
    public int getKey() {
        return key;
    }
    
    @Override
    public void setNome(String nome) {
        this.nome = nome;
        this.dirty = true;
    }
    
    @Override
    public String getNome() {
        return nome;
    }
    
    @Override
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
        this.dirty = true;
    }
    
    @Override
    public String getDescrizione() {
        return descrizione;
    }
    
    @Override
    public void setUtente(Utente utente) {
        this.utente = utente;
        this.utente_key = utente.getKey();
        this.dirty = true;
    }
    
    protected void setUtenteKey(int utente_key) {
        this.utente_key = utente_key;
        this.utente = null;
    }
    
    @Override
    public Utente getUtente() throws DataLayerException {
        if (utente == null && utente_key > 0) {
            utente = ownerdatalayer.getUtente(utente_key);
        }
        return utente;
    }
    
    @Override
    public void setTasks(List<Task> task) {
        this.tasks = task;
        this.dirty = true;
    }
    
    @Override
    public List<Task> getTasks() throws DataLayerException {
        if(tasks == null) {
            tasks = ownerdatalayer.getTasks(this);
        }
        return tasks;
    }
    
    @Override
    public void copyFrom(Progetto progetto) throws DataLayerException {
        key = progetto.getKey();
        nome = progetto.getNome();
        descrizione = progetto.getDescrizione();
        utente_key = progetto.getUtente().getKey();
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
