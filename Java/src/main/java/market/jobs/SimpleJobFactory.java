package market.jobs;

import market.businessLogic.commands.Command;
import market.businessLogic.commands.LoadLastPurchasesCommand;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;

import java.util.List;


public class SimpleJobFactory implements JobFactory {

    private List<Command> jobsLogic;

    public SimpleJobFactory(@NonNull List<Command> jobsLogic) {
        this.jobsLogic = jobsLogic;
    }

    public Job newJob(TriggerFiredBundle triggerFiredBundle, Scheduler scheduler) throws SchedulerException {
        //LoadLastPurchasesJob
        if (LoadLastPurchasesJob.class.equals(triggerFiredBundle.getJobDetail().getJobClass())) {
            Command jobLogic = getByClass(LoadLastPurchasesCommand.class, jobsLogic);
            if (jobLogic != null) {
                return new LoadLastPurchasesJob(jobLogic);
            }
        }

        //default
        try {
            return triggerFiredBundle.getJobDetail().getJobClass().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new SchedulerException(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new SchedulerException(e);
        }
    }

    //private
    @Nullable Command getByClass(@NonNull Class<? extends Command> commandForSearch, @NonNull List<Command> commands) {
        Command result = null;
        for (Command command : commands) {
            if (command.getClass().equals(commandForSearch)) {
                result = command;
                break;
            }
        }
        return result;
    }
}
