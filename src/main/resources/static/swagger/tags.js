//JavaScript代码区域
layui.use(['element', 'table'], function () {
    var $ = layui.$, element = layui.element, table = layui.table, form = layui.form ;
    //方法级渲染
    table.render({
        elem: '#lay_table_swagger_tags'
        , url: 'tags/listPage?t=' + new Date().getTime()
        , toolbar: '#toolbarDemo'
        , title: 'swagger标签表'
        , method: 'post' //如果无需自定义HTTP类型，可不加该参数
        , contentType: 'application/json'
        , cols: [
            [
                {field: 'st_id', title: 'ID', width: 80 }
                , {field: 'si_title', title: '项目' ,width: 200}
                , {field: 'st_name', title: '标签' ,width: 250}
                , {field: 'st_description', title: '描述'}
                , {field: 'st_remark', title: '备注' ,width: 400}
                , {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 180}
            ]
        ]
        , page: true
        , height: 'full-200'
        , request: {
            pageName: 'pageIndex' //页码的参数名称，默认：page
            , limitName: 'pageCount' //每页数据量的参数名，默认：limit
        }
        , done: function (res, curr, count) {
            var stProject = $("#stProject");
            stProject.empty();
            stProject.append('<option value="">项目</option>');
            for(var i in res.stProjects){
                if(parseInt(res.stProjects[i].siId) === parseInt(res.stProjectId)){
                    stProject.append('<option selected="selected" value='+res.stProjects[i].siId+'>'+res.stProjects[i].siTitle+'</option>');
                }else{
                    stProject.append('<option value='+res.stProjects[i].siId+'>'+res.stProjects[i].siTitle+'</option>');
                }
            }
            form.render();
        }
    });
    //执行表格数据重载
    var tagsTableReload = function () {
        table.reload('lay_table_swagger_tags', {
            page: { curr: 1 }
            , where: {
                'stProjectId':$("#stProject").val()
            }
        }, 'data');
    };
    $('.demoTable .layui-btn').on('click', function(){
        switch($(this).data('type')){
            case 'searchBtn':
                tagsTableReload();
                break;
        }
    });
    //监听头工具栏事件
    table.on('toolbar(lay_table_swagger_tags)', function(obj){
        switch(obj.event){
            case 'addBtn':
                addBtn();
                break;
        }
    });
    //监听行工具栏事件
    table.on('tool(lay_table_swagger_tags)', function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case 'look':
                window.open("swagger-ui/index"+data.st_project+"#/"+data.st_name,"_self");
                break;
            case 'del':
                layer.confirm('是否删除该标签【'+data.st_id+'】?', {
                    title:'删除',
                    btn: ['删除', '取消']
                }, function () {
                    delTags({'stId': data.st_id});
                }, function () {
                    layer.closeAll();
                });
                break;
            case 'edit':
                editBtn(data.st_id);
                break;
        }
    });
    //添加
    var addBtn = function(){
        $.ajax({
            url: 'tags/getSwaggerTags?t=' + new Date().getTime(),
            type: "POST",
            data:{},
            dataType: "html",
            success: function (data) {
                layOpen('添加标签', data,
                    function () {
                        form.render();
                        //提交 - 操作
                        form.on('submit(tags_add)', function (data) {
                            tagsForm("", data.field);
                            return false;
                        });
                    }
                )
            },
            error: function () {
                alert("添加标签错误...")
            }
        });
    };
    //编辑
    var editBtn = function(stId){
        $.ajax({
            url: 'tags/getSwaggerTags?t=' + new Date().getTime(),
            type: "POST",
            data:{'stId':stId},
            dataType: "html",
            success: function (data) {
                layOpen('编辑标签', data,
                    function () {
                        form.render();
                        //提交 - 操作
                        form.on('submit(tags_add)', function (data) {
                            tagsForm(stId, data.field);
                            return false;
                        });
                    }
                )
            },
            error: function () {
                alert("编辑标签错误...")
            }
        });
    };
    //删除
    var delTags = function (jsonParam) {
        $.ajax({
            url: 'tags/delSwaggerTags?t=' + new Date().getTime(),
            type: "POST",
            data: JSON.stringify(jsonParam),
            dataType: "json",
            contentType: 'application/json',
            beforeSend: function () {
                layer.load(2);
            },
            success: function (data) {
                layer.closeAll();
                if (data.success) {
                    layer.msg('操作成功', {icon: 1, time: 800}, function () {
                        tagsTableReload();
                    });
                } else {
                    alert("删除 标签 异常...")
                }
            },
            error: function () {
                layer.closeAll();
                alert("删除 标签 错误...")
            }
        });
    };
    //form提交
    var tagsForm = function (stId, jsonParam) {
        $.ajax({
            url: 'tags/submitSwaggerTags?t=' + new Date().getTime(),
            type: "POST",
            data: JSON.stringify(jsonParam),
            headers: {'Content-Type': 'application/json;charset=utf8', 'stId': stId},
            dataType: "json",
            beforeSend: function () {
                layer.load(2);
            },
            success: function (data) {
                layer.closeAll();
                if (data.success) {
                    layer.msg('操作成功', {icon: 1, time: 800},function () {
                        tagsTableReload();
                    });
                } else {
                    alert("提交 标签 异常...")
                }
            },
            error: function () {
                layer.closeAll();
                alert("提交 标签 错误...")
            }
        });
    }
});