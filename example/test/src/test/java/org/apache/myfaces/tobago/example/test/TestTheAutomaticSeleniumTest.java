package org.apache.myfaces.tobago.example.test;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TestTheAutomaticSeleniumTest {

  private static final Log LOG = LogFactory.getLog(TestTheAutomaticSeleniumTest.class);

  @Test
  public void areTheCorrectFilesTested() {

    List<String> metaTestList = new ArrayList<String>();

    Collection<Object[]> pages = AutomaticSeleniumTest.findPages();
    for (Object[] page : pages) {
      String url = (String) page[1];
      String prefix = "/tobago-example-test/faces/meta-test/";
      if (url.startsWith(prefix)) {
        metaTestList.add(url.substring(prefix.length()));
      }
    }

    Collections.sort(metaTestList);

    String[] expected = new String[]{
        "meta-0-ok.jspx",
        "meta-0-ok.xhtml",
        "meta-2-ok.xhtml",
        "meta-3-ok.jspx"
    };

    Assert.assertArrayEquals(expected, metaTestList.toArray());
  }

  @Test
  public void test404() throws Exception {
    try {
      String url = AutomaticSeleniumTest.createUrl("/meta-test/meta-404-not-existing.xhtml");
      LOG.info("Testing page: '" + url + "'");
      AutomaticSeleniumTest test = new AutomaticSeleniumTest(url, url);
      test.setUp();
      test.testPageConsistency();
      test.tearDown();
      Assert.fail("The test should has been failed.");
    } catch (AssertionError e) {
      if (e.getMessage().contains(AutomaticSeleniumTest.CONTAINS_A_404)) {
        // okay, the error was detected.
      } else {
        throw e;
      }
    }
  }

  @Test
  public void testErrorSeverity() throws Exception {
    try {
      String url = AutomaticSeleniumTest.createUrl("/meta-test/meta-1-fail.xhtml");
      LOG.info("Testing page: '" + url + "'");
      AutomaticSeleniumTest test = new AutomaticSeleniumTest(url, url);
      test.setUp();
      test.testPageConsistency();
      test.tearDown();
      Assert.fail("The test should has been failed.");
    } catch (AssertionError e) {
      if (e.getMessage().contains(AutomaticSeleniumTest.HAS_ERROR_SEVERITY)) {
        // okay, the error was detected.
      } else {
        throw e;
      }
    }
  }

  @Test
  public void testNotTobago() throws Exception {
    try {
      String url = AutomaticSeleniumTest.createUrl("/meta-test/meta-4-not-tobago.xhtml");
      LOG.info("Testing page: '" + url + "'");
      AutomaticSeleniumTest test = new AutomaticSeleniumTest(url, url);
      test.setUp();
      test.testPageConsistency();
      test.tearDown();
      Assert.fail("The test should has been failed.");
    } catch (AssertionError e) {
      if (e.getMessage().contains(AutomaticSeleniumTest.IS_BROKEN)) {
        // okay, the error was detected.
      } else {
        throw e;
      }
    }
  }
}
