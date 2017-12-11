package socialdevelop.data.model;

import java.util.List;

/**
 * @author Mario
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
