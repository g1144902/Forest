package forest;


import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.Color;
import java.awt.Point;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;


public class ForestModel extends mvc.Model implements Runnable
{

  /**
   * ノードの集合
   */
  private ArrayList<ForestNode> forestNodes;

  private Point offset;

  private Point oldOffset;

  private JPopupMenu popupMenu;

  public ForestModel(File aFile)
  {
    super();
    forestNodes = new ArrayList<ForestNode>();
    offset = new Point(0, 0);
    oldOffset = new Point(0, 0);
    this.createNodes(aFile);
    this.open(aFile.getName());
    this.perform();
  }

  public void open(String aFileName)
  {
    ForestController aController = new ForestController();
    ForestView aView = new ForestView(this, aController);
    JFrame aWindow = new JFrame(aFileName);
    aView.setLayout(null);

    aWindow.getContentPane().add(aView);
    aWindow.setMinimumSize(new Dimension(400, 300));
    aWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    aWindow.setSize(800, 600);
    aWindow.setVisible(true);

    popupMenu = new JPopupMenu();
    JMenuItem aMenuItem = new JMenuItem("Algorithm Animation");
    aMenuItem.addActionListener(aController);
    popupMenu.add(aMenuItem);

    for (ForestNode aNode : forestNodes)
      {
        JLabel aLabel = aNode.getLabel();
        aView.add(aLabel);
      }

    return;
  }

  /**
   * ファイルの名前からノードの集合を作成する。
   */
  public void createNodes(File aFile)
  {
    try
      {
        FileReader aFileReader = new FileReader(aFile);
        BufferedReader aBufferedReader = new BufferedReader(aFileReader);
        String aLine;
        while ((aLine = aBufferedReader.readLine()) != null)
          {
            if (aLine.equals("nodes:"))
              {
                String[] anElement;
                while (!(aLine = aBufferedReader.readLine()).equals("branches:"))
                  {
                    anElement = aLine.split(", ");
                    ForestNode aNode = new ForestNode(anElement[1]);
                    this.addForestNode(aNode);
                  }
              }
            if (aLine.equals("branches:"))
              {
                String[] indexes;
                int parentIndex, childIndex;
                ForestNode aParent, aChild;
                while ((aLine = aBufferedReader.readLine()) != null)
                  {
                    indexes = aLine.split(", ");
                    parentIndex = Integer.parseInt(indexes[0]) - 1;
                    childIndex = Integer.parseInt(indexes[1]) - 1;

                    aParent = this.getForestNode(parentIndex);
                    aChild = this.getForestNode(childIndex);

                    aParent.addChild(childIndex, aChild);
                    aChild.addParent(aParent);
                  }
              }
          }
            
        aBufferedReader.close();
        aFileReader.close();
      }
    catch (FileNotFoundException anException)
      {
        System.out.println(anException);
      }
    catch (IOException anException)
      {
        System.out.println(anException);
      }
    
    int rootDepth = 0;
    for (ForestNode aNode : forestNodes)
      {
        if (aNode.isRoot())
          {
            aNode.recursiveInitRows();
            aNode.recursiveInitDepth(rootDepth);
          }
      }
    return;
  }

  public void perform()
  {
    int rootRows = 0;
    this.resetNodes();
    for (ForestNode aNode : forestNodes)
      {
        if (aNode.isRoot())
          {
            this.recursiveNodeAligning(rootRows, aNode, 0);
            rootRows += aNode.getRows();
          }
      }
    return;
  }

  public void run()
  {
    int rootRows = 0;
    this.resetNodes();
    for (ForestNode aNode : forestNodes)
      {
        if (aNode.isRoot())
          {
            this.recursiveNodeAligning(rootRows, aNode, 50);
            rootRows += aNode.getRows();
          }
      }
    return;
  }

  public void recursiveNodeAligning(int line, ForestNode aNode, int waitTime)
  {
    double aNodeX, aNodeY;
    JLabel aLabel;
    aLabel = aNode.getLabel();
    if (!aNode.isRoot())
      {
        JLabel aParentLabel;
        aParentLabel = aNode.getParent().getLabel();
        aNodeX = aParentLabel.getX() + aParentLabel.getWidth() + Constants.HORIZONTAL_INTERVAL;
        aNodeY = aParentLabel.getY() + aParentLabel.getHeight() * line + line * Constants.VERTICAL_INTERVAL;
      }
    else
      {
        aNodeX = aLabel.getX();
        aNodeY = line * aLabel.getHeight() + line * Constants.VERTICAL_INTERVAL - offset.y;
      }
    aNode.moveTo((int) aNodeX, (int) aNodeY);
    this.changed();
    try
      {
        Thread.sleep(waitTime);
      }
    catch (InterruptedException anException)
      {
        System.err.println(anException);
        throw new RuntimeException(anException.toString());
      }

    int childLine = 0;
    for (ForestNode aChild : aNode.getChildren().values())
      {
        if (aChild.getParent().equals(aNode))
          {
            this.recursiveNodeAligning(childLine, aChild, waitTime);
            childLine += aChild.getRows() - 1;
            childLine++;
          }
      }
    
    aNodeX = aLabel.getX();
    if ((aNode.getRows() % 2) == 0)
      {
        aNodeY = aLabel.getY() + (aNode.getRows() / 2 - 0.5) * aLabel.getHeight() + aNode.getRows() / 2 * Constants.VERTICAL_INTERVAL;
      }
    else
      {
        aNodeY = aLabel.getY() + (aNode.getRows() / 2) * aLabel.getHeight() + aNode.getRows() / 2 * Constants.VERTICAL_INTERVAL;
      }
    aNode.moveTo((int) aNodeX, (int) aNodeY);
    this.changed();
    try
      {
        Thread.sleep(waitTime);
      }
    catch (InterruptedException anException)
      {
        System.err.println(anException);
        throw new RuntimeException(anException.toString());
      }

    return;
  }

  public void scrollNodes(Point aPoint)
  {
    offset = aPoint;
    if (!offset.equals(oldOffset))
      {
        for (ForestNode aNode : forestNodes)
          {
            Point nodePoint = aNode.getLabel().getLocation();
            nodePoint.translate(-offset.x + oldOffset.x, -offset.y + oldOffset.y);
            aNode.getLabel().setLocation(nodePoint);
          }
      }
    oldOffset = new Point(offset);
  }

  public void resetNodes()
  {
    int aNodeX = 0, aNodeY = Constants.VERTICAL_INTERVAL;
    for (ForestNode aNode : forestNodes)
      {
        aNode.moveTo(aNodeX, aNodeY);
        JLabel aLabel = aNode.getLabel();
        aNodeY += aLabel.getHeight() + Constants.VERTICAL_INTERVAL;
      }
    return;
  }

  /**
   * ノードの集合の最後にノードを追加する。
   */
  public void addForestNode(ForestNode aNode)
  {
    forestNodes.add(aNode);
    return;
  }

  /**
   * インデックスで指定されたノードを返す。
   */
  public ForestNode getForestNode(int anIndex)
  {
    ForestNode aNode = forestNodes.get(anIndex);
    return aNode;
  }

  /**
   * ノードの集合を返す。
   */
  public ArrayList<ForestNode> getForestNodes()
  {
    return forestNodes;
  }

  public JPopupMenu getPopupMenu()
  {
    return popupMenu;
  }
  
}
