package forest;


import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;


public class ForestController extends mvc.Controller
{
  
  public ForestController()
  {
    super();
  }
  
  public void mouseClicked(MouseEvent aMouseEvent)
  {
    Point aPoint = aMouseEvent.getPoint();
    aPoint.translate(view.scrollAmount().x, view.scrollAmount().y);
    System.out.println("test");
    return;
  }
}
