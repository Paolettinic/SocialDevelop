/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socialdevelop.data.impl;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import socialdevelop.data.model.SocialDevelopDataLayer;

import socialdevelop.data.model.Progetto;
import socialdevelop.data.model.Utente;
import socialdevelop.data.model.Task;
import socialdevelop.data.model.Discussione;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Davide De Marco
 */
public class ProgettoImpl implements Progetto{
    
    private int id;
    private String nome;
    private String descrizione;
    
    private Utente utente;
    private int userID;
    private List<Task> task;
    private Map<Discussione, Integer> discussione;
    
    protected SocialDevelopDataLayer ownerdatalayer;
    protected boolean dirty;
    
    public ProgettoImpl(SocialDevelopDataLayer ownerdatalayer) {
        this.ownerdatalayer = ownerdatalayer;
        id = 0;
        nome = "";
        descrizione = "";
        
        utente = null;
        userID = 0;
        task = null;
        discussione = null;
        
        dirty = false;
    }
    
    @Override
    public int getId() {
        return id;
    }
    
    @Override
    public void setNome() {
        this.nome = nome;
        this.dirty = true;
    }
    
    @Override
    public String getNome() {
        return nome;
    }
    
    @Override
    public void setDescrizione() {
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
    public void setTask(List<Task> task) {
        this.task = task;
        this.dirty = true;
    }
    
    @Override
    public List<Task> getTask() throws DataLayerException {
        if(task == null) {
            task = ownerdatalayer.getTasksByProgetto(this);
        }
        return task;
    }
    
    @Override
    public void setDiscussione(Map<Discussione, Integer> discussione) {
        this.discussione = discussione;
        this.dirty = true;
    }
    
    @Override
    public Map<Discussione, Integer> getDiscussione() throws DataLayerException {
        if(discussione == null) {
            discussione = ownerdatalayer.getDiscussioneByProgetto(this);
        }
        return discussione;
    }
    
    @Override
    public void copyFrom(Progetto progetto) throws DataLayerException {
        id = progetto.getId();
        nome = progetto.getNome();
        descrizione = progetto.getDescrizione();
        
        userID = progetto.getUtente().getKey();
        
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
