package com.ojha.bootmotion;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Map;

public class CommandExecutor {

    private Map<String, String> commands;

    public CommandExecutor(Map<String, String> commands) {
        this.commands = commands;
    }

    public String execute(String commandName) throws CommandFailureException {

        if (!this.commands.containsKey(commandName)){
            throw new IllegalArgumentException("Command " + commandName + " does not exist");
        }

        try {
            Runtime runtime = Runtime.getRuntime();

            Process process = runtime.exec(this.commands.get(commandName));
            process.waitFor();

            String commandOutput = IOUtils.toString(process.getInputStream()).trim();

            if (process.exitValue() != 0){
                throw new CommandFailureException(commandOutput);
            }

            return commandOutput;
        }
        catch (IOException e) {
            throw new CommandFailureException(e.getMessage());
         } catch (InterruptedException e) {
            throw new CommandFailureException(e.getMessage());
        }
    }


}
