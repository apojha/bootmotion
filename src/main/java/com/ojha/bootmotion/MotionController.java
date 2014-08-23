package com.ojha.bootmotion;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class MotionController {

    @Autowired
    private CommandExecutor executor;

    @RequestMapping(value ="/command", method = RequestMethod.POST, produces = "application/json")
    public void startMotion(@RequestBody Command command) {
        this.executor.execute(command.getCommandName());
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {IllegalArgumentException.class})
    public @ResponseBody() Error handleIllegalArgumentException() {

//        return "{\"reason\" : \"command not found\"}";
        return new Error("command not found");
    }

    public static class Error {
        private String reason;

        public Error(String reason) {
            this.reason = reason;
        }

        public String getReason(){
            return this.reason;
        }
    }

    public static class Command {
        private String commandName;

        public String getCommandName() {
            return this.commandName;
        }
    }
}
