<%--
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
--%>

<%@ taglib uri="http://myfaces.apache.org/tobago/component" prefix="tc" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib tagdir="/WEB-INF/tags/layout" prefix="layout" %>

<layout:overview>
<jsp:body>
<tc:panel>
<f:facet name="layout">
  <tc:gridLayout rows="9*;16*"/>
</f:facet>

<tc:out escape="false" value="(UNDER CONSTRUCTION) #{overviewBundle.tree_text}"/>

<%-- column --%>

<tc:box label="#{overviewBundle.tree_sampleTitle}">
  <tc:tabGroup state="#{overviewController.treeTabsState}" switchType="reloadPage">
    <tc:tab label="#{overviewBundle.treeLabel}">
      <f:facet name="layout">
        <tc:gridLayout columns="2*;1*"/>
      </f:facet>

      <tc:tree id="tree"
               showIcons="#{demo.showIcons}"
               showJunctions="#{demo.showJunctions}"
               showRootJunction="#{demo.showRootJunction}"
               showRoot="#{demo.showRoot}">
        <tc:treeData value="#{demo.tree}" var="node" id="data">
          <tc:treeNode label="#{node.userObject.name}"
                       id="template"
                       expanded="true"
                       value="#{node}"
                       image="image/feather.png"/>


          <%--
          todo: tree editor
          todo: events instead of state
          todo: test action listener

          actionListener="org.apache.myfaces.tobago.example.demo.actionlistener.TreeEditor"
          markup="#{node.userObject.markup}"
          tip="#{node.userObject.tip}"
          action="#{node.userObject.action}"
          disabled="#{node.userObject.disabled}"
          --%>
        </tc:treeData>
      </tc:tree>

      <tc:panel>
        <f:facet name="layout">
          <tc:gridLayout rows="fixed;fixed;fixed;fixed;1*;fixed;fixed;fixed"/>
        </f:facet>

        <tc:selectBooleanCheckbox label="#{overviewBundle.treeShowIcons}"
                                  value="#{demo.showIcons}"
                                  disabled="#{overviewController.treeTabsState != 0}"/>
        <tc:selectBooleanCheckbox label="#{overviewBundle.treeShowJunctions}"
                                  value="#{demo.showJunctions}"
                                  disabled="#{overviewController.treeTabsState != 0}"/>
        <tc:selectBooleanCheckbox label="#{overviewBundle.treeShowRootJunction}"
                                  value="#{demo.showRootJunction}"
                                  disabled="#{overviewController.treeTabsState != 0}"/>
        <tc:selectBooleanCheckbox label="#{overviewBundle.treeShowRoot}"
                                  value="#{demo.showRoot}"
                                  disabled="#{overviewController.treeTabsState != 0}"/>
        <tc:cell/>
        <tc:selectOneChoice value="#{overviewController.treeSelectMode}">
          <f:selectItems value="#{overviewController.treeSelectModeItems}"/>
        </tc:selectOneChoice>

        <tc:selectBooleanCheckbox label="#{overviewBundle.treeMutable}"
                                  value="#{demo.mutable}"
                                  disabled="#{overviewController.treeTabsState != 0}"/>
        <tc:button action="redisplay" label="#{overviewBundle.submit}"/>
      </tc:panel>

    </tc:tab>
    <tc:tab label="#{overviewBundle.treeListboxLabel} (todo)">

    </tc:tab>
  </tc:tabGroup>

</tc:box>

</tc:panel>
</jsp:body>
</layout:overview>
