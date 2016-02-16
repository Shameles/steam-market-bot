package jobs;

import org.hibernate.SessionFactory;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;

import java.net.MalformedURLException;


public class SimpleJobFactory implements JobFactory {

    private SessionFactory sessionFactory;

    public SimpleJobFactory(SessionFactory sessionFactory){

        this.sessionFactory = sessionFactory;
    }

    public Job newJob(TriggerFiredBundle triggerFiredBundle, Scheduler scheduler) throws SchedulerException {
        if (LoadLastPurchasesJob.class.equals(triggerFiredBundle.getJobDetail().getJobClass())){
            try {
                return new LoadLastPurchasesJob(sessionFactory);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw new SchedulerException("Can't create LoadLastPurchasesJob", e);
            }
        }
        try {
            return triggerFiredBundle.getJobDetail().getJobClass().newInstance();
        }
         catch (InstantiationException e) {
            e.printStackTrace();
            throw new SchedulerException(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new SchedulerException(e);
        }
    }
}
