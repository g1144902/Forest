package forest;


import java.util.ArrayList;
import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;


public class ForestView extends mvc.View
{

  public ForestView(ForestModel aModel)
  {
    super(aModel);
  }

  public void paintComponent(Graphics aGraphics)
  {
    Graphics offsetGraphics = model.picture().getGraphics();

    int width = this.getWidth();
    int height = this.getHeight();
    aGraphics.setColor(Color.white);
    aGraphics.fillRect(0, 0, width, height);
    offsetGraphics.setColor(Color.white);
    offsetGraphics.fillRect(0, 0, model.picture().getWidth(), model.picture().getHeight());
    return;
  }

  public void paintChildren(Graphics aGraphics)
  {
    super.paintChildren(model.picture().getGraphics());

    Graphics offsetGraphics = model.picture().getGraphics();
    offsetGraphics.setColor(Color.black);

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
            
            aParentLabel = aNode.getParent().getLabel();
            aParentX = aParentLabel.getX() + aParentLabel.getWidth();
            aParentY = aParentLabel.getY() + aParentLabel.getHeight() / 2;
            
            offsetGraphics.drawLine(aNodeX, aNodeY, aParentX, aParentY);
          }
      }
    Point offset = this.scrollAmount();
    aGraphics.drawImage(model.picture(), -offset.x, -offset.y, null);
  }

}
