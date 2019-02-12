package transcarpathiancmd;

import java.awt.Color;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 * @authors group CS-31 featuring 
 *  Калабін Олександр, 
 *  Свалявчик Олександр, 
 *  Влад Гутич, 
 *  Роман Данилич, 
 *  Руденко Давид
 */
public class CMD extends javax.swing.JFrame {

  HashMap<String, Color> colorsMap= new HashMap<>();
  String path = System.getProperty("user.dir");
  String[] allLines;
  String lastLine;
  String command;
  
  public CMD() {
    initComponents();
    this.console.setText(path + "> ");
  }

  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jScrollPane1 = new javax.swing.JScrollPane();
    console = new javax.swing.JTextArea();
    closeBtn = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setUndecorated(true);
    setOpacity(0.9F);
    getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    console.setBackground(new java.awt.Color(10, 40, 50));
    console.setColumns(20);
    console.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
    console.setForeground(new java.awt.Color(255, 255, 255));
    console.setRows(5);
    console.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        onKeyReleased(evt);
      }
    });
    jScrollPane1.setViewportView(console);

    getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 36, 1200, 564));

    closeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/close-btn-image.png"))); // NOI18N
    closeBtn.setBorder(null);
    closeBtn.setBorderPainted(false);
    closeBtn.setContentAreaFilled(false);
    closeBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    closeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        onFormClose(evt);
      }
    });
    getContentPane().add(closeBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 6, -1, -1));

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void onFormClose(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onFormClose
    System.exit(0);
  }//GEN-LAST:event_onFormClose

  private void onKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_onKeyReleased
    Pattern linePattern = Pattern.compile("^([A-Z]:[\\\\/].*)> (.*)$");
    Pattern helpPattern = Pattern.compile("^пуйти (.*)$", Pattern.CASE_INSENSITIVE);
    Pattern dirPattern = Pattern.compile("^вкажи шо маєш$", Pattern.CASE_INSENSITIVE);
    if (evt.getKeyCode() == 10) {
      allLines = this.console.getText().split("\n");
      lastLine = allLines[allLines.length - 1];
      Matcher lineMatcher = linePattern.matcher(lastLine); lineMatcher.find();
      path = lineMatcher.group(1);
      command = lineMatcher.group(2);
      if (helpPattern.matcher(command).matches()) {
        Matcher helpMatcher = helpPattern.matcher(command); helpMatcher.find();
        String newPath = helpMatcher.group(1);
        path = this.cd(path, newPath);
      } else {
        this.console.append("Ти позерай шо пишеш, бо я не розуміву!\n");
      }
      this.console.append(path + "> ");
    }
  }//GEN-LAST:event_onKeyReleased

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
      java.util.logging.Logger.getLogger(CMD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(CMD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(CMD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(CMD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        CMD frame = new CMD();
        frame.setSize(1200, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
      }
    });
  }

  public String cd(String prevPath, String newPath){
    if(!newPath.contains("..")){
      File file = new File(newPath);
      if(file.exists() && file.isAbsolute() && file.isDirectory()){
        return file.getAbsolutePath();
      } else {
        file = new File(prevPath + "\\\\" + newPath);
        if(file.exists() && file.isDirectory()) {
            return file.getAbsolutePath();
        }
        return "Не понявім... Щи раз пробуй";
      }
    } else {
      String[] arrOfDots = newPath.split("\\\\|/");
      String[] arrOfDirs = prevPath.split("(\\\\|/)");
      return arrOfDirs.length > 1 ?
        Arrays.stream(arrOfDirs).limit(arrOfDirs.length - arrOfDots.length).collect(Collectors.joining("\\")) + "\\"
        : prevPath;
    }
  }
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton closeBtn;
  private javax.swing.JTextArea console;
  private javax.swing.JScrollPane jScrollPane1;
  // End of variables declaration//GEN-END:variables
}
