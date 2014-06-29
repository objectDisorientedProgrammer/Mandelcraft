package voxelengine;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

@SuppressWarnings("serial")
public class SwingInterface extends JPanel {
	private static Keyboard keyboard = new Keyboard();
	private Image src = null;
	private World world;
	private Renderer renderer;
	private Camera camera = new Camera(0, 0, 0, 0, 0, 0, Math.PI/2, Math.PI/5);
	private static String[] arguments;
	
	private static final int X_SIZE = 800;
	private static final int Y_SIZE = 600;
	private static int pixelScale = 4;
	private static int castScale = 4;

	private static boolean doShadows = false;

	public SwingInterface() {
		new Worker().execute();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (src != null)
			g.drawImage(src, 0, 0, this);
	}
	
	private class Worker extends SwingWorker<Void, Image> {
		
		protected void process(List<Image> chunks) {
			for (Image bufferedImage : chunks) {
				src = bufferedImage;
				repaint();
			}
		}

		protected Void doInBackground() throws Exception {
			set_up();
			int frames = 0;
			long time = System.currentTimeMillis();

			long lastFrameNanos = System.nanoTime();

			while (true) {
				frames++;
				if (frames == 30) {
					double t = System.currentTimeMillis() - time;
					double fps = 30 / (t / 1000);
					System.out.println(((double) Math.round(fps * 100)/100) + "fps");
					frames = 0;
					time = System.currentTimeMillis();
				}
				process_input((System.nanoTime() - lastFrameNanos) / 1000000000.0);
				lastFrameNanos = System.nanoTime();
				Image img = createImage(renderer.renderG(X_SIZE, Y_SIZE, pixelScale, castScale, doShadows));
				BufferedImage buffer = new BufferedImage(X_SIZE, Y_SIZE, BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2 = buffer.createGraphics();
				g2.drawImage(img, 0, 0, null);
				g2.dispose();
				publish(buffer);
			}
		}

		private void set_up() {
			int worldSize = Integer.parseInt(arguments[0]);
			int offset = (worldSize * 16 - Integer.parseInt(arguments[3]))/2;
			world = new World(worldSize);
			world.addColorBlockTypes();
			world.addPalettes();
			long time = System.currentTimeMillis();
			if (arguments[1].equals("Menger Sponge")) {
				System.out.println("Generating Menger Sponge...");
				Byte b = (byte) world.getByteFromName(arguments[2]);
				world.generateMengerSponge(b, Integer.parseInt(arguments[3]), offset, offset, offset);
			} else if (arguments[1].equals("Mandelbulb")) {
				System.out.println("Generating Mandelbulb...");
				world.generateMandelbulb(arguments[2], Integer.parseInt(arguments[3]), Double.parseDouble(arguments[6]), Double.parseDouble(arguments[8]), offset, offset, offset, Integer.parseInt(arguments[4]), Integer.parseInt(arguments[5]));
			} else if (arguments[1].equals("Mandelbox")) {
				System.out.println("Generating Mandelbox...");
				world.generateMandelbox(arguments[2], Integer.parseInt(arguments[3]), Double.parseDouble(arguments[6]), Double.parseDouble(arguments[8]), Integer.parseInt(arguments[4]), Integer.parseInt(arguments[5]), Double.parseDouble(arguments[7]), offset, offset, offset);
			} else if (arguments[1].equals("Greek Cross")) {
				System.out.println("Generating Greek Cross Fractal...");
				Byte b = (byte) world.getByteFromName(arguments[2]);
				world.generateCross(b, Integer.parseInt(arguments[3]), offset, offset, offset, Integer.parseInt(arguments[6]), 0);
			} else if (arguments[1].equals("Octahedron")) {
				System.out.println("Generating Octahedron Fractal...");
				Byte b = (byte) world.getByteFromName(arguments[2]);
				world.generateOctahedron(b, Integer.parseInt(arguments[3]), offset, offset, offset, Integer.parseInt(arguments[6]));
			} else if (arguments[1].equals("Load")) {
				System.out.println("Loading an existing world...");
				world.load(arguments[6]);
			}
			System.out.println((Math.round((System.currentTimeMillis() - time)/10)/100.0)+" seconds spent generating world.");
			time = System.currentTimeMillis();
			world.updateIsEmptyAll();
			System.out.println((Math.round((System.currentTimeMillis() - time)/10)/100.0)+" seconds spent updating isEmpty booleans.");
			camera.x = (double) (offset - 2.0);
			camera.y = (double) (offset - 2.0);
			camera.z = (double) (offset - 2.0);
			camera.rotY = 0;
			camera.rotX = Math.PI / 2;
			renderer = new Renderer(world, camera);
			System.out.println("World set up.  Now rendering...");
		}

		private void process_input(double timestep) {
			final int movement_scale = 15;
			final int rotation_scale = 15;

			if (keyboard.isKeyDown('A')) {
				camera.x += timestep * movement_scale * Math.cos(camera.rotY - Math.PI / 2);
				camera.y += timestep * movement_scale * Math.sin(camera.rotY - Math.PI / 2);
			}
			if (keyboard.isKeyDown('D')) {
				camera.x += timestep * movement_scale * Math.cos(camera.rotY + Math.PI / 2);
				camera.y += timestep * movement_scale * Math.sin(camera.rotY + Math.PI / 2);
			}
			if (keyboard.isKeyDown('W')) {
				camera.x += timestep * movement_scale * Math.cos(camera.rotY);
				camera.y += timestep * movement_scale * Math.sin(camera.rotY);
			}
			if (keyboard.isKeyDown('S')) {
				camera.x += timestep * movement_scale * Math.cos(camera.rotY + Math.PI);
				camera.y += timestep * movement_scale * Math.sin(camera.rotY + Math.PI);
			}
			if (keyboard.isKeyDown('Q')) {
				camera.z -= timestep * movement_scale;
			}
			if (keyboard.isKeyDown('E')) {
				camera.z += timestep * movement_scale;
			}
			if (keyboard.isKeyDown('J')) {
				camera.rotY -= timestep * rotation_scale * Math.PI / 32;
			}
			if (keyboard.isKeyDown('L')) {
				camera.rotY += timestep * rotation_scale * Math.PI / 32;
			}
			if (keyboard.isKeyDown('I')) {
				camera.rotX += timestep * rotation_scale * Math.PI / 32;
			}
			if (keyboard.isKeyDown('K')) {
				camera.rotX -= timestep * rotation_scale * Math.PI / 32;
			}
			if (keyboard.isKeyDown('T')) {
				pixelScale++;
			}
			if (keyboard.isKeyDown('Y')) {
				pixelScale--;
			}
			if (keyboard.isKeyDown('G')) {
				castScale++;
			}
			if (keyboard.isKeyDown('H')) {
				castScale--;
			}
			if (keyboard.isKeyDown('P')) { // P is for pretty
				doShadows = true;
				pixelScale = 1;
			} else {
				doShadows = false;
				pixelScale = 4;
			}
			camera.rotY = camera.rotY % (Math.PI * 2);
			camera.rotX = Math.max(Math.min(camera.rotX, Math.PI), 0);
			pixelScale = Math.max(Math.min(pixelScale, 10), 1);
			castScale = Math.max(Math.min(castScale, 50), 1);
		}
	}
	
	public static void main(String[] args) {
		arguments = args;
		JFrame jf = new JFrame();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.addKeyListener(keyboard);
		jf.getContentPane().add(new SwingInterface(), BorderLayout.CENTER);
		jf.setSize(X_SIZE, Y_SIZE);
		jf.setVisible(true);
	}
}
