<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>授权结果</title>
</head>
<body>

<div>
<#if errorCode=="10000">
    授权成功
<#else>
    ${errorInfo}
</#if>
</div>

</body>
</html>

<script>
    window.parent.postMessage({"errorCode":"${errorCode}","success":${success},"errorInfo":"${errorInfo}","data":{}}, '*');
</script>