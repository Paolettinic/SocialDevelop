/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socialdevelop.data.impl;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import socialdevelop.data.model.SocialDevelopDataLayer;
import java.util.GregorianCalendar;
import socialdevelop.data.model.Task;
import socialdevelop.data.model.Utente;
import socialdevelop.data.model.Invito;
import socialdevelop.data.model.Progetto;

/**
 *
 * @author Nicolò Paoletti
 */
public class InvitoImpl implements Invito{
	
	private int key;
	private String message;
	private GregorianCalendar date;
	private boolean isOffer;
	private boolean status;
	private Utente user;
	private int utente_key;
	private Task task;
	private int task_key;
	private Progetto progetto;
	private int progetto_key;
	protected SocialDevelopDataLayer ownerdatalayer;
	protected boolean dirty;
	
	public InvitoImpl(SocialDevelopDataLayer ownerdatalayer){
		this.ownerdatalayer = ownerdatalayer;
		this.key = 0;
		this.message = "";
		this.date = null;
		this.isOffer = false;
		this.status = false;
		this.utente_key = 0;
		this.user = null;
		this.task = null;
		this.task_key = 0;
	}

	@Override
	public int getKey() {
		return this.key;
	}
	
	protected void setKey(int key){
		this.key = key;
	}
	
	public boolean isDirty(){
		return this.dirty;
	}
	
	@Override
	public Utente getUser() throws DataLayerException{
		if(this.user == null && utente_key > 0)
			this.user = ownerdatalayer.getUser(utente_key);
		return this.user;
	}

	@Override
	public void setUser(Utente user) {
		this.user = user;
		this.utente_key = user.getKey();
		this.dirty = true;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public void setMessage(String message) {
		this.message = message;
		this.dirty = true;
	}

	@Override
	public boolean getStatus() {
		return this.status;
	}

	@Override
	public void setStatus(boolean status) {
		this.status = status;
		this.dirty = true;
	}

	@Override
	public boolean isOffer() {
		return this.isOffer;
	}

	@Override
	public void setOffer(boolean isOffer) {
		this.isOffer = isOffer;
		this.dirty = true;
		
	}

	@Override
	public Task getTask() throws DataLayerException{
		if(this.task == null && this.task_key > 0)
			this.task = ownerdatalayer.getTask(this.task_key);
		return this.task;
	}

	@Override
	public void setTask(Task task) {
		this.task = task;
		this.task_key = task.getKey();
		this.dirty = true;
	}
	
	protected void setTaskKey(int task_key){
		this.task_key = task_key;
		this.task = null;
	}

	@Override
	public GregorianCalendar getDate() {
		return this.date;
	}

	@Override
	public void setDate(GregorianCalendar date) {
		this.date = date;
		this.dirty = true;
	}

	@Override
	public Progetto getProgetto() throws DataLayerException{
		if(this.progetto == null && this.progetto_key > 0)
			this.progetto = ownerdatalayer.getProgetto(this.progetto_key);
		return this.progetto;
	}

	@Override
	public void setProgetto(Progetto progetto) {
		this.progetto = progetto;
		this.progetto_key = progetto.getKey();
		this.dirty = true;
	}
	
	protected void setProgettoKey(int progetto_key){
		this.progetto_key = progetto_key;
		this.progetto = null;
	}

	@Override
	public void copyFrom(Invito invito) throws DataLayerException {
		this.key = invito.getKey();
		this.message = invito.getMessage();
		this.date = invito.getDate();
		this.isOffer = invito.isOffer();
		this.progetto_key = invito.getProgetto().getKey();
		this.utente_key = invito.getUser().getKey();
		this.status = invito.getStatus();
		this.task_key = invito.getTask().getKey();
		this.dirty = true;
	}
	
}
