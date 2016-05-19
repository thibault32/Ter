package nxt.src;

import lejos.nxt.Battery;
import lejos.nxt.Button;
import lejos.nxt.Sound;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

import static nxt.src.Constantes.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Cartographier extends Thread {
	
	private static Deplacement deplacement;
	private static BTConnection pcConnect;
	private static DataInputStream inputData;
	private static DataOutputStream outputData;
	
	public Cartographier(){
		MOTEUR_TETE.setSpeed(100*Battery.getVoltage());
		deplacement = new Deplacement();
		this.start();
	}
	
	public void connexion(){
		pcConnect = Bluetooth.waitForConnection();
		inputData = pcConnect.openDataInputStream();
		outputData = pcConnect.openDataOutputStream();
	}
	
	public void depart(){
		//int av=TETE.getDistance();
		MOTEUR_TETE.rotate(90);
		//int g=TETE.getDistance();
		MOTEUR_TETE.rotate(-180);
		//int d=TETE.getDistance();
		MOTEUR_TETE.rotate(90);
		//if(g>d && g>av)
		//	deplacement.tourner(GAUCHE,false);
		//if(d>av && d>g)
		//	deplacement.tourner(DROITE,false);
	}
	
	public void scanner() throws IOException{Sound.beep();
		MOTEUR_TETE.rotate(90);
		outputData.write(DISTANCE_GAUCHE);
		outputData.flush();
		outputData.write((byte)TETE_ARRIERE.getDistance());
		outputData.flush();
		outputData.write(DISTANCE_DROITE);
		outputData.flush();
		outputData.write((byte)TETE_AVANT.getDistance());
		outputData.flush();
		MOTEUR_TETE.rotate(-90);
	}
	
	public void run(){
		boolean obstacle = false;
		System.out.println(COULEUR_DROITE.getNormalizedLightValue());
		connexion();
		
		while(!Button.ESCAPE.isDown() && TETE_AVANT.getDistance()>20 && !obstacle ){
			deplacement.avancer();
			System.out.println(COULEUR_DROITE.getNormalizedLightValue());
			/*if((COULEUR_DROITE.readValue()<ligne+15 && COULEUR_DROITE.readValue()>ligne-15)){
				//deplacement.arreter();
				Sound.beep();
				//obstacle=true;
			}*/
			try {
				if(deplacement.redresser(outputData)){
					try {
						this.scanner();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else{
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		deplacement.arreter();

	}


}
