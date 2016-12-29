/*
 * @(#)NavigationButtons.java
 *
 * $Date: 2015-09-21 01:24:13 -0400 (Mon, 21 Sep 2015) $
 *
 * Copyright (c) 2011 by Jeremy Wood.
 * All rights reserved.
 *
 * The copyright of this software is owned by Jeremy Wood. 
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * Jeremy Wood. For details see accompanying license terms.
 * 
 * This software is probably, but not necessarily, discussed here:
 * https://javagraphics.java.net/
 * 
 * That site should also contain the most recent official version
 * of this software.  (See the SVN repository for more details.)
 */
package com.pump.swing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.pump.plaf.TexturedButtonUI;

/** Static methods to create navigation buttons.
 * It is assumed these buttons will be placed adjacent to each other.
 * 
 */
public class NavigationButtons {
	private static final boolean isMac = System.getProperty("os.name").toLowerCase().indexOf("mac")!=-1;
	
	public static void formatPrev(AbstractButton button) {
		button.setIcon(createIcon(false, .75f));
		button.setRolloverIcon(createIcon(false, .85f));
		button.setSelectedIcon(createIcon(false, 1f));
		button.setDisabledIcon(createIcon(false, .3f));
		button.setUI(new TexturedButtonUI());
		button.setContentAreaFilled(true);
		button.putClientProperty("JButton.segmentPosition", "first");
		button.setBorderPainted(true);
	}
	
	public static void formatNext(AbstractButton button) {
		button.setIcon(createIcon(true, .75f));
		button.setRolloverIcon(createIcon(true, .85f));
		button.setSelectedIcon(createIcon(true, 1f));
		button.setDisabledIcon(createIcon(true, .3f));
		button.setUI(new TexturedButtonUI());
		button.setContentAreaFilled(true);
		button.putClientProperty("JButton.segmentPosition", "last");
		button.setBorderPainted(true);
	}
	
	public static JButton createPrev() {
		JButton b = new JButton();
		formatPrev(b);
		return b;
	}
	public static JButton createNext() {
		JButton b = new JButton();
		formatNext(b);
		return b;
	}
	
	private static ImageIcon createIcon(boolean flip,float opacity) {
		BufferedImage bi = new BufferedImage(10,10, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bi.createGraphics();
		GeneralPath path = new GeneralPath();
		int x = bi.getWidth()/2;
		int y = bi.getHeight()/2;
		if(flip) {
			path.moveTo(x-4,y-4);
			path.lineTo(x+4,y);
			path.lineTo(x-4,y+4);
		} else {
			path.moveTo(x+4,y+4);
			path.lineTo(x-4,y);
			path.lineTo(x+4,y-4);
		}
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		float dy = .6f;
		g.translate(0,dy);
		g.setColor(Color.white);
		g.fill(path);
		g.translate(0,-dy);
		int gray = (int)(255*(1-opacity));
		g.setColor(new Color(gray,gray,gray));
		g.fill(path);
		g.dispose();
		return new ImageIcon(bi);
	}
}