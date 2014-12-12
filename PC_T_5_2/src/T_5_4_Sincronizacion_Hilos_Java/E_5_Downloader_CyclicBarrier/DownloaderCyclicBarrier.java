package T_5_4_Sincronizacion_Hilos_Java.E_5_Downloader_CyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DownloaderCyclicBarrier {
	private static final int N_FRAGMENTS = 10;
	private static final int N_THREADS = 3;
	private static volatile int[] file = new int[N_FRAGMENTS];
	private static volatile int descargando = 0;
	private static volatile int almacenado = 0;

	static CyclicBarrier barrera = new CyclicBarrier(5);
	static Lock lock = new ReentrantLock();

	// Add the attributes you need

	private static int downloadData(int numFragment) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return numFragment * 2;
	}

	private static void printFile() {
		System.out
				.println("--------------------------------------------------");
		System.out.print("File = [");
		for (int i = 0; i < N_FRAGMENTS; i++) {
			System.out.print(file[i] + ",");
		}
		System.out.println("]");
	}

	public static void downloader() {

		while (true) {
			int n_frag = -1;

			lock.lock();
			n_frag = almacenado + descargando;
			descargando++;
			lock.unlock();

			// While there are more fragments to download...
			if (n_frag < N_FRAGMENTS) {
				// Store the data in the array
				file[n_frag] = downloadData(n_frag);
			}

			lock.lock();
			almacenado++;
			descargando--;
			lock.unlock();

			try {
				if (barrera.await() == 0){
				System.out.println("Soy el último en llegar");
				}else{
					System.out.println("NO soy el último");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {

		Executor executor = Executors.newFixedThreadPool(3);
		executor.execute(DownloaderCyclicBarrier::downloader);
		executor.execute(DownloaderCyclicBarrier::downloader);
		executor.execute(DownloaderCyclicBarrier::downloader);
		executor.execute(DownloaderCyclicBarrier::downloader);
		executor.execute(DownloaderCyclicBarrier::downloader);
		executor.execute(DownloaderCyclicBarrier::downloader);
		executor.execute(DownloaderCyclicBarrier::downloader);
		printFile();
	}
}
