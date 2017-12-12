/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socialdevelop.data.impl;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import socialdevelop.data.model.SocialDevelopDataLayer;

import socialdevelop.data.model.Progetto;
import socialdevelop.data.model.Task;
import socialdevelop.data.model.Utente;
import socialdevelop.data.model.Messaggio;
import socialdevelop.data.model.Discussione;

import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Davide De Marco
 */
public class DiscussioneImpl implements Discussione {
    
    private int id;
    private String titolo;
    private int pubblica;
    private Calendar data;
    
    private Progetto progetto;
    private int progetto_key;
    private Task task;
    private int taskID;
    private Utente utente;
    private int userID;
    private List<Messaggio> messaggio;
    
    protected SocialDevelopDataLayer ownerdatalayer;
    protected boolean dirty;
    
    public DiscussioneImpl(SocialDevelopDataLayer ownerdatalayer) {
        this.ownerdatalayer = ownerdatalayer;
        id = 0;
        titolo = "";
        pubblica = 0;
        data = null;
        
        progetto = null;
        progetto_key = 0;
        task = null;
        taskID = 0;
        utente = null;
        userID = 0;
        messaggio = null;
      
        dirty = false;
    }
    
    @Override
    public int getId() {
        return id;
    }
    
    @Override
    public void setTitolo() {
        this.titolo = titolo;
        this.dirty = true;
    }
    
    @Override
    public String getTitolo() {
        return titolo;
    }
    
    @Override
    public void setPubblica() {
        this.pubblica = pubblica;
        this.dirty = true;
    }
    
    @Override
    public int getPubblica() {
        return pubblica;
    }
    
    @Override
    public void setData() {
        this.data = data;
        this.dirty = true;
    }
    
    @Override
    public Calendar getData() {
        return data;
    }
    
    @Override
    public void setProgetto(Progetto progetto) {
        this.progetto = progetto;
        this.progetto_key = progetto.getId();
        this.dirty = true;
    }
    
    @Override
    public Progetto getProgetto() throws DataLayerException {
        if (progetto == null && progetto_key > 0) {
            progetto = ownerdatalayer.getProgetto(progetto_key);
        }
        return progetto;
    }
    
    @Override
    public void setTask(Task task) {
        this.task = task;
        this.taskID = task.getKey();
        this.dirty = true;
    }
    
    @Override
    public Task getTask() throws DataLayerException {
        if (task == null && taskID > 0) {
            task = ownerdatalayer.getTask(taskID);
        }
        return task;
    }
    
    @Override
    public void setUtente(Utente utente) {
        this.utente = utente;
        this.userID = utente.getKey();
        this.dirty = true;
    }
    
    @Override
    public Utente getUtente() throws DataLayerException {
        if (utente == null && userID > 0) {
            utente = ownerdatalayer.getUser(userID);
        }
        return utente;
    }
    
    @Override
    public void setMessaggio(List<Messaggio> messaggio) {
        this.messaggio = messaggio;
        this.dirty = true;
    }
    
    @Override
    public List<Messaggio> getMessaggio() throws DataLayerException {
        if(messaggio == null) {
            messaggio = ownerdatalayer.getMessaggioByDiscussione(this);
        }
        return messaggio;
    }
    
    @Override
    public void copyFrom(Discussione discussione) throws DataLayerException {
        id = discussione.getId();
        titolo = discussione.getTitolo();
        pubblica = discussione.getPubblica();
        data = discussione.getData();
        
        progetto_key = discussione.getProgetto().getId();
        taskID = discussione.getTask().getKey();
        userID = discussione.getUtente().getKey();
        
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
