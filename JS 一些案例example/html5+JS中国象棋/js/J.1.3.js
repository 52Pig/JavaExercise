(function(window){
var J = function(val){
	return new J.init(val);
	},
	undefined=window.undefined;
	var toString = Object.prototype.toString,
	push = Array.prototype.push,
	slice = Array.prototype.slice;
J.init = function( val ){
	if (typeof val === "object"){
		this[0] = val;
	} else {
		this[0] = document.getElementById( val );
	}
	return this;
}
J.prototype = J.init.prototype = {
	version:"1.3",
	del: function( val ){return J.arr.del( this[0] , val )},
	indexOf: function( val ){return J.arr.indexOf( this[0] , val )},
	delIndex : function( val,n ){return J.arr.delIndex( this[0] , val,n )},
	max : function(){return J.arr.max( this[0] )},
	min : function(){return J.arr.min( this[0] )},
	X : function(){return J.arr.X( this[0] )},
	isRepeat : function(){return J.arr.isRepeat( this[0])},
	clone : function(){
		return toString.call( this[0] ) === "[object Array]"?
		J.arr.clone( this[0] ):J.obj.clone( this[0] );
	},
	unRepeat: function(){return J.arr.unRepeat( this[0] )},
	asc: function(){return J.arr.asc( this[0] )},
	random : function( val ){return J.arr.random( this[0] , val )},
	toJson : function( val ){return J.obj.objToJson( this[0] , val )}
	
}
J.fn = {

//ȡ��ID
id:function(id){
	return document.getElementById(id);
},

//ȡ��tag
tag:function(tag){
	return document.getElementsByTagName(tag);
},

//������չ����
array:{
	
	//����������ָ���ַ���������
	indexOf:function(a,s){	
		for(var i = 0;i < a.length;i++){
			if(a[i] == s) return i;
		}
		return -1;
	},
	
	//����������������ɾ��N��	
	delIndex:function(a,i,n){
		a.splice(i,n||1);
		return a
	},
	
	//ɾ��������Ϊs������	
	del:function(a,s){
		var arr=[];
		for (var i=0; i<a.length ; i++){
			if (a[i] == s) a.splice(i,1);
		}
		return a;
	},
	
	//���������������
	max:function(a){
		return Math.max.apply({},a);
	},
	
	//������������С��
	min:function(a){
		return Math.min.apply({},a);
	},
	
	//�������
	X : function(arr){
		var n = 1;
		for (var i=0,l=arr.length;i<l;i++) n*=arr[i];
		return n;
	},
	
	//�ж����������Ƿ����ظ�
	isRepeat : function (a){
		var b = {};
		for (var i=0,l=a.length; i<l&&!b[a[i]]; b[a[i++]]=1);
		return i<l;	
	},
	
	//������������ ��ʼ/����/����
	create : function (n, len, b){
		var arr = [],b=b||1;
		for (var i=n; i<=len * b; i+=b) arr.push(i);
		return arr;
	},
	
	//��¡���飨����argumentsת����
	clone : function (arr){
		return arr.slice();
	},
	
	//��ȡ�����е������
	random : function(arr, n){
		var arr = J.arr.clone(arr),re=[],n=n||1;
		for (var i=0 ; i<n ; i++){
			var t=J.r(arr.length);
			re.push(arr[t]);
			arr.splice(t,1);
		}
		return re.length==1?re[0]:re;
	},
	
	// ����ȥ�ظ�
	unRepeat : function(a){
		for (var o={},r=[],i=0,l=a.length;i<l;i++){
			if (!o[a[i]]){
				r.push(a[i]);
				o[a[i]] = true;
			}
		}
		return r;
	},
	
	//��������
	asc : function(arr){
		return arr.sort(function (a, b) {return a - b})
	}
},

//���ڴ�����
date: {
	//�õ����ڼ����ڣ�val�Ƿ�ȡ������
	getDate:function(val,x) {
		var data=val?new Date(val):new Date();
		return data.getFullYear()+"��"+(data.getMonth() + 1)+ "��" + data.getDay() + "�� "
				+(x?" ����"+"��һ����������".charAt( data.getDay() ):""); 
	},
	
	//ʱ��תʱ��� 2011-03-09 15:01:23
	timeToSjc : function(val) {
		return new Date(val.slice(0,4),(parseFloat(val.slice(5,7))-1),val.slice(8,10),
						val.slice(11,13),val.slice(14,16),val.slice(17,19)).getTime();
	},
	
	//ʱ���תʱ��
	sjcToTime : function(val){
	 var time = new Date(val) 
		return time.getFullYear()+"-"+(time.getMonth()+1)+"-"+time.getDate()+" "+time.toLocaleTimeString();
	},
	
	//��ʽ��ʱ��
	format : function(str){
		return new Date(str.replace(/[\-\u4e00-\u9fa5]/g, "/"));
	},
	
	//��ת����ʱ��
	sToTime : function(time){
		var s = Math.floor(time % 60);             //��      
		var m = Math.floor((time / 60) % 60);      //��  
		var h = Math.floor((time / 3600) % 24);    //ʱ  
		var d = Math.floor((time / 3600) / 24);     //��
		return {"d":d,"h":h,"m":m,"s":s}; 
	},
	
	//ʱ������
	timeDrift : function(t,val){
		var d=new Date();
		d.setTime(new Date(t.replace( "-","/")).getTime()+val);
		return d;
	},
	
	//ʱ���ʽ��
	dataFormat : function(t){
		var h=t.getFullYear();
		var m=(t.getMonth()<9)?"0"+(t.getMonth()+1):t.getMonth()+1
		var d=(t.getDate()<10)?"0"+t.getDate():t.getDate()
		return  h+"-"+m+"-"+d+" "+t.toLocaleTimeString()
	}
},

//�ַ���������
string:{
	
	//�õ��к����ַ����ĳ���
	len:function(s){
		 var len = 0;
		 for(i = 0;i < s.length;i++){
			 s.charCodeAt(i) > 255?len += 2:len++;
		 }
		 return len;
	},
	
	//ȥ���ַ�����β�ո�
	trim:function(s){
		var reg = /^\s*(.*?)\s*$/gim;
		return s.replace(reg,"$1");
	},
	
	//ȥ������HTML��ǩ,��д��ǩ���������б�ǩ
	trimHtml: function(tag){
		tag ? reg = new RegExp("<\/?"+tag+"(?:(.|\s)*?)>","gi"):reg = /<(?:.|\s)*?>/gi;
		return this.replace(reg,"");
	},
	commaSplit : function(srcNumber) {
		var rxSplit = new RegExp('([0-9])([0-9][0-9][0-9][,.])');
		var arrNumber = ('' + srcNumber).split('.');
		arrNumber[0] += '.';
		do {
			arrNumber[0] = arrNumber[0].replace(rxSplit, '$1,$2');
		} while (rxSplit.test(arrNumber[0]));
		if (arrNumber.length > 1) {
			return arrNumber.join('');
		}else {
		return arrNumber[0].split('.')[0];
      	}
	}
},

//��ѧ����
math: {
	format: function(num,opt){
		return opt?parseInt(num).toLocaleString():parseInt(num).toLocaleString().split(".")[0];
	},
	
	//����ǰ����
	format : function (val,len){
		var len=len||1
		return Array(len).join("0").concat(val).slice(-len);
	},
	
	//����С��������λ
	toFixed : function (num,len){
		return parseFloat(num).toFixed(len||2);
	},
	
	//��������� ���/��С
	random: function( n,m ){
		return Math.floor( Math.random() * ( n || 9999 ) + ( m || 0 ) );
	},
	
	//������� ������ϸ���
	C : function (n,m){
		var n1=1, n2=1;
		for (var i=n,j=1; j<=m; n1*=i--,n2*=j++);
		return n1/n2;
	},
	
	//�����ظ���������ϸ��� ������ϸ���
	C2 : function (n,m){
		var c = 1;
		for (var i=n-m; i<n; c*=++i);
		return c;
	},
	
	//�������� ������ϸ���
	arrC : function (arr, num){
		var r = 0;
		(function f(t,a,n){
			if (n==0) return (r+=t);
			for (var i=0,l=a.length; i<=l-n; i++){
				 f(t*a[i], a.slice(i+1), n-1);
			}
		})(1,arr,num);
    	return r;
	},
	
	//���н��  �����ظ�����
	arrCPermute : function (arr, num){
		var r = [];
		(function f(t,a,n){
			if(n==0) return r.push(t);
			for(var i=0,l=a.length-n; i<=l; i++){
				f(t.concat(a[i]), a.slice(i+1), n-1);
			}
		})([],arr,num);
		return r;
		}
	},
	
	//���н��  �������ظ�����
	arrCPermute1 : function (arr, num){
    	var r = [];
    	(function f(t,a,n){
			if (n==0) return r.push(t);
			for (var i=0,l=a.length; i<l; i++){
            	f(t.concat(a[i]), a.slice(0,i).concat(a.slice(i+1)), n-1);
			}
		})([],arr,num);
    	return r;
	},

//ui����
UI:{
	
	//����
	scroll: function( obj , opt ){
		var opt = opt || {} , boxH = opt.boxH || 30 , trH = opt.trH || 30 ,
			intervalTime = opt.intervalTime || 10, stopTime = opt.stopTime || 1500, play = true;
		obj.style.height = boxH + "px";
		obj.style.lineHeight = trH + "px";
		obj.style.overflow = "hidden";
		obj.innerHTML+=obj.innerHTML;
		obj.onmouseover=function(){play=false};
		obj.onmouseout=function(){play=true};
		(function (){
			var stop=obj.scrollTop%trH==0&&!play;
			if(!stop)obj.scrollTop==parseInt(obj.scrollHeight/2)?obj.scrollTop=0:obj.scrollTop++;
			setTimeout(arguments.callee,obj.scrollTop%trH?intervalTime:stopTime);
		})()
	},
	
	//iframe�Զ��߶�    δ����
	iframeAuto : function (id){
    var a = J("iframe");
    for (var i=0;i<a.length;i++){
        try{
            var d = a[i].contentWindow.document;
            var h = Math.min(d.documentElement.scrollHeight,d.body.scrollHeight);
            fw.dom.setHeight(a[i],h);
        }catch(e){};
    }
}
},

//���󷽷�
object:{
	
	//objת��json
	objToJson : function(obj) {
		switch (typeof(obj)) {
			case 'object':
				var result = [];
				if (obj instanceof Array) {
					for (var i = 0, len = obj.length; i < len; i++) {
						result.push(J.toJson(obj[i]));
					}
					return '[' + result.toString(',') + ']';
				} else if (obj instanceof RegExp) {
					return obj.toString();
				} else {
					for (var attribute in obj) {
						result.push(attribute + ':' + J.object.objToJson(obj[attribute]));
					}
					return '{' + result.join(',') + '}';
				}
			case 'function': return 'function(){}';
			case 'number':return obj.toString();
			case 'string':return  '"' +obj.replace(/(\\|\")/g, '\\$1')
					.replace(/\n|\r|\t/g,function(a) { return ('\n' == a) ? '\\n':('\r' == a) ? '\\r':('\t' == a) ? '\\t': '';}) +'"';
			case 'boolean':return obj.toString();
			default: return obj.toString();
		}
	},
	
	//��ȿ�¡����
	clone : function(o){
		var r;
		if (o.constructor==Object) {
			r = {};
			for(var i in o) r[i] = arguments.callee(o[i]);
		}else if (o.constructor==Array){
			r = [];
			for(var i=0;i<o.length;i++)r[i] = arguments.callee(o[i]);
		}else{
			return o;	
		}
		return r;
	}
},

//include�ļ�
include : function(val){
	document.write('<scr\ipt type="text/javascript" src="'+val+'"></scr\ipt>');
},

//url/����ת
url: function( val ){
	if ( typeof val !== "object" ){
		var url = val || location.href,json = {};
		url.replace( /[\?\&](\w+)=(\w+)/g, function( s , s1 , s2 ){ json[s1] = s2 } );
		return json;
	}else{
		var arr = [];
		for ( var i in val ) arr.push( i+" = "+val[i] );
		return arr.join( "&" );
	}
},

//��������ĸ�������
alert:function (obj,f,n){
	if (typeof obj !== "object") return alert(obj);
	var arr = [];
	for (var i in obj) arr.push(i+" = "+obj[i]);
	return f&&/firefox/i.test(navigator.userAgent)?
	console.log(arr.join(n||"\n")):alert(arr.join(n||"\n\r"));
}
}
J.extend = function ( obj ) {for (var i in obj ) J[ i ] = obj[ i ];}
J.extend(J.fn);
//��д
J.a=J.alert;
J.r=J.math.random;
J.u=J.url;
J.arr=J.array;
J.str=J.string;
J.obj=J.object
J.num=J.math;
J.scroll=J.UI.scroll;
window.a=window.z=J.alert;
window.J = J;
})(window)
//��չ
J.lottery = {
	//�ַ����������� ת��Ϊ��
	toHtml: function(val,opt){
		var opt= opt || {},RB=opt.RB||"RB",BB=opt.BB||"BB",OB=opt.OB||"OB",tag=opt.tag||"li";
		var str=val.replace(/(\#|\:)/g,"+").replace(/(\ )/g,",");
		var getStr=function(arr,css){
		var t="";
			for(var i=0;i<arr.length;i++){
				t+='<'+tag+' class="'+css+'">'+arr[i]+'</'+tag+'>\r';
			}
			return t;
		}
		var tmp=str.indexOf("+")==-1?
			getStr(str.split(','),OB):
			getStr(str.split('+')[0].split(','),RB)+getStr(str.split('+')[1].split(','),BB);
		return tmp;
	},
	//�ַ����������� ת��Ϊ����
	toArray: function(val){
		if ( typeof val !== "string" ) return false;
		var val=val.replace(/(\#|\:)/g,"+").replace(/(\ )/g,",");
		if ( val.indexOf( "+" ) == -1 ) return [val.split( "," ) , ];
		var arr = val.split( "+" );
		return [arr[0].split( "," ) , arr[1].split( "," )];
	}
}
J.lot=J.lottery;