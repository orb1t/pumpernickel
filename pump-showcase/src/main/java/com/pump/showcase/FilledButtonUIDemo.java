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
package com.pump.showcase;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.UIManager;

import com.pump.blog.Blurb;
import com.pump.blog.ResourceSample;
import com.pump.graphics.GraphicsWriterDebugger;
import com.pump.icon.GearIcon;
import com.pump.plaf.BevelButtonUI;
import com.pump.plaf.CapsuleButtonUI;
import com.pump.plaf.FilledButtonUI;
import com.pump.plaf.FilledComboBoxUI;
import com.pump.plaf.GradientButtonUI;
import com.pump.plaf.LabelCellRenderer;
import com.pump.plaf.PlasticButtonUI;
import com.pump.plaf.RecessedButtonUI;
import com.pump.plaf.RetroButtonUI;
import com.pump.plaf.RoundRectButtonUI;
import com.pump.plaf.ShimmerPaintUIEffect;
import com.pump.plaf.SquareButtonUI;
import com.pump.plaf.TexturedButtonUI;
import com.pump.plaf.VistaButtonUI;
import com.pump.plaf.XPButtonUI;
import com.pump.plaf.XPSubtleButtonUI;
import com.pump.plaf.ZoomIconPaintUIEffect;

/** A simple demo of different {@link FilledButtonUI} samples.
 * 
 * 
 * <!-- ======== START OF AUTOGENERATED SAMPLES ======== -->
 * <p><img src="https://raw.githubusercontent.com/mickleness/pumpernickel/master/pump-release/resources/samples/FilledButtonUIDemo/sample.png" alt="new&#160;com.bric.plaf.FilledButtonUIDemo()">
 * <!-- ======== END OF AUTOGENERATED SAMPLES ======== -->
 */
@Blurb (
		filename = "FilledButtonUI",
		title = "Buttons: New UIs",
		releaseDate = "August 2009",
		summary = "Sure the buttons in <a href=\"http://developer.apple.com/mac/library/technotes/tn2007/tn2196.html#BUTTONS\">Apple's Tech Note 2196</a> "+
				"are great, but they're so... black-box-ish. And they're only available on Macs.\n"+
				"<p>This article sets up a <a href=\"https://javagraphics.java.net/doc/com/bric/plaf/FilledButtonUI.html\">new framework</a> for "+
				"<code>ButtonUIs</code>, and provides around 10 UI's to choose from. "+
				"(But you can make more if you want to...) Also each <code>ButtonUI</code> can be turned into a <code>ComboBoxUI</code>.",
				instructions = "This applet demonstrates several new button UIs. Select a UI in the list on the left, "+
						"and interact with the the UIs on the right.",
						link = "http://javagraphics.blogspot.com/2009/08/buttons-new-uis.html",
						sandboxDemo = true
		)
@ResourceSample( sample="new com.bric.plaf.FilledButtonUIDemo()" )
public class FilledButtonUIDemo extends JPanel {
	private static final long serialVersionUID = 1L;
	private static Icon DEMO_ICON = new GearIcon(14, Color.darkGray);


	private static final String BUTTON_TEXT = "XYZ";

	private static void setFocusable(JComponent c,boolean b) {
		c.setFocusable(b);
		for(int a = 0; a<c.getComponentCount(); a++) {
			if(c.getComponent(a) instanceof JComponent)
				setFocusable( (JComponent)c.getComponent(a),b);
		}
	}
	private static void setOpaque(JComponent c,boolean b) {
		c.setOpaque(b);
		for(int a = 0; a<c.getComponentCount(); a++) {
			if(c.getComponent(a) instanceof JComponent)
				setOpaque( (JComponent)c.getComponent(a),b);
		}
	}
	List<AbstractButton> buttons = new ArrayList<AbstractButton>();
	JCheckBox iconCheckBox = new JCheckBox("Show Icons",true);
	JCheckBox contentCheckBox = new JCheckBox("Paint Content",true);
	JCheckBox borderCheckBox = new JCheckBox("Paint Border",true);
	JCheckBox focusCheckBox = new JCheckBox("Paint Focus",true);
	JCheckBox textCheckBox = new JCheckBox("Show Text",true);
	JCheckBox blinkCheckBox = new JCheckBox("Blink Focus",false);
	JCheckBox shimmerCheckBox = new JCheckBox("Shimmer Effect",false);

	JCheckBox zoomCheckBox = new JCheckBox("Zoom Icon Effect",false);

