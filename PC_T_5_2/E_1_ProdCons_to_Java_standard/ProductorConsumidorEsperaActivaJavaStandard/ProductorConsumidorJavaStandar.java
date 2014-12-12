import java.util.concurrent.Semaphore;

public class ProductorConsumidorJavaStandar {
	static volatile boolean producido = false;
	static volatile boolean consumido = true;
	static volatile double producto;
	static Semaphore semaProductores = new Semaphore(1); //Semáforo con un permiso.
	static volatile int iteraciones = 0;

	public static void productor(){
		while(iteraciones < 20){
			
			try {
				semaProductores.acquire();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			iteraciones++;
			
			while (!consumido); //Sincronización con espera activa. NO SE DEBE USAR. Ineficiente. 
			
			producto = Math.random();
			System.out.println("Produciendo... " + producto);
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			consumido = false;
			producido = true;
			
			semaProductores.release();
		}
	}

	public static void consumidor() {
		for (int i = 0; i < 10; i++) {
			while (!producido); //Sincronización con espera activa. NO SE DEBE USAR. Ineficiente.
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("Consumiendo... " + producto);
			consumido = true;
			producido = false;
		}
	}

	public static void main(String[] args) {
		/**
		 * Creación de clase anónima.
		 */
		Thread hiloProductor = new Thread(new Runnable() {

			public void run() {
				productor();
			}

		});
		
		/**
		 * Expresiones lambda que sustituyen la anterior. Más eficiente en tiempo de ejecución y compacta.
		 */
		Thread hiloProductorLambda = new Thread(ProductorConsumidorJavaStandar::productor);
		
		/**
		 * Equivalencia a la expresión anterior. Nótese que () está vacío porque el método run() no recibe parámetros.
		 */
		Thread hiloProductorLambda2 = new Thread(() -> productor());
		
		Thread hiloConsumidor = new Thread(new Runnable() {

			public void run() {
				consumidor();
			}

		});

		/**
		 * Esta implementación es mejorable usando Executors.
		 */
		hiloProductor.start();
		hiloConsumidor.start();
		hiloProductorLambda.start();
		hiloProductorLambda2.start();
	}
}
