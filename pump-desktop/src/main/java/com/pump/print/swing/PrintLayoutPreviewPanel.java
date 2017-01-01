/**
 * This software is released as part of the Pumpernickel project.
 * 
 * All com.pump resources in the Pumpernickel project are distributed under the
 * MIT License:
 * https://raw.githubusercontent.com/mickleness/pumpernickel/master/License.txt
 * 
 * More information about the Pumpernickel project is available here:
 * https://mickleness.github.io/pumpernickel/
 */
package com.pump.print.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.pump.awt.DemoPaintable;
import com.pump.awt.Paintable;
import com.pump.blog.ResourceSample;
import com.pump.plaf.CompactNavigationPanelUI;
import com.pump.plaf.NavigationPanelUI.NumberSpinnerDescriptor;
import com.pump.print.PrintLayout;

/** This panel visually shows a preview of a PrintLayout and
 * all the cells inside it.  It registers a listener
 * on the PrintLayout object itself, so whenever the layout
 * changes this panel immediately repaints.
 * 
 * 
 * <!-- ======== START OF AUTOGENERATED SAMPLES ======== -->
 * <p><img src="https://raw.githubusercontent.com/mickleness/pumpernickel/master/pump-release/resources/samples/PrintLayoutPreviewPanel/sample.png" alt="new&#160;com.bric.print.swing.PrintLayoutPreviewPanel(&#160;new&#160;com.bric.print.PrintLayout()&#160;)">
 * <!-- ======== END OF AUTOGENERATED SAMPLES ======== -->
 */
@ResourceSample( sample = { "new com.bric.print.swing.PrintLayoutPreviewPanel( new com.bric.print.PrintLayout() )"} )
public class PrintLayoutPreviewPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	PrintLayout layout;
	/** True to center the content, otherwise, it's at the top */
	boolean center = true;
	
	private static abstract class CombinedListener implements ChangeListener, PropertyChangeListener {}
	
	CombinedListener propertyChangeListener = new CombinedListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			if(paintables!=null && paintables.length>0) {
				int cellsPerPage = layout.getColumns()*layout.getRows();
				int pageCount = (paintables.length+cellsPerPage-1) / cellsPerPage;
				int page = ((Number)navigationPanel.getValue()).intValue();
				if(page>=pageCount) {
					page = pageCount-1;
				}
				navigationPanel.setModel(new SpinnerNumberModel(page, 0, pageCount-1, 1));
			}
			
			recalculateContents();
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			stateChanged(null);
		}
	};
	
	ComponentListener componentListener = new ComponentAdapter() {
		@Override
		public void componentResized(ComponentEvent e) {
			repaint();
		}
	};
	
	JSpinner navigationPanel = new JSpinner(new SpinnerNumberModel(0,0,0,1));
	
	private Rectangle2D[][] cells;
	Rectangle2D paperRect;
	Paintable layoutPaintable;
	Paintable[] paintables;
	boolean navigationVisible = true;
	
	public PrintLayoutPreviewPanel(PrintLayout layout) {
		this.layout = layout;
		navigationPanel.putClientProperty( CompactNavigationPanelUI.PROPERTY_DESCRIPTOR, new NumberSpinnerDescriptor(navigationPanel, "Page", 1));
		navigationPanel.setUI(new CompactNavigationPanelUI());
		navigationPanel.addChangeListener(propertyChangeListener);
		layout.addPropertyChangeListener(propertyChangeListener);
		addComponentListener(componentListener);
		setPreferredSize(new Dimension( 200,200 ));
		add(navigationPanel);

		propertyChangeListener.propertyChange(null);
		
		
	}
	
	public JSpinner getNavigationPanel() {
		return navigationPanel;
	}
	
	public void setNavigationControlsVisible(boolean b) {
		navigationVisible = b;
		recalculateContents();
	}
	
	public boolean isNavigationControlsVisible() {
		return navigationVisible;
	}
	
	public void setContentCentered(boolean center) {
		this.center = center;
	}
	
	protected void recalculateContents() {
		int cellCount = layout.getColumns()*layout.getRows();
		cells = layout.getCellLayout(cellCount);
		paperRect = new Rectangle2D.Double(0,0,layout.getPaperWidth(),layout.getPaperHeight());
	
		SpinnerNumberModel model = (SpinnerNumberModel)navigationPanel.getModel();
		
		
		int pageNumber = model.getNumber().intValue()+1;
		int pageCount = ((Number)model.getMaximum()).intValue();
		if(paintables==null || paintables.length==0) {
			Paintable[] tempPaintables = createPaintables((int)(cells[0][0].getWidth()),
					(int)(cells[0][0].getHeight()),
					cells[0].length);
			layoutPaintable = layout.createPaintables(tempPaintables,pageNumber,pageCount)[0];
			navigationPanel.setVisible(false);
		} else {
			navigationPanel.setVisible(true && navigationVisible);
			int offset = cellCount*model.getNumber().intValue();
			int length = Math.min(paintables.length-offset,cellCount);
			Paintable[] relevantPaintables = new Paintable[length];
			System.arraycopy(paintables,offset,relevantPaintables,0,length);
			
			layoutPaintable = layout.createPaintables(relevantPaintables,pageNumber,pageCount)[0];
		}
			
		repaint();
	}
	
	public void setPaintables(Paintable[] tiles) {
		paintables = new Paintable[tiles.length];
		System.arraycopy(tiles,0,paintables,0,tiles.length);
		propertyChangeListener.propertyChange(null);
	}

	int navigationPadding = 10;
	Insets insets = new Insets(10,5,10,5);
	@Override
	protected void paintComponent(Graphics g0) {
		super.paintComponent(g0);
		Graphics2D g = (Graphics2D)g0.create();
		
		Dimension navigationSize;
		if(navigationPanel.getParent()==null) {
			navigationSize = new Dimension(0,0);
		} else {
			navigationSize = navigationPanel.getSize();
			navigationSize.height += navigationPadding;
		}
		
		double w = getWidth()-insets.left-insets.right;
		double h = getHeight()-insets.top-insets.bottom-navigationSize.height;
		
		double wRatio = w/paperRect.getWidth();
		double hRatio = h/paperRect.getHeight();
		double zoom = Math.min(wRatio,hRatio);

		g.translate( (w+insets.left+insets.right)/2-paperRect.getWidth()*zoom/2,
				center ? (h+insets.top+insets.bottom)/2-paperRect.getHeight()*zoom/2+navigationSize.height : 0);
			
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		int shadowIterations = (int)(6*Math.pow(Math.min(getWidth(),getHeight())/200.0,.8));
		if(shadowIterations==0) shadowIterations = 1;
		g.setColor(new Color(0,0,0,50/shadowIterations));
		double paperWidth = paperRect.getWidth()*zoom;
		double paperHeight = paperRect.getHeight()*zoom;
		RoundRectangle2D r = new RoundRectangle2D.Double();
		for(int a = 0; a<shadowIterations; a++) {
			r.setRoundRect(-a,-a/3,paperWidth+2*a+1,paperHeight+a+1,3*a,3*a);
			g.fill(r);
		}

		g.scale(zoom, zoom);

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		
		g.setColor(Color.white);
		g.fill(paperRect);
		g.setColor(Color.darkGray);
		g.draw(paperRect);
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		
		// draw demo paintables
		layoutPaintable.paint(g);
	}
	
	private Paintable[] createPaintables(int w,int h,int count) {
		Paintable[] paintables = new Paintable[count];
		for(int a = 0; a<count; a++) {
			paintables[a] = new DemoPaintable(w,h,Integer.toString(a+1));
		}
		return paintables;
	}
}