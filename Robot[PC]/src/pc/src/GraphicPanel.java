package pc.src;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author keke
 */
public class GraphicPanel extends javax.swing.JPanel {
	private static final long serialVersionUID = 1L;
	private ArrayList<Segment> vue;
	
    /**
     * Creates new form GraphicPanel
     */
    public GraphicPanel() {
        vue = new ArrayList<Segment>();
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        setBackground(Color.WHITE);
        this.drawCadre(g, 40);
        g.setColor(Color.BLACK);
        int hypothenuse, angle;
        for(int i=0 ; i<vue.size() ; i++){
            hypothenuse = vue.get(i).longueur;
            angle = vue.get(i).angle;
            int xF = vue.get(i).x+(int)(Math.cos(angle)*hypothenuse);
            int yF = vue.get(i).y+(int)(Math.sin(angle)*hypothenuse);
            g.drawLine(vue.get(i).x, vue.get(i).y, xF, yF);
        }
    }
    
    public void addSegment(int x, int y, int longueur, int angle){
        vue.add(new Segment(x, y, longueur, angle));
    }
    
    public void drawCadre(Graphics g, int dimensionCase){
        g.setColor(Color.RED);
        for(int i=0 ; i<24 ; i++){
            g.drawLine(i*dimensionCase, 0, i*dimensionCase,11*dimensionCase);
        }
        for(int i=0 ; i<12 ; i++){
            g.drawLine(0, i*dimensionCase, 23*dimensionCase, i*dimensionCase);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    class Segment{
        public int x, y, longueur, angle;
        
        public Segment(int posX, int posY, int l, int a){
            x = posX;
            y = posY;
            longueur = l;
            angle =a;
        }
        
    }

}
