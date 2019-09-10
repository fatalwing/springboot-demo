<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>页面跳转中……</title>
</head>
<body>
<div>loading...</div>
</body>
</html>

<script>
String.prototype.format=function() {
    if(arguments.length==0) return this;
    for(var s=this, i=0; i<arguments.length; i++)
        s=s.replace(new RegExp("\\{"+i+"\\}","g"), arguments[i]);
    return s;
};
var wechatAuthUrl = "https://mp.weixin.qq.com/cgi-bin/componentloginpage?component_appid={0}&pre_auth_code={1}&redirect_uri={2}";
var redirectTo = "${url}/wechat/auth/platform/{0}/{1}/redirect.htm";

var componentAppid = '${componentAppid}';
var state = '${state}';
var preAuthCode = '${preAuthCode}';

var rt = redirectTo.format(componentAppid, state);
var wau = wechatAuthUrl.format(componentAppid, preAuthCode, rt);

window.location.replace(wau);

</script>