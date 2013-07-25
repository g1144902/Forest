package forest;


import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.event.MouseInputAdapter;


/**
 * ノードのコントローラー
 */
public class ForestNodeController extends MouseInputAdapter
{

  /**
   * 自身の相手となるラベル
   */
  private JLabel label;

  /**
   * このクラスのコンストラクタ
   */
  public ForestNodeController()
  {
    super();
    label = null;
  }

  /**
   * マウスのクリックに反応し自身の相手となるラベルの名前を出力
   */
  public void mouseClicked(MouseEvent aMouseEvent)
  {
    System.out.println(label.getText());
    return;
  }

  /**
   * ラベルをセットする
   */
  public void setLabel(JLabel aLabel)
  {
    label = aLabel;
    label.addMouseListener(this);
    label.addMouseMotionListener(this);
    return;
  }

}
