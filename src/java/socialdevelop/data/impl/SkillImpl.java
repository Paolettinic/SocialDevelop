package socialdevelop.data.impl;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.util.List;
import socialdevelop.data.model.Skill;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Tipo;

/**
 * @author Mario Vetrini
 */

public class SkillImpl implements Skill {
    
    private int key;
    private String nome;
    // ----------
    private Skill skill_padre;
    private int skill_padre_key;
    List<Tipo> tipi;
    List<Skill> figli;
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
        figli = null;
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
    public Skill getPadre() throws DataLayerException {
        if (skill_padre == null && skill_padre_key > 0) {
            skill_padre = ownerdatalayer.getSkill(skill_padre_key);
        }
        return skill_padre;
    }

    @Override
    public void setPadre(Skill skill_padre) {
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
            tipi = ownerdatalayer.getTipi(this);
        }
        return tipi;
    }

    @Override
    public void setTipi(List<Tipo> tipi) {
        this.tipi = tipi;
        this.dirty = true;
    }
    
    @Override
    public List<Skill> getFigli() throws DataLayerException {
        if(figli == null) {
            figli = ownerdatalayer.getSkillsFigli(this);
        }
        return figli;
    }

    @Override
    public void setFigli(List<Skill> figli) {
        this.figli = figli;
        this.dirty = true;
    }

    @Override
    public void copyFrom(Skill skill) throws DataLayerException {
        key = skill.getKey();
        nome = skill.getNome();
        // ----------
        skill_padre_key = skill.getPadre().getKey();
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
