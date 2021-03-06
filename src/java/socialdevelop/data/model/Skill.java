package socialdevelop.data.model;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.util.List;

/**
 * @author Mario Vetrini
 */

public interface Skill {
    
    int getKey();
    
    String getNome();
    
    void setNome(String nome);
    
    // ----------
    
    Skill getPadre() throws DataLayerException;
    
    void setPadre(Skill skill_padre);
    
    List<Tipo> getTipi() throws DataLayerException;
    
    void setTipi(List<Tipo> tipi);
    
    List<Skill> getFigli() throws DataLayerException;
    
    void setFigli(List<Skill> figli);
    
    // ----------
    
    void copyFrom(Skill skill) throws DataLayerException;
    
    void setDirty(boolean dirty);
    
    boolean isDirty();
    
}
