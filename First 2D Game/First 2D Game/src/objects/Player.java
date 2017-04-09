package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import framework.GameObject;
import framework.KeyInput;
import framework.ObjectId;
import framework.Texture;
import sorts.ShakerSort;
import window.Animation;
import window.Game;
import window.Handler;
import window.InsertionSort;

public class Player extends GameObject {
	private static int array[] = new int [5] ; 

	private float width = 48, height = 92;
	private final float MAX_SPEED = 20;

	private Handler handler;
	KeyInput key;

	Texture tex = Game.getInstance();
	private Animation playerWalk;

	private float gravity = 0.9f;

	public Player(float x, float y, Handler handler, ObjectId id) {
		super(x, y, id);
		this.handler = handler;

		

		playerWalk = new Animation(5, tex.player[1], tex.player[2], tex.player[3], tex.player[4], tex.player[5],
				tex.player[6]);
	}

	@SuppressWarnings("resource")
	public void tick(LinkedList<GameObject> object) {
	
		Scanner input = new Scanner(System.in) ; 
		int an ; 
		
		//QUESTION ONE 
		if(x > 3000 && x < 3007 && y < 400) {
			clearScreen() ; 
			System.out.println();
			
			System.out.println("What type of sort is this: ");
			System.out.println("1. Insertion\t\t3. Selection");
			System.out.println("2. Bubble\t\t4. Shell Sort");
			intialize(array) ; 
			printArray(array) ; 
			InsertionSort.sort(array) ; 
			System.out.print("Answer: ");
			an = input.nextInt() ; 
			if(an != 1) {
				x = 3002; 
			}
			else {
				x = 3008 ; 
				
				clearScreen() ; 
			}
			
			
	
		}
		if(x > 3663 && x < 3669) {
			clearScreen() ; 
			System.out.println();
			
			System.out.println("What type of sort is this: ");
			System.out.println("1. Insertion\t\t\t3. Selection");
			System.out.println("2. Shaker (Bubble)\t\t4. Shell Sort");
			intialize(array) ; 
			printArray(array) ; 
			ShakerSort.shakerSort(array) ; 
			System.out.println();
			System.out.print("Answer: ");
			an = input.nextInt() ; 
			if(an != 2) {
				
			}
			else {
				x = 3670 ; 
				clearScreen() ; 
			}
		
		}
		
		
		x += velX;
		y += velY;

		if (falling || jumping) {
			velY += gravity;

			if (velY > MAX_SPEED) {
				velY = MAX_SPEED;
			}

			Collision(object);
			playerWalk.runAnimation();
		}

		

	}

	// Method used to initialize any array passed
		public static int[] intialize(int array[]) {
			Random random = new Random();
			for (int i = 0; i < 5; i++) {

				array[i] = random.nextInt(50);

			}

			return array;
		}
	
	public static void printArray(int array[]) {
	
		for(int i = 0; i < array.length; i++) {
			System.out.print(array[i] + ", ");
		}
		
	}

	private void Collision(LinkedList<GameObject> object) {

		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			if (tempObject.getId() == ObjectId.Block) {

				// If player collides with TOP HALF
				if (getBoundsTop().intersects(tempObject.getBounds())) {
					y = tempObject.getY() + 32;
					velY = 0;

				}

				// If player collides with BOTTOM HALF
				if (getBounds().intersects(tempObject.getBounds())) {
					y = tempObject.getY() - height;
					velY = 0;
					falling = false;
					jumping = false;
				}

				// If player collides with RIGHT HALF
				if (getBoundsRight().intersects(tempObject.getBounds())) {
					x = tempObject.getX() - width;

				}
				// If player collides with Left HALF
				if (getBoundsLeft().intersects(tempObject.getBounds())) {
					x = tempObject.getX() + 34;

				}

				else
					falling = true;

			}
			
			
		}
	}
	
	public static void clearScreen() {
		for(int x = 0; x <= 15; x++) {
			System.out.println();
		}
	}

	public void render(Graphics g) {
		;
		// scaled (48, 96)
		g.setColor(Color.blue);

		if (velX != 0) {

			if (velX > 0) {
				playerWalk.drawAnimation(g, (int) x, (int) y, 48, 96);
			}
			if (velX < 0) {
				playerWalk.drawAnimation(g, (int) (x + width), (int) y, -48, 96);
			}
		} else {

			g.drawImage(tex.player[0], (int) x, (int) y, 48, 96, null);

		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int) ((int) x + (width / 2) - (width / 2) / 2), (int) ((int) y + (height / 2)),
				(int) width / 2, (int) height / 2);
	}

	public Rectangle getBoundsTop() {
		return new Rectangle((int) ((int) x + (width / 2) - (width / 2) / 2), (int) y, (int) width / 2,
				(int) height / 2);
	}

	public Rectangle getBoundsRight() {
		return new Rectangle((int) ((int) x + width - 5), (int) y + 5, (int) 5, (int) height - 10);
	}

	public Rectangle getBoundsLeft() {
		return new Rectangle((int) x, (int) y + 5, (int) 5, (int) height - 10);
	}

}
