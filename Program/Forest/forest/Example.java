package forest;


import java.util.ArrayList;


public class Example extends Object
{
  
  public static void main(String[] args)
  {
    ForestModel aModel = new ForestModel();
    ArrayList<ForestNode> aList = aModel.getForestNodes();
    for (ForestNode aNode : aList)
      {
        System.out.print(aNode.getLabel().getText() + " - ");
        for (ForestNode aChild : aNode.getChildren().values())
          {
            System.out.print(aChild.getLabel().getText() + " - ");
          }
        System.out.println();
      }
  }

}
