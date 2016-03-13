package market.job;

import market.businessLogic.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Базовый класс для джобов, логика которых инкапсулирована в классах команд({@link Command})
 */
public abstract class AbstractCommandJob implements Job {

    protected Logger log = LogManager.getLogger(getClass());
    private Command command;

    public AbstractCommandJob(@NonNull Command command){
        this.command = command;
    }

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("job started");
        try {
            command.execute();
            log.info("job completed successfully");
        }
        catch (Exception e){
            log.error("error completed with error", e);
        }
    }
}
