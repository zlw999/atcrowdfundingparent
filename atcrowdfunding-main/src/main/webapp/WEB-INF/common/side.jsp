<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="col-sm-3 col-md-2 sidebar">
    <div class="tree">
        <ul style="padding-left:0px;" class="list-group">
            <c:forEach items="${listParent}" var="parent">

                <!--没有子节点的父节点-->
                <c:if test="${parent.listChild.size()==0}">
                    <li class="list-group-item tree-closed" >
                        <a href="${path}/${parent.url}"><i class="${parent.icon}"></i> ${parent.name}</a>
                    </li>
                </c:if>
                <!--有子节点的父节点-->
                <c:if test="${parent.listChild.size()!=0}">
                    <!--父节点-->
                    <li class="list-group-item tree-closed">
                        <span><i class="${parent.icon}"></i>${parent.name} <span class="badge" style="float:right">${parent.listChild.size()}</span></span>
                        <!---子节点-->
                        <ul style="margin-top:10px;display:none;">
                            <c:forEach items="${parent.listChild}" var="child">
                                <li style="height:30px;">
                                    <a href="${path}/${child.url}"><i class="${child.icon}"></i>${child.name}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </li>
                </c:if>
            </c:forEach>
        </ul>
    </div>
</div>