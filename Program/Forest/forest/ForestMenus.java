package forest;


import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.MenuElement;


/**
 * Forestのメニュー
 */
public class ForestMenus extends Object
{

  /**
   * モデルを束縛
   */
  private ForestModel model;

  /**
   * ポップアップメニューを束縛 
   */
  private JPopupMenu popupMenu;

  /**
   * メニューのコンストラクタ
   */
  public ForestMenus(ForestModel aModel)
  {
    super();
    model = aModel;
  }

  /**
   * ポップアップメニューにメニュー追加しポップアップメニューを返す
   */
  public JPopupMenu init()
  {
    popupMenu = new JPopupMenu();
    JMenuItem aMenuItem = new JMenuItem("Algorithm Animation");
    ActionListener aListener = new ActionListener()
      {
        private ForestModel model;
        
        public ActionListener setModel(ForestModel aModel)
        {
          model = aModel;
          return this;
        }
        
        public void actionPerformed(ActionEvent anActionEvent)
        {
          Thread aThread = new Thread(model);
          aThread.start();
          
          MenuElement[] elements = model.getPopupMenu().getSubElements();
          for (MenuElement anElement : elements)
            {
              JMenuItem anItem = (JMenuItem) anElement;
              anItem.setEnabled(false);
            }
          return;
        }
      }.setModel(model);
    aMenuItem.addActionListener(aListener);
    popupMenu.add(aMenuItem);

    aMenuItem = new JMenuItem("Zoom Up");
    aListener = new ActionListener()
      {
        private ForestModel model;
        
        public ActionListener setModel(ForestModel aModel)
        {
          model = aModel;
          return this;
        }
        
        public void actionPerformed(ActionEvent anActionEvent)
        {
          ArrayList<ForestNode> nodes = model.getForestNodes();
          for (ForestNode aNode : nodes)
            {
              int aFontSize = aNode.getFontSize();
              aFontSize++;
              aNode.setFontSize(aFontSize);
              aNode.adjustLabelSize();
            }
          model.perform();
          return;
        }
      }.setModel(model);
    aMenuItem.addActionListener(aListener);
    popupMenu.add(aMenuItem);

    aMenuItem = new JMenuItem("Zoom Down");
    aListener = new ActionListener()
      {
        private ForestModel model;
        
        public ActionListener setModel(ForestModel aModel)
        {
          model = aModel;
          return this;
        }
        
        public void actionPerformed(ActionEvent anActionEvent)
        {
          ArrayList<ForestNode> nodes = model.getForestNodes();
          for (ForestNode aNode : nodes)
            {
              int aFontSize = aNode.getFontSize();
              aFontSize--;
              aNode.setFontSize(aFontSize);
              aNode.adjustLabelSize();
            }
          model.perform();
          return;
        }
      }.setModel(model);
    aMenuItem.addActionListener(aListener);
    popupMenu.add(aMenuItem);

    aMenuItem = new JMenuItem("Reset");
    aListener = new ActionListener()
      {
        private ForestModel model;
        
        public ActionListener setModel(ForestModel aModel)
        {
          model = aModel;
          return this;
        }
        
        public void actionPerformed(ActionEvent anActionEvent)
        {
          ArrayList<ForestNode> nodes = model.getForestNodes();
          for (ForestNode aNode : nodes)
            {
              aNode.setFontSize(Constants.FONT_SIZE);
              aNode.adjustLabelSize();
            }
          model.perform();
          return;
        }
      }.setModel(model);
    aMenuItem.addActionListener(aListener);
    popupMenu.add(aMenuItem);

    return popupMenu;
  }

}
