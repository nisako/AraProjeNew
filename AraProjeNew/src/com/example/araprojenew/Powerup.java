package com.example.araprojenew;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
public class Powerup extends Sprite
{
	private Plane plane;
	private powerupType pType;
	private PlaneEnemy planeEnemy;
	public Powerup(float pX, float pY,Plane mPlane,PlaneEnemy planeEnemy,powerupType mpType,ITextureRegion pTextureRegion){
		
		super(pX, pY, pTextureRegion, ResourcesManager.getInstance().vbom);
		this.plane = mPlane;
		this.pType = mpType;
		this.planeEnemy = planeEnemy;
	}
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
		if (this.collidesWith(planeEnemy))
        {
			planeEnemy.aplyPowerup(pType);
            this.setVisible(false);
            this.setIgnoreUpdate(true);
        }
		else if (this.collidesWith(plane))
        {
			plane.aplyPowerup(pType);
            this.setVisible(false);
            this.setIgnoreUpdate(true);
        }
	}
	public powerupType getType(){
		return pType;
	}
	public void remove() {
		this.setVisible(false);
        this.setIgnoreUpdate(true);
	}
}