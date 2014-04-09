function compDay(a, b) {
	var arr = a.split("-");
	var starttime = new Date(arr[0], arr[1], arr[2]);
	var starttimes = starttime.getTime();
	var arrs = b.split("-");
	var lktime = new Date(arrs[0], arrs[1], arrs[2]);
	var lktimes = lktime.getTime();
	if (starttimes > lktimes) {
		return 1;
	} else if (starttimes == lktimes) {
		return 0;
	}else 
		return -1;
}

function compDayTime(beginTime, endTime) {
	var beginTimes = beginTime.substring(0, 10).split('-');
	var endTimes = endTime.substring(0, 10).split('-');
	beginTime = beginTimes[1] + '-' + beginTimes[2] + '-' + beginTimes[0] + ' '
			+ beginTime.substring(10, 19);
	endTime = endTimes[1] + '-' + endTimes[2] + '-' + endTimes[0] + ' '
			+ endTime.substring(10, 19);

	var a = (Date.parse(endTime) - Date.parse(beginTime)) / 3600 / 1000;
	if (a < 0) {
		return 1;
	} else if (a > 0) {
		return -1;
	} else if (a == 0) {
		return 0;
	}
}

function illegal(str)// 字符串包含测试函数
{
	var i;
	var charset = "%\(\)><";
	for(i=0;i<charset.length;i++)
		if(str.indexOf(charset.charAt(i))>=0)
			return true;
	return false;
}