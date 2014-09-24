package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainMenuPanel extends JPanel
{
	private JButton generateWorld;
	
	public MainMenuPanel()
	{
		System.out.println("Created MainMenuPanel");
		
		generateWorld = new JButton("Generate World");
		generateWorld.setMaximumSize(new Dimension(140, 40));
		generateWorld.setForeground(Color.LIGHT_GRAY);
		generateWorld.setBackground(Color.BLACK);
		
		/*generateWorld.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				clearMenu();
				generationMenu();
			}
		});*/
		
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		this.add(Box.createRigidArea(new Dimension(0,170)));
		add(generateWorld, BorderLayout.CENTER);
		this.add(Box.createRigidArea(new Dimension(0,50)));
	}
}
