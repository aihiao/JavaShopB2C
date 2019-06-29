 var tms = [];
    var day = [];
    var hour = [];
    var minute = [];
    var second = [];
    function takeCount() {
        setTimeout("takeCount()", 1000);
        for (var i = 0, j = tms.length; i < j; i++) {
            tms[i] -= 1;
            //计算天、时、分、秒、
            var days = Math.floor(tms[i] / (1 * 60 * 60 * 24));
            var hours = Math.floor(tms[i] / (1 * 60 * 60)) % 24;
            var minutes = Math.floor(tms[i] / (1 * 60)) % 60;
            var seconds = Math.floor(tms[i] / 1) % 60;
            if (days < 0) days = 0;
            if (hours < 0) hours = 0;
            if (minutes < 0) minutes = 0;
            if (seconds < 0) seconds = 0;
            //将天、时、分、秒插入到html中
            document.getElementById(day[i]).innerHTML = days;
            document.getElementById(hour[i]).innerHTML = hours;
            document.getElementById(minute[i]).innerHTML = minutes;
            document.getElementById(second[i]).innerHTML = seconds;
        }
    }
    setTimeout("takeCount()", 1000);