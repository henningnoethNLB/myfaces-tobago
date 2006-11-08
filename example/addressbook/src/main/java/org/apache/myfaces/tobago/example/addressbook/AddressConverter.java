package org.apache.myfaces.tobago.example.addressbook;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/*
 * Created 03.12.2004 00:08:01.
 * $Id: AddressConverter.java,v 1.1.1.1 2004/12/15 12:51:35 lofwyr Exp $
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class AddressConverter implements Converter {

  private static final Log LOG = LogFactory.getLog(AddressConverter.class);

  public Object getAsObject(
      FacesContext facesContext, UIComponent component, String reference) {
    if (reference == null || reference.length() == 0) {
      return null;
    }
    return new EmailAddress(reference);
  }

  public String getAsString(
      FacesContext facesContext, UIComponent component, Object object) {
    return object.toString();
  }

}
