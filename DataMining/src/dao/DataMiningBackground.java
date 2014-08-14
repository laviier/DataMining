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
        scheduler.scheduleAtFixedRate(new GetSecondFriends(), 0, 3, TimeUnit.MINUTES);
        scheduler.scheduleAtFixedRate(new AverageSalary(), 0, 3, TimeUnit.MINUTES);
        scheduler.scheduleAtFixedRate(new NormalizedPoints(), 0, 3, TimeUnit.MINUTES);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }

}