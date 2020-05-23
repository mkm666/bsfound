//创建xhr对象
function createXHR() {
    //判断是否支持这个属性,如果支持,则是IE7+,FF,Chrome,...
    if (window.XMLHttpRequest) {
        return new XMLHttpRequest();
    }
    //IE6-
    return new ActiveXObject("Microsoft.XMLHTTP");
}
