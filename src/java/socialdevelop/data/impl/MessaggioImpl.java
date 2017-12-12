/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socialdevelop.data.impl;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import socialdevelop.data.model.SocialDevelopDataLayer;

import socialdevelop.data.model.Messaggio;
import socialdevelop.data.model.Utente;
import socialdevelop.data.model.Discussione;

import java.util.Calendar;
/**
 *
 * @author Davide De Marco
 */
public class MessaggioImpl implements Messaggio {
    
    private int id;
    private String testo;
    private Calendar data;
    
    private Utente utente;
    private int userID;
    private Discussione discussione;
    private int discussione_key;
    
    protected SocialDevelopDataLayer ownerdatalayer;
    protected boolean dirty;

    public MessaggioImpl(SocialDevelopDataLayer ownerdatalayer) {
        this.ownerdatalayer = ownerdatalayer;
        id = 0;
        testo = "";
        data = null;
        
        utente = null;
        userID = 0;
        discussione = null;
        discussione_key = 0;
        
        dirty = false;
    }
    
    @Override
    public int getId() {
        return id;
    }
    
    @Override
    public void setTesto() {
        this.testo = testo;
        this.dirty = true;
    }
    
    @Override
    public String getTesto() {
        return testo;
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
    public void setDiscussione(Discussione discussione) {
        this.discussione = discussione;
        this.discussione_key = discussione.getId();
        this.dirty = true;
    }
    
    @Override
    public Discussione getDiscussione() throws DataLayerException {
        if (discussione == null && discussione_key > 0) {
            discussione = ownerdatalayer.getDiscussione(discussione_key);
        }
        return discussione;
    }
    
    @Override
    public void copyFrom(Messaggio messaggio) throws DataLayerException {
        id = messaggio.getId();
        testo = messaggio.getTesto();
        data = messaggio.getData();
        
        userID = messaggio.getUtente().getKey();
        discussione_key = messaggio.getDiscussione().getId();
        
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
