package forest;


import java.util.ArrayList;
import java.util.TreeMap;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;


/**
 * 木構造の葉にあたるノード
 */
public class ForestNode extends Object
{

  /**
   * 自身を描画するためにビューに渡すラベル
   */
  private JLabel label;

  /**
   * 自身のコントローラー
   */
  private ForestNodeController controller;

  /**
   * 自身のサイズ
   */
  private Dimension size;

  /**
   * 自身の親ノードの集合
   */
  private ArrayList<ForestNode> parents;

  /**
   * 自身の子ノードの集合
   */
  private TreeMap<Integer, ForestNode> children;

  /**
   * 自身をルートとした時のツリーの大きさ
   */
  private int rows;

  /**
   * ルートから見た自身の深さ
   */
  private int depth;

  /**
   * テキストから自身を形成
   */
  public ForestNode(String aText)
  {
    label = new JLabel(aText);
    label.setBorder(new LineBorder(Color.black));
    label.setFont(new Font("Serif", Font.PLAIN, Constants.FONT_SIZE));
    this.adjustLabelSize();

    controller = new ForestNodeController();
    controller.setLabel(label);

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

  /**
   * 親ノードを追加する
   */
  public void addParent(ForestNode aNode)
  {
    parents.add(aNode);
    return;
  }

  /**
   * 自身の束縛する親ノードの中で最も深い親ノードを返す
   */
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

  /**
   * 親ノードの集合を返す
   */
  public ArrayList<ForestNode> getParents()
  {
    return parents;
  }

  /**
   * 子ノードを追加
   */
  public void addChild(int anIndex, ForestNode aNode)
  {
    children.put(anIndex, aNode);
    return;
  }

  /**
   * 子ノードの集合を返す
   */
  public TreeMap<Integer, ForestNode> getChildren()
  {
    return children;
  }

  /**
   * 再帰的に大きさを設定する
   */
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

  /**
   * 再帰的に深さを設定する
   */
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

  /**
   * 大きさを返す
   */
  public int getRows()
  {
    return rows;
  }

  /**
   * 深さを返す
   */
  public int getDepth()
  {
    return depth;
  }

  /**
   * 自身がルートであるかを判定する
   */
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
