//JavaScript代码区域
layui.use(['element', 'table'], function () {
    var $ = layui.$, element = layui.element, table = layui.table, form = layui.form ;
    form.on('select(suProjectSelect)', function(data){
        suProjectSelectEvent(data.value,$("#suTags"),"");
    });
    //方法级渲染
    table.render({
        elem: '#lay_table_swagger_config'
        , url: 'url/listPage?t=' + new Date().getTime()
        , toolbar: '#toolbarDemo'
        , title: 'swagger配置表'
        , method: 'post' //如果无需自定义HTTP类型，可不加该参数
        , contentType: 'application/json'
        , cols: [
            [
                {field: 'su_id', title: 'ID', width: 80 }
                , {field: 'si_title', title: '项目' ,width: 200}
                , {field: 'st_name', title: '标签', width: 120 }
                , {field: 'su_operationId', title: '实例方法名', width: 200 }
                , {field: 'su_url', title: '请求URL' ,width: 250}
                , {field: 'su_method', title: '请求方法', width: 100 }
                , {field: 'su_summary', title: '标题' }
                , {field: 'su_description', title: '描述' }
                , {field: 'su_createtime', title: '创建时间',width: 180 ,templet : "<span>{{layui.util.toDateString(d.su_createtime, 'yyyy-MM-dd HH:mm:ss')}}</span>" }
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
            var suProject = $("#suProject");
            suProject.empty();
            suProject.append('<option value="">项目</option>');
            for(var i in res.suProjects){
                if(parseInt(res.suProjects[i].siId) === parseInt(res.suProjectId)){
                    suProject.append('<option selected="selected" value='+res.suProjects[i].siId+'>'+res.suProjects[i].siTitle+'</option>');
                }else{
                    suProject.append('<option value='+res.suProjects[i].siId+'>'+res.suProjects[i].siTitle+'</option>');
                }
            }
            form.render();
        }
    });
    //执行表格数据重载
    var urlTableReload = function () {
        table.reload('lay_table_swagger_config', {
            page: { curr: 1 }
            , where: {
                'suProjectId':$("#suProject").val(),
                'suTags': $('#suTags').val(),
                'suOperationid': $('#suOperationid').val(),
                'suMethod': $('#suMethod').val()
            }
        }, 'data');
    };
    $('.demoTable .layui-btn').on('click', function(){
        switch($(this).data('type')){
            case 'searchBtn':
                urlTableReload();
                break;
        }
    });
    //监听头工具栏事件
    table.on('toolbar(lay_table_swagger_config)', function(obj){
        switch(obj.event){
            case 'addBtn':
                addBtn();
                break;
        }
    });
    //监听行工具栏事件
    table.on('tool(lay_table_swagger_config)', function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case 'look':
                window.open("swagger-ui/index"+data.su_project+"#/"+data.st_name+"/"+data.su_operationId,"_self");
                break;
            case 'del':
                var delHtml = '项目：'+data.si_title+'<br/>'+'方法：'+data.su_operationId+'<br/>'+'标题：'+data.su_summary+'<br/>';
                layer.confirm(delHtml, {
                    title: '是否删除该配置【'+data.su_id+'】？',
                    btn: ['删除', '取消']
                }, function () {
                    delUrl({'suId': data.su_id});
                }, function () {
                    layer.closeAll();
                });
                break;
            case 'edit':
                editBtn(data.su_id);
                break;
        }
    });
    //添加
    var addBtn = function(){
        $.ajax({
            url: 'url/getSwaggerUrl?t=' + new Date().getTime(),
            type: "POST",
            data:{},
            dataType: "html",
            success: function (data) {
                layOpen('添加配置', data,
                    function () {
                        form.render();
                        form.on('select(suProjectSelectAdd)', function(data){
                            suProjectSelectEvent(data.value,$("#suTagsAdd"),"");
                        });
                        //提交 - 操作
                        form.on('submit(url_add)', function (data) {
                            urlForm("", data.field);
                            return false;
                        });
                    }
                )
            },
            error: function () {
                alert("添加配置错误...")
            }
        });
    };
    //编辑
    var editBtn = function(suId){
        $.ajax({
            url: 'url/getSwaggerUrl?t=' + new Date().getTime(),
            type: "POST",
            data:{'suId':suId},
            dataType: "html",
            success: function (data) {
                layOpen('编辑配置', data,
                    function () {
                        form.render();
                        form.on('select(suProjectSelectAdd)', function(data){
                            suProjectSelectEvent(data.value,$("#suTagsAdd"),"");
                        });
                        suProjectSelectEvent(form.val('urlFormFilter').suProject,$("#suTagsAdd"),form.val('urlFormFilter').suTagsVal);
                        //提交 - 操作
                        form.on('submit(url_add)', function (data) {
                            urlForm(suId, data.field);
                            return false;
                        });
                    }
                )
            },
            error: function () {
                alert("编辑配置错误...")
            }
        });
    };
    // 删除
    var delUrl = function (jsonParam) {
        $.ajax({
            url: 'url/delSwaggerUrl?t=' + new Date().getTime(),
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
                        urlTableReload();
                    });
                } else {
                    alert("删除配置错误...")
                }
            },
            error: function () {
                layer.closeAll();
                alert("删除配置异常...")
            }
        });
    };
    //form提交
    var urlForm = function (suId, jsonParam) {
        $.ajax({
            url: 'url/submitSwaggerUrl?t=' + new Date().getTime(),
            type: "POST",
            data: JSON.stringify(jsonParam),
            headers: {'Content-Type': 'application/json;charset=utf8', 'suId': suId},
            dataType: "json",
            beforeSend: function () {
                layer.load(2);
            },
            success: function (data) {
                layer.closeAll();
                if (data.success) {
                    layer.msg('操作成功', {icon: 1, time: 800},function () {
                        urlTableReload();
                    });
                } else {
                    alert("提交 配置 异常...")
                }
            },
            error: function () {
                layer.closeAll();
                alert("提交 配置 错误...")
            }
        });
    };
    //项目下拉框 显示标签 事件
    var suProjectSelectEvent = function(suProjectId,suTags,suTagsVal){
        $.ajax({
            url: 'tags/list?t=' + new Date().getTime(),
            type: "POST",
            data: JSON.stringify({'suProjectId':suProjectId}),
            dataType: "json",
            contentType: 'application/json',
            beforeSend: function () {
                layer.load(2);
            },
            success: function (result) {
                layer.closeAll('loading');
                if (result.success) {
                    suTags.empty();
                    suTags.append('<option value="">标签</option>');
                    for(var key in result.data){
                        suTags.append('<option value='+key+'>'+result.data[key]+'</option>');
                    }
                    form.val('urlFormFilter', { "suTags": suTagsVal });
                    form.render();
                } else {
                    alert("加载标签错误...")
                }
            },
            error: function () {
                layer.closeAll('loading');
                alert("加载标签异常...")
            }
        });
    };

});