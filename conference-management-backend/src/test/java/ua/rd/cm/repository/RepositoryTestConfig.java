package ua.rd.cm.repository;

import org.junit.runner.RunWith;

import ua.rd.cm.config.InMemoRepositoryConfig;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = InMemoRepositoryConfig.class)
@Rollback(false)
public class RepositoryTestConfig extends AbstractTransactionalJUnit4SpringContextTests{

	
}
