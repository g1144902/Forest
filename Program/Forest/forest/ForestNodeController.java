package forest;


import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.event.MouseInputAdapter;


public class ForestNodeController extends MouseInputAdapter
{

  private JLabel label;

  public ForestNodeController()
  {
    super();
    label = null;
  }

  public void mouseClicked(MouseEvent aMouseEvent)
  {
    System.out.println(label.getText());
    return;
  }

  public void setLabel(JLabel aLabel)
  {
    label = aLabel;
    label.addMouseListener(this);
    label.addMouseMotionListener(this);
    return;
  }

}
