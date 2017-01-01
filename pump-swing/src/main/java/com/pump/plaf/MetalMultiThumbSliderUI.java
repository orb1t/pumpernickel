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
package com.pump.plaf;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.SwingConstants;

import com.pump.blog.ResourceSample;
import com.pump.geom.ShapeBounds;
import com.pump.swing.MultiThumbSlider;

/** A <code>MultiThumbSliderUI</code> designed to resemble <code>JSliders</code>
 * using the Metal look-and-feel.
 *
 * @param <T> the parameter for the <code>MultiThumbSlider</code>.
 * 
 * <!-- ======== START OF AUTOGENERATED SAMPLES ======== -->
 * <p>Here are samples demonstrating different possible thumbs:
 * <table summary="Resource&#160;Samples&#160;for&#160;com.bric.plaf.MetalMultiThumbSliderUI"><tr>
 * <td>Thumb.Circle</td>
 * <td><img src="https://raw.githubusercontent.com/mickleness/pumpernickel/master/pump-release/resources/samples/MetalMultiThumbSliderUI/sample.png" alt="com.pump.plaf.DefaultMultiThumbSliderUI.createDemo(&#160;com.bric.plaf.MetalMultiThumbSliderUI.class,&#160;com.bric.plaf.MultiThumbSliderUI$Thumb.Circle&#160;)"></td>
 * </tr><tr>
 * <td>Thumb.Triangle</td>
 * <td><img src="https://raw.githubusercontent.com/mickleness/pumpernickel/master/pump-release/resources/samples/MetalMultiThumbSliderUI/sample2.png" alt="com.pump.plaf.DefaultMultiThumbSliderUI.createDemo(&#160;com.bric.plaf.MetalMultiThumbSliderUI.class,&#160;com.bric.plaf.MultiThumbSliderUI$Thumb.Triangle&#160;)"></td>
 * </tr><tr>
 * <td>Thumb.Rectangle</td>
 * <td><img src="https://raw.githubusercontent.com/mickleness/pumpernickel/master/pump-release/resources/samples/MetalMultiThumbSliderUI/sample3.png" alt="com.pump.plaf.DefaultMultiThumbSliderUI.createDemo(&#160;com.bric.plaf.MetalMultiThumbSliderUI.class,&#160;com.bric.plaf.MultiThumbSliderUI$Thumb.Rectangle&#160;)"></td>
 * </tr><tr>
 * <td>Thumb.Hourglass</td>
 * <td><img src="https://raw.githubusercontent.com/mickleness/pumpernickel/master/pump-release/resources/samples/MetalMultiThumbSliderUI/sample4.png" alt="com.pump.plaf.DefaultMultiThumbSliderUI.createDemo(&#160;com.bric.plaf.MetalMultiThumbSliderUI.class,&#160;com.bric.plaf.MultiThumbSliderUI$Thumb.Hourglass&#160;)"></td>
 * </tr><tr>
 * </tr></table>
 * <!-- ======== END OF AUTOGENERATED SAMPLES ======== -->
 */
@ResourceSample( 
		columnCount=1,
		tableIntroduction="Here are samples demonstrating different possible thumbs:",
		rowNames= {"Thumb.Circle", "Thumb.Triangle", "Thumb.Rectangle", "Thumb.Hourglass"},
		sample= {
				"com.pump.plaf.DefaultMultiThumbSliderUI.createDemo( com.bric.pump.MetalMultiThumbSliderUI.class, com.bric.pump.MultiThumbSliderUI$Thumb.Circle )",
				"com.pump.plaf.DefaultMultiThumbSliderUI.createDemo( com.bric.pump.MetalMultiThumbSliderUI.class, com.bric.pump.MultiThumbSliderUI$Thumb.Triangle )",
				"com.pump.plaf.DefaultMultiThumbSliderUI.createDemo( com.bric.pump.MetalMultiThumbSliderUI.class, com.bric.pump.MultiThumbSliderUI$Thumb.Rectangle )",
				"com.pump.plaf.DefaultMultiThumbSliderUI.createDemo( com.bric.pump.MetalMultiThumbSliderUI.class, com.bric.pump.MultiThumbSliderUI$Thumb.Hourglass )" })
public class MetalMultiThumbSliderUI<T> extends DefaultMultiThumbSliderUI<T> {

	public MetalMultiThumbSliderUI(MultiThumbSlider<T> slider) {
		super(slider);
		DEPTH = 5;
		FOCUS_PADDING = 0;
		trackHighlightColor = new Color(0x3a99fc);
	}

	
	/** @return true if Thumbs should be rendered with curved antialiasing. False if
	 * a crisp pixelated appearance is expected.
	 */
	protected boolean getThumbAntialiasing() {
		return false;
	}
	
	@Override
	protected int getPreferredComponentDepth() {
		return 22;
	}

	@Override
	protected void paintFocus(Graphics2D g) {
		//do nothing, this is really handled in paintThumb now
	}

