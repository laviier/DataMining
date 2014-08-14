package dao;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import mining2.AverageSalary;
import mining2.NormalizedPoints;

import javax.servlet.*;

public class DataMiningBackground implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        /*
		 * For test, the two operations are hidden,since they consume too many memory
		 * For production, two operations should be shown
		 * 1. get second degree friend
		 * 2. get the salary of all the jobs
		 */
       // scheduler.scheduleAtFixedRate(new GetSecondFriends(), 0, 1440, TimeUnit.MINUTES);
       // scheduler.scheduleAtFixedRate(new AverageSalary(), 120, 1440, TimeUnit.MINUTES);
    //    scheduler.scheduleAtFixedRate(new NormalizedPoints(), 240, 1440, TimeUnit.MINUTES);
      //  scheduler.scheduleAtFixedRate(new GetFBFeeds(), 0, 30, TimeUnit.MINUTES);
        
        scheduler.scheduleAtFixedRate(new GetLinkedInFeeds(), 0, 30, TimeUnit.MINUTES);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }

}