package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import voxelengine.SwingInterface;

public class MainWindow
{
	// main frame components
	private JFrame frame;	// main frame
	private JPanel panel;	// main panel
	
	private JPanel optionsPanel; // contains most GUI elements
	private final int rows = 0;		// for grid layout: 0 means any number of rows
	private final int columns = 2;	// 2 columns
	
	
	// image
	private BufferedImage bgImage;
	private int imageCounter = 1;
	private final int LAST_IMAGE = 32;
	private boolean running = true;
	private long threadSleepTime = 2000;	// 2 seconds (in ms)
	
	// labels
	private JLabel imageLbl;
	
	// textfields
	private JTextField worldSizeTF;
	private JTextField voxelCountTF;
	
	// comboboxes
	private JComboBox<String> worldChoice;
	private String[] worldOptions = {"Mandelbulb"};//, "Mandelbox"};
	
	private JComboBox<String> colorChoice;
	private String[] colorOptions = {"red", "orange", "yellow", "green", "blue", "magenta", "pink", "cyan", "white", "gray", "dark gray", "light gray"};
	//private Color[] colorOptions = {Color.red, Color.orange, Color.yellow, Color.green, Color.blue, Color.magenta, Color.pink,
			//Color.cyan, Color.white, Color.gray, Color.darkGray, Color.lightGray};
	
	// buttons
	private JButton generateBtn;
	
	
	public MainWindow()
	{
		frame = new JFrame("Voxel Engine");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		createUI();
		addUI();
		
		// Change the background image every 'threadSleepTime' seconds
		Thread bgImageChangeThread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					do
					{
						Thread.sleep(threadSleepTime);
						++imageCounter;
						if(imageCounter < LAST_IMAGE)
							imageLbl.setIcon(new ImageIcon(getClass().getResource("/" + imageCounter + ".jpg"))); // this path needs to change if images move
						else
							imageCounter = 0;
					} while(running);
				} catch(InterruptedException e)
				{
					System.out.println("MainWindow thread error:" + e.getMessage());
				}
			}
		});
		bgImageChangeThread.start();
		
		frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * Add components to panels and panels to frame.
	 */
	private void addUI()
	{
		panel.add(imageLbl, BorderLayout.CENTER);
		panel.add(optionsPanel, BorderLayout.SOUTH);
		
		optionsPanel.add(worldSizeTF);
		optionsPanel.add(voxelCountTF);
		optionsPanel.add(worldChoice);
		optionsPanel.add(colorChoice);
		optionsPanel.add(generateBtn);
		
		frame.add(panel);
	}
	
	/**
	 * Initialize panels and UI components.
	 */
	private void createUI()
	{
		panel = new JPanel(new BorderLayout());
		
		optionsPanel = new JPanel(new GridLayout(rows, columns));
		
		imageLbl = new JLabel(new ImageIcon(getClass().getResource("/"+ imageCounter + ".jpg"))); // this path needs to change if images move
		
		worldSizeTF = new JTextField(6);
		worldSizeTF.setToolTipText("World Size");
		
		voxelCountTF = new JTextField(6);
		voxelCountTF.setToolTipText("Size");
		
		worldChoice = new JComboBox<String>(worldOptions);
		
		colorChoice = new JComboBox<String>(colorOptions);
		
		generateBtn = new JButton("Generate World");
		generateBtn.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				running = false; // stop changing background images
				// create a new frame with the world
				SwingInterface.instance(new String[]{worldSizeTF.getText(), worldChoice.getSelectedItem().toString(), colorChoice.getSelectedItem().toString(),
						voxelCountTF.getText(), "8", "4", "4", "16", "5"});
			}
		});
	}

	// should move this to a Driver.java or something...
	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new MainWindow();
            }
        });
	}
}
