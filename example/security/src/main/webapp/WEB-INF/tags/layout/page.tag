<%--
 * Copyright 2002-2006 The Apache Software Foundation.
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
--%>
<%@ taglib uri="http://myfaces.apache.org/tobago/component" prefix="tc" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<f:view>
  <tc:page label="Tobago Security Demo" id="page" width="640px" height="480px">
    <f:facet name="layout">
      <tc:gridLayout margin="5"/>
    </f:facet>

    <tc:box label="User: #{facesContext.externalContext.request.userPrincipal.name}">
      <f:facet name="layout">
        <tc:gridLayout margin="5px" />
      </f:facet>

      <jsp:doBody/>

    </tc:box>

  </tc:page>
</f:view>
