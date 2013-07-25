package forest;


import java.util.ArrayList;
import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;
import javax.swing.JLabel;

/**
 * MVCにおけるビュー
 */
public class ForestView extends mvc.View
{

  /**
   * モデルとコントローラーからビューを形成
   */
  public ForestView(ForestModel aModel, ForestController aController)
  {
    super(aModel, aController);    
  }

  /**
   * 自身を白で塗りつぶしノードをスクロール量分スクロール
   */
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

  /**
   * 自身の子コンポーネントであるノードを描写
   */
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
