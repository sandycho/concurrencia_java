package E_2_Main_Mensajes_Interrupcion.MainMensajeInterrupcionThread;

/**
 * Se desea implementar en Java un programa con dos hilos, el hilo principal
 * Main y un hilo de Mensajes.
 * 
 * El hilo principal: Crea el hilo de mensajes Espera a que el hilo de mensajes
 * finalice Si no lo hace, cada segundo imprime “Todavía esperando...” Cuando ha
 * pasado un tiempo máximo de 5 segundos, si el hilo de mensajes no ha terminado
 * todavía, dice “Cansado de esperar!”, le interrumpe y espera a que termine Al
 * finalizar el hilo dice “Por fin!”
 * 
 * El hilo de mensajes: Dice las siguientes frases cada dos segundos: "La vida
 * es bella", "O no...", "Los pajaritos cantan", "Y molestan...” Si el hilo
 * principal le interrumpe antes de terminar, dice “Se acabó!”
 * 
 * @author sandya
 *
 */
public class MainMensajeInterrupcionThread {
	public static void emisorMensajes() {
		System.out.println("La vida es bella");

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("O no...");

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Los pajaritos cantan");

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Y molestan...");

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Acabando...");
	}

	public static void receptor() {

	}

	public static void main(String[] args) throws InterruptedException {
		// Thread hiloEmisorMensajes = new Thread(
		// MainMensajeInterrupcionThread::emisorMensajes);

		/**
		 * No se puede hacer esto con una expresion lambda. Tiene que ser con la
		 * habitual clase anónima.
		 */
		Thread hiloEmisorMensajes = new Thread(new Runnable() {

			@Override
			public void run() {
				emisorMensajes();
				System.out.println("Se acabó!");
			}

		});

		int segundos = 0;

		hiloEmisorMensajes.start();

		while (segundos < 5) {
			Thread.sleep(1000);
			System.out.println("****************Sigo esperando******");
			segundos++;
		}
		System.out.println("****************Cansado de esperar******");

		hiloEmisorMensajes.interrupt();

		hiloEmisorMensajes.join();

		System.out.println("****************Por fin!******");

	}
}
