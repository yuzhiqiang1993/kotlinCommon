/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

// Wait for the deviceready event before using any of Cordova's device APIs.
// See https://cordova.apache.org/docs/en/latest/cordova/events/events.html#deviceready
document.addEventListener('deviceready', onDeviceReady, false);

function onDeviceReady() {
    // Cordova is now initialized. Have fun!

    console.log('deviceready Running cordova-' + cordova.platformId + '@' + cordova.version);
    document.getElementById('deviceready').classList.add('ready');
    console.log("navigator.camera" + navigator.camera);
    /*监听电池电量*/
    window.addEventListener("batterystatus", onBatteryStatus, false);


    /*拍照*/
    document.getElementById('btnCamera').addEventListener('click', function () {
        takePicture()
    })

    /*原生标签的拍照*/
    document.getElementById("inputImage").addEventListener("change", handleImageChange);

}


function onBatteryStatus(status) {
    console.log('onBatteryStatus', status)
    document.getElementById('batterystatus').innerText = "当前电量：" + status.level + "%"
    console.log("Level: " + status.level + " isPlugged: " + status.isPlugged);
    throw Error("test error")
}


function takePicture() {
    console.log("takePicture")
    navigator.camera.getPicture(cameraCallback, cameraError, {
        quality: 50,
        destinationType: Camera.DestinationType.DATA_URL
    });
}


function cameraCallback(imageData) {
    console.log('cameraCallback:', imageData)
    const image = document.getElementById('myImg');
    image.src = "data:image/jpeg;base64," + imageData;
}

function cameraError(msg) {
    console.log('cameraError:', msg)
}


function handleImageChange(event) {
    // 获取用户选择的文件
    console.log('handleImageChange', event)
    const file = event.target.files[0];
    console.log(`file`, file)
    if (file) {
        // 创建一个 FileReader 实例
        const reader = new FileReader();

        // 设置 onload 事件处理器
        reader.onload = function (e) {
            // 获取图片的 Base64 编码数据 URL
            const dataUrl = e.target.result;

            // 在这里处理图片，例如将其显示在一个 <img> 元素中
            const image = document.getElementById('myImg');
            console.log('image', image)
            image.src = dataUrl;
        };

        // 以 Data URL 格式读取文件
        reader.readAsDataURL(file);
    }
}
