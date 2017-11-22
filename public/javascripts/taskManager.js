function kakunin(obj){
	// $(obj).addClass('animated bounceOutRight');
	obj.style.display = 'none'
	var form = obj.parentNode.children[1];
	form.style.display = 'inline';
	// $(form).addClass('animated bounceInLeft');
}
$(function() {
	$('h1, h2, h3').click(function() {
		$(this).addClass('animated bounceOutRight');
	});
	// $("h1").click(
	// function() {
	// $.get("https://api.coindesk.com/v1/bpi/currentprice.json",
	// function(data) {
	// var json = JSON.parse(data);
	// var rate = json.bpi.USD.rate;
	// $("body").append(rate);
	// });
	// });
	$('.changeTaskNameButton').click(function() {
		var taskName = $(this).siblings('.changedTaskName').val();
		var taskId = $(this).siblings('.changedTaskId').val();
		clickedChangeButton(taskName, taskId);
	});
});
//function clickedChangeButton(obj){
//	var id = obj.id;
//	var listAction = #{jsAction @changeAjax(':task1', ':task2') /}
//    $('document').load(
//       listAction({task1: 'あほ', task2: id}),
//       function(data) {
//           $("div#${id}").text(data)
//       }
//   )
//}
function clickedChangeButton(taskName, taskId) {
    var req = new XMLHttpRequest();
    // 送信先のURLを指定する．
    req.open("POST", "changeAjax");
    // 結果が帰ってきた際に実行されるハンドラを指定する．
    req.onreadystatechange = function () {
        // readyState == 4: 終了
        if (req.readyState != 4) {
            return;
        }
        // status == 200: 成功
        if (req.status != 200) {
            // 成功しなかったため，失敗であることを表示して抜ける．
            alert("失敗しました．");
            return;
        }

        // body にはサーバから返却された文字列が格納される．
        var body = req.responseText;
        // デバッグ表示
        alert('body: ' + body);

        // 戻ってきた JSON 文字列を JavaScript オブジェクトに変換
        var data = eval("(" + body + ")");

        // デバッグ表示
        alert('taskName: ' + data.taskName);

        // 入力に利用したインプットボックスの値を，
        // サーバから戻ってきた値で書き換える．
//        document.getElementById(obj.id).value = data.taskName;
    }
    alert("入力:"　+　taskName);
    // Content-Type の指定
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    // <input id="f"> に入力された文字列をエンコードして送信
    req.send("taskName=" + taskName + "&" + "taskId=" + taskId);
}
function enc(s) {
    return encodeURIComponent(s).replace(/%20/g, '+');
}
























