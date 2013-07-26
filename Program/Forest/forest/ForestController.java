package forest;


import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.MenuElement;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;


/**
 * MVCにおけるコントローラー
 */
public class ForestController extends mvc.Controller implements ActionListener
{
  
  /**
   * スーパークラスのコンストラクタを呼び出すだけ
   */
  public ForestController()
  {
    super();
  }
  
  /**
   * クリックを判別し右クリックならポップアップメニューを表示
   */
  public void mouseClicked(MouseEvent aMouseEvent)
  {
    Point aPoint = aMouseEvent.getPoint();
    if (SwingUtilities.isRightMouseButton(aMouseEvent))
      {
        ForestModel aModel = (ForestModel) model;
        aModel.getPopupMenu().show(aMouseEvent.getComponent(), aPoint.x, aPoint.y);
      }
    return;
  }

  /**
   * メニューが押されたとき新たなスレッドを作りアニメーションを実行させる
   */
  public void actionPerformed(ActionEvent anActionEvent)
  {
    ForestModel aModel = (ForestModel) model;
    Thread aThread = new Thread(aModel);
    aThread.start();
    
    MenuElement[] elements = aModel.getPopupMenu().getSubElements();
    JMenuItem anItem = (JMenuItem) elements[0];
    anItem.setEnabled(false);
    return;
  }

}
