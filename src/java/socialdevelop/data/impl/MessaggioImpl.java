package socialdevelop.data.impl;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Messaggio;
import socialdevelop.data.model.Utente;
import socialdevelop.data.model.Discussione;
import java.util.GregorianCalendar;

/**
 * @author Davide De Marco
 */
public class MessaggioImpl implements Messaggio {
    
    private int key;
    private String testo;
    private GregorianCalendar data;
    
    private Utente utente;
    private int utente_key;
    private Discussione discussione;
    private int discussione_key;
    
    protected SocialDevelopDataLayer ownerdatalayer;
    protected boolean dirty;

    public MessaggioImpl(SocialDevelopDataLayer ownerdatalayer) {
        this.ownerdatalayer = ownerdatalayer;
        key = 0;
        testo = "";
        data = null;
        utente = null;
        utente_key = 0;
        discussione = null;
        discussione_key = 0;
        dirty = false;
    }
    
    @Override
    public int getKey() {
        return key;
    }
    
    @Override
    public void setTesto(String testo) {
        this.testo = testo;
        this.dirty = true;
    }
    
    @Override
    public String getTesto() {
        return testo;
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
    public void setUtente(Utente utente) {
        this.utente = utente;
        this.utente_key = utente.getKey();
        this.dirty = true;
    }
    
    @Override
    public Utente getUtente() throws DataLayerException {
        if (utente == null && utente_key > 0) {
            utente = ownerdatalayer.getUser(utente_key);
        }
        return utente;
    }

    @Override
    public void setDiscussione(Discussione discussione) {
        this.discussione = discussione;
        this.discussione_key = discussione.getKey();
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
        key = messaggio.getKey();
        testo = messaggio.getTesto();
        data = messaggio.getData();
        utente_key = messaggio.getUtente().getKey();
        discussione_key = messaggio.getDiscussione().getKey();
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
