/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socialdevelop.data.impl;

import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.io.File;
import socialdevelop.data.model.Immagine;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Utente;

/**
 *
 * @author Nicolò Paoletti
 */
public class ImmagineImpl implements Immagine {

	private int key;
	private String nome;
	private File percorso;
	private Utente utente;
	private int utente_key;
	protected SocialDevelopDataLayer ownerdatalayer;
	protected boolean dirty;
	
	public ImmagineImpl(SocialDevelopDataLayer ownerdatalayer){
		this.ownerdatalayer = ownerdatalayer;
		this.key = 0;
		this.nome = "";
		this.percorso = null;
		this.utente = null;
		this.utente_key = 0;
		this.dirty = false;
	}
	
	@Override
	public int getKey() {
		return this.key;
	}

	@Override
	public String getNome() {
		return this.nome;
	}

	
	@Override
	public void setNome(String nome) {
		this.nome = nome;
		this.dirty = true;
	}

	@Override
	public File getPercorso() {
		return this.percorso;
	}

	@Override
	public void setPercorso(File percorso) {
		this.percorso = percorso;
		this.dirty = true;
	}
	
	@Override
	public Utente getUtente() throws DataLayerException{
		if(this.utente == null && this.utente_key>0)
			this.utente = ownerdatalayer.getUser(this.utente_key);
		return this.utente;
	}

	@Override
	public void setUtente(Utente user){
		this.utente = user;
		this.utente_key = user.getKey();
		this.dirty = true;
	}

	@Override
	public int getUtenteKey() {
		return this.utente_key;
	}

	@Override
	public void setUtenteKey(int utente_key) {
		this.utente_key = utente_key;
	}

	@Override
	public boolean isDirty() {
		return this.dirty;
	}

	@Override
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

}
