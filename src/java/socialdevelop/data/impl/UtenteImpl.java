/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socialdevelop.data.impl;

import java.util.GregorianCalendar;
import java.util.Map;
import socialdevelop.data.model.Skill;
import socialdevelop.data.model.Task;
import socialdevelop.data.model.Utente;

/**
 * @author Mario
 */
public class UtenteImpl implements Utente {
    
    private int key;
    private String nome;
    private String cognome;
    private String username;
    private String email;
    private GregorianCalendar datanascita;
    private String password;
    private String biografia;
    // ----------
    private Curriculum curriculum;
    private int curriculum_key;
    private Immagine immagine;
    private int immagine_key;
    private Map<Skill, Integer> skills;
    private Map<Task, Integer> tasks;
    // ----------
    protected SocialDevelopDataLayer ownerdatalayer;
    protected boolean dirty;
    
    public UtenteImpl(SocialDevelopDataLayer ownerdatalayer) {
        this.ownerdatalayer = ownerdatalayer;
        // ----------
        key = 0;
        nome = "";
        cognome = "";
        username = "";
        email = "";
        datanascita = null;
        password = "";
        biografia = "";
        // ----------
        curriculum = null;
        curriculum_key = 0;
        immagine = null;
        immagine_key = 0;
        skills = null;
        tasks = null;
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

    @Override
    public String getCognome() {
        return cognome;
    }

    @Override
    public void setCognome(String cognome) {
        this.cognome = cognome;
        this.dirty = true;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
        this.dirty = true;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
        this.dirty = true;
    }

    @Override
    public GregorianCalendar getDataNascita() {
        return datanascita;
    }

    @Override
    public void setDataNascita(GregorianCalendar datanascita) {
        this.datanascita = datanascita;
        this.dirty = true;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
        this.dirty = true;
    }

    @Override
    public String getBiografia() {
        return biografia;
    }

    @Override
    public void setBiografia(String biografia) {
        this.biografia = biografia;
        this.dirty = true;
    }
    
    // ----------

    @Override
    public Curriculum getCurriculum() throws DataLayerException {
        if (curriculum == null && curriculum_key > 0) {
            curriculum = ownerdatalayer.getCurriculum(curriculum_key);
        }
    }

    @Override
    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
        this.curriculum_key = curriculum.getKey();
        this.dirty = true;
    }
    
    protected void seturriculumKey(int curriculum_key) {
        this.curriculum_key = curriculum_key;
        this.curriculum = null;
    }

    @Override
    public Immagine getImmagine() throws DataLayerException {
        if (immagine == null && immagine_key > 0) {
            immagine = ownerdatalayer.getImmagine(immagine_key);
        }
    }

    @Override
    public void setImmagine(Immagine immagine) {
        this.immagine = immagine;
        this.immagine_key = immagine.getKey();
        this.dirty = true;
    }
    
    protected void setImmagineKey(int immagine_key) {
        this.immagine_key = immagine_key;
        this.immagine = null;
    }
    
    @Override
    public Map<Skill, Integer> getSkills() throws DataLayerException {
        if(skills == null){
            skills = ownerdatalayer.getSkillsByUtente(this);
        }
        return skills;
    }

    @Override
    public void setSkills(Map<Skill, Integer> skills) {
        this.skills = skills;
        this.dirty = true;
    }
    
    @Override
    public Map<Task, Integer> getTasks() throws DataLayerException {
        if(tasks == null){
            tasks = ownerdatalayer.getTasksByUtente(this);
        }
        return tasks;
    }

    @Override
    public void setTasks(Map<Task, Integer> tasks) {
        this.tasks = tasks;
        this.dirty = true;
    }
    
    // ----------

    @Override
    public void copyFrom(Utente utente) throws DataLayerException {
        key = utente.getKey();
        nome = utente.getNome();
        cognome = utente.getCognome();
        username = utente.getUsername();
        email = utente.getEmail();
        datanascita = utente.getDataNascita();
        password = utente.getPassword();
        biografia = utente.getBiografia();
        curriculum_key = utente.getCurriculum().getKey();
        immagine_key = utente.getImmagine().getKey();
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
