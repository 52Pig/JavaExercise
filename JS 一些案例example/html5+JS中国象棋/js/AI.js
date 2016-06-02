/*! 一叶孤舟 | qq:28701884 | 欢迎指教 */

var AI = AI||{};

AI.tree = {};


//人工智能初始化
AI.init = function(pace){
	var len=pace.length;
	var arr=[];
	//先搜索棋谱
	for (var i=0;i< com.gambit.length;i++){
		if (com.gambit[i].slice(0,len)==pace) {
			arr.push(com.gambit[i]);
		}
	}
	if (arr.length){
		var inx=Math.floor( Math.random() * arr.length );
		return arr[inx].slice(len,len+4).split("");
	}else{ //如果棋谱里面没有，人工智能开始运作
		var initTime = new Date().getTime();
		AI.treeDepth=play.depth;
		AI.number=0;
		var val=AI.getAlphaBeta(-99999 ,99999, AI.treeDepth, com.arr2Clone(play.map),play.my);
		
		if (!val) {
			AI.treeDepth=2;
			val=AI.getAlphaBeta(-99999 ,99999, AI.treeDepth, com.arr2Clone(play.map),play.my);
		}
		//var val = AI.iterativeSearch(com.arr2Clone(play.map),play.my);
		if (val) {
			var man = play.mans[val.key];
			var nowTime= new Date().getTime();
			com.get("moveInfo").innerHTML='<h3>AI搜索结果：</h3>最佳着法：'+
											com.createMove(com.arr2Clone(play.map),man.x,man.y,val.x,val.y)+
											'<br />搜索深度：'+play.depth+'<br />搜索分支：'+
											AI.number+'个 <br />最佳着法评估：'+
											val.value+'分'
											+' <br />搜索用时：'+
											(nowTime-initTime)+'毫秒'
			return [man.x,man.y,val.x,val.y]
		}else {
			return false;	
		}
	}
}

//迭代加深搜索着法
AI.iterativeSearch = function (map, my){
	var timeOut=1 * 1000;
	var initDepth = 1;
	var maxDepth = 5;
	var initTime = new Date().getTime();
	var val = {};
	for (var i=initDepth; i<=maxDepth; i++){
		var nowTime= new Date().getTime();
		z(i)
		var val = AI.getAlphaBeta(-99999, 99999, i , map ,my)
		if (nowTime-initTime > timeOut){
			return val;
		}
	}
	return false;
}

//取得棋盘上所有棋子
AI.getMapAllMan = function (map, my){
	var mans=[];
	for (var i=0; i<map.length; i++){
		for (var n=0; n<map[i].length; n++){
			var key = map[i][n];
			if (key && play.mans[key].my == my){
				play.mans[key].x = n;
				play.mans[key].y = i;
				mans.push(play.mans[key])
			}
		}
	}
	return mans;
}

//取得棋谱所有己方棋子的着法
AI.getMoves = function (map, my){
	var manArr = AI.getMapAllMan (map, my);
	var moves = [];
	for (var i=0; i<manArr.length; i++){
		var man = manArr[i];
		var val=man.bl(map);
		for (var n=0; n<val.length; n++){
			moves.push([man.x,man.y,val[n][0],val[n][1],man.key])
		}
	}
	return moves;
}

//A:当前棋手value/B:对手value/depth：层级
AI.getAlphaBeta = function (A, B, depth, map ,my) { 
	if (depth == 0) {
		return AI.evaluate(map , my) //局面评价函数; 
　	} 
　	var moves = AI.getMoves(map , my); //生成全部走法; 
　	//这里排序以后会增加效率
	for (var i=0; i < moves.length; i++) {
		AI.number++;
　　	//走这个走法;
		var move= moves[i];
		var key = move[4];
		var oldX= move[0];
		var oldY= move[1];
		var newX= move[2];
		var newY= move[3];
		var clearKey = map[ newY ][ newX ]||"";

		map[ newY ][ newX ] = key;
		delete map[ oldY ][ oldX ];
		play.mans[key].x = newX;
		play.mans[key].y = newY;
		
	　　if (clearKey=="j0"||clearKey=="J0") {//被吃老将,撤消这个走法; 
			play.mans[key]	.x = oldX;
			play.mans[key]	.y = oldY;
			map[ oldY ][ oldX ] = key;
			delete map[ newY ][ newX ];
			if (clearKey){
				 map[ newY ][ newX ] = clearKey;
			}
			var rootKey={"key":key,"x":newX,"y":newY,"value":8888};
			return rootKey; 
	　　}else { 
	　　	var val = -AI.getAlphaBeta(-B, -A, depth - 1, map , -my); 
	
	　　	//撤消这个走法;　 
			play.mans[key]	.x = oldX;
			play.mans[key]	.y = oldY;
			map[ oldY ][ oldX ] = key;
			delete map[ newY ][ newX ];
			if (clearKey){
				 map[ newY ][ newX ] = clearKey;
			}
	　　	if (val >= B) { 
				return B; 
			} 
			if (val > A) { 
	　　　　	A = val; //设置最佳走法; 
				if (AI.treeDepth == depth) var rootKey={"key":key,"x":newX,"y":newY,"value":A};
			} 
		} 
　	} 
	if (AI.treeDepth == depth) {//已经递归回根了
		if (!rootKey){
			//AI没有最佳走法，说明AI被将死了，返回false
			return false;
		}else{
			//z(rootKey)
			//这个就是最佳走法;
			return rootKey;
		}
	}
　return A; 
}

//取得棋盘双方棋子价值差
AI.getMapValue = function (map,my){
	var val=0;
	for (var i=0; i<map.length; i++){
		for (var n=0; n<map[i].length; n++){
			var key = map[i][n];
			if (key){
				val += play.mans[key].value[i][n] * play.mans[key].my;
			}
		}
	}
	//com.show()
	//z(val*my)
	return val*my;
}

//评估棋局
AI.evaluate = function (map,my){
	return AI.getMapValue(map, my);
}



