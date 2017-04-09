package window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

import framework.KeyInput;
import framework.ObjectId;
import framework.Texture;
import objects.Block;
import objects.Player;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	private boolean running = false;
	private Thread thread;

	public static int WIDTH, HEIGHT;

	private BufferedImage level = null;
	private BufferedImage clouds = null;
	private BufferedImage sign = null;

	private BufferedImage man = null;

	// Object
	Handler handler;
	Camera cam;
	static Texture tex;
	static Player player;

	Random rand = new Random();

	private void init() {
		WIDTH = getWidth();
		HEIGHT = getHeight();

		tex = new Texture();

		BufferedImageLoader loader = new BufferedImageLoader();
		man = loader.loadImage("/man.jpg");
		level = loader.loadImage("/level.png"); // loading the level
		sign = loader.loadImage("/sign.png");
		clouds = loader.loadImage("/clouds.png");

		handler = new Handler();

		cam = new Camera(0, 0);

		// handler.addObject(new Player(100,100, handler , ObjectId.Player));
		// handler.createLeve();
		LoadImageLevel(level);
		this.addKeyListener(new KeyInput(handler));
	}

	public synchronized void start() {
		if (running) {
			return;
		}

		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public void run() {
		init();
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			render();
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
			}
		}
	}

	private void tick() {
		handler.tick();
		for (int i = 0; i < handler.object.size(); i++) {
			if (handler.object.get(i).getId() == ObjectId.Player) {
				cam.tick(handler.object.get(i));
			}
		}

	}

	private void render() {
		// Graphical stuff
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		////
		// Draw Here

		// g.drawImage(man, 0,0,this) ;
		g.setColor(new Color(25, 191, 224));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(man, 0, 0, WIDTH, HEIGHT, this);

		g2d.translate(cam.getX(), cam.getY()); // start cam

		for (int xx = 0; xx < clouds.getWidth() * 4; xx += clouds.getWidth()) {
			g.drawImage(clouds, xx + 100, 50, 800, 300, this);
			if (xx == 3 * clouds.getWidth()) {
				g.drawImage(sign, xx - 170, 340, 200, 100, this);
			}

		}

		handler.render(g);

		g2d.translate(-cam.getX(), cam.getY()); // end of cam
		///

		g.dispose();
		bs.show();

	}

	private void LoadImageLevel(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();

		// Loops through every pixel of image
		for (int xx = 0; xx < h; xx++) {
			for (int yy = 0; yy < w; yy++) {
				int pixel = image.getRGB(xx, yy);
				int red = (pixel >> 16) & 0xff; // bit operator
				int green = (pixel >> 8) & 0xff; // bit operator
				int blue = (pixel) & 0xff; /// bit operator

				if (red == 255 && green == 255 && blue == 255)
					handler.addObject(new Block(xx * 32, yy * 32, 0, ObjectId.Block));
				if (red == 0 && green == 255 && blue == 8)
					handler.addObject(new Block(xx * 32, yy * 32, 1, ObjectId.Block));
				if (red == 0 && green == 0 && blue == 255)
					handler.addObject(new Player(xx * 32, yy * 32, handler, ObjectId.Player));

			}
		}

	}

	public static Texture getInstance() {
		return tex;
	}

	@SuppressWarnings("resource")
	public static void main(String args[]) throws IOException {

		File file = new File("test.txt");
		Scanner read = new Scanner(file);
		while (read.hasNextLine()) {
			String line = read.nextLine();
			System.out.println("Last Played: " + line);

		}

		PrintWriter writer = new PrintWriter("test.txt", "UTF-8");
		Scanner input = new Scanner(System.in);
		System.out.print("What's your name: ");
		writer.println(input.nextLine());
		writer.close();

		new Window(800, 600, "4U Summative Game", new Game());

	}

}