	JCheckBox enabledCheckBox = new JCheckBox("Enabled",true);

	JComboBox componentTypes = new JComboBox();
	
	Map<FilledButtonUI, JInternalFrame> uiFrameMap = new LinkedHashMap<>();

	protected Collection<JComponent> components = new HashSet<>();

	JDesktopPane desktop;

	JComboBox<FilledButtonUI> uiComboBox = new JComboBox<>();

	JPanel controlPanel = new JPanel(new GridBagLayout());

	List<JComponent> controls = new ArrayList<JComponent>();

	public FilledButtonUIDemo() {
		prepareInternalFrames(
				new BevelButtonUI(),
				new CapsuleButtonUI(),
				new GradientButtonUI(),
				new PlasticButtonUI(),
				new RecessedButtonUI(),
				new RetroButtonUI(),
				new RoundRectButtonUI(),
				new SquareButtonUI(),
				new TexturedButtonUI(),
				new VistaButtonUI(),
				new XPButtonUI(),
				new XPSubtleButtonUI());
		
		for(FilledButtonUI ui : uiFrameMap.keySet()) {
			uiComboBox.addItem(ui);
		}
		
		desktop = new JDesktopPane();

		Color bkgnd = desktop.getBackground();

		if(bkgnd.getRed()<30 && bkgnd.getBlue()<30 && bkgnd.getGreen()<30) {
			//on vista the bkgnd is black?  boo.
			desktop.setBackground(Color.white);
		}

		JComponent[] handlerControls = getControls();
		controls.add(uiComboBox);
		for(int b = 0; b<handlerControls.length; b++) {
			handlerControls[b].setFocusable(false);
			controls.add(handlerControls[b]);
		}

		setLayout(new GridBagLayout());
		uiComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateDemo();
			}
		});

		GridBagConstraints c = new GridBagConstraints();
		c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0;
		c.weightx = 1; c.weighty = 0;
		c.insets = new Insets(5,5,5,5);
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = GridBagConstraints.REMAINDER;
		add(controlPanel,c);
		c.gridy++; c.weighty = 1;
		add(desktop,c);

		uiComboBox.setRenderer(new LabelCellRenderer<FilledButtonUI>() {

			@Override
			protected void formatLabel(FilledButtonUI value) {
				label.setText( value.getClass().getSimpleName() );
			}
		});

		setOpaque(controlPanel,false);
		setFocusable(controlPanel,false);

		desktop.setPreferredSize(new Dimension(300,300));


		iconCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Icon icon = iconCheckBox.isSelected() ? DEMO_ICON : null;

				for(int a = 0; a<buttons.size(); a++) {
					(buttons.get(a) ).setIcon(icon);
				}
			}
		});
		textCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textCheckBox.isSelected() ? BUTTON_TEXT : "";

				for(int a = 0; a<buttons.size(); a++) {
					(buttons.get(a) ).setText(text);
				}
			}
		});

		blinkCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean blinking = blinkCheckBox.isSelected();
				for(int a = 0; a<buttons.size(); a++) {
					(buttons.get(a) ).putClientProperty("Focus.blink",
							new Boolean(blinking));
				}
			}
		});

		shimmerCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int a = 0; a<buttons.size(); a++) {
					AbstractButton button = (buttons.get(a) );
					if(shimmerCheckBox.isSelected()) {
						button.addMouseListener(ShimmerPaintUIEffect.mouseListener);
					} else {
						button.removeMouseListener(ShimmerPaintUIEffect.mouseListener);
					}
				}
			}
		});

		focusCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int a = 0; a<buttons.size(); a++) {
					AbstractButton button = (buttons.get(a) );
					button.setFocusPainted( focusCheckBox.isSelected() );
				}
			}
		});
		borderCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int a = 0; a<buttons.size(); a++) {
					AbstractButton button = (buttons.get(a) );
					button.setBorderPainted( borderCheckBox.isSelected() );
				}
			}
		});
		contentCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int a = 0; a<buttons.size(); a++) {
					AbstractButton button = (buttons.get(a) );
					button.setContentAreaFilled( contentCheckBox.isSelected() );
				}
			}
		});

		zoomCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int a = 0; a<buttons.size(); a++) {
					AbstractButton button = (buttons.get(a) );
					if(zoomCheckBox.isSelected()) {
						button.addActionListener(ZoomIconPaintUIEffect.actionListener);
					} else {
						button.removeActionListener(ZoomIconPaintUIEffect.actionListener);
					}
				}
			}
		});

		enabledCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(JComponent jc : components) {
					jc.setEnabled(enabledCheckBox.isSelected());
				}
			}
		});

		componentTypes.addItem(JButton.class);
		componentTypes.addItem(JToggleButton.class);

		componentTypes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateVisibility();
			}
		});

		componentTypes.setRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList list,
					Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Class<?> type = (Class<?>)value;
				String name = type.getName();
				name = name.substring(name.lastIndexOf('.')+1);

				return super.getListCellRendererComponent(list, name, index, isSelected,
						cellHasFocus);
			}
		});

		updateVisibility();

		updateDemo();
	}

	protected JComboBox createComboBox(FilledButtonUI fui,String name,boolean isPopDown,boolean showSeparators) {
		JComboBox comboBox = new JComboBox();
		comboBox.putClientProperty(FilledButtonUI.SHOW_SEPARATORS, showSeparators);
		comboBox.putClientProperty(FilledComboBoxUI.IS_POP_DOWN_KEY, isPopDown);
		comboBox.setUI(fui.createComboBoxUI());
		comboBox.addItem("Item 1");
		comboBox.addItem("Item 2");
		comboBox.addItem("Item 3");
		String tooltip = "This is a demo of \""+name+"\" applied to a JComboBox";
		if(isPopDown) {
			tooltip += " (pop down)";
		} else {
			tooltip += " (pop up)";
		}
		if(showSeparators) {
			tooltip += " showing separators.";
		} else {
			tooltip +=".";
		}
		comboBox.setToolTipText(tooltip);
		return comboBox;
	}
	public JComponent[] getControls() {
		return new JComponent[] {
				iconCheckBox, contentCheckBox,
				borderCheckBox, focusCheckBox,
				textCheckBox, blinkCheckBox,
				shimmerCheckBox, zoomCheckBox,
				enabledCheckBox, componentTypes
		};
	}
	
	public JPanel makeDemoPanel(FilledButtonUI fui) {

		String name = fui.getClass().getName();
		name = name.substring(name.lastIndexOf('.')+1);

		JPanel motherPanel = new JPanel(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		JPanel segmentPanel = new JPanel(new GridBagLayout());
		String[] hPos = new String[] {"first", "middle", "last", "only"};
		String[] vPos = new String[] {"top", "middle", "bottom", "only"};

		c.weightx = 1; c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;

		for(int h = 0; h<hPos.length; h++) {
			for(int v = 0; v<vPos.length; v++) {
				String toolTip = "this is a demo of \""+name+"\".";

				AbstractButton button1 = new JButton(BUTTON_TEXT);
				AbstractButton button2 = new JToggleButton(BUTTON_TEXT);

				for(int a = 0; a<2; a++) {
					AbstractButton button = (a==0) ? button1 : button2;
					button.setOpaque(false);
					button.setIcon(DEMO_ICON);
					button.putClientProperty("JButton.segmentHorizontalPosition", hPos[h]);
					button.putClientProperty("JButton.segmentVerticalPosition", vPos[v]);
					button.setUI( fui );
					button.setFont(UIManager.getFont("IconButton.font")); //miniature-ish
					buttons.add(button);
					button.setToolTipText(toolTip);
				}

				c.gridx = h; c.gridy = v;

				segmentPanel.add(button1,c);
				segmentPanel.add(button2,c);
			}
		}

		JPanel shapesPanel = new JPanel(new GridBagLayout());
		c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0;
		c.weightx = 1; c.weighty = 1;
		c.fill = GridBagConstraints.NONE;

		for(int z = 0; z<3; z++) {

			AbstractButton button1 = new JButton(BUTTON_TEXT);
			AbstractButton button2 = new JToggleButton(BUTTON_TEXT);
			buttons.add(button1);
			buttons.add(button2);

			String toolTip = "this is a demo of \""+name+"\"";

			Shape shape = null;
			if(z==0) {
				shape = new Ellipse2D.Float(0,0,20,20);
				toolTip = toolTip+" using an ellipse.";
			} else if(z==1) {
				GeneralPath diamond = new GeneralPath();
				diamond.moveTo(0, 0);
				diamond.lineTo(10, 10);
				diamond.lineTo(0, 20);
				diamond.lineTo(-10, 10);
				diamond.closePath();
				shape = diamond;
				toolTip = toolTip+" using a diamond.";
			} else {
				GeneralPath arrow = new GeneralPath();
				arrow.moveTo(0, -5);
				arrow.lineTo(20, -5);
				arrow.lineTo(20, -10);
				arrow.lineTo(30, 0);
				arrow.lineTo(20, 10);
				arrow.lineTo(20, 5);
				arrow.lineTo(0, 5);
				arrow.closePath();
				shape = arrow;
				toolTip = toolTip+" using an arrow.";
			}
			button1.putClientProperty(FilledButtonUI.SHAPE, shape);
			button2.putClientProperty(FilledButtonUI.SHAPE, shape);

			for(int a = 0; a<2; a++) {
				AbstractButton button = (a==0) ? button1 : button2;
				button.setOpaque(false);
				button.setIcon(DEMO_ICON);
				button.setUI( fui );
				button.setFont(UIManager.getFont("IconButton.font")); //miniature-ish
				button.setToolTipText(toolTip);
			}

			c.gridx = z; c.gridy = 0;

			shapesPanel.add(button1,c);
			shapesPanel.add(button2,c);
		}
		
		components.addAll(buttons);

		JPanel otherPanel = new JPanel(new GridBagLayout());
		c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0;
		c.weightx = 1; c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		JComboBox comboBox1 = createComboBox(fui, name, false, false);
		JComboBox comboBox2 = createComboBox(fui, name, true, false);
		JComboBox comboBox3 = createComboBox(fui, name, false, true);
		otherPanel.add( comboBox1, c);
		c.gridx++; c.insets = new Insets(0,4,0,4);
		otherPanel.add( comboBox2, c);
		c.gridx++; c.insets = new Insets(0,0,0,0);
		otherPanel.add( comboBox3, c);
		
		components.add(comboBox1);
		components.add(comboBox2);
		components.add(comboBox3);

		c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0;
		c.weightx = 1; c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		motherPanel.add(segmentPanel, c);

		c.gridy++; c.weighty = 0; 
		c.fill = GridBagConstraints.HORIZONTAL;
		motherPanel.add(shapesPanel, c);

		c.gridy++; c.weighty = 1; 
		c.fill = GridBagConstraints.BOTH;
		motherPanel.add(otherPanel, c);

		segmentPanel.setOpaque(false);
		shapesPanel.setOpaque(false);
		motherPanel.setOpaque(false);
		otherPanel.setOpaque(false);
		return motherPanel;
	}

	private void prepareInternalFrames(FilledButtonUI... uis) {
		for(FilledButtonUI ui : uis) {
			String name = ui.getClass().getSimpleName();
			JInternalFrame frame = new JInternalFrame(name, true, false);

			frame.setVisible(true);
			frame.getContentPane().setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0; c.gridy = 0; c.weightx = 1; c.weighty = 1;
			c.fill = GridBagConstraints.BOTH;
			c.insets = new Insets(10,10,10,10);
			JComponent panel = makeDemoPanel(ui);
			frame.getContentPane().add(panel, c);
			frame.pack();
			
			uiFrameMap.put(ui, frame);
		}
	}

	private void updateDemo() {
		FilledButtonUI ui = (FilledButtonUI) uiComboBox.getSelectedItem();

		desktop.removeAll();
		JInternalFrame internalFrame = uiFrameMap.get(ui);
		desktop.add(internalFrame);
		desktop.setSelectedFrame(internalFrame);
		desktop.repaint();

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; c.weightx = 1; c.weighty = 1;
		c.insets = new Insets(3,3,3,3);
		c.anchor = GridBagConstraints.WEST;
		int width = 0;
		for(int a = 0; a<controls.size(); a++) {
			JComponent control = controls.get(a);
			int thisWidth = control.getPreferredSize().width+4;
			if(control.isVisible()) {
				if(width+thisWidth>400) {
					width = 0;
					c.gridx = 0;
					c.gridy++;
				}
				controlPanel.add(control,c);
				width += thisWidth;
				c.gridx++;
				if(c.gridx==4) {
					width = 0;
					c.gridx = 0;
					c.gridy++;
				}
			}
		}
		setOpaque(controlPanel,false);
	}

	protected void updateVisibility() {
		Class<?> type = (Class<?>)componentTypes.getSelectedItem();
		if(type!=null) {
			for(int a = 0; a<buttons.size(); a++) {
				Component c = buttons.get(a);
				c.setVisible( c.getClass().equals(type) );
			}
		}
	}
}