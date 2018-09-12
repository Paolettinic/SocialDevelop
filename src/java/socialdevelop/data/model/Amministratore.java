/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socialdevelop.data.model;

import it.univaq.f4i.iw.framework.data.DataLayerException;

/**
 *
 * @author Nicolò Paoletti
 */
public interface Amministratore {
    int getKey();
    
    String getUsername();
    
    void setUsername(String username) ;
    
    String getEmail();
    
    void setEmail(String email);
    
    String getPassword();
    
    void setPassword(String password);
    
    void copyFrom(Amministratore amministratore) throws DataLayerException;
    
    void setDirty(boolean dirty);
    
    boolean isDirty();
}
