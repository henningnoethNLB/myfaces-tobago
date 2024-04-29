/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package org.apache.myfaces.tobago.example.demo;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class SheetSelect2Controller implements Serializable {

  private List<String> strings = new ArrayList();
  private static final long serialVersionUID = 1L;

  public List<String> getStrings() {
    if (this.strings.isEmpty()) {
      this.strings.add("Hello");
      this.strings.add("World");
      this.strings.add("line3");
    }

    return this.strings;
  }

  public void setStrings(List<String> strings) {
    this.strings = strings;
  }
}
