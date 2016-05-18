package nxt.src;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.Sound;
import lejos.robotics.navigation.DifferentialPilot;
import static nxt.src.Constantes.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.Math;

public class Deplacement {
	
	public Deplacement(){
		pilote=new DifferentialPilot(WHEEL_SIZE, TRACKWIDTH, Motor.A, Motor.C);
		pilote.setTravelSpeed(SPEED);
	}
	
	public void avancer(){
		pilote.forward();
	}
	
	public void avancer(double distance){
		pilote.travel(distance);
	}
	
	public void arreter(){
		pilote.stop();
	}
	
	public void tourner(int cote, boolean sansArret ){
		//pilote.setTravelSpeed(ROTATE_SPEED);
		switch(cote){
			case DROITE :
				if(sansArret){
					int test = MOTEUR_GAUCHE.getTachoCount();
					while(MOTEUR_GAUCHE.getTachoCount()<test+350 && !Button.ESCAPE.isDown()){
						MOTEUR_GAUCHE.forward();
						MOTEUR_DROITE.setSpeed(0);
					}
				}else{
					this.arreter();
					pilote.arc(0,-90);
				}
				//MOTEUR_DROITE.setSpeed(SPEED);
			break;
			case GAUCHE : 
				if(sansArret){
					int test = MOTEUR_DROITE.getTachoCount();
					while(MOTEUR_DROITE.getTachoCount()<test+350 && !Button.ESCAPE.isDown()){
						MOTEUR_DROITE.forward();
						MOTEUR_GAUCHE.setSpeed(0);
					}
				}else{
					this.arreter();
					pilote.arc(0,90);
				}
			break;
		}
		pilote.setTravelSpeed(SPEED);
		this.avancer();
	}
	
	public void demiTour(){
		pilote.setTravelSpeed(ROTATE_SPEED);
		pilote.arc(0, 180);
		pilote.setTravelSpeed(SPEED);
	}
	
	public void patienter(int angle, NXTRegulatedMotor moteur){
		int angle_depart = moteur.getTachoCount();
		while(moteur.getTachoCount()<angle_depart+angle){}
	}
	
	public void redresser(int ligne,DataOutputStream outputData) throws IOException{
		if(COULEUR_GAUCHE.getNormalizedLightValue()<400 && COULEUR_DROITE.getNormalizedLightValue()<400){
			int i = MOTEUR_GAUCHE.getTachoCount();
			while(MOTEUR_GAUCHE.getTachoCount()<i+45){}
		}else if(COULEUR_GAUCHE.getNormalizedLightValue()<450 && COULEUR_DROITE.getNormalizedLightValue()>400){
			
			while(COULEUR_DROITE.getNormalizedLightValue()>450)
				MOTEUR_GAUCHE.setSpeed(0);
			pilote.setTravelSpeed(SPEED);
			this.patienter(22, MOTEUR_DROITE);
			this.avancer();
			this.patienter(45, MOTEUR_DROITE);
		}else if(COULEUR_DROITE.getNormalizedLightValue()<450 && COULEUR_GAUCHE.getNormalizedLightValue()>400){
			
			while(COULEUR_GAUCHE.getNormalizedLightValue()>450)
				MOTEUR_DROITE.setSpeed(0);
			pilote.setTravelSpeed(SPEED);
			this.patienter(29, MOTEUR_GAUCHE);
			this.avancer();
			this.patienter(45, MOTEUR_GAUCHE);
		}
	}


}
