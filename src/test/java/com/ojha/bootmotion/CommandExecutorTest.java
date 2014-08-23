package com.ojha.bootmotion;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class CommandExecutorTest {

    @Test
    public void executesSuccessfulCommand() throws Exception {
        //given
        CommandExecutor underTest = new CommandExecutor(
                ImmutableMap.of("echo_cat", "echo I am a cat")
        );

        //when
        String output = underTest.execute("echo_cat");

        //then
        assertEquals("I am a cat", output);
    }

    @Test(expected = IllegalArgumentException.class)
    public void commandDoesNotExistInMap() throws Exception {
        //given
        CommandExecutor underTest = new CommandExecutor(
                ImmutableMap.of("", "")
        );

        //when
        String output = underTest.execute("echo_cat");
    }

    @Test(expected = CommandFailureException.class)
    public void commandFails() throws IOException, InterruptedException {
        //given
        CommandExecutor underTest = new CommandExecutor(
                ImmutableMap.of("echo_cat", "ecoh I am a cat")
        );

        //when
        String output = underTest.execute("echo_cat");
    }
}