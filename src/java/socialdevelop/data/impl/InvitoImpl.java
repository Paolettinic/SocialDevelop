package socialdevelop.data.impl;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import socialdevelop.data.model.SocialDevelopDataLayer;
import java.util.GregorianCalendar;
import socialdevelop.data.model.Task;
import socialdevelop.data.model.Utente;
import socialdevelop.data.model.Invito;

/**
 * @author Nicolò Paoletti
 */
public class InvitoImpl implements Invito {
    
    private int key;
    private String messaggio;
    private GregorianCalendar data;
    private boolean offerta;
    private String stato;
    private Utente utente;
    private int utente_key;
    private Task task;
    private int task_key;
    protected SocialDevelopDataLayer ownerdatalayer;
    protected boolean dirty;
    
    public InvitoImpl(SocialDevelopDataLayer ownerdatalayer) {
        this.ownerdatalayer = ownerdatalayer;
        this.key = 0;
        this.messaggio = "";
        this.data = null;
        this.offerta = false;
        this.stato = "";
        this.utente_key = 0;
        this.utente = null;
        this.task = null;
        this.task_key = 0;
    }
    
    @Override
    public int getKey() {
        return this.key;
    }
    
    protected void setKey(int key){
        this.key = key;
    }
    
    @Override
    public Utente getUtente() throws DataLayerException{
        if(this.utente == null && utente_key > 0)
            this.utente = ownerdatalayer.getUtente(utente_key);
        return this.utente;
    }
    
    @Override
    public void setUtente(Utente user) {
        this.utente = user;
        this.utente_key = user.getKey();
        this.dirty = true;
    }
    
    @Override
    public String getMessaggio() {
        return this.messaggio;
    }
    
    @Override
    public void setMessaggio(String message) {
        this.messaggio = message;
        this.dirty = true;
    }
    
    @Override
    public String getStato() {
        return this.stato;
    }
    
    @Override
    public void setStato(String stato) {
        this.stato = stato;
        this.dirty = true;
    }
    
    @Override
    public boolean getOfferta() {
        return this.offerta;
    }
    
    @Override
    public void setOfferta(boolean isOffer) {
        this.offerta = isOffer;
        this.dirty = true;
        
    }
    
    @Override
    public Task getTask() throws DataLayerException{
        if(this.task == null && this.task_key > 0)
            this.task = ownerdatalayer.getTask(this.task_key);
        return this.task;
    }
    
    @Override
    public void setTask(Task task) {
        this.task = task;
        this.task_key = task.getKey();
        this.dirty = true;
    }
    
    protected void setTaskKey(int task_key){
        this.task_key = task_key;
        this.task = null;
    }
    
    @Override
    public GregorianCalendar getData() {
        return this.data;
    }
    
    @Override
    public void setData(GregorianCalendar date) {
        this.data = date;
        this.dirty = true;
    }
    
    @Override
    public void copyFrom(Invito invito) throws DataLayerException {
        this.key = invito.getKey();
        this.messaggio = invito.getMessaggio();
        this.data = invito.getData();
        this.offerta = invito.getOfferta();
        this.utente_key = invito.getUtente().getKey();
        this.stato = invito.getStato();
        this.task_key = invito.getTask().getKey();
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
