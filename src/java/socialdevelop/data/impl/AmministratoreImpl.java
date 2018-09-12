/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socialdevelop.data.impl;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import socialdevelop.data.model.Amministratore;
import socialdevelop.data.model.Curriculum;
import socialdevelop.data.model.SocialDevelopDataLayer;

/**
 *
 * @author Nicolò Paoletti
 */
public class AmministratoreImpl implements Amministratore{
    
    private int key;
    private String username;
    private String email;
    private String password;
    protected SocialDevelopDataLayer ownerdatalayer;
    protected boolean dirty;
    
    public AmministratoreImpl(SocialDevelopDataLayer ownerdatalayer){
        this.ownerdatalayer = ownerdatalayer;
        this.key = 0;
        this.username = "";
        this.email = "";
        this.password = "";
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
    public String getUsername() {
        return this.username; 
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
        this.dirty = true;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
        this.dirty = true;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
        this.dirty = true;
    }

    @Override
    public void copyFrom(Amministratore amministratore) throws DataLayerException {
        this.key = amministratore.getKey();
        this.username = amministratore.getUsername();
        this.email = amministratore.getEmail();
        this.password = amministratore.getPassword();
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
