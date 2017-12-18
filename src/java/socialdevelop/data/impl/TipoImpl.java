package socialdevelop.data.impl;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.util.List;
import socialdevelop.data.model.Skill;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Tipo;

/**
 * @author Nicolò Paoletti
 */
public class TipoImpl implements Tipo {
    
    private int key;
    private String nome;
    private List<Skill> skills;
    protected SocialDevelopDataLayer ownerdatalayer;
    protected boolean dirty;

    public TipoImpl(SocialDevelopDataLayer ownerdatalayer){
        this.ownerdatalayer = ownerdatalayer;
        this.key = 0;
        this.nome = "";
        this.skills = null;
        this.dirty = false;
    }

    @Override
    public int getKey() {
        return this.key;
    }

    protected void setKey(int key){
        this.key = key;
    }

    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public List<Skill> getSkills() throws DataLayerException{
        if(this.skills == null)
                skills = ownerdatalayer.getSkillsByTipo(this.key);
        return this.skills;
    }


    @Override
    public boolean isDirty() {
        return this.dirty;
    }

    @Override
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    @Override
    public void copyFrom(Tipo tipo) throws DataLayerException {
        this.key = tipo.getKey();
        this.nome = tipo.getNome();
        this.dirty = true;
    }
    
}
