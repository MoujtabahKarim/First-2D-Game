package objects;


import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import framework.GameObject;
import framework.ObjectId;
import framework.Texture;
import window.Game;

public class Block extends GameObject {
	
	Texture tex = Game.getInstance() ; 
	
	private int type ; 
	


	public Block(float x, float y, int type,  ObjectId id) {
		super(x, y, id);
		this.type = type ; 
		
	}

	@Override
	public void tick(LinkedList<GameObject> object) {
		
		 
		 
		 
		
		
	}

	@Override
	public void render(Graphics g) {
		
	
		
		//Dirt block
		if(type == 0) {
			g.drawImage(tex.block[0], (int)x, (int)y, null) ; 
		}
		//Grass block
		if(type == 1) {
			g.drawImage(tex.block[1], (int)x, (int)y, null) ; 
		}
		
		
		
	}

	public Rectangle getBounds() {
		
		return new Rectangle((int)x, (int)y, 32, 32);
	}



}
