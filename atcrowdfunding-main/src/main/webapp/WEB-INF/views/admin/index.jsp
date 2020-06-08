
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="GB18030">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <jsp:include page="/WEB-INF/common/css.jsp"></jsp:include>

    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
        table tbody tr:nth-child(odd){background:#F4F4F4;}
        table tbody td:nth-child(even){color:#C00;}
    </style>
</head>

<body>

<jsp:include page="/WEB-INF/common/nav.jsp"></jsp:include>

<div class="container-fluid">
    <div class="row">
        <jsp:include page="/WEB-INF/common/side.jsp"></jsp:include>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;" action="${path}/admin/index" method="post" id="queryForm">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input class="form-control has-success" type="text" placeholder="请输入查询条件" name="keyWord" value="${param.keyWord}">
                            </div>
                        </div>
                        <button type="button"onclick="${'#queryForm'}.submit()" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <sec:authorize access="hasRole('宗师')">
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;" id="deleteBath"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    </sec:authorize>
                     <button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='${path}/admin/toAdd'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox" id="checkAll"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${pageInfo.list}" var="admin">
                            <tr>
                                <td>1</td>
                                <td><input type="checkbox" class="checkboxAdmin" id="${admin.id}"></td>
                                <td>${admin.loginacct}</td>
                                <td>${admin.username}</td>
                                <td>${admin.email}</td>
                                <td>
                                    <button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>
                                    <%--点击按钮跳转到修改页面的方法--%>
                                    <button type="button" class="btn btn-primary btn-xs" onclick="location.href='${path}/admin/toUpdate?pageNum=${pageInfo.pageNum}&id=${admin.id}'"><i class=" glyphicon glyphicon-pencil"></i></button>
                                    <button type="button" class="btn btn-danger btn-xs" onclick="location.href='${path}/admin/delete?id=${admin.id}'"><i class=" glyphicon glyphicon-remove"></i></button>
                                </td>
                            </tr>
                            </c:forEach>
                            </tbody>
                            <tfoot>
                            <tr >
                                <td colspan="6" align="center">
                                    <ul class="pagination">
                                        <c:if test="${pageInfo.isFirstPage}">
                                        <li class="disabled"><a href="#">上一页</a></li>
                                        </c:if>
                                        <c:if test="${not pageInfo.isFirstPage}">
                                            <li ><a href="${path}/admin/index?keyWord=${param.keyWord}&pageNum=${pageInfo.pageNum-1}">上一页</a></li>
                                        </c:if>
                                        <c:forEach items="${pageInfo.navigatepageNums}" var="i">
                                            <c:if test="${pageInfo.pageNum==i}">
                                                <li class="active"><a href="${path}/admin/index?keyWord=${param.keyWord}&pageNum=${i}">${i} <span class="sr-only">(current)</span></a></li>
                                            </c:if>
                                            <c:if test="${pageInfo.pageNum!=i}">
                                                <li><a href="${path}/admin/index?pageNum=${i}">${i} <span class="sr-only">(current)</span></a></li>
                                            </c:if>

                                        </c:forEach>


                                        <c:if test="${pageInfo.isLastPage}">
                                            <li class="disabled"><a href="#">下一页</a></li>
                                        </c:if>
                                        <c:if test="${not pageInfo.isLastPage}">
                                            <li ><a href="${path}/admin/index?keyWord=${param.keyWord}&pageNum=${pageInfo.pageNum+1}">下一页</a></li>
                                        </c:if>
                                    </ul>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/common/js.jsp"></jsp:include>
<script src="${path}/static/layer/layer.js"></script>
<script type="text/javascript">
    $(function () {
        $(".list-group-item").click(function(){
            if ( $(this).find("ul") ) {
                $(this).toggleClass("tree-closed");
                if ( $(this).hasClass("tree-closed") ) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });

    //获取总的复选框
    var checkAll = $("#checkAll");

    var checkboxAdmin = $(".checkboxAdmin");

    checkAll.click(function () {
        $.each(checkboxAdmin,function (i,checkbox) {
            checkbox.checked=checkAll[0].checked;

        });

    });
        var deleteBath=$("#deleteBath");

        deleteBath.click(function(){
            //获取所有选中的复选框
            var checkeds=  $(".checkboxAdmin:checked");
            if(checkeds.length==0){
               // alert("您还没有选中要删除的行")
                layer.alert("您还没有选中要删除的行")
            }else {
/*
                var b = confirm("您确定要删除吗?");
                if(b==true){

                    //保存被选中的行的id
                    var ids=new Array();
                    $.each(checkeds,function(i,check){
                        var id= $(check).attr("id");

                        ids.push(id);
                    });
                    location.href="${path}/admin/deleteBath?ids="+ids;

                }*/
                layer.confirm("您确定要删除吗?",
                    {icon:6,btn:["确定","取消"]},
                    function(index){
                        var ids=new Array();//保存被选中的行的id
                        $.each(checkeds,function(i,check){
                            var id= $(check).attr("id");

                            ids.push(id);
                        });
                        location.href="${appPath}/admin/deleteBath?ids="+ids;
                        layer.msg("删除成功",{time:2000,icon:6});
                        layer.close(index);
                    },
                    function(index){
                        layer.msg("取消操作!!",{time:2000,icon:5,shift:6});
                        layer.close(index);
                    });
            }
        });
 });


</script>
</body>
</html>
