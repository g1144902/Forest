package forest;


import java.util.ArrayList;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Example extends Object
{
  
  public static void main(String[] args)
  {
    JFileChooser chooser = new JFileChooser();
    FileNameExtensionFilter filter = 
      new FileNameExtensionFilter("Text File", "txt");
    chooser.setFileFilter(filter);
    int returnVal = chooser.showOpenDialog(null);
    if(returnVal == JFileChooser.APPROVE_OPTION) {
      System.out.println("You chose to open this file: " +
                         chooser.getSelectedFile().getName());
    }

    ForestModel aModel = new ForestModel(chooser.getSelectedFile());
  }

}
