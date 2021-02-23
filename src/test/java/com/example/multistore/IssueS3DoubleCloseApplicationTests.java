package com.example.multistore;

import static com.github.paulcwarren.ginkgo4j.Ginkgo4jDSL.BeforeEach;
import static com.github.paulcwarren.ginkgo4j.Ginkgo4jDSL.Describe;
import static com.github.paulcwarren.ginkgo4j.Ginkgo4jDSL.It;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.ByteArrayInputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.paulcwarren.ginkgo4j.Ginkgo4jSpringRunner;

@RunWith(Ginkgo4jSpringRunner.class)
@SpringBootTest(classes = {
        IssueS3DoubleCloseApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IssueS3DoubleCloseApplicationTests {

    @Autowired
    private FileRepository repo;

    @Autowired
    private FileContentStore store;

    private Exception e;

    {
        Describe("issue-double-close", () -> {

            BeforeEach(() -> {

                try {
                    File f = repo.save(new File());
                    f = store.setContent(f, new ByteArrayInputStream("foo".getBytes()));
                    f = store.setContent(f, new ByteArrayInputStream("bar".getBytes()));
                } catch (Exception e) {
                    this.e = e;
                }

            });

            It("should load the context", () -> {
                assertThat(e, is(nullValue()));
            });
        });
    }

	@Test
	public void noop() {
	}

}
