var count =1;
function gpt(room, user, msg, callback) {
    const url = "http://222.121.47.141:8080/cor/"+room+"/"+user+"?message="+msg;
    const params = JSON.stringify({
        roomId: room,
        userId: user,
        message: msg
    });

    httpPostRequest(url, params, function(error, response) {
        if (error) {
            callback("Error: " + error.message);
        } else {
            try {
                const jsonResponse = JSON.parse(response);
                callback(jsonResponse.answer);
            } catch (e) {
                callback("Error: " + e.message);
            }
        }
    });
}
function simsim(msg){
    const apiKey = "RKm4diizuJflPLZT-ugFzuTTJWfkj.-as9EmwJPE";
    const url = "https://wsapi.simsimi.com/190410/talk";
    const data = JSON.stringify({
        utext: msg,
        lang: "ko"
    });

    const xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("x-api-key", apiKey);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                const response = JSON.parse(xhr.responseText);
                console.log('Success:', response);
            } else {
                console.error('Error:', xhr.statusText);
            }
        }
    };

    xhr.send(data);
}

function httpPostRequest(url, params, callback) {
    try {
        const javaUrl = new java.net.URL(url);
        const connection = javaUrl.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        const os = connection.getOutputStream();
        const writer = new java.io.OutputStreamWriter(os, "UTF-8");
        writer.write(params);
        writer.flush();
        writer.close();
        os.close();

        const responseCode = connection.getResponseCode();
        if (responseCode === java.net.HttpURLConnection.HTTP_OK) {
            const is = connection.getInputStream();
            const reader = new java.io.BufferedReader(new java.io.InputStreamReader(is, "UTF-8"));
            let line;
            const response = new java.lang.StringBuilder();
            while ((line = reader.readLine()) !== null) {
                response.append(line.trim());
            }
            reader.close();
            callback(null, response.toString());
        } else {
            callback(new Error("HTTP request failed with response code " + responseCode));
        }
    } catch (e) {
        callback(e);
    }
}

function responseFix(room, msg, sender, isGroupChat, replier, imageDB, packageName) {
    count= count+1;
    if (msg.startsWith("@종봇")) {
        let cmd = msg.substr(3);
        gpt(room, sender, cmd, function(result) {
            replier.reply(result);
        });
    }else if(msg.startsWith("[나를 멘션]")){
        let cmd = msg.substr(12);
        gpt(room, sender, cmd, function(result) {
            replier.reply(result);
        });
    }

    if(count%100===1){
        replier.reply("그러게");
    }
}

function onNotificationPosted(sbn, sm) {
    var packageName = sbn.getPackageName();
    if (!packageName.startsWith("com.kakao.tal")) return;
    var actions = sbn.getNotification().actions;
    if (actions == null) return;
    var act = actions[actions.length - 1];
    var bundle = sbn.getNotification().extras;

    var msg = bundle.get("android.text").toString();
    var sender = bundle.getString("android.title");
    var room = bundle.getString("android.subText");
    if (room == null) room = bundle.getString("android.summaryText");
    var isGroupChat = room != null;
    var replier = new com.xfl.msgbot.script.api.legacy.SessionCacheReplier(packageName, act, room, false, "");
    var icon = bundle.getParcelable('android.messagingUser').getIcon().getBitmap();
    var image = bundle.getBundle("android.wearable.EXTENSIONS");
    if (image != null) image = image.getParcelable("background");
    var imageDB = new com.xfl.msgbot.script.api.legacy.ImageDB(icon, image);
    com.xfl.msgbot.application.service.NotificationListener.e.put(room, act);
    responseFix(room, msg, sender, isGroupChat, replier, imageDB, packageName);
}
