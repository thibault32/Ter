package pc.src;

import static pc.src.Constantes.depart1;
import static pc.src.Constantes.depart2;
import static pc.src.Constantes.depart3;
import static pc.src.Constantes.depart4;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

public class AStar {
	private Terrain carte;
    
	private Fenetre fen;
    private TreeMap<Case, Case> listeOuverte = new TreeMap<Case, Case>();
    private HashMap<Case, Case> listeFermee = new HashMap<Case, Case>();
    private Case caseCourante;
    private Case caseParent;
	
    public AStar(Terrain arene, Point arrivee) throws InterruptedException{
    	
    	carte = arene;
    	carte.getCase(depart1).setDepart();
        carte.getCase(depart2).setDepart();
        carte.getCase(depart3).setDepart();
        carte.getCase(depart4).setDepart();
        
        
        caseCourante = carte.getCase(arrivee);
        caseCourante.setPoids(0, 1);
        caseParent = caseCourante;

    	fen = new Fenetre(arene);
    	fen.jTable1.setDefaultRenderer(Object.class, new TableRendererAStar(carte));
    	fen.setVisible(true);
        algorithme(0, 0);
    }
    
    public void algorithme(int direction, int profondeur){
        /*if(caseCourante.getPoids() == 10){
            System.out.println("ATTENTION !!!");
        }
        if(caseCourante.getX()==10 && caseCourante.getY()==19){
            System.out.println("ATTENTION !!!");
        }*/
    	
        ArrayList<Integer> directions = carte.getDirections(caseCourante, direction);
        Case caseAvancer = null;
        int i=0;
        //on regarde les possibilit�s (ajout des cases adjacentes accessibles)
        while(i<directions.size()){
            Case caseTmp = carte.avancer(caseCourante.getX(), caseCourante.getY(), directions.get(i));
                        
            //mise ajour de la vue
            carte.getCase(caseTmp.getPosition()).setPoids(caseCourante.getPoids()+1, directions.get(i));
            carte.getCase(caseTmp.getPosition()).setVisite(true);
            try {
    			Thread.sleep(35);
    		} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            fen.jTable1.setValueAt(caseTmp, caseTmp.getX(), caseTmp.getY());
            caseTmp.setPoids(caseCourante.getPoids()+1, directions.get(i));
                    
            Case caseFermee = getListeFermee(caseTmp);
            
            Case caseOuverte = getListeOuverte(caseTmp);
            
            if(estDepart(caseTmp)){
                //System.out.println("SOLUTION TROUVE !!!!!!!!");
                ajouter_listeFermee(caseTmp, caseCourante);
                return;
            }
            //MAJ de la case adjacente temporaire
            else if(caseFermee != null){
                //System.out.println("PRESENT DANS LISTE FERMEE");
                if(caseTmp.getPoids()<caseFermee.getPoids()){
                    supprimer_listeFermee(caseFermee);
                    ajouter_listeOuverte(caseTmp, caseCourante);
                }//boucle infinie
                else{
                    listeFermee.remove(caseTmp);
                }
            } //Sinon on regarde
            else if(caseOuverte != null){ 
                //System.out.println(" PRESENT(S) DANS LISTE OUVERTE");
                //on regarde si on peut éliminer des choix
                if(caseTmp.memeTrajectoire(caseOuverte.getDirection())){
                    if(caseTmp.getPoids()<caseOuverte.getPoids()){
                        remplacer_listeOuverte(caseOuverte, caseTmp, caseCourante);
                    }
                    else if(caseTmp.memeTrajectoire(caseOuverte.getDirection())){
                        supprimer_listeOuverte(caseTmp);
                    }
                }
                //si on avance
                if(caseTmp.memeTrajectoire(direction)){
                    //System.out.println("AV ET AVANCER");
                    caseAvancer = caseTmp;
                }
            }
            else{
                if(directions.get(i) == direction || direction == 0){
                    caseAvancer = caseTmp;
                }
                else if(!caseTmp.directionsPossible(caseTmp.getDirection()).isEmpty()){
                    ajouter_listeOuverte(caseTmp, caseCourante);
                }
                else{
                    directions.remove(i);
                }
            }
            i++;
        }
        //Fin recherche cases adjacentes
        ajouter_listeFermee(caseCourante, new Case(caseParent));
    
        supprimer_listeOuverte(caseCourante);
        
        //condition d'arret
        if(!listeOuverte.isEmpty() && caseAvancer == null && caseCourante.getPoids()<100){
            //System.out.println("\nMeilleurChoix");
            supprimer_listeOuverte(caseCourante);
            
            caseCourante = listeOuverte.firstKey();
            caseParent = new Case(caseCourante);
            algorithme(caseCourante.getDirection(), profondeur+1);
        }
        else if(caseAvancer != null){
            caseCourante = caseAvancer;
            algorithme(caseAvancer.getDirection(), profondeur+1);
        }
        else{
            System.out.println("DOMMAGE pas de solution !!!!!");
        }
    }

