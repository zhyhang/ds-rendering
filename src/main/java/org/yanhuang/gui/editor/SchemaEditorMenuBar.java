package org.yanhuang.gui.editor;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxGraphActions;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxResources;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SchemaEditorMenuBar extends JMenuBar
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6776304509649205465L;

	@SuppressWarnings("serial")
	public SchemaEditorMenuBar(final BasicGraphEditor editor)
	{
		final mxGraphComponent graphComponent = editor.getGraphComponent();
		final mxGraph graph = graphComponent.getGraph();
		JMenu menu = null;
		JMenu submenu = null;

		// Creates the file menu
		menu = add(new JMenu(mxResources.get("file")));

		menu.add(editor.bind(mxResources.get("new"), new EditorActions.NewAction(),
				"/images/new.gif"));
		menu.add(editor.bind(mxResources.get("openFile"), new EditorActions.OpenAction(),
				"/images/open.gif"));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("save"), new EditorActions.SaveAction(false),
				"/images/save.gif"));
		menu.add(editor.bind(mxResources.get("saveAs"), new EditorActions.SaveAction(true),
				"/images/saveas.gif"));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("pageSetup"),
				new EditorActions.PageSetupAction(),
				"/images/pagesetup.gif"));
		menu.add(editor.bind(mxResources.get("print"), new EditorActions.PrintAction(),
				"/images/print.gif"));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("exit"), new EditorActions.ExitAction()));

		// Creates the edit menu
		menu = add(new JMenu(mxResources.get("edit")));

		menu.add(editor.bind(mxResources.get("undo"), new EditorActions.HistoryAction(true),
				"/images/undo.gif"));
		menu.add(editor.bind(mxResources.get("redo"), new EditorActions.HistoryAction(false),
				"/images/redo.gif"));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("cut"), TransferHandler
				.getCutAction(), "/images/cut.gif"));
		menu.add(editor
				.bind(mxResources.get("copy"), TransferHandler.getCopyAction(),
						"/images/copy.gif"));
		menu.add(editor.bind(mxResources.get("paste"), TransferHandler
				.getPasteAction(),
				"/images/paste.gif"));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("delete"), mxGraphActions
				.getDeleteAction(),
				"/images/delete.gif"));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("selectAll"), mxGraphActions
				.getSelectAllAction()));
		menu.add(editor.bind(mxResources.get("selectNone"), mxGraphActions
				.getSelectNoneAction()));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("warning"), new EditorActions.WarningAction()));
		menu.add(editor.bind(mxResources.get("edit"), mxGraphActions
				.getEditAction()));

		// Creates the view menu
		menu = add(new JMenu(mxResources.get("view")));

		JMenuItem item = menu.add(new EditorActions.TogglePropertyItem(graphComponent,
				mxResources.get("pageLayout"), "PageVisible", true,
				new ActionListener()
				{
					/**
					 * 
					 */
					public void actionPerformed(ActionEvent e)
					{
						if (graphComponent.isPageVisible()
								&& graphComponent.isCenterPage())
						{
							graphComponent.zoomAndCenter();
						}
					}
				}));

		item.addActionListener(new ActionListener()
		{
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(ActionEvent e)
			{
				if (e.getSource() instanceof EditorActions.TogglePropertyItem)
				{
					final mxGraphComponent graphComponent = editor
							.getGraphComponent();
					EditorActions.TogglePropertyItem toggleItem = (EditorActions.TogglePropertyItem) e
							.getSource();

					if (toggleItem.isSelected())
					{
						// Scrolls the view to the center
						SwingUtilities.invokeLater(new Runnable()
						{
							/*
							 * (non-Javadoc)
							 * @see java.lang.Runnable#run()
							 */
							public void run()
							{
								graphComponent.scrollToCenter(true);
								graphComponent.scrollToCenter(false);
							}
						});
					}
					else
					{
						// Resets the translation of the view
						mxPoint tr = graphComponent.getGraph().getView()
								.getTranslate();

						if (tr.getX() != 0 || tr.getY() != 0)
						{
							graphComponent.getGraph().getView().setTranslate(
									new mxPoint());
						}
					}
				}
			}
		});

		menu.add(new EditorActions.TogglePropertyItem(graphComponent, mxResources
				.get("antialias"), "AntiAlias", true));

		menu.addSeparator();

		menu.add(new EditorActions.ToggleGridItem(editor, mxResources.get("grid")));
		menu.add(new EditorActions.ToggleRulersItem(editor, mxResources.get("rulers")));

		menu.addSeparator();

		submenu = (JMenu) menu.add(new JMenu(mxResources.get("zoom")));

		submenu.add(editor.bind("400%", new EditorActions.ScaleAction(4)));
		submenu.add(editor.bind("200%", new EditorActions.ScaleAction(2)));
		submenu.add(editor.bind("150%", new EditorActions.ScaleAction(1.5)));
		submenu.add(editor.bind("100%", new EditorActions.ScaleAction(1)));
		submenu.add(editor.bind("75%", new EditorActions.ScaleAction(0.75)));
		submenu.add(editor.bind("50%", new EditorActions.ScaleAction(0.5)));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("custom"), new EditorActions.ScaleAction(0)));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("zoomIn"), mxGraphActions
				.getZoomInAction()));
		menu.add(editor.bind(mxResources.get("zoomOut"), mxGraphActions
				.getZoomOutAction()));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("page"), new EditorActions.ZoomPolicyAction(
				mxGraphComponent.ZOOM_POLICY_PAGE)));
		menu.add(editor.bind(mxResources.get("width"), new EditorActions.ZoomPolicyAction(
				mxGraphComponent.ZOOM_POLICY_WIDTH)));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("actualSize"), mxGraphActions
				.getZoomActualAction()));

		// Creates the diagram menu
		menu = add(new JMenu(mxResources.get("diagram")));

		menu.add(new EditorActions.ToggleOutlineItem(editor, mxResources.get("outline")));

		menu.addSeparator();

		submenu = (JMenu) menu.add(new JMenu(mxResources.get("background")));

		submenu.add(editor.bind(mxResources.get("backgroundColor"),
				new EditorActions.BackgroundAction()));
		submenu.add(editor.bind(mxResources.get("backgroundImage"),
				new EditorActions.BackgroundImageAction()));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("pageBackground"),
				new EditorActions.PageBackgroundAction()));

		submenu = (JMenu) menu.add(new JMenu(mxResources.get("grid")));

		submenu.add(editor.bind(mxResources.get("gridSize"),
				new EditorActions.PromptPropertyAction(graph, "Grid Size", "GridSize")));
		submenu.add(editor.bind(mxResources.get("gridColor"),
				new EditorActions.GridColorAction()));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("dashed"), new EditorActions.GridStyleAction(
				mxGraphComponent.GRID_STYLE_DASHED)));
		submenu.add(editor.bind(mxResources.get("dot"), new EditorActions.GridStyleAction(
				mxGraphComponent.GRID_STYLE_DOT)));
		submenu.add(editor.bind(mxResources.get("line"), new EditorActions.GridStyleAction(
				mxGraphComponent.GRID_STYLE_LINE)));
		submenu.add(editor.bind(mxResources.get("cross"), new EditorActions.GridStyleAction(
				mxGraphComponent.GRID_STYLE_CROSS)));

		menu.addSeparator();

		submenu = (JMenu) menu.add(new JMenu(mxResources.get("layout")));

		submenu.add(editor.graphLayout("verticalHierarchical", true));
		submenu.add(editor.graphLayout("horizontalHierarchical", true));

		submenu.addSeparator();

		submenu.add(editor.graphLayout("verticalPartition", false));
		submenu.add(editor.graphLayout("horizontalPartition", false));

		submenu.addSeparator();

		submenu.add(editor.graphLayout("verticalStack", false));
		submenu.add(editor.graphLayout("horizontalStack", false));

		submenu.addSeparator();

		submenu.add(editor.graphLayout("verticalTree", true));
		submenu.add(editor.graphLayout("horizontalTree", true));

		submenu.addSeparator();

		submenu.add(editor.graphLayout("parallelEdges", false));

		submenu.addSeparator();

		submenu.add(editor.graphLayout("organicLayout", true));

		submenu = (JMenu) menu.add(new JMenu(mxResources.get("selection")));

		submenu.add(editor.bind(mxResources.get("selectPath"),
				new EditorActions.SelectShortestPathAction(false)));
		submenu.add(editor.bind(mxResources.get("selectDirectedPath"),
				new EditorActions.SelectShortestPathAction(true)));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("selectTree"),
				new EditorActions.SelectSpanningTreeAction(false)));
		submenu.add(editor.bind(mxResources.get("selectDirectedTree"),
				new EditorActions.SelectSpanningTreeAction(true)));

		menu.addSeparator();

		submenu = (JMenu) menu.add(new JMenu(mxResources.get("stylesheet")));

		submenu
				.add(editor
						.bind(
								mxResources.get("basicStyle"),
								new EditorActions.StylesheetAction(
										"/resources/basic-style.xml")));
		submenu
				.add(editor
						.bind(
								mxResources.get("defaultStyle"),
								new EditorActions.StylesheetAction(
										"/resources/default-style.xml")));

		// Creates the options menu
		menu = add(new JMenu(mxResources.get("options")));

		submenu = (JMenu) menu.add(new JMenu(mxResources.get("display")));
		submenu.add(new EditorActions.TogglePropertyItem(graphComponent, mxResources
				.get("buffering"), "TripleBuffered", true));
		submenu.add(editor.bind(mxResources.get("dirty"),
				new EditorActions.ToggleDirtyAction()));

		submenu.addSeparator();

		item = submenu.add(new EditorActions.TogglePropertyItem(graphComponent, mxResources
				.get("centerPage"), "CenterPage", true, new ActionListener()
		{
			/**
			 * 
			 */
			public void actionPerformed(ActionEvent e)
			{
				if (graphComponent.isPageVisible()
						&& graphComponent.isCenterPage())
				{
					graphComponent.zoomAndCenter();
				}
			}
		}));

		submenu.add(new EditorActions.TogglePropertyItem(graphComponent, mxResources
				.get("centerZoom"), "CenterZoom", true));
		submenu.add(new EditorActions.TogglePropertyItem(graphComponent, mxResources
				.get("zoomToSelection"), "KeepSelectionVisibleOnZoom", true));

		submenu.addSeparator();

		submenu.add(new EditorActions.TogglePropertyItem(graphComponent, mxResources
				.get("preferPagesize"), "PreferPageSize", true));

		// This feature is not yet implemented
		//submenu.add(new TogglePropertyItem(graphComponent, mxResources
		//		.get("pageBreaks"), "PageBreaksVisible", true));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("tolerance"),
				new EditorActions.PromptPropertyAction(graph, "Tolerance")));

		// Creates the window menu
		menu = add(new JMenu(mxResources.get("window")));

		UIManager.LookAndFeelInfo[] lafs = UIManager.getInstalledLookAndFeels();

		for (int i = 0; i < lafs.length; i++)
		{
			final String clazz = lafs[i].getClassName();
			menu.add(new AbstractAction(lafs[i].getName())
			{
				public void actionPerformed(ActionEvent e)
				{
					editor.setLookAndFeel(clazz);
				}
			});
		}

		// Creates the help menu
		menu = add(new JMenu(mxResources.get("help")));

		item = menu.add(new JMenuItem(mxResources.get("aboutGraphEditor")));
		item.addActionListener(new ActionListener()
		{
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(ActionEvent e)
			{
				editor.about();
			}
		});
	}

}
