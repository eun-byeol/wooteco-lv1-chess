package controller.command;

import java.util.Objects;

public class Command {
    private final CommandType commandType;
    private final String value;

    public Command(String value) {
        this.commandType = CommandType.findByCommand(value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public CommandType getType() {
        return commandType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return commandType == command.commandType && value.equals(command.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commandType, value);
    }
}