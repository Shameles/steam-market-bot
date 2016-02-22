package market.jobs;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.JobFactory;

import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.*;


public class QuartzUtils {
    private static final String LOAD_PURCHASE_HISTORY_GROUP = "LOAD_PURCHASE_HISTORY_GROUP";
    private static final int DEFAULT_JOB_INTERVAL_IN_SECONDS = 80;

    public static Scheduler configureScheduler(JobFactory jobFactory, Properties quartzSettings) throws SchedulerException, InterruptedException {
        SchedulerFactory sf = new StdSchedulerFactory(quartzSettings);
        Scheduler sched = sf.getScheduler();
        sched.setJobFactory(jobFactory);

        JobDetail job = newJob(LoadLastPurchasesJob.class)
                .withIdentity(LoadLastPurchasesJob.class.getSimpleName(), LOAD_PURCHASE_HISTORY_GROUP)
                .build();

        int jobInterval = Integer.parseInt(quartzSettings.getProperty("jobs.customsSettings.loadLastPurchaseJobInterval"
                , Integer.toString(DEFAULT_JOB_INTERVAL_IN_SECONDS)));

        Trigger trigger = newTrigger()
                .withIdentity("trigger1", LOAD_PURCHASE_HISTORY_GROUP)
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(jobInterval)
                        .repeatForever())
                .build();
        sched.scheduleJob(job, trigger);

        return sched;
    }
}
