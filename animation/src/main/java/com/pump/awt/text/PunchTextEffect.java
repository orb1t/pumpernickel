/*
 * @(#)PunchTextEffect.java
 *
 * $Date: 2016-01-30 19:07:08 -0500 (Sat, 30 Jan 2016) $
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
package com.pump.awt.text;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** This begins and ends in an undecorated state, and during the animation the
 * letters are punched for emphasis:
 * <p><img src="https://javagraphics.java.net/resources/punch-text-effect.gif" alt="punch text effect demo"></p>
 */
public class PunchTextEffect implements TextEffect {
	int width, height;
	Font font;
	String text;
	
	List<BlockLetter> blockLetters = new ArrayList<BlockLetter>();
	float textWidth = 0;
	float textHeight = 0;
	Color foreground = new Color(220, 0, 130);
	Color shadow = new Color(80, 0, 15);
	
	public PunchTextEffect(Font font,String text, int width,int height ) {
		this.font = font;
		this.text = text;
		this.width = width;
		this.height = height;
		
		int wordCount = 0;
		boolean lastCharWasWhiteSpace = true;
		// maps word index -> starting BlockLetter index
		Map<Integer, Integer> wordMap = new HashMap<>();
		
		FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);
		for(int a = 0; a<text.length(); a++) {
			char c = text.charAt(a);
			if(Character.isWhitespace(c)==false) {
				BlockLetter.Simple l = new BlockLetter.Simple(c, font, foreground);
				l.setBlockPaint( shadow );
				l.setAngle( (float)( 7*Math.PI/4 ) );
				l.put("x", new Float(textWidth));
				textHeight = Math.max(l.getDepth(), textHeight);
				if(lastCharWasWhiteSpace) {
					wordCount++;
					wordMap.put(new Integer(wordCount), new Integer( blockLetters.size() ));
					lastCharWasWhiteSpace = false;
				}
				blockLetters.add( l );
			} else {
				lastCharWasWhiteSpace = true;
			}
			Rectangle2D r = font.getStringBounds(c+"", frc);
			textWidth += (float)r.getWidth();
		}
		
		if(wordCount>1) {
			int ctr = 1;
			while(true) {
				Integer index = ((Integer)wordMap.get(new Integer(ctr)));
				if(index==null) return;
				
				float start = (index.floatValue())/(blockLetters.size());

				ctr++;
				
				Integer nextIndex = ((Integer)wordMap.get(new Integer(ctr)));
				if(nextIndex==null) {
					for(int a = index.intValue(); a<blockLetters.size(); a++) {
						BlockLetter l = (BlockLetter)blockLetters.get(a);
						l.put("start", new Float(start));
						l.put("end", new Float(1));
					}
				} else {
					float end = (nextIndex.floatValue())/(blockLetters.size());
					for(int a = index.intValue(); a<nextIndex.intValue(); a++) {
						BlockLetter l = (BlockLetter)blockLetters.get(a);
						l.put("start", new Float(start*.7f));
						l.put("end", new Float(end*.7f+.3f));
					}
				}
			}
		} else {
			for(int a = 0; a<blockLetters.size(); a++) {
				BlockLetter l = (BlockLetter)blockLetters.get(a);
				float x = ((Number)l.get("x")).floatValue();
				float start = x/textWidth*.7f;
				float end = (x+l.getCharWidth())/textWidth*.7f+.3f;
				l.put("start", new Float(start));
				l.put("end", new Float(end));
			}
		}
		
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	public void paint(Graphics2D g, float fraction) {
		for(int a = 0; a<blockLetters.size(); a++) {
			BlockLetter l = (BlockLetter)blockLetters.get(a);
			float x = ((Number)l.get("x")).floatValue();
			
			float start = ((Number)l.get("start")).floatValue();
			float end = ((Number)l.get("end")).floatValue();
			
			float k;
			if(fraction>=end) {
				k = 0;
			} else if(fraction>=start) {
				//make k range from [0,1]
				k = (fraction-start)/(end-start);
				//now make k an arc:
				k = -4*k*k+4*k;
			} else {
				k = 0;
			}
			l.setDepth( k*6 );
			
			l.paint(g, width/2-textWidth/2+x, height/2+textHeight/2);
		}
	}
}
