package jobs;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

import static org.quartz.DateBuilder.*;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;


public class QuartzTest {

    public QuartzTest() throws SchedulerException, InterruptedException {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        JobDetail job = newJob(LoadLastPurchasesJob.class)
                .withIdentity("job1", "group1")
                .build();

        Date runTime = evenSecondDateAfterNow();

        Trigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(runTime).build();

        sched.scheduleJob(job, trigger);
        sched.start();

                Thread.sleep(90L * 1000L);

        sched.shutdown(true);
    }


}
