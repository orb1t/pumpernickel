/*
 * @(#)Dimension2DDouble.java
 *
 * $Date: 2015-01-24 02:24:41 -0500 (Sat, 24 Jan 2015) $
 *
 * Copyright (c) 2012 by Jeremy Wood.
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
package com.pump.awt;

import java.awt.Dimension;

public class Dimension2D extends java.awt.geom.Dimension2D {


	/** This is a convenience method to calculate how to scale down
	 * an image proportionally.
	 * 
	 * @param originalSize the original image dimensions.
	 * @param maxSize the maximum new dimensions.
	 * @return dimensions that are <code>maxSize</code> or smaller.
	 */
	public static Dimension scaleProportionally(Dimension originalSize,Dimension maxSize) {
		float widthRatio = ((float)maxSize.width)/((float)originalSize.width);
		float heightRatio = ((float)maxSize.height)/((float)originalSize.height);
		int w, h;
		if(widthRatio<heightRatio) {
			w = maxSize.width;
			h = (int)(widthRatio*originalSize.height);
		} else {
			h = maxSize.height;
			w = (int)(heightRatio*originalSize.width);
		}
		return new Dimension(w,h);
	}

	/** This is a convenience method to calculate how to scale down
	 * an image proportionally.
	 * 
	 * @param originalSize the original image dimensions.
	 * @param maxSize the maximum new dimensions.
	 * @return dimensions that are <code>maxSize</code> or smaller.
	 */
	public static java.awt.geom.Dimension2D scaleProportionally(java.awt.geom.Dimension2D originalSize,java.awt.geom.Dimension2D maxSize) {
		double widthRatio = maxSize.getWidth()/originalSize.getWidth();
		double heightRatio = maxSize.getHeight()/originalSize.getHeight();
		double w, h;
		if(widthRatio<heightRatio) {
			w = maxSize.getWidth();
			h = widthRatio*originalSize.getHeight();
		} else {
			h = maxSize.getHeight();
			w = heightRatio*originalSize.getWidth();
		}
		return new Dimension2D(w,h);
	}
	
	double width, height;

	public Dimension2D(Dimension d) {
		this(d.width, d.height);
	}
	
	public Dimension2D(double width,double height) {
		this.width = width;
		this.height = height;
	}
	
	@Override
	public String toString() {
		return "Dimension2D[width="+width+" height="+height+"]";
	}
	
	@Override
	public double getHeight() {
		return height;
	}

	@Override
	public double getWidth() {
		return width;
	}

	@Override
	public Object clone() {
		return new Dimension2D(width, height);
	}

	@Override
	public void setSize(double width, double height) {
		this.width = width;
		this.height = height;
	}

}