	/** This optional method highlights the space on the track (by simply adding a shadow)
	 * between two thumbs.
	 * 
	 * @param g
	 */
	protected void paintTrackHighlight(Graphics2D g) {
		if(!isTrackHighlightActive()) return;
		g = (Graphics2D)g.create();
		Point2D p1 = getThumbCenter(0);
		Point2D p2 = getThumbCenter(1);
		Shape outline;
		if(slider.getOrientation()==MultiThumbSlider.HORIZONTAL) {
			float minX = (float)Math.min(p1.getX(), p2.getX());
			float maxX = (float)Math.max(p1.getX(), p2.getX());
			outline = new Rectangle2D.Float( minX, trackRect.y+1, maxX - minX, trackRect.height-1 );
			g.setPaint(new LinearGradientPaint(
					new Point(trackRect.x, trackRect.y+1),
					new Point(trackRect.x, trackRect.y+trackRect.height-2),
					new float[] { 0, .45f, 1},
					new Color[] {
						new Color(0xffffff),
						new Color(0xffffff),
						new Color(163,184,204)
					}));
		} else {
			float minY = (float)Math.min(p1.getY(), p2.getY());
			float maxY = (float)Math.max(p1.getY(), p2.getY());
			outline = new Rectangle2D.Float( trackRect.x+1, minY, trackRect.width-1, maxY - minY );
			g.setPaint(new LinearGradientPaint(
					new Point(trackRect.x+1, trackRect.y),
					new Point(trackRect.x+trackRect.width-2, trackRect.y),
					new float[] { 0, .45f, 1},
					new Color[] {
						new Color(0xffffff),
						new Color(0xffffff),
						new Color(163,184,204)}
					));
		}
		g.fill(outline);
		g.dispose();
	}

	@Override
	protected Dimension getThumbSize(int thumbIndex) {
		Thumb thumb = getThumb(thumbIndex);
		if(Thumb.Hourglass.equals(thumb)) {
			return new Dimension(8, 16);
		} else if(Thumb.Triangle.equals(thumb)) {
			return new Dimension(14, 14);
		} else if(Thumb.Rectangle.equals(thumb)) {
			return new Dimension(10, 20);
		} else {
			return new Dimension(16, 16);
		}
	}

	@Override
	protected void paintTrack(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		
		g.setStroke(new BasicStroke(1));
		g.setPaint(new Color(0xa3b8cc));
		if(slider.getOrientation()==SwingConstants.HORIZONTAL) {
			g.drawLine(trackRect.x, trackRect.y+1, trackRect.x+trackRect.width, trackRect.y+1);
		} else {
			g.drawLine(trackRect.x+1, trackRect.y, trackRect.x+1, trackRect.y+trackRect.height);
		}
		g.setPaint(new Color(99, 130, 191));
		g.drawRect(trackRect.x, trackRect.y, trackRect.width, trackRect.height);

		paintTrackHighlight(g);

		if(slider.isPaintTicks()) {
			g.setColor(new Color(0,0,0,140));
			g.setStroke(new BasicStroke(1));
			paintTick(g,.25f,3,8,false);
			paintTick(g,.5f,3,8,false);
			paintTick(g,.75f,3,8,false);
			paintTick(g,0f,3,8,false);
			paintTick(g,1f,3,8,false);
		}
	}

	@Override
	protected Shape getTrackOutline() {
		trackRect = calculateTrackRect();
		return trackRect;
	}
	
	@Override
	protected void paintThumb(Graphics2D g,int thumbIndex,float selected) {
		Shape outline = getThumbShape(thumbIndex);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);

		Rectangle2D thumbBounds = ShapeBounds.getBounds(outline);
		
		Paint fill;
		Paint strokePaint = new Color(0x333333);
		if(thumbIndex==slider.getSelectedThumb()) {
			fill = new LinearGradientPaint(
					new Point2D.Double(0,thumbBounds.getMinY()), 
					new Point2D.Double(0,thumbBounds.getMaxY()),
					new float[] {0, .5f, 1},
					new Color[] { 
						tween(new Color(0xc8ddf2), new Color(0x000000), .16f ),
						tween(new Color(0xffffff), new Color(0x000000), .16f ),
						tween(new Color(0xbcd2e8), new Color(0x000000), .16f ) });
		} else {
			fill = new LinearGradientPaint(
					new Point2D.Double(0,thumbBounds.getMinY()), 
					new Point2D.Double(0,thumbBounds.getMaxY()),
					new float[] {0, .5f, 1},
					new Color[] { 
						new Color(0xc8ddf2),
						new Color(0xffffff),
						new Color(0xbcd2e8) });
		}
		g.setPaint(fill);
		g.fill(outline);
		
		if(Thumb.Hourglass.equals(getThumb(thumbIndex))) {
			//add another coat to make the darkness more visible
			g.setPaint(new Color(0,0,0,(int)(selected*255)));
			g.fill(outline);
		}
		
		g.setStroke(new BasicStroke(1f));
		g.setPaint(strokePaint);
		g.draw(outline);
	}
	
	private static final Color tween(Color c1,Color c2,float f) {
		if(f<0) f = 0;
		if(f>1) f = 1;
		int r = (int)(c1.getRed()*(1-f)+f*c2.getRed());
		int g = (int)(c1.getGreen()*(1-f)+f*c2.getGreen());
		int b = (int)(c1.getBlue()*(1-f)+f*c2.getBlue());
		int a = (int)(c1.getAlpha()*(1-f)+f*c2.getAlpha());
		return new Color(r, g, b, a);
	}
}