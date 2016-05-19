package pc.src;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.TableColumn;
import static pc.src.Constantes.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author keke
 */
@SuppressWarnings("serial")
public class Fenetre extends javax.swing.JFrame {
	private Terrain carte;
    
    private TreeMap<Case, Case> listeOuverte = new TreeMap<Case, Case>();
    private HashMap<Case, Case> listeFermee = new HashMap<Case, Case>();
    private Case caseCourante;
    private Case caseParent;
    
    /**
     * Creates new form Fenetre
     */
    public Fenetre() throws InterruptedException {
        carte = new Terrain(10,0);
        initComponents();
        jTable1.setRowHeight((jPanel1.getHeight()-jTable1.getTableHeader().getHeight())/ARENE_HEIGHT-5);
        TableColumn col;
        for(int i=0 ; i<ARENE_WIDTH+1 ; i++){
            col = jTable1.getColumnModel().getColumn(i);
            col.setPreferredWidth(jPanel1.getWidth()/(ARENE_WIDTH+1));
        }
        carte.getCase(depart1.x,depart1.y).setDepart();
        carte.getCase(depart2.x,depart2.y).setDepart();
        carte.getCase(depart3.x,depart3.y).setDepart();
        carte.getCase(depart4.x,depart4.y).setDepart();
        this.setVisible(true);
        
        caseCourante = carte.getCase(9, 22);
        caseCourante.setPoids(0, 1);
        
        caseParent = caseCourante;
        
        long timer = System.currentTimeMillis();
        algorithme(0, 0);
        jLabel3.setText(Long.toString(System.currentTimeMillis()-timer) + " ms");
        System.out.println("temps de traitement : " + jLabel3.getText());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Vue Terrain");

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel4.setMaximumSize(new java.awt.Dimension(2147483647, 119));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jToggleButton1.setText("Start");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });
        jPanel4.add(jToggleButton1, new java.awt.GridBagConstraints());

        jButton3.setText("Stop");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton3, new java.awt.GridBagConstraints());

        jButton1.setText("Lancer A*");
        jPanel4.add(jButton1, new java.awt.GridBagConstraints());

        jButton2.setText("G�n�rer labyrinthe");
        jPanel4.add(jButton2, new java.awt.GridBagConstraints());

        jPanel2.add(jPanel4);
        jPanel2.setSize(jPanel1.getWidth()-300, jPanel1.getHeight());

        jTable1.setModel(new TableModel(carte));
        jTable1.setEnabled(false);
        jTable1.setFocusable(false);
        jTable1.setRowSelectionAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        jTable1.setDefaultRenderer(Object.class, new TableRenderer(carte));
        jTable1.setSize(jPanel5.getWidth(), jPanel5.getHeight());

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel5);

        jPanel1.add(jPanel2);

        jPanel3.setEnabled(false);

        jLabel4.setText("nombre de coups :");

        jLabel3.setText("jLabel3");

        jLabel2.setText("temps de r�solution : ");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("R�sultats");

        jLabel5.setText("jLabel5");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addContainerGap(430, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 717, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    protected void jToggleButton1ActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		
	}

	protected void jButton3ActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		
	}

	/**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Fenetre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Fenetre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Fenetre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Fenetre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Fenetre().setVisible(true);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToggleButton jToggleButton1;
    // End of variables declaration//GEN-END:variables

    /* M�thodes pour la cartographie */
    public Terrain getCarte(){
    	return carte;
    }
    
    public void maj(){
    	jTable1.setModel(new TableModel(carte));
    }

    /* M�thodes pour l'algorithme A* */
    
    public void algorithme(int direction, int profondeur) throws InterruptedException{
        System.out.println("\n******************\nCASE COURANTE : " + caseCourante+ "\n******************");
        if(caseCourante.getPoids() == 10){
            System.out.println("ATTENTION !!!");
        }
        if(caseCourante.getX()==10 && caseCourante.getY()==19){
            System.out.println("ATTENTION !!!");
        }
        ArrayList<Integer> directions = carte.getDirections(caseCourante, direction);
        Case caseAvancer = null;
        int i=0;
        //on regarde les possibilit�s (ajout des cases adjacentes accessibles)
        while(i<directions.size()){
            Case caseTmp = carte.avancer(caseCourante.getX(), caseCourante.getY(), directions.get(i));
            
            if(caseTmp.getX()==10 && caseTmp.getY()==19){
                System.out.println("ATTENTION !!!");
            }
            
            //mise ajour de la vue
            carte.getCase(caseTmp.getX(), caseTmp.getY())
                    .setPoids(caseCourante.getPoids()+1, directions.get(i));
            carte.getCase(caseTmp.getX(), caseTmp.getY()).setVisite(true);
            jTable1.setValueAt(caseTmp, caseTmp.getX(), caseTmp.getY());
            
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
        if(!listeOuverte.isEmpty() && caseAvancer == null && caseCourante.getPoids()<60){
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
    /**
     * 
     * @param c
     * @return 
     */
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
