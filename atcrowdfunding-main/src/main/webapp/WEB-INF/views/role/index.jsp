
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="GB18030">
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
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input class="form-control has-success" type="text" name="keyWord"  placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="button" class="btn btn-warning" id="btnSearch"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;" id="deleteBath"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button type="button" class="btn btn-primary" style="float:right;" id="btnAdd"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox" id="checkAll"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%-- 使用异步获取角色数据--%>

                            </tbody>
                            <tfoot>
                            <tr >
                                <td colspan="6" align="center">
                                    <ul class="pagination">

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

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">添加角色信息</h4>
            </div>
            <div class="modal-body">
                <form role="form" method="post">

                    <div class="form-group">
                        <label for="exampleInputPassword1">角色名称</label>
                        <input type="text" class="form-control" id="exampleInputPassword1" name="name" placeholder="请输入角色名称">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="btnSave">保存</button>
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

        // 异步获取数据库信息    1表示获取第一页的信息
        loadData(1);
    });
    var keyWord;//查询的条件
    function loadData(pageNum){

        $.getJSON("${path}/role/loadData",{"pageNum":pageNum,"pageSize":"2","keyWord":keyWord},function(res){//res 包含了 角色的信息和分页的信息
           if(res=="403"){
               layer.msg("没有权限查询该功能");
           }else {
               showRole(res.list);
               showPage(res);
           }
        });



    }
    //使用异步动态的将角色数据显示到页面上
    function showRole(roleList){
        var content="";
        for (var i=0;i<roleList.length;i++){
            content+='<tr>';
            content+='<td>1</td>';
            content+='<td><input type="checkbox" class="roleCheck" id="'+roleList[i].id+'"></td>';
            content+='<td>'+roleList[i].name+'</td>';
            content+='<td>';
            content+='	<button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>';
            content+='	<button type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>';
            content+='	<button type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>';
            content+='</td>';
            content+='</tr>';

        }
        $("tbody").html(content);



    }
    //使用异步动态的将分页的信息显示到页面上
    function showPage(pageInfo){
        var content="";
        if(pageInfo.isFirstPage){
            content+='<li class="disabled"><a href="#">上一页</a></li>';
        }else{
            content+='<li><a  onclick="loadData('+(pageInfo.pageNum-1)+')">上一页</a></li>';

        }
        for (var i=0;i<pageInfo.navigatepageNums.length;i++){
            if(pageInfo.pageNum==pageInfo.navigatepageNums[i]){
                content+='<li class="active"><a onclick="loadData('+(pageInfo.navigatepageNums[i])+')">'+(pageInfo.navigatepageNums[i])+' <span class="sr-only">(current)</span></a></li>';
            }else{
                content+='<li ><a onclick="loadData('+(pageInfo.navigatepageNums[i])+')">'+(pageInfo.navigatepageNums[i] )+'<span class="sr-only">(current)</span></a></li>';
            }
        }

        if(pageInfo.isLastPage){
            content+='<li class="disabled"><a href="#">下一页</a></li>';
        }else{
            content+='<li><a  onclick="loadData('+(pageInfo.pageNum+1)+')">下一页</a></li>';
        }
        $(".pagination").html(content);
    }

    //给查询按钮添加单击事件

    $("#btnSearch").click(function () {
       keyWord=$("input[name='keyWord']").val();

       loadData(1);
    });

    //弹出模态框
    $("#btnAdd").click(function(){
        $('#myModal').modal({
            show:true,
            backdrop:false,
            keyboard:false

        })

    });

    //给添加按钮指定单击事件
    $("#btnSave").click(function(){
        var roleName=$("input[name='name']").val();
        $.post("${path}/role/save",{"name":roleName},function(res){
            if(res=="yes"){
                layer.msg("添加角色成功!!",{time:1000,icon:6},function(){
                    $('#myModal').modal("hide");
                    loadData(100000);
                })

            }else{
                layer.msg("添加角色成功!!",{icon:5})
            }
        });
    });
    //给复选框绑定单击事件
    var  checkAll=$("#checkAll");
    checkAll.click(function () {
        var roleCheck = $(".roleCheck");
        $.each(roleCheck,function (i,check) {
            check.checked =checkAll.get(0).checked;
            
        });
    });
    //给删除按钮绑定单击事件
    $("#deleteBath").click(function(){
        var checks=$(".roleCheck:checked");
        if(checks.length==0){
            layer.msg("没有被删除的选项!!");
        }else{

            layer.confirm("确定要删除吗?",{btn:["确定","取消"]},function(){
                    var ids=new Array();
                    $.each(checks,function(i,check){
                        var id= $(check).attr("id");
                        ids.push(id);
                    });

                    $.get("${path}/role/deleteBath?ids="+ids,function(res){
                        if(res=="yes"){
                            layer.msg("删除成功!!");
                            loadData(1);
                        }
                    });

                });
        }
    });

</script>
</body>
</html>

