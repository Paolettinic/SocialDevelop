package socialdevelop.data.impl;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.util.GregorianCalendar;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Task;
import socialdevelop.data.model.Utente;
import socialdevelop.data.model.Messaggio;
import socialdevelop.data.model.Discussione;
import java.util.List;

/**
 * @author Davide De Marco
 */
public class DiscussioneImpl implements Discussione {
    
    private int key;
    private String titolo;
    private boolean pubblica;
    private GregorianCalendar data;
    private Task task;
    private int task_key;
    private Utente utente;
    private int utente_key;
    private List<Messaggio> messaggi;
    protected SocialDevelopDataLayer ownerdatalayer;
    protected boolean dirty;
    
    public DiscussioneImpl(SocialDevelopDataLayer ownerdatalayer) {
        this.ownerdatalayer = ownerdatalayer;
        key = 0;
        titolo = "";
        pubblica = false;
        data = null;
        task = null;
        task_key = 0;
        utente = null;
        utente_key = 0;
        messaggi = null;
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
    public void setTitolo(String titolo) {
        this.titolo = titolo;
        this.dirty = true;
    }
    
    @Override
    public String getTitolo() {
        return titolo;
    }
    
    @Override
    public void setPubblica(boolean pubblica) {
        this.pubblica = pubblica;
        this.dirty = true;
    }
    
    @Override
    public boolean getPubblica() {
        return pubblica;
    }
    
    @Override
    public void setData(GregorianCalendar data) {
        this.data = data;
        this.dirty = true;
    }
    
    @Override
    public GregorianCalendar getData() {
        return data;
    }
    
    @Override
    public void setTask(Task task) {
        this.task = task;
        this.task_key = task.getKey();
        this.dirty = true;
    }
    
    protected void setTaskKey(int task_key) {
        this.task_key = task_key;
        this.task = null;
    }
    
    @Override
    public Task getTask() throws DataLayerException {
        if (task == null && task_key > 0) {
            task = ownerdatalayer.getTask(task_key);
        }
        return task;
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
    public void setMessaggi(List<Messaggio> messaggio) {
        this.messaggi = messaggio;
        this.dirty = true;
    }
    
    @Override
    public List<Messaggio> getMessaggi() throws DataLayerException {
        if(messaggi == null) {
            messaggi = ownerdatalayer.getMessaggi(this);
        }
        return messaggi;
    }
    
    @Override
    public void copyFrom(Discussione discussione) throws DataLayerException {
        key = discussione.getKey();
        titolo = discussione.getTitolo();
        pubblica = discussione.getPubblica();
        data = discussione.getData();
        task_key = discussione.getTask().getKey();
        utente_key = discussione.getUtente().getKey();
        
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
