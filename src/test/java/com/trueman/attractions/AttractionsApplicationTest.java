package com.trueman.attractions;

import com.trueman.attractions.servicesTest.AssistanceServiceTest;
import com.trueman.attractions.servicesTest.AttractionServiceTest;
import com.trueman.attractions.servicesTest.LocalityServiceTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        AssistanceServiceTest.class,
        AttractionServiceTest.class,
        LocalityServiceTest.class
})
public class AttractionsApplicationTest {
}
