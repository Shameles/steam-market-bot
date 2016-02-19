package jobs;

import market.businessLogic.commands.Command;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Базовый класс для джобов, логика которых инкапсулирована в классах команд({@link Command})
 */
public abstract class AbstractCommandJob implements Job {

    protected Logger _log = LogManager.getLogger(getClass());
    private Command command;

    public AbstractCommandJob(@NonNull Command command){
        this.command = command;
    }

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        _log.info("job started");
        try {
            command.execute();
            _log.info("job completed successfully");
        }
        catch (Exception e){
            _log.error("error completed with error", e);
        }
    }
}
