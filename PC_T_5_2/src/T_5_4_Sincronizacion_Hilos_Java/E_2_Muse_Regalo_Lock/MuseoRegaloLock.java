package T_5_4_Sincronizacion_Hilos_Java.E_2_Muse_Regalo_Lock;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MuseoRegaloLock {
	static volatile int nVisitantes = 0;
	static Lock lock = new ReentrantLock();

	public static void persona() {

		while (true) {

			lock.lock();

			System.out.println("¡Hola a los " + nVisitantes++ + " del museo!");

			if (nVisitantes == 1) {
				System.out.println("TENGO regalo!");
			} else {
				System.out.println("NO tengo regalo!");
			}

			lock.unlock();

			/***********************************/

			System.out.println("WOW");

			try {
				Thread.sleep(1000);

				System.out.println("Alucinante...");

				Thread.sleep(1000);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			/***********************************/

			lock.lock();

			System.out.println("¡Adiós a los " + --nVisitantes + " del museo!");

			lock.unlock();

			System.out
					.println("¡*******************Paseando en el parque!***************************");

		}
	}

	public static void main(String[] args) {
		/**
		 * Creamos un piscina de hilos con 5 hilos.
		 */
		Executor executor = Executors.newFixedThreadPool(5);

		/**
		 * Le pasamos al executor las tareas que necesitemos, en este caso le
		 * pasamos la tarea varias veces. Tendremos la tarea ejecutándose el
		 * número de veces que le pasemos al executor. En este caso el pool se
		 * encargará de ejecutar con 5 hilos 8 tareas.
		 */
		executor.execute(MuseoRegaloLock::persona);
		executor.execute(MuseoRegaloLock::persona);
		executor.execute(MuseoRegaloLock::persona);
		executor.execute(MuseoRegaloLock::persona);
		executor.execute(MuseoRegaloLock::persona);
		executor.execute(MuseoRegaloLock::persona);
		executor.execute(MuseoRegaloLock::persona);
		executor.execute(MuseoRegaloLock::persona);

	}
}
