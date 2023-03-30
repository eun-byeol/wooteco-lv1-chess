package chess.command;

import chess.domain.game.ActionHandler;
import chess.domain.game.Game;
import chess.domain.game.Status;
import chess.history.History;
import java.util.List;

public class EndCommand implements Command {
    
    public static final int END_ARGUMENTS_SIZE = 0;
    
    private final CommandType type = CommandType.END;
    
    public EndCommand(final List<String> arguments) {
        this.validate(arguments);
    }
    
    private void validate(final List<String> arguments) {
        if (arguments.size() != END_ARGUMENTS_SIZE) {
            throw new IllegalArgumentException(
                    COMMAND_ERROR_PREFIX + this.type + INVALID_ARGUMENT_ERROR_MESSAGE);
        }
    }
    
    @Override
    public Status query(final ActionHandler action) {
        throw new UnsupportedOperationException(
                COMMAND_ERROR_PREFIX + this.type + INVALID_QUERY_ERROR_MESSAGE);
    }
    
    @Override
    public Game update(final ActionHandler action) {
        return action.end();
    }
    
    @Override
    public void addHistory(final History history) {
        throw new UnsupportedOperationException(
                COMMAND_ERROR_PREFIX + this.type + INVALID_EXECUTE_ERROR_MESSAGE);
        
    }
    
    @Override
    public CommandType getType() {
        return this.type;
    }
}