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
    
    Skill getSkillPadre() throws DataLayerException;

    void setSkillPadre(Skill skill_padre);
    
    List<Tipo> getTipi() throws DataLayerException;
    
    void SetTipi(List<Tipo> tipi);
    
    // ----------
    
    void copyFrom(Skill skill) throws DataLayerException;
    
    void setDirty(boolean dirty);

    boolean isDirty();
    
}
