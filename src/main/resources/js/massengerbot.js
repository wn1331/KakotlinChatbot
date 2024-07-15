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

function response(room, msg, sender, isGroupChat, replier, imageDB, packageName) {
    if (msg.startsWith("@종봇")) {
        let cmd = msg.substr(3);
        gpt(room, sender, cmd, function(result) {
            replier.reply(result);
        });
    }else if(msg.startsWith("[나를 멘션]")){
        let cmd = msg.substr(7);
        gpt(room, sender, cmd, function(result) {
            replier.reply(result);
        });
    }
}
