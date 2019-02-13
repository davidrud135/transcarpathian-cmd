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
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @authors group CS-31:
 *  Калабін Олександр, 
 *  Свалявчик Олександр, 
 *  Влад Гутич, 
 *  Роман Данилич, 
 *  Руденко Давид
 */
public class CMD extends javax.swing.JFrame {

  int ignoreUKRCase = Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE;
  Pattern linePattern = Pattern.compile("^([A-Z]:[\\\\/].*)> (.*)$");
  Pattern mkdirPattern = Pattern.compile("^создай мі папку (.+)$", ignoreUKRCase);
  Pattern cdPattern = Pattern.compile("^пуйти (.+)$", ignoreUKRCase);
  Pattern rmPattern = Pattern.compile("^вушмарь (.*)$", ignoreUKRCase);
  Pattern colorPattern = Pattern.compile("^цвіт (.+)$", ignoreUKRCase);
  Pattern dirPattern = Pattern.compile("^вкажи шо маєш$", ignoreUKRCase);
  Pattern clsPattern = Pattern.compile("^повтерай всьо$", ignoreUKRCase);
  Pattern datePattern = Pattern.compile("^вкажи нишню дату$", ignoreUKRCase);
  Pattern helpPattern = Pattern.compile("^поможи мі$", ignoreUKRCase);
  Pattern exitPattern = Pattern.compile("^вуйти гет$", ignoreUKRCase);
  HashMap<String, Color> colorsMap= new HashMap<>();
  String path = System.getProperty("user.dir");
  String[] allLines;
  String lastLine;
  String command;
  
  public CMD() {
    initComponents();
    this.colorsMap.put("червений", Color.red);
    this.colorsMap.put("помаранчевий", Color.orange);
    this.colorsMap.put("зелений", Color.green);
    this.colorsMap.put("як ся було", Color.white);
    this.showPath();
  }

  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jScrollPane1 = new javax.swing.JScrollPane();
    console = new javax.swing.JTextArea();
    closeBtn = new javax.swing.JButton();
    hideBtn = new javax.swing.JButton();

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

    closeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/close-btn-image.png"))); // NOI18N
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

    hideBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hide-btn-image.png"))); // NOI18N
    hideBtn.setBorder(null);
    hideBtn.setBorderPainted(false);
    hideBtn.setContentAreaFilled(false);
    hideBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    hideBtn.setMaximumSize(new java.awt.Dimension(24, 24));
    hideBtn.setMinimumSize(new java.awt.Dimension(24, 24));
    hideBtn.setPreferredSize(new java.awt.Dimension(24, 24));
    hideBtn.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        onFormHide(evt);
      }
    });
    getContentPane().add(hideBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(1135, 6, -1, -1));

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void onKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_onKeyReleased
    if (evt.getKeyCode() == 10) {
      try {
        allLines = this.console.getText().split("\n");
        lastLine = allLines[allLines.length - 1];
        Matcher lineMatcher = linePattern.matcher(lastLine); lineMatcher.find();
        path = lineMatcher.group(1);
        command = lineMatcher.group(2);
        if (cdPattern.matcher(command).matches()) {
          Matcher cdMatcher = cdPattern.matcher(command); cdMatcher.find();
          String newPath = cdMatcher.group(1);
          path = this.cd(path, newPath);
        } else if (dirPattern.matcher(command).matches()) {
          this.console.append(this.dir(path));
        } else if(mkdirPattern.matcher(command).matches()) {
          Matcher mkdirMatcher = mkdirPattern.matcher(command); mkdirMatcher.find();
          String pathToDir = mkdirMatcher.group(1);
          this.mkdir(path, pathToDir);
        } else if (colorPattern.matcher(command).matches()) {
          Matcher colorMatcher = colorPattern.matcher(command); colorMatcher.find();
          String color = colorMatcher.group(1).toLowerCase();
          this.console.setForeground(this.colorsMap.getOrDefault(color, this.console.getForeground()));
        } else if (clsPattern.matcher(command).matches()) {
          this.cls();
        } else if (datePattern.matcher(command).matches()) {
          this.console.append(this.date());
        } else if(rmPattern.matcher(command).matches()) {
          Matcher rmMatcher = rmPattern.matcher(command); rmMatcher.find();
          String pathToFile = rmMatcher.group(1);
          this.rm(path, pathToFile);
        } else if (helpPattern.matcher(command).matches()) {
          this.console.append(this.help());
        } else if (exitPattern.matcher(command).matches()) {
          System.exit(0);
        } else {
          this.console.append("Ти позерай шо пишеш, бо я тебе не розуміву..\n");
        }
        this.showPath();
      } catch (Exception e) {
        System.out.println(e.getMessage());
        this.cls();
        this.showPath();
      }
    }
  }//GEN-LAST:event_onKeyReleased

  private void onFormClose(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onFormClose
    System.exit(0);
  }//GEN-LAST:event_onFormClose

  private void onFormHide(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onFormHide
    setExtendedState(getExtendedState() | JFrame.ICONIFIED);
  }//GEN-LAST:event_onFormHide

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
        frame.setIconImage(new ImageIcon("src/images/terminal.png").getImage());
        frame.setVisible(true);
      }
    });
  }

  public String date() {
    Calendar calendar = Calendar.getInstance();
    Date currentDate = calendar.getTime();
    SimpleDateFormat formatDate = new SimpleDateFormat("yyyy.MM.dd HH:mm");
    return formatDate.format(currentDate) + "\n";
  }
  
  public String help() {
    return String.join("\n", new String[] {
      "Ту є то, шо мож робити:",
      "> создай мі папку [путь] - створює папку за заданим шляхом",
      "> пуйти [путь] - змінює директорію за заданим шляхом",
      "> вушмарь [путь] - видаляє файл або папку за заданим шляхом",
      "> цвіт [червений|помаранчевий|зелений|як ся було] - змінює колір шрифту",
      "> вкажи шо маєш - виводить вміст теперішньої папки",
      "> повтерай всьо - очищує екран",
      "> вкажи нишню дату - виводить теперішню дату",
      "> поможи мі - виводить список доступних команд",
      "> вуйти гет - закриває вікно"
    }) + "\n";
  }
  
  public void rm(String currentPath, String pathToFile) {
    File file = new File(pathToFile);
    if(file.isAbsolute()){
      file.delete();
    } else {
      file = new File(currentPath + "\\" + pathToFile);
      file.delete();
    }
  }
  
  public void showPath() {
    this.console.append(path + "> ");
  }
  
  public void cls() {
    this.console.setText("");
  }
  
  public void mkdir(String currentPath, String pathToDir){
    File file = new File(pathToDir);
    if (file.isAbsolute()) {
      file.mkdir();
    } else {
      file = new File(currentPath + "\\" + pathToDir);
      file.mkdir();
    }
  }
  
  public String dir(String path) {
    File folder = new File(path);
    return String.join("\n", folder.list()) + "\n";
  }
  
  public String cd(String prevPath, String newPath){
    if (!newPath.contains("..")) {
      File file = new File(newPath);
      if(file.exists() && file.isAbsolute() && file.isDirectory()){
        return file.getAbsolutePath();
      } else {
        file = new File(prevPath + "\\\\" + newPath);
        if (file.exists() && file.isDirectory()) {
          return file.getAbsolutePath();
        }
        return prevPath;
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
  private javax.swing.JButton hideBtn;
  private javax.swing.JScrollPane jScrollPane1;
  // End of variables declaration//GEN-END:variables
}
