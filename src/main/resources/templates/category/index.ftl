<html>
<#include "../common/header.ftl" />
<body>

<div id="wrapper" class="toggled">
<#--左侧导航栏-->
<#include "../common/nav.ftl" />

    <#--新增/修改类目-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" method="post" action="/sell/seller/category/save">
                        <input type="hidden" name="categoryId" value="${(category.categoryId)!''}" />
                        <div class="form-group">
                            <label>名字</label>
                            <input name="categoryName" type="text" class="form-control" value="${(category.categoryName)!''}" />
                        </div>
                        <div class="form-group">
                            <label>type</label>
                            <input name="categoryType" type="text" class="form-control" value="${(category.categoryType)!''}" />
                        </div>
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>

        </div>

    </div>

</div>

</body>
</html>

