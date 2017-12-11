enchant();

// 定数
var SCREEN_WIDTH    = 320;
var SCREEN_HEIGHT   = 320;
var CHARA_IMAGE     = "http://enchantjs.com/assets/images/chara1.gif";
var SPRITE_MAX_NUM  = 8;

// ランダム値生成
var randint = function(min, max) { return Math.floor( Math.random()*(max-min+1) ) + min; };

// メイン処理
window.onload = function() {
    // ゲームオブジェクトを生成
    var game = new Game(SCREEN_WIDTH, SCREEN_HEIGHT);
    // 画像読み込み
    game.preload(CHARA_IMAGE);

    // ロード完了時の処理
    game.onload = function() {
        var scene = game.rootScene;
        // 背景色を変更
        scene.backgroundColor = "black";
        // スプライトを大量に生成
        for (var i=0; i<SPRITE_MAX_NUM; ++i) {
            // 画像を生成, 表示
            var sprite = new Sprite(32, 32);
            sprite.image = game.assets[CHARA_IMAGE];
            sprite.moveTo( randint(0, SCREEN_WIDTH-32), randint(0, SCREEN_HEIGHT-32) )
            sprite.tl.moveBy(64, 32, 50).moveBy(-64, -32, 10).loop();
            sprite.ontouchstart = function() { this.parentNode.removeChild(this); };
            scene.addChild(sprite);
        }
        // シーン更新時の処理
        scene.onenterframe = function() {
            // スプライトが全て削除されたかどうかを判定
            if (this.childNodes.length <= 0) {
                game.end((300-game.frame)|0, ((300-game.frame)|0) + "point!!");
            }
        };
    };

    // ゲーム開始
    game.start();
};