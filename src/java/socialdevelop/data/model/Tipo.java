package socialdevelop.data.model;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.util.List;

/**
 * @author Nicolò Paoletti
 */
public interface Tipo {
    
    int getKey();

    String getNome();

    void setNome(String nome);

    List<Skill> getSkills() throws DataLayerException;

    void copyFrom(Tipo tipo) throws DataLayerException;
    
    void setDirty(boolean dirty);

    boolean isDirty();
    
}
