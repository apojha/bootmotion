package com.ojha.bootmotion;

public class CommandFailureException extends RuntimeException {

    private String commandOutput;

    public CommandFailureException(String commandOutput) {

        this.commandOutput = commandOutput;
    }
}
