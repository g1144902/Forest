package forest;


import java.util.ArrayList;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * プロジェクトForestの実行クラス
 */
public class Example extends Object
{
  
  /**
   * ファイルをユーザーに選択してもらいそれを元にモデルを作る
   */
  public static void main(String[] args)
  {
    JFileChooser chooser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Text File", "txt");
    chooser.setFileFilter(filter);
    chooser.showOpenDialog(null);

    ForestModel aModel = new ForestModel(chooser.getSelectedFile());
  }

}
