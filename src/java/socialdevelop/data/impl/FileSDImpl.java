/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socialdevelop.data.impl;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.io.File;
import socialdevelop.data.model.FileSD;
import socialdevelop.data.model.SocialDevelopDataLayer;
import socialdevelop.data.model.Utente;
/**
 *
 * @author Nicolò Paoletti
 */
public class FileSDImpl implements FileSD{
	
	private int file_id;
	private File percorso;
	private String nome;
	private int userID;
	private Utente utente;
	protected boolean dirty;
	protected SocialDevelopDataLayer ownerdatalayer;

	
	public FileSDImpl(SocialDevelopDataLayer ownerdatalayer){
		this.ownerdatalayer = ownerdatalayer;
		this.file_id = 0;
		this.percorso = null;
		this.nome = "";
		this.userID = 0;
		this.utente = null;
		this.dirty = false;
	}
	
	@Override
	public int getKey() {
		return this.file_id;
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
	}

	@Override
	public Utente getUtente() throws DataLayerException {
		if(this.utente == null && this.userID > 0)
			this.utente = ownerdatalayer.getUser(this.userID);
		return this.utente;
	}

	@Override
	public void setUtente(Utente utente) {
		this.userID = utente.getKey();
		this.utente = utente;
		this.dirty = true;
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
	public int getUtenteKey() {
		return this.userID;
	}

	@Override
	public void setUtenteKey(int utente_key) {
		this.userID = utente_key;
		this.dirty = true;
	}

}
