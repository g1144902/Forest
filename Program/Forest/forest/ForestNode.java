package forest;


import java.util.ArrayList;
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

  private ArrayList<ForestNode> parents;

  private TreeMap<Integer, ForestNode> children;

  private int rows;

  private int depth;

  public ForestNode(String aText)
  {
    label = new JLabel(aText);
    label.setBorder(new LineBorder(Color.black));
    label.setFont(new Font("Serif", Font.PLAIN, Constants.FONT_SIZE));
    this.adjustLabelSize();

    parents = new ArrayList<ForestNode>();
    children = new TreeMap<Integer, ForestNode>();
    depth = 0;
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

  public void addParent(ForestNode aNode)
  {
    parents.add(aNode);
    return;
  }

  public ForestNode getParent()
  {
    ForestNode aParent = null;
    for (ForestNode aNode : parents)
      {
        if (aParent == null || aNode.getDepth() > aParent.getDepth())
          {
            aParent = aNode;
          }
      }
    return aParent;
  }

  public ArrayList<ForestNode> getParents()
  {
    return parents;
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

  public void recursiveInitDepth(int parentDepth)
  {
    int aDepth = parentDepth + 1;
    if (depth != 0)
      {
        if (aDepth < depth)
          {
            return;
          }
      }
    depth = aDepth;
    if (!children.isEmpty())
      {
        for (ForestNode aChild : children.values())
          {
            aChild.recursiveInitDepth(depth);
          }
      }
    return;
  }

  public int getRows()
  {
    return rows;
  }

  public int getDepth()
  {
    return depth;
  }

  public boolean isRoot()
  {
    if (parents.isEmpty())
      {
        return true;
      }
    else
      {
        return false;
      }
  }

}
