/*
 * Copyright 2010-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springextensions.db4o;

import javax.inject.Inject;

import com.db4o.events.EventRegistry;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.testng.listener.PaxExam;
import org.ops4j.pax.exam.util.Filter;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Map;

import static org.ops4j.pax.exam.CoreOptions.*;

/**
 * @author olli
 */
@Listeners({PaxExam.class})
public class BlueprintIT {

    @Inject
    @Filter(timeout = 50000)
    private Db4oOperations db4oOperations;

    @Inject
    @Filter(timeout = 50000)
    private EventRegistry eventRegistry;

    @Configuration
    public Option[] configure() {
        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            System.out.format("%s=%s%n",
                    envName,
                    env.get(envName));
        }
        CoreOptions.cleanCaches();
        return options(
            // test
            mavenBundle("org.testng", "testng", "6.4"),
            // Spring Framework
                wrappedBundle(mavenBundle("org.springframework", "spring-beans", "4.3.7.RELEASE")),
                        wrappedBundle(mavenBundle("org.springframework", "spring-context", "4.3.7.RELEASE")),
                                wrappedBundle(mavenBundle("org.springframework", "spring-tx", "4.3.7.RELEASE")),
            // db4o
            mavenBundle("com.db4o", "db4o-full-java5", "8.1.209.15862"),
            wrappedBundle(mavenBundle("org.apache.ant", "ant", "1.8.4")),
            // Spring db4o
                mavenBundle("org.springextensions.db4o", "org.springextensions.db4o", "1.0.0.BUILD-SNAPSHOT"),
            //bundle("file:../org.springextensions.db4o/target/org.springextensions.db4o-1.0.0.BUILD-SNAPSHOT.jar"),
                //wrappedBundle(bundle("file:../org.springextensions.db4o/target/org.springextensions.db4o-1.0.0.BUILD-SNAPSHOT-tests.jar")),
            // Aries Blueprint
            mavenBundle("org.apache.aries.blueprint", "org.apache.aries.blueprint", "1.1.0"),
            mavenBundle("org.apache.aries.proxy", "org.apache.aries.proxy", "1.0.1"),
            mavenBundle("org.apache.aries", "org.apache.aries.util", "1.1.3"),
            mavenBundle("org.ow2.asm", "asm", "5.2"),
            mavenBundle("org.ow2.asm", "asm-commons", "5.2"),
            mavenBundle("org.ow2.asm", "asm-tree", "5.2")
        );
    }

    @Test
    public void testDb4oOperations() {
        Assert.assertNotNull(db4oOperations, "Db4oOperations is null");
    }

    @Test
    public void testEventRegistry() {
        Assert.assertNotNull(eventRegistry, "EventRegistry is null");
    }

}
