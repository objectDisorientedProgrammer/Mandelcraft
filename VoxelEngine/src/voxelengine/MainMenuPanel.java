package voxelengine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MainMenuPanel extends JPanel
{

	private JButton generateWorld;
	private JButton settings;
	private JButton quit;

	public MainMenuPanel()
	{
		generateWorld = new JButton("Generate World");
		generateWorld.setMaximumSize(new Dimension(140, 40));
		generateWorld.setForeground(Color.LIGHT_GRAY);
		generateWorld.setBackground(Color.BLACK);
		settings = new JButton("Settings");
		settings.setMaximumSize(new Dimension(140, 40));
		settings.setForeground(Color.LIGHT_GRAY);
		settings.setBackground(Color.BLACK);
		quit = new JButton("Quit");
		quit.setMaximumSize(new Dimension(140, 40));
		quit.setForeground(Color.LIGHT_GRAY);
		quit.setBackground(Color.BLACK);
		/*generateWorld.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				clearMenu();
				generationMenu();
			}
		});
		settings.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				clearMenu();
				settingsMenu();
			}
		});
		quit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				clearMenu();
				System.exit(0);
			}
		});*/
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		this.add(Box.createRigidArea(new Dimension(0,170)));
		add(generateWorld, BorderLayout.CENTER);
		this.add(Box.createRigidArea(new Dimension(0,50)));
		add(settings, BorderLayout.CENTER);
		this.add(Box.createRigidArea(new Dimension(0,50)));
		add(quit, BorderLayout.CENTER);
	}
}
