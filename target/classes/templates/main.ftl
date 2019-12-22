<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">


<@c.page>
<div class="form-row">
    <div class="form-group col-md-6">
        <form method="get" action="/main" class="form-inline">
            <input type="text" name="filter" class="form-control" value="${filter!}" placeholder="Search by door">
            <button type="submit" class="btn btn-primary ml-3">Search</button>
        </form>
    </div>
</div>
    <#if isAdmin>
    <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
        Add new door
    </a>
    </#if>
    <div class="collapse <#if door??>show</#if>" id="collapseExample">
        <div class="form-group mt-3">
            <form method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <input type="text" class="form-control ${(doornameError??)?string('is-invalid', '')}"
                           value="<#if message??>${door.doorname}</#if>" name="doorname" placeholder="Enter door name / number"/>
                    <#if doornameError??>
                    <div class="invalid-feedback">
                        ${doornameError}
                    </div>
                    </#if>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control"
                           value="<#if message??>${door.password}</#if>" name="password" placeholder="Enter a description / location of the door"/>
                    <#if passwordError??>
                        <div class="invalid-feedback">
                            ${passwordError}
                        </div>
                    </#if>
                </div>
                <div class="form-group">
                    <div class="custom-file">
                        <input type="file" name="file" id="customFile">
                        <label class="custom-file-label" for="customFile">Choose file</label>
                    </div>
                    </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Add</button>
                </div>
            </form>
        </div>
    </div>

    <div class="card-columns">
        <#list doors as door>
            <div class="card my-3">
                <#if door.filename??>
                    <img src="/img/${door.filename}" class="card-img-top">
                </#if>
                <div class="m-2">
                    <span>${door.doorname}</span>
                    <div class="text-right">
                    <button type="button" id="btn1" class="btn btn-danger" onclick="change_color(this)"><span>Closed</span></button>
                    </div>
                </div>
                <div class="card-footer text-muted">
                    ${door.password}
                </div>
            </div>
        <#else>
            No door
        </#list>
    </div>
</@c.page>

<script>
    function change_color(elem) {
        elem.className =   elem.className != 'btn btn-success' ?  'btn btn-success' : 'btn btn-danger';
    }

    $("button").click(function() {
        $(this).text(function(i, text) {

            return text === "Closed" ? "Opened" : "Closed";
        })
    });

</script>