    private boolean estDepart(Case caseCourante){
        Point pointTmp = new Point(caseCourante.getX(), caseCourante.getY());
        return pointTmp.equals(depart1) || 
                pointTmp.equals(depart2) || 
                pointTmp.equals(depart3);
    }
    
    private void heuristique(){
        
    }
    
    public ArrayList<Case> getSolution(){
    	ArrayList<Case> solution = new ArrayList<Case>();
    	
    	/* parcourir liste fermee et retenir chaque case avec 
    	 * comme poids le nombre de case � avanceret comme direction
    	 * la futur direction � prendre (droite ou gauche)
    	 */
    	
    	return solution;
    }
    
    
    /* M�thodes utiles pour les listes ouverte et ferm�e */
    
    private void ajouter_listeOuverte(Case caseCourante, Case caseParent){
        Case caseTmp = new Case(caseCourante);
        caseTmp.setVisite(false);
        listeOuverte.put(caseTmp, caseParent);
    }
    
    private void supprimer_listeOuverte(Case caseCourante){
        Case caseTmp = new Case(caseCourante);
        caseTmp.setVisite(true);
        listeOuverte.remove(caseTmp);
    }
    
    private void remplacer_listeOuverte(Case oldCase, Case newCase, Case newParent){
        Case caseTmp = new Case(caseCourante);
        caseTmp.setVisite(true);
        listeOuverte.put(newCase, newCase);
    }
    
    private void ajouter_listeFermee(Case caseCourante, Case caseParent){
        Case caseTmp = new Case(caseCourante);
        caseTmp.setVisite(true);
        listeFermee.put(caseTmp, caseParent);
        listeOuverte.remove(caseTmp, caseParent);
    }
    
    private void supprimer_listeFermee(Case cle){
        Case caseTmp = new Case(caseCourante);
        caseTmp.setVisite(true);
        listeFermee.remove(cle);
    }
    
    private Case getListeOuverte(Case c){
        if(listeOuverte.isEmpty())
            return null;
        Iterator<Case> it = listeOuverte.keySet().iterator();
        Case key = it.next();
        while(key.getPoids()<c.getPoids() && it.hasNext()){
            if(c.equals(key) && c.memeTrajectoire(key.getDirection())){
                break;
            }
            key = it.next();
        }
        return (key.equals(c) && c.memeTrajectoire(key.getDirection())) ? key : null;
    }
    
    private Case getListeFermee(Case c){
        if(listeFermee.isEmpty())
            return null;
        Iterator<Case> it = listeFermee.keySet().iterator();
        Case key = it.next();
        while(!(c.equals(key) && c.memeTrajectoire(key.getDirection())) && it.hasNext()){
            key = it.next();
        }
        return (c.equals(key)&& c.memeTrajectoire(key.getDirection())) ? key : null;
    }
}
