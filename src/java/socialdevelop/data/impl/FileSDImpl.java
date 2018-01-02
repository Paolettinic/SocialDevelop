package socialdevelop.data.impl;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import socialdevelop.data.model.FileSD;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Utente;

/**
 * @author Nicolò Paoletti
 */
public class FileSDImpl implements FileSD {
    
    private int key;
    private String nome;
    private String tipo;
    private long grandezza;
    private Utente utente;
    private int utente_key;
    protected SocialDevelopDataLayer ownerdatalayer;
    protected boolean dirty;
    
    public FileSDImpl(SocialDevelopDataLayer ownerdatalayer) {
        this.ownerdatalayer = ownerdatalayer;
        this.key = 0;
        this.nome = "";
        this.tipo = "";
        this.grandezza = 0;
        this.utente = null;
        this.utente_key = 0;
        this.dirty = false;
    }
    
    @Override
    public int getKey() {
        return this.key;
    }
    
    protected void setKey(int key) {
        this.key = key;
    }
    
    @Override
    public String getTipo() {
        return this.tipo;
    }
    
    @Override
    public void setTipo(String tipo) {
        this.tipo = tipo;
        this.dirty = true;
    }
    
    @Override
    public long getGrandezza() {
        return this.grandezza;
    }
    
    protected void setGrandezza(int grandezza) {
        this.grandezza = grandezza;
    }
    
    @Override
    public String getNome() {
        return this.nome;
    }
    
    @Override
    public void setNome(String nome) {
        this.nome = nome;
        this.dirty = true;
    }
    
    @Override
    public InputStream getFile() throws DataLayerException {
        try {
            return new FileInputStream(nome);
        } catch (FileNotFoundException ex) {
            throw new DataLayerException("Errore in fase di apertura dell'immagine", ex);
        }
    }
    
    @Override
    public void setFile(InputStream is) throws DataLayerException {
        OutputStream os = null;
        try {
            byte[] buffer = new byte[1024];
            os = new FileOutputStream(nome);
            int read;
            while ((read = is.read(buffer)) > 0) {
                os.write(buffer, 0, read);
            }
            this.dirty = true;
        } catch (FileNotFoundException ex) {
            throw new DataLayerException("Erroe in fase di salvataggio dell'immagine", ex);
        } catch (IOException ex) {
            throw new DataLayerException("Erroe in fase di salvataggio dell'immagine", ex);
        } finally {
            try {
                os.close();
            } catch (IOException ex) {
                Logger.getLogger(ImmagineImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public Utente getUtente() throws DataLayerException{
        if(this.utente == null && this.utente_key>0)
            this.utente = ownerdatalayer.getUtente(this.utente_key);
        return this.utente;
    }
    
    @Override
    public void setUtente(Utente user){
        this.utente = user;
        this.utente_key = user.getKey();
        this.dirty = true;
    }
    
    protected void setUtenteKey(int utente_key) {
        this.utente_key = utente_key;
        this.utente = null;
    }
    
    @Override
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
    
    @Override
    public boolean isDirty() {
        return dirty;
    }

  @Override
  public void copyFrom(FileSD file) throws DataLayerException {
    try{
      this.key = file.getKey();
      this.nome = file.getNome();
      this.grandezza = file.getGrandezza();
      this.tipo = file.getTipo();
      this.utente_key = file.getUtente().getKey();
    } catch(DataLayerException ex){
      throw new DataLayerException("Impossibile copiare il file",ex);
    }
  }
}
