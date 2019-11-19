package kcn.libgdxbrowser;

// by KCN

import kcn.libgdxbrowser.content.ContentManager;
import kcn.libgdxbrowser.menu.MenuManager;

public class Browser
{
     public final MenuManager menuManager;
     public final ContentManager contentManager;

     public final SignallingInputProcessor inputProcessor;

     public Browser(SignallingInputProcessor inputProcessor)
     {
          this.inputProcessor = inputProcessor;
          contentManager = new ContentManager(inputProcessor);
          menuManager = new MenuManager(inputProcessor,contentManager);
     }
}
