package src;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JFrame;

import lejos.pc.comm.NXTConnector;
import static src.Constantes.*;

public class RobotRicochet {

    private static Terrain carte;
	private static Fenetre fen;
    private static int niveau = 0;
    private static boolean contourner = false;
    private static Case caseContournerArrivee = null;
    private static Point positionCourante = new Point(depart1);
    private static int directionCourante = BAS;
    private static int LEVEL_MAX = ARENE_HEIGHT/2+ARENE_HEIGHT%2;
    private static HashMap<Integer, Integer> distances = new HashMap<>(3);
    
	private static NXTConnector nxtConnect;
	private static DataOutputStream outputData;
	private static DataInputStream inputData;
	private static boolean connecte = false;
	
	public static void cartographier() throws IOException{
		
        int action=0;

		do{
			byte data=inputData.readByte();
			//System.out.println((int)(data& (0xff)));
			switch(data){
				case DISTANCE_GAUCHE :	distances.put(GAUCHE,(int)(inputData.readByte()& (0xff)));
										break;
				case DISTANCE_DROITE :	distances.put(DROITE,(int)(inputData.readByte()& (0xff)));
										//System.out.println(distances.get(AVANT)+" "+distances.get(DROITE)+" "+distances.get(GAUCHE)+" ");
										action = strategie();
										//System.out.println("scanner "+action);
										outputData.writeInt(action);
										outputData.flush();
										break;
				default: 				distances.put(AVANT,(int)(data& (0xff)) );
										break;	
			}
		}while(action != FIN);
	}

	public static int tourner(int dir){
		int direction = directionCourante;
    	switch(dir){
    	case GAUCHE: if(direction!=DROITE)
    		direction*=2;
			else
				direction=HAUT;
    		break;
    	case DROITE: if(direction!=HAUT)
    		direction/=2;
			else
				direction=DROITE;
    		break;
    	case ARRIERE: if(direction == HAUT || direction == GAUCHE)
    		direction*=4;
			else
				direction/=4;
    		break;
    	}
		return direction;
    }
	
	public static void demiTour(){
		switch(directionCourante){
        	case HAUT : directionCourante=BAS;
            break;
            
        	case BAS : directionCourante=HAUT;
            break;
            
        	case DROITE : directionCourante=GAUCHE;;
            break;
            
        	case GAUCHE : directionCourante=DROITE;;
            break;
		}
	}
    
	private static void ajouterMurs(int direction, int distance){
		int nbMur = distance/40;
		//System.out.println("AjouterMurs " + distance + " /direction " + direction);
		switch(direction){
    	case HAUT: if(positionCourante.x-nbMur<0)
    			nbMur = positionCourante.x;
    		break;
    	case BAS: if(positionCourante.x+nbMur+1>ARENE_HEIGHT)
    			nbMur = ARENE_HEIGHT-positionCourante.x-1;
    		break;
    	case GAUCHE: if(positionCourante.y-nbMur<0)
    			nbMur = positionCourante.y;
    		break;
    	case DROITE: if(positionCourante.y+nbMur+1>ARENE_WIDTH)
    		 	nbMur = ARENE_WIDTH-positionCourante.y-1;
    		break;
		}
		int i=0;
		Case caseCourante = carte.getCase(positionCourante);

		while(nbMur>i && i<4){
			carte.addNoMurs(caseCourante.getPosition(), direction);
			fen.jTable1.setValueAt(caseCourante, caseCourante.getX(), caseCourante.getY());
			caseCourante = carte.avancer(caseCourante, direction);
			i++;
		}

		if(nbMur>4){
			carte.addNoMurs(caseCourante.getPosition(), direction);
		}
		else{
			carte.addMur(caseCourante.getPosition(), direction);
		}
		fen.jTable1.setValueAt(caseCourante, caseCourante.getX(), caseCourante.getY());
			
	}
	
    /**
     * Methode qui ajoute des murs ou noMurs selon la distance mesure.
     * Attention si on sors de l'arene sinon EXCEPTION !!!
     * @param distance
     */
    private static void ajouterMursVue(){
		ajouterMurs(directionCourante, distances.get(AVANT));
		ajouterMurs(tourner(GAUCHE), distances.get(GAUCHE)+15);
		ajouterMurs(tourner(DROITE), distances.get(DROITE)+15);
    }
    
