package socialdevelop.data.impl;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import socialdevelop.data.model.SocialDevelopDataLayer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import socialdevelop.data.model.Curriculum;

/**
 * @author Nicolò Paoletti
 * @author Mario Vetrini;
 */

public class CurriculumImpl implements Curriculum {
    
    private int key;
    private String nome;
    private String tipo;
    private String testuale;
    protected SocialDevelopDataLayer ownerdatalayer;
    protected boolean dirty;
    
    public CurriculumImpl(SocialDevelopDataLayer ownerdatalayer) {
        this.ownerdatalayer = ownerdatalayer;
        this.key = 0;
        this.nome = "";
        this.tipo = "";
        this.testuale = "";
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
    public String getNome() {
        return this.nome;
    }
    
    @Override
    public void setNome(String nome) {
        this.nome = nome;
        this.dirty = true;
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
    public String getTestuale() {
        return this.testuale;
    }
    
    @Override
    public void setTestuale(String testuale) {
        this.testuale = testuale;
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
            throw new DataLayerException("Errore in fase di salvataggio del curriculum", ex);
        } catch (IOException ex) {
            throw new DataLayerException("Errore in fase di salvataggio del curriculum", ex);
        } finally {
            try {
                os.close();
            } catch (IOException ex) {
                Logger.getLogger(ImmagineImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public void copyFrom(Curriculum curriculum) throws DataLayerException {
        this.key = curriculum.getKey();
        this.nome = curriculum.getNome();
        this.tipo = curriculum.getTipo();
        this.testuale = curriculum.getTestuale();
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
