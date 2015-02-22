package pacman.menus;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GameStateManager
{
	private ArrayList<GameState> states;
	private int currentState;
	
	public static final int MENUSTATE = 0;
	public static final int LOGINSTATE = 1;
	public static final int REGISTRARSTATE = 2;
	public static final int MENSAJESTATE = 3;
	public static final int MENUPRINCIPALSTATE = 4;
	public static final int MENUTORNEOSTATE = 5;
	public static final int MENUCREARTORNEOSTATE = 6;
	public static final int MENULISTATORNEOSSTATE = 7;
	public static final int SALAESPERASTATE = 8;
	
	public static final int PRUEBAMAPASTATE = 9;
	
	public static String IP = "190.75.131.10";
	//public static String IP = "192.168.1.100";
	public static int PUERTO = 3000;
	public static Cliente cliente = new Cliente();
    
	public GameStateManager()
	{
		states = new ArrayList<GameState>();
		states.add(new MenuInicialState(this));
		states.add(new LoginState(this));
		states.add(new RegistrarState(this));
		states.add(new MensajeState(this));
		states.add(new MenuPrincipalState(this));
		states.add(new MenuTorneoState(this));
		states.add(new MenuCrearTorneoState(this));
		states.add(new MenuTorneosExistentesState(this));
		states.add(new MenuSalaEsperaState(this));
		states.add(new PruebaMapa(this));
		setState(MENUSTATE);
	}

	public void draw(Graphics2D g)
	{
		states.get(currentState).draw(g);
		
	}

	public int getCurrentState()
	{
		return currentState;
	}

	public void keyPressed(KeyEvent ke)
	{
		states.get(currentState).keyPressed(ke);
	}

	public void keyReleased(KeyEvent ke)
	{
		states.get(currentState).keyReleased(ke);
	}

	public void keyTyped(KeyEvent ke)
	{
		states.get(currentState).keyTyped(ke);	
	}	

	public void mouseClicked(MouseEvent me)
	{
		states.get(currentState).mouseClicked(me);
	}

	
	public void mouseDragged(MouseEvent me)
	{
		states.get(currentState).mouseDragged(me);
	}

	
	public void mouseEntered(MouseEvent me)
	{
		states.get(currentState).mouseEntered(me);
	}

	
	public void mouseExited(MouseEvent me)
	{
		states.get(currentState).mouseExited(me);		
	}

	
	public void mouseMoved(MouseEvent me)
	{
		states.get(currentState).mouseMoved(me);
	}

	
	public void mousePressed(MouseEvent me)
	{
		states.get(currentState).mousePressed(me);
	}

	
	public void mouseReleased(MouseEvent me)
	{
		states.get(currentState).mouseReleased(me);
	}
	
	public void setState(int state)
	{
		currentState = state;
		states.get(state).init();
	}

	public void setStateMensaje(String titulo, String mensaje, int stateAnterior)
	{
		currentState = MENSAJESTATE;
		MensajeState msj = (MensajeState) states.get(MENSAJESTATE);
		msj.init(titulo, mensaje, stateAnterior);
	}
	
	public void setStateSalaEspera(long idSala)
	{
		currentState = SALAESPERASTATE;
		MenuSalaEsperaState espera = (MenuSalaEsperaState) states.get(SALAESPERASTATE);
		espera.init(idSala);
	}	
	
	public void setStateMapa(long idSala)
	{
		currentState = PRUEBAMAPASTATE;
		PruebaMapa espera = (PruebaMapa) states.get(PRUEBAMAPASTATE);
		espera.init(idSala);
	}
	
	public void update()
	{
		states.get(currentState).update();
	}
}