    public static int redressement (int distance, int cote){
    	switch(cote){
    	case DROITE :
    		if(distance%40<10){
    			return REDRESSER_DROITE;
    		}else if(distance%40>20){
    			return REDRESSER_GAUCHE;
    		}else {
    			return AVANT;
    		}
    	case GAUCHE :
    		if(distance%40<10){
    			return REDRESSER_GAUCHE;
    		}else if(distance%40>20){
    			return REDRESSER_DROITE;
    		}else {
    			return AVANT;
    		}
    	default :
    		return AVANT;
    	}
    	
    }
    
    public static int calculerRedressement(double position, boolean inverse, int longeur){
    	if (inverse){
    		if(distances.get(GAUCHE)/40<position && distances.get(GAUCHE)<255){
    			return redressement(distances.get(GAUCHE),GAUCHE);
			}else if(distances.get(DROITE)/40<longeur-position && distances.get(DROITE)<255){
				return redressement(distances.get(DROITE),DROITE);
			}
    	}else{
    		if(distances.get(DROITE)/40<position && distances.get(DROITE)<255){
    			return redressement(distances.get(GAUCHE),DROITE);
			}else if(distances.get(GAUCHE)/40<longeur-position && distances.get(GAUCHE)<255){
				return redressement(distances.get(GAUCHE),GAUCHE);
			}
    	}
    	return AVANT;
    }
    
    public static int calculerRedressementOriente(){

    	switch (directionCourante) {
		case HAUT :
			return(calculerRedressement(positionCourante.getY(), true, ARENE_WIDTH));
		
		case BAS :
			return(calculerRedressement(positionCourante.getY(), false, ARENE_WIDTH));
		
		case DROITE :
			return(calculerRedressement(positionCourante.getX(), true, ARENE_HEIGHT));

		case GAUCHE :
			return(calculerRedressement(positionCourante.getX(), false, ARENE_HEIGHT));
			
		default :
	    	return AVANT;
		}
    }
    
    public static int distanceMax(){
    	switch (directionCourante) {
		case HAUT :
			return (int) positionCourante.getX();
			
		case BAS :
			return ARENE_HEIGHT-(int)positionCourante.getX()-1;
			
		case GAUCHE :
			return (int) positionCourante.getY();
			
		case DROITE :
			return ARENE_WIDTH-(int)positionCourante.getY()-1;

		default:
			return -1;
		}
    }
    

    public static int strategie(){
    	ajouterMursVue();
    	//System.out.println("strategie "+positionCourante.getX()+" "+positionCourante.getY());
    	int action = AVANT;
    	Point coin;
    	switch (directionCourante) {
			case HAUT:
				System.out.println("haut");
				if(contourner){
					if(positionCourante.x == niveau || 
							(positionCourante.y == caseContournerArrivee.getY() && positionCourante.x<caseContournerArrivee.getX())){
						action = finContourner();
					}
					else
						action = contourner();
				}
				else{
					coin = new Point((int)positionCourante.getX()-niveau, (int)positionCourante.getY()+niveau);
					if(coin.equals(depart3))
						action=GAUCHE;
				}				
			break;
			case BAS :
				System.out.println("bas");
				if(contourner){
					if(positionCourante.x == ARENE_HEIGHT-1-niveau || 
							(positionCourante.y == caseContournerArrivee.getY() && positionCourante.x>caseContournerArrivee.getX())){
						action = finContourner();
					}
					else
						action = contourner();
				}
				else{
					coin = new Point((int)positionCourante.getX()+niveau, (int)positionCourante.getY()-niveau);
					if(coin.equals(depart2))
						action=GAUCHE;
				}
			break;
			case GAUCHE :
				if(contourner){
					if(positionCourante.x == niveau || 
							(positionCourante.y == caseContournerArrivee.getY() && positionCourante.x<caseContournerArrivee.getX())){
						action = finContourner();
					}//passage au niveau superieur
					else if(positionCourante.y == niveau+1 && caseContournerArrivee.getDirection() == GAUCHE){
						action = finContourner();
						niveau++;
					}
					else
						action = contourner();
				}
				else{
					coin = new Point(positionCourante.x-niveau, positionCourante.y-niveau-1);
					System.out.println("gauche"+coin.getX()+" "+coin.getY()+" d1.x"+depart1.getX()+" d1.y"+depart1.getY());
					if(depart1.equals(coin)){
						System.out.println("coin");
						action=GAUCHE;
						niveau++;
					}
				}
			break;
			case DROITE :
				if(contourner){
					if(positionCourante.y == ARENE_WIDTH-1-niveau || 
							(positionCourante.x == caseContournerArrivee.getX() && positionCourante.y>caseContournerArrivee.getY())){
						action = finContourner();
					}
					else
						action = contourner();
				}
				else{
					coin = new Point((int)positionCourante.getX()+niveau, (int)positionCourante.getY()+niveau);
					System.out.println("droite"+coin.getX()+" "+coin.getY());
					if(coin.equals(depart4) && niveau<LEVEL_MAX)
						action=GAUCHE;
					else{
						if(coin.y == depart4.y)
							action=FIN;
					}
				}
			break;
		}
    	if(action != FIN && !contourner){
    		//on commence a contourner un mur
    		if(distances.get(AVANT)<35 && distances.get(AVANT)/40 <= distanceMax()){
    			contourner = true;
    			System.out.println("etape 0"+contourner);
    			caseContournerArrivee = carte.avancer(carte.getCase(positionCourante), directionCourante);
    			caseContournerArrivee.setDirection(directionCourante);
    			if(distances.get(GAUCHE) > 40){
    				System.out.println("tourner gauche");
    				action = GAUCHE;
    			}
    			else{
    				action = ARRIERE;
    			}
        		System.out.println(action);
        		//return action;
    		}else if(action == AVANT){
    			//action=calculerRedressementOriente();
    		}
    	}
    	
    	switch(action){
    	case DROITE: 
    	case GAUCHE: directionCourante = tourner(action);
    		break;
    	case ARRIERE: demiTour();
    		break;
    	}
    	avancer();
    	
    	/* fin de strategie */
    	return action;
    }
    
