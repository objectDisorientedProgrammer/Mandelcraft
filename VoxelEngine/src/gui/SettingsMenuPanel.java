package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class SettingsMenuPanel extends JPanel
{
	private JButton back;
	private MainWindow refFrame;

	public SettingsMenuPanel(MainWindow mainWindow)
	{
		System.out.println("Created SettingsMenuPanel");
		refFrame = mainWindow;
		
		back = new JButton("Back");
		back.setMaximumSize(new Dimension(140, 40));
		back.setForeground(Color.LIGHT_GRAY);
		back.setBackground(Color.BLACK);
		back.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				refFrame.setPanel(new MainMenuPanel(refFrame));
			}
		});

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		this.add(Box.createRigidArea(new Dimension(0,250)));
		add(back, BorderLayout.CENTER);
	}
}
