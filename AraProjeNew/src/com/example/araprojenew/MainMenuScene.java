package com.example.araprojenew;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.modifier.CardinalSplineMoveModifier.CardinalSplineMoveModifierConfig;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.modifier.CardinalSplineMoveModifier;
import org.andengine.entity.modifier.CardinalSplineMoveModifier.CardinalSplineMoveModifierConfig;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.opengl.util.GLState;
import org.andengine.util.modifier.ease.EaseLinear;

import android.graphics.Path;

import com.example.araprojenew.SceneManager.SceneType;


public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener {
	private MenuScene menuChildScene,optionsMenuChildScene,preGameMenuChildScene,playMenuChildScene,aboutMenuChildScene,howMenuChildScene;
	private final int ITEM_WIDTH = 125;
	private final int MENU_PLAY = 0;
	private final int MENU_OPTIONS = 1;
	private final int MENU_AUDIO = 2;
	private final int MENU_MUSIC = 3;
	private final int MENU_PRACTICE = 4;
	private final int MENU_HOST = 5;
	private final int MENU_JOIN = 6;
	private final int MENU_ABOUT = 7;
	private final int MENU_HOW = 8;
	
	private static final float[] CONTROLPOINT_1_XS = {
		200,
		400,
		200,
		20,
		200
	};
	private static final float[] CONTROLPOINT_YS = {
		20,
		200,
		420,
		200,
		20
	};
	
	public static int selected_plane=0; //tasar�m facias� oldu�unu biliyorum ama daha iyi bi ��z�m bulamad�m
	
	@Override
	public void createScene() {
		createBackground();
		createMenuChildScene();
		createOptionsMenuChildScene();
		createPlayMenuChildScene();
		createAboutMenuChildScene();
		createHowMenuChildScene();
		createPreGamesMenuChildScene();
	}
	
	

	@Override
	public void onBackKeyPressed() {
		//TODO zaten men�de ise back'e bast���nda ��kabilir
		if(getChildScene()==optionsMenuChildScene)setChildScene(menuChildScene);
		else if(getChildScene()==playMenuChildScene)setChildScene(preGameMenuChildScene);
		else if(getChildScene()==preGameMenuChildScene)setChildScene(menuChildScene);
		else if(getChildScene()==aboutMenuChildScene)setChildScene(menuChildScene);
		else if(getChildScene()==howMenuChildScene)setChildScene(menuChildScene);
		
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_MENU;
	}

	@Override
	public void disposeScene() {
		// TODO bo� dispose gene
	}
	
	//setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
	private void createBackground()
	{
	    setBackground(new SpriteBackground(new Sprite(0, 0, ResourcesManager.getInstance().bacground_region, vbom){
	    protected void preDraw(final GLState pGLState, final Camera pCamera)
	    {
	        super.preDraw(pGLState, pCamera);
	        pGLState.enableDither();
	    }
	}
	    		));
	    final CardinalSplineMoveModifierConfig catmullRomMoveModifierConfig1 = new CardinalSplineMoveModifierConfig(MainMenuScene.CONTROLPOINT_1_XS.length, 1);
	    AnimatedSprite menuplane1 = new AnimatedSprite(20, 20, ResourcesManager.getInstance().plane_regions[0], vbom);
	    AnimatedSprite menuplane2 = new AnimatedSprite(20, 420, ResourcesManager.getInstance().plane_regions[6], vbom);
	    menuplane1.animate(1);
	    menuplane2.animate(1);
	    attachChild(menuplane1);
	    attachChild(menuplane2);
	    for(int i = 0; i < MainMenuScene.CONTROLPOINT_1_XS.length; i++) {
			catmullRomMoveModifierConfig1.setControlPoint(i, MainMenuScene.CONTROLPOINT_1_XS[i] - 25 / 2, MainMenuScene.CONTROLPOINT_YS[i] - 25 / 2);
			
		}
	  
	    menuplane1.registerEntityModifier(new LoopEntityModifier( new ParallelEntityModifier(
				new CardinalSplineMoveModifier(4, catmullRomMoveModifierConfig1, EaseLinear.getInstance()),
				new RotationModifier(4, 0, 90)
			)));
	}
	
	private void createMenuChildScene()
	{
	    menuChildScene = new MenuScene(camera);
	    menuChildScene.setPosition(400, 240);
	    
	    final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, resourcesManager.play_region, vbom), 1.2f, 1);
	    final IMenuItem optionsMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_OPTIONS, resourcesManager.options_region, vbom), 1.2f, 1);
	    final IMenuItem aboutMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_ABOUT, resourcesManager.about_region, vbom), 1.2f, 1);
	    final IMenuItem howMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_HOW, resourcesManager.howtoplay_region, vbom), 1.2f, 1);
	    
	    
	    menuChildScene.addMenuItem(playMenuItem);
	    menuChildScene.addMenuItem(optionsMenuItem);
	    menuChildScene.addMenuItem(aboutMenuItem);
	    menuChildScene.addMenuItem(howMenuItem);
	    
	    menuChildScene.buildAnimations();
	    menuChildScene.setBackgroundEnabled(false);
	    
	    playMenuItem.setPosition(ITEM_WIDTH, -200);
	    optionsMenuItem.setPosition(ITEM_WIDTH, -100);
	    howMenuItem.setPosition(ITEM_WIDTH, 0);
	    aboutMenuItem.setPosition(ITEM_WIDTH, +100);
	    
	    
	    menuChildScene.setOnMenuItemClickListener(this);
	    
	    setChildScene(menuChildScene);
	}
	
	private void createPlayMenuChildScene() 
	{
			playMenuChildScene = new MenuScene(camera);
			playMenuChildScene.setPosition(400, 240);
			final IMenuItem practiceMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PRACTICE, resourcesManager.practice_region, vbom), 1.2f, 1);
		    final IMenuItem hostMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_HOST, resourcesManager.host_region, vbom), 1.2f, 1);
		    final IMenuItem joinMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_JOIN, resourcesManager.join_region, vbom), 1.2f, 1);
	
		    playMenuChildScene.addMenuItem(practiceMenuItem);
		    playMenuChildScene.addMenuItem(hostMenuItem);
		    playMenuChildScene.addMenuItem(joinMenuItem);
		    
		    playMenuChildScene.buildAnimations();
		    playMenuChildScene.setBackgroundEnabled(false);
		    
		    practiceMenuItem.setPosition(ITEM_WIDTH, -170);
		    hostMenuItem.setPosition(ITEM_WIDTH, - 50);
		    joinMenuItem.setPosition(ITEM_WIDTH, +70);
		    
		    playMenuChildScene.setOnMenuItemClickListener(this);
	}
	
	public void createOptionsMenuChildScene()
	{
		optionsMenuChildScene = new MenuScene(camera);
		optionsMenuChildScene.setPosition(400, 240);
		final IMenuItem audioMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_AUDIO, resourcesManager.audio_region, vbom), 1.2f, 1);
	    final IMenuItem musicMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_MUSIC, resourcesManager.music_region, vbom), 1.2f, 1);
	    
	    optionsMenuChildScene.addMenuItem(audioMenuItem);
	    optionsMenuChildScene.addMenuItem(musicMenuItem);
	    
	    optionsMenuChildScene.buildAnimations();
	    optionsMenuChildScene.setBackgroundEnabled(false);
	    
	    audioMenuItem.setPosition(ITEM_WIDTH, -90);
	    musicMenuItem.setPosition(ITEM_WIDTH, + 20);
	    
	    optionsMenuChildScene.setOnMenuItemClickListener(this);
		
	}
	
	public void createAboutMenuChildScene(){
		aboutMenuChildScene = new MenuScene(camera);
		aboutMenuChildScene.setPosition(400, 240);
		
		final IMenuItem creditsMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(99, resourcesManager.credits_region, vbom), 1, 1);
		aboutMenuChildScene.addMenuItem(creditsMenuItem);
		aboutMenuChildScene.setBackgroundEnabled(false);
		
		//creditsMenuItem.setPosition(-creditsMenuItem.getWidth()/2, -creditsMenuItem.getHeight()/2);
		creditsMenuItem.setPosition(-creditsMenuItem.getWidth()/2,-creditsMenuItem.getHeight()/2);
	}
	
	public void createHowMenuChildScene(){
		howMenuChildScene = new MenuScene(camera);
		howMenuChildScene.setPosition(400, 240);		
		howMenuChildScene.setBackgroundEnabled(false);
		
		
	}
	
	public void createPreGamesMenuChildScene()
	{
		preGameMenuChildScene = new MenuScene(camera);
		preGameMenuChildScene.setPosition(400, 350);
	    //TODO select your plane yazs�s�
	    final IMenuItem plane11 = new ScaleMenuItemDecorator(new SpriteMenuItem(11, resourcesManager.plane_regions[0], vbom), 1.2f, 1);
	    final IMenuItem plane12 = new ScaleMenuItemDecorator(new SpriteMenuItem(12, resourcesManager.plane_regions[1], vbom), 1.2f, 1);
	    final IMenuItem plane21 = new ScaleMenuItemDecorator(new SpriteMenuItem(21, resourcesManager.plane_regions[2], vbom), 1.2f, 1);
	    final IMenuItem plane22 = new ScaleMenuItemDecorator(new SpriteMenuItem(22, resourcesManager.plane_regions[3], vbom), 1.2f, 1);
	    final IMenuItem plane31 = new ScaleMenuItemDecorator(new SpriteMenuItem(31, resourcesManager.plane_regions[4], vbom), 1.2f, 1);
	    final IMenuItem plane32 = new ScaleMenuItemDecorator(new SpriteMenuItem(32, resourcesManager.plane_regions[5], vbom), 1.2f, 1);
	    final IMenuItem plane41 = new ScaleMenuItemDecorator(new SpriteMenuItem(41, resourcesManager.plane_regions[6], vbom), 1.2f, 1);
	    final IMenuItem plane42 = new ScaleMenuItemDecorator(new SpriteMenuItem(42, resourcesManager.plane_regions[7], vbom), 1.2f, 1);
	    
	    preGameMenuChildScene.addMenuItem(plane11);
	    preGameMenuChildScene.addMenuItem(plane12);
	    preGameMenuChildScene.addMenuItem(plane21);
	    preGameMenuChildScene.addMenuItem(plane22);
	    preGameMenuChildScene.addMenuItem(plane31);
	    preGameMenuChildScene.addMenuItem(plane32);
	    preGameMenuChildScene.addMenuItem(plane41);
	    preGameMenuChildScene.addMenuItem(plane42);
	    
	    preGameMenuChildScene.buildAnimations();
	    preGameMenuChildScene.setBackgroundEnabled(false);
	    
	    plane11.setPosition(-200, -100);
	    plane12.setPosition(-200, 0);
	    plane21.setPosition(-100, -100);
	    plane22.setPosition(-100, 0);
	    plane31.setPosition(0, -100);
	    plane32.setPosition(0, 0);
	    plane41.setPosition(100, -100);
	    plane42.setPosition(100, 0);
	    
	    preGameMenuChildScene.setOnMenuItemClickListener(this);
	    
		
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY)
	{
	        switch(pMenuItem.getID())
	        {
	        case MENU_PLAY:
	        	setChildScene(preGameMenuChildScene);
	        	return true;
	        case MENU_PRACTICE:
	        	SceneManager.getInstance().loadPractiseGameScene(engine);
	        	return true;
	        case MENU_HOST:
	        	SceneManager.getInstance().loadHostGameScene(engine);
	        	return true;
	        case MENU_JOIN:
	        	SceneManager.getInstance().loadClientGameScene(engine);
	        	return true;
	        case MENU_OPTIONS:
	    	    setChildScene(optionsMenuChildScene);
	    	    return true;
	        case MENU_AUDIO:
	        	//TODO Audio'lar� master bir noktada y�netmek �ok olas� g�z�km�yor �u anda
	        	return true;
	        case MENU_MUSIC:
	        	if(ResourcesManager.getInstance().music.isPlaying()){
	        		ResourcesManager.getInstance().music.pause();
	        		SharedPreferencesManager.getInstance().setMusicEnabled(false);
	        	}
	        	else {
	        		ResourcesManager.getInstance().music.play();
	        		SharedPreferencesManager.getInstance().setMusicEnabled(true);
	        	}
	        	return true;
	        case MENU_ABOUT:
	        	setChildScene(aboutMenuChildScene);
	        	return true;
	        case MENU_HOW:
	        	setChildScene(playMenuChildScene);
	        	return true;
	        case 11:
	        	selected_plane = 0;
	        	setChildScene(playMenuChildScene);
	        	return true;
	        case 12:
	        	selected_plane = 1;
	        	setChildScene(playMenuChildScene);
	        	return true;
	        case 21:
	        	selected_plane = 2;
	        	setChildScene(playMenuChildScene);
	        	return true;
	        case 22:
	        	selected_plane = 3;
	        	setChildScene(playMenuChildScene);
	        	return true;
	        case 31:
	        	selected_plane = 4;
	        	setChildScene(playMenuChildScene);
	        	return true;
	        case 32:
	        	selected_plane = 5;
	        	setChildScene(playMenuChildScene);
	        	return true;
	        case 41:
	        	selected_plane = 6;
	        	setChildScene(playMenuChildScene);
	        	return true;
	        case 42:
	        	selected_plane = 7;
	        	setChildScene(playMenuChildScene);
	        	return true;
	        default:
	            return false;
	    }
	}

	

}
