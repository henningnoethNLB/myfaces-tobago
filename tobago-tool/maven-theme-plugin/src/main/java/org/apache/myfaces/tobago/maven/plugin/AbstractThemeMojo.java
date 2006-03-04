package org.apache.myfaces.tobago.maven.plugin;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0(the "License");
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

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.DirectoryScanner;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: bommel
 * Date: 23.10.2005
 * Time: 16:37:04
 */
public abstract class AbstractThemeMojo extends AbstractMojo {
  /**
   * The maven project.
   *
   * @parameter expression="${project}"
   * @required
   * @readonly
   */
  private MavenProject project;

  public MavenProject getProject() {
    return project;
  }

  protected String[] getThemeFiles(File sourceDir) {
    DirectoryScanner scanner = new DirectoryScanner();
    scanner.setBasedir(sourceDir);
    scanner.setIncludes(new String[]{"**"});
    scanner.setExcludes(new String [] {"**/*.properties", "**/*.properties.xml","**/*.class",});
    scanner.scan();
    return scanner.getIncludedFiles();
  }
}
