package socialdevelop.data.impl;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.util.List;
import socialdevelop.data.model.Skill;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Tipo;

/**
 * @author Mario
 */
public class SkillImpl implements Skill {
    
    private int key;
    private String nome;
    // ----------
    private Skill skill_padre;
    private int skill_padre_key;
    List<Tipo> tipi;
    // ----------
    protected SocialDevelopDataLayer ownerdatalayer;
    protected boolean dirty;
    
    public SkillImpl(SocialDevelopDataLayer ownerdatalayer) {
        this.ownerdatalayer = ownerdatalayer;
        // ----------
        key = 0;
        nome = "";
        // ----------
        skill_padre = null;
        skill_padre_key = 0;
        tipi = null;
        // ----------
        dirty = false;
    }

    @Override
    public int getKey() {
        return key;
    }
    
    protected void setKey(int key) {
        this.key = key;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
        this.dirty = true;
    }
    
    // ----------

    @Override
    public Skill getSkillPadre() throws DataLayerException {
        if (skill_padre == null && skill_padre_key > 0) {
            skill_padre = ownerdatalayer.getSkill(skill_padre_key);
        }
        return skill_padre;
    }

    @Override
    public void setSkillPadre(Skill skill_padre) {
        this.skill_padre = skill_padre;
        this.skill_padre_key = skill_padre.getKey();
        this.dirty = true;
    }
    
    protected void setSkillPadreKey(int skill_padre_key) {
        this.skill_padre_key = skill_padre_key;
        this.skill_padre = null;
    }

    @Override
    public List<Tipo> getTipi() throws DataLayerException {
        if(tipi == null) {
            tipi = ownerdatalayer.getTipiBySkill(this);
        }
        return tipi;
    }

    @Override
    public void SetTipi(List<Tipo> tipi) {
        this.tipi = tipi;
        this.dirty = true;
    }

    @Override
    public void copyFrom(Skill skill) throws DataLayerException {
        key = skill.getKey();
        nome = skill.getNome();
        // ----------
        skill_padre_key = skill.getSkillPadre().getKey();
        // ----------
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
