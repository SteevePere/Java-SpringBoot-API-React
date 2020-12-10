package com.quest.etna;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
		AuthControllerTests.class,
        BoardControllerTests.class,
        IssueControllerTests.class,
        MetricsControllerTests.class,
		UserControllerTests.class,
		MilestoneControllerTests.class
})
class QuestSpringBootApplicationTests
{
}
