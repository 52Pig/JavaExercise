/*! һҶ���� | qq:28701884 | ��ӭָ�� */

var AI = AI||{};

AI.tree = {};


//�˹����ܳ�ʼ��
AI.init = function(pace){
	var len=pace.length;
	var arr=[];
	//����������
	for (var i=0;i< com.gambit.length;i++){
		if (com.gambit[i].slice(0,len)==pace) {
			arr.push(com.gambit[i]);
		}
	}
	if (arr.length){
		var inx=Math.floor( Math.random() * arr.length );
		return arr[inx].slice(len,len+4).split("");
	}else{ //�����������û�У��˹����ܿ�ʼ����
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
			com.get("moveInfo").innerHTML='<h3>AI���������</h3>����ŷ���'+
											com.createMove(com.arr2Clone(play.map),man.x,man.y,val.x,val.y)+
											'<br />������ȣ�'+play.depth+'<br />������֧��'+
											AI.number+'�� <br />����ŷ�������'+
											val.value+'��'
											+' <br />������ʱ��'+
											(nowTime-initTime)+'����'
			return [man.x,man.y,val.x,val.y]
		}else {
			return false;	
		}
	}
}

//�������������ŷ�
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

//ȡ����������������
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

//ȡ���������м������ӵ��ŷ�
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

//A:��ǰ����value/B:����value/depth���㼶
AI.getAlphaBeta = function (A, B, depth, map ,my) { 
	if (depth == 0) {
		return AI.evaluate(map , my) //�������ۺ���; 
��	} 
��	var moves = AI.getMoves(map , my); //����ȫ���߷�; 
��	//���������Ժ������Ч��
	for (var i=0; i < moves.length; i++) {
		AI.number++;
����	//������߷�;
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
		
	����if (clearKey=="j0"||clearKey=="J0") {//�����Ͻ�,��������߷�; 
			play.mans[key]	.x = oldX;
			play.mans[key]	.y = oldY;
			map[ oldY ][ oldX ] = key;
			delete map[ newY ][ newX ];
			if (clearKey){
				 map[ newY ][ newX ] = clearKey;
			}
			var rootKey={"key":key,"x":newX,"y":newY,"value":8888};
			return rootKey; 
	����}else { 
	����	var val = -AI.getAlphaBeta(-B, -A, depth - 1, map , -my); 
	
	����	//��������߷�;�� 
			play.mans[key]	.x = oldX;
			play.mans[key]	.y = oldY;
			map[ oldY ][ oldX ] = key;
			delete map[ newY ][ newX ];
			if (clearKey){
				 map[ newY ][ newX ] = clearKey;
			}
	����	if (val >= B) { 
				return B; 
			} 
			if (val > A) { 
	��������	A = val; //��������߷�; 
				if (AI.treeDepth == depth) var rootKey={"key":key,"x":newX,"y":newY,"value":A};
			} 
		} 
��	} 
	if (AI.treeDepth == depth) {//�Ѿ��ݹ�ظ���
		if (!rootKey){
			//AIû������߷���˵��AI�������ˣ�����false
			return false;
		}else{
			//z(rootKey)
			//�����������߷�;
			return rootKey;
		}
	}
��return A; 
}

//ȡ������˫�����Ӽ�ֵ��
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

//�������
AI.evaluate = function (map,my){
	return AI.getMapValue(map, my);
}



