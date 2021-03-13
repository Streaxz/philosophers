class DiningPhilosophers {
    boolean done = false;
    ReentrantLock[] locks = new ReentrantLock[5];

    Lock reentrantLock = new ReentrantLock();
    boolean[] forks = new boolean[5];

    public DiningPhilosophers() {
        for (int i = 0; i < 5; i += 1) {
            locks[i] = new ReentrantLock(true);
            forks[i] = true;
        }
    }


    // call the run() method of any runnable to execute its code
    public void wantsToEat(int philosopher,
                           Runnable pickLeftFork,
                           Runnable pickRightFork,
                           Runnable eat,
                           Runnable putLeftFork,
                           Runnable putRightFork) throws InterruptedException {
        reentrantLock.lock();
        boolean LeftFork;
        boolean RightFork;
        if (philosopher != 4) {

            LeftFork = forks[philosopher];
            RightFork = forks[philosopher + 1];
            if (LeftFork && RightFork) {
                locks[philosopher].lock();
                forks[philosopher] = false;
                pickLeftFork.run();
                locks[philosopher + 1].lock();
                pickRightFork.run();
                forks[philosopher + 1] = false;

                eat.run();
                putRightFork.run();
                forks[philosopher] = true;
                locks[philosopher + 1].unlock();
                putLeftFork.run();
                forks[philosopher + 1] = true;
                locks[philosopher].unlock();
            } else {
                Thread.sleep(5);
            }
        } else {
            LeftFork = forks[0];
            RightFork = forks[4];
            if (LeftFork && RightFork) {
                locks[0].lock();
                forks[0] = false;
                pickRightFork.run();
                locks[4].lock();
                forks[4] = false;
                pickLeftFork.run();

                eat.run();
                putLeftFork.run();
                forks[4] = true;
                locks[4].unlock();
                putRightFork.run();
                forks[0] = true;
                locks[0].unlock();
            } else {
                Thread.sleep(5);
            }
        }
        reentrantLock.unlock();
    }
}