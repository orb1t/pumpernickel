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
package com.pump.image.transition;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import com.pump.geom.Clipper;
import com.pump.geom.RectangularTransform;
import com.pump.geom.ShapeStringUtils;

/** This is an instruction to render an image.
 */
public class ImageInstruction extends Transition2DInstruction {
	/** If this is true, then this instruction relates to Frame A.  Otherwise this relates to Frame B. */
	public boolean isFirstFrame = true;

	/** This is the clipping to apply.  This may be null, indicating that clipping is not needed.  */
	public Shape clipping = null;

	/** This is the transform to render this graphic through.  This may be null, indicating that no transform is needed.  */
	public AffineTransform transform = null;
	
	/** This is the opacity of this image.  By default this value is 1. */
	public float opacity = 1;
		
	public ImageInstruction(boolean isFirstFrame,float opacity,AffineTransform transform,Shape clipping) {
		this(isFirstFrame,transform,clipping);
		this.opacity = opacity;
	}
	
	public ImageInstruction(boolean isFirstFrame,float opacity) {
		this(isFirstFrame);
		this.opacity = opacity;
	}
	
	public ImageInstruction(boolean isFirstFrame,float opacity,Rectangle2D dest,Dimension frameSize,Shape clipping) {
		this(isFirstFrame,opacity,RectangularTransform.create(new Rectangle2D.Double(0,0,frameSize.width,frameSize.height),dest),clipping);
	}
	
	/** Renders a completely opaque image, anchored at (0,0), at its original
	 * size with no clipping.
	 * @param isFirstFrame indicates whether to use the original image or
	 * the incoming image.
	 */
	public ImageInstruction(boolean isFirstFrame) {
		this(isFirstFrame,null,null);
	}
	
	@Override
	public String toString() {
		String clippingString = (clipping==null) ? "null" : ShapeStringUtils.toString(clipping);
		return "ImageInstruction[ isFirstFrame = "+isFirstFrame+", transform = "+transform+", clipping = "+clippingString+" opacity="+opacity+"]";
	}
	
	/** Creates a shallow clone of the argument */
	public ImageInstruction(ImageInstruction i) {
		this.clipping = i.clipping;
		this.isFirstFrame = i.isFirstFrame;
		this.transform = i.transform;
		this.opacity = i.opacity;
	}
	
	public ImageInstruction(boolean isFirstFrame,AffineTransform transform,Shape clipping) {
		this.isFirstFrame = isFirstFrame;
		if(transform!=null)
			this.transform = new AffineTransform(transform);
		if(clipping!=null) {
			if(clipping instanceof Rectangle) {
				this.clipping = new Rectangle((Rectangle)clipping);
			} else if(clipping instanceof Rectangle2D) {
				Rectangle2D r = new Rectangle2D.Float();
				r.setFrame((Rectangle2D)clipping);
				this.clipping = r;
			} else {
				this.clipping = new GeneralPath(clipping);
			}
		}
	}
	
	public ImageInstruction(boolean isFirstFrame,Rectangle2D dest,Dimension frameSize,Shape clipping) {
		this(isFirstFrame,RectangularTransform.create(new Rectangle2D.Double(0,0,frameSize.width,frameSize.height),dest),clipping);
	}

	@Override
	public void paint(Graphics2D g, BufferedImage frameA, BufferedImage frameB) {
		BufferedImage img = isFirstFrame ? frameA : frameB;
		
		Composite oldComposite = null;
		if(opacity!=1) {
			oldComposite = g.getComposite();
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,opacity));
		}

		Shape oldClipping = null;
		if(clipping!=null) {
			oldClipping = g.getClip();
			Clipper.clip(g,clipping);
		}
		
		g.drawImage(img,transform,null);
		
		if(clipping!=null)
			g.setClip(oldClipping);
		
		if(opacity!=1) {
			g.setComposite(oldComposite);
		}
	}
}