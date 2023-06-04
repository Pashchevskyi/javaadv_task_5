package com.example.javaadv_task_5;

import com.example.javaadv_task_5.web.EmployeeController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AppTests.class)
class AppTests {

    @Autowired
    @Lazy
    private EmployeeController employeeController;

    @Test
    @DisplayName("context loads")
    void contextLoads() {
        Assertions.assertThat(employeeController).isNotNull();
    }
}
