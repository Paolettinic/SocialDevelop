package socialdevelop.data.model;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.io.InputStream;

/**
 * @author Nicolò Paoletti
 * @author Mario Vetrini
 */

public interface FileSD {
    
    int getKey();
    
    String getTipo();
    
    void setTipo(String tipo);
    
    public String getNome();
    
    public void setNome(String nome);
    
    InputStream getFile() throws DataLayerException;
    
    void setFile(InputStream is) throws DataLayerException;
    
    void setDirty(boolean dirty);
    
    void copyFrom(FileSD file) throws DataLayerException;
    
    boolean isDirty();
    
}
