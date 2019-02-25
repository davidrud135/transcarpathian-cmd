package transcarpathiancmd;

import java.awt.Color;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
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
	Pattern copyPattern = Pattern.compile("^ши яден файл (.+) (.+)$", ignoreUKRCase);
  Pattern mkdirPattern = Pattern.compile("^создай мі папку (.+)$", ignoreUKRCase);
  Pattern cdPattern = Pattern.compile("^пуйти (.+)$", ignoreUKRCase);
  Pattern rmPattern = Pattern.compile("^вушмарь (.*)$", ignoreUKRCase);
  Pattern colorPattern = Pattern.compile("^цвіт (.+)$", ignoreUKRCase);
  Pattern typePattern = Pattern.compile("^дай попозерати (.+)$", ignoreUKRCase);
  Pattern dirPattern = Pattern.compile("^вкажи шо маєш$", ignoreUKRCase);
  Pattern clsPattern = Pattern.compile("^повтерай всьо$", ignoreUKRCase);
  Pattern datePattern = Pattern.compile("^вкажи нишню дату$", ignoreUKRCase);
  Pattern helpPattern = Pattern.compile("^поможи ми$", ignoreUKRCase);
  Pattern exitPattern = Pattern.compile("^вуйти гет$", ignoreUKRCase);
	
  HashMap<String, Color> colorsMap= new HashMap<>();
  String path = System.getProperty("user.dir");
  String[] allLines;
  String lastLine;
  String command;
  int counterOfCommands = 0;
  ArrayList<String> arrOfCmds = new ArrayList<>();
  
  public CMD() {
    initComponents();
    this.colorsMap.put("червений", Color.red);
    this.colorsMap.put("блакитний", Color.cyan);
    this.colorsMap.put("зелений", Color.green);
    this.colorsMap.put("як ся було", Color.white);
    this.showPath();
    this.console.requestFocus();
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
    console.setFont(new java.awt.Font("Century Gothic", 3, 20)); // NOI18N
    console.setForeground(new java.awt.Color(255, 255, 255));
    console.setRows(5);
    console.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
    console.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(java.awt.event.KeyEvent evt) {
        onKeyPressed(evt);
      }
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
        String clearLastLine = lastLine.replaceAll(" +", " ").trim();
        Matcher lineMatcher = linePattern.matcher(clearLastLine); lineMatcher.find();
        path = lineMatcher.group(1);
        command = lineMatcher.group(2);
				if(copyPattern.matcher(command).matches()){
					Matcher copyMatcher = copyPattern.matcher(command); copyMatcher.find();
					String pathToFile = copyMatcher.group(1);
					String newPath = copyMatcher.group(2);
					this.copy(path, pathToFile, newPath);
				} else if (cdPattern.matcher(command).matches()) {
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
        } else if(typePattern.matcher(command).matches()){
					Matcher typeMatcher = typePattern.matcher(command); typeMatcher.find();
					String pathToFile = typeMatcher.group(1);
					this.console.append(this.type(path, pathToFile));
				}	else if (clsPattern.matcher(command).matches()) {
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
          this.console.append("Ти позерай шо пишеш, бо я тебе не розуміву...\n");
        }
				arrOfCmds.add(command);
				counterOfCommands++;
        this.showPath();
      } catch (Exception e) {
        System.out.println(e.getMessage());
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

  private void onKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_onKeyPressed
    boolean isArrowUp = evt.getKeyCode() == 38;
    boolean isArrowDown = evt.getKeyCode() == 40;
		allLines = this.console.getText().split("\n");
    int allLinesAmount = allLines.length;
		lastLine = allLines[allLinesAmount - 1];
    
    boolean isArrowLeft = evt.getKeyCode() == 37;
    boolean isBackspace = evt.getKeyCode() == 8;
    
    if (isArrowLeft || isBackspace) {
      int cursorPosition = console.getCaretPosition();
      if (allLinesAmount > 1) {
        for (int i = 0; i < allLinesAmount - 1; i++) {
          String line = allLines[i];
          cursorPosition -= line.length();
        }
      }
      System.out.println(cursorPosition);
      if (cursorPosition <= path.length() + allLinesAmount + 1) {
        evt.consume();
      }
    }
    
		if(isArrowUp) {
			evt.consume();
			if(allLines.length > 1 && counterOfCommands > 0) {
        counterOfCommands--;
        console.setText(Arrays.stream(allLines).limit(allLines.length - 1).collect(Collectors.joining("\n")) + "\n");
        console.append(path + "> " + arrOfCmds.get(counterOfCommands));
      }
		} else if(isArrowDown) {
			evt.consume();
			if(counterOfCommands < arrOfCmds.size() - 1) {
				counterOfCommands++;
        console.setText(Arrays.stream(allLines).limit(allLines.length - 1).collect(Collectors.joining("\n")) + "\n");
        console.append(path + "> " + arrOfCmds.get(counterOfCommands));
      }
		}
  }//GEN-LAST:event_onKeyPressed

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
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/terminal.png")));
        frame.setVisible(true);
      }
    });
  }
	
	public String type(String currentPath, String pathToFile){
		ArrayList<String> arrOfLines = new ArrayList<>();
		BufferedReader inputReader;
		try {
			if (new File(pathToFile).isAbsolute()) {
				inputReader = new BufferedReader(new InputStreamReader(new FileInputStream(pathToFile), "Cp1251"));
				while (inputReader.ready()) {
					arrOfLines.add(inputReader.readLine());
				}
			} else {
				inputReader = new BufferedReader(new InputStreamReader(new FileInputStream(currentPath + "\\" + pathToFile), "Cp1251"));
				while (inputReader.ready()) {
					arrOfLines.add(inputReader.readLine());
				}
			}
			inputReader.close();
		} catch (IOException iOException) {
			iOException.printStackTrace();
			return "";
		}
		return (arrOfLines.isEmpty()) 
      ? "Ниє шо позирити\n"
      : String.join("\n", arrOfLines) + "\n";
	}
	
	public void copy(String currentPath, String pathToFile, String newPath) {
		File newFile;
		File file;
		
		try {
			file = new File(pathToFile);
			if (file.isAbsolute()) {
				newFile = new File(newPath);
				if (newFile.isAbsolute()) {
					Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
				} else {
					newFile = new File(currentPath + "\\" + newPath);
					Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
				}
			} else {
				file = new File(currentPath + "\\" + pathToFile);
				newFile = new File(newPath);
				if (newFile.isAbsolute()) {
					Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
				} else {
					newFile = new File(currentPath + "\\" + newPath);
					Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
				}
			}
		} catch (IOException iOException) {
			iOException.printStackTrace();
		}
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
			"> ши яден файл [старий путь] [новий путь] - знаходить файл за старим шляхом і копіює за новим",
      "> создай мі папку [путь] - створює папку за заданим шляхом",
      "> пуйти [путь] - змінює директорію за заданим шляхом",
      "> вушмарь [путь] - видаляє файл або папку за заданим шляхом",
      "> цвіт [червений|блакитний|зелений|як ся було] - змінює колір шрифту",
			"> дай попозерати [путь] - відображає вміст файлу за заданим шляхом",
      "> вкажи шо маєш - виводить вміст теперішньої папки",
      "> повтерай всьо - очищує екран",
      "> вкажи нишню дату - виводить теперішню дату",
      "> поможи ми - виводить список доступних команд",
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
    this.console.append(String.format("%s> ", path));
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
    String[] folderItems = folder.list();
    int folderItemsAmount = folderItems.length;
    return (folderItemsAmount == 0) 
      ? "Нич туй не маю\n"
      : String.join("\n", folderItems) + "\n";
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
