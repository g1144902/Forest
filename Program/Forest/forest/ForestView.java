package forest;


import java.util.ArrayList;
import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;
import javax.swing.JLabel;


public class ForestView extends mvc.View
{

  private Point oldOffset;

  public ForestView(ForestModel aModel)
  {
    super(aModel);
    oldOffset = new Point(0, 0);
  }

  public void paintComponent(Graphics aGraphics)
  {
    int width = this.getWidth();
    int height = this.getHeight();
    aGraphics.setColor(Color.white);
    aGraphics.fillRect(0, 0, width, height);

    ForestModel aModel = (ForestModel) model;
    Point aPoint = this.scrollAmount();
    aModel.scrollNodes(aPoint);

    return;
  }

  public void paintChildren(Graphics aGraphics)
  {
    super.paintChildren(aGraphics);

    aGraphics.setColor(Color.black);

    ForestModel aModel = (ForestModel) model;
    JLabel aNodeLabel;
    int aNodeX, aNodeY;
    JLabel aParentLabel;
    int aParentX, aParentY;
    for (ForestNode aNode : aModel.getForestNodes())
      {
        if (!aNode.isRoot())
          {
            aNodeLabel = aNode.getLabel();
            aNodeX = aNodeLabel.getX();
            aNodeY = aNodeLabel.getY() + aNodeLabel.getHeight() / 2;
            
            for (ForestNode aParent : aNode.getParents())
              {
                aParentLabel = aParent.getLabel();
                aParentX = aParentLabel.getX() + aParentLabel.getWidth();
                aParentY = aParentLabel.getY() + aParentLabel.getHeight() / 2;
            
                aGraphics.drawLine(aNodeX, aNodeY, aParentX, aParentY);
              }
          }
      }
  }

}