    public static void avancer(){  
    	//System.out.println("avancer");
        switch(directionCourante){
            case HAUT : positionCourante.x--;
                break;
            case BAS : positionCourante.x++;
                break;
            case DROITE : positionCourante.y++;
                break;
            case GAUCHE : positionCourante.y--;
                break;
            default: ;
        }
    }
    
    public static int contourner(){
    	int action = AVANT;
    	
    	//Teste si fin de contournement
		if(positionCourante.equals(caseContournerArrivee.getPosition())){
			System.out.println("fini");
    		contourner=false;
			if(distances.get(GAUCHE) < 40){
				action = ARRIERE;
        		contourner=true;
			}
			else if(tourner(GAUCHE) != caseContournerArrivee.getDirection()){
    			action = ARRIERE;
    		}
    		else{
    			action = GAUCHE;
    		}
    	}
		else{
			if(distances.get(DROITE) > 40){
				System.out.println("tourner droite");
				action = DROITE;
			}
			else if(distances.get(AVANT) > 40){
				action = AVANT;
			}
			else if(distances.get(GAUCHE) > 40){
				action = GAUCHE;
			}
			else{
				action =ARRIERE;
			}    	
		}
    	return action;
    }
    
    private static int finContourner(){
    	int action = AVANT;
    	if(distances.get(DROITE) > 40){
			action = DROITE;
		}
		else if(distances.get(GAUCHE) < 40){
			caseContournerArrivee = carte.avancer(carte.getCase(positionCourante), tourner(GAUCHE));
			caseContournerArrivee.setDirection(tourner(GAUCHE));
			contourner = true;
			action = ARRIERE;
		}else{
			action = GAUCHE;
			contourner = false;
		}
    	
    	return action;
	}
    
	public static void main(String[] args) throws IOException, InterruptedException {
		carte = new Terrain();
		fen = new Fenetre(carte, new JFrame(), false);
    	fen.setVisible(true);
    	//cartographier();
    	fen.setVisible(false);
    	ArrayList<Case> solution = fen.lancerAStar();
    	System.out.println("FIN AFFICHAGE");
		/*
		// TODO Auto-generated method stub
		nxtConnect = new NXTConnector();
		connecte = nxtConnect.connectTo("btspp://001653162E5B");
		outputData = new DataOutputStream(nxtConnect.getOutputStream());
		inputData = new DataInputStream(nxtConnect.getInputStream());
		if(connecte){
			System.out.println("connecte");
			cartographier();
			
			Scanner sc = new Scanner(System.in);
			String arriveeX = sc.nextLine();
			String arriveeY = sc.nextLine();
			//attendre fermeture fenetre et r�cup�rer solution
			ArrayList<Case> solution = algo.algorithme(0, 0);
			
			for(Case caseTmp : solution){
				outputData.write(caseTmp.getPoids());
				outputData.flush();
				outputData.write(caseTmp.getDirection());
				outputData.flush();
			}
			
			//attente du d�part
			sc.nextLine();
			
			outputData.write(FIN);
			outputData.flush();
			
		}else{
			System.out.println("non connecte");
		}*/
	}
}