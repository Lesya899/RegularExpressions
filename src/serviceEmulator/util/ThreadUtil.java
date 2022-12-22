package serviceEmulator.util;

public class ThreadUtil {

        private ThreadUtil() {
        }

        public static void startThreads(Thread... threads) {
            for (Thread thread : threads) {
                thread.start();
            }
        }

        public static void joinThreads(Thread... threads) throws InterruptedException {
            for (Thread thread : threads) {
                thread.join();
            }
        }
}
