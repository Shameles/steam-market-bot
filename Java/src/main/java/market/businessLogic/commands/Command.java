package market.businessLogic.commands;

import market.businessLogic.BusinessLogicException;

/**
 * Интерфейс команды. Удобно подходит для описания действий, выполняемых джобами.
 */
public interface Command {

    /**
     * Выполнить логику команды.
     */
    void execute() throws BusinessLogicException;
}
