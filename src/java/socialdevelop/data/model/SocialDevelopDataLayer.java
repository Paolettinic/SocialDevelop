/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socialdevelop.data.model;
import it.univaq.f4i.iw.framework.data.DataLayer;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import socialdevelop.data.impl.SkillImpl;
import socialdevelop.data.impl.TaskImpl;
import socialdevelop.data.impl.UtenteImpl;
import socialdevelop.data.impl.DiscussioneImpl;

/**
 *
 * @author Nicolò Paoletti
 */
public interface SocialDevelopDataLayer extends DataLayer{
	
	Invito createInvitation();
	
	Tipo createType();
	
	FileSD createFile();
	
	Invito getInvitation(int inviteID) throws DataLayerException;
	
	Utente getUser(int userID) throws DataLayerException;
	
	Tipo getTipo(int tipo_key) throws DataLayerException;
	
	Task getTask(int taskID) throws DataLayerException;
	
	Skill getSkill(int skillID) throws DataLayerException;
	
	Progetto getProgetto(int progetto_key) throws DataLayerException;
	
	List<Skill> getSkillsByType(Tipo tipo) throws DataLayerException;

	List<Tipo> getTipiBySkill(Skill skill);

	Curriculum getCurriculum(int curriculum_key);

	Immagine getImmagine(int immagine_key);

	Map<Skill, Integer> getSkillsByUtente(Utente utente);

	Map<Task, Integer> getTasksByUtente(Utente utente);
	
	List<Progetto> getProgettiByUtente(Utente utente);
	
	Map<Skill, Integer> getSkillsByTask(Task task);	

	Map<Utente, Integer> getUtentiByTask(Task task);
        
        Discussione getDiscussione(int discussione_key);
        
        List<Task> getTasksByProgetto(Progetto progetto);
	
        Map<Discussione, Integer> getDiscussioneByProgetto(Progetto progetto);
        
        List<Messaggio> getMessaggioByDiscussione(Discussione discussione);

}
