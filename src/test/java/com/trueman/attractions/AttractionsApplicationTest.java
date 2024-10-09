package com.trueman.attractions;

import com.trueman.attractions.servicesTest.AssistanceServiceTest;
import com.trueman.attractions.servicesTest.AttractionServiceTest;
import com.trueman.attractions.servicesTest.LocalityServiceTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@Suite
@SelectClasses({
        AssistanceServiceTest.class,
        AttractionServiceTest.class,
        LocalityServiceTest.class
})
@SpringBootTest
public class AttractionsApplicationTest {
}
