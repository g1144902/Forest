package forest;


import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import javax.swing.JPopupMenu;


public class ForestController extends mvc.Controller implements ActionListener
{
  
  public ForestController()
  {
    super();
  }
  
  public void mouseClicked(MouseEvent aMouseEvent)
  {
    Point aPoint = aMouseEvent.getPoint();
    if (SwingUtilities.isRightMouseButton(aMouseEvent))
      {
        ForestModel aModel = (ForestModel) model;
        aModel.getPopupMenu().show(aMouseEvent.getComponent(), aPoint.x, aPoint.y);
      }
    else
      {
        aPoint.translate(view.scrollAmount().x, view.scrollAmount().y);
      }
    return;
  }

  public void actionPerformed(ActionEvent anActionEvent)
  {
    ForestModel aModel = (ForestModel) model;
    Thread aThread = new Thread(aModel);
    aThread.start();
    return;
  }

}
