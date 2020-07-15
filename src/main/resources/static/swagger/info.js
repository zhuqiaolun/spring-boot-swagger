//JavaScript代码区域
layui.use(['element', 'table'], function () {
    var $ = layui.$, element = layui.element, table = layui.table, form = layui.form ;
    //方法级渲染
    table.render({
        elem: '#lay_table_swagger'
        , url: 'swagger/listPage?t=' + new Date().getTime()
        , toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
        , title: 'swagger项目表'
        , method: 'post' //如果无需自定义HTTP类型，可不加该参数
        , contentType: 'application/json'
        , cols: [
            [
                {field: 'siId', title: 'ID', width: 80 }
                , {field: 'siTitle', title: '项目' ,width: 250}
                , {field: 'siDescription', title: '描述'}
                , {field: 'siVersion', title: '版本',width: 150 }
                , {field: 'siSchemeshttp', title: '协议http',width: 100, templet:function (d) { return d.siSchemeshttp?'支持':'不支持';} }
                , {field: 'siSchemeshttps', title: '协议https',width: 100,templet:function (d) { return d.siSchemeshttps?'支持':'不支持';} }
                , {field: 'siServerhost', title: '地址',width: 150 }
                , {field: 'siServerport', title: '端口',width: 100 }
                , {field: 'siServerpath', title: '路径',width: 250 }
                , {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 180 }
            ]
        ]
        , page: true
        , height: 'full-200'
        , request: {
            pageName: 'pageIndex' //页码的参数名称，默认：page
            , limitName: 'pageCount' //每页数据量的参数名，默认：limit
        }
        , done: function (res, curr, count) {

        }
    });
    //执行表格数据重载
    var infoTableReload = function () {
        table.reload('lay_table_swagger', {
            page: { curr: 1 }
            , where: {
                'siTitle':$("#siTitle").val()
            }
        }, 'data');
    };
    $('.demoTable .layui-btn').on('click', function(){
        switch($(this).data('type')){
            case 'searchBtn':
                infoTableReload();
                break;
        }
    });
    //监听头工具栏事件
    table.on('toolbar(lay_table_swagger)', function(obj){
        switch(obj.event){
            case 'addBtn':
                addBtn();
                break;
        }
    });
    //监听行工具栏事件
    table.on('tool(lay_table_swagger)', function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case 'look':
                window.open("swagger-ui/index"+data.siId,"_self");
                break;
            case 'del':
                layer.confirm('是否删除该项目【'+data.siId+'】?', {
                    title:'删除',
                    btn: ['删除', '取消']
                }, function () {
                    delInfos({'siId': data.siId});
                }, function () {
                    layer.closeAll();
                });
                break;
            case 'edit':
                editBtn(data.siId);
                break;
        }
    });
    //添加
    var addBtn = function(){
        $.ajax({
            url: 'swagger/getSwaggerInfo?t=' + new Date().getTime(),
            type: "POST",
            data:{},
            dataType: "html",
            success: function (data) {
                layOpen('添加项目', data,
                    function () {
                        form.render();
                        //提交 - 操作
                        form.on('submit(swagger_add)', function (data) {
                            var obj = data.field;
                            infoForm({
                                'title':obj.title,
                                'description':obj.description,
                                'version':obj.version,
                                'schemes_http':obj.schemes_http,
                                'schemes_https':obj.schemes_https,
                                'server_host':obj.serverhost,
                                'server_port':obj.serverport,
                                'server_path':obj.serverpath,
                                'security_definitions':obj.securitydefinitions,
                                'contact_name':obj.contactname,
                                'contact_email':obj.contactemail,
                                'contact_url':obj.contacturl
                            });
                            return false;
                        });
                    }
                )
            },
            error: function () {
                alert("添加项目错误...")
            }
        });
    };
    //编辑
    var editBtn = function(siId){
        $.ajax({
            url: 'swagger/getSwaggerInfo?t=' + new Date().getTime(),
            type: "POST",
            data:{'siId':siId},
            dataType: "html",
            success: function (data) {
                layOpen('编辑项目', data,
                    function () {
                        form.render();
                        //提交 - 操作
                        form.on('submit(swagger_add)', function (data) {
                            var obj = data.field;
                            infoForm({
                                'siId':siId,
                                'title':obj.title,
                                'description':obj.description,
                                'version':obj.version,
                                'schemes_http':obj.schemes_http,
                                'schemes_https':obj.schemes_https,
                                'server_host':obj.serverhost,
                                'server_port':obj.serverport,
                                'server_path':obj.serverpath,
                                'security_definitions':obj.securitydefinitions,
                                'contact_name':obj.contactname,
                                'contact_email':obj.contactemail,
                                'contact_url':obj.contacturl
                            });
                            return false;
                        });
                    }
                )
            },
            error: function () {
                alert("编辑项目错误...")
            }
        });
    };
    //删除
    var delInfos = function (jsonParam) {
        $.ajax({
            url: 'swagger/delSwaggerInfo?t=' + new Date().getTime(),
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
                        infoTableReload();
                    });
                } else {
                    alert("删除 项目 异常...")
                }
            },
            error: function () {
                layer.closeAll();
                alert("删除 项目 错误...")
            }
        });
    };
    //form提交
    var infoForm = function (jsonParam) {
        $.ajax({
            url: 'swagger/submitSwaggerInfo?t=' + new Date().getTime(),
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
                    layer.msg('操作成功', {icon: 1, time: 800},function () {
                        infoTableReload();
                    });
                } else {
                    alert("提交 项目 异常...")
                }
            },
            error: function () {
                layer.closeAll();
                alert("提交 项目 错误...")
            }
        });
    }
});