/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interface.ClientGUI;

/**
 *
 * @author Bernhard
 */
public class VideoPane extends javax.swing.JPanel {

    /**
     * Creates new form VideoPane
     */
    public VideoPane() {
        initComponents();
    }
    
    public void setImage(String image) {
//        lblVideo.setIcon(ImageUtils.resizeConvert(image, lblVideo.getWidth(), 
//                lblVideo.getHeight()));
        lblVideo.setImage(image,false);
    }
    
    public void clear() {
        lblVideo.clear();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblVideo = new Interface.ClientGUI.ImageContainer();

        setPreferredSize(new java.awt.Dimension(909, 399));

        javax.swing.GroupLayout lblVideoLayout = new javax.swing.GroupLayout(lblVideo);
        lblVideo.setLayout(lblVideoLayout);
        lblVideoLayout.setHorizontalGroup(
            lblVideoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 909, Short.MAX_VALUE)
        );
        lblVideoLayout.setVerticalGroup(
            lblVideoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 399, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblVideo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblVideo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Interface.ClientGUI.ImageContainer lblVideo;
    // End of variables declaration//GEN-END:variables
}
