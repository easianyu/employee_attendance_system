/**
 * 以下是视频拍摄相关操作
 *
 */
var video;//视频流对象
var context;//绘制对象
var canvas;//画布对象
$(function () {
    var flag = false;
    //开启摄像头
    $("#open").click(function () {
        //判断摄像头是否打开
        if (!flag) {
            //调用摄像头初始化
            open();
            flag = true;
            var videoarea = document.getElementById("videoarea");
            videoarea.style.display = "";
        }
    });
    //关闭摄像头
    $("#close").click(function () {
        //判断摄像头是否打开
        if (flag) {
            video.srcObject.getTracks()[0].stop();
            flag = false;
            var videoarea = document.getElementById("videoarea");
            videoarea.style.display = "none";
        }
    });
    //拍照确定
    $("#CatchCode").click(function () {
        if (flag) {
            context.drawImage(video, 0, 0, 330, 250);
            CatchCode();
        } else
            alert("请先开启摄像头!");
    });

    //选择打卡类型确定
    $("#ensuretype").click(function () {
        var videoButton = document.getElementById("videobutton");
        if (videoButton.style.display === "none") {
            videoButton.style.display = "";
        } else {
            videoButton.style.display = "none";
        }

    })
});



//开启摄像头
function open() {
    //获取摄像头对象
    canvas = document.getElementById("canvas");
    context = canvas.getContext("2d");
    //获取视频流
    video = document.getElementById("video");
    var videoObj = {
        "video": true
    }, errBack = function (error) {
        console.log("Video capture error: ", error.code);
    };
    context.drawImage(video, 0, 0, 330, 250);
    //初始化摄像头参数
    if (navigator.getUserMedia || navigator.webkitGetUserMedia
        || navigator.mozGetUserMedia) {
        navigator.getUserMedia = navigator.getUserMedia
            || navigator.webkitGetUserMedia
            || navigator.mozGetUserMedia;
        navigator.getUserMedia(videoObj, function (stream) {
            video.srcObject = stream;
            video.play();
        }, errBack);
    }
}

//将摄像头拍取的图片转换为Base64格式字符串
function getBase64() {
    //获取当前图像并转换为Base64的字符串
    var imgSrc = canvas.toDataURL("image/png");
    //截取字符串
    return imgSrc.substring(22);
}