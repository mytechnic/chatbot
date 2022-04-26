let chatEventWs = null;
let connectId = null;
let serviceId = null;
let channel = 'PC';
let authToken = 'xxx';
let currentMessageNo = 0;

function chatEventConnect(serviceId, connectId, channel) {
    chatEventWs = new SockJS('/ws/event?serviceId=' + serviceId + '&connectId=' + connectId);
    chatEventWs.onerror = function (e) {
        console.log('onerror:', e);
    }
    chatEventWs.onopen = function (e) {
        console.log('onopen:', e);
        connectAction(true);
    }
    chatEventWs.onmessage = function (e) {
        let res = JSON.parse(e.data);
        let eventType = res['eventType'];
        if (eventType === 'MESSAGE' || eventType === 'TIMEOUT') {
            ajax('GET', '/api/v1/chatroom/messages',
                {
                    'serviceId': serviceId,
                    'connectId': connectId
                }, function (data) {
                    for (let i in data.messages) {
                        if (currentMessageNo >= data.messages[i]['messageNo']) {
                            continue;
                        }
                        paint(data.messages[i]);
                        currentMessageNo = data.messages[i]['messageNo']
                    }
                });
        }
    }
    chatEventWs.onclose = function (e) {
        console.log('onclose:', e);
        connectAction(false);
    }
}

function paint(message) {
    let msgHistory;
    if (message['writeType'] === 'BOT') {
        msgHistory = '<div class="incoming_msg">\n' +
            '           <div class="received_msg">\n' +
            '             <div class="received_withd_msg">\n' +
            '               <p>' + JSON.stringify(message['answer']) + '</p>\n' +
            '               <span class="time_date">' + toDate(message['created']) + '</span>\n' +
            '             </div>\n' +
            '           </div>\n' +
            '         </div>';

    } else {
        msgHistory = '<div class="outgoing_msg">\n' +
            '           <div class="sent_msg">\n' +
            '             <p>' + message['utterance']['text'] + '</p>\n' +
            '             <span class="time_date">' + toDate(message['created']) + '</span>\n' +
            '           </div>\n' +
            '         </div>';
    }
    $('#msgHistory').append(msgHistory);
    $('#msgHistory').scrollTop(10000000000);
}

function disconnect() {
    if (chatEventWs != null) {
        chatEventWs.close();
    }
}

function connectAction(connect) {
    $("#connectBtn").prop("disabled", connect);
    $("#writeMsg").prop("disabled", !connect).focus();
}

function connect() {
    serviceId = searchParam('serviceId') || '';

    ajax('GET', '/api/v1/chatroom/connect-id?serviceId=' + serviceId + '&channel=' + channel + '&authToken=' + authToken,
        null, function (data) {
            connectId = data
            chatEventConnect(serviceId, connectId, channel);
            $("#writeMsg").focus();
        });
}

function ajax(type, url, data, successFunc) {
    $.ajax({
        type: type,
        url: url,
        data: data || {},
        dataType: 'json',
        success: function (data, textStatus, jqXHR) {
            if (data.code !== 'OK') {
                console.error('code:', data.code, ', message:', data.message);
                return;
            }

            successFunc(data.result);
        }
    });
}

function searchParam(key) {
    return new URLSearchParams(location.search).get(key);
}

function toDate(timestampInMilliSeconds) {
    let date = new Date(timestampInMilliSeconds);
    let day = (date.getDate() < 10 ? '0' : '') + date.getDate();
    let month = (date.getMonth() < 9 ? '0' : '') + (date.getMonth() + 1);
    let year = date.getFullYear();
    let hours = ((date.getHours() % 12 || 12) < 10 ? '0' : '') + (date.getHours() % 12 || 12);
    let minutes = (date.getMinutes() < 10 ? '0' : '') + date.getMinutes();
    let meridiem = (date.getHours() >= 12) ? 'pm' : 'am';
    let formattedDate = year + '-' + month + '-' + day + ' at ' + hours + ':' + minutes + ' ' + meridiem;
    return formattedDate;
}

function writeMsg($writeMsg) {
    ajax('POST', '/api/v1/chatroom/message/text',
        {
            'serviceId': serviceId,
            'connectId': connectId,
            'channel': channel,
            'channelId': null,
            'message': $writeMsg.val()
        }, function (data) {
            $writeMsg.val('');
            $writeMsg.focus();
        });
}

$(function () {
    if (!searchParam('serviceId')) {
        location.href = './?serviceId=star-chatbot';
    }

    $('#msgSendBtn').keypress(function (e) {
        writeMsg($('#writeMsg'));
    });

    $('#writeMsg').keypress(function (e) {
        let code = e.keyCode || e.which;
        if (code !== 13) {
            return;
        }

        writeMsg($(this));
    });

    connect();
});