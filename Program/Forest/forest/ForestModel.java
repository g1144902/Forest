package forest;


import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class ForestModel extends mvc.Model
{

  /**
   * ノードの集合
   */
  private ArrayList<ForestNode> forestNodes;

  public ForestModel(File aFile)
  {
    super();
    forestNodes = new ArrayList<ForestNode>();
    this.createNodes(aFile);
    this.open(aFile.getName());
    this.perform();
  }

  public void open(String aFileName)
  {
    ForestView aView = new ForestView(this);
    JFrame aWindow = new JFrame(aFileName);
    aView.setLayout(null);

    int maxWidth = 0;
    int maxHeight = 0;
    for (ForestNode aNode : forestNodes)
      {
        JLabel aLabel = aNode.getLabel();
        if (aLabel.getWidth() > maxWidth)
          {
            maxWidth = aLabel.getWidth();
          }
        if (aLabel.getHeight() > maxHeight)
          {
            maxHeight = aLabel.getHeight();
          }
      }
    int maximumWidth = forestNodes.size() * maxWidth + Constants.HORIZONTAL_INTERVAL * (forestNodes.size() - 1);
    int maximumHeight = forestNodes.size() * maxHeight + forestNodes.size();
    this.picture(new BufferedImage(maximumWidth, maximumHeight, BufferedImage.TYPE_INT_BGR));

    aWindow.getContentPane().add(aView);
    aWindow.setMinimumSize(new Dimension(400, 300));
    aWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    aWindow.setSize(800, 600);
    aWindow.setVisible(true);

    int aNodeX = 0, aNodeY = Constants.VERTICAL_INTERVAL;
    for (ForestNode aNode : forestNodes)
      {
        aNode.moveTo(aNodeX, aNodeY);
        JLabel aLabel = aNode.getLabel();
        aView.add(aLabel);
        aNodeY += aLabel.getHeight() + Constants.VERTICAL_INTERVAL;
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
    for (ForestNode aNode : forestNodes)
      {
        if (aNode.isRoot())
          {
            this.recursiveNodeAligning(rootRows, aNode);
            rootRows += aNode.getRows();
          }
      }
    return;
  }

  public void recursiveNodeAligning(int line, ForestNode aNode)
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
        aNodeY = line * aLabel.getHeight() + line * Constants.VERTICAL_INTERVAL;
      }
    aNode.moveTo((int) aNodeX, (int) aNodeY);
    this.changed();
    try
      {
        Thread.sleep(50);
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
            this.recursiveNodeAligning(childLine, aChild);
            childLine += aChild.getRows() - 1;
            childLine++;
          }
      }
    
    aNodeX = aLabel.getX();
    if ((aNode.getRows() % 2) == 0)
      {
        aNodeY = aLabel.getY() + (aNode.getRows() / 2 - 0.5) * aLabel.getHeight() + aNode.getRows() / 2;
      }
    else
      {
        aNodeY = aLabel.getY() + (aNode.getRows() / 2) * aLabel.getHeight() + aNode.getRows() / 2;
      }
    aNode.moveTo((int) aNodeX, (int) aNodeY);
    this.changed();
    try
      {
        Thread.sleep(50);
      }
    catch (InterruptedException anException)
      {
        System.err.println(anException);
        throw new RuntimeException(anException.toString());
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

}
