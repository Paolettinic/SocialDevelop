package socialdevelop.data.model;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.io.InputStream;

/**
 * @author Nicolò Paoletti
 */
public interface FileSD {
    
    int getKey();
    
    String getTipo();

    void setTipo(String tipo);
    
    long getGrandezza();

    public String getNome();

    public void setNome(String nome);

    InputStream getFile() throws DataLayerException;

    void setFile(InputStream is) throws DataLayerException;
    
    Utente getUtente() throws DataLayerException;

    void setUtente(Utente utente);

    void setDirty(boolean dirty);

    boolean isDirty();
    
}
