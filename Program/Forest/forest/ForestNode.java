package forest;


import java.util.TreeMap;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;


public class ForestNode extends Object
{

  /**
   * 自身を描画するために View に渡すラベル
   */
  private JLabel label;

  /**
   * 自身のサイズ
   */
  private Dimension size;

  private ForestNode parent;

  private TreeMap<Integer, ForestNode> children;

  private int rows;

  public ForestNode(String aText)
  {
    label = new JLabel(aText);
    label.setBorder(new LineBorder(Color.black));
    label.setFont(new Font("Serif", Font.PLAIN, 12));
    this.adjustLabelSize();

    children = new TreeMap<Integer, ForestNode>();
  }

  /**
   * ラベルを返す
   */
  public JLabel getLabel()
  {
    return label;
  }

  /**
   * ラベルのサイズを文字列とフォントに合わせて適切なサイズにする
   */
  public void adjustLabelSize()
  {
    size = label.getPreferredSize();
    label.setSize(size);
    return;
  }

  /**
   * 指定された x, y 座標に移動する
   */
  public void moveTo(int x, int y)
  {
    label.setBounds(x, y, size.width, size.height);
    return;
  }

  public void setParent(ForestNode aNode)
  {
    parent = aNode;
    return;
  }

  public ForestNode getParent()
  {
    return parent;
  }

  public void addChild(int anIndex, ForestNode aNode)
  {
    children.put(anIndex, aNode);
    return;
  }

  public TreeMap<Integer, ForestNode> getChildren()
  {
    return children;
  }

  public void recursiveInitRows()
  {
    rows = 0;
    if (!children.isEmpty())
      {
        for (ForestNode aChild : children.values())
          {
            aChild.recursiveInitRows();
            rows += aChild.getRows();
          }
      }
    else
      {
        rows++;
      }
    return;
  }

  public int getRows()
  {
    return rows;
  }

  public boolean isRoot()
  {
    if (parent == null)
      {
        return true;
      }
    else
      {
        return false;
      }
  }

}
