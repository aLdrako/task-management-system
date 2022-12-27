package com.telerikacademy.tms.commands;

import com.telerikacademy.tms.commands.contracts.Command;
import com.telerikacademy.tms.core.TaskManagementRepositoryImpl;
import com.telerikacademy.tms.core.contracts.TaskManagementRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static com.telerikacademy.tms.commands.ShowAllPeople.EXPECTED_NUMBER_PARAMETERS;
import static com.telerikacademy.tms.utils.TestUtils.getList;

public class ShowAllPeopleTests {

    private TaskManagementRepository repository;
    private Command command;

    @BeforeEach
    public void before() {
        repository = new TaskManagementRepositoryImpl();
        command = new ShowAllPeople(repository);
    }

    @ParameterizedTest(name = "arguments count: {0}")
    @ValueSource(ints = {EXPECTED_NUMBER_PARAMETERS + 1})
    public void execute_Should_ThrowException_When_ArgumentsAreInvalid(int argumentsCount) {
        // Arrange
        List<String> parameters = getList(argumentsCount);

        // Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }
}
