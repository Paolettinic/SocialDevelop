package socialdevelop.data.model;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.io.InputStream;

/**
 * @author Mario Vetrini
 */

public interface Immagine {
    
    int getKey();
    
    public String getNome();
    
    public void setNome(String nome);
    
    String getTipo();
    
    void setTipo(String tipo);
    
    InputStream getFile() throws DataLayerException;
    
    void setFile(InputStream is) throws DataLayerException;
    
    void copyFrom(Immagine immagine) throws DataLayerException;
    
    void setDirty(boolean dirty);
    
    boolean isDirty();
    
}
